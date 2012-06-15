//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2003,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.impl;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.emc.plants.persistence.BackOrder;
import com.emc.plants.persistence.Inventory;
import com.emc.plants.pojo.beans.BackOrderItem;
import com.emc.plants.service.interfaces.BackOrderStock;
import com.emc.plants.utils.Util;
/**
 * Bean implementation class for Enterprise Bean: BackOrderStock
 */
//@Stateless (name="BackOrderStock")
//@RolesAllowed ("SampAdmin")
@Repository("backOrderStockBean")
@SuppressWarnings("unchecked")
public class BackOrderStockBean implements BackOrderStock
{
	@PersistenceContext(unitName="PBW")
	EntityManager em;
	
	/**
	 * Method receiveConfirmation.
	 * @param backOrderID
	 */
	public int receiveConfirmation(long backOrderID)
	{
		int rc = 0;
		BackOrder backOrder;
		/*
		 try
		 {
		 BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKeyUpdate(backOrderID);
		 */
		Util.debug("BackOrderStockBean.receiveConfirmation() - Finding Back Order for backOrderID=" + backOrderID);
		backOrder = em.find(BackOrder.class, backOrderID);
		backOrder.setStatus(Util.STATUS_RECEIVEDSTOCK);
		Util.debug("BackOrderStockBean.receiveConfirmation() - Updating status(" + Util.STATUS_RECEIVEDSTOCK + ") of backOrderID(" + backOrderID + ")");
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.receiveConfirmation() -  Exception: " + e);
		 rc = -1;
		 }
		 */
		return (rc);
	}
	/**
	 * Method orderStock.
	 * @param backOrderID
	 * @param quantity
	 */
	public void orderStock(long backOrderID, int quantity)
	{
		this.setBackOrderStatus(backOrderID, Util.STATUS_ORDEREDSTOCK);
		this.setBackOrderQuantity(backOrderID, quantity);
		this.setBackOrderOrderDate(backOrderID);
	}
	/**
	 * Method updateStock.
	 * @param backOrderID
	 * @param quantity
	 */
	public void updateStock(long backOrderID, int quantity)
	{
		this.setBackOrderStatus(backOrderID, Util.STATUS_ADDEDSTOCK);
	}
	/**
	 * @param backOrderID
	 */
	public void abortorderStock(long backOrderID)
	{
		Util.debug("backOrderStockBean.abortorderStock() - Aborting orderStock transation for backorderID: " + backOrderID);
		// Reset the back order status since the order failed.
		this.setBackOrderStatus(backOrderID, Util.STATUS_ORDERSTOCK);
	}
	/**
	 * Method getBackOrderID.
	 * @param backOrderID
	 * @return String
	 */
	public long getBackOrderID(long backOrderID)
	{
		long retbackOrderID = 0;
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.getBackOrderID() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKey(new BackOrderKey(backOrderID));
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		retbackOrderID = backOrder.getBackOrderID();
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.getBackOrderID() - Exception: " + e);
		 }
		 */
		return retbackOrderID;
	}
	/**
	 * Method getBackOrderInventoryID.
	 * @param backOrderID
	 * @return String
	 */
	public String getBackOrderInventoryID(long backOrderID)
	{
		String retinventoryID = "";
		
		Util.debug("BackOrderStockBean.getBackOrderID() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKey(new BackOrderKey(backOrderID));
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		retinventoryID = backOrder.getInventory().getInventoryId();
		
		return retinventoryID;
	}
	/**
	 * Method getSupplierOrderID.
	 * @param backOrderID
	 * @return String
	 */
	public String getSupplierOrderID(long backOrderID)
	{
		String supplierOrderID = "";
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.getSupplierOrderID() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKey(new BackOrderKey(backOrderID));
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		supplierOrderID = backOrder.getSupplierOrderID();
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.getSupplierOrderID() - Exception: " + e);
		 } */
		return supplierOrderID;
	}
	/**
	 * Method getBackOrderQuantity.
	 * @param backOrderID
	 * @return int
	 */
	public int getBackOrderQuantity(long backOrderID)
	{
		int backOrderQuantity = -1;
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.getBackOrderQuantity() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKey(new BackOrderKey(backOrderID));
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		backOrderQuantity = backOrder.getQuantity();
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.getBackOrderQuantity() - Exception: " + e);
		 } */
		return backOrderQuantity;
	}
	/**
	 * Method getBackOrderStatus.
	 * @param backOrderID
	 * @return String
	 */
	public String getBackOrderStatus(long backOrderID)
	{
		String backOrderStatus = "";
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.getBackOrderStatus() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKey(new BackOrderKey(backOrderID));
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		backOrderStatus = backOrder.getStatus();
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.getBackOrderQuantity() - Exception: " + e);
		 } */
		return backOrderStatus;
	}
	/**
	 * Method setSupplierOrderID.
	 * @param backOrderID
	 * @param supplierOrderID
	 */
	public void setSupplierOrderID(long backOrderID, String supplierOrderID)
	{
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.setSupplierOrderID() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKeyUpdate(backOrderID);
		// TODO: lowp: prefer find for update
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		backOrder.setSupplierOrderID(supplierOrderID);
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.setSupplierOrderID() - Exception: " + e);
		 } */
	}
	/**
	 * Method setBackOrderQuantity.
	 * @param backOrderID
	 * @param quantity
	 */
	public void setBackOrderQuantity(long backOrderID, int quantity)
	{
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.setBackOrderQuantity() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKeyUpdate(backOrderID);
		// TODO: lowp: prefer find for update
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		backOrder.setQuantity(quantity);
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.setBackOrderQuantity() - Exception: " + e);
		 } */
	}
	/**
	 * Method setBackOrderStatus.
	 * @param backOrderID
	 * @param Status
	 */
	public void setBackOrderStatus(long backOrderID, String Status)
	{
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.setBackOrderStatus() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKeyUpdate(backOrderID);
		// TODO: lowp: prefer find for update
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		backOrder.setStatus(Status);
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.setBackOrderStatus() - Exception: " + e);
		 } */
	}
	/**
	 * Method setBackOrderOrderDate.
	 * @param backOrderID
	 */
	public void setBackOrderOrderDate(long backOrderID)
	{
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.setBackOrderQuantity() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKeyUpdate(backOrderID);
		// TODO: lowp: prefer find for update
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		backOrder.setOrderDate(System.currentTimeMillis());
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.setBackOrderQuantity() - Exception: " + e);
		 } */
	}
	/**
	 * Method deleteBackOrder.
	 * @param backOrderID
	 */
	public void deleteBackOrder(long backOrderID)
	{
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.deleteBackOrder() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKeyUpdate(backOrderID);
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		em.remove(backOrder);
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.deleteBackOrder() - Exception: " + e);
		 } */
	}
	/**
	 * Method findByID.
	 * @param backOrderID
	 * @return BackOrderItem
	 */
	public BackOrderItem findByID(long backOrderID)
	{
		BackOrderItem backOrderItem = null;
		/*
		 try
		 { */
		Util.debug("BackOrderStockBean.findByID() - Entered");
		// BackOrderLocal backOrder = getBackOrderLocalHome().findByPrimaryKey(new BackOrderKey(backOrderID));
		BackOrder backOrder = em.find(BackOrder.class, backOrderID);
		backOrderItem = new BackOrderItem(backOrder);
		/*
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.findByID() - Exception: " + e);
		 } */
		return backOrderItem;
	}
	/**
	 * Method findBackOrderItems.
	 * @return Collection
	 */
	public Collection findBackOrderItems()
	{
		Collection backOrders;
		Collection<BackOrderItem> backOrderItems = new ArrayList<BackOrderItem>();
		/*
		 try
		 {
		 Util.debug("BackOrderStockBean.findBackOrderItems() - Entered");
		 Collection backOrderItems = getBackOrderLocalHome().findAll();
		 */
		Query q = em.createNamedQuery("findAllBackOrders");
		backOrders = q.getResultList();
		/*
		 Iterator i = backOrderItems.iterator();
		 while (i.hasNext())
		 {
		 BackOrderLocal backOrder = (BackOrderLocal) i.next();
		 backOrders.add(new BackOrderItem(backOrder));
		 }
		 }
		 catch (Exception e)
		 {
		 Util.debug("BackOrderStockBean.findBackOrderItems() - Exception: " + e);
		 }
		 */
		for (Object o : backOrders) {
			BackOrderItem bi = new BackOrderItem((BackOrder)o);
			backOrderItems.add(bi);
		}
		return backOrderItems;
	}
	/**
	 * Method createBackOrder.
	 * @param inventoryID
	 * @param amountToOrder
	 * @param maximumItems
	 */
	public void createBackOrder(String inventoryID, int amountToOrder, int maximumItems)
	{
		try
		{
			Util.debug("BackOrderStockBean.createBackOrder() - Entered");
			BackOrder backOrder = null;
			try
			{
				// See if there is already an existing backorder and increase the order quantity
				// but only if it has not been sent to the supplier.
				// backOrder = getBackOrderLocalHome().findByInventoryIDUpdate(inventoryID);
				// TODO: query need updating based on relation?
				Query q = em.createNamedQuery("findByInventoryID");
				q.setParameter("id", inventoryID);
				backOrder = (BackOrder) q.getSingleResult();
				if (!(backOrder.getStatus().equals(Util.STATUS_ORDERSTOCK)))
				{
					Util.debug("BackOrderStockBean.createBackOrder() - Backorders found but have already been ordered from the supplier");
//					throw new FinderException();
				}
				// Increase the BackOrder quantity for an existing Back Order.
				backOrder.setQuantity(backOrder.getQuantity() + amountToOrder);
			}
			catch (NoResultException e)
			{
				Util.debug("BackOrderStockBean.createBackOrder() - BackOrder doesn't exist." + e);
				Util.debug("BackOrderStockBean.createBackOrder() - Creating BackOrder for InventoryID: " + inventoryID);
				// Order enough stock from the supplier to reach the maximum threshold and to
				// satisfy the back order.
				amountToOrder = maximumItems + amountToOrder;
				// backOrder = getBackOrderLocalHome().create(inventoryID, amountToOrder);
				Inventory inv = em.find(Inventory.class, inventoryID);
				BackOrder b = new BackOrder(inv, amountToOrder);
				em.persist(b);
			}
		}
		catch (Exception e)
		{
			Util.debug("BackOrderStockBean.createBackOrder() - Exception: " + e);
		}
	}

}
