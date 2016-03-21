package api;

import JsonParser.CustomGson;
import PersistenceData.Configuration;
import PersistenceData.ConfigurationType;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import exceptions.InvalidCompanyDataException;
import externalDependencies.GeneralConfiguration;
import models.User;
import services.AuthenticationService;

import static app.ResponseManager.toJson;
import static spark.Spark.put;
import static spark.Spark.get;

/**
 * Created by lupena on 2/15/2016.
 */
//@Authenticated(route = "/configuration")
public class ConfigurationController extends BaseJsonController {

    public ConfigurationController() {

        put("/configuration/bandwidth", (request, response) -> {

            models.Configuration config = CustomGson.Gson().fromJson(request.body(), models.Configuration.class);
            config.setType(ConfigurationType.Bandwidth);

            if(!config.isValid())
                throw new InvalidCompanyDataException(config.getErrorMessage());

            try{
                Configuration bandwidthConfig = new Configuration().findByConfigurationType(ConfigurationType.Bandwidth);
                GeneralConfiguration.setAvailableBandwidth(Double.parseDouble(config.getData()));
                bandwidthConfig.setData(config.getData());
                bandwidthConfig.update();

            }catch (NumberFormatException ex){
                throw new InvalidCompanyDataException("Ocurrio un error al salvar los datos, verifique que los datos sean correctos");

            }
            return config;
        }, toJson());

        get("/configuration/bandwidth", (request, response) -> {
            Configuration bandwidthConfig = new Configuration().findByConfigurationType(ConfigurationType.Bandwidth);
            models.Configuration config = new models.Configuration();
            config.setType(ConfigurationType.Bandwidth);
            config.setData(bandwidthConfig == null ? Double.toString(GeneralConfiguration.getAvailableBandwidth()) : bandwidthConfig.getData());
            return config;
        }, toJson());

//    options("/authenticate", (request, response) -> true);

    }
}
