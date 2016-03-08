package exceptions;

/**
 * Created by lupena on 2/8/2016.
 */
public class GenericMessage {

    private boolean error;
    private String message;

    public GenericMessage(String message) {
        this.setMessage(message);
        this.setError(false);
    }

    public String getMessage() {
        return message;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    protected void setError(boolean error) {
        this.error = error;
    }
}
