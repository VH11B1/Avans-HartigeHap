package edu.avans.hartigehap.domain.planning;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.avans.hartigehap.domain.DomainObject;
import edu.avans.hartigehap.domain.Restaurant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alex on 3-3-2015.
 */
@Entity
@Table(name = "EMPLOYEES")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Employee extends DomainObject {

    @NotBlank(message = "{validation.employee.name.NotBlank.message}")
    @Size(max = 30, message = "{validation.employee.name.Size.message}")
    private String name;

    @NotBlank(message = "{validation.employee.username.NotBlank.message}")
    @Size(min = 4, max = 16, message = "validation.employee.username.Size.message")
    private String username;

    private String password;

    @NotBlank(message = "{validation.employee.email.NotBlank.message}")
    @Email(message = "{validation.employee.email.Email.message}")
    private String email;

    private byte[] photo;

    @Min(value = 0, message = "{validation.employee.hoursPerMonth.Min.message}")
    private int hoursPerMonth;

    @ManyToOne
    private Restaurant restaurant;

    @OneToMany
    private Collection<AvailableSlot> availability = new ArrayList<AvailableSlot>();

    @ManyToMany
    private Collection<EmployeeRole> roles = new ArrayList<EmployeeRole>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private Collection<Planning> plannings = new ArrayList<Planning>();

    public Employee () {
        //
    }

    public Employee (String name, String username, String email, int hoursPerMonth) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.hoursPerMonth = hoursPerMonth;
    }

    public Employee (String name, String username, String password, String email, int hoursPerMonth) {
        this(name, username, email, hoursPerMonth);
        this.password = password;
    }

    public void updateEditableFields (Employee employee) {
        this.name = employee.name;
        this.username = employee.username;
        this.email = employee.email;
        this.photo = employee.photo;
        this.hoursPerMonth = employee.hoursPerMonth;
        this.restaurant = employee.restaurant;

        if (!employee.password.isEmpty()) {
            this.password = employee.password;
        }
    }

}
