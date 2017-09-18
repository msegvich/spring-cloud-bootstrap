angular.module('employeeDetails', ['ngRoute', 'configModule', 'EmployeeProviders'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/details/:id', {
    templateUrl: 'views/details/details.html',
    controller: 'employeeDetailController'
  });
}])

.controller('employeeDetailController', ['$scope', '$routeParams', 'config', 'employeeService', 
    function($scope, $routeParams, config, employeeService) {

  $scope.fireEmployee = function() {
    $scope.employee.terminationDate = Date.now();
    employeeService.create($scope.employee);
  };

  config.then(function(data) {
    init();
  });
  var init = function() {
    employeeService.get($routeParams.id).then(function(data) {
      $scope.employee = data.data;
     // employeeService.getDirectReports($routeParams.id).then(function(data2) {
     //   $scope.directReports = data2.data;
     // });
    });
  };
}]);
