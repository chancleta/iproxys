/// <reference path="../../_typings/index.d.ts" />
namespace App {
    import IInjectorService = angular.auto.IInjectorService;
    import IRequestConfig = angular.IRequestConfig;
    import ITimeoutService = angular.ITimeoutService;
    'use strict';

    class Init {

        public static $inject = ["$routeProvider", "$stateProvider", "$urlRouterProvider", "$rootScope", "$httpProvider", "$state", "authorizationService","ChartJsProvider"];
        private static _moduleName = "iProxys";

        public static init():void {
            //angular.module(Init._moduleName, ["ionic", "LocalStorageModule", "ui.router", "ngResource"]).config(Init.config);
            angular.module(Init._moduleName, ["LocalStorageModule", "ui.router", "ngResource","chart.js"]).config(Init.config);
            angular.module(Init._moduleName).run(Init.run);
            angular.module(Init._moduleName).service("authorizationService", App.Services.AuthorizationService);
            angular.module(Init._moduleName).service("tokenResourceService", App.Services.TokenResourceService);
            angular.module(Init._moduleName).service("userResourceService", App.Services.UserResourceService);
            angular.module(Init._moduleName).service("userManagementService", App.Services.UserManagementService);

            angular.module(Init._moduleName).directive("upgradeMaterialCheckbox", [() => {
                return {
                    restrict: "A",
                    link: ($scope:any, element:any, attributes:any) => {
                        new MaterialCheckbox(element[0].querySelector("label.mdl-js-checkbox"));
                    }
                };
            }]);


            angular.module(Init._moduleName).directive("setMaterialDataTable", [() => {
                return {
                    restrict: "A",
                    link: ($scope:any, element:any, attributes:any) => {
                        let id = attributes.setMaterialDataTable;
                        element[0].querySelector("#" + id + "-all").addEventListener('click', (event:any) => {
                            let checked = event.target.checked;
                            element[0].querySelectorAll("label.mdl-checkbox").forEach((item:Element, index:number) => {
                                if (index != 0) {
                                    if (checked)
                                        item.classList.add("is-checked");
                                    else
                                        item.classList.remove("is-checked");
                                }
                            });
                        });
                    }
                };
            }]);


            angular.module(Init._moduleName).constant(UserRoleConfig.named, UserRoleConfig.values);
            angular.module(Init._moduleName).constant(API.named, API.values);

        }

        private static run($rootScope:angular.IRootScopeService, $state:angular.ui.IStateService, authorizationService:App.Services.IAuthorizationService, $timeout:angular.ITimeoutService) {
            //Reloads all MDL components
            $rootScope.$on('$viewContentLoaded', function () {
                $timeout(function () {
                    componentHandler.upgradeAllRegistered();
                });
            });
            $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {

                //If user is logged in, redirect to dashboard if trying to access /login
                //if user is not logged in and trying to access a url that needs auth, redirect to /login
                let loggedIn = authorizationService.isLoggedIn();
                if (toState.name != 'login' && toState.name != 'frontpage' && !loggedIn) {
                    event.preventDefault();
                    $state.go('login');
                }
                if (toState.name == 'login' && loggedIn) {
                    event.preventDefault();
                    $state.go('admin.dashboard');
                }
            });


        }

        private static
        config($urlRouterProvider:angular.ui.IUrlRouterProvider, $stateProvider:angular.ui.IStateProvider, $httpProvider:angular.IHttpProvider,ChartJsProvider:any):void {

            $urlRouterProvider.otherwise("/login");
            $urlRouterProvider.when('', '/');
            $urlRouterProvider.when('/', '/login');

            $stateProvider
                .state('admin', App.Factories.RouteFactory.getInstance().getRoute(App.Controllers.AdminCtrl))
                .state('admin.dashboard', App.Factories.RouteFactory.getInstance().getRoute(App.Controllers.DashboardCtrl))
                .state('login', App.Factories.RouteFactory.getInstance().getRoute(App.Controllers.LoginCtrl))
                //.state('frontpage', App.Factories.RouteFactory.getInstance().getRoute(App.Controllers.FrontPageCtrl))
                //.state('admin.usermanagement', App.Factories.RouteFactory.getInstance().getRoute(App.Controllers.UserManagementCtrl));
            let inFlightAuthRequest:any = null;

            $httpProvider.interceptors.push(['$q', 'localStorageService', "$injector", ($q:angular.IQService, localStorageService:angular.local.storage.ILocalStorageService, $injector:IInjectorService) => {
                return {
                    'request': function (config:angular.IRequestConfig) {
                        config.headers = config.headers || {};
                        if (localStorageService.get<App.Models.IToken>("token")) {
                            config.headers["Authorization"] = 'Bearer ' + localStorageService.get<App.Models.IToken>("token").token;
                        }
                        return config;
                    },
                    'responseError': function (response) {
                        let deferred = $q.defer();
                        let $state:angular.ui.IStateService = <angular.ui.IStateService>$injector.get("$state");

                        if (response.status === 401 && response.data.message == "Expired Token, please verify your request") {

                            let token = localStorageService.get<App.Models.IToken>("token");

                            if (token != null && typeof token.token != 'undefined' && token.token != null && token.token != "" && token.refreshToken != null && token.refreshToken != "") {
                                let $http:angular.IHttpService = $injector.get("$http");
                                let API:any = $injector.get("API");

                                let user:App.Models.IUser = JWTHelper.getClaims(EncryptionHelper.decript(token.token));

                                if (!inFlightAuthRequest) {
                                    inFlightAuthRequest = $http.post(API.url + API.endPoints.refreshToken, {
                                        userId: user.id,
                                        refreshToken: EncryptionHelper.decript(token.refreshToken)
                                    });
                                }

                                inFlightAuthRequest.
                                then((data:any)=> {
                                    localStorageService.set("token", data.data);
                                    response.config.headers.Authorization = 'Bearer ' + data.data;
                                    $http(response.config).then(function (resp) {
                                        inFlightAuthRequest = null;
                                        deferred.resolve(resp);
                                    }, function (resp) {
                                        inFlightAuthRequest = null;
                                        deferred.reject(resp);
                                    });
                                }, (data:any)=> {
                                    inFlightAuthRequest = null;
                                    deferred.reject(data);
                                    localStorageService.remove("token");
                                    $state.go('login');
                                });

                            }

                        } else {
                            inFlightAuthRequest = null;
                            deferred.reject(response);
                        }
                        return deferred.promise;
                    }
                };
            }]);

            ChartJsProvider.setOptions({
                colours: ['#97BBCD', '#DCDCDC', '#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
                responsive: true
            });
        }
    }

