package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.planning.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Bob on 09/03/15.
 */
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    //
}
