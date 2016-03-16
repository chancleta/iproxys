package api;

import app.ResponseManager;
import com.google.gson.JsonSyntaxException;
import exceptions.GenericErrorMessage;
import exceptions.InvalidCompanyDataException;
import org.reflections.Reflections;

import java.util.Set;

import static app.ResponseManager.toJson;
import static spark.Spark.after;
import static spark.Spark.exception;

/**
 * Created by lupena on 2/5/2016.
 */
public abstract class BaseJsonController {

    public BaseJsonController(){

        after(((request, response) -> response.type(ResponseManager.RESPONSE_TYPE_JSON)));

        exception(InvalidCompanyDataException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new GenericErrorMessage(e.getMessage())));
        });

        exception(JsonSyntaxException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new GenericErrorMessage("Error while parsing Json, please verify the Json for errors")));
        });

    }


}
