<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">  --> 

	<main>
    	<div class="container" style="text-align:center">
        	
        	<!--  Row 1 -->
            <div class="row">
				<h2>Source Destination List</h2>
			</div><!-- end row -->
			
			<!--  Row 2 -->
            <div class="row"> 				
				<!-- new button for Add User -->
				<!-- Using JavaScript to redirect the browser to a new page -->
				<button type="submit"
					   class="add-user-button"	 
					   onClick="window.location.href='add_source_destination_form.jsp'; return false;">
					   Add Source Destination
				</button>	
				<br><br>
			</div><!-- end row -->
		</div><!-- end container2 -->				
						
		<!-- table -->
		<table border="1" class="table table-striped">
			<thead>
				<tr>
					<th scope="col">Flight Number</th>
					<th scope="col">Airline</th>
					<th scope="col">Source Airport Code</th>
					<th scope="col">Destination Airport Code</th>	
					<th scope="col">Total Seats</th>
					<th scope="col">Available First Class Seats</th>
					<th scope="col">Available Business Class Seats</th>
					<th scope="col">Available Economy Class Seats</th>	
					<th scope="col">Action</th>			
				</tr>
			</thead>
			
			<tbody>			
				<!-- As a best practice, use JSTL Tags (instead of JSP Scriptlet Code). Makes the code more readable. -->			
				<c:forEach var="tempSourceDestination" items="${SourceDestination_LIST}">
				
					<!--  set up a link for each user using a JSTL feature called URL -->
					<!-- These 2 parameters are going to the User Controller Servlet -->
					<!-- We want to actually prepopulate the form on load. 
					     So when the user selects a user to update, we will prepopulate the form with that user's information from the database.  -->	
					<c:url var="tempLink" value="SourceDestinationControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="flightdetailsid" value="${tempSourceDestination.flightdetailsid}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					</c:url>	
					
					<c:url var="deleteLink" value="SourceDestinationControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="flightdetailsid" value="${tempSourceDestination.flightdetailsid}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					</c:url>
				
					<tr>
						<td>${tempSourceDestination.flightnumber}</td><!-- Will actually call tempUser.getFullName() due to JSP expression language -->
						<td>${tempSourceDestination.airlineid}</td><!-- Will actually call tempUser.getUserName() due to JSP expression language -->
						<td>${tempSourceDestination.src_airportCode}</td><!-- Will actually call tempUser.getRole() due to JSP expression language -->
						<td>${tempSourceDestination.dest_airportCode}</td><!-- Will actually call tempUser.getEmail() due to JSP expression language -->
						<td>${tempSourceDestination.totalNumSeats}</td>
						<td>${tempSourceDestination.availFirstClassSeats}</td>
						<td>${tempSourceDestination.availBusinessClassSeats}</td>
						<td>${tempSourceDestination.availEconomySeats}</td>
						<td>
							<a href="${tempLink}">Update</a> <!-- So, this'll basically create an HREF with the text of Update, and then embedded is the actual tempLink that has the command and the User ID. -->
							|
							<!-- onclick handler is Javascript. proof that you can integrate Javascript with JSP technology -->
							<a href="${deleteLink}" 
							onclick="if (!(confirm('Are you sure you want to delete this user?'))) return false">Delete</a>						
						</td>																		
					</tr>	
				</c:forEach>
			</tbody>				
		</table>
	</main>
<%@ include file="footer.jsp"%>