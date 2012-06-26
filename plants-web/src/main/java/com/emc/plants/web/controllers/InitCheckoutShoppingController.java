package com.emc.plants.web.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.service.interfaces.BackOrderStock;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.service.interfaces.Mailer;
import com.emc.plants.service.interfaces.ResetDB;
import com.emc.plants.service.interfaces.ShoppingCart;
import com.emc.plants.service.interfaces.Suppliers;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping("/initCheckout")
public class InitCheckoutShoppingController extends ShoppingController {
	
	public static Logger logger = Logger.getLogger(InitCheckoutShoppingController.class);
	
	@PostConstruct
	public void init(){
		this.suppliers = (Suppliers) Util.getSpringBean("suppliersBean");
        this.login = (Login) Util.getSpringBean("login");
        this.backOrderStock = (BackOrderStock) Util.getSpringBean("backOrderStockBean");
        this.catalog = (Catalog) Util.getSpringBean("catalog");
        this.resetDB = (ResetDB) Util.getSpringBean("resetDBBean");
        this.mailer = (Mailer)Util.getSpringBean("mailer");
//        this.shoppingCart=(ShoppingCart)Util.getSpringBean("shopping");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String performTask(HttpServletRequest req,
			HttpServletResponse resp){
		logger.debug("InitCheckOutShoppingController:performTask");
		String url;
		HttpSession session = req.getSession(true);
		CustomerInfo customerInfo = (CustomerInfo) session.getAttribute(Util.ATTR_CUSTOMER);
		if (customerInfo == null)
		{
			req.setAttribute(Util.ATTR_RESULTS, "You must login or register before checking out.");
			session.setAttribute(Util.ATTR_CHECKOUT, new Boolean(true));
			url = Util.PAGE_LOGIN;
		}
		else
		{
			url = Util.PAGE_ORDERINFO;
		}
		//requestDispatch(getServletConfig().getServletContext(), req, resp, url);
		return url;
		
	}
	
}
