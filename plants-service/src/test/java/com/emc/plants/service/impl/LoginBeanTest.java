package com.emc.plants.service.impl;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.service.interfaces.Login;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:app-context-service.xml" })
@Transactional
public class LoginBeanTest extends AbstractTransactionalJUnit4SpringContextTests {

       
		@Autowired 
       private ApplicationContext m_oApplicationContext;
		
		@Autowired
		private Login login;
       
       @PersistenceContext
       private EntityManager entityManager;

       private static Logger logger = Logger.getLogger(LoginBeanTest.class
                     .getName());

       

       
       
       // private Connection connection;
       //@Override
       protected void setUp() throws Exception {

       }

       @Test
       public void testCheckInventory() {

    	   String customerID="ghi.def@xyz.com";
    	   String password="plants";
    	   String firstName="abc";
    	   String lastName="def";
    	   String addr1="xx";
    	   String addr2="yy";
    	   String addrCity="zz";
    	   String addrState="xyz";
    	   String addrZip="560016";
    	   String phone="9988776644";
    	   	login.createNewUser(customerID, password, firstName,
    	   			lastName, addr1, addr2, addrCity, addrState, addrZip, phone);
    	   
                   assertTrue(true);
       }
       
       @Test
       public void testWrongPassword(){
    	   
    	   String customerID="abc.def@xyz.com";
    	   String password="plantsxxx";
    	   String results = login.verifyUserAndPassword(customerID, password);
    	   assertEquals("Result", "Password does not match for : "+customerID, results.trim());
    	   
       }
       
       @Test
       public void testInvalidUser(){
    	   
    	   String customerID="abcxxx.def@xyz.com";
    	   String password="plantsxxx";
    	   String results = login.verifyUserAndPassword(customerID, password);
    	   assertEquals("Result", "Could not find account for : "+customerID, results.trim());
    	   
       }
       
       @Test
       public void testGetCustomerInfo(){
    	   
    	   String customerID="abc.def@xyz.com";
    	   CustomerInfo customerInfo = login.getCustomerInfo(customerID);
    	   assertEquals("Result", "abc" ,customerInfo.getFirstName() );
    	   
       }
       
       @Test
       public void testUpdateUser() {

    	   
    	   String customerID="abc.def@xyz.com";
    	   String firstName="abc";
    	   String lastName="defXXX";
    	   String addr1="xx";
    	   String addr2="yy";
    	   String addrCity="zz";
    	   String addrState="xyz";
    	   String addrZip="560016";
    	   String phone="9988776644";
    	   CustomerInfo customerInfo = login.updateUser(customerID, firstName, lastName, addr1, addr2, addrCity, addrState, addrZip, phone);
    	   assertEquals("Result", "defXXX" ,customerInfo.getLastName());
       }

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

       
}
