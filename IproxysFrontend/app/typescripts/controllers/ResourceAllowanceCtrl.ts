/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class ResourceAllowanceCtrl {
        public static $inject = ["ResourceAllowanceService","$timeout","$scope"];
        private static _snackbarSelector = "#snackbar";
        private static _createResourceForm = "dialog.createResourceDialog form";
        public requestInProgress:boolean = false;
        public liveActions:Array<App.Models.ILiveAction>;
        public newResource:App.Models.ILiveAction;
        public IdentifierNames = [];
        private resourceCreationDialog:Element = document.querySelector('dialog.createResourceDialog');

        constructor(public ResourceAllowanceService:App.Services.IResourceAllowanceService,public $timeout:angular.ITimeoutService, public $scope:angular.IScope) {
            this.$scope.$emit("setActiveDashboardLink", document.querySelector(".dashboard-menu__link--resourceAllowance"));

            this.newResource = <App.Models.ILiveAction>{
                blockedIP: "",
                blockedDomain: "",
                blockedPort: "",
                identifier: "",
                protocol: ""
            };

            let identifierEnum = App.Models.Identifier;

            for (var n in identifierEnum) {
                if (typeof identifierEnum[n] === 'number') this.IdentifierNames.push(n);
            }

            this.ResourceAllowanceService.get().getResources().$promise.then((data)=> {
                this.liveActions = data;
            });


            this.$timeout(()=> {
                componentHandler.downgradeElements(document.querySelectorAll(".mdl-textfield"));
                componentHandler.upgradeElements(document.querySelectorAll(".mdl-textfield"));
                getmdlSelect.init(".getmdl-select");

            }, 100);
        }

        public showResourceCreationDialog():void {
            this.resourceCreationDialog.showModal();
        }

        public closeDialog():void {
            //console.log(this.newResource);
            this.newResource = <App.Models.ILiveAction>{
                blockedIP: "",
                blockedDomain: "",
                blockedPort: "",
                identifier: "",
                protocol: ""
            };
            this.resourceCreationDialog.close();
        }

        public createResource(createResourceForm:IFormControllerExt):void {
            this.requestInProgress = true;
            if (createResourceForm.$valid) {
                this.newResource.identifier = 1;

                if (this.newResource.blockedDomain != "")
                    this.newResource.identifier = 4;
                else if (this.newResource.blockedPort != "" && this.newResource.blockedPort != "" && this.newResource.blockedIP)
                    this.newResource.identifier = 2;
                else if(this.newResource.blockedPort != "" && this.newResource.blockedPort != ""){
                    this.newResource.identifier = 3;
                }
                let protocol = this.newResource.protocol;

                if(this.newResource.protocol == "UDP")
                    this.newResource.protocol = 17;
                else
                    this.newResource.protocol = 6;

                if(this.newResource.blockedPort == "")
                    this.newResource.blockedPort = 0;

                this.ResourceAllowanceService.get().createResource(this.newResource).$promise
                    .then((data:any)=> {
                        data.highlight = true;
                        data.identifier -= 1;
                        this.liveActions.push(data);

                        this.newResource = <App.Models.ILiveAction>{
                            blockedIP: "",
                            blockedDomain: "",
                            blockedPort: "",
                            identifier: "",
                            protocol: ""
                        };
                        this.closeDialog();
                        document.querySelector(ResourceAllowanceCtrl._createResourceForm).reset();
                    })
                    .catch((data)=> {
                        this.newResource.protocol = protocol;
                        let message = data.data != null && typeof data.data.message != 'undefined' ? data.data.message : "Ocurrio un error, favor intente mas tarde";
                          document.querySelector(ResourceAllowanceCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                            message: message,
                            timeout: 7000,
                        });
                    })
                    .finally(()=> this.requestInProgress = false);
            }
        }

        public deleteResource(resource:App.Models.ILiveAction){
            this.requestInProgress = true;

            this.ResourceAllowanceService.get().deleteResource({id:resource.id}).$promise.then(()=>{
                this.liveActions.splice(this.liveActions.indexOf(resource), 1);

            }).catch((data)=>{
                let message = data.data != null && typeof data.data.message != 'undefined' ? data.data.message : "Ocurrio un error, favor intente mas tarde";
                document.querySelector(ResourceAllowanceCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                    message: message,
                    timeout: 7000,
                });
            }).finally(()=>this.requestInProgress = false);
        }
    }

}