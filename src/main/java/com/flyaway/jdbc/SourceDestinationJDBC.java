package com.flyaway.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.flyaway.model.SourceDestination;
import com.flyaway.model.User;

public class SourceDestinationJDBC {
	
	private DataSource dataSource;
	
	/**
	 * @param dataSource
	 */
	public SourceDestinationJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 
	 */
	public SourceDestinationJDBC() {
		
	}
	
	@Override
	public String toString() {
		return "SourceDestinationJDBC [dataSource=" + dataSource + "]";
	}
	
	public List<SourceDestination> getSourceDestinations() throws Exception{
		
		System.out.println("Inside SourceDestinationJDBC.getSourceDestination()...");
		
		List<SourceDestination> sourceDestinations = new ArrayList<>();	//empty list	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;	
		
		//try-finally block		
		try {
			
			//Step 1. Establishing a connection.
			System.out.println("1. Establishing a connection to the database..");
			conn = dataSource.getConnection();
			System.out.println("conn = " + conn.toString());
			
			//Step 2. Create a statement.
			System.out.println("2. Create SQL select statement..");
			String sql = "SELECT * FROM flightdetails"
						+ " ORDER BY flightdetailsid"; //column name in the database is "fullname"
			stmt = conn.createStatement();
			
			//Step 3. Execute the query.
			System.out.println("3. Execute the select query...");
			rs = stmt.executeQuery(sql);
			
			//Step 4. Process the ResultSet object.
			System.out.println("4. Process the ResultSet Object...");
			while(rs.next()) {
				
				//retrieve data from result set row
				int flightdetailsid = rs.getInt("flightdetailsid"); //column name in the database is "userid"
				String flightnumber = rs.getString("flightnumber");//column name is "roleid"
				int airlineid = rs.getInt("airlineid");
				String src_airportCode = rs.getString("src_airportCode"); //column name in the database is "firstname"
				String dest_airportCode = rs.getString("dest_airportCode");//column name is "username"
				int totalNumSeats = rs.getInt("totalNumSeats"); //column name in the database is "email"
				int availFirstClassSeats = rs.getInt("availFirstClassSeats");//column name in the database is "password"
				int availBusinessClassSeats = rs.getInt("availBusinessClassSeats"); //column name in the database is "phoneNumber"
				int availEconomySeats = rs.getInt("availEconomySeats");
				
				SourceDestination tempSourceDestination = new SourceDestination(flightdetailsid, flightnumber, airlineid, src_airportCode, dest_airportCode, totalNumSeats, availFirstClassSeats, availBusinessClassSeats, availEconomySeats);
								
				sourceDestinations.add(tempSourceDestination);			
			}//end while()
			
			return sourceDestinations;
		}
		finally {
			System.out.println("5. Close the connection & JDBC objects...");
			DBUtil.close(conn, stmt, rs);
		}		
	}
	
	public void addSourceDestination(SourceDestination sourceDestination) throws Exception
	{		
		System.out.println("Inside addSourceDestination(SourceDestination sourceDestination)...");
		System.out.println("tempUser object..\n" + sourceDestination.toString());
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {				
				
				//Step 1. Establishing a connection
				System.out.println("1. Establishing a connection to the database..");
				conn = dataSource.getConnection();
				System.out.println("myConn = " + conn.toString());
				
				//Step 2. Create a SQL parameterized statement with placeholders
				System.out.println("2. Create SQL insert statement..");
				String sql = "INSERT INTO flightdetails" 
							+ " (flightnumber, airlineid, src_airportCode, dest_airportCode, totalNumSeats, availFirstClassSeats, availBusinessClassSeats, availEconomySeats)"
						    + " VALUES (?,?,?,?,?,?,?,?)";
			
				ps = conn.prepareStatement(sql);

				//Step 3. Set position and parameter values for the prepared statement
				System.out.println("3. Set position and parameter values for the prepared statement..");
				ps.setString(1, sourceDestination.getFlightnumber()); //remember setString is one-based!!
				ps.setInt(2, sourceDestination.getAirlineid());
				ps.setString(3, sourceDestination.getSrc_airportCode());
				ps.setString(4, sourceDestination.getDest_airportCode());
				ps.setInt(5, sourceDestination.getTotalNumSeats());
				ps.setInt(6, sourceDestination.getAvailFirstClassSeats());
				ps.setInt(7, sourceDestination.getAvailBusinessClassSeats());
				ps.setInt(8, sourceDestination.getAvailEconomySeats());
				
				//Step 4. Execute the query
				System.out.println("4. Executing the insert query...");
				ps.executeUpdate(); //Executes the SQL statement in this PreparedStatement object
		}finally {
			//Step 5. Close the connection & JDBC objects
			System.out.println("5. Close the connection & JDBC objects...");
			DBUtil.close(conn, ps, null);
		}
		
	}
	
