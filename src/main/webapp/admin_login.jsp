<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %> 

        <div class="container" style="text-align:center">
        	<!--  Row 1 -->
            <div class="row">                
                <h2>Admin Login</h2> <br><br>
            </div><!-- end row -->
            
            <!--  Row 2 -->
            <div class="row">               	
               	<form action="UserLoginControllerServlet" method="post">
               		<label for="userName"><strong>User Name</strong></label>
    				<input type="text" placeholder="Enter Username" name="userName" required><br>
    				               		
                   	<label for="password"><strong>Password</strong></label>
    				<input type="password" placeholder="Enter Password" name="password" required><br>
    				
    				<label for="email"><strong>Email</strong></label>
    				<input type="text" placeholder="Enter Email Address" name="email"><br>
    				                   	              	
                    <button type="submit">Login</button>
                </form>
            </div><!-- end row -->  
        </div><!--  end container2 -->              
<%@ include file="footer.jsp" %>