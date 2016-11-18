/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class ResourceAllowanceCtrl {
        public static $inject = ["ResourceAllowanceService", "$timeout", "$scope"];
        private static _snackbarSelector = "#snackbar";
        private static _createResourceForm = "dialog.createResourceDialog form";

        public requestInProgress:boolean = false;
        public liveActions:Array<App.Models.ILiveAction>;
        public newResource:App.Models.ILiveAction;
        public updateResource:App.Models.ILiveAction;
        public IdentifierNames = [];
        private resourceCreationDialog:Element = document.querySelector('dialog.createResourceDialog');
        private resourceUpdateDialog:Element = document.querySelector('dialog.updateResourceDialog');

        constructor(public ResourceAllowanceService:App.Services.IResourceAllowanceService, public $timeout:angular.ITimeoutService, public $scope:angular.IScope) {
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
            this.newResource = <App.Models.ILiveAction>{
                blockedIP: "",
                blockedDomain: "",
                blockedPort: "",
                identifier: "",
                protocol: ""
            };
            this.updateResource = <App.Models.ILiveAction>{
                blockedIP: "",
                blockedDomain: "",
                blockedPort: "",
                identifier: "",
                protocol: ""
            };
            if (this.resourceCreationDialog.hasAttribute("open")) {
                this.resourceCreationDialog.close();
            }
            if (this.resourceUpdateDialog.hasAttribute("open")) {
                this.resourceUpdateDialog.close();
            }

        }

        public openUpdateResource(resource:App.Models.ILiveAction) {
            this.updateResource = angular.copy(resource);

            this.updateResource.blockedPort = this.updateResource.blockedPort == 0 ?  "" :  this.updateResource.blockedPort;
            this.updateResource.protocol = this.updateResource.protocol == 0 ?  "" :  this.updateResource.protocol == 6 ? "TCP" : "UDP";

            this.resourceUpdateDialog.showModal();

            this.$timeout(()=> {
                componentHandler.downgradeElements(document.querySelectorAll(".mdl-textfield"));
                componentHandler.upgradeElements(document.querySelectorAll(".mdl-textfield"));
                getmdlSelect.init(".getmdl-select");

            }, 100);
        }

        public createResource(createResourceForm:IFormControllerExt):void {
            this.requestInProgress = true;
            if (createResourceForm.$valid) {
                this.newResource.identifier = 1;

                if (this.newResource.blockedDomain != "")
                    this.newResource.identifier = 4;
                else if (this.newResource.blockedPort != "" && this.newResource.blockedPort != "" && this.newResource.blockedIP)
                    this.newResource.identifier = 2;
                else if (this.newResource.blockedPort != "" && this.newResource.blockedPort != "") {
                    this.newResource.identifier = 3;
                }
                let protocol = this.newResource.protocol;

                if (this.newResource.protocol == "UDP")
                    this.newResource.protocol = 17;
                else
                    this.newResource.protocol = 6;

                if (this.newResource.blockedPort == "")
                    this.newResource.blockedPort = 0;

                this.ResourceAllowanceService.get().createResource(this.newResource).$promise
                    .then((data:any)=> {
                        data.highlight = true;
                        data.identifier -= 1;

                        if (data.protocol == 17)
                            data.protocol = "UDP";
                        else if(data.protocol == 6)
                            data.protocol = "TCP";
                        else
                            data.protocol = "";

                        if (this.newResource.blockedPort == 0)
                            this.newResource.blockedPort = "";

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
                        document.querySelector(ResourceAllowanceCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                            message: "Recurso creado correctamente",
                            timeout: 7000,
                        });
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

        public deleteResource(resource:App.Models.ILiveAction):void {
            this.requestInProgress = true;

            this.ResourceAllowanceService.get().deleteResource({id: resource.id}).$promise.then(()=> {
                this.liveActions.splice(this.liveActions.indexOf(resource), 1);
                document.querySelector(ResourceAllowanceCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                    message: "Recurso eliminado correctamente",
                    timeout: 7000,
                });
            }).catch((data)=> {
                let message = data.data != null && typeof data.data.message != 'undefined' ? data.data.message : "Ocurrio un error, favor intente mas tarde";
                document.querySelector(ResourceAllowanceCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                    message: message,
                    timeout: 7000,
                });
            }).finally(()=>this.requestInProgress = false);
        }

        public  updateResourceAction():void {
            this.requestInProgress = true;
            this.updateResource.identifier = 1;
            let toUpdate = angular.copy(this.updateResource);
            if ( typeof toUpdate.blockedDomain != "undefined" && toUpdate.blockedDomain != ""  )
                toUpdate.identifier = 4;
            else if (toUpdate.blockedPort != "" && toUpdate.blockedPort != "" && toUpdate.blockedIP)
                toUpdate.identifier = 2;
            else if (toUpdate.blockedPort != "" && toUpdate.blockedPort != "") {
                toUpdate.identifier = 3;
            }

            if (toUpdate.protocol == "UDP")
                toUpdate.protocol = 17;
            else
                toUpdate.protocol = 6;

            if (toUpdate.blockedPort == "")
                toUpdate.blockedPort = 0;

            this.ResourceAllowanceService.get().updateResource({id: toUpdate.id},toUpdate).$promise.then((data:any)=> {
                data.highlight = true;
                data.identifier -= 1;


                this.liveActions.forEach((resourceItem,index)=>{
                   if(resourceItem.id == data.id)
                        this.liveActions[index] = data;
                });
                this.closeDialog();

                document.querySelector(ResourceAllowanceCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                    message: "Recurso actualizado correctamente",
                    timeout: 7000,
                });
            }).catch((data)=> {
                let message = data.data != null && typeof data.data.message != 'undefined' ? data.data.message : "Ocurrio un error, favor intente mas tarde";
                document.querySelector(ResourceAllowanceCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                    message: message,
                    timeout: 7000,
                });
            }).finally(()=>this.requestInProgress = false);
        }
    }

}