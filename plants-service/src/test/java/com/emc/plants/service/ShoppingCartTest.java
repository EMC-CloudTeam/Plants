package com.emc.plants.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.emc.plants.pojo.beans.OrderInfo;
import com.emc.plants.pojo.beans.ShoppingCartItem;
import com.emc.plants.service.impl.ShoppingCartBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:app-context-service.xml" })
@Transactional
public class ShoppingCartTest {

	@Autowired 
	private ApplicationContext m_oApplicationContext;
	
	@PersistenceContext
	private EntityManager entityManager;

	private static Logger logger = Logger.getLogger(ShoppingCartTest.class
			.getName());

	

	@Autowired
	private ShoppingCartBean shopping;
	
	// private Connection connection;
	//@Override
	protected void setUp() throws Exception {
		//super.setUp();

		//context = new ClassPathXmlApplicationContext("app-context-service.xml");
		/*
		 * try { logger.info("Starting in-memory HSQL database for unit tests");
		 * Class.forName("org.hsqldb.jdbcDriver"); connection =
		 * DriverManager.getConnection("jdbc:hsqldb:mem:unit-testing-jpa", "sa",
		 * ""); } catch (Exception ex) { ex.printStackTrace();
		 * fail("Exception during HSQL database startup."); } try {
		 * logger.info("Building JPA EntityManager for unit tests"); emFactory =
		 * Persistence.createEntityManagerFactory("testPU"); em =
		 * emFactory.createEntityManager(); } catch (Exception ex) {
		 * ex.printStackTrace();
		 * fail("Exception during JPA EntityManager instanciation."); }
		 */

	}

	@Test
	public void testCheckInventory() {

		ShoppingCartItem scItem = new ShoppingCartItem("F0003",
				"Black-eyed Susan", "2 plants", 8.0f, 9.0f, 9, 10);
		
		//System.out.println("Shopping :: "+applicationContext);
		shopping.checkInventory(scItem);
		assertTrue(true);
		
	}
	
	
	@Test
	public void testCheckInventoryMulti() {
		ShoppingCartItem scItem = new ShoppingCartItem("F0003",
				"Black-eyed Susan", "2 plants", 8.0f, 9.0f, 9, 10);
		Collection<ShoppingCartItem> scItemsColln = new ArrayList<ShoppingCartItem>();
		//System.out.println("Shopping :: "+applicationContext);
		scItemsColln.add(scItem);
		shopping.checkInventory(scItemsColln);
		assertTrue(true);
	}
	
	@Test
	@Transactional
	public void testCreateOrder() {
		ShoppingCartItem scItem = new ShoppingCartItem("F0003",
				"Black-eyed Susan", "2 plants", 8.0f, 9.0f, 9, 10);
		Collection<ShoppingCartItem> scItemsColln = new ArrayList<ShoppingCartItem>();
		//System.out.println("Shopping :: "+applicationContext);
		scItemsColln.add(scItem);
		
		OrderInfo orderInfo = shopping.createOrder("p@plants.com", "TESTBILL", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", "ADDDRRR", 1, scItemsColln);
		
		System.out.println(" Order ID :: "+orderInfo.getID());
		assertNotNull(orderInfo);
	}
	
	
}
