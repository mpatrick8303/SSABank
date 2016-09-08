//package org.ssa.ironyard;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.math.BigDecimal;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.ssa.ironyard.dao.AccountDAOImpl;
//import org.ssa.ironyard.dao.CustomerDAOImpl;
//import org.ssa.ironyard.model.Account;
//import org.ssa.ironyard.model.Account.Type;
//import org.ssa.ironyard.model.Customer;
//import org.ssa.ironyard.service.BankAccountServicesImpl;
//
//import com.mysql.cj.jdbc.MysqlDataSource;
//
//public class BankAccountServicesImplTest
//{
//    
//    
//    AccountDAOImpl accounts;
//    CustomerDAOImpl cus;
//    BankAccountServicesImpl bAS;
//    
//    Customer cO;
//    
//    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root&" + "useServerPrepStmts=true";
//
//    @Before
//    public void setup()
//    {
//        MysqlDataSource mysqlDataSource = new MysqlDataSource();
//        mysqlDataSource.setURL(URL);
//        accounts = new AccountDAOImpl(mysqlDataSource);
//        cus = new CustomerDAOImpl(mysqlDataSource);
//        bAS = new BankAccountServicesImpl(accounts,cus);
//        
//        
//        //bTest.testDeleteAll();
//        
//        cO = bAS.insertCustomer("Travis", "Adams");
//    }
//    
//    @Test
//    public void testCustomerInsert()
//    {
//      Customer c1 = bAS.insertCustomer("Mike", "Patrick");
//      Customer c2 = cus.read(c1.getId());
//      
//      assertEquals("Mike", c2.getFirstName());
//      assertEquals("Patrick", c2.getLastName());
//
//    }
//    
//    @Test
//    public void testAccountInsert()
//    {
//        
//        Account a1 = bAS.insertAccount(cO, Type.CHECKING,BigDecimal.valueOf(200));
//        
//        Account a2 = accounts.read(a1.getId());
//        
//        assertTrue(a1.equals(a2));
//        assertTrue(a1.deeplyEquals(a2));
//        
//        
//    }
//    
//    @Test
//    public void testDeleteAccount()
//    {
//        Account a1 = bAS.insertAccount(cO, Type.CHECKING,BigDecimal.valueOf(200));
//        Account a2 = bAS.insertAccount(cO, Type.SAVINGS,BigDecimal.valueOf(500));
//        
//        assertTrue(bAS.deleteAccount(a1.getId()));
//        assertTrue(accounts.read(a1.getId()) == null);
//        assertTrue(accounts.read(a2.getId()) != null);
//    }
//    
//    @Test
//    public void testDeleteCustomer()
//    {
//        Customer c1 = bAS.insertCustomer("Mike", "Patrick");
//        Account a1 = bAS.insertAccount(c1, Type.CHECKING,BigDecimal.valueOf(200));
//        Account a2 = bAS.insertAccount(c1, Type.SAVINGS,BigDecimal.valueOf(500));
//        Account a3 = bAS.insertAccount(cO, Type.SAVINGS,BigDecimal.valueOf(1400));
//        
//        
//        bAS.deleteCustomer(c1.getId());
//        
//        assertTrue(accounts.readUser(c1.getId()).size() == 0);
//        assertTrue(cus.read(c1.getId()) == null);
//    }
//    
//    @Test
//    public void testUpdateCustomer()
//    {
//        Customer c1 = bAS.insertCustomer("Mike", "Patrick");
//        
//        Customer cON = new Customer(cO.getId(),"Travis","New");
//        bAS.updateCustomer(cON);
//        
//        Customer cR = cus.read(cON.getId());
//        
//        assertTrue(cO.getLastName() != cON.getLastName());
//        assertTrue(cON.getLastName().equals(cR.getLastName()));
//        assertTrue(cON.deeplyEquals(cR));
//        
//    }
//    
//    @Test
//    public void testUpdateAccount()
//    {
//        Customer c1 = bAS.insertCustomer("Mike", "Patrick");
//        Account a1 = bAS.insertAccount(c1, Type.CHECKING,BigDecimal.valueOf(200));
//        Account a2 = bAS.insertAccount(c1, Type.SAVINGS,BigDecimal.valueOf(500));
//        Account a3 = bAS.insertAccount(cO, Type.SAVINGS,BigDecimal.valueOf(1400));
//        
//        Account a1U = new Account(a1.getId(),a1.getCustomer(),a1.getType(),BigDecimal.valueOf(900));
//        bAS.updateAccount(a1U);
//        
//        Account a1R = accounts.read(a1U.getId());
//        
//        assertTrue(a1U.deeplyEquals(a1R));
//        assertTrue(a1U.getBalance().compareTo(a1R.getBalance()) == 0);
//        assertTrue(a1U.getType().equals(a1R.getType()));
//    }
//
//}
