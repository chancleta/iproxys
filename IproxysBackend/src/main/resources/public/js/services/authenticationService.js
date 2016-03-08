'use strict';
socialNetworkApp.factory('authenticationService', function($resource){
    var resource = $resource("/authenticate");
    return {
        isUserLoggedIn: function(){
            return false;
        },

        doLogIn: function(authenticationForm){
            authenticationForm.hashPassword = authenticationForm.password
          return  resource.save({},authenticationForm);
        },

        doLogOut: function(){

        }

    };
});