//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Customer is the implementation class for the {@link Customer} entity
 * EJB.  Customer implements each of the business methods in the <code>Customer</code>
 * EJB local interface and each of the EJB lifecycle methods in the javax.ejb.EntityBean
 * interface.
 * 
 * @see Customer
 */
@Entity(name="Customer")
@Table(name="CUSTOMER", schema="APP")
@NamedQueries({
	@NamedQuery(
		name="removeAllCustomers",
		query="delete from Customer")
})
public class Customer
{
	@Id
	private String customerID;
	private String password;
	private String firstName;
	private String lastName;
	private String addr1;
	private String addr2;
	private String addrCity;
	private String addrState;
	private String addrZip;
	private String phone;
	
	public Customer() {}
	
	/** 
	 * Create a new Customer.
	 *
	 * @param key CustomerKey
	 * @param password Password used for this customer account.
	 * @param firstName First name of the customer.
	 * @param lastName Last name of the customer
	 * @param addr1 Street address of the customer
	 * @param addr2 Street address of the customer
	 * @param addrCity City 
	 * @param addrState State
	 * @param addrZip Zip code
	 * @param phone Phone number
	 */
	public Customer(String key, String password, 
			String firstName, String lastName,
			String addr1, String addr2,
			String addrCity, String addrState,
			String addrZip, String phone)
			{ 
		this.setCustomerID(key);
		this.setPassword(password);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setAddr1(addr1);
		this.setAddr2(addr2);
		this.setAddrCity(addrCity);
		this.setAddrState(addrState);
		this.setAddrZip(addrZip);
		this.setPhone(phone);
			}
	
	/**
	 * Verify password.
	 *
	 * @param password value to be checked.
	 * @return True, if password matches one stored.
	 */
	public boolean verifyPassword(String password)
	{
		return this.getPassword().equals(password);
	}
	
	/** Update the customer information. */
	// TODO: this can go away
	public void update(String firstName, String lastName,
			String addr1, String addr2,
			String addrCity, String addrState,
			String addrZip, String phone)
	{
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setAddr1(addr1);
		this.setAddr2(addr2);
		this.setAddrCity(addrCity);
		this.setAddrState(addrState);
		this.setAddrZip(addrZip);
		this.setPhone(phone);
	}
	
	/** Get the customer's full name.
	 * @return String of customer's full name. */
	public String getFullName() 
	{ 
		return this.getFirstName() + " " + this.getLastName(); 
	}
	
	public String getAddr1() {
		return addr1;
	}
	
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	
	public String getAddr2() {
		return addr2;
	}
	
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	
	public String getAddrCity() {
		return addrCity;
	}
	
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	
	public String getAddrState() {
		return addrState;
	}
	
	public void setAddrState(String addrState) {
		this.addrState = addrState;
	}
	
	public String getAddrZip() {
		return addrZip;
	}
	
	public void setAddrZip(String addrZip) {
		this.addrZip = addrZip;
	}
	
	public String getCustomerID() {
		return customerID;
	}
	
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
		
}

