package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.EmployeeRole;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 * <p/>
 * filters PLANNING by role, not employees or actual slots
 * i.e. if a planning was meant for Kitchen but assigned to Service and actually done by Manager
 * this will only return the planning if filterBy(EmployeeRole... r) contains Kitchen
 */
public class PlannedRoleFilter extends FilterDecorator<EmployeeRole> {

    public PlannedRoleFilter (List<Planning> list, EmployeeRole... roles) {
        super(list, roles);
    }

    public PlannedRoleFilter (Filter f, EmployeeRole... roles) {
        super(f, roles);
        setOriginal(f);
    }

    @Override
    public List<Planning> filter () {
        List<Planning> originalList = getPlanningList();
        List<Planning> filteredList = new ArrayList<Planning>();
        List<EmployeeRole> roles = getFilterItems();

        for (Planning p : originalList) {
            if (roles.contains(p.getRole())) {
                filteredList.add(p);
            }
        }
        return filteredList;
    }
}
