package models;

import PersistenceData.PersistenceProvider;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by lupena on 2/15/2016.
 */
@Entity
@Table(name = "activeTokens")
@NamedQueries({
        @NamedQuery(name = "ActiveToken.findByUserId", query = "select u from ActiveToken u where u.userId = :id"),
        @NamedQuery(name = "ActiveToken.findByUserIdAndRefreshToken", query = "select u from ActiveToken u where u.userId = :id and u.refreshToken = :refreshToken")
})
public class ActiveToken extends PersistenceProvider implements Serializable {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column
    private long id;
    @Column(nullable = false)
    private long expDate;
    @Column(columnDefinition = "text",nullable = false)
    private String token;
    @Column(nullable = false)
    private long userId;
    @Column(columnDefinition = "text",nullable = false)
    private String refreshToken;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExpDate() {
        return expDate;
    }

    public void setExpDate(long expDate) {
        this.expDate = expDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