    class UserRoleConfig {
        public static named:string = 'userRoleConfig';
        public static values:App.Models.UserRoles = App.Models.UserRoles.SellingPoint;
    }


    class API {
        public static named:string = 'API';
        //TODO: Load from Config FIle
        public static values:any = {
            //url: "http://69.28.92.208:4000",
            url: "http://localhost:4000",
            endPoints: {
                getToken: "/oauth/token",
                validateToken: "/oauth/token/validate",
                getUsers: "/user",
                refreshToken: "/oauth/token/refresh"
            }
        };

    }

    export class EncryptionHelper {
        //TODO: make key be configurable from env or file
        private static _base:string = "UVVKU0xPSUFVSk5DSERZUVRHREpPQUtO";

        public static decript(encrypted:string):string {
            let b = CryptoJS.enc.Base64.parse(EncryptionHelper._base);

            let decryptedData = CryptoJS.TripleDES.decrypt(encrypted, b, {
                mode: CryptoJS.mode.ECB
            });

            return decryptedData.toString(CryptoJS.enc.Utf8);
        }

        public static base64Dec(encoded:string):string {
            var output = encoded.replace('-', '+').replace('_', '/');
            switch (output.length % 4) {
                case 0:
                    break;
                case 2:
                    output += '==';
                    break;
                case 3:
                    output += '=';
                    break;
                default:
                    throw 'Illegal base64url string!';
            }
            return window.atob(output);
        }
    }
    export class JWTHelper {
        public static getClaims(jwt:string):any {
            let encoded = jwt.split('.')[1];
            return JSON.parse(App.EncryptionHelper.base64Dec(encoded));
        }
    }

    Init.init();
}

module App.Factories {
    'use strict';

    export interface IRouteFactory {
        getRoute<T>(controllerType:{ new(...args:any[]): T ;}):angular.route.IRoute;
    }

    export class RouteFactory implements IRouteFactory {

        private static routeFactory:RouteFactory;

        public static getInstance():RouteFactory {
            if (!RouteFactory.routeFactory) {
                RouteFactory.routeFactory = new RouteFactory();
            }
            return RouteFactory.routeFactory;
        }

        public getRoute<T>(controllerType:{ new(...args:any[]): T ;}):angular.route.IRoute {

            let route:IDynamicState = {controllerAs: "vm"};
            route.controllerAs = "vm";
            //noinspection TypeScriptValidateTypes
            switch (<string>controllerType.toString()) {

                //case  App.Controllers.UserManagementCtrl.toString():
                //    route.url = "/user-management";
                //    route.controller = controllerType;
                //    route.templateUrl = "views/usermanagement/usermanagement.html";
                //    //route.templateProvider = DynamicTemplateUrl(route);
                //    break;
                case  App.Controllers.DashboardCtrl.toString():
                    route.url = "/dashboard";
                    route.controller = controllerType;
                    route.templateUrl = "views/dashboard/dashboard.html";
                    //route.templateProvider = DynamicTemplateUrl(route);
                    break;
                case  App.Controllers.AdminCtrl.toString():
                    route.templateUrl = "views/admin/admin.tpl.html";
                    route.controller = controllerType;
                    route.abstract = true;
                    break;
                case  App.Controllers.LoginCtrl.toString():
                    route.url = "/login";
                    route.controller = controllerType;
                    route.templateUrl = "views/login/login.html";
                    break;
                //case  App.Controllers.FrontPageCtrl.toString():
                //    route.url = "/";
                //    route.controller = controllerType;
                //    route.templateUrl = "views/frontpage/frontpage.html";
                //    break;
                default:
                    throw "Argument is not a controller type";
            }
            return route;
        }

    }

    function DynamicTemplateUrl(route:IDynamicState):any {
        return function (authorizationService:App.Services.IAuthorizationService, $templateRequest:angular.ITemplateRequestService) {
            let dynaRoute = route.baseUrl.replace("$USERROLE$", <string>authorizationService.getTokenClaims().role);
            return $templateRequest(dynaRoute);
        };
    }


    interface IDynamicState extends angular.ui.IState {
        baseUrl?:string;
    }

}



