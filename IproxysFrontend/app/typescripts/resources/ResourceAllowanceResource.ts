namespace App.Resources {

    export interface IResourceAllowanceResource extends ng.resource.IResourceClass<App.Models.ILiveAction> {
        getResources() : Array<App.Models.ILiveAction>;
        createResource(resource:App.Models.ILiveAction) : App.Models.ILiveAction;
        deleteResource(id:{id:number}) : void
        updateResource(id:{id:number},resource:App.Models.ILiveAction) : App.Models.ILiveAction;

    }

}