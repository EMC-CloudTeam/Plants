//
// "This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
// instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
// or for redistribution by customer, as part of such an application, in customer's own products. " 
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2003
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.pojo.beans;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A class to hold a shopping cart's contents.
 */
public class ShoppingCartContents implements java.io.Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Hashtable<String, Integer> table = null;

   public ShoppingCartContents()
   {
      table = new Hashtable<String, Integer>();
   }

   /** Add the item to the shopping cart. */
   public void addItem(ShoppingCartItem si)
   {
      table.put(si.getID(), new Integer(si.getQuantity()));
   }

   /** Update the item in the shopping cart. */
   public void updateItem(ShoppingCartItem si)
   {
      table.put(si.getID(), new Integer(si.getQuantity()));
   }

   /** Remove the item from the shopping cart. */
   public void removeItem(ShoppingCartItem si)
   {
      table.remove(si.getID());
   }

   /** 
    * Return the number of items in the cart.
    *
    * @return The number of items in the cart.
    */
   public int size()
   {
      return table.size();
   }

   /**
    * Return the inventory ID at the index given.  The first element
    * is at index 0, the second at index 1, and so on.
    *
    * @return The inventory ID at the index, or NULL if not present.
    */
   public String getInventoryID(int index)
   {
      String retval = null;
      String inventoryID;
      int cnt = 0;
      for (Enumeration <String>myEnum = table.keys(); myEnum.hasMoreElements(); cnt++)
      {
         inventoryID = (String) myEnum.nextElement();
         if (index == cnt)
         {
            retval = inventoryID;
            break;
         }
      }
      return retval;
   }

   /** 
    * Return the quantity for the inventory ID given.
    *
    * @return The quantity for the inventory ID given..
    *
    */
   public int getQuantity(String inventoryID)
   {
      Integer quantity = (Integer) table.get(inventoryID);

      if (quantity == null)
         return 0;
      else
         return quantity.intValue();
   }

}
