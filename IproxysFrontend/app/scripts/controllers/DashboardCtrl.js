'use strict';

socialNetworkApp.controller('DashboardCtrl',["$scope","$state","$location", function DashboardCtrl($scope,$state){

  $('ul.tabs').tabs();

  $scope.loadView = function(viewName){
    $state.transitionTo('dashboard.'+viewName);
  };

}]);
