/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Services {

    export interface IResourceAllowanceService {

        get():App.Resources.IResourceAllowanceResource;

    }

    export class ResourceAllowanceService implements IResourceAllowanceService {

        public static $inject = ["$resource", "API"];

        private static _getResources:ng.resource.IActionDescriptor = {
            method: 'GET',
            isArray: true
        };


        private static _createResource:ng.resource.IActionDescriptor = {
            method: 'POST',
            isArray: false,
        };


        constructor(public $resource:angular.resource.IResourceService, public API:any) {
        }

        public get():App.Resources.IResourceAllowanceResource {

            return <App.Resources.IResourceAllowanceResource> this.$resource(this.API.url + this.API.endPoints.getResourceAllowance, {}, {
                getResources: ResourceAllowanceService._getResources,
                createResource: ResourceAllowanceService._createResource,
                deleteResource: {
                    method: 'DELETE',
                    isArray: false,
                    url: this.API.url + this.API.endPoints.getResourceAllowance + "/:id"
                },
                updateResource:{
                    method: 'PUT',
                    isArray: false,
                    url: this.API.url + this.API.endPoints.getResourceAllowance + "/:id"

                }
            });
        }
    }
}