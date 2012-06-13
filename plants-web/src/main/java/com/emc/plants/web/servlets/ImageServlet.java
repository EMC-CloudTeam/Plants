//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2002
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.web.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;

import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.utils.Util;



/**
 * Servlet to handle image actions.
 */
public class ImageServlet extends HttpServlet 
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	@EJB(beanName="Catalog")
	@Autowired
	private Catalog catalog;

   /**
    * Servlet initialization.
    */

   public void init(ServletConfig config) throws ServletException
   {
      super.init(config);
      this.catalog = (Catalog)Util.getSpringBean("catalog");
      Util.setDebug(true);
   }


   /**
    * Process incoming HTTP GET requests
    *
    * @param request Object that encapsulates the request to the servlet
    * @param response Object that encapsulates the response from the servlet
    */
   public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
           throws ServletException, IOException
   {
      performTask(request,response);
   }

   /**
    * Process incoming HTTP POST requests
    *
    * @param request Object that encapsulates the request to the servlet
    * @param response Object that encapsulates the response from the servlet
    */
   public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
           throws ServletException, IOException
   {
      performTask(request,response);
   }	

  /**
    * Main service method for ImageServlet
    *
    * @param request Object that encapsulates the request to the servlet
    * @param response Object that encapsulates the response from the servlet
    */    	
   private void performTask(HttpServletRequest req, HttpServletResponse resp)
           throws ServletException, IOException 
   {
      String action = null;

      action = req.getParameter("action");
      Util.debug("action=" + action);

      if (action.equals("getimage"))
      {
         String inventoryID = req.getParameter("inventoryID");

         byte[] buf = catalog.getItemImageBytes(inventoryID);
		if (buf != null)
		{
		   resp.setContentType("image/jpeg");
		   resp.getOutputStream().write(buf);
		}
      }
   }

   /** 
    * send redirect
    */
   private void sendRedirect(HttpServletResponse resp, String page)
           throws ServletException, IOException 
   {
      resp.sendRedirect(resp.encodeRedirectURL(page));
   }

   /** 
    * Request dispatch.
    */
   private void requestDispatch(
           ServletContext ctx, 
           HttpServletRequest req, 
           HttpServletResponse resp, 
           String page)
           throws ServletException, IOException {
           ctx.getRequestDispatcher("/"+page).include(req, resp);
   }
}