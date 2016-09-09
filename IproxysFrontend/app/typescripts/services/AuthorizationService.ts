/// <reference path="../../../typings/tsd.d.ts" />
namespace App.Services {

    export interface IAuthorizationService {

        authorize(user:App.Models.IUser):angular.IPromise<App.Models.IToken>;
        getTokenClaims():App.Models.IUser;
        isLoggedIn():boolean;
    }

    /**
     * Given an URL and an id, it will asynch load that file
     */
    export class AuthorizationService implements IAuthorizationService {

        public static $inject = ["tokenResourceService", "userRoleConfig", "localStorageService"];

        public deferred:angular.IDeferred<App.Models.IToken>;
        public url:string;
        public id:string;

        constructor(public tokenResourceService:App.Services.ITokenResourceService, public userRoleConfig:App.Models.UserRoles,
                    public localStorageService:angular.local.storage.ILocalStorageService) {
        }

        authorize(user:App.Models.IUser):angular.IPromise<App.Models.IToken> {
            return this.tokenResourceService.get().getToken(user).$promise;
        }

        getTokenClaims():App.Models.IUser {
            let user:App.Models.IUser = null;

            let tokenObj = this.localStorageService.get<App.Models.IToken>("token");

            if (tokenObj == null || typeof tokenObj.token == 'undefined' || tokenObj.token == null || tokenObj.token == "")
                return user;

            let actualToken:string = App.EncryptionHelper.decript(tokenObj.token);


            user = App.JWTHelper.getClaims(actualToken);

            return user;
        }

        isLoggedIn():boolean {
            //TODO: be more agressive as for expiration date, more fields
           let claims = this.getTokenClaims();
            return   claims != null && typeof this.getTokenClaims().email !== 'undefined';
        }


    }

}