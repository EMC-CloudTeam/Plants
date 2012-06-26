package com.emc.plants.web.controllers;

import java.util.ArrayList;
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
import com.emc.plants.persistence.Inventory;
import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.pojo.beans.OrderInfo;
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
@RequestMapping("/shopping")
public class ShoppingController {

	
	public static Logger logger = Logger.getLogger(ShoppingController.class);

	public static final String ACTION_ADDTOCART = "addtocart";
	public static final String ACTION_COMPLETECHECKOUT = "completecheckout";
	public static final String ACTION_GOTOCART = "goToCart";
	public static final String ACTION_INITCHECKOUT = "initcheckout";
	public static final String ACTION_ORDERINFODONE = "orderinfodone";
	public static final String ACTION_PRODUCTDETAIL = "productDetail";
	public static final String ACTION_SHOPPING = "shopping";
	public static final String ACTION_UPDATEQUANTITY = "updatequantity";

	private static final String ORDERINFO = "orderinfo";

	private static final String LOGIN = "login";

	private static final String CHECKOUTFINAL = "checkout_final";

	private static final String ORDERDONE = "orderdone";
	
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
	
	@Autowired
	protected ShoppingCart shoppingCart;
	
	public Suppliers getSuppliers() {
		return suppliers;
	}


	public Login getLogin() {
		return login;
	}


	public BackOrderStock getBackOrderStock() {
		return backOrderStock;
	}


	public Catalog getCatalog() {
		return catalog;
	}


	public ResetDB getResetDB() {
		return resetDB;
	}


	public Mailer getMailer() {
		return mailer;
	}


	/*public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}*/
	
	@PostConstruct
	public void init(){
		logger.debug("ShoppingController:init");
		Util.setDebug(true);
        
        this.suppliers = (Suppliers) Util.getSpringBean("suppliersBean");
        this.login = (Login) Util.getSpringBean("login");
        this.backOrderStock = (BackOrderStock) Util.getSpringBean("backOrderStockBean");
        this.catalog = (Catalog) Util.getSpringBean("catalog");
        this.resetDB = (ResetDB) Util.getSpringBean("resetDBBean");
        this.mailer = (Mailer)Util.getSpringBean("mailer");
        this.shoppingCart=(ShoppingCart)Util.getSpringBean("shopping");
//		model.addAttribute("message", "Spring 3 MVC Hello World");
        
	}
	
	
	
