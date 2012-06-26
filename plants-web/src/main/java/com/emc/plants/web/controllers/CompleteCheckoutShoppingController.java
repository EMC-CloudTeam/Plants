package com.emc.plants.web.controllers;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.plants.messaging.publisher.PaymentInfoPublisher;
import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.pojo.beans.ShoppingCartContents;
import com.emc.plants.pojo.beans.ShoppingCartItem;
import com.emc.plants.service.exceptions.MailerAppException;
import com.emc.plants.service.interfaces.BackOrderStock;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.service.interfaces.Mailer;
import com.emc.plants.service.interfaces.ResetDB;
import com.emc.plants.service.interfaces.ShoppingCart;
import com.emc.plants.service.interfaces.Suppliers;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping("/completeCheckout")
public class CompleteCheckoutShoppingController extends ShoppingController  {

	public static Logger logger = Logger.getLogger(CompleteCheckoutShoppingController.class);
	
	@Autowired
	protected Suppliers suppliers = null;
	

	@Autowired
    protected Login login;
	

	@Autowired
	protected BackOrderStock backOrderStock = null;
	

	@Autowired
	protected Catalog catalog = null;
	

	@Autowired
	protected ResetDB resetDB;
	@Autowired
	protected Mailer mailer;
	/*@Autowired
	protected ShoppingCart shoppingCart;*/
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
		logger.debug("CompleteCheckoutShoppingController:performTask");
		ShoppingCart shoppingCart = null;
		HttpSession session = req.getSession(true);
		long key = (Long) session.getAttribute(Util.ATTR_ORDERKEY);
		req.setAttribute(Util.ATTR_ORDERID, key);
		long orderKey = key;
		logger.debug("completecheckout: order id ="+orderKey );
		CustomerInfo customerInfo = (CustomerInfo) session.getAttribute(Util.ATTR_CUSTOMER);
		// Check the available inventory and backorder if necessary.
		shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
		// Make sure ShopingCart reference has not timed out.
		try
		{
			logger.debug("completecheckout: ShoppingCart timeout? check getItems() method");
			shoppingCart.getItems();
		}
		// TODO: what exception gets thrown?
		catch (RuntimeException e)
		{
			// ShoppingCart timed out, so create a new one.
			logger.debug("completecheckout: ShoppingCart ref must have timed out");
			ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
			if (cartContents != null)
			{
				shoppingCart.setCartContents(cartContents);
			}
			else
			{
				logger.debug("NoSuchObject Exception!!!");
				logger.debug("Major Problem!!!");
				shoppingCart = null;
			}
		}
		if (shoppingCart != null)
		{
			ShoppingCartItem si;
			Collection items = shoppingCart.getItems();
			for (Object o : items)
			{
				si = (ShoppingCartItem) o;
				shoppingCart.checkInventory(si);
				logger.debug("ShoppingCart.checkInventory() - checking Inventory quantity of item: " + si.getID());
			}
		}

		
		try
		{
			PaymentInfoPublisher pInfoPublisher = (PaymentInfoPublisher)Util.getSpringBean("paymentInfoPublisher");
			
			pInfoPublisher.publishMessage("Order placed successfully for Customer : " +customerInfo.getCustomerID()+" with Order ID : "+orderKey);
		}
		catch (Exception e)
		{
			logger.error("Exception during Posting message to RabbitMQ :"+e);
			e.printStackTrace();
		}
		
		try
		{
			mailer.createAndSendMail(customerInfo, orderKey);
		}
		catch (MailerAppException e)
		{
			logger.error("MailerAppException:"+e);
			e.printStackTrace();
		}
		catch (Exception e)
		{
			logger.error("Exception during create and send mail :"+e);
			e.printStackTrace();
		}
		// Remove items saved in HttpSession.
		session.removeAttribute(Util.ATTR_CART);
		session.removeAttribute(Util.ATTR_CART_CONTENTS);
		session.removeAttribute(Util.ATTR_CATEGORY);
		session.removeAttribute(Util.ATTR_ORDERKEY);
		session.removeAttribute(Util.ATTR_CHECKOUT);
//		HttpSession httpSession = req.getSession(true);
		
		return Util.ORDERDONE;
	}
}
