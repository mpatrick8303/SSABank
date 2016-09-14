package org.ssa.ironyard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> customer(@PathVariable String customerID)
    {
        Map<String,Object> map = new HashMap<>();
        int id = Integer.parseInt(customerID);
        Customer cus = service.readCustomer(id);
        
        if(cus == null)
        {
            map.put("error", new Customer());
            return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(map);
            
        }
        else
        {
            map.put("success", cus);
            return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(map);
        }
            
        
    }
    
    @RequestMapping(value = "/customers", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<Customer> addCustomer(HttpServletRequest request)
    {
        LOGGER.info("hello");
        String fName = request.getParameter("firstName");
        String lName = request.getParameter("lastName");

        Customer c = aService.insertCustomer(fName, lName);
        return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(c);
        
    }
    
    @RequestMapping(value = "/customers/{customerID}", method = RequestMethod.PUT )
    @ResponseBody
    public ResponseEntity<Customer> updateCustomer(@PathVariable int customerID, HttpServletRequest request)
    {
        LOGGER.info("hello");
        int cusID = customerID;
        String fName = request.getParameter("firstName");
        String lName = request.getParameter("lastName");

        Customer c = new Customer(cusID, fName,lName);
        aService.updateCustomer(c);
        return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(c);
        
    }
    

    
    
}
