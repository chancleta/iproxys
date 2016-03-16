'use strict';
socialNetworkApp.factory('ConfigurationService',["$resource","$localStorage","$location","ConfigData", function($resource,$localStorage,$location,ConfigData){
  var resource = $resource(ConfigData.url + ":" +ConfigData.port + "/configuration",{},{ authenticate: {method:'POST'}});

  return {

    saveConfiguration: function(configurationForm){

      resource.authenticate({},configurationForm).$promise.then(function(responseData){
        //EMIT SUCCESS
        if(!responseData.error){
          Materialize.toast('La configuration se actualizó correctamente', 4000,"success");
        }else{
          Materialize.toast(responseData.message, 4000,"error");

        }
        })
        .catch(function(response){
          Materialize.toast("Oops! ocurrió un error, favor intentelo mas tarde.", 5000,"error");
        });


    },

    doLogOut: function(){

    }

  };
}]);
