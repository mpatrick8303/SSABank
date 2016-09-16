package org.ssa.ironyard.controller;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.ssa.ironyard.model.Account;
import org.ssa.ironyard.model.Account.Type;
import org.ssa.ironyard.model.Customer;
import org.ssa.ironyard.service.BankAccountServicesImpl;
import org.ssa.ironyard.service.BankTransactionServicesImpl;

public class AccountControllerTest {

   BankTransactionServicesImpl tservice;
   BankAccountServicesImpl aservice;
   
   AccountController controller;
   MockHttpServletRequest servlet;
   
   Customer cust;
   Account account;
   
   @Before
   public void mock()
   {
       
       this.tservice = EasyMock.createNiceMock(BankTransactionServicesImpl.class);
       this.aservice = EasyMock.createNiceMock(BankAccountServicesImpl.class);
       this.controller = new AccountController(aservice, tservice);
       this.servlet = new MockHttpServletRequest();
       this.cust = new Customer(1,"Joe","Schmoe");
       this.account = new Account(1, cust, Type.CHECKING, new BigDecimal(100.00));
   }
   
   @Test
   public void withdrawal()
   { 
       servlet.addParameter("withdraw", "50.00");
       
       EasyMock.expect(this.tservice.Withdrawl(account.getId(), new BigDecimal("50.00"))).andReturn(account);
       EasyMock.replay(this.tservice);
     
       ResponseEntity<Account> withdraw = this.controller.accountWithdraw(account.getId(), cust.getId(), servlet);
       assertTrue(account.deeplyEquals(withdraw.getBody()));
       
       EasyMock.verify(this.tservice);
   }
   
   @Test
   public void deposit()
   { 
       servlet.addParameter("deposit", "50.00");
       
       EasyMock.expect(this.tservice.Deposit(account.getId(), new BigDecimal("50.00"))).andReturn(account);
       EasyMock.replay(this.tservice);
     
       ResponseEntity<Account> deposit = this.controller.accountDeposit(account.getId(), cust.getId(), servlet);
       assertTrue(account.deeplyEquals(deposit.getBody()));
       
       EasyMock.verify(this.tservice);
   }
   @Test
   public void updateSuccess()
   {
       servlet.addParameter("Customer", cust.getId().toString());
       servlet.addParameter("Type", account.getType().abbrev);
       servlet.addParameter("Balance", account.getBalance().toString());

       EasyMock.expect(this.aservice.updateAccount(account)).andReturn(account);
       EasyMock.replay(this.aservice);
       
       ResponseEntity<Map<String,Account>> update = this.controller.accountUpdate(cust.getId(), account.getId(), servlet);
       
       assertTrue(update.getBody().containsKey("success"));
       assertTrue(update.getBody().get("success").deeplyEquals(account));
   }
   
   @Test
   public void updateError()
   {
       servlet.addParameter("Customer", cust.getId().toString());
       servlet.addParameter("Type", account.getType().abbrev);
       servlet.addParameter("Balance", account.getBalance().toString());
       
       EasyMock.expect(this.aservice.updateAccount(account)).andReturn(null);
       EasyMock.replay(this.aservice);
       
       ResponseEntity<Map<String,Account>> update = this.controller.accountUpdate(cust.getId(), account.getId(), servlet);
       
       assertTrue(update.getBody().containsKey("error"));
       assertTrue(update.getBody().get("error") == null);
   }
 
}