package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.planning.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Mark on 10-3-2015.
 */
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EmployeeRepository employeeRepository;

    // this is a custom method for which Spring cannot create an implementation
    // so we need to make our own repository implementation!
    public Employee findEmployeeByUsername(String username) {
        Query query = em.createNativeQuery("SELECT employeeId FROM users WHERE username = ?");
        query.setParameter(1, username);
        int employeeId = ((BigInteger)query.getSingleResult()).intValue();
        return employeeRepository.findById(employeeId);
    }
}
