<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<main>
		<div class="container" style="text-align:center">        	
        	<!--  Row 1 -->
            <div class="row">
				<h2>Fare Details</h2>
			</div><!-- end row -->			
		</div><!-- end container2 -->
		<!-- table -->
		<table border="1" class="table table-striped">
			<tr>
				<th>Flight Number</th>
				<th>Airline</th>
				<th>Travel Class</th>
				<th>Fare</th>					
				<th>Action</th>			
			</tr>
			
			<!-- As a best practice, use JSTL Tags (instead of JSP Scriptlet Code). Makes the code more readable. -->		
			<c:forEach var="tempFare" items="${FARE_DETAILS_LIST}">
				<!--  set up a link for each user using a JSTL feature called URL -->
				<!-- These 2 parameters are going to the Book Flight Controller Servlet -->
				<!-- We want to actually prepopulate the form on load on the booking details page. 
				     with that flight's information from the database.  -->	
				<c:url var="tempLink" value="FlightBookControllerServlet">
					<c:param name="command" value="BOOK_FARE" />
					<c:param name="flightNumber" value="${tempFare.getFlightDetails.flightNumber}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					<c:param name="airline" value="${tempFare.getFlightDetails.flightName}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					<c:param name="day" value="${tempFare.getFlightDetails.flightday}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					<c:param name="date" value="${SELECTED_DATE}"/> 
					<c:param name="src" value="${tempFare.getFlightDetails.srcAirport}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					<c:param name="srcFullName" value="${tempFare.getFlightDetails.srcAiportFullName}" />
					<c:param name="dest" value="${tempFare.getFlightDetails.destAirport}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					<c:param name="destFullName" value="${tempFare.getFlightDetails.destAiportFullName}" />					
					<c:param name="travelClass" value="${tempFare.travelClass}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					<c:param name="fareUnitCost" value="${tempFare.flightFare}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->							
				</c:url>
				<tr>
					<td>${tempFare.getFlightDetails.flightNumber}</td>
					<td>${tempFare.getFlightDetails.flightName}</td>
					<td>${tempFare.travelClass}</td>
					<td>${tempFare.flightFare}</td>
					<c:if test="${sessionScope.loggedInUser!=null}">
						<td><a href="${tempLink}" class="button">Book Now</a></td> <!-- So, this'll basically create an HREF with the text of Update, and then embedded is the actual tempLink that has the command and the flight number. -->
					</c:if>
					<c:if test="${sessionScope.loggedInUser == null }">
						<td>Please Login to Book Ticket</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		<p>
			<a href="javascript:history.back()">Back to Search Results</a>
		</p>
	</main>
<%@ include file="footer.jsp"%>