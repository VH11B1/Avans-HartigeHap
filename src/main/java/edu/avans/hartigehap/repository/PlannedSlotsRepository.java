package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.planning.PlannedSlot;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Mark on 11-3-2015.
 */
public interface PlannedSlotsRepository extends PagingAndSortingRepository<PlannedSlot, String> {
    public PlannedSlot findById (long id);
}
