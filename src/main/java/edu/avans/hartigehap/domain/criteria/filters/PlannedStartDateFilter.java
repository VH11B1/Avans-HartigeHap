package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.TimeSlot;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Alex on 7-3-2015.
 */
public class PlannedStartDateFilter extends FilterDecorator<LocalDateTime> {
    private Filter original;
    private List<LocalDateTime> dates;

    public PlannedStartDateFilter(List<Planning> list){
        super(list);
    }

    public PlannedStartDateFilter(Filter f){
        super(f);
        this.original = f;
    }

    @Override
    public List<Planning> filter() {
        List<Planning> originalList;

        if(original == null){
            // if no original, starting list is own list
            originalList = getPlanningList();
        }else{
            // else base starting list on previous filter
            originalList = original.filter();
        }
        List<Planning> filteredList = new ArrayList<Planning>();

        for(Planning p : originalList){
            if(dateMatch(p.getPlannedSlot())){
                filteredList.add(p);
            }
        }

        return filteredList;
    }

    @Override
    public void set(LocalDateTime... l) {
        this.dates = Arrays.asList(l);
    }

    private boolean dateMatch(TimeSlot slot){

        LocalDateTime startDate = slot.getStart();

        for (LocalDateTime date : dates){
            if(startDate.getDayOfMonth() == date.getDayOfMonth() &
                    startDate.getMonth() == date.getMonth() &
                    startDate.getYear() == date.getYear()){
                return true;
            }
        }
        return false;
    }
}
