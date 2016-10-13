namespace App.Resources {

    export interface ILiveActionResource extends ng.resource.IResourceClass<App.Models.ILiveAction> {
        getLiveActions() : Array<App.Models.ILiveAction>;
    }

}