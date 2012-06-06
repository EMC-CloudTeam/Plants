//
// "This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
// instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
// or for redistribution by customer, as part of such an application, in customer's own products. " 
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2003
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.interfaces;

import com.emc.plants.pojo.beans.CustomerInfo;

//import javax.ejb.Remote;




/**
 * Remote interface for Login stateless session bean.
 */
//@Remote
public interface Login
{
	/**
	 * Verify that the user exists and the password is value.
	 * 
	 * @param customerID The customer ID
	 * @param password The password for the customer ID
	 * @return String with a results message.
	 */
	public String verifyUserAndPassword(String customerID,
	                                     String password);
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
    public CustomerInfo createNewUser(String customerID, String password, String firstName, 
                                      String lastName, String addr1, String addr2,
                                      String addrCity, String addrState, String addrZip,
                                      String phone);
            
    /**
     * Retrieve an existing user.
     * 
     * @param customerID The customer ID.
     * @return CustomerInfo
     */
    public CustomerInfo getCustomerInfo(String customerID);
    
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
                                   String addrState, String addrZip, String phone);
}

