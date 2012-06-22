package com.emc.plants.web.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.utils.Util;

@Controller
@RequestMapping(value="/account")
public class AccountController {
	
	public static final String ACTION_ACCOUNT = "account";
	public static final String ACTION_LOGIN = "login";
	
	@RequestMapping(method = RequestMethod.GET)
	public String account(HttpServletRequest req) throws ServletException {
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
        return url;
	}

}
