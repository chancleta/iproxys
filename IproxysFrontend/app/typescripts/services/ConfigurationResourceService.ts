/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Services {

    export interface IConfigurationResourceService {
        get():App.Resources.IConfigurationResource;

    }


    export class ConfigurationResourceService implements IConfigurationResourceService {

        public static $inject = ["$resource","API"];

        private static _getConfigDescriptor:ng.resource.IActionDescriptor = {
            method: 'GET',
            isArray: false
        };

        private static _updateConfigDescriptor:ng.resource.IActionDescriptor = {
            method: 'PUT',
            isArray: false,
        };

        constructor(public $resource:angular.resource.IResourceService,public API:any) {}

        public get():App.Resources.IConfigurationResource {

            return <App.Resources.IConfigurationResource> this.$resource(this.API.url + this.API.endPoints.getConfig, {},{
                getConfiguration: ConfigurationResourceService._getConfigDescriptor,
                updateConfiguration : ConfigurationResourceService._updateConfigDescriptor
            });
        }
    }
}