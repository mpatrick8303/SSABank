package org.ssa.ironyard.controller;

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
    
    @RequestMapping(produces = "application/json", value = "accounts/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> accountDetails(@PathVariable int id)
    {
        ResponseEntity.status(HttpStatus.CREATED);
        
        service.
    }
}
