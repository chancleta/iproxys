package exceptions;

/**
 * Created by lupena on 2/5/2016.
 */
public class UnAuthorizedException extends Exception {
    private int code;

    public UnAuthorizedException(String message) {
        super(message);
        this.code = 401;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }
}
