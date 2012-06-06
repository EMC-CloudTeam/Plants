//
// "This program may be used, executed, copied, modified and distributed without royalty for the 
// purpose of developing, using, marketing, or distributing."
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2004
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.interfaces;
import java.rmi.RemoteException;
import java.util.Collection;

import com.emc.plants.pojo.beans.OrderInfo;
import com.emc.plants.pojo.beans.ShoppingCartContents;
import com.emc.plants.pojo.beans.ShoppingCartItem;



/**
 * Remote interface for ShoppingCart stateful session bean.
 */
//@Remote
public interface ShoppingCart
{
   /** 
    * Add an item to the cart.
    *
    * @param item Item to add to the cart.
    */
   public void addItem(ShoppingCartItem item);
   /** 
    * Remove an item from the cart.
    *
    * @param item Item to remove from cart.
    */
   public void removeItem(ShoppingCartItem item);
   /** 
    * Get the items in the shopping cart.
    *
    * @return A Vector of the items.
    */
   public Collection <ShoppingCartItem>getItems();
   /** 
    * Set the items in the shopping cart.
    *
    * @param items A Vector of the items.
    */
   public void setItems(Collection <ShoppingCartItem>items);
   /** 
    * Get the total cost of all items in the shopping cart.
    *
    * @return The total cost of all items in the shopping cart.
    */
   public float getTotalCost();
   /**
    * Get the contents of the shopping cart.
    *
    * @return The contents of the shopping cart.
    */
   public ShoppingCartContents getCartContents();
   public void setCartContents(ShoppingCartContents c);
   
   /**
    * Create an order with contents of a shopping cart.
    *
    * @param customerID customer's ID
    * @param billName billing name
    * @param billAddr1 billing address line 1
    * @param billAddr2 billing address line 2
    * @param billCity billing address city
    * @param billState billing address state
    * @param billZip billing address zip code
    * @param billPhone billing phone
    * @param shipName shippng name
    * @param shipAddr1 shippng address line 1
    * @param shipAddr2 shippng address line 2
    * @param shipCity shippng address city
    * @param shipState shippng address state
    * @param shipZip shippng address zip code
    * @param shipPhone shippng phone
    * @param creditCard credit card
    * @param ccNum credit card number
    * @param ccExpireMonth credit card expiration month
    * @param ccExpireYear credit card expiration year
    * @param cardHolder credit card holder name
    * @param shippingMethod index of shipping method used
    * @param items collection of StoreItems ordered
    * @return OrderInfo
    */
   public OrderInfo createOrder(
      String customerID,
      String billName,
      String billAddr1,
      String billAddr2,
      String billCity,
      String billState,
      String billZip,
      String billPhone,
      String shipName,
      String shipAddr1,
      String shipAddr2,
      String shipCity,
      String shipState,
      String shipZip,
      String shipPhone,
      String creditCard,
      String ccNum,
      String ccExpireMonth,
      String ccExpireYear,
      String cardHolder,
      int shippingMethod,
      Collection <ShoppingCartItem>items);
   /**
    * Method checkInventory.
    * @param items
    * @throws RemoteException
    */
   public void checkInventory(Collection <ShoppingCartItem>items);
   /**
    * Metho checkInventory.
    * @param si
    * @throws RemoteException
    */
   public void checkInventory(ShoppingCartItem si);
}
