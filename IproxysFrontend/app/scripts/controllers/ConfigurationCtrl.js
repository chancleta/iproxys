'use strict';

socialNetworkApp.controller('ConfigurationCtrl',["$scope", function ConfigurationCtrl($scope){

  $scope.$emit('childViewLoaded', {target: 'a[ui-sref=".configuration"]'});


}]);
