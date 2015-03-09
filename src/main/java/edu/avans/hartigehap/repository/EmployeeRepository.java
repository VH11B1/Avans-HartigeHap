package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.planning.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Mark on 9-3-2015.
 */
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
}
