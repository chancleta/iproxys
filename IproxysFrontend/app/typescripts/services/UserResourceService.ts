/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Services {

    export interface IUserResourceService {

        //authorize(user:App.Models.IUser):angular.IPromise<App.Models.IToken>;
        get():App.Resources.IUserResource;

    }


    export class UserResourceService implements IUserResourceService {

        public static $inject = ["$resource", "API"];

        private static _getUserDescriptor:ng.resource.IActionDescriptor = {
            method: 'GET',
            isArray: true
        };

        private static _createUserDescriptor:ng.resource.IActionDescriptor = {
            method: 'POST',
            isArray: false
        };


        constructor(public $resource:angular.resource.IResourceService, public API:any) {
        }

        public get():App.Resources.IUserResource {

            return <App.Resources.IUserResource> this.$resource(this.API.url + this.API.endPoints.getUsers, {}, {
                getUsers: UserResourceService._getUserDescriptor,
                createUser: UserResourceService._createUserDescriptor,
                updateUser:{
                    method: 'PUT',
                    isArray: false,
                    url: this.API.url + this.API.endPoints.getUsers + "/:id"
                },
                deleteUser: {
                    method: 'DELETE',
                    isArray: false,
                    url: this.API.url + this.API.endPoints.getUsers + "/:id"
                }
            });
        }
    }
}