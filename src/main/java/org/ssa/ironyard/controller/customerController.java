package org.ssa.ironyard.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.BankStarter;
import org.ssa.ironyard.model.Customer;
import org.ssa.ironyard.service.BankTransactionServicesImpl;

@RestController
@RequestMapping("/ssa-bank")
public class customerController
{
    @Autowired
    BankTransactionServicesImpl service;
    
    static final Logger LOGGER = LogManager.getLogger(BankStarter.class);
    
    @RequestMapping("/customers")
    @ResponseBody
    public List<Customer> customers ()
    {
        
        return service.readCustomers();
    }
}
