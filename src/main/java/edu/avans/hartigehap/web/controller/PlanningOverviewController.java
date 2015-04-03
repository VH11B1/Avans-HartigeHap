package edu.avans.hartigehap.web.controller;

import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.service.PlanningOverviewService;
import edu.avans.hartigehap.service.PlanningPopulatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by Alex on 9-3-2015.
 */
@Controller
public class PlanningOverviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanningOverviewController.class);

    @Autowired
    private PlanningOverviewService planningOverviewService;

    @Autowired
    private PlanningPopulatorService planningPopulatorService;

    @RequestMapping(value = "/plannings", method = RequestMethod.GET)
    public String listPlanningsPageable (Model uiModel, Integer page, Integer perpage) {
        if(page == null || page < 1){
            LOGGER.warn("No valid page number specified, setting to default.");
            page = 0;
        }
        if(perpage == null || perpage < 1){
            LOGGER.warn("No valid amount per page specified, setting to default.");
            perpage = 10;
        }

        Page<Planning> list = planningOverviewService.getAllPlanningFromNowPageable(new PageRequest(page,perpage));

        uiModel.addAttribute("plannings", list.getContent());
        uiModel.addAttribute("currentpage", list.getNumber());
        uiModel.addAttribute("totalpages", list.getTotalPages());
        uiModel.addAttribute("scope", "all");
        return "plannings/pageable";
    }

    @RequestMapping(value = "/plannings/daily", method = RequestMethod.GET)
    public String showDailyPlanning (Model uiModel) {
        Collection<Planning> list = planningOverviewService.getCurrentWorking();

        uiModel.addAttribute("plannings", list);
        uiModel.addAttribute("scope", "day");

        return "plannings/index";
    }

    @RequestMapping(value = "/plannings/weekly", method = RequestMethod.GET)
    public String showWeeklyPlanning (Model uiModel) {
        Collection<Planning> list = planningOverviewService.getWeekPlanning();

        uiModel.addAttribute("plannings", list);
        uiModel.addAttribute("scope", "week");

        return "plannings/index";
    }

    @RequestMapping(value = "/plannings/{id}", method = RequestMethod.GET)
    public String showPlanning (
    //        @PathVariable("id") Long id
    ) {
        // TODO
        LOGGER.info("Unimplemented method");
        return null;
    }

    @RequestMapping(value = "/plannings/create", method = RequestMethod.GET)
    public String createPlanning () {
        // TODO
        LOGGER.info("Unimplemented method");
        return null;
    }

    @RequestMapping(value = "/plannings", method = RequestMethod.POST)
    public String storePlanning (
//            @Valid Planning planning,
//            BindingResult bindingResult,
//            Model model,
//            HttpServletRequest httpServletRequest,
//            RedirectAttributes redirectAttributes,
//            Locale locale
    ) {
        // TODO
        LOGGER.info("Unimplemented method");
        return null;
    }

    @RequestMapping(value = "/plannings/{id}/edit", method = RequestMethod.GET)
    public String editPlanning (
    //        @PathVariable("id") Long id
    ) {
        // TODO
        LOGGER.info("Unimplemented method");
        return null;
    }

    @RequestMapping(value = "/plannings/{id}", method = RequestMethod.PUT)
    public String updatePlanning (
//            @PathVariable("id") Long id,
//            @Valid Planning planning,
//            BindingResult bindingResult,
//            Model model,
//            HttpServletRequest httpServletRequest,
//            RedirectAttributes redirectAttributes,
//            Locale locale
    ) {
        // TODO
        LOGGER.info("Unimplemented method");
        return null;
    }

    @RequestMapping(value = "/plannings/{id}", method = RequestMethod.DELETE)
    public String destroyPlanning (
//            @PathVariable("id") Long id
    ) {
        // TODO
        LOGGER.info("Unimplemented method");
        return null;
    }

    @PostConstruct
    public void populatePlanning(){
        //planningPopulatorService.populate();
    }
}
