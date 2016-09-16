/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class DashboardCtrl {
        public static $inject = ["$websocket", "API", "localStorageService", "$rootScope", "$scope"];

        private maxTicksPerGraph:number = 18;
        public data:any = [[]];
        public labels:Array<string> = [""];
        public series:Array<string> = ['KB/s'];
        private static _snackbarSelector = "#snackbar";

        public options:any = {
            animation: false,
            tooltipTemplate: function (v) {
                return parseFloat(v.value).toFixed(2) + " KB/s";
            },//Formatting the tooltip 2 decimals and Kbit/s
        };

        private dataStream:angular.websocket.IWebSocket;

        constructor($websocket:angular.websocket.IWebSocket, API:any, localStorageService:angular.local.storage.ILocalStorageService, $rootScope:angular.IRootScopeService, $scope:angular.IScope) {
            document.querySelector("title").innerHTML = "iProxys - Dashboard";

            $scope.$on('$viewContentLoaded', ()=> {
                //noinspection TypeScriptValidateTypes
                this.dataStream = $websocket(API.webSocketsUrl + API.webSockets.liveMonitor + "?token=" + encodeURIComponent(localStorageService.get<App.Models.IToken>("token").token));

                this.dataStream.onMessage((message) => {
                    if (this.data[0].length > this.maxTicksPerGraph) {
                        this.labels = this.labels.slice(1);
                        this.data[0] = this.data[0].slice(1);
                    }
                    let rounded = parseFloat(Math.round(message.data * 100) / 100).toFixed(2);
                    this.labels.push(rounded);
                    this.data[0].push(rounded);
                });


                this.dataStream.onError((event:Event)=> {
                    document.querySelector(DashboardCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                        message: "An error occurred with the WebSocket please try later",
                        timeout: 7000,
                    });
                });

                $rootScope.$on('$stateChangeSuccess', ()=> {
                    console.log("DASBOARDCONTROLLER->$stateChangeStart:event");
                    this.dataStream.close();
                })


            });


        }


    }


}