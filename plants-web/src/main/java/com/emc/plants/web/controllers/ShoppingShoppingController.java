package com.emc.plants.web.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.plants.persistence.Inventory;
import com.emc.plants.service.interfaces.BackOrderStock;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.service.interfaces.Mailer;
import com.emc.plants.service.interfaces.ResetDB;
import com.emc.plants.service.interfaces.ShoppingCart;
import com.emc.plants.service.interfaces.Suppliers;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping("/shopping")
public class ShoppingShoppingController extends ShoppingController  {
	
	public static Logger logger = Logger.getLogger(ShoppingShoppingController.class);
	
	@PostConstruct
	public void init(){
		this.suppliers = (Suppliers) Util.getSpringBean("suppliersBean");
        this.login = (Login) Util.getSpringBean("login");
        this.backOrderStock = (BackOrderStock) Util.getSpringBean("backOrderStockBean");
        this.catalog = (Catalog) Util.getSpringBean("catalog");
        this.resetDB = (ResetDB) Util.getSpringBean("resetDBBean");
        this.mailer = (Mailer)Util.getSpringBean("mailer");
       
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String performTask(HttpServletRequest req,
			HttpServletResponse resp){
		
		logger.debug("ShoppingShoppingController::performTask");
		String category = (String) req.getParameter("category");
		HttpSession session = req.getSession(true);
		// Get session category if none on request, such as
		// 'Continue Shopping' from the Shopping Cart jsp.
		if ((category == null) || (category.equals("null")))
		{
			category = (String) session.getAttribute(Util.ATTR_CATEGORY);
		}
		// If category still null, default to first category.
		if ((category == null) || (category.equals("null")))
		{
			category = "0";
		}
		session.setAttribute(Util.ATTR_CATEGORY, category);
		// Get the shopping items from the catalog.
		Collection c = getCatalog().getItemsByCategory(Integer.parseInt(category));
		ArrayList items = new ArrayList(c);
		for (int i = 0; i < items.size();)
		{
			if (((Inventory) items.get(i)).isPublic())
				i++;
			else
				items.remove(i);
		}
		req.setAttribute(Util.ATTR_INVITEMS, items);
		//requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_SHOPPING);
		return Util.SHOPPING;
	}

}
