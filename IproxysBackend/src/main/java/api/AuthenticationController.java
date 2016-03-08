package api;

import JsonParser.CustomGson;
import com.auth0.jwt.JWTSigner;
import exceptions.InvalidCompanyDataException;
import models.Token;
import models.User;

import java.util.HashMap;
import java.util.Map;
import static app.ResponseManager.toJson;
import static spark.Spark.*;

/**
 * Created by lupena on 2/15/2016.
 */
public class AuthenticationController extends BaseJsonController{

    private final static String SECRET = "my_secret";


    public AuthenticationController(){

    post("/authenticate", ((request, response) -> {

        //Validate User and create a proper Claim
        User user = CustomGson.Gson().fromJson(request.body(), User.class);

        if(!user.isValid())
            throw new InvalidCompanyDataException(user.getErrorMessage());

        Map<String,Object> claim = new HashMap<>();
        claim.put("SomeDataSomeShit", "AnotherDataAnotherShit");

        return  new Token((new JWTSigner(SECRET)).sign(claim));

    }),toJson());

    options("/authenticate", (request, response) -> true);

    }
}
