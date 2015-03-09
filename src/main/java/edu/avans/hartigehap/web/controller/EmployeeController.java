package edu.avans.hartigehap.web.controller;

import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.service.EmployeeService;
import edu.avans.hartigehap.service.RestaurantService;
import edu.avans.hartigehap.web.form.Message;
import edu.avans.hartigehap.web.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by Bob on 09/03/15.
 */

@Controller
@PreAuthorize("hasRole('MANAGEMENT')")
public class EmployeeController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String listEmployees(Model model)
    {
        model.addAttribute("employees", employeeService.findAll());

        return "hartigehap/listemployees";
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public String showEmployee(@PathVariable("id") Long id, Model model)
    {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);

        return "hartigehap/showemployee";
    }

    @RequestMapping(value = "/employees/{id}/edit", method = RequestMethod.GET)
    public String editCustomer(@PathVariable("id") Long id, Model model)
    {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);

        return "hartigehap/editemployee";
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
                    "employee_save_fail", new Object[] {}, locale)));

            model.addAttribute("employee", employee);

            return "hartigehap/editemployee";
        }

        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage(
                "employee_save_success", new Object[] {}, locale));

        Employee existingEmployee = employeeService.findById(employee.getId());
        existingEmployee.updateEditableFields(employee);
        employeeService.save(existingEmployee);

        return "redirect:/employees/" + UrlUtil.encodeUrlPathSegment(
                employee.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/employees/create", method = RequestMethod.GET)
    public String createEmployee(Model model)
    {
        model.addAttribute("employee", new Employee());

        return "hartigehap/createemployee";
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

            return "hartigehap/createemployee";
        }

        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage(
                "employee_create_success", new Object[] {}, locale));

        Employee storedEmployee = employeeService.save(employee);

        return "redirect:/employees/" + UrlUtil.encodeUrlPathSegment(
                storedEmployee.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
    public String destroyEmployee(@PathVariable("id") Long id)
    {
        employeeService.delete(id);

        return "redirect:/employees";
    }

}
