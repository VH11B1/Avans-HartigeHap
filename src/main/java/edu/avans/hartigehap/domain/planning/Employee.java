package edu.avans.hartigehap.domain.planning;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.avans.hartigehap.domain.DomainObject;
import edu.avans.hartigehap.domain.MenuItem;
import edu.avans.hartigehap.domain.Restaurant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotBlank
    @Size(max=30)
    private String name;

    @NotBlank
    @Size(min=4, max=16)
    private String username;

    private String password;

    @NotBlank
    @Email
    private String email;

    private byte[] photo;

    @Min(0)
    private int hoursPerMonth;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "availableslots")
    private Collection<AvailableSlot> availability = new ArrayList<AvailableSlot>();

    @ManyToMany(mappedBy = "employeeroles")
    private Collection<EmployeeRole> roles = new ArrayList<EmployeeRole>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private Collection<Planning> plannings = new ArrayList<Planning>();

    public Employee() {
        //
    }

    public Employee(String name, String username, String email, int hoursPerMonth) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.hoursPerMonth = hoursPerMonth;
    }

    public void updateEditableFields(Employee employee) {
        this.name = employee.name;
        this.username = employee.username;
        this.email = employee.email;
        this.photo = employee.photo;
        this.hoursPerMonth = employee.hoursPerMonth;
        this.restaurant = employee.restaurant;

        if ( ! employee.password.isEmpty())
            this.password = employee.password;
    }

}