	public void updateSourceDestination(SourceDestination sourceDestination) throws Exception{
		System.out.println("Inside updateSourceDestination(SourceDestination sourceDestination)...");
		System.out.println("sourceDestination object...\n" + sourceDestination.toString());		
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		//try-finally block
		try {
			
			//Step 1. Establishing a connection.
			System.out.println("1. Establishing a connection to the database..");
			myConn = dataSource.getConnection();
			System.out.println("myConn = " + myConn.toString());
			
			//Step 2. Create a SQL parameterized statement with placeholders.
			System.out.println("2. Create SQL update statement..");
			String sql = "UPDATE flightdetails"
						+ " SET flightnumber=?, airlineid=?, src_airportCode=?, dest_airportCode=?, totalNumSeats=?, availFirstClassSeats=?, availBusinessClassSeats=?, availEconomySeats=?"				
						+ " WHERE flightdetailsid=?"; //use database column names in the statement
			System.out.println("sql = " + sql);
			myStmt = myConn.prepareStatement(sql);
			//System.out.println("myStmt = \n" + myStmt.toString());
			
			//Step 3. Set position and param values for prepared statement
			System.out.println("3. Set position and parameter values for the prepared statement..");
			myStmt.setString(1, sourceDestination.getFlightnumber());//remember that setString is one-based!!
			System.out.println("sourceDestination.getFlightnumber(): " + sourceDestination.getFlightnumber());
			myStmt.setInt(2, sourceDestination.getAirlineid());
			System.out.println("sourceDestination.getAirlineid(): " + sourceDestination.getAirlineid());
			myStmt.setString(3, sourceDestination.getSrc_airportCode());
			System.out.println("sourceDestination.getSrc_airportCode(): " + sourceDestination.getSrc_airportCode());
			myStmt.setString(4, sourceDestination.getDest_airportCode());
			System.out.println("sourceDestination.getDest_airportCode(): " + sourceDestination.getDest_airportCode());
			myStmt.setInt(5, sourceDestination.getTotalNumSeats());	
			System.out.println("sourceDestination.getTotalNumSeats(): " + sourceDestination.getTotalNumSeats());
			myStmt.setInt(6, sourceDestination.getAvailFirstClassSeats());	
			System.out.println("sourceDestination.getAvailFirstClassSeats(): " + sourceDestination.getAvailFirstClassSeats());
			myStmt.setInt(7, sourceDestination.getAvailBusinessClassSeats());	
			System.out.println("sourceDestination.getAvailBusinessClassSeats(): " + sourceDestination.getAvailBusinessClassSeats());
			myStmt.setInt(8, sourceDestination.getAvailEconomySeats());	
			System.out.println("sourceDestination.getAvailEconomySeats(): " + sourceDestination.getAvailEconomySeats());
			
			myStmt.setInt(9, sourceDestination.getFlightdetailsid());
			System.out.println("sourceDestination.getFlightdetailsid(): " + sourceDestination.getFlightdetailsid());
			System.out.println("myStmt = \n" + myStmt.toString());
			
			//Step 4. Execute the query.
			System.out.println("4. Executing the update query...");
			myStmt.execute(); //Executes the SQL statement in this PreparedStatement object, which may be any kind of SQL statement. There is no result set returned.	
		}
		finally {
			//Step 5. Close the connection & JDBC objects. (clean-up code to ensure that you don't run out of resources, esp for prod)
			DBUtil.close(myConn, myStmt, null); //since we don't have a resultset object, we can pass in null for the 3rd argument
		}
		
	}
	
