<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">  --> 

	<main>
    	<div class="container" style="text-align:center">
        	
        	<!--  Row 1 -->
            <div class="row">
				<h2>User List</h2>
			</div><!-- end row -->
			
			<!--  Row 2 -->
            <div class="row"> 				
				<!-- new button for Add User -->
				<!-- Using JavaScript to redirect the browser to a new page -->
				<button type="submit"
					   class="add-user-button"	 
					   onClick="window.location.href='register_user_form.jsp'; return false;">
					   Add User
				</button>	
				<br><br>
			</div><!-- end row -->
		</div><!-- end container2 -->				
						
		<!-- table -->
		<table border="1" class="table table-striped">
			<thead>
				<tr>
					<th scope="col">Full Name</th>
					<th scope="col">User Name</th>
					<th scope="col">Role</th>
					<th scope="col">Email</th>	
					<th scope="col">Phone Number</th>	
					<th scope="col">Action</th>			
				</tr>
			</thead>
			
			<tbody>			
				<!-- As a best practice, use JSTL Tags (instead of JSP Scriptlet Code). Makes the code more readable. -->			
				<c:forEach var="tempUser" items="${USERS_LIST}">
				
					<!--  set up a link for each user using a JSTL feature called URL -->
					<!-- These 2 parameters are going to the User Controller Servlet -->
					<!-- We want to actually prepopulate the form on load. 
					     So when the user selects a user to update, we will prepopulate the form with that user's information from the database.  -->	
					<c:url var="tempLink" value="UserControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="userId" value="${tempUser.userid}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					</c:url>	
					
					<c:url var="deleteLink" value="UserControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="userId" value="${tempUser.userid}" /> <!-- Want to use the actual (embedded) userid, using JSP expression language -->
					</c:url>
				
					<tr>
						<td>${tempUser.fullName}</td><!-- Will actually call tempUser.getFullName() due to JSP expression language -->
						<td>${tempUser.userName}</td><!-- Will actually call tempUser.getUserName() due to JSP expression language -->
						<td>${tempUser.userRole}</td><!-- Will actually call tempUser.getRole() due to JSP expression language -->
						<td>${tempUser.userEmail}</td><!-- Will actually call tempUser.getEmail() due to JSP expression language -->
						<td>${tempUser.userContactNumber}</td>
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