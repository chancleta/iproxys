package validator;

/**
 * Created by lupena on 2/9/2016.
 */
public interface Validatable {
     <G extends ValiatableObject> boolean isValid();
     <G extends ValiatableObject> String getErrorMessage();
}
