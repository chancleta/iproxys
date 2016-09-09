/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Services {

    export interface ITokenResourceService {
        get():App.Resources.ITokenResource;

    }

    /**
     * Given an URL and an id, it will asynch load that file
     */
    export class TokenResourceService implements ITokenResourceService {

        public static $inject = ["$resource","API"];

        private static _getTokenDescriptor:ng.resource.IActionDescriptor = {
            method: 'POST',
            isArray: false
        };

        private static _refreshTokenDescriptor:ng.resource.IActionDescriptor = {
            method: 'POST',
            isArray: false,
        };

        constructor(public $resource:angular.resource.IResourceService,public API:any) {}

        public get():App.Resources.ITokenResource {
            TokenResourceService._refreshTokenDescriptor.url = this.API.url + this.API.endPoints.refreshToken;
            return <App.Resources.ITokenResource> this.$resource(this.API.url + this.API.endPoints.getToken, {},{
                getToken: TokenResourceService._getTokenDescriptor,
                refreshToken: TokenResourceService._refreshTokenDescriptor
            });
        }
    }
}