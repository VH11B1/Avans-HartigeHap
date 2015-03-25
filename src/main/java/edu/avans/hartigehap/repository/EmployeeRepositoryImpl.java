package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.planning.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;

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
    public Employee findEmployeeByUsername (String username) {
        Query query = em.createNativeQuery("SELECT employeeId FROM users WHERE username = ?");
        query.setParameter(1, username);

        Object result = query.getSingleResult();
        if (result != null) {
            int employeeId = ((BigInteger) result).intValue();
            return employeeRepository.findById(employeeId);
        }
        return null;
    }

    @Override
    public Employee saveEmployeeAndUser(Employee employee) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);

        if(employee.getId() == null){
            employee = employeeRepository.save(employee);

            Query query = em.createNativeQuery("INSERT INTO users (username,password,enabled,employeeId) VALUES (:username,:password,1,:employeeId)");
            query.setParameter("username", employee.getUsername());

            String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());
            query.setParameter("password", encodedPassword);

            query.setParameter("employeeId", employee.getId());
            query.executeUpdate();
        }
        else {
            employee = employeeRepository.save(employee);

            Query query = em.createNativeQuery("UPDATE users SET password = :password WHERE employeeId = :employeeId");

            String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());
            query.setParameter("password", encodedPassword);

            query.setParameter("employeeId", employee.getId());
            query.executeUpdate();
        }
        return employeeRepository.save(employee);
    }
}
