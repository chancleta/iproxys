package services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.Token;
import models.User;
import org.apache.commons.lang3.time.DateUtils;

import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lupena on 3/15/2016.
 */
public class AuthenticationService {

    private static AuthenticationService authenticationService;
    private static HashMap<User, Token> authenticatedUsers = new HashMap<>();
    private static final String KEY = "some_key_value_may_be_configured_within_json_resource_file";

    private AuthenticationService() {
    }

    public static AuthenticationService getInstance() {
        if (authenticationService == null)
            authenticationService = new AuthenticationService();
        return authenticationService;
    }

    public Token Authenticate(User user) {

        Map<String, Object> claim = new HashMap<>();
        claim.put("UserData", "SomeData");

        String jwt =
                Jwts.builder().setIssuer("iproxys")
                        .setSubject("users/" + user.getEmail())
                        .setExpiration(DateUtils.addDays(new Date(), 7))
                        .signWith(SignatureAlgorithm.HS256, KEY)
                        .compact();

        Token token = new Token(jwt);
        authenticatedUsers.put(user, token);
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


}
