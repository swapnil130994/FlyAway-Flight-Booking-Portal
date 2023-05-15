package com.flyaway.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.flyaway.model.Airport;
import com.flyaway.model.FlightFareDetails;
import com.flyaway.model.FlightTicket;
import com.flyaway.model.GetFlightDetails;

public class FlightBookJDBC {
	private DataSource dataSource;
	
	//Constructor with Dependency injection
	public FlightBookJDBC(DataSource dataSource) {		
		this.dataSource = dataSource;
	}
	
	public List<FlightFareDetails> getFlightFareDetails(GetFlightDetails getFlightDetails) throws Exception{
		
		System.out.println("Inside BookingDbUtil.getFareDetails()...");
		
		List<FlightFareDetails> results = new ArrayList<>(); //empty list
		
		//JDBC variables
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		//try-finally block
		try {	
			
			//Step 1. Establishing a connection.
			System.out.println("1. Establishing a connection to the database..");
			conn = dataSource.getConnection();
			System.out.println("myConn = " + conn.toString());
			
			//Step 2. Create a SQL JOIN parameterized statement with placeholders
			System.out.println("2. Create SQL JOIN statement..");
			String sql = "SELECT flightdetails.flightnumber"
					+ " ,airlines.companyName"
					+ " ,flightdays.dayName"
					+ " ,flightdetails.src_airportCode"
					+ " ,flightdetails.dest_airportCode"
					+ " ,travelclass.travelclassName"
					+ " ,flightfaredetails.fare"
					+ " FROM flightdays" 
					+ " JOIN flightdetails ON flightdetails.flightnumber=flightdays.flightnumber"
					+ " JOIN airlines ON flightdetails.airlineid=airlines.airlineid"
					+ " JOIN flightfaredetails ON flightdetails.flightnumber=flightfaredetails.flightnumber"
					+ " JOIN travelclass ON flightfaredetails.travelclassid=travelclass.travelclassid"
					+ " WHERE flightdetails.flightnumber=?"
					+ " AND flightdays.dayName=?"
					+ " AND flightdetails.src_airportCode=?"
					+ " AND flightdetails.dest_airportCode=?";
			System.out.println("sql = " + sql);			
			ps = conn.prepareStatement(sql);
			
			//Step 3. Set position and parameter values for the prepared statement
			System.out.println("3. Set position and parameter values for the prepared statement..");
			ps.setInt(1, getFlightDetails.getFlightNumber());
			ps.setString(2, getFlightDetails.getFlightday());
			ps.setString(3, getFlightDetails.getSrcAirport());
			ps.setString(4, getFlightDetails.getDestAirport());
			
			//Step 4. Execute the query
			System.out.println("4. Executing query...");
			rs = ps.executeQuery();
			
			//Step 5. Process the ResultSet object.
			System.out.println("5. Processing the ResultSet Object...");
			while(rs.next()) {
				//retrieve data from result set row
				int flightNumber = rs.getInt("flightdetails.flightnumber");
				String airline = rs.getString("airlines.companyName");
				String day = rs.getString("flightdays.dayName");
				String srcAirport = rs.getString("flightdetails.src_airportCode");
				String destAirport = rs.getString("flightdetails.dest_airportCode");
				String travelClass = rs.getString("travelclass.travelclassName");
				BigDecimal fare = rs.getBigDecimal("flightfaredetails.fare");
				
				GetFlightDetails tempSearchResult = new GetFlightDetails(flightNumber, airline,day,srcAirport, destAirport);				
				
				//create new FareDetails object based on the result set row
				FlightFareDetails tempFare = new FlightFareDetails(tempSearchResult, travelClass, fare);
				System.out.println("tempFare Object:\n" + tempFare.toString());
								
				//add SearchResult object to the list of SearchResults (not writing to the database)				
				results.add(tempFare);
			}//end while()
			
			return results;
		}
		finally {
			System.out.println("5. Close the connection & JDBC objects...");
			DBUtil.close(conn, ps, rs);
		}
	}
	
