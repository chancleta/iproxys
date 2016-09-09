namespace App.Resources {

    export interface ITokenResource extends ng.resource.IResourceClass<App.Models.IToken> {
        getToken(user:App.Models.IUser) : App.Models.IToken;
        refreshToken(user:App.Models.IRefreshToken) : App.Models.IToken;

    }

}