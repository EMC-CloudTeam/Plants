package com.emc.plants.web.controllers;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.plants.persistence.Inventory;
import com.emc.plants.pojo.beans.ShoppingCartContents;
import com.emc.plants.pojo.beans.ShoppingCartItem;
import com.emc.plants.service.interfaces.ShoppingCart;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping("/addToCart")
public class AddToCartShoppingController extends ShoppingController {
	public static Logger logger = Logger.getLogger(AddToCartShoppingController.class);
	public static final String PAGE_CART="cart";
	
	@PostConstruct
	public void init(){
		
	}

	@RequestMapping(method=RequestMethod.GET)
	public String performTask(HttpServletRequest req,
			HttpServletResponse resp){
		logger.debug("AddToCartShoppingController");

		// Get shopping cart.
		HttpSession session = req.getSession(true);
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
		if (shoppingCart == null)
		{
			logger.debug("shopping cart is NULL, must create it");
			shoppingCart = (ShoppingCart) Util.getSpringBean("shopping");
			logger.debug("Items:: " +shoppingCart.getItems()+ " Size :: " +shoppingCart.getItems().size());
			shoppingCart.setItems(new ArrayList<ShoppingCartItem>());
		}
		else
		{
			// Make sure ShoppingCart reference has not timed out.
			try
			{
				logger.debug("shopping cart is not null, check items, size=" + shoppingCart.getItems()!=null?shoppingCart.getItems().size():null);
				shoppingCart.getItems();
			}
			// TODO: what exception would be thrown?
			catch (RuntimeException e)
			{
				// ShoppingCart timed out, so create a new one.
				logger.debug("addtocart: shopping cart ref must have timed out, create a new one");
				ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
				shoppingCart = (ShoppingCart) Util.getSpringBean("shopping");
				if (cartContents != null) {
					shoppingCart.setCartContents(cartContents);
				}
			}
		}
		// Add item to cart.
		if (shoppingCart != null)
		{
			String invID = req.getParameter("itemID");

			// Gets detached instance
			Inventory inv = catalog.getItemInventory(invID);
			ShoppingCartItem si = new ShoppingCartItem(inv);
			si.setQuantity(Integer.parseInt(req.getParameter("qty").trim()));
			shoppingCart.addItem(si);
			session.setAttribute(Util.ATTR_CART, shoppingCart);
			session.setAttribute(Util.ATTR_CART_CONTENTS, shoppingCart.getCartContents());
		}
		return PAGE_CART;
	
	}
}
