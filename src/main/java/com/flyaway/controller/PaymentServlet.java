package com.flyaway.controller;

import java.io.IOException;
import java.math.BigDecimal;

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

import com.flyaway.jdbc.FlightBookJDBC;
import com.flyaway.model.FlightTicket;

/**
 * Servlet implementation class PaymentServlet
 */
@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FlightBookJDBC flightBookJDBC;
	
	@Resource(name="jdbc/flyaway") 
	private DataSource dataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			flightBookJDBC = new FlightBookJDBC(dataSource);
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
			
			//read the "command" parameter
			String theCommand = request.getParameter("command");
			System.out.println("theCommand = " + theCommand);
			
			//if the command is missing, then default to listing users
			if(theCommand == null) {
				theCommand = "PAY";
			}
			
			//route to the appropriate method in the UserDbUtil	
			switch(theCommand) {
				case "PAY":		
				default:
					System.out.println("case: PAY...");
					System.out.println("hidden command = " + theCommand);
					paymentCollect(request, response);
					break;
					
				case "PAYMENT_SUCCESS":					
					System.out.println("case: PAYMENT_SUCCESS...");
					System.out.println("hidden command = " + theCommand);
					flightBookingFinish(request, response);
					break;
			}//end switch
		} catch (Exception e) {			
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void paymentCollect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Inside PaymentServlet.collectPayment()....");
		
		HttpSession session = request.getSession();
		FlightTicket tempTicket = (FlightTicket) session.getAttribute("pendingTicket");
		FlightTicket savedTicket = new FlightTicket();
		
		BigDecimal totalCost = tempTicket.getTotalFare();		
		System.out.println("totalCost = " + totalCost);
		System.out.println("tempTicket: \n" + tempTicket.toString());
		
		//update bookingStatus
		System.out.println("Updating booking status to 2-confirmed, since the user confirmed the details...");
		tempTicket.setFlightbookStatusid(2); //2-confirmed //it will be changed to 3-purchased after successful processing
		
		//save the ticket to the database and get the assigned bookingid from the database
		int x = flightBookJDBC.saveFlightTicket(tempTicket);
		System.out.println("assigning savedTicket to the ticket retrieved from the database ");
		savedTicket = flightBookJDBC.getFlightTicket(x);
		savedTicket.setTravelDay(tempTicket.getTravelDay());		
		savedTicket.setDestAirportCode(tempTicket.getDestAirportCode());
		savedTicket.setSrcAirportCode(tempTicket.getSrcAirportCode());
		savedTicket.setDestAirportFullName(tempTicket.getDestAirportFullName());
		savedTicket.setSrcAiportFullName(tempTicket.getSrcAiportFullName());
		System.out.println("savedTicket: \n" + savedTicket.toString());		
		
		session.setAttribute("savedTicket", savedTicket);
		session.removeAttribute("pendingTicket"); //moved to the end		
		
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/payment_form.jsp"); 
		dispatcher.forward(request, response);
	}
	
	private void flightBookingFinish(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		FlightTicket updatedTicket = (FlightTicket) session.getAttribute("savedTicket");
		
		//update bookingStatusid to 3-purchased
		flightBookJDBC.updateBookingStatus(updatedTicket.getFlightbookingid(), 3);
		request.setAttribute("purchasedTicket", updatedTicket);
		//session.removeAttribute("pendingTicket");
		session.removeAttribute("savedTicket");			
		
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/payment_check.jsp"); 
		dispatcher.forward(request, response);
		
	}
}
