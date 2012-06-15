
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use
//or for redistribution by customer, as part of such an application, in customer's own products. "

//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2004
//All Rights Reserved * Licensed Materials - Property of IBM

package com.emc.plants.web.servlets;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.emc.plants.persistence.Inventory;
import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.pojo.beans.OrderInfo;
import com.emc.plants.pojo.beans.ShoppingCartContents;
import com.emc.plants.pojo.beans.ShoppingCartItem;
import com.emc.plants.service.exceptions.MailerAppException;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Mailer;
import com.emc.plants.service.interfaces.ShoppingCart;
import com.emc.plants.utils.Util;
import com.emc.plants.web.util.WebUtil;

/**
 * Servlet to handle shopping needs.
 */
//@EJB(name="ejb/ShoppingCart", beanName="ShoppingCart", beanInterface=ShoppingCart.class)
public class ShoppingServlet extends HttpServlet
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
//	Servlet action codes.
	public static final String ACTION_ADDTOCART = "addtocart";
	public static final String ACTION_COMPLETECHECKOUT = "completecheckout";
	public static final String ACTION_GOTOCART = "gotocart";
	public static final String ACTION_INITCHECKOUT = "initcheckout";
	public static final String ACTION_ORDERINFODONE = "orderinfodone";
	public static final String ACTION_PRODUCTDETAIL = "productdetail";
	public static final String ACTION_SHOPPING = "shopping";
	public static final String ACTION_UPDATEQUANTITY = "updatequantity";

//	@EJB(beanName="Catalog")
	@Autowired
	Catalog catalog;

//	@EJB(name="Mailer")
	@Autowired
	private Mailer mailer;

	//@EJB(beanName="ShoppingCart")
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		this.catalog = (Catalog)WebUtil.getSpringBean(this.getServletContext(), "catalog");
		this.mailer = (Mailer)WebUtil.getSpringBean(this.getServletContext(), "mailer");
		
		Util.setDebug(true);
