package validator;

import javax.validation.Validation;

/**
 * Created by lupena on 2/5/2016.
 */
public class ValiatableObject implements Validatable {

   public  <G extends ValiatableObject> boolean isValid() {
      return Validation.buildDefaultValidatorFactory().getValidator().validate((G)this).size() == 0;
   }

   public  <G extends ValiatableObject> String getErrorMessage() {
      final String[] validationError = {""};
      Validation.buildDefaultValidatorFactory().getValidator().validate((G)this).iterator().forEachRemaining(o -> validationError[0] += " " + o.getPropertyPath() +": " + o.getMessage() );
      return validationError[0].trim();
   }
}
