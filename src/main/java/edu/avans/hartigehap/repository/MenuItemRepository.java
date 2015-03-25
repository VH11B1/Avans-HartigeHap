package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.MenuItem;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MenuItemRepository extends PagingAndSortingRepository<MenuItem, String> {
}