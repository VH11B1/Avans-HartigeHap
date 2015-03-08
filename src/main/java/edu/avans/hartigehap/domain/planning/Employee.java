package edu.avans.hartigehap.domain.planning;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 */
@Getter
@Setter
public class Employee {
    private String name;
    private String username;
    private String password;
    private String email;
    private byte[] photo;
    private int hoursPerMonth;

    private List<AvailableSlot> availibility;
    private List<EmployeeRole> roles;
}
