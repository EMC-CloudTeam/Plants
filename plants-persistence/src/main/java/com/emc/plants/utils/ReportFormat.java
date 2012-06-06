//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.utils;

/**
 * Report format for database query.  Currently, this format only determines whether
 * the data is sorted in an ascending or descending order.  This class can be expanded
 * to allow for selecting what columns appear, order of columns, etc.
 */
public class ReportFormat implements java.io.Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private boolean ascending;

   /**
    * Create a ReportFormat object which defaults to an ascending sort order.
    */
   public ReportFormat()
   {
      ascending = true;
   }                                                

   /**
    * Create a ReportFormat object.
    *
    * @param ascending A boolean value set to TRUE when sortingby ascending
    * values, and FALSE when sorting by descending values.
    */
   public ReportFormat(boolean ascending)
   {
      this.ascending = ascending;
   }                                                

   /**
    * Used to establish the sorting rules.
    *
    * @param ascending A boolean value set to TRUE when sortingby ascending
    * values, and FALSE when sorting by descending values.
    */
   public void sortBy(boolean ascending)
   {
      this.ascending = ascending;
   }

   /**
    * Is report sorted in ascending order?
    *
    * @return True, if ascending.
    */
   public boolean isAscending()
   {
      return ascending;
   }
}
