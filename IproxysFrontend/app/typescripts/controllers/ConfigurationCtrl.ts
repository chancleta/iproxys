/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class ConfigurationCtrl {
        public static $inject = ["$timeout", "$scope", "$interval"];
        public users:Array<App.Models.IUser> = [];

        public requestInProgress:boolean = false;

        private static _snackbarSelector = "#snackbar";
        public configuration:App.Models.IConfiguration = <App.Models.IConfiguration>{};
        public dataScales = [];

        constructor(public $timeout:angular.ITimeoutService, public $scope:angular.IScope, public $interval:angular.IIntervalService) {
            let dataScale = App.Models.DataScale;
            this.configuration = <Models.IConfiguration>{
                bandwidth: {
                    bandwidth: 50.00,
                    scale: dataScale[dataScale.MegaBytes]
                }
            };

            for (var n in dataScale) {
                if (typeof dataScale[n] === 'number') this.dataScales.push(n);
            }

        }

        public updateConfig():void {
            this.requestInProgress = true;
            this.$interval(()=> {
                this.requestInProgress = false
            }, 4000);
        }

    }

}