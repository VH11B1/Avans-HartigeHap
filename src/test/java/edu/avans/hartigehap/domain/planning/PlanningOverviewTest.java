package edu.avans.hartigehap.domain.planning;

import junit.framework.TestCase;
import org.h2.table.Plan;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class PlanningOverviewTest extends TestCase {

    @Test
    public void testGetAllPlannedAndNotPresentEmployees() throws Exception {
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getAllPlannedAndNotPresentEmployees();

        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " was not present and was replaced by "
                    + p.getActualSlot().getActualEmployee().getName());
        }
    }
}