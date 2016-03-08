package api;

import app.ResponseManager;

import static spark.Spark.after;

/**
 * Created by lupena on 2/5/2016.
 */
public class BaseJsonController {

    public BaseJsonController(){
        after(((request, response) -> response.type(ResponseManager.RESPONSE_TYPE_JSON)));
    }

}
