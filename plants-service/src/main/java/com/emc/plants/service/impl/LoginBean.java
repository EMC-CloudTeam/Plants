//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2003
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emc.plants.persistence.Customer;
import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.utils.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * LoginBean is the implementation class for the {@link Login} stateless session
 * EJB.  LoginBean implements each of the business methods in the <code>Login</code>
 * EJB remote interface and each of the EJB lifecycle methods in the javax.ejb.SessionBean
 * interface.
 * 
 * @see Login
 */
//@Stateless (name="Login")
 @Repository("login")
public class LoginBean implements Login
{
	@Autowired
	private EntityManagerFactory entityManagerFactory;

   // private EntityManager em;
	
	
//
//	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
//		this.entityManagerFactory =entityManagerFactory;
//	}
	

	



	
	/**
	 * Verify that the user exists and the password is value.
	 * 
	 * @param customerID The customer ID
	 * @param password The password for the customer ID
	 * @return String with a results message.
	 */
	public String verifyUserAndPassword(String customerID, String password)
	{
		// Try to get customer.
		String results = null;
		Customer customer = null;
		
		/*
		 CustomerHome customerHome = (CustomerHome) 
		 Util.getEJBLocalHome("java:comp/env/ejb/Customer",
		 com.ibm.websphere.samples.pbwjpa.CustomerHome.class);
		 
		 try
		 {
		 customer = customerHome.findByPrimaryKey(new CustomerKey(customerID));
		 }
		 catch (ObjectNotFoundException e) { }
		 }
		 catch (FinderException e) { e.printStackTrace(); }
		 */
        EntityManager em = entityManagerFactory.createEntityManager();
		customer = em.find(Customer.class, customerID);
		
		// Does customer exists?
		if (customer != null)
		{
			if ( ! customer.verifyPassword(password) )    // Is password correct?
			{
				results = "\nPassword does not match for : " + customerID; 
				Util.debug("Password given does not match for userid=" + customerID);
			}
		}
		else     // Customer was not found.
		{
			results = "\nCould not find account for : " + customerID; 
			Util.debug("customer " + customerID + " NOT found");
		}
		
		return results;
	}
	
	/**
	 * Create a new user.
	 *
	 * @param customerID The new customer ID.
	 * @param password The password for the customer ID.
	 * @param firstName First name.
	 * @param lastName Last name.
	 * @param addr1 Address line 1.
	 * @param addr2 Address line 2.
	 * @param addrCity City address information.
	 * @param addrState State address information.
	 * @param addrZip Zip code address information.
	 * @param phone User's phone number.
	 * @return CustomerInfo
	 */
      //@Transactional
	public CustomerInfo createNewUser(String customerID, String password, String firstName,
			String lastName, String addr1, String addr2,
			String addrCity, String addrState, String addrZip,
			String phone)
	{
		CustomerInfo customerInfo = null;
		
		/*
		 customerHome = (CustomerHome) Util.getEJBLocalHome("java:comp/env/ejb/Customer",
		 com.ibm.websphere.samples.pbwjpa.CustomerHome.class);
		 
		 try
		 {
		 // Only create new user if it doesn't already exist.
		  customerHome.findByPrimaryKeyUpdate(customerID);
		  }
		  catch (ObjectNotFoundException onfe)
		  {
		  // Create customer and return true if all goes well.
		   Customer customer = 
		   customerHome.create(new CustomerKey(customerID), password, firstName, 
		   lastName, addr1, addr2, addrCity, addrState,
		   addrZip, phone);
		   
		   if (customer != null)
		   customerInfo = new CustomerInfo(customer);
		   }
		   }
		   catch (FinderException e) { e.printStackTrace(); }
		   catch (CreateException e) { e.printStackTrace(); }
		   */
		Customer c = new Customer(customerID, password, firstName, lastName, addr1, addr2,
				addrCity, addrState, addrZip, phone);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
		em.persist(c);
		em.flush();
        em.getTransaction().commit();
		customerInfo = new CustomerInfo(c);
		return customerInfo;
	}
	
	/**
	 * Update an existing user.
	 *
	 * @param customerID The customer ID.
	 * @param firstName First name.
	 * @param lastName Last name.
	 * @param addr1 Address line 1.
	 * @param addr2 Address line 2.
	 * @param addrCity City address information.
	 * @param addrState State address information.
	 * @param addrZip Zip code address information.
	 * @param phone User's phone number.
	 * @return CustomerInfo
	 */
	public CustomerInfo updateUser(String customerID, String firstName, String lastName, 
			String addr1, String addr2, String addrCity, 
			String addrState, String addrZip,String phone)
	{
		CustomerInfo customerInfo = null;
		// TODO: lowp: no lock check is performed to see if cust data has changed since fetch!
		/*
		 customerHome = (CustomerHome) Util.getEJBLocalHome("java:comp/env/ejb/Customer",
		 com.ibm.websphere.samples.pbwjpa.CustomerHome.class);
		 
		 Customer customer = customerHome.findByPrimaryKeyUpdate(customerID);
		 customer.update(firstName, lastName, addr1, addr2, addrCity, addrState, addrZip, phone);
		 customerInfo = new CustomerInfo(customer);
		 */
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
		Customer c = em.find(Customer.class, customerID);
		em.lock(c, LockModeType.WRITE);
		em.refresh(c);
		// TODO: lowp: test and set for update performance?
		c.setFirstName(firstName);
		c.setLastName(lastName);
		c.setAddr1(addr1);
		c.setAddr2(addr2);
		c.setAddrCity(addrCity);
		c.setAddrState(addrState);
		c.setAddrZip(addrZip);
		c.setPhone(phone);
		
		customerInfo = new CustomerInfo(c);
		em.getTransaction().commit();
		return customerInfo;
	}
	
	/**
	 * Retrieve an existing user.
	 * 
	 * @param customerID The customer ID.
	 * @return CustomerInfo
	 */    
	public CustomerInfo getCustomerInfo(String customerID)
	{
		

		//EntityManager em = entityManagerFactory.createEntityManager();
		
		CustomerInfo customerInfo = null;
		/*
		 customerHome = (CustomerHome) Util.getEJBLocalHome("java:comp/env/ejb/Customer",
		 com.ibm.websphere.samples.pbwjpa.CustomerHome.class);
		 
		 Customer customer = customerHome.findByPrimaryKey(new CustomerKey(customerID));
		 customerInfo = new CustomerInfo(customer);
		 */
        EntityManager em = entityManagerFactory.createEntityManager();
        System.out.println(" Entity Manager :: "+em);
		Customer c = em.find(Customer.class, customerID);
		customerInfo = new CustomerInfo(c);
		return customerInfo;
		
	}

  /*  @PersistenceContext(unitName="PBW")
    public void setEm(EntityManager em) {
        this.em = em;
    }*/
}

