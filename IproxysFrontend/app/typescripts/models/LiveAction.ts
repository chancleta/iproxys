/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Models {

    export interface ILiveAction extends ng.resource.IResource<ILiveAction>{
        blockedIP:string;
        blockedDomain:string;
        blockedOnTimeDate:string;
        blockedPort:number;
        identifier:Identifier;
        protocol:number;
    }

    export enum Identifier{
        IP,Socket,Port,Http
    }

}