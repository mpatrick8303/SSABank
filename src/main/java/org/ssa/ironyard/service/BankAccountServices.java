package org.ssa.ironyard.service;

import java.math.BigDecimal;

import org.ssa.ironyard.model.Account;
import org.ssa.ironyard.model.Account.Type;
import org.ssa.ironyard.model.Customer;


public interface BankAccountServices
{
    Customer insertCustomer(String fName, String lName);
    Account insterAccount(Customer customer, Type type, BigDecimal balance);
    boolean deleteAccount(int id);
    boolean deleteCustomer(int id);
    Customer updateCustomer(Customer customer);
    Account updateAccount(Account account);
    
}
