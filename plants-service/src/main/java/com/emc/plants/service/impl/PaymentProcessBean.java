package com.emc.plants.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emc.plants.persistence.PaymentProcessMessage;

@Repository("paymntProcess")
@Transactional
public class PaymentProcessBean {
	
	private static Logger logger = Logger.getLogger("PaymentProcessBean");

	private EntityManager em;
	
	
	@PersistenceContext(unitName="PBW")
	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}
	
	
	public void persistPaymentInfoMsg(PaymentProcessMessage paymntProcMsg) {
		logger.info(" persistPaymentInfoMsg -- >> ");
		//em.getTransaction().begin();
		em.persist(paymntProcMsg);
		em.flush();
		logger.info(" persistPaymentInfoMsg -- >> Persist Success!!");
		//em.getTransaction().commit();
	}
}
