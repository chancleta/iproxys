package app;

import java.util.HashMap;

import static spark.Spark.before;
import static spark.Spark.options;

/**
 * Created by Luis Pena on 8/19/2016.
 */
public class Cors {
    private static final HashMap<String, String> corsHeaders = new HashMap<String, String>();

    static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
    }

    public static void enableCORS() {

        options("/*", (request, response) -> "");

        before((request, response) -> {
            response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Methods", request.headers("Access-Control-Request-Method"));
            response.header("Access-Control-Allow-Headers", request.headers("Access-Control-Request-Headers"));
        });
    }
}
