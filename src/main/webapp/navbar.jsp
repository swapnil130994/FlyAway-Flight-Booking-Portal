<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Flyaway Portal</title>
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- <link rel="stylesheet" href="css/style.css">   -->
</head>
<body>
	
	<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="index.jsp"><img class="img-test split" src="images/flyaway.png" alt="Flyaway Logo"></a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="index.jsp">Home</a></li>
      <li><a href="FlightBookControllerServlet">Book Flight</a></li>
	  <c:if test="${sessionScope.loggedInUser==null}">
		  <li><a href="admin_login.jsp">Admin Login</a></li>
		  <li><a href="login.jsp">Customer Login</a></li>
		  <li><a href="register_user_form.jsp">Register New User</a></li>
	  </c:if>
	  
	  <c:if test="${sessionScope.isAdminUser}">
			<li><a href="UserControllerServlet">List Users</a></li>
			<li><a href="register_user_form.jsp">Register New User</a></li>
			<li><a href="SourceDestinationControllerServlet">List Source Destination</a></li>
			<li><a href="add_sourceDestination_form.jsp">Add New Source Destination</a></li>
	  </c:if>
	  
	  <c:if test="${sessionScope.loggedInUser!=null}">
			<c:url var="tempLink" value="UserControllerServlet">
				<c:param name="command" value="LOAD" />
				<c:param name="userId" value="${sessionScope.loggedInUser.userid}" />
			</c:url>
			
			<li><a href="${tempLink}">Update Profile</a></li>
			<c:url var="tempLink2" value="UserControllerServlet">
				<c:param name="command" value="HISTORY" />
				<c:param name="userId" value="${sessionScope.loggedInUser.userid}" /> 
			</c:url>
			<li><a href="${tempLink2}">Booking History</a></li>
			<li><a href="UserLogoutControllerServlet">Logout</a></li>
	  </c:if>
    </ul>
  </div>
</nav>
	
</body>
</html>