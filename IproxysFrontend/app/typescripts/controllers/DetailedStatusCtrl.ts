/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class DetailedStatusCtrl {
        public static $inject = ["$scope", "$websocket", "API", "localStorageService", "$rootScope"];
        public requestInProgress:boolean = false;
        public liveActions:Array<App.Models.ILiveAction> = [];
        private static _snackbarSelector = "#snackbar";
        public IdentifierNames = [];
        private dataStream:angular.websocket.IWebSocket;

        constructor($scope:angular.IScope, $websocket:angular.websocket.IWebSocket, API:any, localStorageService:angular.local.storage.ILocalStorageService, $rootScope:angular.IRootScopeService) {
            //noinspection TypeScriptValidateTypes
            this.dataStream = $websocket(API.webSocketsUrl + API.webSockets.detailedStatus + "?token=" + encodeURIComponent(localStorageService.get<App.Models.IToken>("token").token));
            this.dataStream.onMessage((message) => {
                this.liveActions = JSON.parse(message.data);
            });
            $scope.$on('$viewContentLoaded', ()=> {

            });
            $rootScope.$on('$stateChangeSuccess', ()=> {
                console.log("LIVEACTIONSCONTROLLER->$stateChangeStart:event");
                this.dataStream.close();
            });
            $scope.$emit("setActiveDashboardLink", document.querySelector(".dashboard-menu__link--liveActions"));

        }

    }

}