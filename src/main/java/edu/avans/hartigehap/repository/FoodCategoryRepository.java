package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.FoodCategory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FoodCategoryRepository extends PagingAndSortingRepository<FoodCategory, String> {
}
