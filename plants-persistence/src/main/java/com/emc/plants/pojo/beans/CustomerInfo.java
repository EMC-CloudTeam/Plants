//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2002
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.pojo.beans;

import com.emc.plants.persistence.Customer;


/**
 * A class to hold a customer's data.
 */
// TODO: lowp: merge with Customer
public class CustomerInfo implements java.io.Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String customerID;
   private String firstName;
   private String lastName;
   private String addr1;
   private String addr2;
   private String addrCity;
   private String addrState;
   private String addrZip;
   private String phone;

   /** 
    * Constructor to create a CustomerInfo by passing each field.
    */
   public CustomerInfo(String customerID, String firstName, String lastName,
                       String addr1, String addr2, String addrCity,
                       String addrState, String addrZip, String phone)
   {
      this.customerID = customerID;
      this.firstName = firstName;
      this.lastName = lastName;
      this.addr1 = addr1;
      this.addr2 = addr2;
      this.addrCity = addrCity;
      this.addrState = addrState;
      this.addrZip = addrZip;
      this.phone = phone;
   }

   /**
    * Constructor to create a CustomerInfo using a Customer.
    */
   public CustomerInfo(Customer customer)
   {
      customerID = customer.getCustomerID();
      firstName = customer.getFirstName();
      lastName = customer.getLastName();
      addr1 = customer.getAddr1();
      addr2 = customer.getAddr2();
      addrCity = customer.getAddrCity();
      addrState = customer.getAddrState();
      addrZip = customer.getAddrZip();
      phone = customer.getPhone();
   }

   /** Get the customer ID.
    * @return Customer ID */
   public String getCustomerID() { return customerID; }
   /** Get the customer first name.
    * @return Customer first name */
   public String getFirstName() { return firstName; }
   /** Get the customer last name.
    * @return Customer last name */
   public String getLastName() { return lastName; }
   /** Get the customer address line 1.
    * @return Customer address line 1 */
   public String getAddr1() { return addr1; }
   /** Get the customer address line 2.
    * @return Customer address line 2 */
   public String getAddr2() { return addr2; }
   /** Get the customer city.
    * @return Customer city */
   public String getAddrCity() { return addrCity; }
   /** Get the customer state.
    * @return Customer state */
   public String getAddrState() { return addrState; }
   /** Get the customer zip code.
    * @return Customer zip code */
   public String getAddrZip() { return addrZip; }
   /** Get the customer phone.
    * @return Customer phone */
   public String getPhone() { return phone; }
}
