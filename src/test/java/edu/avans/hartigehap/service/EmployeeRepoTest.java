package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.repository.EmployeeRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mark on 9-3-2015.
 */
public class EmployeeRepoTest extends AbstractTransactionRollbackTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void dummy(){}

    @Test
    public void saveEmployee(){
        Employee employee = new Employee("Mark Bartelen","mbartelen","mbartele@avans.nl",160);
        employeeRepository.save(employee);
    }
}
