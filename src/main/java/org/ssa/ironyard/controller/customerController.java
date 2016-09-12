package org.ssa.ironyard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ssa.ironyard.BankStarter;
import org.ssa.ironyard.model.Customer;
import org.ssa.ironyard.service.BankAccountServicesImpl;
import org.ssa.ironyard.service.BankTransactionServicesImpl;

@Controller
@RequestMapping("/ssa-bank")
public class CustomerController
{
    
    @Autowired
    BankTransactionServicesImpl service;
    
    @Autowired
    BankAccountServicesImpl aService;
    
    static final Logger LOGGER = LogManager.getLogger(BankStarter.class);
    
    @RequestMapping(value = "")
    public String home()
    {
        return "SSABank.html";
        

        
    }
    
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Customer>> allCustomers()
    {
        
        List<Customer> customers = service.readCustomers();
        
        return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(customers);
        
    }
    
    @RequestMapping(value = "/customers/{customerID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Customer> customer(@PathVariable String customerID)
    {
        int id = Integer.parseInt(customerID);
        Customer cus = service.readCustomer(id);
        
        return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(cus);
        
    }
    
    @RequestMapping(value = "/customers", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<Customer> addCustomer(HttpServletRequest request)
    {
        String fName = request.getParameter("firstName");
        String lName = request.getParameter("lastName");

        Customer c = aService.insertCustomer(fName, lName);
        return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(c);
        
    }
    

    
    
}
