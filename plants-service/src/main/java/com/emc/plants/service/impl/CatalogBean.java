//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.impl;


import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emc.plants.persistence.Inventory;
import com.emc.plants.pojo.beans.StoreItem;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.utils.Util;

/**
 * CatalogBean is the implementation class for the {@link Catalog} stateless session
 * EJB.  CatalogBean implements each of the business methods in the <code>Catalog</code>
 * EJB remote interface and each of the EJB lifecycle methods in the javax.ejb.SessionBean
 * interface.
 * 
 * @see Catalog
 */
@SuppressWarnings("unchecked")
@Repository("catalog")
@Transactional
public class CatalogBean implements Catalog
{
	@PersistenceContext(unitName="PBW")
	EntityManager em;

	
	/**
	 * Get all inventory items.
	 *
	 * @return Vector of Inventorys.
	 */	
	public Vector getItems()
	{
		Vector items = new Vector();
		int count = Util.getCategoryStrings().length;
		for (int i = 0; i < count; i++)
		{
			items.addAll(getItemsByCategory(i));
		}
		//The return type must be Vector because the PBW client ActiveX sample requires Vector
		return items;
	}
	
	/**
	 * Get all inventory items for the given category.
	 *
	 * @param category of items desired.
	 * @return Vector of Inventory.
	 */
	public Vector getItemsByCategory(int category)
	{
		/*
		Vector items = new Vector();
		 InventoryHome invHome = (InventoryHome) Util.getEJBLocalHome("java:comp/env/ejb/Inventory",
		 com.ibm.websphere.samples.pbwjpa.InventoryHome.class);
		 Collection invCollection = invHome.findByCategory(category);
		 
		 Iterator invs = invCollection.iterator();
		 Inventory inv;
		 while (invs.hasNext())
		 {
		 inv = (Inventory) invs.next();
		 items.addElement( new ShoppingCartItem(inv) );
		 }
		 */
		Query q = em.createNamedQuery("getItemsByCategory");
		q.setParameter("category", category);
		//The return type must be Vector because the PBW client ActiveX sample requires Vector
		return new Vector(q.getResultList());
	}
	
	/**
	 * Get inventory items that contain a given String within their names.
	 *
	 * @param name String to search names for.
	 * @return A Vector of Inventorys that match.
	 */
	public Vector getItemsLikeName(String name)
	{
		/*
		Vector items = new Vector();
		 InventoryHome invHome = (InventoryHome) Util.getEJBLocalHome("java:comp/env/ejb/Inventory",
		 com.ibm.websphere.samples.pbwjpa.InventoryHome.class);
		 Collection invCollection = invHome.findByNameLikeness('%' + name + '%');
		 
		 Iterator invs = invCollection.iterator();
		 Inventory inv;
		 while (invs.hasNext())
		 {
		 inv = (Inventory) invs.next();
		 items.addElement( new ShoppingCartItem(inv) );
		 }
		 */
		Query q = em.createNamedQuery("getItemsLikeName");
		q.setParameter("name", '%' + name + '%');
		//The return type must be Vector because the PBW client ActiveX sample requires Vector
		return new Vector(q.getResultList());
	}
	
	/**
	 * Get the StoreItem for the given ID.
	 *
	 * @param inventoryID - ID of the Inventory item desired.
	 * @return StoreItem
	 */
	public StoreItem getItem(String inventoryID)
	{
		return new StoreItem(getItemInventory(inventoryID));
	}
	
	/**
	 * Get the Inventory item for the given ID.
	 *
	 * @param inventoryID - ID of the Inventory item desired.
	 * @return Inventory
	 */
	public Inventory getItemInventory(String inventoryID)
	{
		Inventory si = null;
		/*
		 InventoryHome invHome = (InventoryHome) Util.getEJBLocalHome("java:comp/env/ejb/Inventory",
		 com.ibm.websphere.samples.pbwjpa.InventoryHome.class);
		 Inventory inv = invHome.findByPrimaryKey(new InventoryKey(inventoryID));
		 si = new ShoppingCartItem(inv);
		 */
		si = em.find(Inventory.class, inventoryID);
		return si;
	}
	
	/**
	 * Add an inventory item.
	 *
	 * @param item The Inventory to add.
	 * @return True, if item added.
	 */
	public boolean addItem(Inventory item)
	{
		boolean retval = true;
		/*
		 InventoryHome invHome = (InventoryHome) Util.getEJBLocalHome("java:comp/env/ejb/Inventory",
		 com.ibm.websphere.samples.pbwjpa.InventoryHome.class);
		 Inventory inv = invHome.create(item);
		 
		 // If inventory is not null, then return TRUE to indicate add succeeded.
		  if (inv != null)
		  retval = true;
		  */
		em.persist(item);
		return retval;
	}
	
	/**
	 * Add an StoreItem item (same as Inventory item).
	 *
	 * @param item The StoreItem to add.
	 * @return True, if item added.
	 */
	public boolean addItem(StoreItem item)
	{
		return addItem(new Inventory(item));
	}
	
