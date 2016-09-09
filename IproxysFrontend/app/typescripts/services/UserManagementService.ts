/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Services {

    export interface IUserManagementService {

        getUsers():angular.IPromise<Array<App.Models.IUser>>;
        createUser(user:App.Models.IUser):angular.IPromise<App.Models.IUser>;
        deleteUser(user:App.Models.IUser):angular.IPromise<any>;
        updateUser(user:App.Models.IUser):angular.IPromise<App.Models.IUser>;
    }


    export class UserManagementService implements IUserManagementService {


        public static $inject = ["userResourceService"];

        public deferred:angular.IDeferred<Array<App.Models.IUser>>;
        public url:string;
        public id:string;

        constructor(public userResourceService:App.Services.IUserResourceService) {
        }

        getUsers():angular.IPromise<Array<App.Models.IUser>> {
            return this.userResourceService.get().getUsers().$promise;
        }

        createUser(user:App.Models.IUser):angular.IPromise<App.Models.IUser> {
            return this.userResourceService.get().createUser(user).$promise;
        }

        deleteUser(user:App.Models.IUser):angular.IPromise<any>{
            return this.userResourceService.get().deleteUser({id:user.id}).$promise;
        }

        updateUser(user:App.Models.IUser):angular.IPromise<App.Models.IUser>{
            return this.userResourceService.get().updateUser({id:user.id},user).$promise;
        }

    }


}