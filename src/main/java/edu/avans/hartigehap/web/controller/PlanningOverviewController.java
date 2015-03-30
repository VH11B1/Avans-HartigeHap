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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Locale;

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

    // TODO replace with listPlanningsPageable
    @RequestMapping(value = "/plannings", method = RequestMethod.GET)
    public String listPlannings (Model uiModel) {
        Collection<Planning> list = planningOverviewService.getAllPlanningFromNow();

        uiModel.addAttribute("plannings", list);
        uiModel.addAttribute("scope", "all");

        return "plannings/index";
    }

    @RequestMapping(value = "/plannings/list", method = RequestMethod.GET)
    public String listPlanningsPageable (Model uiModel, Integer page, Integer perpage) {
        if(page == null || page < 1){
            LOGGER.warn("No valid page number specified, setting to default.");
            page = 0;
        }
        if(perpage == null){
            LOGGER.warn("No amount per page specified, setting to default.");
            perpage = 10;
        }

        PageRequest request = new PageRequest(page,perpage);

        Page<Planning> list = planningOverviewService.getAllPlanningFromNowPageable(request);

        // note, due to there not being a database for planning items this next part needs
        // to be hardcoded, normally this would be resolved by the repository
        Collection<Planning> allPlanning = planningOverviewService.getAllPlanningFromNow();

        int totalPages = 0;
        if(allPlanning.size() % perpage == 0){
            totalPages = allPlanning.size() / perpage;
        }else{
            totalPages = (allPlanning.size() / perpage) + 1;
        }
        int currentPage = page;
        // end of hardcoded nastiness

        uiModel.addAttribute("plannings", list.getContent());
        uiModel.addAttribute("currentpage", currentPage);
        uiModel.addAttribute("totalpages", totalPages - 1); // 0 based
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
    public String showPlanning (@PathVariable("id") Long id) {
        // TODO
        return null;
    }

    @RequestMapping(value = "/plannings/create", method = RequestMethod.GET)
    public String createPlanning () {
        // TODO
        return null;
    }

    @RequestMapping(value = "/plannings", method = RequestMethod.POST)
    public String storePlanning (
            @Valid Planning planning,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        // TODO
        return null;
    }

    @RequestMapping(value = "/plannings/{id}/edit", method = RequestMethod.GET)
    public String editPlanning (@PathVariable("id") Long id) {
        // TODO
        return null;
    }

    @RequestMapping(value = "/plannings/{id}", method = RequestMethod.PUT)
    public String updatePlanning (
            @PathVariable("id") Long id,
            @Valid Planning planning,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        // TODO
        return null;
    }

    @RequestMapping(value = "/plannings/{id}", method = RequestMethod.DELETE)
    public String destroyPlanning (@PathVariable("id") Long id) {
        // TODO
        return null;
    }

    /*
    @RequestMapping(value = "/currentoverviews", method = RequestMethod.GET)
    public String currentOverview(Model uiModel)
    {
        Collection<Planning> list = planningOverviewService.getCurrentWorking();

        uiModel.addAttribute("plannings", list);
        uiModel.addAttribute("scope", "day");

        return "plannings/index";
    }
    */

    /*
    @RequestMapping(value = "/weekoverviews", method = RequestMethod.GET)
    public String currentWeekOverview(Model uiModel)
    {
        Collection<Planning> list = planningOverviewService.getWeekPlanning();

        uiModel.addAttribute("plannings", list);
        uiModel.addAttribute("scope", "week");

        return "plannings/index";
    }

    @RequestMapping(value = "/fulloverview", method = RequestMethod.GET)
    public String fullOverview(Model uiModel)
    {
        // TODO pagination

        Collection<Planning> list = planningOverviewService.getAllPlanningFromNow();

        uiModel.addAttribute("plannings", list);
        uiModel.addAttribute("scope", "full");

        return "plannings/index";
    }
    */
    @PostConstruct
    public void populatePlanning(){
        planningPopulatorService.populate();
    }
}
