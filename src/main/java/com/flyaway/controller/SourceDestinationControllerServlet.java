package com.flyaway.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.flyaway.jdbc.SourceDestinationJDBC;
import com.flyaway.jdbc.UserJDBC;
import com.flyaway.model.SourceDestination;
import com.flyaway.model.User;

/**
 * Servlet implementation class SourceDestinationControllerServlet
 */
@WebServlet("/SourceDestinationControllerServlet")
public class SourceDestinationControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SourceDestinationJDBC sourceDestinationJDBC;   
	
	@Resource(name="jdbc/flyaway")
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SourceDestinationControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			sourceDestinationJDBC = new SourceDestinationJDBC(dataSource);
  		}
  		catch (Exception e) {
  			throw new ServletException(e); //in case there is an error reading from the database or other problem
  		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			
			System.out.println("Inside SourceDestinationControllerServlet doGet()...");
			
			//read the "command" parameter
			String theCommand = request.getParameter("command");
			System.out.println("theCommand = " + theCommand);
			
			//if the command is missing, then default to listing users
			if(theCommand == null) {
				theCommand = "LIST";
			}
			
			//route to the appropriate method in the UserDbUtil	
			switch(theCommand) {			
			
			case "LIST":				
				System.out.println("case: LIST...");
				System.out.println("hidden command = " + theCommand);
				listSourceDestinations(request, response);
				break;
				
			case "ADD":				
				System.out.println("case: ADD...");
				System.out.println("hidden command = " + theCommand);
				addSourceDestination(request, response);
				break;
				
			case "EDIT":
			case "UPDATE":
				System.out.println("case: EDIT/UPDATE...");
				System.out.println("hidden command = " + theCommand);
				updateSourceDestination(request, response);
				break;
				
			case "LOAD":
				System.out.println("case: LOAD...");
				System.out.println("hidden command = " + theCommand);
				loadSourceDestination(request, response);
				break;
			
			case "DELETE":
				System.out.println("case: DELETE...");
				System.out.println("hidden command = " + theCommand);
				deleteSourceDestination(request, response);
				break;
				
			default:
				System.out.println("case: default...");
				System.out.println("hidden command = " + theCommand);				
				listSourceDestinations(request, response);
				break;
			}//end switch	
			
		} catch (Exception e) {			
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
	
	private void listSourceDestinations(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("Inside SourceDestinationControllerServlet listSourceDestinations()...");
		
		//1. Reading the Users from the database using the DB Util
		List<SourceDestination> sourceDestinations = sourceDestinationJDBC.getSourceDestinations();
		System.out.println("sourceDestination list: ");
		System.out.println(sourceDestinations);
		
		//2. Add users list to the request object
		request.setAttribute("SourceDestination_LIST", sourceDestinations);		
				
		//3. Send to list-users.jsp JSP page (view) using dispatcher
		RequestDispatcher dispatcher = request.getRequestDispatcher("sourceDestination_list.jsp");
		dispatcher.forward(request, response);		
	}
	
	private void addSourceDestination(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("Inside SourceDestinationControllerServlet addSourceDestination()...");
		
		//1. Read info from form
		String flightnumber = request.getParameter("flightnumber");
		int airlineid = Integer.parseInt(request.getParameter("airlineid"));
		String src_airportCode = request.getParameter("src_airportCode");
		String dest_airportCode = request.getParameter("dest_airportCode");		
		int totalNumSeats = Integer.parseInt(request.getParameter("totalNumSeats"));
		int availFirstClassSeats = Integer.parseInt(request.getParameter("availFirstClassSeats"));
		int availBusinessClassSeats = Integer.parseInt(request.getParameter("availBusinessClassSeats"));
		int availEconomySeats = Integer.parseInt(request.getParameter("availEconomySeats"));
		
		System.out.println("flightnumber = " + flightnumber);
		System.out.println("airlineid = " + airlineid);
		System.out.println("src_airportCode = " + src_airportCode);
		System.out.println("dest_airportCode = " + dest_airportCode);
		System.out.println("totalNumSeats = " + totalNumSeats);
		System.out.println("availFirstClassSeats = " + availFirstClassSeats);
		System.out.println("availBusinessClassSeats = " + availBusinessClassSeats);
		System.out.println("availEconomySeats = " + availEconomySeats);
		//2. Create a new User object
		SourceDestination tempSourceDestination = new SourceDestination(flightnumber, airlineid, src_airportCode, dest_airportCode, totalNumSeats, availFirstClassSeats, availBusinessClassSeats, availEconomySeats);
		
		//3. Add the User to the database
		sourceDestinationJDBC.addSourceDestination(tempSourceDestination);
		
		//4. Send back to the main page and refresh the User list
		listSourceDestinations(request, response);
		
	}
	
	private void updateSourceDestination(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		
		//Step 1. read values from update-user-form
		int flightdetailsid = Integer.parseInt(request.getParameter("flightdetailsid"));
		String flightnumber = request.getParameter("flightnumber");
		int airlineid = Integer.parseInt(request.getParameter("airlineid"));
		String src_airportCode = request.getParameter("src_airportCode");
		String dest_airportCode = request.getParameter("dest_airportCode");				
		int totalNumSeats = Integer.parseInt(request.getParameter("totalNumSeats"));
		int availFirstClassSeats = Integer.parseInt(request.getParameter("availFirstClassSeats"));
		int availBusinessClassSeats = Integer.parseInt(request.getParameter("availBusinessClassSeats"));
		int availEconomySeats = Integer.parseInt(request.getParameter("availEconomySeats"));
		
		//Step 2. create a new SourceDestination object
		SourceDestination sourceDestination = new SourceDestination(flightdetailsid, flightnumber, airlineid, src_airportCode, dest_airportCode, totalNumSeats, availFirstClassSeats, availBusinessClassSeats, availEconomySeats);
		
		//Step 3. perform update on the database
		sourceDestinationJDBC.updateSourceDestination(sourceDestination);
		
		if(session.getAttribute("isAdminUser").toString().toLowerCase().equals("admin")) {
			//Step 4. send to "list SourceDestinations" page (for admin users only)
			listSourceDestinations(request, response);
		}
		else {
			//Step 4. send to jsp page: update-SourceDestination-form.jsp and pre-populate database information
			RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
	private void loadSourceDestination(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Inside SourceDestinationControllerServlet.loadSourceDestination()...");
		int theflightdetailsid = Integer.parseInt(request.getParameter("flightdetailsid"));
		System.out.println("theflightdetailsid = " + theflightdetailsid);
		
		SourceDestination sourceDestination = sourceDestinationJDBC.getSourceDestination(theflightdetailsid);
		System.out.println("sourceDestination object is: \n" + sourceDestination.toString());
		
		request.setAttribute("THE_SourceDestination", sourceDestination); //(attribute name, object)
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/upd_sourceDestination_form.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void deleteSourceDestination(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// Step 1. read user id from update-user-form data
		int flightdetailsid = Integer.parseInt(request.getParameter("flightdetailsid"));
		
		// Step 2. delete User from database
		sourceDestinationJDBC.deleteSourceDestination(flightdetailsid);
		
		// Step 3. redirect to the "list users" page
		listSourceDestinations(request, response);
		
	}
}
