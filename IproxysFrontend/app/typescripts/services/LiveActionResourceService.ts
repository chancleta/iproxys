/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Services {

    export interface ILiveActionResourceService {

        get():App.Resources.ILiveActionResource;

    }

    export class LiveActionResourceService implements ILiveActionResourceService {

        public static $inject = ["$resource", "API"];

        private static _getLiveActions:ng.resource.IActionDescriptor = {
            method: 'GET',
            isArray: true
        };


        constructor(public $resource:angular.resource.IResourceService, public API:any) {
        }

        public get():App.Resources.ILiveActionResource {

            return <App.Resources.ILiveActionResource> this.$resource(this.API.url + this.API.endPoints.getLiveActions, {}, {
                getLiveActions: LiveActionResourceService._getLiveActions,

            });
        }
    }
}