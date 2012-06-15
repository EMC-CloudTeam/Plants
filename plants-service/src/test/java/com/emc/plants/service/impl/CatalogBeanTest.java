package com.emc.plants.service.impl;

import static org.junit.Assert.*;

import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.emc.plants.persistence.Inventory;
import com.emc.plants.pojo.beans.StoreItem;
import com.emc.plants.service.interfaces.Catalog;
import com.emc.plants.service.interfaces.Login;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:app-context-service.xml" })
@Transactional
public class CatalogBeanTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired 
    private ApplicationContext m_oApplicationContext;
		
		@Autowired
		private Catalog catalog;
    
    @PersistenceContext
    private EntityManager entityManager;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetItem() {
		String inventoryID = "F0003";
		StoreItem item = catalog.getItem(inventoryID);
		assertEquals("Result", 0, item.getCategory());
	}

	@Test
	public void testGetItemsByCategory() {
		int cateogory = 3;
		Vector vector = catalog.getItemsByCategory(cateogory);
		assertEquals("Result", 2, vector.size());
	}

	@Test
	public void testGetItemsLikeName() {
		String name = "birdfeeder";
		Vector vector = catalog.getItemsLikeName(name);
		assertEquals("Result", 1, vector.size());
	}

	@Test
	public void testGetItems() {
		Vector vector = catalog.getItems();
		assertEquals("Result", 21, vector.size());
	}

	@Test
	public void testGetItemInventory() {
		String inventoryID = "F0003";
		Inventory inventory = catalog.getItemInventory(inventoryID);
		assertEquals("Result", "Black-eyed Susan", inventory.getName());
	}
//
//	@Test
//	public void testAddItemInventory() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddItemStoreItem() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeleteItem() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemHeading() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemDescription() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemPkginfo() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemCategory() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemImageFileName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetItemImageBytes() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemImageBytes() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemPrice() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemCost() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemQuantity() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemNotes() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetItemPrivacy() {
//		fail("Not yet implemented");
//	}
//
//	public Catalog getCatalog() {
//		return catalog;
//	}
//
//	public void setCatalog(Catalog catalog) {
//		this.catalog = catalog;
//	}
	
	

}
