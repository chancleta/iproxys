/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class LiveActionsCtrl {
        public static $inject = ["$scope"];
        public requestInProgress:boolean = false;

        private static _snackbarSelector = "#snackbar";


        constructor(public $scope:angular.IScope) {

            this.$scope.$on('$viewContentLoaded', ()=> {
  //              this.requestInProgress = false;
//                this.requestInProgress = false;


            });

            $scope.$emit("setActiveDashboardLink", document.querySelector(".dashboard-menu__link--liveActions"));



        }

    }

}