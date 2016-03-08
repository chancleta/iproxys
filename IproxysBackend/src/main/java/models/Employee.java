package models;

/**
 * Created by lupena on 2/5/2016.
 */

import org.hibernate.validator.constraints.NotEmpty;
import validator.ValiatableObject;

import javax.validation.constraints.NotNull;

/**
 * A Pojo representing the Employee
 */

public class Employee extends ValiatableObject {

    @NotNull
    @javax.validation.constraints.Min(value = 1)
    private Long EmployeeID;

    @NotNull
    @NotEmpty
    private String name;

    public Long getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(Long employeeID) {
        EmployeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
