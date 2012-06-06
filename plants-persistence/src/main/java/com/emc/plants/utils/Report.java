//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2002
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class is the report produced using a database query.
 */
public class Report implements java.io.Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
static final public int ORDER_ORDERID            = 0;
   static final public int ORDER_CUSTOMERID         = 1;
   static final public int ORDER_FULLNAME           = 2;
   static final public int ORDER_BILLADDR1          = 3;
   static final public int ORDER_BILLADDR2          = 4;
   static final public int ORDER_BILLCITY           = 5;
   static final public int ORDER_BILLSTATE          = 6;
   static final public int ORDER_BILLZIP            = 7;
   static final public int ORDER_BILLPHONE          = 8;
   static final public int ORDER_CREDITCARD         = 9;
   static final public int ORDER_PROFIT             = 10;
   static final public int ORDER_INVENTORY_ID       = 11;
   static final public int ORDER_INVENTORY_NAME     = 12;
   static final public int ORDER_INVENTORY_PKGINFO  = 13;
   static final public int ORDER_INVENTORY_PRICE    = 14;
   static final public int ORDER_INVENTORY_COST     = 15;
   static final public int ORDER_INVENTORY_QUANTITY = 16;
   static final public int ORDER_INVENTORY_CATEGORY = 17;

   static final public int PROFITS                  = 100;

   static final public String[] ORDER_FIELDS = 
               { "orderID", "customerID", "fullName", "billAddr1", "billAddr2",
                 "billCity", "billState", "billZip", "billPhone", "creditCard", 
                 "profit", "inventoryID", "name", "pkginfo", "price", "cost", 
                 "quantity", "category" };

   private Hashtable<Integer, Vector<?>> reportRows = new Hashtable<Integer, Vector<?>>();

   /**
    * Create a Report object.
    */
   public Report() { }                                                

   /**
    * Get a count of the number of rows in the report.
    *
    * @return int - number of rows
    */
   @SuppressWarnings("unchecked")
   public int getRowCount()
   {
      Enumeration myEnum = reportRows.elements();
      Object obj = myEnum.nextElement();
      Vector rows = (Vector) obj;
      return rows.size();
   }

   /**
    * Get a count of the number of columns in the report.
    *
    * @return int - number of columns
    */
   public int getColumnCount()
   {
      return reportRows.size();
   }

   /**
    * Set the rows for a particular field.
    *                                                  
    * @param field - The field desired.
    * @param rows - Vector representing all the rows' data for this field.
    */
   @SuppressWarnings("unchecked")
   public void setReportFieldByRow(int field, Vector rows)
   {
      reportRows.put(new Integer(field), rows);
   }

   /**
    * Get the value of a particular field in a particular row.
    *                                                  
    * @param field - The field desired.
    * @param index - The row index desired.
    * @return The value of the field for the row given.
    */
   @SuppressWarnings("unchecked")
   public Object getReportFieldByRow(int field, int index)
   {
      Object obj = null;
      Vector vec = (Vector) reportRows.get(new Integer(field));
      if (vec != null)
         obj = vec.elementAt(index);

      return obj;
   }

}
