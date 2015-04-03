package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.planning.*;
import edu.avans.hartigehap.repository.EmployeeRepository;
import edu.avans.hartigehap.repository.PlannedSlotsRepository;
import edu.avans.hartigehap.repository.PlanningRepository;
import edu.avans.hartigehap.repository.RestaurantRepository;
import edu.avans.hartigehap.service.PlanningPopulatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Alex on 26-3-2015.
 */
@Service("planningPopulatorService")
@Repository
@Transactional
public class PlanningPopulatorServiceImpl implements PlanningPopulatorService{
    private static final Logger LOGGER = LoggerFactory.getLogger
            (PlanningPopulatorServiceImpl.class);

    // amounts and variables
    private static final int AMOUNT_KITCHEN = 15;
    private static final int KITCHEN_PER_DAY_PART = 2;
    private static final int AMOUNT_SERVICE = 40;
    private static final int SERVICE_PER_DAY_PART = 4;
    private static final int AMOUNT_MANAGEMENT = 8;
    private static final int DAYS_TO_PLAN = 15;


    // known values and so on, hardcoded ugliness
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

    private static final int HOURS = 160;

    private List<Employee> employees = new LinkedList<>();
    private List<Planning> planningList = new LinkedList<>();

    // repo's
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private PlanningRepository planningRepository;
    @Autowired
    PlannedSlotsRepository plannedSlotsRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    // for all sorts of nasty workarounds
    @Autowired
    private DataSource dataSource;

    // start populating
    public void populate(){
        LOGGER.info("Populating planning. Please hold, your call is important to us.");
        generateRandomPlanning();
    }

    public final List<Employee> getEmployeeList(){
        return employees;
    }

    public final List<Planning> generateRandomPlanning(){

        // need employees first
        generateEmployees();

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

            // next day
            dateIncrement = dateIncrement.plusDays(1);
            counter++;
        }

