package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.TimeSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 */
public class PlannedDayPartFilter extends FilterDecorator<TimeSlot.DayPart> {

    private Filter original;
    private List<TimeSlot.DayPart> parts;

    public PlannedDayPartFilter(List<Planning> list){
        super(list);
    }

    public PlannedDayPartFilter(Filter f){
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
            if(parts.contains(p.getPlannedSlot().getPart())){
                filteredList.add(p);
            }
        }
        return filteredList;
    }

    @Override
    public void set(TimeSlot.DayPart... l) {
        parts = Arrays.asList(l);
    }
}
