//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own //instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use //or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2004, 2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * Bean implementation class for Enterprise Bean: Supplier
 */
@Entity(name="Supplier")
@Table(name="SUPPLIER", schema="APP")
@NamedQueries({
	@NamedQuery(
			name="findAllSuppliers",
			query="select s from Supplier s"),
	@NamedQuery(
			name="removeAllSupplier",
			query="delete from Supplier")
})
public class Supplier {
	@Id
	private String supplierID;
	private String name;
	private String city;
	private String usstate;
	private String zip;
	private String phone;
	private String url;
	private String street;
	
    public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsstate() {
		return usstate;
	}
	public void setUsstate(String usstate) {
		this.usstate = usstate;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public Supplier() {}
    public Supplier(String supplierID) {
        setSupplierID(supplierID);
    }
	/**
     * @param supplierID
     * @param name
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param phone
     * @param url
     * @throws javax.ejb.CreateException
     */
    public Supplier(String supplierID, String name, String street, String city, String state, String zip, String phone, String url)  {
        this.setSupplierID(supplierID);
        this.setName(name);
        this.setStreet(street);
        this.setCity(city);
        this.setUsstate(state);
        this.setZip(zip);
        this.setPhone(phone);
        this.setUrl(url);
    }
}
