package org.ssa.ironyard.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public AccountController(BankAccountServicesImpl aservice2, BankTransactionServicesImpl tservice)
    {
        aService = aservice2;
        service = tservice;
    }

    @RequestMapping(produces = "application/json", value ="/{id}/accounts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<HashMap<String,List<Account>>> allAccounts(@PathVariable int id)
    {
       ResponseEntity.status(HttpStatus.CREATED);
       HashMap<String ,List<Account>> map = new HashMap<String, List<Account>>();
       List<Account> accountList = service.readAccounts(id);
       
       if(accountList.size() == 0)
       {
           map.put("error", accountList);
           return ResponseEntity.ok().header("SSA_Bank Customer", "Account").body(map);
       }
       else
       {
           map.put("success", accountList);
           return ResponseEntity.ok().header("SSA_Bank Customer", "Account").body(map);
       }
       
       
    }
    
    @RequestMapping(produces = "application/json", value = "/{id}/accounts/{accId}/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String,Account>> accountDetails(@PathVariable int id, @PathVariable int accId)
    {
        ResponseEntity.status(HttpStatus.CREATED);   
        
        Map<String, Account> map = new HashMap<>();
        Account a = service.getAccount(accId);
        
        if(a == null)
        {
            map.put("error", a);
            return ResponseEntity.ok().body(map);
        }
        else
        {
            map.put("success", a);
            return ResponseEntity.ok().body(map);
        }
        
    }
    
    @RequestMapping(produces = "application/json", value = "/accounts", method = RequestMethod.POST)//add account
    @ResponseBody
    public ResponseEntity<Map<String,Account>> accountAdd(HttpServletRequest request)
    {
        Map<String,Account> map = new HashMap<>();
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
        if(a == null)
        {
            map.put("error", a);
            return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(map);
        }
        else
        {
            map.put("success", a);
            return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(map);
        }
        
            
        
    }
    
    @RequestMapping(produces = "application/json", value = "{customerID}/accounts/{accId}", method = RequestMethod.PUT)//update account
    @ResponseBody
    public ResponseEntity<Map<String,Account>> accountUpdate(@PathVariable int customerID,@PathVariable int accId, HttpServletRequest request)
    {
        Map<String,Account> map = new HashMap<>();
        int cusID;
        if(customerID != Integer.parseInt(request.getParameter("Customer")))
        {   
            cusID = Integer.parseInt(request.getParameter("Customer"));
        }
        else
            cusID = customerID;
        
        int accountID = accId;
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
        ResponseEntity.status(HttpStatus.CREATED); 
       
        
        
        Account a = new Account(accountID, new Customer(cusID,null,null),type,balance);
        Account aU = aService.updateAccount(a);
        
        if(aU == null)
        {
            map.put("error", aU);
            return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(map);
        }
        else
        {
            map.put("success", aU);
            return ResponseEntity.ok().header("SSA_Bank Customer", "Customer").body(map);
        }
        

    }
    
    @RequestMapping(produces = "application/json", value = "{customerID}/accounts/{accId}/withdraw", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Account> accountWithdraw(@PathVariable int customerID, @PathVariable int accId, HttpServletRequest request )
    {

        BigDecimal amount = new BigDecimal(request.getParameter("withdraw"));
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().header("SSA_Bank Customer", "Account").body(service.Withdrawl(accId, amount));
    }
    
    @RequestMapping(produces = "application/json", value = "{customerID}/accounts/{accId}/deposit", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Account> accountDeposit(@PathVariable int customerID, @PathVariable int accId, HttpServletRequest request)
    {
        BigDecimal amount = new BigDecimal(request.getParameter("deposit"));
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().header("SSA_Bank Customer", "Account").body(service.Deposit(accId, amount));
    }
    
    @RequestMapping(produces = "application/json", value = "{customerID}/accounts/{accId}/transfer", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Account> accountTransfer(@PathVariable int customerID, @PathVariable int accId, HttpServletRequest request)
    {
        int accountIDOne = accId;
        int accountTwo = Integer.parseInt(request.getParameter("accountTwo"));
        BigDecimal amount = new BigDecimal(request.getParameter("transferAmount"));
        
        
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().header("SSA_Bank Customer", "Account").body(service.Transfer(accountIDOne, accountTwo, amount));
    }
    
    @RequestMapping(produces = "application/json", value = "{customerID}/accounts/{accId}/transferFromAccount", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Account> accountTransferFromAccount(@PathVariable int customerID, @PathVariable int accId, HttpServletRequest request)
    {
        int cusID = customerID;
        int accountOne = accId;
        int accountTwo = Integer.parseInt(request.getParameter("accountTwoFA"));
        BigDecimal amount = new BigDecimal(request.getParameter("transferAmountF"));
        
        
        ResponseEntity.status(HttpStatus.CREATED); 
        
        
        return ResponseEntity.ok().header("SSA_Bank Customer", "Account").body(service.Transfer(accountOne, accountTwo, amount));
        
        
    }
    
  
    
    
}
