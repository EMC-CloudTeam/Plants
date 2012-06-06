//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2002
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.exceptions;

/**
 * MailerAppException extends the standard Exception.
 * This is thrown by the mailer component when there is some
 * failure sending the mail.
 */
public class MailerAppException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailerAppException() {}

    public MailerAppException(String str) {
        super(str);
    }
}
