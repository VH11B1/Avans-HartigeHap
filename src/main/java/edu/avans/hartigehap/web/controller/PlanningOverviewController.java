package edu.avans.hartigehap.web.controller;

import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.service.PlanningOverviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * Created by Alex on 9-3-2015.
 */
@Controller
public class PlanningOverviewController {
    final Logger logger = LoggerFactory.getLogger(PlanningOverviewController.class);

    @Autowired
    private PlanningOverviewService planningOverviewService;

    @RequestMapping(value = "/currentoverviews", method = RequestMethod.GET)
    public String currentOverview(Model uiModel) {

        Collection<Planning> list = planningOverviewService.getCurrentWorking();

        uiModel.addAttribute("plannings",list);
        uiModel.addAttribute("scope","day");

        return "hartigehap/currentplanningoverview";
    }


    @RequestMapping(value = "/weekoverviews", method = RequestMethod.GET)
    public String currentWeekOverview(Model uiModel) {

        Collection<Planning> list = planningOverviewService.getWeekPlanning();

        uiModel.addAttribute("plannings",list);
        uiModel.addAttribute("scope","week");

        return "hartigehap/currentplanningoverview";
    }

    @RequestMapping(value = "/fulloverview", method = RequestMethod.GET)
    public String fullOverview(Model uiModel) {

        // TODO pagination

        Collection<Planning> list = planningOverviewService.getAllPlanningFromNow();

        uiModel.addAttribute("plannings",list);
        uiModel.addAttribute("scope","full");

        return "hartigehap/currentplanningoverview";
    }
}
