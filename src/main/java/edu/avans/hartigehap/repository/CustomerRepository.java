package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

	List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

	List<Customer> findByRestaurants(
			Collection<Restaurant> restaurants, 
			Sort sort);

	Page<Customer> findByRestaurants(
			Collection<Restaurant> restaurants, 
			Pageable pageable);
}
