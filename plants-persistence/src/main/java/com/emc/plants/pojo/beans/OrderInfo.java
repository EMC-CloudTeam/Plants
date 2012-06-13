//
// "This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
// instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
// or for redistribution by customer, as part of such an application, in customer's own products. " 
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2003
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.pojo.beans;

import com.emc.plants.persistence.Order;


/**
 * A class to hold an order's data.
 */
public class OrderInfo implements java.io.Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private long orderID;
   private String billName;
   private String billAddr1;
   private String billAddr2;
   private String billCity;
   private String billState;
   private String billZip;
   private String billPhone;
   private String shipName;
   private String shipAddr1;
   private String shipAddr2;
   private String shipCity;
   private String shipState;
   private String shipZip;
   private String shipPhone;
   private int shippingMethod;

   /** 
    * Constructor to create an OrderInfo by passing each field.
    */
   public OrderInfo(String billName, String billaddr1, String billAddr2, 
                    String billCity, String billState, String billZip, 
                    String billPhone, String shipName,
                    String shipaddr1, String shipAddr2, String shipCity,
                    String shipState, String shipZip, String shipPhone,
                    int shippingMethod, long orderID)
   {
      this.orderID = orderID;
      this.billName = billName;
      //this.billAddr1 = billAddr1;
      this.billAddr2 = billAddr2;
      this.billCity = billCity;
      this.billState = billState;
      this.billZip = billZip;
      this.billPhone = billPhone;
      this.shipName = shipName;
      //this.shipAddr1 = shipAddr1;
      this.shipAddr2 = shipAddr2;
      this.shipCity = shipCity;
      this.shipState = shipState;
      this.shipZip = shipZip;
      this.shipPhone = shipPhone;
      this.shippingMethod = shippingMethod;
   }

   /**
    * Constructor to create an OrderInfo using an Order.
    * @param order
    */
   public OrderInfo(Order order)
   {
      orderID = order.getOrderID();
      billName = order.getBillName();
      billAddr1 = order.getBillAddr1();
      billAddr2 = order.getBillAddr2();
      billCity = order.getBillCity();
      billState = order.getBillState();
      billZip = order.getBillZip();
      billPhone = order.getBillPhone();
      shipName = order.getShipName();
      shipAddr1 = order.getShipAddr1();
      shipAddr2 = order.getShipAddr2();
      shipCity = order.getShipCity();
      shipState = order.getShipState();
      shipZip = order.getShipZip();
      shipPhone = order.getShipPhone();
      shippingMethod = order.getShippingMethod();
   }

   /** Get the order ID.
    * @return Order ID */
   public long getID() { return orderID; }
   /** Get the order billing address name.
    * @return Billing address name. */
   public String getBillName() { return billName; }
   /** Get the order billing address line 1.
    * @return Billing address line 1. */
   public String getBillAddr1() { return billAddr1; }
   /** Get the order billing address line 2.
    * @return Billing address line 2. */
   public String getBillAddr2() { return billAddr2; }
   /** Get the order billing address city.
    * @return Billing address city. */
   public String getBillCity() { return billCity; }
   /** Get the order billing address state.
    * @return Billing address state. */
   public String getBillState() { return billState; }
   /** Get the order billing address zip code.
    * @return Billing address zip code. */
   public String getBillZip() { return billZip; }
   /** Get the order billing address phone.
    * @return Billing address phone. */
   public String getBillPhone() { return billPhone; }
   /** Get the order shipping address name.
    * @return Shipping address name. */
   public String getShipName() { return shipName; }
   /** Get the order shipping address line 1.
    * @return Shipping address line 1. */
   public String getShipAddr1() { return shipAddr1; }
   /** Get the order shipping address line 2.
    * @return Shipping address line 2. */
   public String getShipAddr2() { return shipAddr2; }
   /** Get the order shipping address city.
    * @return Shipping address city. */
   public String getShipCity() { return shipCity; }
   /** Get the order shipping address state.
    * @return Shipping address state. */
   public String getShipState() { return shipState; }
   /** Get the order shipping address zip code.
    * @return Shipping address zip code. */
   public String getShipZip() { return shipZip; }
   /** Get the order shipping address phone.
    * @return Shipping address phone. */
   public String getShipPhone() { return shipPhone; }
   /** Get the order shipping method.
    * @return int representing the shipping method. */
   public int getShippingMethod() { return shippingMethod; }
}
