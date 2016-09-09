package org.ssa.ironyard;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.dao.AccountDAOImpl;
import org.ssa.ironyard.dao.CustomerDAO;
import org.ssa.ironyard.dao.CustomerDAOImpl;
import org.ssa.ironyard.model.Account;
import org.ssa.ironyard.model.Customer;
import org.ssa.ironyard.model.Account.Type;

import com.mysql.cj.jdbc.MysqlDataSource;

public class readTest
{

    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root&" + "useServerPrepStmts=true";
    AccountDAOImpl accounts;
    CustomerDAO cus;
    MysqlDataSource mysqlDataSource = new MysqlDataSource();
    
    //@Before
    public void setup()
    {
        
        
        mysqlDataSource.setURL(URL);
        accounts = new AccountDAOImpl(mysqlDataSource);
        cus = new CustomerDAOImpl(mysqlDataSource);
     
    }
    
    //@Test
    public void testRead()
    {
        List<Account> a = accounts.readUser(5171);
        System.out.println(a);
        System.out.println(a.get(0).getId());
        
        System.out.println(a.get(0).getType());
        
        System.out.println(a.get(0).getBalance());
        
    }
    
}
