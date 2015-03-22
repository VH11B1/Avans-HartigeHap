package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by Mark on 9-3-2015.
 */
public class EmployeeRepoTest extends AbstractTransactionRollbackTest {

    private static final String EMPLOYEE_NAME = "Mark Bartelen";
    private static final String EMPLOYEE_USERNAME = "mbartele";
    private static final String EMPLOYEE_PASSWORD = "@passww123";
    private static final String EMPLOYEE_EMAIL = "mbartele@avans.nl";
    private static final int EMPLOYEE_WORKHOURS = 16;

    private static final String EMPLOYEE_NAME_2 = "wiewiewiew";
    private static final String EMPLOYEE_USERNAME_2 = "wiew";
    private static final String EMPLOYEE_PASSWORD_2 = "@passww123";
    private static final String EMPLOYEE_EMAIL_2 = "wiewiewiew@avans.nl";
    private static final int EMPLOYEE_WORKHOURS_2 = 18;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void findEmployeeByUsername()
    {
        // Prepare our employee
        Employee employee = prepareEmployee(
                EMPLOYEE_NAME, EMPLOYEE_USERNAME, EMPLOYEE_PASSWORD, EMPLOYEE_EMAIL, EMPLOYEE_WORKHOURS
        );

        // Find the employee
        Employee foundEmployee = employeeService.findByUsername(EMPLOYEE_USERNAME);

        // Assert the test
        assertNotNull("Found an employee with given username", foundEmployee);
        assertSame("Found the employee with the given username", foundEmployee, employee);
    }

    @Test
    public void createEmployee()
    {
        // Prepare our employee
        Employee employee = prepareEmployee(
                EMPLOYEE_NAME, EMPLOYEE_USERNAME, EMPLOYEE_PASSWORD, EMPLOYEE_EMAIL, EMPLOYEE_WORKHOURS
        );

        // Retrieve all our employees
        Employee createdEmployee = employeeService.findById(employee.getId());

        // Assert the test
        assertNotNull("The employee was not null", createdEmployee);
        assertSame("The employee was created", createdEmployee, employee);
    }

    @Test
    public void saveEmployee()
    {
        // Prepare our employee
        Employee employee = prepareEmployee(
                EMPLOYEE_NAME, EMPLOYEE_USERNAME, EMPLOYEE_PASSWORD, EMPLOYEE_EMAIL, EMPLOYEE_WORKHOURS
        );

        // Change properties of existing employee
        employee.setName(EMPLOYEE_NAME_2);
        employee.setUsername(EMPLOYEE_USERNAME_2);
        employee.setPassword(EMPLOYEE_PASSWORD_2);
        employee.setEmail(EMPLOYEE_EMAIL_2);
        employee.setHoursPerMonth(EMPLOYEE_WORKHOURS_2);

        // Save the changes
        employeeService.save(employee);

        // Assert the test
        assertTrue(employee.getName().equals(EMPLOYEE_NAME_2));
        assertTrue(employee.getUsername().equals(EMPLOYEE_USERNAME_2));
        assertTrue(employee.getPassword().equals(EMPLOYEE_PASSWORD_2));
        assertTrue(employee.getEmail().equals(EMPLOYEE_EMAIL_2));
        assertTrue(employee.getHoursPerMonth() == EMPLOYEE_WORKHOURS_2);
    }

    @Test
    public void destroyEmployee()
    {
        // Prepare our employee
        Employee employee = prepareEmployee(
                EMPLOYEE_NAME, EMPLOYEE_USERNAME, EMPLOYEE_PASSWORD, EMPLOYEE_EMAIL, EMPLOYEE_WORKHOURS
        );

        // Delete the employee
        employeeService.delete(employee.getId());
        Employee foundEmployee = employeeService.findById(employee.getId());

        // Assert the test
        assertNull("The employee has been deleted", foundEmployee);
    }

    private Employee prepareEmployee(String name, String username, String password, String email, int hoursPerMonth)
    {
        Employee employee = new Employee(
                name, username, password, email, hoursPerMonth);

        Employee savedEmployee = employeeService.save(employee);

        return savedEmployee;
    }

}
