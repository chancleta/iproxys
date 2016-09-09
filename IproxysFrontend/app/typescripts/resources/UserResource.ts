namespace App.Resources {

    export interface IUserResource extends ng.resource.IResourceClass<App.Models.IUser> {
        getUsers() : Array<App.Models.IUser>;
        createUser(user:App.Models.IUser): App.Models.IUser;
        deleteUser(id:{id:string}): any;
        updateUser(id:{id:string},user:App.Models.IUser): App.Models.IUser;


    }

}