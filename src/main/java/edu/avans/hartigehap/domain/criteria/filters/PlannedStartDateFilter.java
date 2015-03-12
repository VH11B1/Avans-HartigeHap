package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.TimeSlot;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Alex on 7-3-2015.
 */
public class PlannedStartDateFilter extends FilterDecorator<LocalDateTime> {

    public PlannedStartDateFilter(final List<Planning> list, final LocalDateTime... dates){
        super(list,dates);
    }

    public PlannedStartDateFilter(final Filter f, final LocalDateTime... dates){
        super(f,dates);
        setOriginal(f);
    }

    @Override
    public List<Planning> filter() {
        List<Planning> originalList = getPlanningList();
        List<Planning> filteredList = new ArrayList<Planning>();

        for(Planning p : originalList){
            if(dateMatch(p.getPlannedSlot())){
                filteredList.add(p);
            }
        }

        return filteredList;
    }

    private boolean dateMatch(final TimeSlot slot){

        LocalDateTime startDate = slot.getStart();

        for (LocalDateTime date : getFilterItems()){
            if(startDate.getDayOfMonth() == date.getDayOfMonth() &
                    startDate.getMonth() == date.getMonth() &
                    startDate.getYear() == date.getYear()){
                return true;
            }
        }
        return false;
    }
}
