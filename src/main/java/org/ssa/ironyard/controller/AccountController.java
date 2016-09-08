package org.ssa.ironyard.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.model.Account;
import org.ssa.ironyard.service.BankTransactionServicesImpl;

@RestController
@RequestMapping("/ssa-bank/customers{id}/")
public class AccountController {

    @Autowired
    BankTransactionServicesImpl service;
    
    @RequestMapping(produces = "application/json", value ="accounts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Account>> allAccounts(@PathVariable int id)
    {
       ResponseEntity.status(HttpStatus.CREATED);
       
       List<Account> accountList = service.readAccounts(id);
       return ResponseEntity.ok().body(accountList);
    }
    
    @RequestMapping(produces = "application/json", value = "accounts/{accId}/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> accountDetails(@PathVariable int id, @PathVariable int accId)
    {
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().body(service.getAccount(accId));
    }
    
    @RequestMapping(produces = "application/json", value = "accounts/{accId}/withdraw/{amt}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> accountWithdraw(@PathVariable int id, @PathVariable int accId, @PathVariable BigDecimal amt)
    {
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().body(service.Withdrawl(accId, amt));
    }
    
    @RequestMapping(produces = "application/json", value = "accounts/{accId}/deposit/{amt}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> accountDeposit(@PathVariable int id, @PathVariable int accId, @PathVariable BigDecimal amt)
    {
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().body(service.Deposit(accId, amt));
    }
    
    @RequestMapping(produces = "application/json", value = "accounts/{accId}/transfer/{accId2}/{amt}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> accountDeposit(@PathVariable int id, @PathVariable int accId, @PathVariable int accId2, @PathVariable BigDecimal amt)
    {
        ResponseEntity.status(HttpStatus.CREATED);      
        return ResponseEntity.ok().body(service.Transfer(accId, accId2, amt));
    }
    
    
}
