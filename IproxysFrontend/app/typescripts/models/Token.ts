/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Models {

    export interface IToken extends ng.resource.IResource<IToken>{
        token:string;
        refreshToken:string;
    }

    export interface IRefreshToken{
        userId:string;
        refreshToken:string;
    }
}