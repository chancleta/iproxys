'use strict';

socialNetworkApp.controller('NetworkMonitorCtrl',["$scope","$interval","LiveMontiorService", function NetworkMonitorCtrl($scope,$interval, LiveMontiorService){



 var maxTicksPerGraph = 30;
  $scope.data = [[]];
  $scope.labels = [""];
  $scope.series = ['KB/s'];

  $scope.options = {
    animation: false,
    tooltipTemplate: function(v) {return parseFloat(v.value).toFixed(2)+" KB/s";},//Formatting the tooltip 2 decimals and Kbit/s
  };

  // Update the dataset at 25FPS for a smoothly-animating chart
  $interval(function () {
  }, 400);



  LiveMontiorService.webSocket.$on("$message" , function(data){
    if ($scope.data[0].length > maxTicksPerGraph) {
      $scope.labels = $scope.labels.slice(1);
      $scope.data[0] = $scope.data[0].slice(1);
    }

    $scope.labels.push('');
    $scope.data[0].push(data);
  });


  $scope.$emit('childViewLoaded', {target: 'a[ui-sref=".livemonitor"]'});




}]);
