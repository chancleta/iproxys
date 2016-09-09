package models;

import org.hibernate.validator.constraints.NotEmpty;
import validator.ValiatableObject;

import javax.validation.constraints.NotNull;


public class UserCredentials extends ValiatableObject {



    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
