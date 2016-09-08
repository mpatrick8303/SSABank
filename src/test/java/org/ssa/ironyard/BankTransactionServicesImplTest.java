package org.ssa.ironyard;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.dao.AccountDAOImpl;
import org.ssa.ironyard.dao.CustomerDAO;
import org.ssa.ironyard.dao.CustomerDAOImpl;
import org.ssa.ironyard.model.Account;
import org.ssa.ironyard.model.Account.Type;
import org.ssa.ironyard.model.Customer;
import org.ssa.ironyard.service.BankTransactionServicesImpl;

import com.mysql.cj.jdbc.MysqlDataSource;


public class BankTransactionServicesImplTest
{

    Customer mikePatrick;
    Customer travisAdams;
    Account mikePatrickCH;
    Account mikePatrickSA;
    Account travisAdamsSA;
    Account travisAdamsCH;
    Account mikePatrickCHIns;
    Account mikePatrickSAIns;
    Account travisAdamsSAIns;
    Account travisAdamsCHIns;
    
    
    
    BankTransactionServicesImpl bankS;
    
    AccountDAOImpl accounts;
    CustomerDAOImpl cus;

    
    
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root&" + "useServerPrepStmts=true";
    
    //@Before
    public void setup()
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        accounts = new AccountDAOImpl(mysqlDataSource);
        cus = new CustomerDAOImpl(mysqlDataSource);
        bankS = new BankTransactionServicesImpl(accounts, cus);
        
        //bankS.deleteAll();
        
        mikePatrick = new Customer("Mike","Patrick");
        travisAdams = new Customer("Travis","Adams");
        mikePatrick = cus.insert(mikePatrick);
        travisAdams = cus.insert(travisAdams);
        
        mikePatrickCH = new Account(mikePatrick, Type.CHECKING, BigDecimal.valueOf(1000.00));
        mikePatrickSA = new Account(mikePatrick, Type.SAVINGS, BigDecimal.valueOf(400.00));
        travisAdamsCH = new Account(travisAdams,Type.CHECKING, BigDecimal.valueOf(-700.00));
        travisAdamsSA = new Account(travisAdams,Type.SAVINGS, BigDecimal.valueOf(500.00));
        
        
        mikePatrickCHIns = accounts.insert(mikePatrickCH);
        mikePatrickSAIns = accounts.insert(mikePatrickSA);
        travisAdamsCHIns = accounts.insert(travisAdamsCH);
        travisAdamsSAIns = accounts.insert(travisAdamsSA);
        
    }
    
    //@Test
    public void testWithdrawl()
    {
        BigDecimal amount = BigDecimal.valueOf(200);
        Account a = accounts.read(mikePatrickCHIns.getId());
        
        Account wA = bankS.Withdrawl(a.getId(), amount);

        
        assertTrue(a.getBalance().add(amount).equals(wA.getBalance()));
        Account aN = new Account();
        //assertTrue(aN.equals(bankS.Withdrawl(1, BigDecimal.valueOf(200))));
    }
    
    //@Test
    public void testDeposit()
    {
        BigDecimal amount = BigDecimal.valueOf(200);
        Account a = accounts.read(travisAdamsCHIns.getId());
        
        Account wA = bankS.Deposit(a.getId(), amount);

        
        assertTrue(a.getBalance().subtract(amount).equals(wA.getBalance()));
        
        Account aN = new Account();
        //assertTrue(aN.equals(bankS.Deposit(1, BigDecimal.valueOf(200))));
    }
    
    //@Test
    public void testTransfer()
    {
        BigDecimal amount = BigDecimal.valueOf(200.00);
        Account a = accounts.read(mikePatrickSAIns.getId());
        Account b = accounts.read(travisAdamsSAIns.getId());
        
        Account tR = bankS.Transfer(a.getId(),b.getId(),amount);
        
        
        
        assertTrue(b.getBalance().add(amount).equals(tR.getBalance()));
        
        Account aN = new Account();
        //assertTrue(aN.equals(bankS.Transfer(1, a.getId(), BigDecimal.valueOf(200))));
        //assertTrue(aN.equals(bankS.Transfer(a.getId(), 1, BigDecimal.valueOf(200))));
        //assertTrue(aN.equals(bankS.Transfer(2, 1, BigDecimal.valueOf(200))));
        
        
    }
    
    //@Test
    public void testDeleteAll()
    {
        bankS.deleteAll();
    }

}
