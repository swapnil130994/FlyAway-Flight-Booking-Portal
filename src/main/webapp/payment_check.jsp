<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<main>
        <div class="container" style="text-align:center">
        	<!--  Row 1 -->
            <div class="row alert alert-success" role="alert">                
                <h2>Payment Successful</h2> <br><br>
            </div><!-- end row -->
        	<!--  Row 1 -->
            <div class="row">                
                <h2>Payment Confirmation</h2> <br><br>            
            </div><!-- end row -->            
            
            <!--  table -->
	        <table border="1" class="table">
	        <tr>
	        	<th>Booking Id</th>
				<td>${purchasedTicket.flightbookingid}</td>
	        </tr>
	        <tr>
	        	<th>Customer</th>
				<td>${sessionScope.loggedInUser.fullName}</td>
	        </tr>
	        <tr>
	        	<th>Flight Number</th>
				<td>${purchasedTicket.flightNumber}</td>
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
	        <tr>
	        	<th>Travel Date</th>
	        	<td>${selectedDate}</td>
	        </tr>
	        <tr>
	        	<th>No. of Passengers</th>
	        	<td>${purchasedTicket.totalPassengers}</td>
	        </tr>
	        <tr>
	        	<th>Total Amount to be Paid</th>
	        	<td>${purchasedTicket.totalFare}</td>
	        </tr>
	        	        
	        </table> 
            
            <a href="javascript:history.back()">Back to Payment Form</a>
            
     	</div><!--  end container2 -->              
    </main>       
 <%@ include file="footer.jsp"%>