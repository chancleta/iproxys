package models;

import PersistenceData.ConfigurationType;
import org.hibernate.validator.constraints.NotEmpty;
import validator.ValiatableObject;

import javax.validation.constraints.NotNull;

/**
 * Created by lupena on 3/17/2016.
 */
public class Configuration extends ValiatableObject {

    @NotNull
    private ConfigurationType type;

    @NotNull
    @NotEmpty
    private String data;

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public ConfigurationType getType() {
        return this.type;
    }

    public void setType(ConfigurationType type) {
        this.type = type;
    }
}
