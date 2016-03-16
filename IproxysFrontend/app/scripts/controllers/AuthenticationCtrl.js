'use strict';

socialNetworkApp.controller('AuthenticationCtrl',["$scope","$location","AuthenticationService","$rootScope", function AuthenticationCtrl($scope, $location,AuthenticationService,$rootScope){

  if(AuthenticationService.isUserLoggedIn()){
    $location.path("/");
  }

  $scope.userLoginData = {};

  $scope.doLogIn = function(userData){
    AuthenticationService.doLogIn(userData);
  };

}]);
