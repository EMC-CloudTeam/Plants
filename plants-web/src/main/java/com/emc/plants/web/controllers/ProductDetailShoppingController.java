package com.emc.plants.web.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.plants.service.interfaces.BackOrderStock;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.service.interfaces.Mailer;
import com.emc.plants.service.interfaces.ResetDB;
import com.emc.plants.service.interfaces.ShoppingCart;
import com.emc.plants.service.interfaces.Suppliers;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping("/productDetail")
public class ProductDetailShoppingController extends ShoppingController {

	public static Logger logger = Logger.getLogger(ProductDetailShoppingController.class);
	
	@PostConstruct
	public void init(){
	    this.catalog = (Catalog) Util.getSpringBean("catalog");
    }
	
	@RequestMapping(method=RequestMethod.GET)
	public String performTask(HttpServletRequest req,
			HttpServletResponse resp){
		logger.debug("ProductDetailShoppingController::performTask");
		String invID = (String) req.getParameter("itemID");
		req.setAttribute(Util.ATTR_INVITEM, this.catalog.getItemInventory(invID));
		return Util.PRODUCT;
	}
}
