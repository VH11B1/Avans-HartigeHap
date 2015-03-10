package edu.avans.hartigehap.domain.planning;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.avans.hartigehap.domain.DomainObject;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Alex on 3-3-2015.
 */
@Entity
@Table(name = "EMPLOYEEROLES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")

//Als klasse omdat JPA een ENUM niet als kolom kan opslaan
public class EmployeeRole extends DomainObject{
    private String roleName;

    public EmployeeRole(final String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString(){
        return this.roleName;
    }

    @Override
    public boolean equals(final Object other){
        try {
            if (this.roleName.equals(((EmployeeRole) other).roleName)) {
                return true;
            } else {
                return false;
            }
        }catch(ClassCastException e){
            // SonarQube will bitch, error logging bla bla
            return false;
        }
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }
}
