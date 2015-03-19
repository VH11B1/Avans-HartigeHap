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


    public PlannedStartBetweenDatesFilter(List<Planning> list,LocalDateTime... dates){
        super(list,dates);
    }

    public PlannedStartBetweenDatesFilter(Filter f,LocalDateTime... dates){
        super(f,dates);
        setOriginal(f);
    }

    /**
     * Will only use first two dates to compare, any extra will not be considered.
     * If only one date is specified, will use that date and current date.
     *
     * @return
     */
    @Override
    public List<Planning> filter() {
        List<Planning> originalList = getPlanningList();
        List<Planning> filteredList = new ArrayList<>();

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
            // will cause error log on get
            setFilterItems(null);
        }else if(l.length < 2){
            setFilterItems(Arrays.asList(new LocalDateTime[] {l[0], LocalDateTime.now()}));
        }else{
            setFilterItems(Arrays.asList(new LocalDateTime[] {l[0], l[1]}));
        }

    }

    private boolean dateMatch(TimeSlot slot){
        List<LocalDateTime> dates = getFilterItems();
        LocalDateTime startDate = slot.getStart();

        if(startDate.compareTo(dates.get(0)) >= -1 & startDate.compareTo(dates.get(1)) <= -1){
            return true;
        }
        return false;
    }
}
