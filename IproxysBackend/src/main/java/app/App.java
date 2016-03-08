package app;

import InetDataCollector.Sniffer;
import api.AuthenticationController;
import api.CompanyController;
import api.FeedListController;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import com.google.gson.GsonBuilder;
import externalDependencies.EjecutarComando;
import services.CompanyService;
import services.FeedService;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;
import validator.LongTypeAdapter;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.SignatureException;
import java.util.Map;


/**
 * Created by lupena on 2/5/2016.
 */
public class App {
    private final static String SECRET = "my_secret";

    public static void main(String [] args) throws  Exception{

//        Spark.port(9001);
//        Spark.staticFileLocation("/public");
//        Spark.before("*",(request, response) -> {
//            response.header("Access-Control-Allow-Origin", request.headers("Origin"));
//            response.header("Access-Control-Request-Method", "POST, GET, OPTIONS");
//            response.header("Access-Control-Allow-Headers", request.headers("Access-Control-Request-Headers"));
//        });
      //  new CompanyController(CompanyService.getInstance());
 //     new AuthenticationController();
     //   new FeedListController(FeedService.getInstance());

        Sniffer.getInstance().select();

        System.out.println( Paths.get(App.class.getClass().getResource("/squid_float.conf").toURI()));


    }

}

