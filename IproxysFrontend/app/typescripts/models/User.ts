/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Models {

    export interface IUser extends ng.resource.IResource<IUser>{
        username?:string;
        email?:string;
        password?:string;
        firstName?:string;
        lastName?:string;
        role?:UserRoles|string;
        id?:string;
    }

    export enum  UserRoles{
        Admin,Distribuitor, SellingPoint
    }


}