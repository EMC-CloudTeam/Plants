//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2004
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.impl;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emc.plants.persistence.Order;
import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.pojo.beans.EMailMessage;
import com.emc.plants.service.exceptions.MailerAppException;
import com.emc.plants.service.interfaces.Mailer;
import com.emc.plants.utils.Util;

/**
 * MailerBean is the implementation class for the {@link Mailer} stateless session EJB.
 * MailerBean implements each of the business methods in the <code>Mailer</code>
 * EJB remote interface and each of the EJB lifecycle methods in the javax.ejb.SessionBean
 * interface.
 * 
 * @see Mailer
 */
//@Stateless(name="Mailer")
@Repository("mailer")
public class MailerBean implements Mailer
{
	//public static final String MAIL_SESSION = "java:comp/env/mail/PlantsByWebSphere";
	//@Resource(name="mail/PlantsByWebSphere")
	Session mailSession;

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory =entityManagerFactory;
	}
	
	/**
	 * Create the email message.
	 *
	 * @param orderKey The order number.
	 * @return The email message.
	 */
	private String createMessage(long orderKey) 
	{
		Util.debug("creating email message for order:"+orderKey);
		StringBuffer msg = new StringBuffer();
		/*
		 OrderHome orderHome = (OrderHome) Util.getEJBLocalHome("java:comp/env/ejb/Order",
		 OrderHome.class);
		 Order order = orderHome.findByPrimaryKey(new OrderKey(orderKey));
		 */
		EntityManager em = entityManagerFactory.createEntityManager();
		Order order = em.find(Order.class, orderKey);
		msg.append("Thank you for your order " + orderKey + ".\n");
		msg.append("Your order will be shipped to: " + order.getShipName() + "\n");
		msg.append("                               " + order.getShipAddr1() + " " 
				+ order.getShipAddr2() + "\n");
		msg.append("                               " + order.getShipCity() + ", " 
				+ order.getShipState() + " "
				+ order.getShipZip() + "\n\n");
		msg.append("Please save it for your records.\n");
		return msg.toString();
	}
	
	/**
	 * Create the Subject line.
	 *
	 * @param orderKey The order number.
	 * @return The Order number string.
	 */
	private String createSubjectLine(long orderKey) 
	{
		StringBuffer msg = new StringBuffer();
		msg.append("Your order number " + orderKey);
		
		return msg.toString();
	}
	
	/** 
	 * Create a mail message and send it.
	 *
	 * @param customerInfo  Customer information.
	 * @param orderKey
	 * @throws MailerAppException
	 */
	public void createAndSendMail(CustomerInfo customerInfo, long orderKey) throws MailerAppException 
	{
		try 
		{
			EMailMessage eMessage = new EMailMessage(createSubjectLine(orderKey),
					createMessage(orderKey),
					customerInfo.getCustomerID());
			Util.debug("Sending message" +
					"\nTo: " + eMessage.getEmailReceiver() +
					"\nSubject: " + eMessage.getSubject() +
					"\nContents: " + eMessage.getHtmlContents());
			
			//Session mailSession = (Session) Util.getInitialContext().lookup(MAIL_SESSION);
			
			MimeMessage msg = new MimeMessage(mailSession);
			msg.setFrom();
			
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(eMessage.getEmailReceiver(), false));
			
			msg.setSubject(eMessage.getSubject());
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setText(eMessage.getHtmlContents(), "us-ascii");
			msg.setHeader("X-Mailer", "JavaMailer");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp);
			msg.setContent(mp);
			msg.setSentDate(new Date());
			
			Transport.send(msg);
			
			Util.debug("\nMail sent successfully.");
		}   
		catch (Exception e) 
		{
			Util.debug("Error sending mail. Have mail resources been configured correctly?");
			Util.debug("createAndSendMail exception : " + e);
			e.printStackTrace();
			throw new MailerAppException("Failure while sending mail");
		}
	}
	
}
