'use strict';

socialNetworkApp.controller('NetworkMonitorCtr',["$scope","$location","$interval","AuthenticationService","FeedService", function NetworkMonitorCtr($scope, $location,$interval, AuthenticationService,FeedService){



  var maximum = document.getElementById('container').clientWidth / 2 || 300;
  $scope.data = [[]];
  $scope.labels = ["January"];
  $scope.series = ['Series A'];

  $scope.options = {
    animation: false,

  };

  // Update the dataset at 25FPS for a smoothly-animating chart
  $interval(function () {
    getLiveChartData();
  }, 1000);

  function getLiveChartData () {
    if ($scope.data[0].length > 30) {
      $scope.labels = $scope.labels.slice(1);
      $scope.data[0] = $scope.data[0].slice(1);
    }

    console.log( $scope.data[0]);
    $scope.labels.push('');
    $scope.data[0].push(getRandomInt());

  }
  function getRandomInt() {
    return Math.floor(Math.random() * (300 - 0 + 1)) + 0;
  }





}]);
