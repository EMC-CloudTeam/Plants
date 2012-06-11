//
//"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
//instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
//or for redistribution by customer, as part of such an application, in customer's own products. " 
//
//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001,2002
//All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.emc.plants.service.interfaces.ReportGenerator;
import com.emc.plants.utils.Report;
import com.emc.plants.utils.ReportFormat;
import com.emc.plants.utils.Util;

/**
 * ReportGeneratorBean is the implementation class for the {@link ReportGenerator} stateless session
 * EJB.  ReportGeneratorBean implements each of the business methods in the <code>ReportGenerator</code>
 * EJB remote interface and each of the EJB lifecycle methods in the javax.ejb.SessionBean
 * interface.
 * 
 * @see Report
 * @see ReportFormat
 * @see ReportGenerator
 */
//@Stateless (name="ReportGenerator")
@Repository
public class ReportGeneratorBean implements ReportGenerator
{
	//ReportGenerator has not been updated to use PersistenceContext due to complex sql
//	@PersistenceContext(unitName="PBW")
//	EntityManager em;
	
	/** 
	 * Run the report to get the top selling items for a range of dates.
	 *
	 * @param startdate Start of date range.
	 * @param enddate End of date range.
	 * @param quantity Number of items to return in report.
	 * @param reportFormat - Report format information.
	 * @return Report containing results.
	 */
	@SuppressWarnings("unchecked")
	public Report getTopSellersForDates(java.util.Date startdate, java.util.Date enddate, int quantity,
			ReportFormat reportFormat)
	{
		Report report = null;
		Connection conn = null;
		ResultSet results = null;
		PreparedStatement sqlStatement = null;  
		try
		{
			// Establish connection to datasource.
			String orderItemsTableName="ORDERITEM";
			DataSource ds = (DataSource) Util.getInitialContext().lookup("jdbc/PlantsByWebSphereDataSource");
			conn = ds.getConnection();
			
			// Set sort order of ascending or descending.
			String sortOrder;
			if (reportFormat.isAscending())
				sortOrder = "ASC";
			else
				sortOrder = "DESC";
			
			// Set up where by clause.
			String startDateString = Long.toString(startdate.getTime());
			if (startDateString.length() < 14)
			{
				StringBuffer sb = new StringBuffer(Util.ZERO_14);
				sb.replace((14 - startDateString.length()), 14, startDateString);
				startDateString = sb.toString();
			}
			String endDateString = Long.toString(enddate.getTime());
			if (endDateString.length() < 14)
			{
				StringBuffer sb = new StringBuffer(Util.ZERO_14);
				sb.replace((14 - endDateString.length()), 14, endDateString);
				endDateString = sb.toString();
			}
			String whereString = " WHERE sellDate BETWEEN '" + startDateString +
			"' AND '" + endDateString + "' ";
			
			// Create SQL statement.
			String sqlString = "SELECT inventoryID, name, category," +
			" SUM(quantity * (price - cost)) as PROFIT FROM " + orderItemsTableName +
			whereString +
			" GROUP BY inventoryID, name, category ORDER BY PROFIT " + sortOrder + ", name";
			
			Util.debug("sqlstring=" + sqlString );
		
			sqlStatement = conn.prepareStatement(sqlString);
			results = sqlStatement.executeQuery();
			int i;
			
			// Initialize vectors to store data in.
			Vector[] vecs = new Vector[4];
			for (i = 0; i < vecs.length; i++)
			{
				vecs[i] = new Vector();
			}
			
			// Sift thru results.
			int count = 0;
			while ((results.next()) && (count < quantity))
			{
				count++;
				i = 1;
				vecs[0].addElement(results.getString(i++));
				vecs[1].addElement(results.getString(i++));
				vecs[2].addElement(new Integer(results.getInt(i++)));
				vecs[3].addElement(new Float(results.getFloat(i++)));
			}     
			
			// Create report.
			report = new Report();
			report.setReportFieldByRow(Report.ORDER_INVENTORY_ID, vecs[0]);
			report.setReportFieldByRow(Report.ORDER_INVENTORY_NAME, vecs[1]);
			report.setReportFieldByRow(Report.ORDER_INVENTORY_CATEGORY, vecs[2]);
			report.setReportFieldByRow(Report.PROFITS, vecs[3]);
		}

		catch (Exception e)
		{
			Util.debug("exception in ReportGeneratorBean:getTopSellersForDates.  "+e);
			e.printStackTrace();
		}
		finally
		{            // Clean up.
			try { 
				if (results != null) results.close();
			}
			catch (Exception ignore) {
			}
			
			try { 
				if (sqlStatement != null) sqlStatement.close();
			}
			catch (Exception ignore) {
			}
			
			// Close Connection.
			try { 
				if (conn != null) conn.close();
			}
			catch (Exception ignore) {
			}
		}
		
		return report;
	}
	
