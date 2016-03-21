package app;

import InetDataCollector.Sniffer;
import PersistenceData.Configuration;
import PersistenceData.ConfigurationType;
import api.*;
import externalDependencies.GeneralConfiguration;
import org.reflections.Reflections;
import services.AuthenticationService;
import services.CompanyService;
import services.FeedService;
import spark.Filter;
import spark.Spark;

import java.util.Set;

import static spark.Spark.*;


/**
 * Created by lupena on 2/5/2016.
 */
public class App {
    private final static String SECRET = "my_secret";

    public static void main(String[] args) throws Exception {

        Spark.port(9001);

        webSocket("/liveMonitor", LiveMonitorController.class);

        Spark.staticFileLocation("/public");

        Spark.before("*", (request, response) -> {
            response.header("Access-Control-Allow-Origin", request.headers("Origin"));
            response.header("Access-Control-Request-Method", "POST, GET, OPTIONS, PUT");
            response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
            response.header("Access-Control-Allow-Headers", request.headers("Access-Control-Request-Headers"));
        });

        new CompanyController(CompanyService.getInstance());
        new AuthenticationController(AuthenticationService.getInstance());
        new FeedListController(FeedService.getInstance());
        new ConfigurationController();
        initAuthenticatedRoutes();
        Sniffer.getInstance().select();
        initConfiguration();
    }

    public static void initAuthenticatedRoutes() {
        Reflections reflections = new Reflections("api");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Authenticated.class);

        Filter beforeAuthenticatedRoutes = (request, response) -> {

            String autorizationHeader = request.headers("Authorization");

            if (autorizationHeader == null || autorizationHeader.isEmpty() || !autorizationHeader.startsWith("Bearer")) {
                halt(401, "This Resource Requires Previous Authentication");
            }

            if (AuthenticationService.getInstance().GetDataFromAuthHeader(autorizationHeader) == null) {
                halt(401, "Wrong Token, please verify your request");
            }

        };

        Filter beforeAuthenticatedWebSocket = (request, response) -> {

            String autorizationToken = request.queryParams("token");

            if (autorizationToken == null || autorizationToken.isEmpty()) {
                halt(401, "This Resource Requires Previous Authentication");
            }

            if (AuthenticationService.getInstance().GetDataFromToken(autorizationToken) == null) {
                halt(401, "Wrong Token, please verify your request");
            }

        };

        annotatedClasses.forEach(annotatedClass -> {

            Authenticated annotation = annotatedClass.getAnnotation(Authenticated.class);
            String route = annotation.route();
            boolean isWebSocket = annotation.isWebSocket();

            before(route, isWebSocket ? beforeAuthenticatedWebSocket : beforeAuthenticatedRoutes );
            before(route +"/", isWebSocket ? beforeAuthenticatedWebSocket : beforeAuthenticatedRoutes );
            before(route +"/*", isWebSocket ? beforeAuthenticatedWebSocket : beforeAuthenticatedRoutes );
        });
    }

    public static void initConfiguration(){

        Configuration bandwidthConfig = new Configuration().findByConfigurationType(ConfigurationType.Bandwidth);
        //Setting default configuration If not present
        if(bandwidthConfig == null){
            GeneralConfiguration.setAvailableBandwidth(1.5*GeneralConfiguration.Megabit);
            bandwidthConfig = new Configuration();
            bandwidthConfig.setType(ConfigurationType.Bandwidth);
            bandwidthConfig.setData(Double.toString(GeneralConfiguration.getAvailableBandwidth()));
            bandwidthConfig.save();
        }else{
            GeneralConfiguration.setAvailableBandwidth(Double.parseDouble(bandwidthConfig.getData()));
        }
    }
}

