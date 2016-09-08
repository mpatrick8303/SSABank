package org.ssa.ironyard.service;

import java.math.BigDecimal;
import java.util.List;

import org.ssa.ironyard.dao.AccountDAOImpl;
import org.ssa.ironyard.dao.CustomerDAOImpl;
import org.ssa.ironyard.model.Account;
import org.ssa.ironyard.model.Customer;

public class BankTransactionServicesImpl implements BankTransactionServices
{
    
    AccountDAOImpl accounts;
    CustomerDAOImpl customers;
    
    public BankTransactionServicesImpl(AccountDAOImpl accounts,CustomerDAOImpl customers)
    {
        
        this.accounts = accounts;
        this.customers = customers;
        
    }
 
    @Override
    public Account Withdrawl(int account, BigDecimal amount)
    {
        Account a = accounts.read(account);
        
        if(a == null)
            return new Account();
        
        BigDecimal wBalance = a.getBalance().add(amount);
        
        Account wA = new Account(a.getId(),a.getCustomer(),a.getType(),wBalance);
        return accounts.update(wA);
    }

    @Override
    public Account Deposit(int account, BigDecimal amount)
    {
        Account a = accounts.read(account);
        
        if(a == null)
            return new Account();
        
        BigDecimal wBalance = a.getBalance().subtract(amount);
        
        Account wA = new Account(a.getId(),a.getCustomer(),a.getType(),wBalance);
        return accounts.update(wA);
    }

    @Override
    public Account Transfer(int accountOne, int accountTwo, BigDecimal amount)
    {
        Account a = accounts.read(accountOne);
        Account b = accounts.read(accountTwo);
        
        if(a == null || b == null)
            return new Account();
        
        BigDecimal aBalance = a.getBalance().subtract(amount);
        BigDecimal bBalance = b.getBalance().add(amount);

        
        Account wA = new Account(a.getId(),a.getCustomer(),a.getType(),aBalance);
        Account wB = new Account(b.getId(),b.getCustomer(),b.getType(),bBalance);
        accounts.update(wA);
        return accounts.update(wB);
    }
    
    public List<Customer> readCustomers()
    {
        return customers.readAll();
    }
    
    public void deleteAll()
    {
        customers.deleteAll();
        accounts.clear();
    }

}
