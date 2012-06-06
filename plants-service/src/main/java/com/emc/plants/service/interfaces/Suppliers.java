//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own //instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use //or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2004, 2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.interfaces;
import java.util.Collection;

import com.emc.plants.pojo.beans.SupplierInfo;

/**
 * Remote interface for Enterprise Bean: Suppliers
 */
//@Local
public interface Suppliers {
    /**
     * @param supplierID
     * @param name
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param phone
     * @param url
     */
    public void createSupplier(String supplierID, String name, String street, String city, String state, String zip, String phone, String url);
    /**
     * @param supplierID
     * @param name
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param phone
     * @param url
     * @return SupplierInfo
     */
    public SupplierInfo updateSupplier(String supplierID, String name, String street, String city, String state, String zip, String phone, String url);
    /**
     * @param supplierID
     * @return String
     */
    public String getSupplierURL(String supplierID);
    /**
     * @param supplierID
     * @return SupplierInfo
     */
    public SupplierInfo getSupplierInfo(String supplierID);
    /**
     * @return SupplierInfo
     */
    public SupplierInfo getSupplierInfo();
    /**
     * @return Vector
     */
    public Collection <SupplierInfo>findSuppliers();
}
