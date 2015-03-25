package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.DiningTable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DiningTableRepository extends PagingAndSortingRepository<DiningTable, Long> {
}