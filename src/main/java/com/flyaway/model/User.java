package com.flyaway.model;

import java.io.Serializable;

public class User implements Serializable {
	
	private int userid;
	private String fullName;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String userContactNumber;
	private int userRoleId;
	private String userRole;
	
	
	
	/**
	 * 
	 */
	public User() {
		
	}



	/**
	 * @param userid
	 * @param fullName
	 * @param userName
	 * @param userEmail
	 * @param userPassword
	 * @param userContactNumber
	 * @param userRoleId
	 * @param userRole
	 */
	public User(int userid, String fullName, String userName, String userEmail, String userPassword,
			String userContactNumber, int userRoleId, String userRole) {
		super();
		this.userid = userid;
		this.fullName = fullName;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userContactNumber = userContactNumber;
		this.userRoleId = userRoleId;
		this.userRole = userRole;
	}
	
	
	
	/**
	 * @param fullName
	 * @param userName
	 * @param userEmail
	 * @param userPassword
	 * @param userContactNumber
	 * @param userRole
	 */
	public User(String fullName, String userName, String userEmail, String userPassword, String userContactNumber,
			String userRole) {
		
		this.fullName = fullName;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userContactNumber = userContactNumber;
		this.userRole = userRole;
		this.setUserRoleId(userRole);
	}



	/**
	 * @param userid
	 * @param fullName
	 * @param userName
	 * @param userEmail
	 * @param userPassword
	 * @param userContactNumber
	 */
	public User(int userid, String fullName, String userName, String userEmail, String userPassword,
			String userContactNumber) {
		super();
		this.userid = userid;
		this.fullName = fullName;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userContactNumber = userContactNumber;
	}



	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserContactNumber() {
		return userContactNumber;
	}
	public void setUserContactNumber(String userContactNumber) {
		this.userContactNumber = userContactNumber;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public int getUserRoleId() {
		return userRoleId;
	}
	
	public void setUserRoleId() {
		
		switch(this.userRole) {
		case "Customer":
			this.userRoleId = 2;
			break;
			
		case "Admin":
			this.userRoleId = 1;
			break;
			
		default:
			this.userRoleId = 3; //guest
			break;		
		}//end switch
		
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	public void setUserRoleId(String userRole) {
		switch(userRole) {
		case "Customer":
			this.userRoleId = 2;
			break;
			
		case "Admin":
			this.userRoleId = 1;
			break;
			
		default:
			this.userRoleId = 3; //guest
			break;		
		}//end switch
		
	}
	
	@Override
	public String toString() {
		return "User [userid=" + userid + ", fullName=" + fullName + ", userName=" + userName + ", userEmail="
				+ userEmail + ", userPassword=" + userPassword + ", userContactNumber=" + userContactNumber
				+ ", userRoleId=" + userRoleId + ", userRole=" + userRole + "]";
	}
	
	
}
