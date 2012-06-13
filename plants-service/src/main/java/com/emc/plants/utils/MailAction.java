//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2003
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.utils;

import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.service.exceptions.MailerAppException;
import com.emc.plants.service.interfaces.Mailer;
import com.emc.plants.utils.Util;



/**
 * This class sends the email confirmation message. 
 */

public class MailAction implements java.io.Serializable 
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	@EJB(name="Mailer")
	private Mailer mailer;

/** Public constructor */
    public MailAction( ) { }

   /**
    * Send the email order confirmation message. 
    *
    * @param customerInfo The customer information.
    * @param orderKey The order number.
    */
    public final void sendConfirmationMessage(CustomerInfo customerInfo, long orderKey)
                             
    {
       try 
       {
    	   System.out.println("mailer="+mailer);
          mailer.createAndSendMail(customerInfo, orderKey);
       }
       // The MailerAppException will be ignored since mail may not be configured.
       catch (MailerAppException e) {
    	   Util.debug("Mailer threw exception, mail may not be configured. Exception:"+e);    	   
       }
    }

}