	public String getAirportName(String code) throws Exception{
		System.out.println("Inside BookingDbUtil.getAirportName(String airportcode)...");
		System.out.println("passed in code = " + code);
		String airportName = null;
		
		//JDBC variables
		Connection myConn = null;
		PreparedStatement myPs = null;
		ResultSet myRs = null;
		
		//try-finally block		
		try {
			//Step 1. Establishing a connection.
			System.out.println("1. Establishing a connection to the database..");
			myConn = dataSource.getConnection();
			System.out.println("myConn = " + myConn.toString());
			
			//Step 2. Create a statement.
			System.out.println("2. Create SQL select statement..");
			String sql = "SELECT * from airportcodes"
						+ " WHERE airportCode=?";
			myPs = myConn.prepareStatement(sql);				
			
			System.out.println("3. Set position and parameter values for the prepared statement..");
			myPs.setString(1, code);				
			
			System.out.println("4. Executing query...");
			myRs = myPs.executeQuery();			
			
			//Step 5. Process the ResultSet object.
			System.out.println("5. Process the ResultSet Object...");
			while(myRs.next()) {
				//retrieve data from result set row
				airportName = myRs.getString("airportName");
				System.out.println("Airport code (" + code + ") = " + airportName);
			}//end while()
			return airportName;
		}
		finally {
			//Step 5. Close the connection & JDBC objects. (clean-up code to ensure that you don't run out of resources, esp for prod)
			System.out.println("5. Close the connection & JDBC objects...");
			DBUtil.close(myConn, myPs, myRs);
		}
		
	}
	
	public List<GetFlightDetails> findGetFlightDetails(String day, String src, String dest) throws Exception{
		
		System.out.println("Inside BookingDbUtil.getSearchResults()...");
		
		List<GetFlightDetails> results = new ArrayList<>(); //empty list
		
		//JDBC variables
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		//try-finally block		
		try {
			//Step 1. Establishing a connection.
			System.out.println("1. Establishing a connection to the database..");
			conn = dataSource.getConnection();
			System.out.println("myConn = " + conn.toString());
			
			//Step 2. Create a SQL JOIN parameterized statement with placeholders
			System.out.println("2. Create SQL JOIN statement..");
			String sql = "SELECT flightdetails.flightnumber"
						+ " ,airlines.companyName"
						+ " ,flightdays.dayName"
						+ " ,flightdetails.src_airportCode"
						+ " ,flightdetails.dest_airportCode"
						+ " FROM flightdays" 
						+ " JOIN flightdetails ON flightdetails.flightnumber=flightdays.flightnumber"
						+ " JOIN airlines ON flightdetails.airlineid=airlines.airlineid"
						+ " WHERE flightdays.dayName=?"
						+ " AND flightdetails.src_airportCode=?"
						+ " AND flightdetails.dest_airportCode=?"; 
			System.out.println("sql = " + sql);			
			ps = conn.prepareStatement(sql);
			
			//Step 3. Set position and parameter values for the prepared statement
			System.out.println("3. Set position and parameter values for the prepared statement..");
			ps.setString(1, day);
			ps.setString(2, src);
			ps.setString(3, dest);
			
			//Step 4. Execute the query
			rs = ps.executeQuery();
			
			//Step 5. Process the ResultSet object.
			System.out.println("5. Process the ResultSet Object...");
			while(rs.next()) {
				//retrieve data from result set row
				int flightnumber = rs.getInt("flightdetails.flightnumber");
				String airportName = rs.getString("airlines.companyName");
				String dayOfWeek = rs.getString("flightdays.dayName");
				String source = rs.getString("flightdetails.src_airportCode");
				String destination = rs.getString("flightdetails.dest_airportCode");
				
				System.out.println("flightnumber = " + flightnumber);
				System.out.println("airportName = " + airportName);
				System.out.println("day = " + dayOfWeek);
				System.out.println("source = " + source);
				System.out.println("dest = " + destination);
				
				//create new SearchResult object based on the result set row
				GetFlightDetails tempSearchResult = new GetFlightDetails(flightnumber, airportName, dayOfWeek, source, destination);
				System.out.println("tempSearchResult Object:\n" + tempSearchResult.toString());
								
				//add SearchResult object to the list of SearchResults (not writing to the database)				
				results.add(tempSearchResult);
			}//end while()
			
			return results;			
		}
		finally {
			//Step 5. Close the connection & JDBC objects. (clean-up code to ensure that you don't run out of resources, esp for prod)
			System.out.println("5. Close the connection & JDBC objects...");
			//DBUtil.close(myConn, myStmt, myRs);
			DBUtil.close(conn, ps, rs);
		}		
	}
	
