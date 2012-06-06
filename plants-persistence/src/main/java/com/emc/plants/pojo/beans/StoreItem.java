//
// "This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
// instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
// or for redistribution by customer, as part of such an application, in customer's own products. " 
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2008
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.pojo.beans;

import com.emc.plants.persistence.Inventory;

/**
 * A class to hold a store item's data.
 */
public class StoreItem implements java.io.Serializable
{

	private static final long serialVersionUID = 1970881723120987926L;
	private String id;
   private String name;
   private String heading;
   private String desc;
   private String pkginfo;
   private String image;
   private float price;
   private float cost;
   private int quantity;
   private int category;
   private String notes;
   private boolean isPublic;
   
   /** Default constructor. */
   public StoreItem() { }

   /** Constructor of store item
    * @param id - id of this store item.
    * @param name - name of this store item.
    * @param pkginfo - package info of this store item.
    * @param price - price of this store item.
    * @param cost - cost of this store item.
    * @param category - category of this store item.
    * @param quantity - quantity of this store item.
    */
   public StoreItem(String id, String name, String pkginfo,
                    float price, float cost, int category, 
                    int quantity)
   {
      this.id = id;
      this.name = name;
      this.pkginfo = pkginfo;
      this.price = price;
      this.cost = cost;
      this.category = category;
      this.quantity = quantity;
   }

   /** Constructor of store item
    * @param id - id of this store item.
    * @param name - name of this store item.
    * @param heading - description heading of this store item.
    * @param desc - description of this store item.
    * @param pkginfo - package info of this store item.
    * @param image - image of this store item.
    * @param price - price of this store item.
    * @param cost - cost of this store item.
    * @param quantity - quantity of this store item.
    * @param category - category of this store item.
    * @param notes - notes of this store item.
    * @param isPublic - isPublic flag of this store item.
    */
   public StoreItem(String id, String name, String heading, 
                    String desc, String pkginfo, String image, 
                    float price, float cost, int quantity, 
                    int category, String notes, boolean isPublic)
   {
      this.id = id;
      this.name = name;
      this.heading = heading;
      this.desc = desc;
      this.pkginfo = pkginfo;
      this.image = image;
      this.price = price;
      this.cost = cost;
      this.quantity = quantity;
      this.category = category;
      this.notes = notes;
      this.isPublic = isPublic;
   }

   /**
    * Constructor to convert an Inventory object to StoreItem.
    * @param inv - Inventory item
    */
   public StoreItem(Inventory inv)
   {
      this.id = inv.getInventoryId();
      this.name = inv.getName();
      this.heading = inv.getHeading();
      this.desc = inv.getDescription();
      this.pkginfo = inv.getPkginfo();
      this.image = inv.getImage();
      this.price = inv.getPrice();
      this.cost = inv.getCost();
      this.category = inv.getCategory();
      this.quantity = inv.getQuantity();
      this.notes = inv.getNotes();
      this.isPublic = inv.isPublic();
   }

   /** Compares equality of store items. */
   public boolean equals(StoreItem si)
   {
      return si.getID().equals(this.id);
   }

   /** Get the ID of this store item. */
   public String getID() { return id; }
   /** Get the name of this store item. */
   public String getName() { return name; }
   /** Get the description heading of this store item. */
   public String getHeading() { return heading; }
   /** Get the description of this store item. */
   public String getDescription() { return desc; }
   /** Get the package info of this store item. */
   public String getPkginfo() { return pkginfo; }
   /** Get the image file of this store item. */
   public String getImage() { return image; }
   /** Get the price of this store item. */
   public float getPrice() { return price; }
   /** Get the cost of this store item. */
   public float getCost() { return cost; }
   /** Get the quantity of this store item. */
   public int getQuantity() { return quantity; }
   /** Get the category of this store item. */
   public int getCategory() { return category; }
   /** Get the notes of this store item. */
   public String getNotes() { return notes; }
   /** Is this store item viewable by the public? */
   public boolean isPublic() { return isPublic; }

   /** Set the quantity of this store item. */
   public void setQuantity(int quantity) { this.quantity = quantity; }

}
