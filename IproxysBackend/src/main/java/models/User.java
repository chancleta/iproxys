package models;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;
import validator.ValiatableObject;

import javax.validation.constraints.NotNull;


/**
 * Created by lupena on 2/15/2016.
 */
@Entity("users")
@Indexes(@Index(value = "username", fields = @Field("username")))
public class User extends ValiatableObject {

    @Id
    private long id;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String email;


    private String password;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @Reference
    @NotNull(message = "Cannot be empty and must be valid")
    private UserRoles role;
    private ObjectId createdBy;

    public long getId() {
        return id;
    }

    public ObjectId getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ObjectId createdBy) {
        this.createdBy = createdBy;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRoles getRole() {
        return role;
    }



}