	public String performeTask(HttpServletRequest req,
			HttpServletResponse resp){
		return null;
		/*

		String action = null;
		action = req.getParameter(Util.ATTR_ACTION);
		logger.debug("action=" + action);
		if (action.equals(ACTION_SHOPPING))
		{
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
			Collection c = catalog.getItemsByCategory(Integer.parseInt(category));
			ArrayList items = new ArrayList(c);
			for (int i = 0; i < items.size();)
			{
				if (((Inventory) items.get(i)).isPublic())
					i++;
				else
					items.remove(i);
			}
			req.setAttribute(Util.ATTR_INVITEMS, items);
			////requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_SHOPPING);
			return Util.SHOPPING;
		}
		else if (action.equals(ACTION_PRODUCTDETAIL))
		{
			String invID = (String) req.getParameter("itemID");
			req.setAttribute(Util.ATTR_INVITEM, catalog.getItemInventory(invID));
			////requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_PRODUCT);
			return Util.PRODUCT;
		}
		else if (action.equals(ACTION_GOTOCART))
		{
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
					//shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
					session.setAttribute(Util.ATTR_CART, shoppingCart);
				}
			}
		//	//requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CART);
			return Util.CART;
		}
		else if (action.equals(ACTION_ADDTOCART))
		{
			ShoppingCart shoppingCart = null;
			// Get shopping cart.
			HttpSession session = req.getSession(true);
			shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
			if (shoppingCart == null)
			{
				logger.debug("shopping cart is NULL, must create it");
				//shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
				System.out.println("Items:: " +shoppingCart.getItems()+ " Size :: " +shoppingCart.getItems().size());
				shoppingCart.setItems(new ArrayList<ShoppingCartItem>());
			}
			else
			{
				// Make sure ShopingCart reference has not timed out.
				try
				{
					logger.debug("shopping cart is not null, check items, size=" + shoppingCart.getItems().size());
					shoppingCart.getItems();
				}
				// TODO: what exception would be thrown?
				catch (RuntimeException e)
				{
					// ShoppingCart timed out, so create a new one.
					logger.debug("addtocart: shopping cart ref must have timed out, create a new one");
					ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
					//shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
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
			////requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CART);
			return Util.CART;
		}
		else if (action.equals(ACTION_UPDATEQUANTITY))
		{
			// Get shopping cart.
			HttpSession session = req.getSession(true);
			ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
			// Make sure ShopingCart reference has not timed out.
			try
			{
				shoppingCart.getItems();
			}
			// TODO: what exception gets thrown?
			catch (RuntimeException e)
			{
				// ShoppingCart timed out, so create a new one.
				logger.debug("updatequantity: shopping cart ref must have timed out, create a new one");
				ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
				//shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
				
				
				if (cartContents != null) {
					shoppingCart.setCartContents(cartContents);
				}
			}
			// Update item quantity in cart.
			if (shoppingCart != null)
			{
				try
				{
					int cnt = 0;
					Collection c = shoppingCart.getItems();
					ArrayList items;

					if (c instanceof ArrayList)
						items = (ArrayList) c;
					else
						items = new ArrayList(c);
					ShoppingCartItem si;
					String parm, parmval;
					// Check all quantity values from cart form.
					for (int parmcnt = 0;; parmcnt++)
					{
						parm = "itemqty" + String.valueOf(parmcnt);
						parmval = req.getParameter(parm);
						// No more quantity fields, so break out.
						if ((parmval == null) || parmval.equals("null"))
						{
							break;
						}
						else // Check quantity value of cart item.
						{
							int quantity = Integer.parseInt(parmval);
							// Quantity set to 0, so remove from cart.
							if (quantity == 0)
							{
								items.remove(cnt);
							}
							else // Change quantity of cart item.
							{
								si = (ShoppingCartItem) items.get(cnt);
								si.setQuantity(quantity);
								items.set(cnt, si);
								cnt++;
							}
						}
					}
					// Are items in cart? Yes, set the session attributes.
					if (items.size() > 0)
					{
						shoppingCart.setItems(items);
						session.setAttribute(Util.ATTR_CART, shoppingCart);
						session.setAttribute(Util.ATTR_CART_CONTENTS, shoppingCart.getCartContents());
					}
					else // No items in cart, so remove attributes from session.
					{
						session.removeAttribute(Util.ATTR_CART);
						session.removeAttribute(Util.ATTR_CART_CONTENTS);
					}
				}
				catch (Exception e)
				{
					//  Log the exception but try to continue.
					logger.debug("ShoppingServlet.performAction() -> Exception caught: " +e);
					// This should take us to the error page.
					//throw new ServletException(e.getMessage());
				}
			}
			////requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CART);
			return Util.CART;
		}
		else if (action.equals(ACTION_INITCHECKOUT))
		{
			String url;
			HttpSession session = req.getSession(true);
			CustomerInfo customerInfo = (CustomerInfo) session.getAttribute(Util.ATTR_CUSTOMER);
			if (customerInfo == null)
			{
				req.setAttribute(Util.ATTR_RESULTS, "You must login or register before checking out.");
				session.setAttribute(Util.ATTR_CHECKOUT, new Boolean(true));
				url = Util.LOGIN;
			}
			else
			{
				url = Util.ORDERINFO;
			}
		//	//requestDispatch(getServletConfig().getServletContext(), req, resp, url);
			return url;

		}
		else if (action.equals(ACTION_ORDERINFODONE))
		{
			OrderInfo orderinfo = null;
			ShoppingCart shoppingCart = null;
			HttpSession session = req.getSession(true);
			CustomerInfo customerInfo = (CustomerInfo) session.getAttribute(Util.ATTR_CUSTOMER);
			String customerID = customerInfo.getCustomerID();
			shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
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
					//shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
					shoppingCart.setCartContents(cartContents);
				}
				else
				{
					logger.debug("NoSuchObject Exception!!!");
					logger.debug("Major Problem!!!");
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
			//	//requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CHECKOUTFINAL);
				return Util.CHECKOUTFINAL;
			}
		}
		else if (action.equals(ACTION_COMPLETECHECKOUT))
		{
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
					//shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
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
				mailer.createAndSendMail(customerInfo, orderKey);
			}
			catch (MailerAppException e)
			{
				System.out.println("MailerAppException:"+e);
				e.printStackTrace();
			}
			catch (Exception e)
			{
				System.out.println("Exception during create and send mail :"+e);
				e.printStackTrace();
			}
			// Remove items saved in HttpSession.
			session.removeAttribute(Util.ATTR_CART);
			session.removeAttribute(Util.ATTR_CART_CONTENTS);
			session.removeAttribute(Util.ATTR_CATEGORY);
			session.removeAttribute(Util.ATTR_ORDERKEY);
			session.removeAttribute(Util.ATTR_CHECKOUT);
			HttpSession httpSession = req.getSession(true);
			
			//httpSession.invalidate();
			////requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_ORDERDONE);
			return Util.ORDERDONE;
		}
		return action;
	
	*/}

