package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;

import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 *
 * decorator pattern
 */
public abstract class FilterDecorator<E> extends Filter<E>{

    public FilterDecorator(final Filter f, final E... items){
        setPlanningList(f.getPlanningList());
        set(items);
    }

    public FilterDecorator(final List<Planning> list, final E... items){
        setPlanningList(list);
        set(items);
    }
}
