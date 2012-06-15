//
// "This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
// instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
// or for redistribution by customer, as part of such an application, in customer's own products. " 
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2003,2004
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.interfaces;
import java.rmi.RemoteException;
import java.util.Collection;

import com.emc.plants.pojo.beans.BackOrderItem;

//import javax.ejb.Local;
/**
 * Local interface for Enterprise Bean: BackOrderStock
 */
//@Local
@SuppressWarnings("unchecked")
public interface BackOrderStock
{
   /**
    * Method receiveConfirmation.
    * @param backOrderID
    */
   public int receiveConfirmation(long backOrderID);
   /**
    * Method orderStock.
    * @param backOrderID
    * @param quantity
    */
   public void orderStock(long backOrderID, int quantity);
   /**
    * Method updateStock.
    * @param backOrderID
    * @param quantity
    */
   public void updateStock(long backOrderID, int quantity);
   /**
    * @param backOrderID
    */
   public void abortorderStock(long backOrderID);
   /**
    * Method getBackOrderID.
    * @param backOrderID
    * @return String
    */
   public long getBackOrderID(long backOrderID);
   /**
    * Method getBackOrderInventoryID.
    * @param backOrderID
    * @return String
    * @throws RemoteException
    */
   public String getBackOrderInventoryID(long backOrderID);
   /**
    * Method getSupplierOrderID.
    * @param backOrderID
    * @return String
    */
   public String getSupplierOrderID(long backOrderID);
   /**
    * Method getBackOrderQuantity.
    * @param backOrderID
    * @return int
    */
   /**
    * getBackOrderQuantity
    */
   public int getBackOrderQuantity(long backOrderID);
   /**
    * Method setSupplierOrderID.
    * @param backOrderID
    * @param supplierOrderID
    */
   public void setSupplierOrderID(long backOrderID, String supplierOrderID);
   /**
    * Method setBackOrderQuantity.
    * @param backOrderID
    * @param quantity
    */
   /**
    * setBackOrderQuantity
    */
   public void setBackOrderQuantity(long backOrderID, int quantity);
   /**
    * Method getBackOrderStatus.
    * @param backOrderID
    * @return String
    */
   /**
    * getBackOrderStatus
    */
   public String getBackOrderStatus(long backOrderID);
   /**
    * Method setBackOrderStatus.
    * @param backOrderID
    * @param Status
    */
   /**
    * setBackOrderStatus
    */
   public void setBackOrderStatus(long backOrderID, java.lang.String Status);
   /**
    * Method setBackOrderOrderDate.
    * @param backOrderID
    */
   public void setBackOrderOrderDate(long backOrderID);
   /**
    * Method deleteBackOrder.
    * @param backOrderID
    */
   /**
    * deleteBackOrder
    */
   public void deleteBackOrder(long backOrderID);
   /**
    * Method createBackOrder.
    * @param inventoryID
    * @param amountToOrder
    * @param maximumItems
    */
   public void createBackOrder(String inventoryID, int amountToOrder, int maximumItems);
   /**
    * Method findBackOrderItems.
    * @return Vector
    */
   public Collection findBackOrderItems();
   /**
    * Method findByID.
    * @param backOrderID
    * @return BackOrderItem
    */
   public BackOrderItem findByID(long backOrderID);
}
