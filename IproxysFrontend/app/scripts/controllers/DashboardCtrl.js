'use strict';

socialNetworkApp.controller('DashboardCtrl',["$scope","$state","$location", function DashboardCtrl($scope,$state){

  //Initialize Mobile Menu
  $(".button-collapse").sideNav();
  $('ul.tabs').tabs();

  $scope.loadState = function($event){


    angular.element("#selectedMenuText").html(angular.element($event.target).html());
    $state.transitionTo('dashboard.'+angular.element($event.target).attr('data-sref'));

    if( angular.element($event.target).parent().parent().attr("id") == "mobile-menu"){
      angular.element($event.target).parent().parent().find(".active").removeClass("active");
      angular.element($event.target).parent().toggleClass("active");
      setTimeout(
        function(){
          $('ul.tabs').tabs('select_tab',angular.element($event.target).attr('data-sref'));

        },300);

    }else{
      angular.element("#mobile-menu").find(".active").removeClass("active");
      angular.element("#mobile-menu").find('a[data-sref="'+angular.element($event.target).attr('data-sref')+'"]').parent().toggleClass("active");
    }
  };
}]);
