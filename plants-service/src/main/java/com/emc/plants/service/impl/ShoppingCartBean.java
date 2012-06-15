//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.impl;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emc.plants.persistence.BackOrder;
import com.emc.plants.persistence.Customer;
import com.emc.plants.persistence.Inventory;
import com.emc.plants.persistence.Order;
import com.emc.plants.persistence.OrderItem;
import com.emc.plants.pojo.beans.OrderInfo;
import com.emc.plants.pojo.beans.ShoppingCartContents;
import com.emc.plants.pojo.beans.ShoppingCartItem;
import com.emc.plants.service.interfaces.ShoppingCart;
import com.emc.plants.utils.Util;

/**
 * ShoppingCartBean is the implementation class for the {@link ShoppingCart} stateful session
 * EJB.  ShoppingCartBean implements each of the business methods in the <code>ShoppingCart</code>
 * EJB local interface and each of the EJB lifecycle methods in the javax.ejb.SessionBean
 * interface.
 * 
 * @see ShoppingCart
 */
//@Stateful(name="ShoppingCart")
@Repository("shopping")
@Scope(value="session")
@Transactional
public class ShoppingCartBean implements ShoppingCart
{

	/*@Autowired
	private EntityManagerFactory entityManagerFactory;*/
	
	private EntityManager em;
	
	
	@PersistenceContext(unitName="PBW")
	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}

	/*public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory =entityManagerFactory;
	}*/
	
	private ArrayList<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
	
	/**
	 * Get the inventory item.
	 *
	 * @param id of inventory item.
	 * @return an inventory bean.
	 */
	private Inventory getInventoryItem(String inventoryID)
	{
		Inventory inv = null;
		/*      // Get the Inventory home.
		 InventoryHome inventoryHome = (InventoryHome) Util.getEJBLocalHome("java:comp/env/ejb/Inventory", InventoryHome.class);
		 try
		 {
		 inv = inventoryHome.findByPrimaryKeyUpdate(inventoryID);
		 }
		 catch (FinderException e)
		 {
		 Util.debug("ShoppingCartBean.getInventoryItem() - Exception: " + e);
		 }
		 */
		//EntityManager em = entityManagerFactory.createEntityManager();
		inv = em.find(Inventory.class, inventoryID);
		return inv;
	}
	/**
	 * Create a shopping cart.
	 *
	 * @param cartContents Contents to populate cart with.
	 */
	public void setCartContents(ShoppingCartContents cartContents)
	{
		//EntityManager em = entityManagerFactory.createEntityManager();
		items = new ArrayList<ShoppingCartItem>();
		int qty;
		String inventoryID;
		ShoppingCartItem si;
		Inventory inv;
		for (int i = 0; i < cartContents.size(); i++)
		{
			inventoryID = cartContents.getInventoryID(i);
			qty = cartContents.getQuantity(inventoryID);
			// Get the Inventory home.
			/*
			InventoryHome inventoryHome = (InventoryHome) Util.getEJBLocalHome("java:comp/env/ejb/Inventory", com.ibm.websphere.samples.pbwjpa.InventoryHome.class);
			try
			{ */
			// inv = inventoryHome.findByPrimaryKey(new InventoryKey(inventoryID));
			inv = em.find(Inventory.class, inventoryID);
			// clone so we can use Qty as qty to purchase, not inventory in stock
			si = new ShoppingCartItem(inv);
			si.setQuantity(qty);
			addItem(si);
			/* }
			 */
		}
	}
	
	/** 
	 * Add an item to the cart.
	 *
	 * @param new_item Item to add to the cart.
	 */
	public void addItem(ShoppingCartItem new_item)
	{
		boolean added = false;
		ShoppingCartItem old_item;
		// If the same item is already in the cart, just increase the quantity.
		for (int i = 0; i < items.size(); i++)
		{
			old_item = (ShoppingCartItem) items.get(i);
			if (new_item.equals(old_item))
			{
				old_item.setQuantity(old_item.getQuantity() + new_item.getQuantity());
				added = true;
				break;
			}
		}
		// Add this item to shopping cart, if it is a brand new item.
		if (!added)
			items.add(new_item);
	}
	/** 
	 * Remove an item from the cart.
	 *
	 * @param item Item to remove from cart.
	 */
	public void removeItem(ShoppingCartItem item)
	{
		for (Object obj : items)
		{
			ShoppingCartItem si = (ShoppingCartItem) obj;
			if (item.equals(si))
			{
				items.remove(si);
				break;
			}
		}
	}
	/** 
	 * Get the items in the shopping cart.
	 *
	 * @return A Collection of ShoppingCartItems.
	 */
	public Collection <ShoppingCartItem>getItems()
	{
		return items;
	}
	/** 
	 * Set the items in the shopping cart.
	 *
	 * @param items A Vector of ShoppingCartItem's.
	 */
	@SuppressWarnings("unchecked")
	public void setItems(Collection items)
	{
		this.items = new ArrayList<ShoppingCartItem>(items);
	}
	/** 
	 * Get the total cost of all items in the shopping cart.
	 *
	 * @return The total cost of all items in the shopping cart.
	 */
	public float getTotalCost()
	{
		float total = 0.0f;
		ShoppingCartItem si;
		for (Object o : items)
		{
			si = (ShoppingCartItem) o;
			total += si.getPrice() * si.getQuantity();
		}
		return total;
	}
	/** 
	 * Method checkInventory.
	 * Check the inventory level of a store item.
	 * Order additional inventory when necessary.
	 *
	 * @param si - Store item
	 */
	public void checkInventory(ShoppingCartItem si)
	{
		Util.debug("ShoppingCart.checkInventory() - checking Inventory quantity of item: " + si.getID());
		Inventory inv = getInventoryItem(si.getID());
		
		/**
		 * Decrease the quantity of this inventory item.
		 * @param quantity The number to decrease the inventory by.
		 * @return The number of inventory items removed.
		 */
		int quantity=si.getQuantity();
		int minimumItems = inv.getMinThreshold();
			
		int amountToOrder = 0;
		Util.debug("ShoppingCartBean:checkInventory() - Decreasing inventory item " +inv.getInventoryId());
		int quantityNotFilled = 0;
		if (inv.getQuantity() < 1)
		{
			quantityNotFilled = quantity;
		}
		else if (inv.getQuantity() < quantity)
		{
			quantityNotFilled = quantity - inv.getQuantity();
		}
			
		// When quantity becomes < 0, this will be to determine the
		// quantity of unfilled orders due to insufficient stock.
		inv.setQuantity(inv.getQuantity() - quantity);
			
		//  Check to see if more inventory needs to be ordered from the supplier
		//  based on a set minimum Threshold
		if (inv.getQuantity() < minimumItems)
		{
			// Calculate the amount of stock to order from the supplier
			// to get the inventory up to the maximum.
			amountToOrder = quantityNotFilled;
			backOrder(inv, amountToOrder);
		}
			
		
	}
	
	/**
	 * Create a BackOrder of this inventory item.
	 * @param quantity The number of the inventory item to be backordered
	 */
	private void backOrder(Inventory inv, int amountToOrder)
	{
		//EntityManager em = entityManagerFactory.createEntityManager();
	//	BackOrder b=em.find(BackOrder.class, inv.getBackOrder().getBackOrderID());
        BackOrder b = inv.getBackOrder() ;
		if (b == null) {
			//create a new backorder if none exists
			BackOrder newBO=new BackOrder(inv, amountToOrder);
            System.out.println("coming to back order 1");
			em.persist(newBO);
			em.flush();
			inv.setBackOrder(newBO);
		} else {
			//update the backorder with the new quantity			
			int quantity=b.getQuantity();
			quantity+=amountToOrder;
            System.out.println("coming to back order 11");
			em.lock(b, LockModeType.WRITE);
			em.refresh(b);
			b.setQuantity(quantity);
			em.flush();
			inv.setBackOrder(b);
		}
	}
	/** 
	 * Method checkInventory.
	 * Check the inventory level all items in the shopping cart.
	 * Order additional inventory when necessary
	 *
	 * @param items
	 */
	public void checkInventory(Collection <ShoppingCartItem>items)
	{
		ShoppingCartItem si;
		for (Object o : items)
		{
			si = (ShoppingCartItem) o;
			checkInventory(si);			
		}
	}
	/**
	 * Get the contents of the shopping cart.
	 *
	 * @return The contents of the shopping cart.
	 */
	public ShoppingCartContents getCartContents()
	{
		ShoppingCartContents cartContents = new ShoppingCartContents();
		// Fill it with data.
		for (int i = 0; i < items.size(); i++)
		{
			cartContents.addItem((ShoppingCartItem) items.get(i));
		}
		return cartContents;
	}
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
	 * @param shippingMethod int of shipping method used
	 * @param items vector of StoreItems ordered
	 * @return OrderInfo
	 */
	@Transactional
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
			Collection <ShoppingCartItem>items)
	{
		Order order = null;
		/*
		 try
		 {
		 OrderHome orderHome = (OrderHome) Util.getEJBLocalHome("java:comp/env/ejb/Order", OrderHome.class);
		 Util.debug("ShoppingCartBean.createOrder:  Creating Order");
		 order = orderHome.create(customerID, billName, billAddr1, billAddr2, billCity, billState, billZip, 
		 billPhone, shipName, shipAddr1, shipAddr2, shipCity, shipState, shipZip, shipPhone, creditCard, 
		 ccNum, ccExpireMonth, ccExpireYear, cardHolder, shippingMethod, items);
		 */
		Collection<OrderItem> orderitems = new ArrayList<OrderItem>();
		//EntityManager em = entityManagerFactory.createEntityManager();
		for (Object o : items) {
			ShoppingCartItem si = (ShoppingCartItem) o;
			Inventory inv = em.find(Inventory.class, si.getID());
			OrderItem oi = new OrderItem(inv);
			oi.setQuantity(si.getQuantity());
			orderitems.add(oi); 
		}
		Customer c = em.find(Customer.class, customerID);
		order = new Order(c, billName, billAddr1, billAddr2, billCity, billState, billZip, billPhone,
				shipName, shipAddr1, shipAddr2, shipCity, shipState, shipZip, shipPhone, creditCard,
				ccNum, ccExpireMonth, ccExpireYear, cardHolder, shippingMethod, orderitems);	
		
		//em.getTransaction().begin();
		em.persist(order);
		
		System.out.println("Order persist success!");
		em.flush();
		//em.getTransaction().commit();
		
		//em.getTransaction().begin();
		//store the order items
		for (OrderItem o : orderitems) {
			o.setOrder(order);

			
			System.out.println("OrderID :: " + order.getOrderID());

			o.updatePK(o.getInventory().getInventoryId());
			em.persist(o);
		}
		em.flush();
		//em.getTransaction().commit();

		OrderInfo orderInfo=new OrderInfo(order);
		/*
		 }
		 catch (CreateException e)
		 {
		 Util.debug("ShoppingCartBean(createOrder): Exception - " + e);
		 e.printStackTrace();
		 }
		 if (order != null)
		 {
		 */
		return orderInfo;
		/*
		 }
		 else
		 return null; */
	}

}
