package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 */
public abstract class Filter<E> {

    @Getter
    @Setter
    private List<Planning> planningList;


    // TODO move set(E...) to constructor?
    /**
     * Note: will override a previous filter.
     *
     * For example: if the previous filter filters on kitchen staff,
     * and this one on service staff, all kitchen staff will be filtered out
     *
     * @return a filtered list
     */
    public abstract List<Planning> filter();

    public abstract void set(E... l);
}
