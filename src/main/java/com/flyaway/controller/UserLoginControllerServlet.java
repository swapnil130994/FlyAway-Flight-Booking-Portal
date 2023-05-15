package com.flyaway.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.flyaway.jdbc.UserJDBC;
import com.flyaway.model.User;

/**
 * Servlet implementation class UserLoginControllerServlet
 */
@WebServlet("/UserLoginControllerServlet")
public class UserLoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserJDBC userJDBC;    
    private String username;
    private String useremail;
    private String userpassword;
    private User loggedInUser = null;
    private boolean isAdmin = false;
    
    @Resource(name="jdbc/flyaway") 
    private DataSource dataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginControllerServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		
		super.init();
		try {
			userJDBC = new UserJDBC(dataSource);
  		}
  		catch (Exception e) {
  			throw new ServletException(e); //in case there is an error reading from the database or other problem
  		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			HttpSession session = request.getSession();
			boolean usernameStatus = false;
			boolean passwordStatus = false;
			boolean emailStatus = false;			
			
			getFormData(request, response);
			usernameStatus = validateUsername(request,response);
			passwordStatus = validatePassword(request,response);
			System.out.println("usernameStatus = " + usernameStatus);
			System.out.println("passwordStatus = " + passwordStatus);
			
			if(usernameStatus && passwordStatus) {
			
				System.out.println("Adding the logged in user to the session object...");
				this.loggedInUser = authenticateUser(request, response);
				System.out.println("this.loggedInUser: \n" + this.loggedInUser.toString());
				session.setAttribute("loggedInUser", this.loggedInUser);				
				//session.setAttribute("loggedInUsername",this.username);
				emailStatus = validateEmail(request, response);
				System.out.println("emailStatus = " + emailStatus);
				this.isAdmin = checkUserRole(request, response);
				System.out.println("isAdmin = " + isAdmin);
				System.out.println("Adding the isAdmin user state to the session object...");
				session.setAttribute("isAdminUser", isAdmin);
			}
			
			//4. Send to index.jsp JSP page (view) using dispatcher
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);			
			
		}catch (Exception e) {			
			e.printStackTrace();
			throw new ServletException(e);
		} 
	}
	
	private void getFormData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Inside LoginControllerServlet getFormData()...");
		
		//1. Read info from login form and set respective class properties		
		this.username = request.getParameter("userName");
		this.useremail = request.getParameter("email");
		this.userpassword = request.getParameter("password");
		
		System.out.println("username from form: " + username);
		System.out.println("email from form: " + useremail);
		System.out.println("password from form: " + userpassword);
		
	}
	
	private boolean validateUsername(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Inside LoginControllerServlet validateUsername()...");
		boolean checkUsername=false;
		
		checkUsername = userJDBC.checkUsername(this.username);
		
		return checkUsername;
		
	}
	
	private boolean validatePassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Inside LoginControllerServlet validatePassword()...");
		boolean checkPassword = false;
		
		//lookup password for the user
		checkPassword = userJDBC.checkPassword(this.username, this.userpassword);
		
		return checkPassword;		
	}
	
	private boolean validateEmail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Inside LoginControllerServlet validatePassword()...");
		boolean checkEmail = false;
		
		//lookup email for the user
		checkEmail = userJDBC.checkEmail(this.username, this.userpassword, this.useremail);
		
		return checkEmail;		
	}
	
	private User authenticateUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User tempUser = new User();
		tempUser = userJDBC.getUser(this.username, this.userpassword);
		return tempUser;		
	}
	
	private boolean checkUserRole(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Inside LoginControllerServlet validatePassword()...");
		String userRole = "";
		boolean userIsAdmin = false;
		
		//lookup email for the user
		userRole = userJDBC.checkUserRole(this.username);
		
		if(userRole.equals("admin"))
			userIsAdmin = true;
		
		return userIsAdmin;		
	}
}
