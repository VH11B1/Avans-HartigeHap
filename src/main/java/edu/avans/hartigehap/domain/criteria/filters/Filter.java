package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 */
public abstract class Filter<E> {

    protected final Logger LOGGER = LoggerFactory.getLogger(Filter.class);
    @Getter
    @Setter
    private Filter original;
    @Setter
    private List<Planning> planningList;
    @Setter
    private List<E> filterItems;

    /**
     * Note: will override a previous filter.
     * <p/>
     * For example: if the previous filter filters on kitchen staff,
     * and this one on service staff, all kitchen staff will be filtered out
     *
     * @return a filtered list
     */
    public abstract List<Planning> filter ();

    protected List<E> getFilterItems () {
        if (filterItems == null || filterItems.isEmpty()) {
            LOGGER.info("No filter items specified: " + this.getClass().getCanonicalName());
        }
        return filterItems;
    }

    protected List<Planning> getPlanningList () {
        if (original == null) {
            // if no original, starting list is own list
            return planningList;
        } else {
            // else base starting list on previous filter
            return original.filter();
        }
    }

    public void set (E... l) {
        setFilterItems(Arrays.asList(l));
    }
}
