//package org.ssa.ironyard.controller;
//
//import static org.junit.Assert.*;
//
//import org.easymock.Capture;
//import org.easymock.EasyMock;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.http.ResponseEntity;
//import org.ssa.ironyard.model.Customer;
//import org.ssa.ironyard.service.BankAccountServicesImpl;
//import org.ssa.ironyard.service.BankTransactionServicesImpl;
//
//public class CustomerControllerTest
//{
//    
//    BankTransactionServicesImpl bts;
//    BankAccountServicesImpl bas;
//    CustomerController controller;
//    
//
//    @Before
//    public void mock()
//    {
//        this.bts = 
//    }
//
//    @Test
//    public void detail()
//    {
//        Customer detail = new Customer(75,"Mike","Patrick");
//        
//        EasyMock.expect(this.bts.readCustomer(detail.getId())).andReturn(detail);//expect just calls .equals
//        EasyMock.replay(this.bts);
//        
//        ResponseEntity<DualResponse<String,Customer>> response = this.controller.service.readCustomer(detail.getId());
//        assertEquals("",detail,response.getBody().getSuccess());
//        assertNull("",response.getBody().getError());
//        
//        EasyMock.verify(this.bts); // will verify that easymock was called and this.bts was put in
//    }
//    
//    @Test
//    public void addCustomer()
//    {
//        Customer returned = new Customer(12,"Mike","Patrick");
//        
//        Capture<Customer> captured = Capture.<Customer>newInstance();//this is capturing the argument that was called so we can inspect it
//        
//        EasyMock.expect(this.bas.insertCustomer(EasyMock.capture(captured).getFirstName(),EasyMock.capture(captured).getLastName())).andReturn(returned.clone());
//        EasyMock.expect(this.request.getParameter("firstName")).andReturn(returned.clone().getFirstName());
//        EasyMock.expect(this.request.getParameter("lastName")).andReturn(returned.clone().getFirstName());
//        
//        EasyMock.replay(this.request,this.bas);
//        ResponseEntity<DualResponse<String,Customer>> response = this.controller.aService.insertCustomer(returned.getFirstName(), returned.getLastName());
//        
//        Customer parameter = captured.getValue();
//        assertEquals("", returned.getFirstName(),parameter.getFirstName());
//        assertEquals("", returned.getLastName(), parameter.getLastName());
//        
//        assertTrue("",returned.deeplyEquals(response.getBody().getSuccess()));
//        EasyMock.verify(this.request, this.bas);
//    }
//}
