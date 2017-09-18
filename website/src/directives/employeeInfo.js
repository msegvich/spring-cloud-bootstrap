angular.module('employeeInfo', [])

.directive('fullInfo', function() {
  return {
    templateUrl: 'templates/employeeFullInfo.html',
    restrict: 'EA',
    scope: {
      employee: "="
    },
  };
});
