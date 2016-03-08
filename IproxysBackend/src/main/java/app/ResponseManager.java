package app;

import JsonParser.CustomGson;
import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by lupena on 2/5/2016.
 */
public final class ResponseManager
{

    public static final String RESPONSE_TYPE_JSON = "application/json";

    public static String toJson(Object o){
        return CustomGson.Gson().toJson(o);
    }

    public static  ResponseTransformer toJson() {
        return ResponseManager::toJson;
    }
}