	public List<Airport> getAirports() throws Exception{
		
		List<Airport> airports = new ArrayList<>(); //empty list		
		
		//JDBC variables
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;	
		
		//try-finally block		
		try {
			//Step 1. Establishing a connection.
			System.out.println("1. Establishing a connection to the database..");
			conn = dataSource.getConnection();
			System.out.println("myConn = " + conn.toString());
			
			//Step 2. Create a statement.
			System.out.println("2. Create SQL select statement..");
			String sql = "select * from airportcodes"; 
			stmt = conn.createStatement();
			
			//Step 3. Execute the query.
			System.out.println("3. Execute the select query...");
			rs = stmt.executeQuery(sql);
			
			//Step 4. Process the ResultSet object.
			System.out.println("4. Process the ResultSet Object...");
			while(rs.next()) {
				//retrieve data from result set row
				int airportid = rs.getInt("airportCodeid");
				int countryid = rs.getInt("countryid");
				String airportCode = rs.getString("airportCode");
				String airportName = rs.getString("airportName");
				//create new Airport object based on the result set row
				Airport tempAirport = new Airport(airportid, countryid, airportCode, airportName);
								
				//add User object to the list of Users (not writing to the database)
				//System.out.println("Add the tempUser to the users ArrayList");
				airports.add(tempAirport);			
			}//end while()
			
			return airports;
		}
		finally {
			//Step 5. Close the connection & JDBC objects. (clean-up code to ensure that you don't run out of resources, esp for prod)
			System.out.println("5. Close the connection & JDBC objects...");
			DBUtil.close(conn, stmt, rs);
		}	
		
	}
	
	public int saveFlightTicket(FlightTicket tempTicket) throws Exception{
		System.out.println("Inside BookingDbUtil.storeTicket()...");
		System.out.println("Passed in tempTicket: \n" + tempTicket);
		
		int assignedid = 0;
		
		//JDBC variables
		Connection myConn=null, myConn2 = null;
		PreparedStatement myPs=null, myPs2 = null;
		ResultSet myRs=null;
		
		//try-finally block
		try {	
			
			//Step 1. Establishing a connection.
			System.out.println("1. Establishing a connection to the database..");
			myConn = dataSource.getConnection();
			System.out.println("myConn = " + myConn.toString());
			
			//Step 2. Create a SQL INSERT parameterized statement with placeholders
			System.out.println("2. Create SQL INSERT statement..");
			String sql = "INSERT INTO ticketdetails"
						+ " (userid, bookingstatusid, directionid, flightnumber, travelclassid, travelDate, totalPassengers, totalfare)"
						+ " VALUES (?,?,?,?,?,?,?,?)";
			System.out.println("sql = " + sql);			
			myPs = myConn.prepareStatement(sql);			
			
			//Step 3. Set position and parameter values for the prepared statement
			System.out.println("3. Set position and parameter values for the prepared statement..");
			myPs.setInt(1, tempTicket.getUserid());
			myPs.setInt(2, tempTicket.getFlightbookStatusid());
			myPs.setInt(3, tempTicket.getTriptypeid());
			myPs.setInt(4, tempTicket.getFlightNumber());
			myPs.setInt(5, tempTicket.getTravelClassid());
			myPs.setString(6, tempTicket.getTravelDate());
			myPs.setInt(7, tempTicket.getTotalPassengers());
			myPs.setBigDecimal(8, tempTicket.getTotalFare());							
			
			//Step 4. Execute the Update query
			System.out.println("4. Executing query...");
			myPs.executeUpdate(); //Executes the SQL statement in this PreparedStatement object
			
			//DBUtil.close(myConn, myPs, null);		
			
			myConn2 = dataSource.getConnection();	
			System.out.println("myConn2 = " + myConn2.toString());
			
			String sql2 = "SELECT bookingid FROM ticketdetails"
					+ " WHERE userid=?"
					+ " AND bookingstatusid=?"
					+ " AND directionid=?"
					+ " AND flightnumber=?"
					+ " AND travelclassid=?"
					+ " AND travelDate=?"
					+ " AND totalPassengers=?"
					+ " AND totalfare=?";
			System.out.println("sql2 = " + sql2);
			myPs2 = myConn2.prepareStatement(sql2);	
			
			System.out.println("tempTicket: \n" + tempTicket.toString());
			
			System.out.println("3. Set position and parameter values for the prepared statement..");
			myPs2.setInt(1, tempTicket.getUserid());
			myPs2.setInt(2, tempTicket.getFlightbookStatusid());
			myPs2.setInt(3, tempTicket.getTriptypeid());
			myPs2.setInt(4, tempTicket.getFlightNumber());
			myPs2.setInt(5, tempTicket.getTravelClassid());
			myPs2.setString(6, tempTicket.getTravelDate());
			myPs2.setInt(7, tempTicket.getTotalPassengers());
			myPs2.setBigDecimal(8, tempTicket.getTotalFare());	
			
			System.out.println("4. Executing query...");
			myRs = myPs2.executeQuery();
			
			while(myRs.next()) {
				System.out.println("inside the resultset while...");
				assignedid = myRs.getInt("bookingid");
				System.out.println("assignedid = " + assignedid);				
			}
			
			return assignedid;			
		}
		finally {
			//Step 5. Close the connection & JDBC objects. (clean-up code to ensure that you don't run out of resources, esp for prod)
			System.out.println("5. Close the connection & JDBC objects...");			
			DBUtil.close(myConn, myPs, null);
			DBUtil.close(myConn2, myPs2, myRs);
		}
	}
	
