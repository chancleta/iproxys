package api;

import JsonParser.CustomGson;
import com.auth0.jwt.JWTSigner;
import exceptions.InvalidCompanyDataException;
import models.Token;
import models.User;
import services.AuthenticationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static app.ResponseManager.toJson;
import static spark.Spark.*;

/**
 * Created by lupena on 2/15/2016.
 */
public class AuthenticationController extends BaseJsonController{

    public AuthenticationController(final AuthenticationService authenticationService){

    post("/authenticate", ((request, response) -> {

        User user = CustomGson.Gson().fromJson(request.body(), User.class);

        if(!user.isValid())
            throw new InvalidCompanyDataException(user.getErrorMessage());

        return authenticationService.Authenticate(user);

    }),toJson());

    options("/authenticate", (request, response) -> true);

    }
}
