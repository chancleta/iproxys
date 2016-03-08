package exceptions;

import java.io.InputStream;
import java.util.stream.IntStream;

/**
 * Created by lupena on 2/5/2016.
 */
public class InvalidCompanyDataException extends Exception {

    public InvalidCompanyDataException(String message){
        super(message);
    }

}
