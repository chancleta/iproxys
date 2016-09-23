package models;

import validator.ValiatableObject;

import javax.validation.constraints.NotNull;

public class JsonConfig  extends ValiatableObject {

    @NotNull
    private Bandwidth bandwidth;

    public Bandwidth getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Bandwidth bandwidth) {
        this.bandwidth = bandwidth;
    }


}
