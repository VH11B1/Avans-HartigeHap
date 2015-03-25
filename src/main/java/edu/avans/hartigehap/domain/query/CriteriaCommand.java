package edu.avans.hartigehap.domain.query;

import edu.avans.hartigehap.domain.planning.Planning;

import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 * <p/>
 * Command pattern
 */
public abstract class CriteriaCommand {
    public abstract List<Planning> fetch (final List<Planning> plannings);
}
