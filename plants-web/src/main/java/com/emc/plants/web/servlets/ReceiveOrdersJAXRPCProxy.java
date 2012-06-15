//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2003,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.web.servlets;
import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.InitialContext;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.ServiceException;

import com.emc.plants.pojo.beans.SupplierInfo;
import com.emc.plants.service.exceptions.NoSupplierException;
import com.emc.plants.service.interfaces.Suppliers;
import com.emc.plants.utils.Util;


public class ReceiveOrdersJAXRPCProxy {
	
    private Suppliers suppliers;
    private URL portAddress = null;
    /**
     * @see java.lang.Object#Object()
     */
    public ReceiveOrdersJAXRPCProxy(Suppliers suppliers) {
    	this.suppliers=suppliers;
    }
    /**
     * @param defaultPortAddress
     * @return
     * @throws NoSupplierException
     * @throws MalformedURLException
     */
    private URL getPortAddress(String defaultPortAddress) throws NoSupplierException, MalformedURLException {
        try {
            if (portAddress == null) {
                String stringPortAddress = "";
                SupplierInfo supplierInfo = suppliers.getSupplierInfo();
                if (supplierInfo != null)
                    stringPortAddress = suppliers.getSupplierURL(supplierInfo.getID());
                Util.debug("ReceiveOrdersProxy.getPortAddress(): PortAddress URL read: " + stringPortAddress);
                if ((stringPortAddress == null) || (stringPortAddress.equals(""))) {
                    Util.debug("ReceiveOrdersProxy.getPortAddress(): Invalid URL in Supplier Configuration");
                    Util.debug("ReceiveOrdersProxy.getPortAddress(): Setting to default Port Address: " + defaultPortAddress);
                    stringPortAddress = defaultPortAddress;
                }
                portAddress = new URL(stringPortAddress);
            }
        } catch (JAXRPCException e) {
            Util.debug("ReceiveOrdersJAXRPCProxy.getPortAddress() -  JAXRPCException: " + e);
            throw new NoSupplierException("No Supplier was found.");
        } catch (Exception e) {
            Util.debug("ReceiveOrdersJAXRPCProxy.getPortAddress() -  Exception: " + e);
            e.printStackTrace();
        }
        return portAddress;
    }
    /**
     * @param customerID
     * @param backOrderID
     * @param inventoryID
     * @param quantity
     * @return supplierOrderID
     * @throws Exception
     */
    public String sendOrder(java.lang.String customerID, long backOrderID, java.lang.String inventoryID, int quantity) throws Exception {
        String trackNum = "0";
        try {
            InitialContext ctx = new InitialContext();
//            FrontGate_SEIService cs = (FrontGate_SEIService) ctx.lookup("java:comp/env/service/FrontGate_SEIService");
            // The default Service address is based on the WSDL file.
            // It is overridden by the Service address in the properties file.
//            String WSDLPortAddress = cs.getFrontGateAddress();
//            FrontGate_SEI supplierOrders = (FrontGate_SEI) cs.getFrontGate(getPortAddress(WSDLPortAddress));
            Util.debug("ReceiveOrdersJAXRPCProxy.sendOrder() -  sending Order to Supplier");
//            trackNum = supplierOrders.sendOrder(customerID, backOrderID, inventoryID, quantity);
            Util.debug("ReceiveOrdersJAXRPCProxy.sendOrder() -  sending order to Supplier has completed.");
        }  catch (JAXRPCException e) {
            trackNum = "0";
            Util.debug("ReceiveOrdersJAXRPCProxy.sendOrder() -  JAXRPCException: " + e);
            throw new NoSupplierException("Supplier Not Found");
        } catch (Exception e) {
            trackNum = "0";
            Util.debug("ReceiveOrdersJAXRPCProxy.sendOrder() -  Exception: " + e);
            throw new NoSupplierException("Error sending order to the Supplier");
        }
        return (trackNum);
    }
    /**
     * @param supplierOrderID
     * @return orderStatus
     * @throws Exception
     */
    public boolean checkOrderStatus(java.lang.String supplierOrderID) throws Exception {
        boolean orderStatus = false;
        try {
            InitialContext ctx = new InitialContext();
//            FrontGate_SEIService cs = (FrontGate_SEIService) ctx.lookup("java:comp/env/service/FrontGate_SEIService");
            // The default Service address is based on the WSDL file.
            // It is overridden by the Service address in the properties file.
//            String WSDLPortAddress = cs.getFrontGateAddress();
//            FrontGate_SEI supplierOrders = (FrontGate_SEI) cs.getFrontGate(getPortAddress(WSDLPortAddress));
            Util.debug("ReceiveOrdersJAXRPCProxy.checkOrderStatus() -  checking status of order " +supplierOrderID +" with Supplier");
//            orderStatus = supplierOrders.checkOrderStatus(supplierOrderID);
            Util.debug("ReceiveOrdersJAXRPCProxy.checkOrderStatus() -  checking status of order with Supplier has completed.  Supplier returned: " +orderStatus);
        }  catch (JAXRPCException e) {
            orderStatus = false;
            Util.debug("ReceiveOrdersJAXRPCProxy.checkOrderStatus() -  JAXRPCException: " + e);
            throw new NoSupplierException("Supplier Not Found");
        } catch (Exception e) {
            orderStatus = false;
            Util.debug("ReceiveOrdersJAXRPCProxy.checkOrderStatus() -  Exception: " + e);
            throw new NoSupplierException("Error sending order to the Supplier");
        }
        return (orderStatus);
    }
}
