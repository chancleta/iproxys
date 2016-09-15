package app;

import PersistenceData.User;
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
import spark.Request;

import java.util.Date;
import java.util.Set;

/**
 * Created by Luis Pena on 8/19/2016.
 */
public class AuthorizationFilters {

    public static void setFilters() {
        Reflections reflections = new Reflections("api");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(RequiresAuthorization.class);

        annotatedClasses.forEach(annotatedClass -> {

                    RequiresAuthorization annotation = annotatedClass.getAnnotation(RequiresAuthorization.class);
                    boolean isWebSocket = annotation.isWebSocket();
                    Authorize[] authorizations = annotation.allowedRoles();

                    for (Authorize authorization : authorizations) {
                        spark.Spark.before(authorization.route(), getFilterFor(authorization.roles(), authorization.method(), isWebSocket));
                    }
                }

        );
    }

    private static Filter getFilterFor(UserRoles[] allowedRoles, RequestMethod requestMethod, boolean isWebSocket) {
        return (request, response) -> {

            if (!isWebSocket && !requestMethod.equals(request.requestMethod())) {
                return;
            }
            String encryptedToken = isWebSocket ? getTokenFromQueryString(request) : getTokenFromHeader(request);


            String plainToken = EncryptionHelper.decrypt(encryptedToken);
            if (plainToken == null) {
                throw new UnAuthorizedException("Invalid Token, please verify your request");
            }

            Jws<Claims> claimsJwt = AuthorizationService.getInstance().GetDataFromToken(plainToken);

            if (claimsJwt == null) {
                throw new UnAuthorizedException("Invalid Token, please verify your request");
            }


            Integer id= claimsJwt.getBody().get("id", Integer.class);

            if (id == null) {
                throw new UnAuthorizedException("Invalid Token, please verify your request");
            }

            ActiveToken activeToken = ActiveTokenDao.findByUserIdAndToken(id, encryptedToken);

            if (activeToken == null) {
                throw new UnAuthorizedException("Invalid Token, please verify your request");
            }

            if (activeToken.getExpDate() < (new Date()).getTime()) {
                throw new UnAuthorizedException("Expired Token, please verify your request");
            }

            User user = UserDao.findUserById(id);

            if (user == null /* || !Arrays.asList(allowedRoles).contains(user.getRole())*/) {
                throw new UnAuthorizedException("User is unauthorized for this EndPoint");
            }

        };
    }

    private static String getTokenFromQueryString(Request request) throws UnAuthorizedException {

        String authorizationToken = request.queryParams("token");

        if (authorizationToken == null || authorizationToken.isEmpty()) {
            throw new UnAuthorizedException("This Resource Requires Previous Authentication");
        }
        return authorizationToken;
    }

    private static String getTokenFromHeader(Request request) throws UnAuthorizedException {
        String authorizationHeader = request.headers("Authorization");

        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer")) {
            throw new UnAuthorizedException("This Resource Requires Previous Authorization");
        }
        return authorizationHeader.substring("Bearer".length()).trim();

    }
}
