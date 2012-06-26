package com.emc.plants.web.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.plants.pojo.beans.ShoppingCartContents;
import com.emc.plants.service.interfaces.BackOrderStock;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.service.interfaces.Mailer;
import com.emc.plants.service.interfaces.ResetDB;
import com.emc.plants.service.interfaces.ShoppingCart;
import com.emc.plants.service.interfaces.Suppliers;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping("/goToCart")
public class GoToCartShoppingController extends ShoppingController  {

	public static Logger logger = Logger.getLogger(GoToCartShoppingController.class);
	public static final String PAGE_CART = "cart";
	
	@PostConstruct
	public void init(){
		this.suppliers = (Suppliers) Util.getSpringBean("suppliersBean");
        this.login = (Login) Util.getSpringBean("login");
        this.backOrderStock = (BackOrderStock) Util.getSpringBean("backOrderStockBean");
        this.catalog = (Catalog) Util.getSpringBean("catalog");
        this.resetDB = (ResetDB) Util.getSpringBean("resetDBBean");
        this.mailer = (Mailer)Util.getSpringBean("mailer");
//        this.shoppingCart=
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String performTask(HttpServletRequest req,
			HttpServletResponse resp){
		logger.debug("GoToCartShoppingController::performTask");
		HttpSession session = req.getSession(true);
		// Get shopping cart.
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
		if (shoppingCart != null)
		{
			// Make sure ShopingCart reference has not timed out.
			try
			{
				shoppingCart.getItems();
			}
			// TODO: what exception would be thrown?
			catch (RuntimeException e)
			{
				logger.debug("gotocart: shopping cart ref must have timed out");
				ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
				shoppingCart=(ShoppingCart)Util.getSpringBean("shopping");
				shoppingCart.setCartContents(cartContents);
				session.setAttribute(Util.ATTR_CART, shoppingCart);
			}
		}
		return PAGE_CART;
	}
}
