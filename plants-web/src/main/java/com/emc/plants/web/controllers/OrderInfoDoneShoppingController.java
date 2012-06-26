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
import com.emc.plants.pojo.beans.OrderInfo;
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
@RequestMapping("/orderInfoDone")
public class OrderInfoDoneShoppingController extends ShoppingController  {

	public static Logger logger = Logger.getLogger(OrderInfoDoneShoppingController.class);
	
	@Autowired
	private Suppliers suppliers = null;
	

	@Autowired
    private Login login;
	

	@Autowired
    private BackOrderStock backOrderStock = null;
	

	@Autowired
    private Catalog catalog = null;
	

	@Autowired
    private ResetDB resetDB;
	@Autowired
	private Mailer mailer;
	
	
	@PostConstruct
	public void init(){
		logger.debug("ShoppingController:init");
        this.suppliers = (Suppliers) Util.getSpringBean("suppliersBean");
        this.login = (Login) Util.getSpringBean("login");
        this.backOrderStock = (BackOrderStock) Util.getSpringBean("backOrderStockBean");
        this.catalog = (Catalog) Util.getSpringBean("catalog");
        this.resetDB = (ResetDB) Util.getSpringBean("resetDBBean");
        this.mailer = (Mailer)Util.getSpringBean("mailer");
        
//		model.addAttribute("message", "Spring 3 MVC Hello World");
        
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String performTask(HttpServletRequest req,
			HttpServletResponse resp){
		logger.debug("InitCheckOutShoppingController:performTask");
		OrderInfo orderinfo = null;
		ShoppingCart shoppingCart=null;
		HttpSession session = req.getSession(true);
		CustomerInfo customerInfo = (CustomerInfo) session.getAttribute(Util.ATTR_CUSTOMER);
		String customerID = customerInfo.getCustomerID();
		shoppingCart=(ShoppingCart)session.getAttribute(Util.ATTR_CART);
		// Make sure ShopingCart reference has not timed out.
		try
		{
			logger.debug("orderinfodone: ShoppingCart timeout? check getItems() method");
			shoppingCart.getItems();
		}
		// TODO: what exception gets thrown?
		catch (RuntimeException e)
		{
			// ShoppingCart timed out, so create a new one.
			logger.debug("orderinfodone: ShoppingCart ref must have timed out");
			ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
			if (cartContents != null)
			{
				shoppingCart = (ShoppingCart)Util.getSpringBean("shopping");
				shoppingCart.setCartContents(cartContents);
			}
			else
			{
				logger.error("NoSuchObject Exception!!!");
				logger.error("Major Problem!!!");
				shoppingCart = null;
			}
		}
		logger.debug("orderinfodone: got cart?");
		if (shoppingCart != null)
		{
			logger.debug("orderinfodone: cart not NULL");
			String billName = req.getParameter("bname");
			String billAddr1 = req.getParameter("baddr1");
			String billAddr2 = req.getParameter("baddr2");
			String billCity = req.getParameter("bcity");
			String billState = req.getParameter("bstate");
			String billZip = req.getParameter("bzip");
			String billPhone = req.getParameter("bphone");
			String shipName = req.getParameter("sname");
			String shipAddr1 = req.getParameter("saddr1");
			String shipAddr2 = req.getParameter("saddr2");
			String shipCity = req.getParameter("scity");
			String shipState = req.getParameter("sstate");
			String shipZip = req.getParameter("szip");
			String shipPhone = req.getParameter("sphone");
			int shippingMethod = Integer.parseInt(req.getParameter("shippingMethod"));
			String creditCard = req.getParameter("ccardname");
			String ccNum = req.getParameter("ccardnum");
			String ccExpireMonth = req.getParameter("ccexpiresmonth");
			String ccExpireYear = req.getParameter("ccexpiresyear");
			String cardHolder = req.getParameter("ccholdername");
			orderinfo = shoppingCart.createOrder(customerID, billName, billAddr1, billAddr2, billCity, billState, billZip, billPhone, shipName, shipAddr1, shipAddr2, shipCity, shipState, shipZip, shipPhone, creditCard, ccNum, ccExpireMonth, ccExpireYear, cardHolder, shippingMethod, shoppingCart.getItems());
			logger.debug("orderinfodone: order created");
		}
		if (orderinfo != null)
		{
			req.setAttribute(Util.ATTR_ORDERINFO, orderinfo);
			req.setAttribute(Util.ATTR_CARTITEMS, shoppingCart.getItems());
			session.setAttribute(Util.ATTR_ORDERKEY, orderinfo.getID());
	//		requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CHECKOUTFINAL);
			return Util.CHECKOUTFINAL;
		}
	
		return Util.CHECKOUTFINAL;
	}
}