	@RequestMapping(method=RequestMethod.GET)
	public String performTask(HttpServletRequest req, HttpServletResponse resp){
		String view=null;
		String action = null;
		action = req.getParameter(Util.ATTR_ACTION);
		logger.debug("action=" + action);
		if (action.equalsIgnoreCase(ACTION_SHOPPING))
		{
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
			Collection c = catalog.getItemsByCategory(Integer.parseInt(category));
			ArrayList items = new ArrayList(c);
			for (int i = 0; i < items.size();)
			{
				if (((Inventory) items.get(i)).isPublic())
					i++;
				else
					items.remove(i);
			}
			req.setAttribute(Util.ATTR_INVITEMS, items);
//			//requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_SHOPPING);
			view = Util.SHOPPING;
		}
		else if (action.equalsIgnoreCase(ACTION_PRODUCTDETAIL))
		{
			String invID = (String) req.getParameter("itemID");
			req.setAttribute(Util.ATTR_INVITEM, catalog.getItemInventory(invID));
			//requestDispatch(getServle	tConfig().getServletContext(), req, resp, Util.PAGE_PRODUCT);
			return Util.ATTR_PRODUCT;
		}
		else if (action.equalsIgnoreCase(ACTION_GOTOCART))
		{
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
					shoppingCart = (ShoppingCart) Util.getSpringBean("shopping");
					session.setAttribute(Util.ATTR_CART, shoppingCart);
				}
			}
			//requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CART);
			view = Util.CART;
		}
		else if (action.equalsIgnoreCase(ACTION_ADDTOCART))
		{
			ShoppingCart shoppingCart = null;
			// Get shopping cart.
			HttpSession session = req.getSession(true);
			shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
			if (shoppingCart == null)
			{
				logger.debug("shopping cart is NULL, must create it");
				shoppingCart = (ShoppingCart) Util.getSpringBean("shopping");
				logger.debug("Items:: " +shoppingCart.getItems()+ " Size :: " +shoppingCart.getItems().size());
				shoppingCart.setItems(new ArrayList<ShoppingCartItem>());
			}
			else
			{
				// Make sure ShopingCart reference has not timed out.
				try
				{
					logger.debug("shopping cart is not null, check items, size=" + shoppingCart.getItems().size());
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
			//requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CART);
			view = Util.CART;
		}
		else if (action.equalsIgnoreCase(ACTION_UPDATEQUANTITY))
		{
			// Get shopping cart.
			HttpSession session = req.getSession(true);
			ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
			// Make sure ShopingCart reference has not timed out.
			try
			{
				shoppingCart.getItems();
			}
			// TODO: what exception gets thrown?
			catch (RuntimeException e)
			{
				// ShoppingCart timed out, so create a new one.
				logger.debug("updatequantity: shopping cart ref must have timed out, create a new one");
				ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
				shoppingCart = (ShoppingCart) Util.getSpringBean("shopping");
				
				if (cartContents != null) {
					shoppingCart.setCartContents(cartContents);
				}
			}
			// Update item quantity in cart.
			if (shoppingCart != null)
			{
				try
				{
					int cnt = 0;
					Collection c = shoppingCart.getItems();
					ArrayList items;

					if (c instanceof ArrayList)
						items = (ArrayList) c;
					else
						items = new ArrayList(c);
					ShoppingCartItem si;
					String parm, parmval;
					// Check all quantity values from cart form.
					for (int parmcnt = 0;; parmcnt++)
					{
						parm = "itemqty" + String.valueOf(parmcnt);
						parmval = req.getParameter(parm);
						// No more quantity fields, so break out.
						if ((parmval == null) || parmval.equals("null"))
						{
							break;
						}
						else // Check quantity value of cart item.
						{
							int quantity = Integer.parseInt(parmval);
							// Quantity set to 0, so remove from cart.
							if (quantity == 0)
							{
								items.remove(cnt);
							}
							else // Change quantity of cart item.
							{
								si = (ShoppingCartItem) items.get(cnt);
								si.setQuantity(quantity);
								items.set(cnt, si);
								cnt++;
							}
						}
					}
					// Are items in cart? Yes, set the session attributes.
					if (items.size() > 0)
					{
						shoppingCart.setItems(items);
						session.setAttribute(Util.ATTR_CART, shoppingCart);
						session.setAttribute(Util.ATTR_CART_CONTENTS, shoppingCart.getCartContents());
					}
					else // No items in cart, so remove attributes from session.
					{
						session.removeAttribute(Util.ATTR_CART);
						session.removeAttribute(Util.ATTR_CART_CONTENTS);
					}
				}
				catch (Exception e)
				{
					//  Log the exception but try to continue.
					logger.debug("ShoppingServlet.performAction() -> Exception caught: " +e);
					// This should take us to the error page.
//					throw new ServletException(e.getMessage());
				}
			}
			//requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CART);
			view=Util.CART;
		}
		else if (action.equalsIgnoreCase(ACTION_INITCHECKOUT))
		{
			String url;
			HttpSession session = req.getSession(true);
			CustomerInfo customerInfo = (CustomerInfo) session.getAttribute(Util.ATTR_CUSTOMER);
			if (customerInfo == null)
			{
				req.setAttribute(Util.ATTR_RESULTS, "You must login or register before checking out.");
				session.setAttribute(Util.ATTR_CHECKOUT, new Boolean(true));
				url = LOGIN;
			}
			else
			{
				url = ORDERINFO;
			}
			//requestDispatch(getServletConfig().getServletContext(), req, resp, url);
			view=url;
		}
		else if (action.equalsIgnoreCase(ACTION_ORDERINFODONE))
		{
			OrderInfo orderinfo = null;
			ShoppingCart shoppingCart = null;
			HttpSession session = req.getSession(true);
			CustomerInfo customerInfo = (CustomerInfo) session.getAttribute(Util.ATTR_CUSTOMER);
			String customerID = customerInfo.getCustomerID();
			shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
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
					logger.debug("NoSuchObject Exception!!!");
					logger.debug("Major Problem!!!");
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
//				requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CHECKOUTFINAL);
				view=CHECKOUTFINAL;
			}
		}
		else if (action.equalsIgnoreCase(ACTION_COMPLETECHECKOUT))
		{
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
					shoppingCart = (ShoppingCart) Util.getSpringBean("shopping");
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
				System.out.println("Exception during Posting message to RabbitMQ :"+e);
				e.printStackTrace();
			}
			
			try
			{
				mailer.createAndSendMail(customerInfo, orderKey);
			}
			catch (MailerAppException e)
			{
				System.out.println("MailerAppException:"+e);
				e.printStackTrace();
			}
			catch (Exception e)
			{
				System.out.println("Exception during create and send mail :"+e);
				e.printStackTrace();
			}
			// Remove items saved in HttpSession.
			session.removeAttribute(Util.ATTR_CART);
			session.removeAttribute(Util.ATTR_CART_CONTENTS);
			session.removeAttribute(Util.ATTR_CATEGORY);
			session.removeAttribute(Util.ATTR_ORDERKEY);
			session.removeAttribute(Util.ATTR_CHECKOUT);
			HttpSession httpSession = req.getSession(true);
			
			//httpSession.invalidate();
//			requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_ORDERDONE);
			view = ORDERDONE;
		}
		
		
		return view;
	}
}
