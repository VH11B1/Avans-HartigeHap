package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.Restaurant;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, String> {
}