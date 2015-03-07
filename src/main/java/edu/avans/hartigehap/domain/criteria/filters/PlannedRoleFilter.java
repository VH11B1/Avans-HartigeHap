package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.EmployeeRole;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 *
 * filters PLANNING by role, not employees or actual slots
 * i.e. if a planning was meant for Kitchen but assigned to Service and actually done by Manager
 * this will only return the planning if filterBy(EmployeeRole... r) contains Kitchen
 */
public class PlannedRoleFilter extends FilterDecorator<EmployeeRole> {

    private Filter original;
    private List<EmployeeRole> roles;

    public PlannedRoleFilter(List<Planning> list){
        super(list);
    }

    public PlannedRoleFilter(Filter f){
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

        if(roles == null){
            LOGGER.info("No filter specified: " + this.getClass().getCanonicalName());
            return originalList;
        }

        for(Planning p : originalList){

            if(roles.contains(p.getRole())){
                filteredList.add(p);
            }
        }

        return filteredList;
    }

    @Override
    public void set(EmployeeRole... r) {
        roles = Arrays.asList(r);
    }



}
