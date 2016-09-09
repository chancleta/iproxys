package app;

import api.UserController;
import api.common.OAuthController;
import services.UserService;
import services.common.AuthorizationService;
import spark.Spark;

/**
 * Created by lupena on 2/5/2016.
 */
public class App {

    public static void main(String[] args) throws Exception {

        Spark.port(4000);

//        webSocket("/liveMonitor", LiveMonitorController.class);
//        Spark.staticFileLocation("/public");

//        User u = new User();
//        u.setUsername("chan");
//        u.setRole(UserRoles.SellingPoint);
//        u.setPassword("123456");
//        u.setEmail("chan@chan.com");
//        u.setFirstName("LP");
//        u.setLastName("PL");
//        u.setCreatedBy(new ObjectId("57bd01dbef008d5ff4873a70"));
//        Mongo.getDataStore().save(u);
        Cors.enableCORS();

        new OAuthController(AuthorizationService.getInstance());
        new UserController(UserService.getInstance());

        AuthorizationFilters.setFilters();
    }


}

