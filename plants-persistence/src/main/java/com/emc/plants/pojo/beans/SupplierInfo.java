//
// "This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
// instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
// or for redistribution by customer, as part of such an application, in customer's own products. " 
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2004,2004
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.pojo.beans;

import com.emc.plants.persistence.Supplier;


/**
 * A class to hold a store item's data.
 */
public class SupplierInfo implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String location_url;
    /** Default constructor. */
    public SupplierInfo() {}
    /** Constructor of supplier item
     * @param name - name of this supplier item.
     * @param street - street address of this supplier item.
     * @param city - city of this supplier item.
     * @param state - state of this supplier item.
     * @param phone - phone number of this supplier item.
     * @param location_url - location url of this supplier item.
     */
    public SupplierInfo(String id, String name, String street, String city, String state, String zip, String phone, String location_url) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.location_url = location_url;
    }
    /**
     * @param supplier
     */
    public SupplierInfo(Supplier supplier) {
        this.id = supplier.getSupplierID();
        this.name = supplier.getName();
        this.street = supplier.getStreet();
        this.city = supplier.getCity();
        this.state = supplier.getUsstate();
        this.zip = supplier.getZip();
        this.phone = supplier.getPhone();
        this.location_url = supplier.getUrl();
    }
    /** Get the id of the supplier. */
    public String getID() {
        return id;
    }
    /** Get the name of the supplier. */
    public String getName() {
        return name;
    }
    /** Get the street address of the supplier. */
    public String getStreet() {
        return street;
    }
    /** Get the city of the supplier. */
    public String getCity() {
        return city;
    }
    /** Get the state  of the supplier. */
    public String getState() {
        return state;
    }
    /** Get the zip code of the supplier. */
    public String getZip() {
        return zip;
    }
    /** Get the phone number of the supplier. */
    public String getPhone() {
        return phone;
    }
    /** Get the location url of the supplier. */
    public String getLocationURL() {
        return location_url;
    }
}
