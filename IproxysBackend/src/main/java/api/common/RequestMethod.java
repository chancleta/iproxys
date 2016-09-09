package api.common;

/**
 * Created by Luis Pena on 8/22/2016.
 */
public enum RequestMethod {
    GET("GET"), HEAD("HEAD"), POST("POST"), PUT("PUT"), PATCH("PATCH"), DELETE("DELETE"), OPTIONS("OPTIONS"), TRACE("TRACE");
    private final String name;

    private RequestMethod(String s) {
        name = s;
    }

    public boolean equals(String otherName) {
        return otherName != null && name.toLowerCase().equals(otherName.toLowerCase());
    }

    public String toString() {
        return this.name;
    }
}
