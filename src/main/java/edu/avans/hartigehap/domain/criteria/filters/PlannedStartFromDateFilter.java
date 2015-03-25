package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.TimeSlot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11-3-2015.
 */
public class PlannedStartFromDateFilter extends FilterDecorator<LocalDateTime> {

    public PlannedStartFromDateFilter (List<Planning> list, LocalDateTime... dates) {
        super(list, dates);
    }

    public PlannedStartFromDateFilter (Filter f, LocalDateTime... dates) {
        super(f, dates);
        setOriginal(f);
    }

    @Override
    public List<Planning> filter () {
        List<Planning> originalList = getPlanningList();
        List<Planning> filteredList = new ArrayList<>();

        for (Planning p : originalList) {
            if (dateMatch(p.getPlannedSlot())) {
                filteredList.add(p);
            }
        }

        return filteredList;
    }

    @Override
    public void set(LocalDateTime... slots){
        super.set(slots[0]);
    }

    private boolean dateMatch (TimeSlot slot) {

        LocalDateTime startDate = slot.getStart();

        if (startDate.compareTo(getFilterItems().get(0)) >= -1) {
            return true;
        }
        return false;
    }
}
