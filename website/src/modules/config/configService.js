var configModule = angular.module('configModule', []);

//Temporarily hardcoded, to go into an env file for building
var configServiceUrl = 'http://localhost:8888/zuul/config/employee-web-ui/key';

configModule.factory('config', ['$http', function($http) {
  return $http.get(configServiceUrl).then(function(response) {
    return response.data;
  });
}]);
