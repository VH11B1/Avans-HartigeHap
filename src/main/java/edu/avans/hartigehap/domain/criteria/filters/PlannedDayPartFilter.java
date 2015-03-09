package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.TimeSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 */
public class PlannedDayPartFilter extends FilterDecorator<TimeSlot.DayPart> {

    public PlannedDayPartFilter(List<Planning> list,TimeSlot.DayPart... l){
        super(list,l);
    }

    public PlannedDayPartFilter(Filter f,TimeSlot.DayPart... l){
        super(f,l);
        setOriginal(f);
    }

    @Override
    public List<Planning> filter() {
        List<Planning> originalList = getPlanningList();
        List<Planning> filteredList = new ArrayList<Planning>();
        List<TimeSlot.DayPart> parts = getFilterItems();

        for(Planning p : originalList){
            if(parts.contains(p.getPlannedSlot().getPart())){
                filteredList.add(p);
            }
        }

        return filteredList;
    }

}
