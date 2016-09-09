package models;

import org.hibernate.validator.constraints.NotEmpty;
import validator.ValiatableObject;

import javax.validation.constraints.NotNull;


public class RefreshToken extends ValiatableObject {

    @NotNull
    private long userId;

    @NotNull
    @NotEmpty
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
