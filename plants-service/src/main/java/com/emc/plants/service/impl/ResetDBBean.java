//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own //instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use //or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2004, 2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.impl;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.emc.plants.service.interfaces.ResetDB;
import com.emc.plants.utils.Util;
/**
 * Bean implementation class for Enterprise Bean: ResetDB
 */
//@Stateless (name="ResetDB")
@Repository("resetDBBean")
//@TransactionManagement(value=BEAN) 	// TODO: TransactionManagement(value=BEAN) 
public class ResetDBBean implements ResetDB {
   
	@PersistenceContext(unitName="PBW")
	EntityManager em;
	
    public void deleteAll() { 
        try {            	
        	Query q=em.createNamedQuery("removeAllOrders");
        	q.executeUpdate();
        	q=em.createNamedQuery("removeAllInventory");
        	q.executeUpdate();
        	q=em.createNamedQuery("removeAllIdGenerator");
        	q.executeUpdate();
        	q=em.createNamedQuery("removeAllCustomers");
        	q.executeUpdate();
        	q=em.createNamedQuery("removeAllOrderItem");
        	q.executeUpdate();
        	q=em.createNamedQuery("removeAllBackOrder");
        	q.executeUpdate();            	
        	q=em.createNamedQuery("removeAllSupplier");
        	q.executeUpdate();
            em.flush();
        } catch (Exception e) {
            Util.debug("ResetDB(deleteAll) -- Error deleting data from the database: "+e);
            e.printStackTrace();
        } 
        
    }


}