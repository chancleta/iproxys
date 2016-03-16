'use strict';
socialNetworkApp.factory('AuthenticationService',["$resource","$localStorage","$location","ConfigData", function($resource,$localStorage,$location,ConfigData){
  var resource = $resource(ConfigData.url + ":" +ConfigData.port + "/authenticate",{},{ authenticate: {method:'POST'}});

  var doLogin = function(responseData){
    if(responseData.error){
      Materialize.toast(responseData.messsage, 5000,"error");
      return;
    }
    $localStorage.token = responseData.token;

    $("nav.navbar-fixed").attr("logged","true");
    $('#loginForm').hide();
    $location.path("/");
  };



  return {
    isUserLoggedIn: function(){
      return $localStorage.token != null;
    },

    doLogIn: function(authenticationForm){

      if(this.isUserLoggedIn())
      {
        Materialize.toast("Usuario ya estó autenticado", 5000,"error");
        return;
      }

      authenticationForm.hashPassword = authenticationForm.password;
      resource.authenticate({},authenticationForm).$promise.then(function(responseData){
        doLogin(responseData);
      })
      .catch(function(response){
        Materialize.toast("Oops! ocurrió un error, favor intentelo mas tarde.", 5000,"error");
      });

    },

    doLogOut: function(){

    }

  };
}]);
