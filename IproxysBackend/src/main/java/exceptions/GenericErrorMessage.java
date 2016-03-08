package exceptions;

/**
 * Created by lupena on 2/8/2016.
 */
public class GenericErrorMessage extends GenericMessage{

    public GenericErrorMessage(String message) {
        super(message);
        setError(true);
    }
}


