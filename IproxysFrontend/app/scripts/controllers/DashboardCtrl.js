'use strict';

socialNetworkApp.controller('DashboardCtrl',["$scope","$location","AuthenticationService", function DashboardCtrl($scope, $location,AuthenticationService){

  $scope.$on('childViewLoaded', function (event, args) {
    angular.element(".dashboard .dashboard-tabs div").removeClass("active");
    angular.element(args.target).parent().addClass("active");
  });



}]);
