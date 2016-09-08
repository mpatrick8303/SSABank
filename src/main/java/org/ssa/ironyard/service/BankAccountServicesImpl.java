package org.ssa.ironyard.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssa.ironyard.dao.AccountDAOImpl;
import org.ssa.ironyard.dao.CustomerDAOImpl;
import org.ssa.ironyard.model.Account;
import org.ssa.ironyard.model.Account.Type;
import org.ssa.ironyard.model.Customer;

public class BankAccountServicesImpl implements BankAccountServices
{
    
    AccountDAOImpl accounts;
    CustomerDAOImpl customers;
    
    @Autowired
    public BankAccountServicesImpl(AccountDAOImpl accounts,CustomerDAOImpl customers)
    {
        
        this.accounts = accounts;
        this.customers = customers;
        
    }
    
    @Override
    public Customer insertCustomer(String fName, String lName)
    {
        Customer c = new Customer(fName, lName);
        return customers.insert(c);
    }
    
    @Override
    public Account insertAccount(Customer customer, Type type, BigDecimal balance)
    {
        Account a = new Account(customer, type, balance);
        return accounts.insert(a);
    }


    @Override
    public boolean deleteAccount(int id)
    {
        Account a = accounts.read(id);
        
        if(a == null)
            return false;
        
        return accounts.delete(a.getId());
    }
    
    @Override
    public boolean deleteCustomer(int id)
    {
        Customer c = customers.read(id);
        
        if(c == null)
            return false;
        
        List<Account> accountsByUser = new ArrayList<>();
        accountsByUser = accounts.readUser(c.getId());
        
        for (int i = 0; i < accountsByUser.size(); i++)
        {
            accounts.delete(accountsByUser.get(i).getId());
        }
        
        return customers.delete(id);
       
    }

    @Override
    public Customer updateCustomer(Customer customer)
    {        
        if(customers.read(customer.getId()) == null)
            return new Customer();
        
        Customer c = new Customer(customer.getId(),customer.getFirstName(),customer.getLastName());
        
        return customers.update(c);
    }

    @Override
    public Account updateAccount(Account account)
    {
        if(accounts.read(account.getId()) == null)
            return new Account();
        
        Account a = new Account(account.getId(),account.getCustomer(),account.getType(),account.getBalance());
        
        return accounts.update(a);
    }
}
