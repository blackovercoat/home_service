package com.dolphine.my_services.controller;

import com.dolphine.my_services.dto.CatalogForm;
import com.dolphine.my_services.dto.Customer;
import com.dolphine.my_services.dto.CustomerForm;
import com.dolphine.my_services.model.CustomerEntity;
import com.dolphine.my_services.service.common.CommonService;
import com.dolphine.my_services.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * Created by PC on 4/30/2017.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    final private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/list")
    public String customers(Model model){
        model.addAttribute("listCustomer",customerService.getAllCustomer());
        return "customer/customer_list";
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProvider(@RequestBody Customer customer) {
        int id = customer.getId();
        customerService.removeCustomerById(id);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/edit/{customerId}",method = RequestMethod.GET)
    public String editProvider(@PathVariable("customerId") int customerId, Model model){

        CustomerEntity customerEntity = customerService.getCustomerById(customerId);
        model.addAttribute("customerForm", new CustomerForm());
        model.addAttribute("customer",customerEntity);
        return "customer/edit_customer";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateProvider(@ModelAttribute("customerForm") CustomerForm customerForm
            , BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "redirect:/customer/edit/"+customerForm.getId();
        }
        customerService.setCustomerById(customerForm,customerForm.getId());
        return "redirect:/customer/list";
    }
}
