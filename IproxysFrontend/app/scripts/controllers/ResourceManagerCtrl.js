'use strict';

socialNetworkApp.controller('ResourceManagerCtrl', ["$scope", function ResourceManagerCtrl($scope) {

  $scope.resourceType = {
    1: "IP",
    2: "Socket"
  };
  $scope.resourceList = [
    {
      ip: "10.0l0.1.1",
      port: 540,
      protocol: "udp",
      type: "Socket"
    },
    {
      ip: "10.0l0.1.1",
      port: 540,
      protocol: "udp",
      type: "Socket"
    },
    {
      ip: "10.0l0.1.1",
      port: 540,
      protocol: "udp",
      type: "Socket"
    },
    {
      ip: "10.0l0.1.1",
      port: "",
      protocol: "",
      type: "IP"
    }
  ];

  $scope.deleteResource = function (resource, $event) {
    var index = $scope.resourceList.indexOf(resource);
    angular.element("#resourceManagerList .progress").css("visibility","visible");
    if (index > -1) {
      angular.element($event.target).parent().parent().parent().hide({
        duration: 500,
        complete: function () {
          $scope.resourceList.splice(index, 1);
          angular.element("#resourceManagerList .progress").css("visibility","hidden");
        }
      });
    }
  };

  $scope.addNewResource = function(){
    angular.element('#addNewResource').openModal({
      dismissible: false
    });

  };

}]);