	/** 
	 * Run the report to get the top zip codes for a range of dates.
	 *
	 * @param startdate Start of date range.
	 * @param enddate End of date range.
	 * @param quantity Number of items to return in report.
	 * @param reportFormat - Report format information.
	 * @return Report containing results.
	 */
	@SuppressWarnings("unchecked")
	public Report getTopSellingZipsForDates(java.util.Date startdate, java.util.Date enddate, int quantity,
			ReportFormat reportFormat)
	{
		Report report = null;
		Connection conn = null;
		ResultSet results = null;
		PreparedStatement sqlStatement = null;  
		try
		{
			// Establish connection to datasource.
			String orderInfoTableName = "ORDER1";
			
			DataSource ds = (DataSource) Util.getInitialContext().lookup("jdbc/PlantsByWebSphereDataSource");
			conn = ds.getConnection();
			
			// Set sort order of ascending or descending.
			String sortOrder;
			if (reportFormat.isAscending())
				sortOrder = "ASC";
			else
				sortOrder = "DESC";
			
			// Set up where by clause.
			String startDateString = Long.toString(startdate.getTime());
			if (startDateString.length() < 14)
			{
				StringBuffer sb = new StringBuffer(Util.ZERO_14);
				sb.replace((14 - startDateString.length()), 14, startDateString);
				startDateString = sb.toString();
			}
			String endDateString = Long.toString(enddate.getTime());
			if (endDateString.length() < 14)
			{
				StringBuffer sb = new StringBuffer(Util.ZERO_14);
				sb.replace((14 - endDateString.length()), 14, endDateString);
				endDateString = sb.toString();
			}
			String whereString = " WHERE sellDate BETWEEN '" + startDateString +
			"' AND '" + endDateString + "' ";
			
			// Create SQL statement.
			String sqlString = "SELECT billZip, SUM(profit) AS PROFITS FROM " + orderInfoTableName +
			whereString +
			" GROUP BY billZip ORDER BY PROFITS " + sortOrder + ", billZip";
			
			Util.debug("sqlstring=" + sqlString + "=");
			sqlStatement = conn.prepareStatement(sqlString);
			results = sqlStatement.executeQuery();
			int i;
			
			// Initialize vectors to store data in.
			Vector[] vecs = new Vector[2];
			for (i = 0; i < vecs.length; i++)
			{
				vecs[i] = new Vector();
			}
			
			// Sift thru results.
			int count = 0;
			while ((results.next()) && (count < quantity))
			{
				count++;
				i = 1;
				vecs[0].addElement(results.getString(i++));
				vecs[1].addElement(new Float(results.getFloat(i++)));
			}     
			
			// Create report.
			report = new Report();
			report.setReportFieldByRow(Report.ORDER_BILLZIP, vecs[0]);
			report.setReportFieldByRow(Report.PROFITS, vecs[1]);
		}
		catch (NamingException e)
		{ 
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{            // Clean up.
			try { 
				if (results != null) results.close();
			}
			catch (Exception ignore) {
			}
			
			try { 
				if (sqlStatement != null) sqlStatement.close();
			}
			catch (Exception ignore) {
			}
			
			// Close Connection.
			try { 
				if (conn != null) conn.close();
			}
			catch (Exception ignore) {
			}
		}
		
		return report;
	}
}

