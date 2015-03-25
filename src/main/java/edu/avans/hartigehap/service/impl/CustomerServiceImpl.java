package edu.avans.hartigehap.service.impl;

import com.google.common.collect.Lists;
import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.CustomerRepository;
import edu.avans.hartigehap.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("customerService")
@Repository
@Transactional
public class CustomerServiceImpl implements CustomerService {

    final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<Customer> findAll () {
        return Lists.newArrayList(customerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Customer findById (Long id) {
        return customerRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public Customer findByFirstNameAndLastName (String firstName, String lastName) {
        List<Customer> customers = customerRepository.findByFirstNameAndLastName(firstName, lastName);

        if (!customers.isEmpty())
            return customers.get(0);

        return null;
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomersForRestaurant (Restaurant restaurant) {
        List<Customer> customersForRestaurants = customerRepository.findByRestaurants(
                Arrays.asList(new Restaurant[]{restaurant}), new Sort(Sort.Direction.ASC, "lastName"));

        return customersForRestaurants;
    }

    @Transactional(readOnly = true)
    public Page<Customer> findAllByPage (Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Customer> findCustomersForRestaurantByPage (Restaurant restaurant, Pageable pageable) {
        Page<Customer> customersForRestaurants = customerRepository.
                findByRestaurants(Arrays.asList(new Restaurant[]{restaurant}), pageable);

        return customersForRestaurants;
    }

    public Customer save (Customer customer) {
        return customerRepository.save(customer);
    }

    public void delete (Long id) {
        customerRepository.delete(id);
    }

}

