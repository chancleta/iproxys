'use strict';
socialNetworkApp.controller('authenticationController', function authenticationController($scope, authenticationService){
    $scope.userLoginData = {}

    $scope.doLogIn = function(userData){
        authenticationService.doLogIn(userData).$promise.then(function(token){console.log(token)}).catch(function(response){ console.log(response);});
    };
});