/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class UserManagementCtrl {
        public static $inject = ["userManagementService", "authorizationService", "$timeout", "$scope"];
        public users:Array<App.Models.IUser> = [];
        public isAdmin:boolean = false;
        public newUser:App.Models.IUser = <App.Models.IUser>{};
        public requestInProgress:boolean = false;
        private userCreationDialog:Element = document.querySelector('dialog.createUserDialog');
        private errorDialog:Element = document.querySelector('dialog.errorDialog');
        private updateUserDialog:Element = document.querySelector('dialog.editUserDialog');

        public errorMsg:string = "";
        private static _snackbarSelector = "#snackbar";
        private static _createUserForm = ".createUserDialog form";
        public editUser:App.Models.IUser = <App.Models.IUser>{};
        private currentUser:App.Models.IUser = <App.Models.IUser>{};

        constructor(public userManagementService:App.Services.IUserManagementService, public authorizationService:App.Services.IAuthorizationService, public $timeout:angular.ITimeoutService, public $scope:angular.IScope) {

            this.$scope.$on('$viewContentLoaded', ()=> {
                this.requestInProgress = true;
                this.userManagementService.getUsers().then((data) => {
                    this.currentUser = authorizationService.getTokenClaims();
                    this.users = data.filter((user) => {
                        return user.id != this.currentUser.id;
                    });

                }).catch((data) => {
                    let message = data.data != null && typeof data.data.message != 'undefined' ? data.data.message : "Ocurrio un error, favor intente mas tarde";
                    this.showErrorCreationDialog(message);
                }).finally(()=>{
                    this.requestInProgress = false;
                });


            });

            this.isAdmin = this.authorizationService.getTokenClaims().role == App.Models.UserRoles[App.Models.UserRoles.Admin];

            //register Dialogs
            if (!this.userCreationDialog.showModal) {
                dialogPolyfill.registerDialog(this.userCreationDialog);
            }
            if (!this.errorDialog.showModal) {
                dialogPolyfill.registerDialog(this.errorDialog);
            }
        }

        createUser(createUserForm:IFormControllerExt):void {
            this.requestInProgress = true;
            let roles = App.Models.UserRoles;
            if (createUserForm.$valid) {
                this.newUser.role = this.authorizationService.getTokenClaims().role == roles[roles.Admin] ? roles[roles.Distribuitor] : roles[roles.SellingPoint];
                this.newUser.username = this.newUser.username.toLowerCase();
                this.newUser.email = this.newUser.email.toLowerCase();
                this.userManagementService.createUser(this.newUser)
                    .then((data:any)=> {
                        data.highlight = true;
                        this.users.push(data);
                        this.newUser = <App.Models.IUser>{};
                        this.closeUserCreationDialog();
                        document.querySelector(UserManagementCtrl._createUserForm).reset();
                    })
                    .catch((data)=> {
                        let message = data.data != null && typeof data.data.message != 'undefined' ? data.data.message : "Ocurrio un error, favor intente mas tarde";

                        if (message == "Email already exists") {
                            createUserForm.email.$setValidity("alreadyExists", false);
                        }
                        if (message == "Username already exists") {
                            createUserForm.username.$setValidity("alreadyExists", false);
                        }
                        document.querySelector(UserManagementCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                            message: message,
                            timeout: 7000,
                        });
                    })
                    .finally(()=> this.requestInProgress = false);
            }

        }

        updateUser(editUserForm:IFormControllerExt):void {
            this.requestInProgress = true;
            let roles = App.Models.UserRoles;

            if (editUserForm.$valid) {
                this.userManagementService.updateUser(this.editUser)
                    .then((data:any)=> {
                        data.highlight = true;
                        this.users.forEach((user, index) => {
                            if (user.id == data.id) {
                                this.users[index] = data;
                            }
                        });
                        this.closeUpdateUserDialog();
                    })
                    .catch((data)=> {
                        let message = data.data != null && typeof data.data.message != 'undefined' ? data.data.message : "Ocurrio un error, favor intente mas tarde";

                        if (message == "Email already exists") {
                            editUserForm.email.$setValidity("alreadyExists", false);
                        }
                        if (message == "Username already exists") {
                            editUserForm.username.$setValidity("alreadyExists", false);
                        }
                        document.querySelector(UserManagementCtrl._snackbarSelector).MaterialSnackbar.showSnackbar({
                            message: message,
                            timeout: 7000,
                        });
                    })
                    .finally(()=> this.requestInProgress = false);
            }

        }

        //TODO: finish delete user, add the checkbox functionality , add pagination

        public showUserCreationDialog():void {
            this.closeAllDialogs();
            this.userCreationDialog.showModal();
        }

        public showErrorCreationDialog(message:string):void {
            this.closeAllDialogs();
            this.errorMsg = message;
            this.errorDialog.showModal();

        }

        public closeUserCreationDialog():void {
            this.userCreationDialog.close();
        }

        public closeErrorDialog():void {
            this.errorDialog.close();
        }

        public deleteUser(user:App.Models.IUser):void {
            if (user.id == this.currentUser.id) {
                return;
            }

            this.userManagementService.deleteUser(user).then(()=> {
                document.querySelector("#user_" + user.id).classList.add("removing");
                this.$timeout(()=> {
                    this.users.splice(this.users.indexOf(user), 1);
                }, 500);
            });
        }

        public showEditUserDialog(user:App.Models.IUser) {

            if (user.id == this.currentUser.id) {
                return;
            }
            this.closeAllDialogs();

            this.editUser = angular.copy(user);
            //this.$scope.editUserForm.$valid = true;
            (<any>this.updateUserDialog.querySelectorAll(".mdl-textfield")).forEach(function (item:Element) {
                item.classList.add("is-dirty");
                item.classList.add("ng-touched");
                item.classList.remove("is-invalid");

            });
            this.updateUserDialog.showModal();
        }

        public closeUpdateUserDialog():void {
            this.editUser = <App.Models.IUser>{};
            this.updateUserDialog.close();
        }

        private closeAllDialogs() {
            if (this.userCreationDialog.hasAttribute("open")) {
                this.closeUserCreationDialog();
            }
            if (this.updateUserDialog.hasAttribute("open")) {
                this.closeUserCreationDialog();
            }
            if (this.errorDialog.hasAttribute("open")) {
                this.closeUserCreationDialog();
            }

        }
    }

}