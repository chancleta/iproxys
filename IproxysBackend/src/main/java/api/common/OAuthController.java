package api.common;

import JsonParser.CustomGson;
import PersistenceData.UserTable;
import app.EncryptionHelper;
import exceptions.GenericException;
import exceptions.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import models.ActiveToken;
import models.RefreshToken;
import models.UserCredentials;
import persistence.dao.ActiveTokenDao;
import persistence.dao.UserDao;
import services.common.AuthorizationService;

import java.util.Date;

import static app.ResponseManager.toJson;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 * @api {POST} /oauth/authorize Request Json Web Token
 * @apiName GetToken
 * @apiGroup oAuth
 * @apiParam {String} username Username.
 * @apiParam {String} password User password.
 * @apiParam {String} role User Role: Admin, Distribuitor or SellingPoint.
 * @apiSuccess {String} token The users Auth Token.
 * @apiSuccessExample Success-Response:
 * HTTP/1.1 200 OK
 * {
 * "token": "eyJhbGciOiJIUzUxMiJ9.eyJmaXJzdE5hbWUiOiJMdWlzIiwibGFzdE5hbWUiOiJQZW5hIiwiaXNzIjoid3d3LmFjdGl2YWNpb25lc3JkLmNvbSIsImlkIjoiNTdiNjY4NmIxMWI2ZTQyNWE4YzM4YTg2IiwiZXhwIjoxNDcxNjE5NjI5MjY2LCJpYXQiOjE0NzE2MTYwMjkyNjgsImVtYWlsIjoibGpwZW5hdXJlbmFAZ21haWwuY29tIiwidXNlcm5hbWUiOiJjaGFuY2xldGEifQ.kvXRRa13H9T9JxBB-c8nvzU5aiZUw9YI97BOMoLU9L_y9877w3S7Se0JvAWsshajVQ6wPCA0kqqv6RTippHzWQ",
 * }
 * @apiError InvalidRole The role is not valid or empty
 * @apiErrorExample InvalidRole:
 * HTTP/1.1 400 Bad Request
 * {
 * "error": true,
 * "message": "role: Cannot be empty and must be valid"
 * }
 * @apiError InvalidUser Invalid Username or Password
 * @apiErrorExample InvalidUser:
 * HTTP/1.1 400 Bad Request
 * {
 * "error": true,
 * "message": "Invalid Username or Password"
 * }
 * @apiError EmptyUserOrPassword Username/Password field are empty or missing
 * @apiErrorExample EmptyUserOrPassword:
 * HTTP/1.1 400 Bad Request
 * {
 * "error": true,
 * "message": "field: may not be empty field: may not be null"
 * }
 */
public class OAuthController extends BaseJsonController {

    public OAuthController(final AuthorizationService authorizationService) {
        super();

        get("/oauth/token/validate", (request, response) -> {

            String autorizationHeader = request.headers("Authorization");

            if (autorizationHeader == null || autorizationHeader.isEmpty() || !autorizationHeader.startsWith("Bearer")) {
                throw new UnAuthorizedException("Invalid Token");
            }

            String encryptedToken = autorizationHeader.substring("Bearer".length()).trim();

            String plainToken = EncryptionHelper.decrypt(encryptedToken);
            if (plainToken == null) {
                throw new UnAuthorizedException("Invalid Token");
            }

            Jws<Claims> claimsJwt = AuthorizationService.getInstance().GetDataFromToken(plainToken);

            if (claimsJwt == null) {
                throw new UnAuthorizedException("Invalid Token");
            }

            Long id = claimsJwt.getBody().get("id", Long.class);

            if (id == null) {
                throw new UnAuthorizedException("Invalid Token");
            }

            ActiveToken activeToken = ActiveTokenDao.findByUserId(id);

            if (activeToken == null) {
                throw new UnAuthorizedException("Invalid Token");
            }


            if (activeToken.getExpDate() < (new Date()).getTime()){
                throw new UnAuthorizedException("Token expired");

            }

            if(!activeToken.getToken().equals(encryptedToken)) {
                throw new UnAuthorizedException("Invalid Token");
            }

            return "";
        });

        post("/oauth/token", (request, response) -> {

            UserCredentials recievedUserData = CustomGson.Gson().fromJson(request.body(), UserCredentials.class);

            if (recievedUserData == null)
                throw new GenericException("Invalid payload");

            if (!recievedUserData.isValid())
                throw new GenericException(recievedUserData.getErrorMessage());


            UserTable actualUserDB = UserDao.findUserByCredentials(recievedUserData.getUsername(), recievedUserData.getPassword());

            if (actualUserDB == null)
                throw new GenericException("Invalid Username or Password");

            return authorizationService.Authorize(actualUserDB);

        }, toJson());


        post("/oauth/token/refresh", (request, response) -> {

            RefreshToken recievedRereshToken = CustomGson.Gson().fromJson(request.body(), RefreshToken.class);

            if (recievedRereshToken == null)
                throw new GenericException("Invalid payload");

            if (!recievedRereshToken.isValid())
                throw new GenericException(recievedRereshToken.getErrorMessage());

            //TODO: refresh token must have a exp date
            ActiveToken activeToken = ActiveTokenDao.findByUserIdAndRefreshToken(recievedRereshToken.getUserId(), recievedRereshToken.getRefreshToken());

            if (activeToken == null)
                throw new GenericException("Invalid Refresh token or userid");

            return authorizationService.Authorize( UserDao.findUserById(recievedRereshToken.getUserId()));

        }, toJson());
    }


}