        return planningList;
    }

    private void planAnEmployee(LocalDateTime startDate, LocalDateTime endDate, TimeSlot.DayPart part, EmployeeRole role, String restaurant){
        Employee toPlan = getRandomEmployeeForRestaurant(role, restaurant,
                startDate);
        Planning p = new Planning();
        p.setEmployee(toPlan);

        p.setRole(role);


        PlannedSlot slot = new PlannedSlot(part, startDate, endDate);
        p.setPlannedSlot(slot);

        toPlan.getPlannings().add(p);
        planningList.add(p);
        planningRepository.save(p);
    }

    private boolean isPlannedOnDate(final Employee e, final LocalDateTime date){
        for(Planning p : planningList){
            if(p.getPlannedSlot().getStart().compareTo(date) == 0 & p.getEmployee().equals(e)){
                return true;

            }
        }
        return false;
    }

    private Employee getRandomEmployeeForRestaurant(final EmployeeRole role, final String restaurant, final LocalDateTime date){
        List<Employee> employeesForRestaurant = new LinkedList<>();

        for (Employee employee : employees) {
            if (employee.getRoles().contains(role) && employee.getEmail().contains(restaurant)){
                employeesForRestaurant.add(employee);
            }
        }
        if(employeesForRestaurant.isEmpty()){
            return employees.get(0);
        }
        Employee random = employeesForRestaurant.get(new Random().nextInt(employeesForRestaurant.size()));

        if(isPlannedOnDate(random,date)){
            return getRandomEmployeeForRestaurant(role,restaurant,date);
        }else{
            return random;
        }
    }

    // very @#$@# important: transient errors will ensue if @Transactional removed
    @Transactional
    private final List<Employee> generateEmployees() {
        List<Employee> l = new LinkedList<>();
        int counter = 0;


        // ugliest workaround
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (username VARCHAR(45) NOT NULL ,\n" +
                    "  password VARCHAR(255) NOT NULL ,\n" +
                    "  enabled TINYINT NOT NULL DEFAULT 1 ,\n" +
                    "  employeeId BIGINT,\n" +
                    "  PRIMARY KEY (username),\n" +
                    "  CONSTRAINT fk_employee FOREIGN KEY (employeeId) " +
                    "REFERENCES employees(id));";

            stmt.executeUpdate(sql);

            stmt = conn.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS user_roles  (\n" +
                    "  user_role_id INT(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  username VARCHAR(45) NOT NULL,\n" +
                    "  ROLE VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (user_role_id),\n" +
                    "  UNIQUE KEY uni_username_role (ROLE,username),\n" +
                    "  KEY fk_username_idx (username),\n" +
                    "  CONSTRAINT fk_username FOREIGN KEY (username) " +
                    "REFERENCES users (username));";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage(), e);
        }

        if(employeeRepository.findById(1)==null){

                // super god power employees
                Employee mark = new Employee("mark","mark","mbartele@avans.nl",HOURS);
                mark.setPassword("mark");
                mark.setUsername("mark");
                employeeRepository.saveEmployeeAndUser(mark);

                Employee erco = new Employee("erco","erco","erco@test.nl",HOURS);
                erco.setPassword("erco");
                erco.setUsername("erco");
                employeeRepository.saveEmployeeAndUser(erco);
        }

        // per restaurant
        // 20 kitchen => morning, evening, afternoon, night
        // 50 service => morning, evening, afternoon, night
        // 4 manager => morning, evening, afternoon, night
        for (String restaurant : RESTAURANTS) {

            while (counter < AMOUNT_KITCHEN + AMOUNT_SERVICE + AMOUNT_MANAGEMENT) {

                // randomize fields
                String name = getRandomName();
                String userName = getRandomUserName();
                String email = getRandomEmail(userName,restaurant);
                int hoursPerMonth = getRandomHoursPerMonth();

                // enter fields
                Employee e = new Employee(name,userName,email,hoursPerMonth);
                e.setRestaurant(restaurantRepository.findOne(restaurant));

                // sshhtt
                e.setPassword("admin");

                if(counter < AMOUNT_KITCHEN){
                    // if we are a kitchen employee
                    e.setRoles(getRandomRoles(0));
                }else if(counter < AMOUNT_SERVICE){
                    // if we are a service employee
                    e.setRoles(getRandomRoles(1));
                }else{
                    // if we are manager
                    e.setRoles(getRandomRoles(2));
                }

                employees.add(e);
                employeeRepository.save(e);
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

        // first letter of first name
        b.append(CAPS_LETTERS[new Random().nextInt(CAPS_LETTERS.length)]);
        i++;

        // rest of first name
        while(i < 10){
            b.append(LETTERS[new Random().nextInt(LETTERS.length)]);

            // if we have more than 5 letters, random stop
            if(i > 5 & new Random().nextBoolean()){

                // sorry Pascal
                break;
            }
            i++;
        }

        // space between first and last name
        b.append(" ");

        // first letter of last name
        b.append(CAPS_LETTERS[new Random().nextInt(CAPS_LETTERS.length)]);
        i++;

        // rest of last name
        while(i<27){
            b.append(LETTERS[new Random().nextInt(LETTERS.length)]);

            // if we have more than 15 letters, random stop
            if(i > 15 & new Random().nextBoolean()){
                return b.toString();
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

                // if at first you don't succeed, try and try again
                return getRandomUserName();
            }
        }

        return b.toString();
    }

    private final String getRandomEmail(final String userName, final String restaurantName){
        // username@restaurantname.randomextension
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

        // i.e. if we are kitchen, always add kitchen
        roles.add(ROLES[alwaysInclude]);

        // pick a card, any card
        int random = new Random().nextInt(ROLES.length);

        // is this your card?
        if (random != alwaysInclude){

            // well it is now
            roles.add(ROLES[random]);
        }
        return roles;
    }

}

