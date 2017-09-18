'use strict';

angular.module('eemApp', [
    'ngRoute',
    'employeeSummary',
    'employeeDetails',
    'employeeInfo',
    'configModule'
])
.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');

  $routeProvider.otherwise({redirectTo: '/summary'});
}]);
