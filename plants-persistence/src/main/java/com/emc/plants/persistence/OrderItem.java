//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2003,2003
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.emc.plants.utils.Util;

/**
 * Bean implementation class for Enterprise Bean: OrderItem
 */
@Entity(name="OrderItem")
@Table(name="ORDERITEM", schema="APP")
@NamedQueries({
	@NamedQuery(
		name="removeAllOrderItem",
		query="delete from OrderItem")
})
public class OrderItem
{
	/**
	 * Composite Key class for Entity Bean: OrderItem
	 * 
	 * Key consists of essentially two foreign key relations, but is mapped
	 * as foreign keys.
	 */
	@Embeddable
	public static class PK implements java.io.Serializable {
		static final long serialVersionUID = 3206093459760846163L;
		@Column(name="INVENTORYID")
		public String inventoryID;
		@Column(name="ORDER_ORDERID")
		public long order_orderID;
		
		public PK() {  Util.debug("OrderItem.PK()"); }			
		
		public PK(String inventoryID,	long argOrder) {
			Util.debug("OrderItem.PK() inventoryID=" + inventoryID + "=");
			Util.debug("OrderItem.PK() orderID=" + argOrder + "=");
			this.inventoryID = inventoryID;
			this.order_orderID = argOrder;
			// TODO: this constructor really needs to use Inventory and Order as args and maintain the Inventory and Order relations
		}
		/**
		 * Returns true if both keys are equal.
		 */
		public boolean equals(java.lang.Object otherKey) {
			if (otherKey instanceof PK) {
				PK o = (PK) otherKey;
				return (
						(this.inventoryID.equals(o.inventoryID))
						&& (this.order_orderID == o.order_orderID));
			}
			return false;
		}
		/**
		 * Returns the hash code for the key.
		 */
		public int hashCode() {
			Util.debug("OrderItem.PK.hashCode() inventoryID=" + inventoryID + "=");
			Util.debug("OrderItem.PK.hashCode() orderID=" + order_orderID + "=");
			
			return (inventoryID.hashCode() + (int)order_orderID);
		}
	}
	
	@SuppressWarnings("unused")
	@EmbeddedId
	private OrderItem.PK id;
	private String name;
	private String pkginfo;
	private float price;
	private float cost;
	private int category;
	private int quantity;
	private String sellDate;
	//private String inventoryId;
	
	@ManyToOne
	@JoinColumn(name="INVENTORYID" , insertable=false , updatable=false)
	private Inventory inventory;
	@ManyToOne
	@JoinColumn(name="ORDER_ORDERID" , insertable=false , updatable=false)
	private Order order;
	
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPkginfo() {
		return pkginfo;
	}
	public void setPkginfo(String pkginfo) {
		this.pkginfo = pkginfo;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSellDate() {
		return sellDate;
	}
	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}
	public OrderItem() {} 
	public OrderItem(Inventory inv) {
		Util.debug("OrderItem(inv) - id = "+inv.getInventoryId());
		//setInventoryId(inv.getInventoryId());
		inventory = inv;
		name = inv.getName();
		pkginfo = inv.getPkginfo();
		price = inv.getPrice();
		cost = inv.getCost();
		category = inv.getCategory();	
	}

	public OrderItem(Order order, String orderID, 
			Inventory inv, java.lang.String name,
			java.lang.String pkginfo, float price, float cost,
			int quantity, int category, 
			java.lang.String sellDate)
	{
		Util.debug("OrderItem(etc.)");		
		inventory = inv;
		//setInventoryId(inv.getInventoryId());
		setName(name);
		setPkginfo(pkginfo);
		setPrice(price);
		setCost(cost);
		setQuantity(quantity);
		setCategory(category);
		setSellDate(sellDate);
		setOrder(order);	
		id=new OrderItem.PK(inv.getInventoryId(),order.getOrderID());
	}
	
	/*
	 * updates the primary key field with the composite orderId+inventoryId
	 */
	public void updatePK(String inventoryId){
		id=new OrderItem.PK(inventoryId,order.getOrderID());
	}

	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inv) {
		this.inventory = inv;
	}
	public Order getOrder() {
		return order;
	}
	/**
	 * Sets the order for this item
	 * Also updates the sellDate
	 * @param order
	 */
	public void setOrder(Order order) {
		this.order = order;
		this.sellDate=order.getSellDate();		
	}
	
	/*public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}*/

} 