//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2002
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.pojo.beans;

/**
 * This class encapsulates the info needed to send an email
 * message. This object is passed to the Mailer EJB 
 * sendMail() method.
 */
public class EMailMessage implements java.io.Serializable 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subject;
    private String htmlContents;
    private String emailReceiver;

    public EMailMessage( String subject, String htmlContents,
                         String emailReceiver) {
        this.subject       = subject;
        this.htmlContents  = htmlContents;
        this.emailReceiver = emailReceiver;
    }

    //subject field of email message
    public String getSubject() {
        return subject;
    }

    //Email address of recipient of email message
    public String getEmailReceiver() {
        return emailReceiver;
    }

    //contents of email message
    public String getHtmlContents() {
        return htmlContents;
    }

    public String toString() {
        return  " subject=" + subject + " " +  emailReceiver + " " + htmlContents;
    }
}

