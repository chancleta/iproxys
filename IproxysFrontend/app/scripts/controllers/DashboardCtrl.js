'use strict';

socialNetworkApp.controller('DashboardCtrl',["$scope","$state","$location", function DashboardCtrl($scope,$state){

  //Initialize Mobile Menu
  $(".button-collapse").sideNav();

  $scope.updateActiveItem = function($event){

    angular.element($event.target).parent().parent().find(".active").removeClass("active");
    angular.element($event.target).parent().toggleClass("active");

    angular.element("#selectedMenuText").html(angular.element($event.target).html());

    if( angular.element($event.target).parent().parent().attr("id") == "mobile-menu"){
      angular.element("#desktopMenu").find(".active").removeClass("active");
      angular.element("#desktopMenu").find('a[ui-sref="'+angular.element($event.target).attr('ui-sref')+'"]').parent().toggleClass("active");
    }else{
      angular.element("#mobile-menu").find(".active").removeClass("active");
      angular.element("#mobile-menu").find('a[ui-sref="'+angular.element($event.target).attr('ui-sref')+'"]').parent().toggleClass("active");
    }
  };
}]);
