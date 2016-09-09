/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';


    export class LoginCtrl {
        public static $inject = ["localStorageService", "$rootScope", "authorizationService", "$state","$templateCache"];
        public user:App.Models.IUser = null;
        public requestInProgress:boolean = false;
        private static _snackbarSelector = "#snackbar";

        constructor(public localStorage:angular.local.storage.ILocalStorageService, $rootScope:angular.IRootScopeService, public authorizationService:App.Services.IAuthorizationService,
                    public $state:angular.ui.IStateService, $templateCache:angular.ITemplateCacheService) {
            document.querySelector("title").innerHTML = "Punto de Ventas - Login";

            //inviladate all view cache so the user can log in with diferent roles
            $templateCache.removeAll();
        }

        doLogin(loginForm:angular.IFormController):void {
            this.requestInProgress = true;

            if (loginForm.$valid) {
                this.authorizationService.authorize(this.user)
                    .then((data)=> {
                        this.localStorage.set("token", data);
                        this.$state.go("admin.dashboard");
                    })
                    .catch((data)=> {
                        document.querySelector(LoginCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                            message: data.data.message,
                            timeout: 7000,
                        });
                        //this.user.password = "";
                    })
                    .finally(()=> this.requestInProgress = false);
            }

        }

        goBack():void{
            this.$state.go("frontpage");
        }

        toLowerCase(){
            this.user.username = this.user.username.toLowerCase();
        }
    }
}