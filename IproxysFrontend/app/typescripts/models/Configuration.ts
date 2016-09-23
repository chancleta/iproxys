/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Models {

    export interface IConfiguration extends ng.resource.IResource<IConfiguration>{
        id?:number;
        bandwidth:IBandwidth
    }

    export interface IBandwidth{
        bandwidthScale: BandwidthScale;
        bandwidth: number;
    }

    export enum  BandwidthScale{
        MegaBit,KiloBit, Bit,
    }


}