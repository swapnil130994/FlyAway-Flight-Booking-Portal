<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<main>
    	<div class="container" style="text-align:center">
        	<!--  Row 1 -->
            <div class="row">
				<h2>${loggedInUser.fullName}'s Booking Details</h2>
			</div><!-- end row -->
		</div><!-- end container2 -->
		
		<!--  table -->
	        <table border="1" class="table table-striped">        
				<tr>
					<th>Booking Id</th> <!-- Table: ticketdetails -->
					<th>Booking Status</th> <!-- Table: ticketdetails -->
					<th>Purchase Date</th> <!-- Table: ticketdetails -->
					<th>Flight Number</th> <!-- Table: ticketdetails -->
					<th>Airline</th> <!-- Table: flightdetails joined airlines -->
					<th>Travel Class</th> <!-- Table: ticketdetails joined travelclass -->
					<th>Departure Travel Day</th> <!-- calculated -->
					<th>Departure Travel Date</th> <!-- Table: ticketdetails -->				
					<th>Source Airport</th> <!-- retrieved code from db; calculated name -->					
					<th>Destination Airport</th> <!-- retrieved code from db; calculated name  -->
					<th>Passengers</th> <!-- Table: ticketdetails -->
					<th>Total Fare</th>	<!-- Table: ticketdetails -->							
				</tr>
				
				<c:forEach var="temp" items="${BOOKING_HISTORY_LIST}">
					<tr>
						<td>${temp.bookingid}</td>
						<td>${temp.bookingStatus}</td>
						<td>${temp.purchaseDate}</td>
						<td>${temp.flightNumber}</td>
						<td>${temp.airline}</td>
						<td>${temp.travelClass}</td>
						<td>${temp.travelDay}</td>
						<td>${temp.travelDate}</td>
						<td>${temp.sourceAirportCode} | ${temp.sourceAirportName} </td>
						<td>${temp.destAirportCode} | ${temp.destAirportName}</td>
						<td>${temp.totalPassengers}</td>
						<td>${temp.totalFare}</td>
					</tr>
				</c:forEach>				
			</table>
	</main>
 <%@ include file="footer.jsp"%>