//		shoppingCartHome = (ShoppingCartHome) Util.getEJBHome("java:comp/env/ejb/ShoppingCart", com.ibm.websphere.samples.pbwejb.ShoppingCartHome.class);
//		catalogHome = (CatalogHome) Util.getEJBHome("java:comp/env/ejb/Catalog", com.ibm.websphere.samples.pbwejb.CatalogHome.class);
	}
	/**
	 * Process incoming HTTP GET requests
	 *
	 * @param request Object that encapsulates the request to the servlet
	 * @param response Object that encapsulates the response from the servlet
	 */
	public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException
	{
		performTask(request, response);
	}
	/**
	 * Process incoming HTTP POST requests
	 *
	 * @param request Object that encapsulates the request to the servlet
	 * @param response Object that encapsulates the response from the servlet
	 */
	public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException
	{
		//sanitize the request parameters to prevent XSS security vulnerability
		Enumeration names=request.getParameterNames();
		while (names.hasMoreElements()){
			String val=(String)request.getParameter((String)names.nextElement());
			if (!Util.validateString(val)){
				throw new ServletException("Invalid input parameter in ShoppingServlet:"+URLEncoder.encode(val,"UTF8"));
			}
		}
		performTask(request, response);
	}
	/**
	 * Main service method for ShoppingServlet
	 *
	 * @param req Object that encapsulates the request to the servlet
	 * @param resp Object that encapsulates the response from the servlet
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String action = null;
		action = req.getParameter(Util.ATTR_ACTION);
		Util.debug("action=" + action);
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
			// If categoyr still null, default to first category.
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
			requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_SHOPPING);
		}
		else if (action.equals(ACTION_PRODUCTDETAIL))
		{
			String invID = (String) req.getParameter("itemID");
			req.setAttribute(Util.ATTR_INVITEM, catalog.getItemInventory(invID));
			requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_PRODUCT);
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
					Util.debug("gotocart: shopping cart ref must have timed out");
					ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
					shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
					session.setAttribute(Util.ATTR_CART, shoppingCart);
				}
			}
			requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CART);
		}
		else if (action.equals(ACTION_ADDTOCART))
		{
			ShoppingCart shoppingCart = null;
			// Get shopping cart.
			HttpSession session = req.getSession(true);
			shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
			if (shoppingCart == null)
			{
				Util.debug("shopping cart is NULL, must create it");
				shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
				System.out.println("Items:: " +shoppingCart.getItems()+ " Size :: " +shoppingCart.getItems().size());
				shoppingCart.setItems(new ArrayList<ShoppingCartItem>());
			}
			else
			{
				// Make sure ShopingCart reference has not timed out.
				try
				{
					Util.debug("shopping cart is not null, check items, size=" + shoppingCart.getItems().size());
					shoppingCart.getItems();
				}
				// TODO: what exception would be thrown?
				catch (RuntimeException e)
				{
					// ShoppingCart timed out, so create a new one.
					Util.debug("addtocart: shopping cart ref must have timed out, create a new one");
					ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
					shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
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
			requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CART);
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
				Util.debug("updatequantity: shopping cart ref must have timed out, create a new one");
				ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
				shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
				
				
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
					Util.debug("ShoppingServlet.performAction() -> Exception caught: " +e);
					// This should take us to the error page.
					throw new ServletException(e.getMessage());
				}
			}
			requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CART);
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
				url = Util.PAGE_LOGIN;
			}
			else
			{
				url = Util.PAGE_ORDERINFO;
			}
			requestDispatch(getServletConfig().getServletContext(), req, resp, url);
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
				Util.debug("orderinfodone: ShoppingCart timeout? check getItems() method");
				shoppingCart.getItems();
			}
			// TODO: what exception gets thrown?
			catch (RuntimeException e)
			{
				// ShoppingCart timed out, so create a new one.
				Util.debug("orderinfodone: ShoppingCart ref must have timed out");
				ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
				if (cartContents != null)
				{
					shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
					shoppingCart.setCartContents(cartContents);
				}
				else
				{
					Util.debug("NoSuchObject Exception!!!");
					Util.debug("Major Problem!!!");
					shoppingCart = null;
				}
			}
			Util.debug("orderinfodone: got cart?");
			if (shoppingCart != null)
			{
				Util.debug("orderinfodone: cart not NULL");
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
				Util.debug("orderinfodone: order created");
			}
			if (orderinfo != null)
			{
				req.setAttribute(Util.ATTR_ORDERINFO, orderinfo);
				req.setAttribute(Util.ATTR_CARTITEMS, shoppingCart.getItems());
				session.setAttribute(Util.ATTR_ORDERKEY, orderinfo.getID());
				requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_CHECKOUTFINAL);
			}
		}
		else if (action.equals(ACTION_COMPLETECHECKOUT))
		{
			ShoppingCart shoppingCart = null;
			HttpSession session = req.getSession(true);
			long key = (Long) session.getAttribute(Util.ATTR_ORDERKEY);
			req.setAttribute(Util.ATTR_ORDERID, key);
			long orderKey = key;
			Util.debug("completecheckout: order id ="+orderKey );
			CustomerInfo customerInfo = (CustomerInfo) session.getAttribute(Util.ATTR_CUSTOMER);
			// Check the available inventory and backorder if necessary.
			shoppingCart = (ShoppingCart) session.getAttribute(Util.ATTR_CART);
			// Make sure ShopingCart reference has not timed out.
			try
			{
				Util.debug("completecheckout: ShoppingCart timeout? check getItems() method");
				shoppingCart.getItems();
			}
			// TODO: what exception gets thrown?
			catch (RuntimeException e)
			{
				// ShoppingCart timed out, so create a new one.
				Util.debug("completecheckout: ShoppingCart ref must have timed out");
				ShoppingCartContents cartContents = (ShoppingCartContents) session.getAttribute(Util.ATTR_CART_CONTENTS);
				if (cartContents != null)
				{
					shoppingCart = (ShoppingCart) WebUtil.getSpringBean(this.getServletContext(), "shopping");
					shoppingCart.setCartContents(cartContents);
				}
				else
				{
					Util.debug("NoSuchObject Exception!!!");
					Util.debug("Major Problem!!!");
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
					Util.debug("ShoppingCart.checkInventory() - checking Inventory quantity of item: " + si.getID());
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
			requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_ORDERDONE);
		}
	}
	/**
	 * Send redirect
	 */
	private void sendRedirect(HttpServletResponse resp, String page) throws ServletException, IOException
	{
		resp.sendRedirect(resp.encodeRedirectURL(page));
	}
	/**
	 * Request dispatch
	 */
	private void requestDispatch(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String page) throws ServletException, IOException
	{
		resp.setContentType("text/html");
		ctx.getRequestDispatcher("/"+page).forward(req, resp);
	}
}