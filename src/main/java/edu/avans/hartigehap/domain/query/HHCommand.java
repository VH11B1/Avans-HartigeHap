package edu.avans.hartigehap.domain.query;

import edu.avans.hartigehap.domain.planning.Planning;

import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 *
 * Command pattern
 */
public abstract class HHCommand {
    public abstract List<Planning> filter(final List<Planning> plannings);
}
