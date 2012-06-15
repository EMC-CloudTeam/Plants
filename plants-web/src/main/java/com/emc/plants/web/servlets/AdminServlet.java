//
// "This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
// instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
// or for redistribution by customer, as part of such an application, in customer's own products. " 
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2003,2004
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.web.servlets;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

/**
 * Servlet to handle Administration actions
 */
public class AdminServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	@EJB(name="Suppliers")
	private Suppliers suppliers = null;
//	@EJB(name="Login")
    private Login login;
//	@EJB(name="BackOrderStock")
    private BackOrderStock backOrderStock = null;
	
//	@EJB(name="Catalog")
    private Catalog catalog = null;
	
//	@EJB(name="resetDB")
    private ResetDB resetDB;
    /**
     * @see javax.servlet.Servlet#init(ServletConfig)
     */
    /**
     * Servlet initialization.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Uncomment the following to generated debug code.
         Util.setDebug(true);
         
         this.suppliers = (Suppliers) Util.getSpringBean("suppliersBean");
         this.login = (Login) Util.getSpringBean("login");
         this.backOrderStock = (BackOrderStock) Util.getSpringBean("backOrderStockBean");
         this.catalog = (Catalog) Util.getSpringBean("catalog");
         this.resetDB = (ResetDB) Util.getSpringBean("resetDBBean");
        
    }
    /**
     * Process incoming HTTP GET requests
     *
     * @param req Object that encapsulates the request to the servlet
     * @param resp Object that encapsulates the response from the servlet
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        performTask(req, resp);
    }
    /**
     * Process incoming HTTP POST requests
     *
     * @param req Object that encapsulates the request to the servlet
     * @param resp Object that encapsulates the response from the servlet
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        performTask(req, resp);
    }
    /**
     * Method performTask.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String admintype = null;
        admintype = req.getParameter(Util.ATTR_ADMINTYPE);
        Util.debug("inside AdminServlet:performTask. admintype="+admintype);
        if ((admintype == null) || (admintype.equals(""))) {
            // Invalid Admin
            requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_ADMINHOME);
        }
        if (admintype.equals(Util.ADMIN_BACKORDER)) {
            performBackOrder(req, resp);
        } else if (admintype.equals(Util.ADMIN_SUPPLIERCFG)) {
            performSupplierConfig(req, resp);
        } else if (admintype.equals(Util.ADMIN_POPULATE)) {
            performPopulate(req, resp);
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
    public SupplierInfo updateSupplierInfo(String supplierID, String name, String street, String city, String state, String zip, String phone, String location_url) {
        // Only retrieving info for 1 supplier.
        SupplierInfo supplierInfo = null;
        try {
            supplierInfo = suppliers.updateSupplier(supplierID, name, street, city, state, zip, phone, location_url);
        } catch (Exception e) {
            Util.debug("AdminServlet.updateSupplierInfo() - Exception: " + e);
        }
        return (supplierInfo);
    }
    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void performSupplierConfig(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SupplierInfo supplierInfo = null;
        String action = null;
        action = req.getParameter(Util.ATTR_ACTION);
        if ((action == null) || (action.equals("")))
            action = Util.ACTION_GETSUPPLIER;
        Util.debug("AdminServlet.performSupplierConfig() - action=" + action);
        HttpSession session = req.getSession(true);
        if (action.equals(Util.ACTION_GETSUPPLIER)) {
            // Get supplier info
            try {
                supplierInfo = suppliers.getSupplierInfo();
            } catch (Exception e) {
                Util.debug("AdminServlet.performSupplierConfig() Exception: " + e);
            }
        } else if (action.equals(Util.ACTION_UPDATESUPPLIER)) {
            String supplierID = req.getParameter("supplierid");
            Util.debug("AdminServlet.performSupplierConfig() - supplierid = " + supplierID);
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
            sendRedirect(resp, "/plants-web/" + Util.PAGE_ADMINHOME);
        }
        session.setAttribute(Util.ATTR_SUPPLIER, supplierInfo);
        requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_SUPPLIERCFG);
    }
    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void performPopulate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Populate popDB = new Populate(resetDB,catalog,login,backOrderStock,suppliers);
        popDB.doPopulate();
        sendRedirect(resp, "/plants-web/" + Util.PAGE_HELP);
    }
    /**
     * Method performBackOrder.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void performBackOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = null;
        action = req.getParameter(Util.ATTR_ACTION);
        if ((action == null) || (action.equals("")))
            action = Util.ACTION_GETBACKORDERS;
        Util.debug("AdminServlet.performBackOrder() - action=" + action);
        HttpSession session = req.getSession(true);
        if (action.equals(Util.ACTION_GETBACKORDERS)) {
            getBackOrders(session);
            requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
        } else if (action.equals(Util.ACTION_ORDERSTOCK)) {
            String[] backOrderIDs = (String[]) req.getParameterValues("selectedObjectIds");
            if (backOrderIDs != null) {
                for (int i = 0; i < backOrderIDs.length; i++) {
                    long backOrderID = Long.parseLong(backOrderIDs[i]);
                    Util.debug("AdminServlet.performBackOrder() - Selected BackOrder backOrderID: " + backOrderID);
                    try {
                        String inventoryID = backOrderStock.getBackOrderInventoryID(backOrderID);
                        Util.debug("AdminServlet.performBackOrder() - backOrderID = " + inventoryID);
                        int quantity = backOrderStock.getBackOrderQuantity(backOrderID);
                        String inputQuantity = req.getParameter("itemqty" + backOrderID);
                        Util.debug("AdminServlet.performBackOrder() - itemqty" + backOrderID + " = " + inputQuantity);
                        if ((inputQuantity != null) && (!inputQuantity.equals(""))) {
                            if (!inputQuantity.equals(String.valueOf(quantity))) {
                                // Administrator has modified the quantity on the form for this Back Order
                                quantity = new Integer(inputQuantity).intValue();
                                backOrderStock.setBackOrderQuantity(backOrderID, quantity);
                            }
                        }
                        Util.debug("AdminServlet.performBackOrder() - quantity: " + quantity);
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
                                //Util.debug("AdminServlet.performBackOrder() - trackNum returned from Supplier is null.  Ignoring trackNum.");
                                //req.setAttribute(Util.ATTR_RESULTS, "The Stock Order has successfully been sent to the Supplier.  Tracking number is unknown");
                            }
                        } catch (NoSupplierException e) {
                            // Sending the Stock Order to the Supplier has failed.
                            Util.debug("ReceiveOrdersJAXRPCProxy.performBackOrder() -  NoSupplierException: " + e);
                            req.setAttribute(Util.ATTR_RESULTS, "Sending the Stock Order to the Supplier has failed.  Check the status of the Supplier.");
                            backOrderStock.abortorderStock(backOrderID);
                        }
                    }  catch (Exception e) {
                        Util.debug("AdminServlet.performBackOrder() - Exception: " + e);
                        e.printStackTrace();
                    }
                }
            }
            getBackOrders(session);
            requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
        } else if (action.equals(Util.ACTION_ORDERSTATUS)) {
            // Check the order status from the Supplier.
            Util.debug("AdminServlet.performBackOrder() - AdminServlet(performTask):  Order Status Action");
            String[] backOrderIDs =  req.getParameterValues("selectedObjectIds");
            if (backOrderIDs != null) {
                for (int i = 0; i < backOrderIDs.length; i++) {
                    long backOrderID = Long.parseLong(backOrderIDs[i]);
                    Util.debug("AdminServlet.performBackOrder() - Selected BackOrder backOrderID: " + backOrderID);
                    try {
                        String trackNum = backOrderStock.getSupplierOrderID(backOrderID);
                        if (checkStatus(trackNum)) {
                            // Supplier indicates that the order has been completed.
                            // For our purposes, we can assume that the order is received.
                            backOrderStock.receiveConfirmation(backOrderID);
                        }
                    } catch (Exception e) {
                        Util.debug("AdminServlet.performBackOrder() - Exception: " + e);
                        e.printStackTrace();
                    }
               }
            }
            getBackOrders(session);
            requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
        } else if (action.equals(Util.ACTION_UPDATESTOCK)) {
            Util.debug("AdminServlet.performBackOrder() - AdminServlet(performTask):  Update Stock Action");
            String[] backOrderIDs = (String[]) req.getParameterValues("selectedObjectIds");
            if (backOrderIDs != null) {
                for (int i = 0; i < backOrderIDs.length; i++) {
                    long backOrderID = Long.parseLong(backOrderIDs[i]);
                    Util.debug("AdminServlet.performBackOrder() - Selected BackOrder backOrderID: " + backOrderID);
                    try {
                        String inventoryID = backOrderStock.getBackOrderInventoryID(backOrderID);
                        Util.debug("AdminServlet.performBackOrder() - backOrderID = " + inventoryID);
                        int quantity = backOrderStock.getBackOrderQuantity(backOrderID);
                        catalog.setItemQuantity(inventoryID, quantity);
                        // Update the BackOrder status
                        Util.debug("AdminServlet.performBackOrder() - quantity: " + quantity);
                        backOrderStock.updateStock(backOrderID, quantity);
                        // catalog.remove();
                    } catch (Exception e) {
                        Util.debug("AdminServlet.performBackOrder() - Exception: " + e);
                        e.printStackTrace();
                    }
                }
            }
            getBackOrders(session);
            requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
        } else if (action.equals(Util.ACTION_CANCEL)) {
            Util.debug("AdminServlet.performBackOrder() - AdminServlet(performTask):  Cancel Action");
            String[] backOrderIDs = (String[]) req.getParameterValues("selectedObjectIds");
            
            if (backOrderIDs != null) {
                for (int i = 0; i < backOrderIDs.length; i++) {
                    long backOrderID = Long.parseLong(backOrderIDs[i]);
                    Util.debug("AdminServlet.performBackOrder() - Selected BackOrder backOrderID: " + backOrderID);
                    try {
                        backOrderStock.deleteBackOrder(backOrderID);
                    } catch (Exception e) {
                        Util.debug("AdminServlet.performBackOrder() - Exception: " + e);
                        e.printStackTrace();
                    }
                }
            }
            getBackOrders(session);
            requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
        } else if (action.equals(Util.ACTION_UPDATEQUANTITY)) {
            Util.debug("AdminServlet.performBackOrder() -  Update Quantity Action");
            try {
                String backOrderIDs = req.getParameter("backOrderID");
                if (backOrderIDs != null) {
                    long backOrderID = Long.parseLong(backOrderIDs);
                    Util.debug("AdminServlet.performBackOrder() - backOrderID = " + backOrderID);
                    String paramquantity = req.getParameter("itemqty");
                    if (paramquantity != null) {
                        int quantity = new Integer(paramquantity).intValue();
                        Util.debug("AdminServlet.performBackOrder() - quantity: " + quantity);
                        backOrderStock.setBackOrderQuantity(backOrderID, quantity);
                    }
                }
            } catch (Exception e) {
                Util.debug("AdminServlet.performBackOrder() - Exception: " + e);
                e.printStackTrace();
            }
            getBackOrders(session);
            requestDispatch(getServletConfig().getServletContext(), req, resp, Util.PAGE_BACKADMIN);
        } else {
            // Unknown Backup Admin Action so go back to the Administration home page
            sendRedirect(resp, "/plants-web/" + Util.PAGE_ADMINHOME);
        }
    }
    /**
     * Method getBackOrders.
     * @param session
     */
    public void getBackOrders(HttpSession session) {
        try {
            // Get the list of back order items.
            Util.debug("AdminServlet.getBackOrders() - Looking for BackOrders");
            Collection backOrderItems = backOrderStock.findBackOrderItems();
            if (backOrderItems != null) {
                Util.debug("AdminServlet.getBackOrders() - BackOrders found!");
                Iterator i = backOrderItems.iterator();
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
                        Util.debug("AdminServlet.getBackOrders() - backOrderID = " + backOrderID);
                        Util.debug("AdminServlet.getBackOrders() -    supplierOrderID = " + supplierOrderID);
                        Util.debug("AdminServlet.getBackOrders() -    invID = " + invID);
                        Util.debug("AdminServlet.getBackOrders() -    name = " + name);
                        Util.debug("AdminServlet.getBackOrders() -    quantity = " + quantity);
                        Util.debug("AdminServlet.getBackOrders() -    status = " + status);
                        Util.debug("AdminServlet.getBackOrders() -    lowDate = " + lowDate);
                        Util.debug("AdminServlet.getBackOrders() -    orderDate = " + orderDate);
                    }
                }
            } else {
                Util.debug("AdminServlet.getBackOrders() - NO BackOrders found!");
            }
            session.setAttribute("backorderitems", backOrderItems);
        } catch (Exception e) {
            e.printStackTrace();
            Util.debug("AdminServlet.getBackOrders() - RemoteException: " + e);
        }
    }
    /**
     * Method purchaseInventory.
     * @param invID
     * @param amountToOrder
     * @returns orderID
     */
    private String purchaseInventory(long backOrderID, String invID, int amountToOrder) throws NoSupplierException {
        String customerID = "PBW0001";
        String trackNum = "0" ;
        try {
            // Use JAXRPC to send the order to the Supplier.   A supplier tracking order ID is returned.
            ReceiveOrdersJAXRPCProxy supplierOrdersProxy = new ReceiveOrdersJAXRPCProxy(suppliers);
            trackNum = supplierOrdersProxy.sendOrder(customerID, backOrderID, invID, amountToOrder);
            if (trackNum.equals("0")) {
                Util.debug("AdminServlet.purchaseInventory() - sendOrder has failed with rc = " + trackNum);
            }
        } catch (NoSupplierException e) {
            Util.debug("AdminServlet.purchaseInventory() -  NoSupplierException: " + e);
            throw e;
        } catch (Exception e) {
            Util.debug("AdminServlet.purchaseInventory() - Exception: " + e);
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
        boolean orderStatus = false;
        try {
            // Use JAXRPC to query the supplier for the order status.
            ReceiveOrdersJAXRPCProxy supplierOrdersProxy = new ReceiveOrdersJAXRPCProxy(suppliers);
            orderStatus = supplierOrdersProxy.checkOrderStatus(trackNum);
        } catch (NoSupplierException e) {
            Util.debug("AdminServlet.checkStatus() -  NoSupplierException: " + e);
            throw e;
        } catch (Exception e) {
            Util.debug("AdminServlet.checkStatus() - Exception: " + e);
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
    private void sendRedirect(HttpServletResponse resp, String page) throws ServletException, IOException {
        resp.sendRedirect(resp.encodeRedirectURL(page));
    }
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
    private void requestDispatch(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String page) throws ServletException, IOException {
        resp.setContentType("text/html");
        ctx.getRequestDispatcher("/"+ page).forward(req, resp);
    }
}
