angular.module('employeeSummary', ['ngRoute', 'configModule', 'EmployeeProviders'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/summary', {
    templateUrl: 'views/summary/summary.html',
    controller: 'employeeSummaryController'
  });
}])

.controller('employeeSummaryController', ['$scope', 'config', 'employeeService',
    function($scope, config, employeeService) {
  $scope.employees = [];

  $scope.addEmployee = function() {
    var employee = {
      "firstName": "Matt",
      "middleInitial": "",
      "lastName": "Segvich",
      "terminationDate": null,
      "logonId": "123v4"
    };
    employeeService.create(employee).then(function(data) { 
      $scope.employees.push(data.data);
    });
  };

  config.then(function(data) {
    console.log('config loaded');
    init();
  });

  var init = function() {
    employeeService.getAll().then(function(data) {
      $scope.employees = data.data;
    });
  };
}]);
