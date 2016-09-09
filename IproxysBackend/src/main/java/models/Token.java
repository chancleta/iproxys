package models;

/**
 * Created by lupena on 2/15/2016.
 */
public class Token {
    private String token;
    private String refreshToken;

    public Token(String token){
        this.token = token;
//        this.refreshToken =
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToke) {
        this.refreshToken = refreshToke;
    }
}
