package org.ssa.ironyard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.BankStarter;
import org.ssa.ironyard.model.Customer;
import org.ssa.ironyard.service.BankTransactionServicesImpl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ssa-bank")
public class customerController
{
    
    @Autowired
    BankTransactionServicesImpl service;
    
    static final Logger LOGGER = LogManager.getLogger(BankStarter.class);
    
    @RequestMapping("/customers")
    @ResponseBody
    public ResponseEntity<List<String>> customers()
    {
        List<Customer> customers = service.readCustomers();
        List<String> customerStrings = new ArrayList<>();
        
        for (int i = 0; i < customers.size(); i++)
        {
            customerStrings.add(convertCustomerJson(customers.get(i)));
        }
        return ResponseEntity.ok().header("SSA-Bank Customer", "List of Customers").body(customerStrings);
    }
    
    @RequestMapping(value = "/customers/{customerID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> customer(@PathVariable String customerID)
    {
        int id = Integer.parseInt(customerID);
        String customer = convertCustomerJson(service.readCustomer(id));
        
        return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(customer);
        
    }
    
    @RequestMapping( "/customers/cus")
    @ResponseBody
    public ResponseEntity<String> customer2(HttpServletRequest customerID)
    {
        String cID = customerID.getParameter("id");
        int id = Integer.parseInt(cID);
        String customer = convertCustomerJson(service.readCustomer(id));
        
        return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(customer);
        
    }
    
    private String convertCustomerJson(Customer customers) 
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        String jsonInString2 = null;
        
        try {
            jsonInString = mapper.writeValueAsString(customers);
    
            jsonInString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customers);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonInString;
        
    }
}
