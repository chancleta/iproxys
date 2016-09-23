namespace App.Resources {

    export interface IConfigurationResource extends ng.resource.IResourceClass<App.Models.IConfiguration> {
        getConfiguration() : App.Models.IConfiguration;
        updateConfiguration(user:App.Models.IConfiguration) : App.Models.IConfiguration;
    }

}