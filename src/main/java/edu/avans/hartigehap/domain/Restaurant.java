package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.avans.hartigehap.domain.planning.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Erco
 */
@Entity
@Table(name = "RESTAURANTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"menu", "diningTables", "customers"})
@NoArgsConstructor
public class Restaurant extends DomainObjectNaturalId {
    private static final long serialVersionUID = 1L;

    private String imageFileName;

    // unidirectional one-to-one
    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    private Menu menu = new Menu();

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "restaurant")
    private Collection<DiningTable> diningTables = new ArrayList<DiningTable>();

    @OneToMany(mappedBy = "restaurant")
    private Collection<Employee> employees = new ArrayList<Employee>();

    // no cascading
    @ManyToMany(mappedBy = "restaurants")
    private Collection<Customer> customers = new ArrayList<Customer>();

    public Restaurant (String name, String imageFileName) {
        super(name);
        this.imageFileName = imageFileName;
    }


    // business methods

    public void warmup () {
        Iterator<DiningTable> diningTableIterator = diningTables.iterator();
        while (diningTableIterator.hasNext()) {
            diningTableIterator.next().getId();
        }

        Iterator<MenuItem> mealsIterator = menu.getMeals().iterator();
        while (mealsIterator.hasNext()) {
            MenuItem mi = mealsIterator.next();
            mi.getId();
            Iterator<FoodCategory> fcIterator = mi.getFoodCategories().iterator();
            while (fcIterator.hasNext()) {
                fcIterator.next().getId();
            }
        }

        Iterator<MenuItem> drinksIterator = menu.getDrinks().iterator();
        while (drinksIterator.hasNext()) {
            MenuItem mi = drinksIterator.next();
            mi.getId();
            Iterator<FoodCategory> fcIterator = mi.getFoodCategories().iterator();
            while (fcIterator.hasNext()) {
                fcIterator.next().getId();
            }
        }

        Iterator<FoodCategory> foodCategoryIterator = menu.getFoodCategories().iterator();
        while (foodCategoryIterator.hasNext()) {
            FoodCategory fc = foodCategoryIterator.next();
            Iterator<MenuItem> miIterator = fc.getMenuItems().iterator();
            while (miIterator.hasNext()) {
                miIterator.next().getId();
            }
        }

    }
}
