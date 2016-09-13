package services.common;

import PersistenceData.User;
import app.EncryptionHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.ActiveToken;
import models.Token;
import org.apache.commons.lang3.time.DateUtils;
import persistence.dao.ActiveTokenDao;
import spark.Request;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lupena on 3/15/2016.
 */
public class AuthorizationService {

    private static AuthorizationService authorizationService;
    //TODO: implement this in a file along with mongodb config
    //TODO: When issueing a Token, save the URL to adentify the source of the token
    //TODO: implement Refresh tokens and if the token is expired send an expecific error so the client knows and can refresh the token
    //TODO: encrypt password qhen creating a new user
    private static final String KEY = "some_key_value_may_be_configured_within_json_resource_file";

    private AuthorizationService() {
    }

    public static AuthorizationService getInstance() {
        if (authorizationService == null)
            authorizationService = new AuthorizationService();
        return authorizationService;
    }

    public Token Authorize(User user) {

        Date expDate = DateUtils.addSeconds(new Date(), 3600);
        SecureRandom random = new SecureRandom();

        Map<String, Object> claims = new HashMap<>();
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());

        claims.put("iss", "iproxys");
        claims.put("iat", new Date());
        claims.put("exp", expDate);

        String jwt =
                Jwts.builder().setClaims(claims)
                        .signWith(SignatureAlgorithm.HS512, KEY)
                        .compact();

        Token token = new Token(jwt);

        /**
         * Verify if there is a current token on the db for this user, if so, then we delete it from the db
         */
        ActiveToken currentActiveToken = ActiveTokenDao.findByUserId(user.getId());
        if (currentActiveToken != null) {
            currentActiveToken.delete();
        }

        token.setToken(EncryptionHelper.encrypt(token.getToken())); //encrypting the token
        token.setRefreshToken( new BigInteger(256, random).toString(32)); //setting up the refreshToken


        ActiveToken newActiveToken = new ActiveToken();
        newActiveToken.setExpDate(expDate.getTime());
        newActiveToken.setToken(token.getToken());
        newActiveToken.setUserId(user.getId());
        newActiveToken.setRefreshToken(token.getRefreshToken());

        newActiveToken.save();
        //encrypting the refresh token before response
        token.setRefreshToken(EncryptionHelper.encrypt(token.getRefreshToken()));
        return token;
    }

    public Jws<Claims> GetDataFromAuthHeader(String autorizationHeader) {
        Jws<Claims> jwtClaims = null;
        try {
            jwtClaims =
                    Jwts.parser().setSigningKey(KEY).parseClaimsJws(autorizationHeader.substring("Bearer".length()).trim());
            return jwtClaims;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return jwtClaims;

    }

    public Jws<Claims> GetDataFromToken(String autorizationToken) {
        Jws<Claims> jwtClaims = null;
        try {
            jwtClaims =
                    Jwts.parser().setSigningKey(KEY).parseClaimsJws(autorizationToken);
            return jwtClaims;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return jwtClaims;

    }

    public Jws<Claims> getDataFromRequest(Request request) {
        return authorizationService.GetDataFromToken(EncryptionHelper.decrypt(request.headers("Authorization").substring("Bearer".length()).trim()));
    }


}
