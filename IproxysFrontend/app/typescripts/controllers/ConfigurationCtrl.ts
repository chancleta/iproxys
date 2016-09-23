/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class ConfigurationCtrl {
        public static $inject = ["$timeout", "$scope", "$interval", "configurationResourceService"];
        public users:Array<App.Models.IUser> = [];

        public requestInProgress:boolean = false;

        private static _snackbarSelector = "#snackbar";
        public configuration:App.Models.IConfiguration = <App.Models.IConfiguration>{};
        public dataScales = [];

        constructor(public $timeout:angular.ITimeoutService, public $scope:angular.IScope, public $interval:angular.IIntervalService, public configurationResourceService:App.Services.IConfigurationResourceService) {
            let dataScale = App.Models.BandwidthScale;
            this.configuration = <Models.IConfiguration>{
                bandwidth: {
                    bandwidth: 1,
                    scale: dataScale[dataScale.KiloBit]
                }
            };

            for (var n in dataScale) {
                if (typeof dataScale[n] === 'number') this.dataScales.push(n);
            }

            $scope.$emit("setActiveDashboardLink", document.querySelector(".dashboard-menu__link--configuration"));

            $timeout(()=> {
                componentHandler.downgradeElements(document.querySelectorAll(".mdl-textfield"));
                componentHandler.upgradeElements(document.querySelectorAll(".mdl-textfield"));
                //noinspection TypeScriptUnresolvedVariable
                getmdlSelect.init(".getmdl-select");
            }, 100);

            this.requestInProgress = true;
            this.configurationResourceService.get().getConfiguration().$promise.then((data)=> {
                this.configuration.bandwidth = data.bandwidth;
                this.configuration.id = data.id;
            }).finally(()=> {
                this.requestInProgress = false;
            });
        }

        public updateConfig(configForm:angular.IFormController):void {
            this.requestInProgress = true;
            if (configForm.$valid) {
                this.configurationResourceService.get().updateConfiguration(this.configuration).$promise.finally(()=> {
                    this.requestInProgress = false
                });
            }
        }
    }

}