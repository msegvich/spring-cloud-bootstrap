'use strict';

angular.module('EmployeeProviders', ['configModule'])

.service('employeeService', function($http, config) {

  var endpoint = '';
  config.then(function(data) {
    endpoint = data.propertySources[0].source.zuulEndpoint;
  }); //'http://localhost:8888/zuul/employee/new-services/employee';
  $http.defaults.headers.common.Authorization = 'Basic dXNlcjpwYXNzd29yZA==';

  return {
    getAll: function() {
      return $http.get(endpoint + '/all');
    },
    get: function(id) {
      return $http.get(endpoint + '/' + id);
    },
    create: function(employee) {
      $http.defaults.headers.post.Authorization = "Basic YWRtaW46cGFzc3dvcmQ==";
      return $http.post(endpoint, employee);
    },
    getDirectReports: function(id) {
      return $http.get(endpoint + '/' + id + '/directReports');
    }
  };
});
