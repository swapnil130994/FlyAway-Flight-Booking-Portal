<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<main>
			<div class="container" style="text-align:center">        	
	        	<!--  Row 1 -->
	            <div class="row">
					<h2>Booking Details</h2>
				</div><!-- end row -->			
			</div><!-- end container2 -->
			
			<!--  table -->
	        <table border="1" class="table">
	        <tr>
	        	<th>Flight Number</th>
				<td>${selectedFlightNumber}</td>
	        </tr>
	        <tr>
	        	<th>Airline</th>
	        	<td>${selectedAirline}</td>
	        </tr>
	        <tr>
	        	<th>Travel Class</th>
	        	<td>${selectedTravelClass}</td>
	        </tr>
	        <tr>
	        	<th>Source</th>
	        	<td>${selectedSrc} | ${selectedSourceFN}</td>
	        </tr>
	        <tr>
	        	<th>Destination</th>
	        	<td>${selectedDest} | ${selectedDestFN}</td>
	        </tr>
	        <tr>
	        	<th>Travel Day</th>
	        	<td>${selectedDay}</td>
	        </tr>
	        <tr>
	        	<th>Travel Date</th>
	        	<td>${selectedDate}</td>
	        </tr>
	        <tr>
	        	<th>No. of Passengers</th>
	        	<td>${sessionScope.passengers}</td>
	        </tr>
	        <tr>
	        	<th>Total Amount to be Paid</th>
	        	<td>${selectedTotalCost}</td>
	        </tr>
	        
	        <tr>
	        	<th>Passenger Details</th>
        	</tr>
        	<tr>	        	
	        	<th>Customer Id:</th>
	        	<td>${sessionScope.loggedInUser.userid}</td>
        	</tr>
	        <tr>
	        	<th>Customer Name:</th>
	        	<td>${sessionScope.loggedInUser.fullName}</td>
        	</tr>
        	<tr>
	        	<th>Email Address:</th>
	        	<td>${sessionScope.loggedInUser.userEmail}</td>
	        </tr>
	        <tr>
	        	<th>Phone Number:</th>
	        	<td>${sessionScope.loggedInUser.userContactNumber}</td>
	        </tr>	        
	        </table>
	        </main>
	        <c:url var="tempLink" value="PaymentServlet">
					<c:param name="command" value="PAY" />
			</c:url>
			<a href="${tempLink}" class="button">Go to Payment</a>
	        <!--   <a href="payment-form.jsp" class="button">Go to Payment</a>-->  <!--  should go to payment form -->
	        <!--<button type="submit">Go to Payment</button>-->
	        <a href="javascript:history.back()">Back to Fare Details</a>
	
<%@ include file="footer.jsp" %>