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
      'chart.js',
      'ui.router'
    ])
    .config(['$routeProvider', '$httpProvider', 'ChartJsProvider','$stateProvider','$urlRouterProvider', function ($routeProvider, $httpProvider, ChartJsProvider,$stateProvider,$urlRouterProvider) {


      $urlRouterProvider.otherwise('/404');
      $urlRouterProvider.when('', '/');
      $urlRouterProvider.when('/', '/configuration');


      $stateProvider
        .state('dashboard', {
          url: "/",
          templateUrl: 'views/dashboard.html',
          controller: 'DashboardCtrl',
          abstract:true
        })
        .state('dashboard.livemonitor', {
          url: "liveMonitor",
          templateUrl: 'views/networkMonitor.html',
          controller: 'NetworkMonitorCtrl',
        })
        .state('dashboard.configuration', {
          url: "configuration",
          templateUrl: 'views/configuration.html',
          controller: 'ConfigurationCtrl',
        })
        .state('login', {
          url: "/login",
          templateUrl: 'views/loginForm.html',
          controller: 'AuthenticationCtrl',

        })
        .state('404', {
          url: "/404",
          templateUrl: 'views/404.html'
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
    .constant("ConfigData", {url: "http://10.100.29.137", port: 9001, wsURL: "ws://10.100.29.137"})
    .run(["AuthenticationService","$localStorage","$location", function (AuthenticationService,$localStorage,$location) {
      //delete $localStorage['token'];
      if (!AuthenticationService.isUserLoggedIn()) {
        $location.path("/login");
      }
    }]);
