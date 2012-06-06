package com.emc.plants.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	EntityManagerFactory emf = 
    			Persistence.createEntityManagerFactory("PBW");

    		EntityManager entityManager = emf.createEntityManager();
    		
    		try	{
    			Customer cust =  entityManager.find(Customer.class, "123456");
    			System.out.println(cust.getFirstName());
    		}finally	{
    			if(entityManager != null)	{
    				entityManager.close();
    			}
    				
    		}
    }
}
