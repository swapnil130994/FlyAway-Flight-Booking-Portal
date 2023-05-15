<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

	<div id="wrapper">
		<div id="header">
		<h2>Update User</h2>
		</div><!-- end header -->
	</div><!-- end wrapper -->
	
	<div class="container" style="text-align:center">
		<div id="content">
		
		<form action="UserControllerServlet" method="GET">
		
				<!-- Hidden Fields -->
				<input type="hidden" name="command" value="UPDATE" />
				<input type="hidden" name="userId" value="${THE_USER.userid}" />
				
				<table>
				<tbody>
						<tr>
							<td><label>First Name: </label></td>
							<td><input type="text" name="fullName" value="${THE_USER.fullName}"/></td>
						</tr>
						<tr>
							<td><label>Username: </label></td>
							<td><input type="text" name="username" value="${THE_USER.userName}"></td>
						</tr>
						<tr>
							<td><label>Email: </label></td>
							<td><input type="text" name="email" value="${THE_USER.userEmail}"></td>
						</tr>
						<tr>
							<td><label>Password: </label></td>
							<td><input type="password" name="pwd" value="${THE_USER.userPassword}"></td>						
							<!-- <a href="${tempLink}">Change Password</a> -->
						</tr>
						<tr>
							<td><label>Phone Number: </label></td>
							<td><input type="text" name="phoneNumber" value="${THE_USER.userContactNumber}"></td>
						</tr>						
						<tr>
							<td><label></label></td>						
							<td><input class="save" type="submit" value="Save"></td>
						</tr>							
					</tbody>
				</table>
		</form>
		<c:if test="${sessionScope.isAdminUser}">
			<div style="clear: both;"></div><!-- end clear -->
				<p>
					<a href="UserControllerServlet">Back to User List</a>
				</p>
		</c:if>
		</div><!-- end content -->
	</div><!-- end container -->
<%@ include file="footer.jsp"%>