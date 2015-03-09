package edu.avans.hartigehap.domain.planning;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.avans.hartigehap.domain.DomainObject;
import edu.avans.hartigehap.domain.Restaurant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 */
@Entity
@Table(name = "EMPLOYEES")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Employee extends DomainObject {

    private String name;
    private String username;
    private String password;
    private String email;
    private byte[] photo;
    private int hoursPerMonth;

    @ManyToOne
    private Restaurant worksAt;

    @ManyToMany
    private List<AvailableSlot> availability;

    @ManyToMany
    private List<EmployeeRole> roles;

    public Employee()
    {
        //
    }

    public Employee(String name, String username, String email, int hoursPerMonth)
    {
        this.name = name;
        this.username = username;
        this.email = email;
        this.hoursPerMonth = hoursPerMonth;
    }

    public void updateEditableFields(Employee employee)
    {
        this.name = employee.name;
        this.username = employee.username;
        this.password = employee.password;
        this.email = employee.email;
        this.photo = employee.photo;
        this.hoursPerMonth = employee.hoursPerMonth;
    }

}
