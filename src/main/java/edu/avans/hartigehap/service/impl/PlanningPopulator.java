package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.planning.*;
import edu.avans.hartigehap.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Alex on 26-3-2015.
 */
public class PlanningPopulator {
    private static final int AMOUNT_KITCHEN = 20;
    private static final int KITCHEN_PER_DAY_PART = 5;
    private static final int AMOUNT_SERVICE = 52;
    private static final int SERVICE_PER_DAY_PART = 13;
    private static final int AMOUNT_MANAGEMENT = 4;
    private static final int MANAGEMENT_PER_DAY_PART = 1;
    private static final int DAYS_TO_PLAN = 30;

    private static final String[] RESTAURANTS = new String[]{"HartigeHap","HmmmBurger","PittigePannekoek"};
    private static final EmployeeRole[] ROLES = new EmployeeRole[]{new EmployeeRole("KITCHEN"),new EmployeeRole("SERVICE"),new EmployeeRole("MANAGEMENT")};
    private static final char[] CAPS_LETTERS = new char[] { 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static final char[] LETTERS = new char[] { 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    private static final String[] EXTENSIONS = new String[] {".net", ".com", ".nl",".edu",".org",".de",".fr",".gov"};
    private static final TimeSlot.DayPart[] DAY_PARTS = new TimeSlot.DayPart[]{TimeSlot.DayPart.MORNING, TimeSlot.DayPart.AFTERNOON, TimeSlot.DayPart.EVENING, TimeSlot.DayPart.NIGHT};

    private List<Employee> employees = new LinkedList<>();
    private List<Planning> planningList = new LinkedList<>();

    @Autowired
    private RestaurantRepository restaurantRepository;

    public final List<Employee> getEmployeeList(){
        return employees;
    }

    public final List<Planning> generateRandomPlanning(){
        generateEmployees();

        // planning for a month
        int counter = 0;
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        // start of today is now() - current hours - current minutes
        LocalDateTime dateIncrement = LocalDateTime.now().minusHours(hours).minusMinutes(minutes);

        // for every day
        while(counter < DAYS_TO_PLAN){

            // for every day part
            for(TimeSlot.DayPart daypart : DAY_PARTS){

                LocalDateTime start = dateIncrement.plusHours(daypart.getStart());
                LocalDateTime end = dateIncrement.plusHours(daypart.getEnd());

                // for every restaurant
                for(String restaurant : RESTAURANTS){

                    // for kitchen
                    for (int i = 0; i < KITCHEN_PER_DAY_PART; i++) {
                        planAnEmployee(start,end,daypart,ROLES[0],restaurant);
                    }

                    // for service
                    for (int i = 0; i < SERVICE_PER_DAY_PART; i++) {
                        planAnEmployee(start,end,daypart,ROLES[1],restaurant);
                    }

                    // for manager
                    planAnEmployee(start,end,daypart,ROLES[2],restaurant);

                }
            }
            dateIncrement = dateIncrement.plusDays(1);
            counter++;
        }

        return planningList;
    }

    private void planAnEmployee(LocalDateTime startDate, LocalDateTime endDate, TimeSlot.DayPart part, EmployeeRole role, String restaurant){
        Employee toPlan = getRandomEmployeeForRestaurant(role, restaurant, startDate);
        Planning p = new Planning();
        p.setEmployee(toPlan);
        p.setPlannedSlot(new PlannedSlot(part, startDate, endDate));
        p.setRole(role);
        planningList.add(p);
    }

    private boolean isPlannedOnDate(final Employee e, final LocalDateTime date){
        for(Planning p : planningList){
            if(p.getPlannedSlot().getStart().compareTo(date) == 0){
                if(p.getEmployee().equals(e)){
                    return true;
                }
            }
        }
        return false;
    }

    private Employee getRandomEmployeeForRestaurant(final EmployeeRole role, final String restaurant, final LocalDateTime date){
        List<Employee> employeesForRestaurant = new LinkedList<>();

        for (Employee employee : employees) {
            if(employee.getRoles().contains(role) && employee.getEmail().contains(restaurant)){
                employeesForRestaurant.add(employee);
            }
        }
        Employee random = employeesForRestaurant.get(new Random().nextInt(employeesForRestaurant.size()));
        if(isPlannedOnDate(random,date)){
            return getRandomEmployeeForRestaurant(role,restaurant,date);
        }else{
            return random;
        }
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

                //e.setRestaurant(restaurantRepository.findOne(restaurant));
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
            counter = 0;
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

