/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Models {

    export interface IConfiguration extends ng.resource.IResource<IConfiguration>{
        id?:number;
        bandwidth:IBandwidth
        maxBandwidthPerUser:IBandwidth
        tempTimeDuration:number
    }

    export interface IBandwidth{
        bandwidthScale: BandwidthScale;
        bandwidth: number;
    }

    export enum  BandwidthScale{
        Mbps,Kbps, bps,
    }


}