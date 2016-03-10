'use strict';

socialNetworkApp.controller('DashboardCtrl',["$scope","$state","$location", function DashboardCtrl($scope){

  $scope.$on('childViewLoaded', function (event, args) {
    angular.element(".dashboard .dashboard-tabs div").removeClass("active");
    angular.element(args.target).parent().addClass("active");
  });

}]);
