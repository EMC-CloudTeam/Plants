//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.interfaces;


import java.util.Vector;

import com.emc.plants.persistence.Inventory;
import com.emc.plants.pojo.beans.StoreItem;

//import javax.ejb.Remote;

/**
 * Remote interface for Catalog stateless session bean.
 */
//@Remote
@SuppressWarnings("unchecked")
public interface Catalog
{
   /**
    * Get all inventory items.
    *
    * @return Collection of Inventory.
    */
   public Vector getItems();

   /**
    * Get all inventory items for the given category.
    *
    * @param category - category of items desired.
    * @return Collection of Inventory.
    */
   public Vector getItemsByCategory(int category);

   /**
    * Get inventory items that contain a given String within their names.
    *
    * @param name String to search names for.
    * @return A Collection of Inventory that match.
    */
   public Vector getItemsLikeName(String name);

   /**
    * Get the StoreItem for the given ID.
    *
    * @param inventoryID - ID of the Inventory item desired.
    * @return StoreItem
    */
   public StoreItem getItem(String inventoryID);
   
   /**
    * Get the Inventory item for the given ID.
    *
    * @param inventoryID - ID of the Inventory item desired.
    * @return Inventory
    */
   public Inventory getItemInventory(String inventoryID);

   /**
    * Add an inventory item.
    *
    * @param item The ShoppingCartItem to add.
    * @return True, if item added.
    */
   public boolean addItem(Inventory item);
   
   /**
    * Add an inventory item.
    *
    * @param item The StoreItem to add.
    * @return True, if item added.
    */
   public boolean addItem(StoreItem item);

   /**
    * Delete an inventory item.
    *
    * @param inventoryID The ID of the inventory item to delete.
    * @return True, if item deleted.
    */
   public boolean deleteItem(String inventoryID);

   /**
    * Set the inventory item's name.
    *
    * @param inventoryID The inventory item's ID.
    * @param name The inventory item's new name.
    */
   public void setItemName(String inventoryID, String name);
   /**
    * Set the inventory item's heading.
    *
    * @param inventoryID The inventory item's ID.
    * @param heading The inventory item's new heading.
    */
   public void setItemHeading(String inventoryID, String heading);
   /**
    * Set the inventory item's description.
    *
    * @param inventoryID The inventory item's ID.
    * @param desc The inventory item's new description.
    */
   public void setItemDescription(String inventoryID, String desc);
   /**
    * Set the inventory item's package information.
    *
    * @param inventoryID The inventory item's ID.
    * @param pkginfo The inventory item's new package information.
    */
   public void setItemPkginfo(String inventoryID, String pkginfo);

   /**
    * Set the inventory item's category.
    *
    * @param inventoryID The inventory item's ID.
    * @param category The inventory item's new category.
    */
   public void setItemCategory(String inventoryID, int category);
 
   /**
    * Set the inventory item's image file name.
    *
    * @param inventoryID The inventory item's ID.
    * @param imageName The inventory item's new image file name.
    */
   public void setItemImageFileName(String inventoryID, String imageName);

   /** 
    * Get the image for the inventory item.
    * @param inventoryID The id of the inventory item wanted.
    * @return Buffer containing the image.
    */
   public byte[] getItemImageBytes(String inventoryID);

   /** 
    * Set the image for the inventory item. 
    * @param inventoryID The id of the inventory item wanted.
    * @param imgbytes Buffer containing the image.
    */
   public void setItemImageBytes(String inventoryID, byte[] imgbytes);

   /**
    * Set the inventory item's price.
    *
    * @param inventoryID The inventory item's ID.
    * @param price The inventory item's new price.
    */
   public void setItemPrice(String inventoryID, float price);

   /**
    * Set the inventory item's cost.
    *
    * @param inventoryID The inventory item's ID.
    * @param cost The inventory item's new cost.
    */
   public void setItemCost(String inventoryID, float cost);

   /**
    * Set the inventory item's quantity.
    *
    * @param inventoryID The inventory item's ID.
    * @param amount The inventory item's new quantity.
    */
   public void setItemQuantity(String inventoryID, int amount);

   /**
    * Set the inventory item's notes.
    *
    * @param inventoryID The inventory item's ID.
    * @param note The inventory item's new notes.
    */
   public void setItemNotes(String inventoryID, String note);

   /**
    * Set the inventory item's access availability.
    *
    * @param inventoryID The inventory item's ID.
    * @param isPublic True, if this item can be viewed by the public.
    */
   public void setItemPrivacy(String inventoryID, boolean isPublic);

}

