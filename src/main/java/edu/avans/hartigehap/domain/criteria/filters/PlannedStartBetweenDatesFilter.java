package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.TimeSlot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 */
public class PlannedStartBetweenDatesFilter extends FilterDecorator<LocalDateTime> {

    private Filter original;
    private List<LocalDateTime> dates;

    public PlannedStartBetweenDatesFilter(List<Planning> list){
        super(list);
    }

    public PlannedStartBetweenDatesFilter(Filter f){
        super(f);
        this.original = f;
    }

    /**
     * Will only use first two dates to compare, any extra will not be considered.
     * If only one date is specified, will use that date and current date.
     *
     * @return
     */
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
        if(l == null || l.length < 1){
            this.dates = null;
        }else if(l.length < 2){
            this.dates = Arrays.asList(new LocalDateTime[] {l[0], LocalDateTime.now()});
        }else{
            this.dates = Arrays.asList(new LocalDateTime[] {l[0], l[1]});
        }

    }

    private boolean dateMatch(TimeSlot slot){

        LocalDateTime startDate = slot.getStart();
        if(startDate.compareTo(dates.get(0)) > -1 & startDate.compareTo(dates.get(1)) < -1){
            return true;
        }
        return false;
    }
}
