package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10-3-2015.
 */
public class PlannedEmployeeFilter extends FilterDecorator<Employee>{

    public PlannedEmployeeFilter(List<Planning> list, Employee... employees){
        super(list, employees);
    }

    public PlannedEmployeeFilter(Filter f, Employee... employees){
        super(f, employees);
        setOriginal(f);
    }

    @Override
    public List<Planning> filter() {
        List<Planning> originalList = getPlanningList();
        List<Planning> filteredList = new ArrayList<Planning>();

        for(Planning p : originalList){
            if(employeeInList(p.getEmployee())){
                filteredList.add(p);
            }
        }

        return filteredList;
    }

    private boolean employeeInList(Employee e){
        if (getFilterItems().contains(e)){
            return true;
        }else{
            return false;
        }
    }
}
