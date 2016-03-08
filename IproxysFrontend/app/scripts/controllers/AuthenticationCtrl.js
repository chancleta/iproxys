'use strict';

socialNetworkApp.controller('AuthenticationCtrl',["$scope","$location","AuthenticationService", function AuthenticationCtrl($scope, $location,AuthenticationService){
  if(AuthenticationService.isUserLoggedIn()){
    $location.path("/");
  }

  $scope.userLoginData = {};

  $scope.doLogIn = function(userData){
    AuthenticationService.doLogIn(userData);
  };
}]);
