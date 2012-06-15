//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2003,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.emc.plants.utils.Util;
/**
 * Bean implementation class for Enterprise Bean: BackOrder
 */
@Entity(name="BackOrder") 
@Table(name="BACKORDER", schema="APP") 
@NamedQueries({
	@NamedQuery(
		name="findAllBackOrders",
		query="select b from BackOrder b"),
	@NamedQuery(
		name="findByInventoryID",
		query="select b from BackOrder b where ((b.inventory.inventoryId = :id) and (b.status = 'Order Stock'))"	),
	@NamedQuery(
			name="removeAllBackOrder",
			query="delete from BackOrder")		
})
public class BackOrder
{
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="BackOrderSeq")
	@TableGenerator(name="BackOrderSeq", table="IDGENERATOR", pkColumnName="IDNAME", 
			pkColumnValue="BACKORDER", valueColumnName="IDVALUE",schema = "APP")
	private long backOrderID;
	private int quantity;
	private String status;
	private long lowDate;
	private long orderDate;
	private String supplierOrderID;			// missing table
	
	// relationships
	@OneToOne
	@JoinColumn(name="INVENTORYID")
	private Inventory inventory;
	
	public BackOrder() {} 
	public BackOrder(long backOrderID)
	{
		setBackOrderID(backOrderID);
	}
	public BackOrder(Inventory inventory, int quantity) {
		try
		{
			// Get the Order ID to uniquely identity this BackOrder.
//			IdGeneratorHome idGeneratorHome = (IdGeneratorHome) Util.getEJBLocalHome("java:comp/env/ejb/IdGenerator", com.ibm.websphere.samples.plantsbywebsphereejb.IdGeneratorHome.class);
//			IdGenerator idGenerator = idGeneratorHome.findByPrimaryKeyUpdate("BACKORDER");
//			int orderInt = idGenerator.nextId();
//			Util.debug("BackOrder.ejbCreate() - Setting backOrderID in BackOrder EJB to " + orderInt);
//			this.setBackOrderID(new Integer(orderInt).toString());
			Util.debug("BackOrder.ejbCreate() - Setting inventoryID in BackOrder EJB to " + inventory.getInventoryId());
			this.setInventory(inventory);
			Util.debug("BackOrder.ejbCreate() - Setting Quantity in BackOrder EJB to " + quantity);
			this.setQuantity(quantity);
			Util.debug("BackOrder.ejbCreate() - Setting STATUS_ORDERSTOCK in BackOrder EJB to " + Util.STATUS_ORDERSTOCK);
			this.setStatus(Util.STATUS_ORDERSTOCK);
			this.setLowDate(System.currentTimeMillis());
		}
		catch (Exception e)
		{
			Util.debug("BackOrder.ejbCreate() - Exception: " + e);
		}
	}
	
	public long getBackOrderID() {
		return backOrderID;
	}
	public void setBackOrderID(long backOrderID) {
		this.backOrderID = backOrderID;
	}
	public long getLowDate() {
		return lowDate;
	}
	public void setLowDate(long lowDate) {
		this.lowDate = lowDate;
	}
	public long getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(long orderDate) {
		this.orderDate = orderDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void increateQuantity(int delta) {
		if (!(status.equals(Util.STATUS_ORDERSTOCK)))
		{
			Util.debug("BackOrderStockBean.createBackOrder() - Backorders found but have already been ordered from the supplier");
			throw new RuntimeException("cannot increase order size for orders already in progress");
		}
		// Increase the BackOrder quantity for an existing Back Order.
		quantity = quantity + delta;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSupplierOrderID() {
		return supplierOrderID;
	}
	public void setSupplierOrderID(String supplierOrderID) {
		this.supplierOrderID = supplierOrderID;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
}
