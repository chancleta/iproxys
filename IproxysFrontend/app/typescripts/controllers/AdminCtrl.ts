/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class AdminCtrl {
        public static $inject = ["localStorageService", "$state", "authorizationService"];
        public user:App.Models.IUser = <App.Models.IUser>{};
        public userRoles = App.Models.UserRoles;
        public dashboardMenuItems:NodeListOf<Element> = document.querySelectorAll(".mdl-navigation .mdl-navigation__link");
        public dashboardItem:Element = document.querySelector(".dashboard-menu__link--dashboard");
        public dashboardMenu:Element = document.querySelector(".dashboard-menu");

        constructor(public localStorage:angular.local.storage.ILocalStorageService, public $state:angular.ui.IStateService, authorizationService:App.Services.IAuthorizationService) {

            this.user = authorizationService.getTokenClaims();

            for (let linkCount = 0; linkCount < this.dashboardMenuItems.length; linkCount++) {
                this.dashboardMenuItems[linkCount].addEventListener('click',(event:UIEvent) => {
                    this.setActiveDashboardLink(event.target);
                });
            }
            this.setActiveDashboardLink(this.dashboardItem);
        }

        private setActiveDashboardLink(active:EventTarget|Element) {
            this.cleanActiveDashboardLink();
            (<Element>active).classList.add('mdl-navigation__link--current');
            this.closeDashboardMenuMobileDevices();
        }

        private cleanActiveDashboardLink() {
            for (var linkCount = 0; linkCount < this.dashboardMenuItems.length; linkCount++) {
                this.dashboardMenuItems[linkCount].classList.remove("mdl-navigation__link--current");
            }
        }
        private closeDashboardMenuMobileDevices(){
            this.dashboardMenu.classList.remove("is-visible");
            let obfusacator  = document.querySelector('.mdl-layout__obfuscator');
            if(obfusacator)
                obfusacator.classList.remove('is-visible');

        }

        logout():void {
            this.localStorage.remove('token');
            this.$state.go('frontpage');
        }
    }

}