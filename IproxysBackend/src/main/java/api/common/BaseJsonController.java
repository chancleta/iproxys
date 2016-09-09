package api.common;

import app.ResponseManager;
import com.google.gson.JsonSyntaxException;
import exceptions.GenericErrorMessage;
import exceptions.GenericException;
import exceptions.UnAuthorizedException;

import static app.ResponseManager.toJson;
import static spark.Spark.after;
import static spark.Spark.exception;

/**
 * Created by lupena on 2/5/2016.
 */
public abstract class BaseJsonController {

    public BaseJsonController(){

        after(((request, response) -> response.type(ResponseManager.RESPONSE_TYPE_JSON)));

        exception(GenericException.class, (e, req, res) -> {
            res.type(ResponseManager.RESPONSE_TYPE_JSON);
            res.status(400);
            res.body(toJson(new GenericErrorMessage(e.getMessage())));
        });

        exception(JsonSyntaxException.class, (e, req, res) -> {
            res.type(ResponseManager.RESPONSE_TYPE_JSON);
            res.status(400);
            res.body(toJson(new GenericErrorMessage("Error while parsing Json, please verify the Json for errors")));
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.type(ResponseManager.RESPONSE_TYPE_JSON);
            res.status(400);
            res.body(toJson(new GenericErrorMessage(e.getMessage())));
        });

        exception(UnAuthorizedException.class, (e, req, res) -> {
            res.type(ResponseManager.RESPONSE_TYPE_JSON);
            res.status(401);
            res.body(toJson(new GenericErrorMessage(e.getMessage())));
        });
    }


}
