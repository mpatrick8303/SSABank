package org.ssa.ironyard.service;

import java.math.BigDecimal;

import org.ssa.ironyard.model.Account;
import org.ssa.ironyard.model.Account.Type;
import org.ssa.ironyard.model.Customer;

public interface BankTransactionServices
{
    
    Account Withdrawl(int account, BigDecimal amount) throws IllegalArgumentException;
    
    Account Deposit(int account, BigDecimal amount) throws IllegalArgumentException;
    
    Account Transfer(int accountOne, int accountTwo, BigDecimal amount) throws IllegalArgumentException;
    

    
}
