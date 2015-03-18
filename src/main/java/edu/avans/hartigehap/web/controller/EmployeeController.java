package edu.avans.hartigehap.web.controller;

import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.service.EmployeeService;
import edu.avans.hartigehap.service.RestaurantService;
import edu.avans.hartigehap.web.controller.pe.RestaurantEditor;
import edu.avans.hartigehap.web.form.Message;
import edu.avans.hartigehap.web.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
public class EmployeeController {

    final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RestaurantService restaurantService;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Restaurant.class, new RestaurantEditor(this.restaurantService));
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String listEmployees(Model model)
    {
        model.addAttribute("employees", employeeService.findAll());

        logger.info("Listing employees");

        return "employees/index";
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public String showEmployee(@PathVariable("id") Long id, Model model)
    {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);

        logger.info("Showing employee: '" + employee.getName() + "'");

        return "employees/show";
    }

    @RequestMapping(value = "/employees/{id}/edit", method = RequestMethod.GET)
    public String editEmployee(@PathVariable("id") Long id, Model model)
    {
        Employee employee = employeeService.findById(id);
        List<Restaurant> restaurants = restaurantService.findAll();

        model.addAttribute("employee", employee);
        model.addAttribute("restaurants", restaurants);

        logger.info("Editing employee: '" + employee.getName() + "'");

        return "employees/edit";
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.PUT)
    public String updateEmployee(
            @PathVariable("id") Long id,
            @Valid Employee employee,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Locale locale
    )
    {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("message", new Message("error", messageSource.getMessage(
                    "employee_save_fail", new Object[]{}, locale)));

            model.addAttribute("employee", employee);

            return "employees/edit";
        }

        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage(
                "employee_save_success", new Object[] {}, locale));

        Employee existingEmployee = employeeService.findById(employee.getId());
        existingEmployee.updateEditableFields(employee);
        employeeService.save(existingEmployee);

        logger.info("Updated employee: '" + existingEmployee.getName() + "'");

        return "redirect:/employees/" + employee.getId() + "/edit";
    }

    @RequestMapping(value = "/employees/create", method = RequestMethod.GET)
    public String createEmployee(Model model)
    {
        model.addAttribute("employee", new Employee());
        model.addAttribute("restaurants", restaurantService.findAll());

        logger.info("Creating a new employee");

        return "employees/create";
    }

    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public String storeEmployee(
            @Valid Employee employee,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Locale locale
    )
    {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("message", new Message("error", messageSource.getMessage(
                    "employee_create_fail", new Object[] {}, locale)));

            model.addAttribute("employee", employee);

            return "employees/create";
        }

        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage(
                "employee_create_success", new Object[] {}, locale));

        Employee storedEmployee = employeeService.save(employee);

        logger.info("New employee created: '" + storedEmployee.getName() + "'");

        return "redirect:/employees/" + UrlUtil.encodeUrlPathSegment(
                storedEmployee.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
    public String destroyEmployee(@PathVariable("id") Long id)
    {
        employeeService.delete(id);

        logger.info("Deleted employee");

        return "redirect:/employees";
    }

}