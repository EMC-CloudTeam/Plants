//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own //instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use //or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2004, 2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.impl;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emc.plants.persistence.Supplier;
import com.emc.plants.pojo.beans.SupplierInfo;
import com.emc.plants.service.interfaces.Suppliers;
import com.emc.plants.utils.Util;
/**
 * Bean implementation class for Enterprise Bean: Suppliers
 */
//@Stateless(name="Suppliers")
@Repository("suppliersBean")
public class SuppliersBean implements Suppliers
{

	@PersistenceContext(unitName="PBW")
	EntityManager em;
	
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
	public void createSupplier(String supplierID, String name, String street, String city, String state, String zip, String phone, String url) {
		try {
			Util.debug("SuppliersBean.createSupplier() - Entered");
			Supplier supplier = null;
			/*
			 try {
			 // Create a new Supplier if there is NOT an existing Supplier.
			  supplier = getSupplierLocalHome().findByPrimaryKey(new SupplierKey(supplierID));
			  } catch (FinderException e) {
			  */
			supplier = em.find(Supplier.class, supplierID);
			if (supplier == null) {
				Util.debug("SuppliersBean.createSupplier() - supplier doesn't exist.");
				Util.debug("SuppliersBean.createSupplier() - Creating Supplier for SupplierID: " + supplierID);
				//supplier = getSupplierLocalHome().create(supplierID, name, street, city, state, zip, phone, url);
				supplier = new Supplier(supplierID, name, street, city, state, zip, phone, url);
				em.persist(supplier);
			}
		} catch (Exception e) {
			Util.debug("SuppliersBean.createSupplier() - Exception: " + e);
		}
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
	 * @return supplierInfo
	 */
	@Transactional
	public SupplierInfo updateSupplier(String supplierID, String name, String street, String city, String state, String zip, String phone, String url) {
		SupplierInfo supplierInfo = null;
		try {
			Util.debug("SuppliersBean.updateSupplier() - Entered");
			Supplier supplier = null;
			/*
			 try { */
			supplier = em.find(Supplier.class, supplierID);
			if (supplier != null) {
				// Create a new Supplier if there is NOT an existing Supplier.
				// supplier = getSupplierLocalHome().findByPrimaryKey(new SupplierKey(supplierID));
				supplier.setName(name);
				supplier.setStreet(street);
				supplier.setCity(city);
				supplier.setUsstate(state);
				supplier.setZip(zip);
				supplier.setPhone(phone);
				supplier.setUrl(url);
				supplierInfo = new SupplierInfo(supplier);
				em.persist(supplier);
				em.flush();
			} else { // catch (FinderException e) {
				Util.debug("SuppliersBean.updateSupplier() - supplier doesn't exist.");
				Util.debug("SuppliersBean.updateSupplier() - Couldn't update Supplier for SupplierID: " + supplierID);
			}
		} catch (Exception e) {
			Util.debug("SuppliersBean.createSupplier() - Exception: " + e);
		}
		return (supplierInfo);
	}
	/**
	 * @param supplierID
	 * @return url
	 */
	public String getSupplierURL(String supplierID) {
		String url = "";
		/*
		 try { */
		Util.debug("SuppliersBean.getSupplierURL() - Entered");
		Supplier supplier = null;
		// Create a new Supplier if there is NOT an existing Supplier.
		// supplier = getSupplierLocalHome().findByPrimaryKey(new SupplierKey(supplierID));
		supplier = em.find(Supplier.class, supplierID);
		if (supplier != null) url = supplier.getUrl();
		/*
		 } catch (Exception e) {
		 Util.debug("SuppliersBean.getSupplierURL() - Exception: " + e);
		 }
		 */
		return (url);
	}
	/**
	 * @return supplierItem
	 */
	public SupplierInfo getSupplierInfo(String supplierID) {
		SupplierInfo supplierItem = null;
		Util.debug("SuppliersBean.getSupplierInfo() - Entered");
		Supplier supplier = null;
		
		// Return the supplier Info if the supplier exists.
		supplier = em.find(Supplier.class, supplierID);
		if (supplier != null)
			supplierItem = new SupplierInfo(supplier);
		else {
			Util.debug("SuppliersBean.getSupplierInfo() - Supplier "+supplierID+" not found");
		}
		return (supplierItem);
	}
	/**
	 * @return supplierInfo
	 */
	public SupplierInfo getSupplierInfo() {
		// Retrieve the first Supplier Info
		SupplierInfo supplierInfo = null;
		try {
			Collection <SupplierInfo>suppliers = this.findSuppliers();
			if (suppliers != null) {
				Util.debug("AdminServlet.getSupplierInfo() - Supplier found!");
				Iterator <SupplierInfo>i = suppliers.iterator();
				if (i.hasNext()) {
					supplierInfo = (SupplierInfo) i.next();
				}
			}
		} catch (Exception e) {
			Util.debug("AdminServlet.getSupplierInfo() - Exception: " + e);
		}
		return (supplierInfo);
	}
	/**
	 * @return suppliers
	 */
	@SuppressWarnings("unchecked")
	public Collection<SupplierInfo> findSuppliers() {
		Vector suppliers = new Vector();
		/*
		 try {
		 Util.debug("BackOrderStockBean.findSuppliers() - Entered");
		 Collection supplierItems = getSupplierLocalHome().findAll();
		 Iterator i = supplierItems.iterator();
		 while (i.hasNext()) {
		 SupplierLocal supplier = (SupplierLocal) i.next();
		 suppliers.add(new SupplierInfo(supplier));
		 }
		 } catch (Exception e) {
		 Util.debug("BackOrderStockBean.findSupplierItems() - Exception: " + e);
		 }
		 */
		Query q = em.createNamedQuery("findAllSuppliers");
		List l=q.getResultList();
		if (l!=null){
			Iterator i=l.iterator();
			while (i.hasNext()) {
				 Supplier supplier = (Supplier) i.next();
				 suppliers.add(new SupplierInfo(supplier));
			}
		}
		
		return suppliers;
	}

}
