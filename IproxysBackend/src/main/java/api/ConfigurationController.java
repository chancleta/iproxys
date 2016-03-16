package api;

import JsonParser.CustomGson;
import exceptions.InvalidCompanyDataException;
import models.User;
import services.AuthenticationService;

import static app.ResponseManager.toJson;
import static spark.Spark.put;

/**
 * Created by lupena on 2/15/2016.
 */
public class ConfigurationController extends BaseJsonController{

    public ConfigurationController(final AuthenticationService authenticationService){

    put("/configuration", (request, response) -> {

        User user = CustomGson.Gson().fromJson(request.body(), User.class);

        if(!user.isValid())
            throw new InvalidCompanyDataException(user.getErrorMessage());

        return authenticationService.Authenticate(user);

    },toJson());

//    options("/authenticate", (request, response) -> true);

    }
}
