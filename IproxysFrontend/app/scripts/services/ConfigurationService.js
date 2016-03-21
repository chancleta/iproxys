'use strict';
socialNetworkApp.factory('ConfigurationService', ["$resource", "$localStorage", "$location", "ConfigData", function ($resource, $localStorage, $location, ConfigData) {
  var bandwithResource = $resource(ConfigData.url + ":" + ConfigData.port + "/configuration/bandwidth", {}, {
    save: {method: 'PUT'},
    get: {method: 'GET'}
  });
  return {

    saveBandwidth: function (configurationForm, callback) {
      var bwData = {data : configurationForm.bandwidth };
      bandwithResource.save({}, bwData).$promise.then(function (responseData) {
          //EMIT SUCCESS
          if (!responseData.error) {
            Materialize.toast('La configuration se actualizó correctamente', 4000, "success");
          } else {
            Materialize.toast(responseData.message, 4000, "error");
          }
        })
        .catch(function (response) {
          Materialize.toast("Oops! ocurrió un error, favor intentelo mas tarde.", 5000, "error");
        })
        .finally(function () {
          callback();
        })
    },

    loadBandwith: function () {
      return bandwithResource.get();
    }

  };
}]);
