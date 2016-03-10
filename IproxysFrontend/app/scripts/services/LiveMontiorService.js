socialNetworkApp.factory('LiveMontiorService',  ["ConfigData",function(ConfigData) {

  var webSocket = new WebSocket(ConfigData.wsURL + ":" + ConfigData.port + "/liveMonitor/");
  return {
    webSocket: webSocket
  };

}]);
