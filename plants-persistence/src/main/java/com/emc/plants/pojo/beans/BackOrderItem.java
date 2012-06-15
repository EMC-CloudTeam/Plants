//
// "This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
// instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
// or for redistribution by customer, as part of such an application, in customer's own products. " 
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2003,2004
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.pojo.beans;

import com.emc.plants.persistence.BackOrder;
import com.emc.plants.persistence.Inventory;
import com.emc.plants.utils.Util;


/**
 * A class to hold a back order item's data.
 */
// TODO: lowp: merge with BackOrder
public class BackOrderItem implements java.io.Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private long backOrderID;
   private String name;
   private int quantity;
   private int inventoryQuantity;
   private String status;
   private long lowDate;
   private long orderDate;
   private String supplierOrderID;
   private Inventory inventory;
   
   /**
    * @see java.lang.Object#Object()
    */
   /** Default constructor. */
   public BackOrderItem()
   {
   }
   /**
    * Method BackOrderItem.
    * @param backOrderID
    * @param inventoryID
    * @param name
    * @param quantity
    * @param status
    */
   public BackOrderItem(long backOrderID, Inventory inventoryID, String name, int quantity, String status)
   {
      this.backOrderID = backOrderID;
      this.inventory = inventoryID;
      this.name = name;
      this.quantity = quantity;
      this.status = status;
   }
   /**
    * Method BackOrderItem.
    * @param backOrder
    */
   public BackOrderItem(BackOrder backOrder)
   {
      try
      {
         this.backOrderID = backOrder.getBackOrderID();
         this.inventory = backOrder.getInventory();
         this.quantity = backOrder.getQuantity();
         this.status = backOrder.getStatus();
         this.lowDate = backOrder.getLowDate();
         this.orderDate = backOrder.getOrderDate();
         this.supplierOrderID = backOrder.getSupplierOrderID();
      }
      catch (Exception e)
      {
         Util.debug("BackOrderItem - Exception: " + e);
      }
   }
   /**
    * Method getBackOrderID.
    * @return String
    */
   public long getBackOrderID()
   {
      return backOrderID;
   }
   /**
    * Method setBackOrderID.
    * @param backOrderID
    */
   public void setBackOrderID(long backOrderID)
   {
      this.backOrderID = backOrderID;
   }
   /**
    * Method getSupplierOrderID.
    * @return String
    */
   public String getSupplierOrderID()
   {
      return supplierOrderID;
   }
   /**
    * Method setSupplierOrderID.
    * @param supplierOrderID
    */
   public void setSupplierOrderID(String supplierOrderID)
   {
      this.supplierOrderID = supplierOrderID;
   }
   /**
    * Method setQuantity.
    * @param quantity
    */
   public void setQuantity(int quantity)
   {
      this.quantity = quantity;
   }
   /**
    * Method getInventoryID.
    * @return String
    */
   public Inventory getInventory()
   {
      return inventory;
   }
   /**
    * Method getName.
    * @return String
    */
   public String getName()
   {
      return name;
   }
   /**
    * Method setName.
    * @param name
    */
   public void setName(String name)
   {
      this.name = name;
   }
   /**
    * Method getQuantity.
    * @return int
    */
   public int getQuantity()
   {
      return quantity;
   }
   /**
    * Method getInventoryQuantity.
    * @return int
    */
   public int getInventoryQuantity()
   {
      return inventoryQuantity;
   }
   /**
    * Method setInventoryQuantity.
    * @param quantity
    */
   public void setInventoryQuantity(int quantity)
   {
      this.inventoryQuantity = quantity;
   }
   /**
    * Method getStatus.
    * @return String
    */
   public String getStatus()
   {
      return status;
   }
   /**
    * Method getLowDate.
    * @return long
    */
   public long getLowDate()
   {
      return lowDate;
   }
   /**
    * Method getOrderDate.
    * @return long
    */
   public long getOrderDate()
   {
      return orderDate;
   }
}
