'use strict';
socialNetworkApp.factory('AuthenticationService',["$resource","$localStorage","$location","ConfigData", function($resource,$localStorage,$location,ConfigData){
  var resource = $resource(ConfigData.url + ":" +ConfigData.port + "/authenticate",{},{ authenticate: {method:'POST'}});

  var doLogin = function(responseData){
    if(responseData.error){
      //push error
      return;
    }
    $localStorage.token = responseData.token;
    $location.path("/");

  };



  return {
    isUserLoggedIn: function(){
      return $localStorage.token != null;
    },

    doLogIn: function(authenticationForm){

      if(this.isUserLoggedIn())
      {
        //push message user already loagged in, redirecting to feed
        return;
      }

      authenticationForm.hashPassword = authenticationForm.password;
      resource.authenticate({},authenticationForm).$promise.then(function(responseData){
        doLogin(responseData);
      })
      .catch(function(response){

        //push error
      });


    },

    doLogOut: function(){

    }

  };
}]);
