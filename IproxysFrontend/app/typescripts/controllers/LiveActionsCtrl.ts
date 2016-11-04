/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class LiveActionsCtrl {
        public static $inject = ["$scope","LiveActionResourceService","$websocket","API","localStorageService","$rootScope"];
        public requestInProgress:boolean = false;
        public liveActions:Array<App.Models.ILiveAction>;
        private static _snackbarSelector = "#snackbar";
        public IdentifierNames = [];
        private dataStream:angular.websocket.IWebSocket;

        constructor($scope:angular.IScope, LiveActionResourceService:App.Services.ILiveActionResourceService,$websocket:angular.websocket.IWebSocket,API:any,localStorageService:angular.local.storage.ILocalStorageService,$rootScope:angular.IRootScopeService) {
            //noinspection TypeScriptValidateTypes
            this.dataStream = $websocket(API.webSocketsUrl + API.webSockets.liveAction + "?token=" + encodeURIComponent(localStorageService.get<App.Models.IToken>("token").token));
            this.dataStream.onMessage((message) => {
              this.liveActions = JSON.parse(message.data);
                console.log(this.liveActions);
            });

            let identifierEnum = App.Models.Identifier;

            for (var n in identifierEnum) {
                if (typeof identifierEnum[n] === 'number') this.IdentifierNames.push(n);
            }

            $scope.$on('$viewContentLoaded', ()=> {
                this.requestInProgress = true;
                LiveActionResourceService.get().getLiveActions().$promise.then((data)=>{
                    this.liveActions = data;
                }).catch((err)=>{
                    console.log(err);
                }).finally(()=>{
                    this.requestInProgress = false;
                });
            });

            $rootScope.$on('$stateChangeSuccess', ()=> {
                console.log("LIVEACTIONSCONTROLLER->$stateChangeStart:event");
                this.dataStream.close();
            });


            $scope.$emit("setActiveDashboardLink", document.querySelector(".dashboard-menu__link--liveActions"));


        }

    }

}