/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Models {

    export interface IConfiguration extends ng.resource.IResource<IConfiguration>{
        bandwidth:IBandwidth
    }

    export interface IBandwidth{
        scale: DataScale;
        bandwidth: number;
    }

    export enum  DataScale{
        MegaBytes,KiloBytes, Bytes,
    }


}