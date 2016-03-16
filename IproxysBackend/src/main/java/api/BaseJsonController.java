package api;

import app.ResponseManager;
import org.reflections.Reflections;

import java.util.Set;

import static spark.Spark.after;

/**
 * Created by lupena on 2/5/2016.
 */
public abstract class BaseJsonController {

    public BaseJsonController(){
        after(((request, response) -> response.type(ResponseManager.RESPONSE_TYPE_JSON)));
    }


}
