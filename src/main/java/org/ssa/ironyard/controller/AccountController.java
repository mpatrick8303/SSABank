package org.ssa.ironyard.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.BankStarter;
import org.ssa.ironyard.model.Account;
import org.ssa.ironyard.model.Account.Type;
import org.ssa.ironyard.model.Customer;
import org.ssa.ironyard.service.BankAccountServicesImpl;
import org.ssa.ironyard.service.BankTransactionServicesImpl;

@RestController
@RequestMapping("/ssa-bank/customers")
public class AccountController {

    static final Logger LOGGER = LogManager.getLogger(BankStarter.class);
    
    @Autowired
    BankTransactionServicesImpl service;
    
    @Autowired
    BankAccountServicesImpl aService;
    
    @RequestMapping(produces = "application/json", value ="/{id}/accounts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Account>> allAccounts(@PathVariable int id)
    {
       ResponseEntity.status(HttpStatus.CREATED);
       
       List<Account> accountList = service.readAccounts(id);
       return ResponseEntity.ok().header("SSA_Bank Customer", "Account").body(accountList);
    }
    
    @RequestMapping(produces = "application/json", value = "/{id}/accounts/{accId}/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> accountDetails(@PathVariable int id, @PathVariable int accId)
    {
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().body(service.getAccount(accId));
    }
    
    @RequestMapping(produces = "application/json", value = "/accounts", method = RequestMethod.POST)//add account
    @ResponseBody
    public ResponseEntity<Account> accountAdd(HttpServletRequest request)
    {

        int cusID = Integer.parseInt(request.getParameter("custID"));
        Type type = null;
        if(request.getParameter("Type").toLowerCase().equals("ch") || request.getParameter("Type").toLowerCase().equals("checking"))
        {
            type = Type.CHECKING;
        }
        if(request.getParameter("Type").toLowerCase().equals("sa") || request.getParameter("Type").toLowerCase().equals("savings"))
        {
            type = Type.SAVINGS;
        }
        BigDecimal balance = new BigDecimal(request.getParameter("Balance"));
        

        Account a = aService.insertAccount(new Customer(cusID,null,null),type,balance);
        
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(a);
    }
    
    @RequestMapping(produces = "application/json", value = "{customerID}/accounts/{accId}", method = RequestMethod.PUT)//update account
    @ResponseBody
    public ResponseEntity<Account> accountUpdate(@PathVariable int customerID,@PathVariable int accId, HttpServletRequest request)
    {
        
        
        
        int accountID = accId;
        int cusID = customerID;
        Type type = null;
        if(request.getParameter("Type").toLowerCase().equals("ch") || request.getParameter("Type").toLowerCase().equals("checking"))
        {
            type = Type.CHECKING;
        }
        if(request.getParameter("Type").toLowerCase().equals("sa") || request.getParameter("Type").toLowerCase().equals("savings"))
        {
            type = Type.SAVINGS;
        }
        LOGGER.info("Balance " + request.getParameter("Balance"));

        
        BigDecimal balance = new BigDecimal(request.getParameter("Balance"));
        
        LOGGER.info("accId" + accId);
        LOGGER.info("cusID" + cusID);
        LOGGER.info("Type" +type);
        LOGGER.info("Balance" + balance);
        
        
        Account a = new Account(accountID, new Customer(cusID,null,null),type,balance);
        aService.updateAccount(a);
        
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().header("SSA_Bank Customer", "Account").body(a);
    }
    
    @RequestMapping(produces = "application/json", value = "/accounts/{accId}/withdraw/{amt}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> accountWithdraw(@PathVariable int id, @PathVariable int accId, @PathVariable BigDecimal amt)
    {
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().body(service.Withdrawl(accId, amt));
    }
    
    @RequestMapping(produces = "application/json", value = "/accounts/{accId}/deposit/{amt}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> accountDeposit(@PathVariable int id, @PathVariable int accId, @PathVariable BigDecimal amt)
    {
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().body(service.Deposit(accId, amt));
    }
    
    @RequestMapping(produces = "application/json", value = "/accounts/{accId}/transfer/{accId2}/{amt}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> accountTransfer(@PathVariable int id, @PathVariable int accId, @PathVariable int accId2, @PathVariable BigDecimal amt)
    {
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().body(service.Transfer(accId, accId2, amt));
    }
    
  
    
    
}
