package com.emc.plants.web.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping(value="/register")
public class RegistrationController {
	
	public static final String ACTION_REGISTER = "register";
	public static final String ACTION_ORDERINFO = "orderinfo";
    public static final String ACTION_PROMO_HTML = "promo";
    public static final String ACTION_SHOPPING = "shopping";
	
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
	 public String register(HttpServletRequest req, @RequestParam("action") String action) throws ServletException {
		 

         // Register a new user.
//         try
//         {
            String url;
            HttpSession session = req.getSession(true);

            String userid = req.getParameter("userid");
            String password = req.getParameter("passwd");
            String cpassword = req.getParameter("vpasswd");
            String firstName = req.getParameter("fname");
            String lastName = req.getParameter("lname");
            String addr1 = req.getParameter("addr1");
            String addr2 = req.getParameter("addr2");
            String addrCity = req.getParameter("city");
            String addrState = req.getParameter("state");
            String addrZip = req.getParameter("zip");
            String phone = req.getParameter("phone");

            //validate all user input
            //This could be done more eloquently using a framework such as Struts...
            if (!Util.validateString(userid)){
            	req.setAttribute(Util.ATTR_RESULTS, "Email address contains invalid characters.");
                url = ACTION_REGISTER;
            }
            else if (!Util.validateString(firstName)){
            	req.setAttribute(Util.ATTR_RESULTS, "First Name contains invalid characters.");
                url = ACTION_REGISTER;
            }
            else if (!Util.validateString(lastName)){
            	req.setAttribute(Util.ATTR_RESULTS, "Last Name contains invalid characters.");
                url = ACTION_REGISTER;
            }
            else if (!Util.validateString(addr1)){
            	req.setAttribute(Util.ATTR_RESULTS, "Address Line 1 contains invalid characters.");
                url = ACTION_REGISTER;
            }
            else if (!Util.validateString(addr2)){
            	req.setAttribute(Util.ATTR_RESULTS, "Address Line 2 contains invalid characters.");
                url = ACTION_REGISTER;
            }
            else if (!Util.validateString(addrCity)){
            	req.setAttribute(Util.ATTR_RESULTS, "City contains invalid characters.");
                url = ACTION_REGISTER;
            }
            else if (!Util.validateString(addrState)){
            	req.setAttribute(Util.ATTR_RESULTS, "State contains invalid characters.");
                url = ACTION_REGISTER;
            }
            else if (!Util.validateString(addrZip)){
            	req.setAttribute(Util.ATTR_RESULTS, "Zip contains invalid characters.");
                url = ACTION_REGISTER;
            }
            else if (!Util.validateString(phone)){
            	req.setAttribute(Util.ATTR_RESULTS, "Phone Number contains invalid characters.");
                url = ACTION_REGISTER;
            }
            // Make sure passwords match.
            else if (!password.equals(cpassword))
            {
               req.setAttribute(Util.ATTR_RESULTS, "Passwords do not match.");
               url = ACTION_REGISTER;
            }
            else
            {
               // Create the new user.
               CustomerInfo customerInfo =
                     login.createNewUser(userid, password, firstName,
                                         lastName, addr1, addr2,
                                         addrCity, addrState, addrZip, phone);

               if (customerInfo != null)
               {
                  // Store customer info in HttpSession.
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
               }
               else
               {
                  url = ACTION_REGISTER;
                  req.setAttribute(Util.ATTR_RESULTS, "New user NOT created!");
               }
            }
            return url;
//         }
//         catch (CreateException e) { }
      
	 }

}
