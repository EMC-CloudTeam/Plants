package com.emc.plants.web.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping(value="/login")
public class LoginController {
	
	public static final String ACTION_ACCOUNT       = "account";
   public static final String ACTION_ACCOUNTUPDATE = "accountUpdate";
   public static final String ACTION_LOGIN         = "login";
   public static final String ACTION_REGISTER      = "register";
   public static final String ACTION_SETLOGGING    = "SetLogging";
   public static final String ACTION_ORDERINFO    = "orderinfo";
   public static final String ACTION_PROMO_HTML    = "promo";
   public static final String ACTION_SHOPPING    = "shopping";
	
//	@Autowired
//	private HttpServletRequest req;
	
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
	public String login(HttpServletRequest req) throws ServletException {
		
//		HttpServletRequest req =  ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()) .getRequest();
		System.out.println(" Entered login :: with ModelMap :: " );
	         try
	         {
	            HttpSession session = req.getSession(true);
	            String userid = req.getParameter("userid");
	            String passwd = req.getParameter("passwd");
	            System.out.println("LoginController:: "+userid);
	            String updating = req.getParameter(Util.ATTR_UPDATING);

	            String results= null;
	            if (Util.validateString(userid)){
	            	results= login.verifyUserAndPassword(userid, passwd);
	            }
	            else
	            {
	            	//user id was invalid, and may contain XSS attack
	            	results = "\nEmail address was invalid.";
					Util.debug("User id or email address was invalid. id=" + userid);
	            }

	            // If results have an error msg, return it, otherwise continue.
	            if (results != null)
	            {
	               // Proliferate UPDATING flag if user is trying to update his account.
	               if (updating.equals("true"))
	                  req.setAttribute(Util.ATTR_UPDATING, "true");

	               req.setAttribute(Util.ATTR_RESULTS, results);
	               return ACTION_LOGIN;
	            }
	            else
	            {
	               // If not logging in for the first time, then clear out the
	               // session data for the old user.
	               if (session.getAttribute(Util.ATTR_CUSTOMER) != null)
	               {
	                  session.removeAttribute(Util.ATTR_CART);
	                  session.removeAttribute(Util.ATTR_CART_CONTENTS);
	                  session.removeAttribute(Util.ATTR_CHECKOUT);
	                  session.removeAttribute(Util.ATTR_ORDERKEY);
	               }

	               // Store customer userid in HttpSession.
	               CustomerInfo customerInfo = login.getCustomerInfo(userid);
	               session.setAttribute(Util.ATTR_CUSTOMER, customerInfo);
	               Util.debug("updating=" + updating + "=");

	               // Was customer trying to edit account information.
	               if (updating.equals("true"))
	               {
	                  req.setAttribute(Util.ATTR_EDITACCOUNTINFO, customerInfo);

	                  return ACTION_ACCOUNT;
	               }
	               else
	               {
	                  // See if user was in the middle of checking out.
	                  Boolean checkingOut = (Boolean) session.getAttribute(Util.ATTR_CHECKOUT);
	                  Util.debug("checkingOut=" + checkingOut + "=");
	                  if ((checkingOut != null) && (checkingOut.booleanValue()))
	                  {
	                     Util.debug("must be checking out");
	                     return ACTION_ORDERINFO;
	                  }
	                  else
	                  {
	                     Util.debug("must NOT be checking out");
	                     String url;
	                     String category = (String) session.getAttribute(Util.ATTR_CATEGORY);

	                     // Default to plants
	                      Util.debug("category : "+category);
	                     if ((category == null) || (category.equals("null")))
	                     {
	                        url = ACTION_PROMO_HTML;
	                     }
	                     else
	                     {
	                        url = ACTION_SHOPPING;
	                        req.setAttribute(Util.ATTR_INVITEMS,
	                                         catalog.getItemsByCategory(Integer.parseInt(category)));
	                     }

	                     return url;
	                  }
	               }
	            }
	         }
	         catch (Exception e)
	         {
	            req.setAttribute(Util.ATTR_RESULTS, "/nException occurred");
	            e.printStackTrace();
	            throw new ServletException(e.getMessage());
	         }
	     
		
		
 
	}



}
