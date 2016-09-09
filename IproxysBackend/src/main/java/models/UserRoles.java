package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Luis Pena on 8/18/2016.
 */
public enum UserRoles implements Serializable {
    @SerializedName("Admin")
    Admin,
    @SerializedName("Distribuitor")
    Distribuitor,
    @SerializedName("SellingPoint")
    SellingPoint
}
