package app;

import PersistenceData.UserTable;
import api.common.Authorize;
import api.common.RequestMethod;
import api.common.RequiresAuthorization;
import exceptions.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import models.ActiveToken;
import models.UserRoles;
import org.reflections.Reflections;
import persistence.dao.ActiveTokenDao;
import persistence.dao.UserDao;
import services.common.AuthorizationService;
import spark.Filter;

import java.util.Date;
import java.util.Set;

/**
 * Created by Luis Pena on 8/19/2016.
 */
public class AuthorizationFilters {

    public static void setFilters() {
        Reflections reflections = new Reflections("api");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(RequiresAuthorization.class);


//        Filter beforeAuthenticatedWebSocket = (request, response) -> {

//            String autorizationToken = request.queryParams("token");
//
//            if (autorizationToken == null || autorizationToken.isEmpty()) {
//               throw new UnAuthorizedException( "This Resource Requires Previous Authentication");
//            }
//
//            if (AuthorizationService.getInstance().GetDataFromToken(autorizationToken) == null) {
//               throw new UnAuthorizedException( "Wrong Token, please verify your request");
//            }
//           throw new UnAuthorizedException( "Not implemented");
//
//        };

        annotatedClasses.forEach(annotatedClass -> {

            RequiresAuthorization annotation = annotatedClass.getAnnotation(RequiresAuthorization.class);
//            boolean isWebSocket = annotation.isWebSocket();
            Authorize[] authorizations = annotation.allowedRoles();

            for (Authorize authorization : authorizations) {
                spark.Spark.before(authorization.route(), getFilterFor(authorization.roles(), authorization.method()));
            }
        }

    );
}

    private static Filter getFilterFor(UserRoles[] allowedRoles, RequestMethod requestMethod) {
        return (request, response) -> {

            if( !requestMethod.equals(request.requestMethod())){
                return;
            }
            String autorizationHeader = request.headers("Authorization");

            if (autorizationHeader == null || autorizationHeader.isEmpty() || !autorizationHeader.startsWith("Bearer")) {
               throw new UnAuthorizedException( "This Resource Requires Previous Authorization");
            }

            String encryptedToken = autorizationHeader.substring("Bearer".length()).trim();

            String plainToken = EncryptionHelper.decrypt(encryptedToken);
            if (plainToken == null) {
               throw new UnAuthorizedException( "Invalid Token, please verify your request");
            }

            Jws<Claims> claimsJwt = AuthorizationService.getInstance().GetDataFromToken(plainToken);

            if (claimsJwt == null) {
               throw new UnAuthorizedException( "Invalid Token, please verify your request");
            }

            Long id = claimsJwt.getBody().get("id", Long.class);

            if (id == null) {
               throw new UnAuthorizedException( "Invalid Token, please verify your request");
            }

            ActiveToken activeToken = ActiveTokenDao.findByUserIdAndToken(id,encryptedToken);

            if (activeToken == null) {
               throw new UnAuthorizedException( "Invalid Token, please verify your request");
            }

            if (activeToken.getExpDate() < (new Date()).getTime()) {
                throw new UnAuthorizedException("Expired Token, please verify your request");
            }

            UserTable user = UserDao.findUserById(id);

            if (user == null /* || !Arrays.asList(allowedRoles).contains(user.getRole())*/) {
               throw new UnAuthorizedException( "User is unauthorized for this EndPoint");
            }


        };
    }
}
