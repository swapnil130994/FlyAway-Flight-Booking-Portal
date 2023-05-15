package com.flyaway.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.flyaway.jdbc.FlightBookJDBC;
import com.flyaway.model.Airport;
import com.flyaway.model.FlightFareDetails;
import com.flyaway.model.FlightTicket;
import com.flyaway.model.GetFlightDetails;
import com.flyaway.model.User;

/**
 * Servlet implementation class FlightBookControllerServlet
 */
@WebServlet("/FlightBookControllerServlet")
public class FlightBookControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FlightBookJDBC flightBookJDBC;
    
    //Define datasource/connection pool for Resource Injection
    @Resource(name="jdbc/flyaway") //comes from the context.xml (alternatively, I could have used properties file. Chose this method for more readable code.)
    private DataSource dataSource;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightBookControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
  	public void init() throws ServletException {  		
  		try {
  			flightBookJDBC = new FlightBookJDBC(dataSource);
  		}
  		catch (Exception e) {
  			throw new ServletException(e); //in case there is an error reading from the database or other problem
  		}
  		
  	}//end init()
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Inside FlightBookControllerServlet doGet()...");
		try {
					
					String theCommand = request.getParameter("command");
					System.out.println("theCommand = " + theCommand);
					
					if(theCommand == null) {
						theCommand = "LIST";
					}
					
					//route to the appropriate method in the UserDbUtil	
					switch(theCommand) {			
						
					case "LOAD":
						System.out.println("case: LOAD...");
						System.out.println("hidden command = " + theCommand);
						getFlight(request, response);
						break;
						
					case "SEARCH":
						System.out.println("case: SEARCH...");
						System.out.println("hidden command = " + theCommand);
						findFlights(request, response);
						break;
						
					case "BOOK_FARE":
						System.out.println("case: BOOK_FARE...");
						System.out.println("hidden command = " + theCommand);
						bookFlightFare(request, response);
						break;			
						
					case "LIST":
					default:
						System.out.println("case: LIST/default...");
						System.out.println("hidden command = " + theCommand);				
						getCities(request, response);				
						break;
					}//end switch
				} catch (Exception e) {			
					e.printStackTrace();
					throw new ServletException(e);
				} 
	}
	
	private void getFlight(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int theFlightNumber = Integer.parseInt(request.getParameter("flightNumber"));
		System.out.println("theFlightNumber = " + theFlightNumber);
		
		String theAirline = request.getParameter("airline");
		String theDay = request.getParameter("day");
		String theDate = request.getParameter("date");
		String theSrc = request.getParameter("src");//airportcode
		String theSrcFullName = request.getParameter("srcFullName");
		String theDest = request.getParameter("dest");	//airportcode
		String theDestFullName = request.getParameter("destFullName");
		
		GetFlightDetails theSearchResult = new GetFlightDetails(theFlightNumber, theAirline, theDay, theSrc, theSrcFullName, theDest, theDestFullName);
		System.out.println("theSearchResult = \n" + theSearchResult);
		
		//Step 2. get flight details from the database
		List<FlightFareDetails> fareDetails = flightBookJDBC.getFlightFareDetails(theSearchResult);
		
		for(FlightFareDetails x : fareDetails) {
			String temp1 = x.getGetFlightDetails().getDestAirport();
			x.getGetFlightDetails().setDestAiportFullName(flightBookJDBC.getAirportName(temp1));
			String temp2 = x.getGetFlightDetails().getSrcAirport();
			x.getGetFlightDetails().setSrcAiportFullName(flightBookJDBC.getAirportName(temp2));
		}
		
		//Step 3. place flight details in the request by setting attribute that we can refer to in the JSP
		request.setAttribute("FARE_DETAILS_LIST", fareDetails); //(attribute name, object)
		request.setAttribute("SELECTED_DATE", theDate);
		
		//Step 4. send to jsp form
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/flight_fare_details.jsp");
		dispatcher.forward(request, response);		
	}
	
	private void findFlights(HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			System.out.println("Inside BookFlightControllerServlet searchFlights()...");
			
			//Properties
			List<GetFlightDetails> departResults = new ArrayList<>(); //empty list
			List<GetFlightDetails> returnResults = new ArrayList<>(); //empty list
			HttpSession session = request.getSession();
			
			//1. Read info from book-flight.jsp form		
			String flightDirection = request.getParameter("flightDirection");		
			String departureCityString = request.getParameter("departureCity");		
			String arrivalCityString = request.getParameter("arrivalCity");		
			String departDateString = request.getParameter("departDate");		
			String numTravelers = request.getParameter("numOfTravelers");
					
			//Decomposing departureCityString & extracting airportCode		
			String[] str1 = departureCityString.split(" | ");
			System.out.println("str.length = " + str1.length);		
			String departureAirportCode = str1[0];		
			System.out.println("departureAirportCode = " + departureAirportCode);
			System.out.println("departureCityString = " + departureCityString);
			
			//Decomposing arrivalCityString & extracting airportCode
			String[] str2 = arrivalCityString.split(" | ");
			System.out.println("str.length = " + str2.length);		
			String arrivalAirportCode = str2[0];		
			System.out.println("arrivalAirportCode = " + arrivalAirportCode);
			System.out.println("arrivalCityString = " + arrivalCityString);
			
			//1b. Formatting Date and Day fields
			SimpleDateFormat sdfDepart = new SimpleDateFormat("yyyy-MM-dd");
			Date departDate = sdfDepart.parse(departDateString); //Date object has date and time
			String departDay = new SimpleDateFormat("EEEE").format(departDate);
			
			System.out.println("flightDirection = " + flightDirection);		
			System.out.println("departDateString = " + departDateString);
			System.out.println("departDate = " + departDate);
			System.out.println("departDay = " + departDay);		
			System.out.println("numTravelers = " + numTravelers);				
					
			System.out.println("Building the departing flight list...");
			//2. Get matching results from the database using the DB Util
			departResults = flightBookJDBC.findGetFlightDetails(departDay, departureAirportCode, arrivalAirportCode); //departDay, departCity, arrivalCity
			
			for(GetFlightDetails x : departResults) {
				String temp1 = x.getDestAirport();
				x.setDestAiportFullName(flightBookJDBC.getAirportName(temp1));
				String temp2 = x.getSrcAirport();
				x.setSrcAiportFullName(flightBookJDBC.getAirportName(temp2));
			}
			
			System.out.println("Updated departResults List:\n" + departResults);
			
			//3. Add results to request object for the search-results.page.jsp
			request.setAttribute("DEPARTING_SEARCH_RESULTS_LIST", departResults);		
			
			//4. Add results to session object for future use		
			session.setAttribute("departDate", departDateString);
			session.setAttribute("flightDirection", flightDirection);
			session.setAttribute("passengers", numTravelers);
			session.setAttribute("arrivalAC", arrivalAirportCode);
			session.setAttribute("arrivalAirportFullName", arrivalCityString);
			session.setAttribute("departingAirportFullName", departureCityString);
			session.setAttribute("departAC", departureAirportCode);
			
			if(flightDirection.equals("roundtrip")) {
				System.out.println("In the roundtrip block....");
				//1. Read info from book-flight.jsp form					
				String returnDateString = request.getParameter("returnDate");
			
				//Formatting Date and Day fields
				SimpleDateFormat sdfReturn = new SimpleDateFormat("yyyy-MM-dd");
				Date returnDate = sdfReturn.parse(returnDateString);
				String returnDay = new SimpleDateFormat("EEEE").format(returnDate);		
				
				System.out.println("returnDateString = " + returnDateString);
				System.out.println("returnDate = " + returnDate);
				System.out.println("returnDay = " + returnDay);
				System.out.println("arrivalAirportCode = " + arrivalAirportCode);
				System.out.println("departureAirportCode = " + departureAirportCode);
							
				System.out.println("Building the return flight list...");
				//2. Get matching results from the database using the DB Util
				returnResults = flightBookJDBC.findGetFlightDetails(returnDay, arrivalAirportCode, departureAirportCode); //arrivalDay, arrivalCity
				
				for(GetFlightDetails x : returnResults) {
					String temp1 = x.getDestAirport();
					x.setDestAiportFullName(flightBookJDBC.getAirportName(temp1));
					String temp2 = x.getSrcAirport();
					x.setSrcAiportFullName(flightBookJDBC.getAirportName(temp2));
				}
				
				System.out.println("Updated returnResults List:\n" + returnResults);
				
				//3. Add results to request object for the search-results.page.jsp
				request.setAttribute("RETURN_SEARCH_RESULTS_LIST", returnResults);
				
				//4. Add results to session object for future use
				session.setAttribute("returnDate", returnDateString);			
			}
			
			//5. Send to search-results-page.jsp JSP page (view) using dispatcher
			RequestDispatcher dispatcher = request.getRequestDispatcher("search_flight.jsp");
			dispatcher.forward(request, response);
		}
	
	private void bookFlightFare(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//Properties
		HttpSession session = request.getSession();
		User customer = (User) session.getAttribute("loggedInUser");
		FlightTicket objFlightTicket = new FlightTicket();		
		
		//1. Read FareDetails info from fare-details.jsp form
		String selectedFlightNumber = request.getParameter("flightNumber");
		String selectedAirline = request.getParameter("airline");
		String selectedDay = request.getParameter("day");
		String selectedTravelClass = request.getParameter("travelClass");
		String selectedSource = request.getParameter("src");//airport code
		String selectedSourceFN = request.getParameter("srcFullName");
		String selectedDest = request.getParameter("dest");//airport code
		String selectedDestFN = request.getParameter("destFullName");
		String selectedFare = request.getParameter("fareUnitCost");
		String selectedDate = request.getParameter("date");
		String selectedDirection = session.getAttribute("flightDirection").toString().toLowerCase();
		System.out.println("selectedDirection = " + selectedDirection);
		
		//Formatting necessary String data
		BigDecimal test = BigDecimal.valueOf(Double.valueOf(selectedFare));
		int passengers = Integer.parseInt(session.getAttribute("passengers").toString());
		BigDecimal totalCost = test.multiply(new BigDecimal(passengers));
				
		//Debug Statements
		System.out.println("selectedFlightNumber = " + selectedFlightNumber);
		System.out.println("selectedAirline = " + selectedAirline);
		System.out.println("selectedTravelClass = " + selectedTravelClass);
		System.out.println("selectedSource = " + selectedSource);
		System.out.println("selectedSourceFN = " + selectedSourceFN);
		System.out.println("selectedDest = " + selectedDest);
		System.out.println("selectedDestFN = " + selectedDestFN);
		System.out.println("selectedFare = " + selectedFare);
		System.out.println("selectedDay = " + selectedDay);
		System.out.println("selectedDate = " + selectedDate);
		System.out.println("passengers (session) = " + passengers);		
		System.out.println("total cost = " + totalCost);
		
		//3. Created a temporary ticket with the data. 
		//The object will passed along to the payment confirmation screen
		//objFlightTicket.bookingid will be assigned at the database level
		objFlightTicket.setUserid(customer.getUserid());
		objFlightTicket.setFlightbookStatusid(1); //1-pending; will get set to 2-confirmed when selected button is pressed, and 3-purchased when payment has been processed
		//objFlightTicket FlightbookStatusid logic
		switch(selectedDirection) {			
			case "one-way":
			case "oneway":
				objFlightTicket.setFlightbookStatusid(1);
				break;		
			
			case "roundtrip":
			case "round trip":
			default:
				objFlightTicket.setFlightbookStatusid(2);
				break;
		}
		objFlightTicket.setFlightNumber(Integer.parseInt(selectedFlightNumber));
		//objFlightTicket travelClass logic
		switch(selectedTravelClass.toLowerCase()) {
		case "business":
			objFlightTicket.setTravelClassid(2);
			break;
			
		case "first/premium class":
			objFlightTicket.setTravelClassid(3);
			break;
			
		case "economy":
		default:	
			objFlightTicket.setTravelClassid(1);
			break;		
		}
		objFlightTicket.setTravelDay(selectedDay);
		objFlightTicket.setTravelDate(selectedDate);
		objFlightTicket.setTotalPassengers(passengers);
		objFlightTicket.setTotalFare(totalCost);		
		objFlightTicket.setSrcAirportCode(selectedSource);
		objFlightTicket.setSrcAiportFullName(selectedSourceFN);
		objFlightTicket.setDestAirportCode(selectedDest);
		objFlightTicket.setDestAirportFullName(selectedDestFN);
		System.out.println("Temp Ticket: \n" + objFlightTicket.toString());
		
		//4. Add results to request object for the search-results.page.jsp
		request.setAttribute("selectedFlightNumber", selectedFlightNumber);
		request.setAttribute("selectedTravelClass", selectedTravelClass);
		request.setAttribute("selectedTotalCost", totalCost);
		
		session.setAttribute("selectedAirline", selectedAirline);		
		session.setAttribute("selectedSrc", selectedSource);
		session.setAttribute("selectedSourceFN", selectedSourceFN);
		session.setAttribute("selectedDest", selectedDest);
		session.setAttribute("selectedDestFN", selectedDestFN);
		session.setAttribute("selectedDay", selectedDay);		
		session.setAttribute("selectedDate", selectedDate);
		session.setAttribute("pendingTicket", objFlightTicket); //will be sent to the database in the next step
		
		//Step 5. send to jsp form
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/flight_book_details.jsp"); 
		dispatcher.forward(request, response);
		
	}
	
	private void getCities(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Inside BookFlightControllerServlet listCities()...");
		HttpSession session = request.getSession();
		
		//1. Reading the Cities from the database using the DB Util
		List<Airport> airports = flightBookJDBC.getAirports();
		System.out.println("airports: ");
		System.out.println(airports);
		
		//2. Add cities list to the request object
		session.setAttribute("CITIES_LIST", airports);		
				
		//3. Send to book-flight.jsp JSP page (view) using dispatcher
		RequestDispatcher dispatcher = request.getRequestDispatcher("flight_book.jsp");
		dispatcher.forward(request, response);
	}
}