	public FlightTicket getFlightTicket(int bookingid) throws Exception {		
		System.out.println("Inside BookingDbUtil.getTicket()..");
		System.out.println("passed in bookingid=" + bookingid);
		
		FlightTicket theTicket = new FlightTicket();
		//JDBC Objects
		Connection myConn = null;
		PreparedStatement myPs = null;
		ResultSet myRs = null;	
		
		//try-finally block		
		try {
			
			//Step 1. Establishing a connection.
			System.out.println("1. Establishing a connection to the database..");
			myConn = dataSource.getConnection();
			System.out.println("myConn = " + myConn.toString());
			
			//Step 2. Create a SQL SELECT parameterized statement with placeholders
			System.out.println("2. Create SQL UPDATE statement..");
			String sql = "SELECT * from ticketdetails"
						+ " WHERE bookingid=?";
			
			System.out.println("sql = " + sql);			
			myPs = myConn.prepareStatement(sql);			
			
			//Step 3. Set position and parameter values for the prepared statement
			System.out.println("3. Set position and parameter values for the prepared statement..");
			myPs.setInt(1, bookingid);			
			
			//Step 4. Execute the Update query
			System.out.println("4. Executing query...");
			myRs = myPs.executeQuery(); //Executes the SQL statement in this PreparedStatement object
			
			while(myRs.next()) {				
				theTicket.setFlightbookingid(myRs.getInt("bookingid"));
				theTicket.setUserid(myRs.getInt("userid"));
				theTicket.setFlightbookStatusid(myRs.getInt("bookingstatusid"));
				theTicket.setTriptypeid(myRs.getInt("directionid"));
				theTicket.setFlightNumber(myRs.getInt("flightnumber"));
				theTicket.setTravelClassid(myRs.getInt("travelclassid"));
				theTicket.setTravelDate(myRs.getString("travelDate"));
				theTicket.setTotalPassengers(myRs.getInt("totalPassengers"));
				theTicket.setTotalFare(myRs.getBigDecimal("totalFare"));
			}
			return theTicket;			
		}
		finally {
			//Step 5. Close the connection & JDBC objects. (clean-up code to ensure that you don't run out of resources, esp for prod)
			System.out.println("5. Close the connection & JDBC objects...");			
			DBUtil.close(myConn, myPs, myRs);
		}
		
	}
	
	public void updateBookingStatus(int bookingid, int bookingStatus) throws Exception{
		System.out.println("Inside BookingDbUtil.updatedBookingStatus(int, bookingid, int bookingStatus)...");
		System.out.println("passed in bookingid=" + bookingid + ", bookingStatus=" + bookingStatus);
		
		Connection myConn = null;
		PreparedStatement myPs = null;		
		
		//try-finally block		
		try {
			
			//Step 1. Establishing a connection.
			System.out.println("1. Establishing a connection to the database..");
			myConn = dataSource.getConnection();
			System.out.println("myConn = " + myConn.toString());
			
			//Step 2. Create a SQL JOIN parameterized statement with placeholders
			System.out.println("2. Create SQL INSERT statement..");
			String sql = "UPDATE ticketdetails"
						+ " SET bookingstatusid=?"
						+ " WHERE bookingid = ?";
			
			System.out.println("sql = " + sql);			
			myPs = myConn.prepareStatement(sql);			
			
			//Step 3. Set position and parameter values for the prepared statement
			System.out.println("3. Set position and parameter values for the prepared statement..");
			myPs.setInt(1, bookingStatus);
			myPs.setInt(2, bookingid);
			
			//Step 4. Execute the Update query
			System.out.println("4. Executing query...");
			myPs.executeUpdate(); //Executes the SQL statement in this PreparedStatement object		
		}
		finally {
			//Step 5. Close the connection & JDBC objects. (clean-up code to ensure that you don't run out of resources, esp for prod)
			System.out.println("5. Close the connection & JDBC objects...");
			DBUtil.close(myConn, myPs, null);
		}		
	}
}
