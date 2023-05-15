<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<main>
        <div class="container" style="text-align:center">
        	<!--  Row 1 -->
            <div class="row">                
                <h2>Payment Form</h2> <br><br>
            </div><!-- end row -->
            
            <!--  Row 2 -->
            <div class="row">               	
               	<form action="PaymentServlet" method="post">
               		<input type="hidden" name="command" value="PAY" />
               		<label for="customerName"><strong>CardHolder Name</strong></label>
    				<input type="text" value="${sessionScope.loggedInUser.fullName}" name="customerName" required><br>
    				               		
                   	<label for="cardNumber"><strong>Card Number</strong></label>
    				<input type="text" placeholder="Enter Card Number" name="cardNumber" required><br>
    				
    				<label for="totalCost"><strong>Total Amount to be Paid</strong></label>
    				<input type="text" name="totalCost" value="${savedTicket.totalFare}" readonly /><br>
    				
    				<c:url var="tempLink" value="PaymentServlet">
					<c:param name="command" value="PAYMENT_SUCCESS" />
					</c:url>
					<a href="${tempLink}" class="button">Confirm Payment</a>    				                   	              	
                    <!--  <a href="PaymentServlet" type="submit">Confirm Payment</button> -->
                    <a href="javascript:history.back()">Back to Booking Details</a>
                </form>
            </div><!-- end row -->  
        </div><!--  end container2 -->              
    </main>
<%@ include file="footer.jsp"%>