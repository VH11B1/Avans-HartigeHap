package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.repository.EmployeeRepository;
import edu.avans.hartigehap.repository.EmployeeRepositoryImpl;
import edu.avans.hartigehap.repository.OrderRepositoryImpl;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Mark on 9-3-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "file:D:\\Users\\Mark\\IdeaProjects\\hhbuk\\src\\main\\webapp\\WEB-INF\\spring\\root-context.xml"})
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

    @Transactional
    @Test
    public void getByUsername(){
        EmployeeRepositoryImpl empRepo = new EmployeeRepositoryImpl();

        Employee employee = empRepo.findEmployeeByUsername("mark");
        assertNotNull(employee);
    }

}
