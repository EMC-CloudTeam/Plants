package com.emc.plants.web.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.plants.persistence.Inventory;
import com.emc.plants.pojo.beans.BackOrderItem;
import com.emc.plants.pojo.beans.SupplierInfo;
import com.emc.plants.service.exceptions.NoSupplierException;
import com.emc.plants.service.interfaces.BackOrderStock;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;
import com.emc.plants.service.interfaces.ResetDB;
import com.emc.plants.service.interfaces.Suppliers;
import com.emc.plants.utils.Util;
import com.emc.plants.web.servlets.Populate;
import com.emc.plants.web.servlets.ReceiveOrdersJAXRPCProxy;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	public static Logger logger = Logger.getLogger(AdminController.class);

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
	
	@PostConstruct
	public void init(){
		logger.debug("AdminController:init");
		Util.setDebug(true);
        
        this.suppliers = (Suppliers) Util.getSpringBean("suppliersBean");
        this.login = (Login) Util.getSpringBean("login");
        this.backOrderStock = (BackOrderStock) Util.getSpringBean("backOrderStockBean");
        this.catalog = (Catalog) Util.getSpringBean("catalog");
        this.resetDB = (ResetDB) Util.getSpringBean("resetDBBean");
//		model.addAttribute("message", "Spring 3 MVC Hello World");
        
	}
	
	/**
     * Method performTask.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
	@RequestMapping(method=RequestMethod.GET)
	public String performTask(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException {
		logger.debug("AdminController:performTask");
		String admintype = null;
		admintype = req.getParameter(Util.ATTR_ADMINTYPE);
		logger.debug("inside AdminController:performTask. admintype=" + admintype);
		if ((admintype == null) || (admintype.equals(""))) {
			// Invalid Admin
			// requestDispatch(req.getSession().getServletContext(), req, resp,
			// Util.PAGE_ADMINHOME);
			admintype = "admin";
		}
		if (admintype.equals(Util.ADMIN_BACKORDER)) {
			admintype = performBackOrder(req, resp);
		} else if (admintype.equals(Util.ADMIN_SUPPLIERCFG)) {
			admintype = performSupplierConfig(req, resp);
		} else if (admintype.equals(Util.ADMIN_POPULATE)) {
			admintype = performPopulate(req, resp);
		}
		return admintype;
	}
	
    
    
	/**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public String performSupplierConfig(HttpServletRequest req,
    		HttpServletResponse resp) throws ServletException {
    	logger.debug("AdminController:performSupplierConfig");
        SupplierInfo supplierInfo = null;
        String action = null;
        action = req.getParameter(Util.ATTR_ACTION);
        if ((action == null) || (action.equals("")))
            action = Util.ACTION_GETSUPPLIER;
        logger.debug("AdminController.performSupplierConfig() - action=" + action);
        HttpSession session = req.getSession(true);
        if (action.equals(Util.ACTION_GETSUPPLIER)) {
            // Get supplier info
            try {
                supplierInfo = suppliers.getSupplierInfo();
            } catch (Exception e) {
                logger.debug("AdminController.performSupplierConfig() Exception: " + e);
            }
        } else if (action.equals(Util.ACTION_UPDATESUPPLIER)) {
            String supplierID = req.getParameter("supplierid");
            logger.debug("AdminController.performSupplierConfig() - supplierid = " + supplierID);
            if ((supplierID != null) && (!supplierID.equals(""))) {
                String name = req.getParameter("name");
                String street = req.getParameter("street");
                String city = req.getParameter("city");
                String state = req.getParameter("state");
                String zip = req.getParameter("zip");
                String phone = req.getParameter("phone");
                String location_url = req.getParameter("location_url");
                supplierInfo = updateSupplierInfo(supplierID, name, street, city, state, zip, phone, location_url);
            }
        } else {
            // Unknown Supplier Config Admin Action so go back to the Administration home page
//            sendRedirect(resp, "/plants-web/" + Util.PAGE_ADMINHOME);
            return Util.ADMIN;
        }
        session.setAttribute(Util.ATTR_SUPPLIER, supplierInfo);
//        requestDispatch(req.getSession().getServletContext(), req, resp, Util.PAGE_SUPPLIERCFG);
        return Util.SUPPLIERCFG;
    }
    /**
     * @param req
     * @param resp
     * @throws ServletException
     */
	public String performPopulate(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException {
    	logger.debug("AdminController:performPopulate");
        Populate popDB = new Populate(resetDB,catalog,login,backOrderStock,suppliers);
        popDB.doPopulate();
//        sendRedirect(resp, "/plants-web/" + Util.PAGE_HELP);
        return Util.HELP;
    }
	
	/**
     * Method performBackOrder.
     * @param req
     * @param resp
     * @throws ServletException
     */
	public String performBackOrder(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException {
    	logger.debug("AdminController:performBackOrder");
        String action = null;
        action = req.getParameter(Util.ATTR_ACTION);
        if ((action == null) || (action.equals("")))
            action = Util.ACTION_GETBACKORDERS;
        logger.debug("AdminController.performBackOrder() - action=" + action);
        HttpSession session = req.getSession(true);
        if (action.equals(Util.ACTION_GETBACKORDERS)) {
            getBackOrders(session);
            //requestDispatch(req.getSession().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
            return Util.BACKORDERADMIN;
        } else if (action.equals(Util.ACTION_ORDERSTOCK)) {
            String[] backOrderIDs = (String[]) req.getParameterValues("selectedObjectIds");
            if (backOrderIDs != null) {
                for (int i = 0; i < backOrderIDs.length; i++) {
                    long backOrderID = Long.parseLong(backOrderIDs[i]);
                    logger.debug("AdminController.performBackOrder() - Selected BackOrder backOrderID: " + backOrderID);
                    try {
                        String inventoryID = backOrderStock.getBackOrderInventoryID(backOrderID);
                        logger.debug("AdminController.performBackOrder() - backOrderID = " + inventoryID);
                        int quantity = backOrderStock.getBackOrderQuantity(backOrderID);
                        String inputQuantity = req.getParameter("itemqty" + backOrderID);
                        logger.debug("AdminController.performBackOrder() - itemqty" + backOrderID + " = " + inputQuantity);
                        if ((inputQuantity != null) && (!inputQuantity.equals(""))) {
                            if (!inputQuantity.equals(String.valueOf(quantity))) {
                                // Administrator has modified the quantity on the form for this Back Order
                                quantity = new Integer(inputQuantity).intValue();
                                backOrderStock.setBackOrderQuantity(backOrderID, quantity);
                            }
                        }
                        logger.debug("AdminController.performBackOrder() - quantity: " + quantity);
                        try {
                            // Mark the Stock Order as ordered.
                            backOrderStock.orderStock(backOrderID, quantity);
                            //  Send the Stock Order to the Supplier.
                            String trackNum = purchaseInventory(backOrderID, inventoryID, quantity);
                            if (trackNum != null) {
                                if (!trackNum.equals("0")) {
                                    req.setAttribute(Util.ATTR_RESULTS, "The Stock Order has successfully been sent to the Supplier.  Tracking number is  " + trackNum);
                                    // Save the returned Supplier Order ID with the back order.  This Supplier Order ID (or tracking number) is used to check the order status.
                                    backOrderStock.setSupplierOrderID(backOrderID, trackNum);
                                } else {
                                    throw new NoSupplierException("Sending the Stock Order to the Supplier has failed.");
                                }
                            } else {
                                throw new NoSupplierException("Sending the Stock Order to the Supplier has failed.");
                                //logger.debug("AdminController.performBackOrder() - trackNum returned from Supplier is null.  Ignoring trackNum.");
                                //req.setAttribute(Util.ATTR_RESULTS, "The Stock Order has successfully been sent to the Supplier.  Tracking number is unknown");
                            }
                        } catch (NoSupplierException e) {
                            // Sending the Stock Order to the Supplier has failed.
                            logger.debug("ReceiveOrdersJAXRPCProxy.performBackOrder() -  NoSupplierException: " + e);
                            req.setAttribute(Util.ATTR_RESULTS, "Sending the Stock Order to the Supplier has failed.  Check the status of the Supplier.");
                            backOrderStock.abortorderStock(backOrderID);
                        }
                    }  catch (Exception e) {
                        logger.debug("AdminController.performBackOrder() - Exception: " + e);
                        e.printStackTrace();
                    }
                }
            }
            getBackOrders(session);
//            requestDispatch(req.getSession().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
            return Util.BACKORDERADMIN;

        } else if (action.equals(Util.ACTION_ORDERSTATUS)) {
            // Check the order status from the Supplier.
            logger.debug("AdminController.performBackOrder() - AdminController(performTask):  Order Status Action");
            String[] backOrderIDs =  req.getParameterValues("selectedObjectIds");
            if (backOrderIDs != null) {
                for (int i = 0; i < backOrderIDs.length; i++) {
                    long backOrderID = Long.parseLong(backOrderIDs[i]);
                    logger.debug("AdminController.performBackOrder() - Selected BackOrder backOrderID: " + backOrderID);
                    try {
                        String trackNum = backOrderStock.getSupplierOrderID(backOrderID);
                        if (checkStatus(trackNum)) {
                            // Supplier indicates that the order has been completed.
                            // For our purposes, we can assume that the order is received.
                            backOrderStock.receiveConfirmation(backOrderID);
                        }
                    } catch (Exception e) {
                        logger.debug("AdminController.performBackOrder() - Exception: " + e);
                        e.printStackTrace();
                    }
               }
            }
            getBackOrders(session);
//            requestDispatch(req.getSession().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
            return Util.BACKORDERADMIN;
        } else if (action.equals(Util.ACTION_UPDATESTOCK)) {
            logger.debug("AdminController.performBackOrder() - AdminController(performTask):  Update Stock Action");
            String[] backOrderIDs = (String[]) req.getParameterValues("selectedObjectIds");
            if (backOrderIDs != null) {
                for (int i = 0; i < backOrderIDs.length; i++) {
                    long backOrderID = Long.parseLong(backOrderIDs[i]);
                    logger.debug("AdminController.performBackOrder() - Selected BackOrder backOrderID: " + backOrderID);
                    try {
                        String inventoryID = backOrderStock.getBackOrderInventoryID(backOrderID);
                        logger.debug("AdminController.performBackOrder() - backOrderID = " + inventoryID);
                        int quantity = backOrderStock.getBackOrderQuantity(backOrderID);
                        catalog.setItemQuantity(inventoryID, quantity);
                        // Update the BackOrder status
                        logger.debug("AdminController.performBackOrder() - quantity: " + quantity);
                        backOrderStock.updateStock(backOrderID, quantity);
                        // catalog.remove();
                    } catch (Exception e) {
                        logger.debug("AdminController.performBackOrder() - Exception: " + e);
                        e.printStackTrace();
                    }
                }
            }
            getBackOrders(session);
//            requestDispatch(req.getSession().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
            return Util.BACKORDERADMIN;
        } else if (action.equals(Util.ACTION_CANCEL)) {
            logger.debug("AdminController.performBackOrder() - AdminController(performTask):  Cancel Action");
            String[] backOrderIDs = (String[]) req.getParameterValues("selectedObjectIds");
            
            if (backOrderIDs != null) {
                for (int i = 0; i < backOrderIDs.length; i++) {
                    long backOrderID = Long.parseLong(backOrderIDs[i]);
                    logger.debug("AdminController.performBackOrder() - Selected BackOrder backOrderID: " + backOrderID);
                    try {
                        backOrderStock.deleteBackOrder(backOrderID);
                    } catch (Exception e) {
                        logger.debug("AdminController.performBackOrder() - Exception: " + e);
                        e.printStackTrace();
                    }
                }
            }
            getBackOrders(session);
//            requestDispatch(req.getSession().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
            return Util.BACKORDERADMIN;
        } else if (action.equals(Util.ACTION_UPDATEQUANTITY)) {
            logger.debug("AdminController.performBackOrder() -  Update Quantity Action");
            try {
                String backOrderIDs = req.getParameter("backOrderID");
                if (backOrderIDs != null) {
                    long backOrderID = Long.parseLong(backOrderIDs);
                    logger.debug("AdminController.performBackOrder() - backOrderID = " + backOrderID);
                    String paramquantity = req.getParameter("itemqty");
                    if (paramquantity != null) {
                        int quantity = new Integer(paramquantity).intValue();
                        logger.debug("AdminController.performBackOrder() - quantity: " + quantity);
                        backOrderStock.setBackOrderQuantity(backOrderID, quantity);
                    }
                }
            } catch (Exception e) {
                logger.debug("AdminController.performBackOrder() - Exception: " + e);
                e.printStackTrace();
            }
            getBackOrders(session);
//            requestDispatch(req.getSession().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
            return Util.BACKORDERADMIN;
        } else {
            // Unknown Backup Admin Action so go back to the Administration home page
//            sendRedirect(resp, "/plants-web/" + Util.PAGE_ADMINHOME);
        	return Util.ADMIN;
        }
    }
	
	/**
     * Method getBackOrders.
     * @param session
     */
    public void getBackOrders(HttpSession session) {
    	logger.debug("AdminController:getBackOrders");
        try {
            // Get the list of back order items.
            logger.debug("AdminController.getBackOrders() - Looking for BackOrders");
            Collection<BackOrderItem> backOrderItems = backOrderStock.findBackOrderItems();
            if (backOrderItems != null) {
                logger.debug("AdminController.getBackOrders() - BackOrders found!");
                Iterator<BackOrderItem> i = backOrderItems.iterator();
                while (i.hasNext()) {
                    BackOrderItem backOrderItem = (BackOrderItem) i.next();
                    long backOrderID = backOrderItem.getBackOrderID();
                    String inventoryID = backOrderItem.getInventory().getInventoryId();
                    // Get the inventory quantity and name for the back order item information.
                    Inventory item = catalog.getItemInventory(inventoryID);
                    int quantity = item.getQuantity();
                    backOrderItem.setInventoryQuantity(quantity);
                    String name = item.getName();
                    // catalog.remove();
                    backOrderItem.setName(name);
                    // Don't include backorders that have been completed.
                    if (!(backOrderItem.getStatus().equals(Util.STATUS_ADDEDSTOCK))) {
                        String invID = backOrderItem.getInventory().getInventoryId();
                        String supplierOrderID = backOrderItem.getSupplierOrderID();
                        String status = backOrderItem.getStatus();
                        String lowDate = new Long(backOrderItem.getLowDate()).toString();
                        String orderDate = new Long(backOrderItem.getOrderDate()).toString();
                        logger.debug("AdminController.getBackOrders() - backOrderID = " + backOrderID);
                        logger.debug("AdminController.getBackOrders() - supplierOrderID = " + supplierOrderID);
                        logger.debug("AdminController.getBackOrders() -    invID = " + invID);
                        logger.debug("AdminController.getBackOrders() -    name = " + name);
                        logger.debug("AdminController.getBackOrders() -    quantity = " + quantity);
                        logger.debug("AdminController.getBackOrders() -    status = " + status);
                        logger.debug("AdminController.getBackOrders() -    lowDate = " + lowDate);
                        logger.debug("AdminController.getBackOrders() -    orderDate = " + orderDate);
                    }
                }
            } else {
                logger.debug("AdminController.getBackOrders() - NO BackOrders found!");
            }
            session.setAttribute("backorderitems", backOrderItems);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("AdminController.getBackOrders() - RemoteException: " + e);
        }
    }
    
    /**
     * @param supplierID
     * @param name
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param phone
     * @param location_url
     * @return supplierInfo
     */
	public SupplierInfo updateSupplierInfo(String supplierID, String name,
			String street, String city, String state, String zip, String phone,
			String location_url) {
    	logger.debug("AdminController:updateSupplierInfo");
        // Only retrieving info for 1 supplier.
        SupplierInfo supplierInfo = null;
        try {
            supplierInfo = suppliers.updateSupplier(supplierID, name, street, city, state, zip, phone, location_url);
        } catch (Exception e) {
            logger.debug("AdminController.updateSupplierInfo() - Exception: " + e);
        }
        return (supplierInfo);
    }
    
	/**
     * Method purchaseInventory.
     * @param invID
     * @param amountToOrder
     * @returns orderID
     */
	private String purchaseInventory(long backOrderID, String invID,
			int amountToOrder) throws NoSupplierException {
		logger.debug("AdminController:purchaseInventory");
		String customerID = "PBW0001";
		String trackNum = "0";
		try {
			// Use JAXRPC to send the order to the Supplier. A supplier tracking
			// order ID is returned.
			ReceiveOrdersJAXRPCProxy supplierOrdersProxy = new ReceiveOrdersJAXRPCProxy(suppliers);
			trackNum = supplierOrdersProxy.sendOrder(customerID, backOrderID, invID, amountToOrder);
			if (trackNum.equals("0")) {
				logger.debug("AdminController.purchaseInventory() - sendOrder has failed with rc = "
						+ trackNum);
			}
		} catch (NoSupplierException e) {
			logger.debug("AdminController.purchaseInventory() -  NoSupplierException: " + e);
			throw e;
		} catch (Exception e) {
			logger.debug("AdminController.purchaseInventory() - Exception: " + e);
			e.printStackTrace();
		}
		return (trackNum);
	}
	
	/**
     * Method checkStatus.
     * @param backOrderID
     * @returns status of order
     */
    private boolean checkStatus(String trackNum) throws NoSupplierException {
    	logger.debug("AdminController:purchaseInventory");    	
        boolean orderStatus = false;
        try {
            // Use JAXRPC to query the supplier for the order status.
            ReceiveOrdersJAXRPCProxy supplierOrdersProxy = new ReceiveOrdersJAXRPCProxy(suppliers);
            orderStatus = supplierOrdersProxy.checkOrderStatus(trackNum);
        } catch (NoSupplierException e) {
            logger.debug("AdminController.checkStatus() -  NoSupplierException: " + e);
            throw e;
        } catch (Exception e) {
            logger.debug("AdminController.checkStatus() - Exception: " + e);
            e.printStackTrace();
        }
        return (orderStatus);
    }
	
    /**
     * Method sendRedirect.
     * @param resp
     * @param page
     * @throws ServletException
     * @throws IOException
     */
    /*private void sendRedirect(HttpServletResponse resp, String page) throws ServletException, IOException {
    	logger.debug("AdminController:sendRedirect");     	
        resp.sendRedirect(resp.encodeRedirectURL(page));
    }*/
    
    /**
     * Method requestDispatch.
     * @param ctx
     * @param req
     * @param resp
     * @param page
     * @throws ServletException
     * @throws IOException
     */
    /** 
     * Request dispatch
     */
	/*private void requestDispatch(ServletContext ctx, HttpServletRequest req,
			HttpServletResponse resp, String page) throws ServletException,
			IOException {
		logger.debug("AdminController:requestDispatch");
		resp.setContentType("text/html");
		ctx.getRequestDispatcher("/" + page).forward(req, resp);
	}*/
}
