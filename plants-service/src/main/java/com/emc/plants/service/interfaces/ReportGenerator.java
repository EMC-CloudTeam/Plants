//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2002
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.interfaces;

import java.util.Date;

import com.emc.plants.utils.Report;
import com.emc.plants.utils.ReportFormat;

/**
 * Remote interface for ReportGenerator bean.
 */
//@Remote
public interface ReportGenerator
{  
   /** 
    * Run the report to get the top selling items for a range of dates.
    */
   public Report getTopSellersForDates(Date startdate, Date enddate, int quantity,
                                       ReportFormat reportFormat);

   /** 
    * Run the report to get the top zip codes for a range of dates.
    */
   public Report getTopSellingZipsForDates(Date startdate, Date enddate, int quantity,
                                           ReportFormat reportFormat);
}

