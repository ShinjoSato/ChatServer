package common;

import java.io.Serializable;

public class User implements Serializable{
	private String userID;
	private String UserName;
	private String password;
	private String email;
	private boolean state;
	
	public User (String userID, String userName, String password, String email, boolean state) {
		this.userID = userID;
		this.UserName = userName;
		this.password = password;
		this.email = email;
		this.state = state;
	}
	
	public User () {
		
	}
	
	/**
	 * @return the userId
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userID = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "User [userID=" + userID + ", UserName=" + UserName + ", password=" + password + ", email=" + email
				+ ", state=" + state + "]";
	} 
   	
	

}
