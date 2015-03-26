package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.domain.planning.EmployeeRole;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex on 26-3-2015.
 */
public class PlanningPopulator {
    private static final int AMOUNT_KITCHEN = 20;
    private static final int AMOUNT_SERVICE = 50;
    private static final int AMOUNT_MANAGEMENT = 4;

    private static final String[] RESTAURANTS = new String[]{"HartigeHap","HmmmBurger","PittigePannekoek"};
    private static final EmployeeRole[] ROLES = new EmployeeRole[]{new EmployeeRole("KITCHEN"),new EmployeeRole("SERVICE"),new EmployeeRole("MANAGEMENT")};
    private static final char[] CAPS_LETTERS = new char[] { 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static final char[] LETTERS = new char[] { 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    private static final String[] EXTENSIONS = new String[] {".net", ".com", ".nl",".edu",".org",".de",".fr",".gov"};

    private List<Employee> employees = new LinkedList<>();

    public final List<Employee> getEmployeeList(){
        generateEmployees();
        return employees;
    }

    public final List<Planning> generateRandomPlanning(){
        List<Planning> list = new LinkedList<>();
        generateEmployees();

        for (Employee employee : employees) {
            //random chance of getting planned
        }

        return list;
    }

    private final List<Employee> generateEmployees() {
        List<Employee> l = new LinkedList<>();
        int counter = 0;
        // per restaurant
        // 20 kitchen => morning, evening, afternoon, night
        // 50 service => morning, evening, afternoon, night
        // 4 manager => morning, evening, afternoon, night
        for (String restaurant : RESTAURANTS) {
            while (counter < AMOUNT_KITCHEN + AMOUNT_SERVICE + AMOUNT_MANAGEMENT) {
                String name = getRandomName();
                String userName = getRandomUserName();
                String email = getRandomEmail(userName,restaurant);
                int hoursPerMonth = getRandomHoursPerMonth();
                Employee e = new Employee(name,userName,email,hoursPerMonth);

                //e.setRestaurant(restaurantService.findById(restaurant));
                if(counter < AMOUNT_KITCHEN){
                    e.setRoles(getRandomRoles(0));
                }else if(counter < AMOUNT_SERVICE){
                    e.setRoles(getRandomRoles(1));
                }else{
                    e.setRoles(getRandomRoles(2));
                }

                employees.add(e);
                counter++;
            }
        }
        return l;
    }

    private final String getRandomName(){
        // max 30 chars => 10 + 19
        StringBuilder b = new StringBuilder();
        int i = 0;
        b.append(CAPS_LETTERS[new Random().nextInt(CAPS_LETTERS.length)]);
        i++;
        while(i < 10){
            b.append(LETTERS[new Random().nextInt(LETTERS.length)]);
            if(i > 5){
                if(new Random().nextBoolean()){
                    break; // sorry Pascal
                }
            }
            i++;
        }
        b.append(" ");
        b.append(CAPS_LETTERS[new Random().nextInt(CAPS_LETTERS.length)]);
        i++; 
        while(i<29){
            b.append(LETTERS[new Random().nextInt(LETTERS.length)]);
            if(i > 15){
                if(new Random().nextBoolean()){
                    return b.toString();
                }
            }
            i++;
        }
        return b.toString();
    }

    private final String getRandomUserName(){
        StringBuilder b = new StringBuilder();
        int i = 0;
        while(i < 6){
            b.append(LETTERS[new Random().nextInt(LETTERS.length)]);
            i++;
        }

        // must be unique
        for (Employee employee : employees) {
            if(employee.getUsername().equals(b.toString())){
                return getRandomUserName();
            }
        }

        return b.toString();
    }

    private final String getRandomEmail(final String userName, final String restaurantName){
        StringBuilder b = new StringBuilder();
        b.append(userName);
        b.append("@");
        b.append(restaurantName);
        b.append(EXTENSIONS[new Random().nextInt(EXTENSIONS.length)]);
        return b.toString();
    }

    private final int getRandomHoursPerMonth(){
        // between 4 and 60
        return new Random().nextInt(56)+4;
    }

    private List<EmployeeRole> getRandomRoles(int alwaysInclude){
        List<EmployeeRole> roles = new ArrayList<>();
        roles.add(ROLES[alwaysInclude]);
        int random = new Random().nextInt(ROLES.length);
        if (random != alwaysInclude){
            roles.add(ROLES[random]);
        }
        return roles;
    }

}

