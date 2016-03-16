socialNetworkApp.factory('LiveMontiorService',  ["ConfigData", "$websocket","$localStorage",function(ConfigData,$websocket,$localStorage) {
  var webSocket = $websocket.$new({'url': ConfigData.wsURL + ":" + ConfigData.port + "/liveMonitor/?token="+$localStorage.token, 'protocols': [], 'subprotocols': ['base46'] });

  return {
    webSocket: webSocket
  };

}]);
