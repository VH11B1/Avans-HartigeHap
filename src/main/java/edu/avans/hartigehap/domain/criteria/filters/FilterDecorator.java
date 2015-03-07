package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 *
 * decorator pattern
 */
public abstract class FilterDecorator<E> extends Filter<E>{

    // TODO implement error logging in decorators
    protected final Logger LOGGER = LoggerFactory.getLogger(FilterDecorator.class);

    public FilterDecorator(Filter f){
        setPlanningList(f.getPlanningList());
    }

    public FilterDecorator(List<Planning> list){
        setPlanningList(list);
    }
}
