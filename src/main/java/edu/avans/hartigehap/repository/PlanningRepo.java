package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.planning.Planning;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Mark on 11-3-2015.
 */
public interface PlanningRepo extends PagingAndSortingRepository<Planning, String> {
}
