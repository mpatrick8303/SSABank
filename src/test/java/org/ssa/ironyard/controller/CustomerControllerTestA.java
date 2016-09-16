package org.ssa.ironyard.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.ssa.ironyard.model.Customer;
import org.ssa.ironyard.service.BankAccountServicesImpl;
import org.ssa.ironyard.service.BankTransactionServicesImpl;


public class CustomerControllerTestA
{
    BankAccountServicesImpl bs;
    BankTransactionServicesImpl bts;
    CustomerController controller;
    
    @Before
    public void setup()
    {
        bs = EasyMock.niceMock(BankAccountServicesImpl.class);
        bts = EasyMock.niceMock(BankTransactionServicesImpl.class);
        this.controller = new CustomerController(bs, bts);
    }

    @Test
    public void customerTest()
    {
        Customer c = new Customer();
        c.setFirstName("mike");
        c.setLastName("patrick");
        c.setID(5172);
        c.setLoaded(true);

        EasyMock.expect(bts.readCustomer(5172)).andReturn(c);
        EasyMock.replay(bts);

        ResponseEntity<Map<String, Object>> customerMap = this.controller.customer("5172");

        Customer customer = (Customer) customerMap.getBody().get("success");

        assertTrue(c.deeplyEquals(customer));
        
        EasyMock.verify(bts);

    }
    
    @Test
    public void addCustomerTest()
    {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addParameter("firstName", "Thurston");
        mockRequest.addParameter("lastName", "Nabe");
    
        Customer c = new Customer();
        c.setFirstName("Thurston");
        c.setLastName("Nabe");
        c.setLoaded(true);
        
        Capture<Customer> capturedCustomer = Capture.<Customer>newInstance();

        EasyMock.expect(bs.insertCustomer("Thurston", "Nabe")).andReturn(c);
        EasyMock.replay(bs);

        ResponseEntity<Map<String, Customer>> customerMap = this.controller.addCustomer(mockRequest);

        Customer customer = (Customer) customerMap.getBody().get("success");

         assertEquals("Thurston", customer.getFirstName());
        assertEquals("Nabe", customer.getLastName());
        
        
        

    
    }
 
    
}
