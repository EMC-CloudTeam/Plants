package com.emc.plants.web.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping(value="/accountUpdate")
public class AccountUpdateController {
	
	public static final String ACTION_ACCOUNT = "account";
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_ORDERINFO    = "orderinfo";
	public static final String ACTION_PROMO_HTML    = "promo";
	public static final String ACTION_SHOPPING    = "shopping";
	
	@Autowired
	   private Login login;
	 
	 @Autowired
	   private Catalog catalog;
	 
	 /**
	    * Replacement for Servlet initialization.
	    */
	 @PostConstruct
	   public void init() throws ServletException
	   {
		   this.login = (Login)Util.getSpringBean("login");
		   this.catalog = (Catalog)Util.getSpringBean("catalog");
	      Util.setDebug(true);
	   }
	
	@RequestMapping(method = RequestMethod.POST)
	public String accountUpdate(HttpServletRequest req) throws ServletException {

         String url;
         HttpSession session = req.getSession(true);
         CustomerInfo customerInfo = (CustomerInfo) session.getAttribute(Util.ATTR_CUSTOMER);
         if (customerInfo == null)
         {
            url = ACTION_LOGIN;
            req.setAttribute(Util.ATTR_UPDATING, "true");
            req.setAttribute(Util.ATTR_RESULTS, "\nYou must login first.");
         }
         else
         {
            url = ACTION_ACCOUNT;
            req.setAttribute(Util.ATTR_EDITACCOUNTINFO, customerInfo);
         }

         String userid = customerInfo.getCustomerID();
         String firstName = req.getParameter("fname");
         String lastName = req.getParameter("lname");
         String addr1 = req.getParameter("addr1");
         String addr2 = req.getParameter("addr2");
         String addrCity = req.getParameter("city");
         String addrState = req.getParameter("state");
         String addrZip = req.getParameter("zip");
         String phone = req.getParameter("phone");

         // Create the new user.
         customerInfo = login.updateUser(userid, firstName, lastName,
                                         addr1, addr2, addrCity,
                                         addrState, addrZip, phone);
         // Store updated customer info in HttpSession.
         session.setAttribute(Util.ATTR_CUSTOMER, customerInfo);

         // See if user was in the middle of checking out.
         Boolean checkingOut = (Boolean) session.getAttribute(Util.ATTR_CHECKOUT);
         if ((checkingOut != null) && (checkingOut.booleanValue()))
         {
            url = ACTION_ORDERINFO;
         }
         else
         {
            String category = (String) session.getAttribute(Util.ATTR_CATEGORY);

            // Default to plants
            if (category == null)
            {
               url = ACTION_PROMO_HTML;
            }
            else
            {
               url = ACTION_SHOPPING;
               req.setAttribute(Util.ATTR_INVITEMS,
                                catalog.getItemsByCategory(Integer.parseInt(category)));
            }
         }

         return url;
   
	}

}