	public SourceDestination getSourceDestination(int flightdetailsid) throws Exception {
		
		System.out.println("Inside SourceDestination getSourceDestination(int flightdetailsid)...");
		System.out.println("flightdetailsid = " + flightdetailsid);
		
		SourceDestination tempSourceDestination = null;		
		
		//Define JDBC object
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		//try-finally block
		try {			
			
			//get connection to the database			
			myConn = dataSource.getConnection();
			
			//create sql to get selected user
			String sql = "SELECT * FROM flightdetails"
						+ " WHERE flightdetailsid=?";
			
			//create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			//set params
			myStmt.setInt(1, flightdetailsid);
			
			//execute statement
			myRs = myStmt.executeQuery();//Executes the SQL query in this PreparedStatement object and returns the ResultSet object generated by the query.
			
			//retrieve data from result set row
			if(myRs.next()) {
				String flightnumber = myRs.getString("flightnumber");
				int airlineid = myRs.getInt("airlineid");//column name is "roleid"
				String src_airportCode = myRs.getString("src_airportCode"); //column name in the database is "firstname"
				String dest_airportCode = myRs.getString("dest_airportCode");//column name is "username"
				int totalNumSeats = myRs.getInt("totalNumSeats"); //column name in the database is "email"
				int availFirstClassSeats = myRs.getInt("availFirstClassSeats"); //column name in the database is "password"
				int availBusinessClassSeats = myRs.getInt("availBusinessClassSeats"); //column name in the database is "phoneNumber"
				int availEconomySeats = myRs.getInt("availEconomySeats");	
				//use the userId during construction
				tempSourceDestination = new SourceDestination(flightdetailsid, flightnumber, airlineid, src_airportCode, dest_airportCode, totalNumSeats, availFirstClassSeats, availBusinessClassSeats, availEconomySeats);
				//System.out.println("tempUser = \n" + tempUser.toString());
			}
			
			else {
				throw new 
				Exception("Could  not find flightdetailsid: " + flightdetailsid);
			}
			System.out.println("tempSourceDestination = \n" + tempSourceDestination.toString());
			return tempSourceDestination;//end method
		}
		finally {
			//clean up JDBC objects
			DBUtil.close(myConn, myStmt, myRs);			
		}
		
	}
	
	public void deleteSourceDestination(int flightdetailsid) throws Exception{
		System.out.println("Inside deleteSourceDestination(int flightdetailsid)...");
		System.out.println("flightdetailsid = " + flightdetailsid);
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		//try-finally block
		try {
			
			//Step 1. Establishing a connection.
			myConn = dataSource.getConnection();
			
			//Step 2. Create a SQL parameterized statement with placeholders.
			String sql = "delete from flightdetails where flightdetailsid=? ";
			
			myStmt = myConn.prepareStatement(sql);
			
			//Step 3. Set position and param values for prepared statement		
			myStmt.setInt(1, flightdetailsid); //remember that setString is one-based!!		 
			
			//Step 4. Execute the query.
			myStmt.execute(); //Executes the SQL statement in this PreparedStatement object, which may be any kind of SQL statement. There is no result set returned.		
			}
			finally {
				//Step 5. Close the connection & JDBC objects. (clean-up code to ensure that you don't run out of resources, esp for prod)
				DBUtil.close(myConn, myStmt, null); //since we don't have a resultset object, we can pass in null for the 3rd argument
			}
	}
}
