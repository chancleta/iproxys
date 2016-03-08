'use strict';

/**
 * @ngdoc overview
 * @name socialNetworkApp
 * @description
 * # socialNetworkApp
 *
 * Main module of the application.
 */
var socialNetworkApp =
  angular
  .module('socialNetworkApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngStorage',
    'chart.js'
  ])
  .config( ['$routeProvider','$httpProvider','ChartJsProvider', function($routeProvider,$httpProvider,ChartJsProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/networkMonitor.html',
        controller: 'NetworkMonitorCtr',
        controllerAs: 'monitor'
      })
      .when('/login', {
        templateUrl: 'views/loginForm.html',
        controller: 'AuthenticationCtrl',
        controllerAs: 'authentication'
      })
      .when('/404', {
        templateUrl: 'views/404.html'
      })
      .otherwise({
        redirectTo: '/404'
      });


      // Configure all charts
      ChartJsProvider.setOptions({
        colours: ['#97BBCD', '#DCDCDC', '#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
        responsive: true
      });

    $httpProvider.interceptors.push(['$q', '$location', '$localStorage', function ($q, $location, $localStorage) {
      return {
        'request': function (config) {
          config.headers = config.headers || {};
          if ($localStorage.token) {
            config.headers.Authorization = 'Bearer ' + $localStorage.token;
          }
          return config;
        },
        'responseError': function (response) {
          if (response.status === 401 || response.status === 403) {
            $location.path('/');
          }
          return $q.reject(response);
        }
      };
    }]);
  }])
    .constant("ConfigData", {url:"http://10.100.29.137", port: 9001});