	/**
	 * Delete an inventory item.
	 *
	 * @param inventoryID The ID of the inventory item to delete.
	 * @return True, if item deleted.
	 */
	public boolean deleteItem(String inventoryID)
	{
		boolean retval = true;
		/*
		 InventoryHome invHome = (InventoryHome) Util.getEJBLocalHome("java:comp/env/ejb/Inventory",
		 com.ibm.websphere.samples.pbwjpa.InventoryHome.class);
		 Inventory inv = invHome.findByPrimaryKeyUpdate(inventoryID);
		 inv.remove();
		 retval = true;
		 */
		em.remove(em.find(Inventory.class, inventoryID));
		return retval;
	}
	
	/**
	 * Set the inventory item's name.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param name The inventory item's new name.
	 */
	public void setItemName(String inventoryID, String name) 
	{ 
		// TODO: lowp: convert all this stupid fine grained update to coarse grained
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setName(name);
		}
	}
	
	/**
	 * Set the inventory item's heading.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param heading The inventory item's new heading.
	 */
	public void setItemHeading(String inventoryID, String heading)
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setHeading(heading);
		}
	}
	
	/**
	 * Set the inventory item's description.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param desc The inventory item's new description.
	 */
	public void setItemDescription(String inventoryID, String desc) 
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setDescription(desc);
		}
	}
	
	/**
	 * Set the inventory item's package information.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param pkginfo The inventory item's new package information.
	 */
	public void setItemPkginfo(String inventoryID, String pkginfo) 
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setPkginfo(pkginfo);
		}
	}
	
	/**
	 * Set the inventory item's category.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param category The inventory item's new category.
	 */
	public void setItemCategory(String inventoryID, int category)
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setCategory(category);
		}
	}
	
	/**
	 * Set the inventory item's image file name.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param imageName The inventory item's new image file name.
	 */
	public void setItemImageFileName(String inventoryID, String imageName)
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setImage(imageName);
		}
	}
	
	/** 
	 * Get the image for the inventory item.
	 * @param inventoryID The id of the inventory item wanted.
	 * @return Buffer containing the image.
	 */
	public byte[] getItemImageBytes(String inventoryID)
	{
		byte[] retval = null;
		Inventory inv = getInv(inventoryID);
		if (inv != null)
		{
			retval = inv.getImgbytes();
		}
		
		return retval;
	}
	
	/** 
	 * Set the image for the inventory item. 
	 * @param inventoryID The id of the inventory item wanted.
	 * @param imgbytes Buffer containing the image.
	 */
	@Transactional
	public void setItemImageBytes(String inventoryID, byte[] imgbytes)
	{
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setImgbytes(imgbytes);
		}
	}
	
	/**
	 * Set the inventory item's price.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param price The inventory item's new price.
	 */
	@Transactional
	public void setItemPrice(String inventoryID, float price)
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setPrice(price);
		}
	}
	
	/**
	 * Set the inventory item's cost.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param cost The inventory item's new cost.
	 */
	@Transactional
	public void setItemCost(String inventoryID, float cost)
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setCost(cost);
		}
	}
	
	/**
	 * Set the inventory item's quantity.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param quantity The inventory item's new quantity.
	 */
	@Transactional
	public void setItemQuantity(String inventoryID, int quantity)
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setQuantity(quantity);
		}
	}
	
	/**
	 * Set the inventory item's notes.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param notes The inventory item's new notes.
	 */
	@Transactional
	public void setItemNotes(String inventoryID, String notes)
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setNotes(notes);
		}
	}
	
	/**
	 * Set the inventory item's access availability.
	 *
	 * @param inventoryID The inventory item's ID.
	 * @param isPublic True, if this item can be viewed by the public.
	 */
	@Transactional
	public void setItemPrivacy(String inventoryID, boolean isPublic)
	{ 
		Inventory inv = getInvUpdate(inventoryID);
		if (inv != null)
		{
			inv.setPrivacy(isPublic);
		}
	}
	
	/**
	 * Get a remote Inventory object.
	 *
	 * @param inventoryID The id of the inventory item wanted.
	 * @return Reference to the remote Inventory object.
	 */
	private Inventory getInv(String inventoryID)
	{
		return em.find(Inventory.class, inventoryID);
	}
	
	/**
	 * Get a remote Inventory object to Update.
	 *
	 * @param inventoryID The id of the inventory item wanted.
	 * @return Reference to the remote Inventory object.
	 */
	private Inventory getInvUpdate(String inventoryID)
	{
		Inventory inv = null;
		/*
		 InventoryHome invHome = (InventoryHome) Util.getEJBLocalHome("java:comp/env/ejb/Inventory",
		 com.ibm.websphere.samples.pbwjpa.InventoryHome.class);
		 inv = invHome.findByPrimaryKeyUpdate(inventoryID);
		 */
		// TODO: lowp: eventually replace with find for update hint
		inv = em.find(Inventory.class, inventoryID);
		em.lock(inv, LockModeType.WRITE);
		em.refresh(inv);
		return inv;
	}
	
	
}

