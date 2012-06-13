//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.interfaces;

import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.service.exceptions.MailerAppException;


/**
 * Local interface for the Mailer session bean.
 */
//@Local
public interface Mailer 
{
    /**
     * This method sends an email message.
     *
     * @param customerInfo  Customer information.
     * @param orderKey
     * @throws MailerAppException
     */
    public void createAndSendMail(CustomerInfo customerInfo, long orderKey) throws MailerAppException;

}
