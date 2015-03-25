package edu.avans.hartigehap.web.controller;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.EmptyBillException;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.service.DiningTableService;
import edu.avans.hartigehap.service.RestaurantService;
import edu.avans.hartigehap.web.form.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Locale;

@Controller
public class DiningTableController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiningTableController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DiningTableService diningTableService;

    @RequestMapping(value = "/diningTables/{diningTableId}", method = RequestMethod.GET)
    public String showTable (@PathVariable("diningTableId") String diningTableId, Model uiModel) {
        LOGGER.info("diningTable = " + diningTableId);

        warmupRestaurant(diningTableId, uiModel);

        return "diningtables/show";
    }

    @RequestMapping(value = "/diningTables/{diningTableId}/menuItems", method = RequestMethod.POST)
    public String addMenuItem (@PathVariable("diningTableId") String diningTableId, @RequestParam String menuItemName, Model uiModel) {
        DiningTable diningTable = diningTableService.fetchWarmedUp(Long.valueOf(diningTableId));
        uiModel.addAttribute("diningTable", diningTable);

        diningTableService.addOrderItem(diningTable, menuItemName);

        return "redirect:/diningTables/" + diningTableId;
    }

    @RequestMapping(value = "/diningTables/{diningTableId}/menuItems/{menuItemName}", method = RequestMethod.DELETE)
    public String deleteMenuItem (@PathVariable("diningTableId") String diningTableId, @PathVariable("menuItemName") String menuItemName, Model uiModel) {
        DiningTable diningTable = diningTableService.fetchWarmedUp(Long.valueOf(diningTableId));
        uiModel.addAttribute("diningTable", diningTable);

        diningTableService.deleteOrderItem(diningTable, menuItemName);

        return "redirect:/diningTables/" + diningTableId;
    }

    @RequestMapping(value = "/diningTables/{diningTableId}", method = RequestMethod.PUT)
    public String receiveEvent (@PathVariable("diningTableId") String diningTableId, @RequestParam String event, RedirectAttributes redirectAttributes, Model uiModel, Locale locale) {
        LOGGER.info("(receiveEvent) diningTable = " + diningTableId);


        // because of REST, the "event" parameter is part of the body. It therefore cannot be used for
        // the request mapping so all events for the same resource will be handled by the same
        // controller method; so we end up with an if statement

        switch (event) {
            case "submitOrder":
                return submitOrder(diningTableId, redirectAttributes, uiModel, locale);
            // break unreachable

            case "submitBill":
                return submitBill(diningTableId, redirectAttributes, uiModel, locale);
            // break unreachable

            default:
                warmupRestaurant(diningTableId, uiModel);
                LOGGER.error("internal error: event " + event + "not recognized");
                return "diningtables/show";
        }
    }

    private String submitOrder (String diningTableId, RedirectAttributes redirectAttributes, Model uiModel, Locale locale) {
        DiningTable diningTable = warmupRestaurant(diningTableId, uiModel);
        try {
            diningTableService.submitOrder(diningTable);
        } catch (StateException e) {
            LOGGER.error("StateException", e);
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("message_submit_order_fail", new Object[]{}, locale)));

            // StateException triggers a rollback; consequently all Entities are invalidated by Hibernate
            // So new warmup needed
            diningTable = warmupRestaurant(diningTableId, uiModel);

            return "diningtables/show";
        }
        // store the message temporarily in the session to allow displaying after redirect
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("message_submit_order_success", new Object[]{}, locale)));
        return "redirect:/diningTables/" + diningTableId;

    }

    private String submitBill (String diningTableId, RedirectAttributes redirectAttributes, Model uiModel, Locale locale) {
        DiningTable diningTable = warmupRestaurant(diningTableId, uiModel);
        try {
            diningTableService.submitBill(diningTable);
        } catch (EmptyBillException e) {
            LOGGER.error("EmptyBillException", e);
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("message_submit_empty_bill_fail", new Object[]{}, locale)));
            return "diningtables/show";
        } catch (StateException e) {
            LOGGER.error("StateException", e);
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("message_submit_bill_fail", new Object[]{}, locale)));

            // StateException triggers a rollback; consequently all Entities are invalidated by Hibernate
            // So new warmup needed
            diningTable = warmupRestaurant(diningTableId, uiModel);

            return "diningtables/show";
        }
        // store the message temporarily in the session to allow displaying after redirect
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("message_submit_bill_success", new Object[]{}, locale)));
        return "redirect:/diningTables/" + diningTableId;
    }

    private DiningTable warmupRestaurant (String diningTableId, Model uiModel) {
        Collection<Restaurant> restaurants = restaurantService.findAll();

        uiModel.addAttribute("restaurants", restaurants);
        DiningTable diningTable = diningTableService.fetchWarmedUp(Long.valueOf(diningTableId));
        uiModel.addAttribute("diningTable", diningTable);
        Restaurant restaurant = restaurantService.fetchWarmedUp(diningTable.getRestaurant().getId());
        uiModel.addAttribute("restaurant", restaurant);

        return diningTable;
    }
}
