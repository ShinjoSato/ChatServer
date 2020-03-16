package common;


import java.io.Serializable;
import java.util.List;

import client.ClientController;

public class User implements Serializable{
	private String userID;
	private String userName;
	private String password;
	private String email;
	private boolean state;
	private List<User> friendList;

	public User (String userID, String userName, String password, String email, boolean state) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.state = state;
	}
	
	public User () {
	}
	
	//this is from friend list
	public User (String userID, String userName, boolean state) {
		this.userID = userID;
		this.userName = userName;
		this.state = state;
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
	public void setUserID(String userID) {
		this.userID = userID;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
		return "User [userID=" + userID + ", userName=" + userName + ", state=" + state + "]";
	}
	
	/**
	 * @return the friendList
	 */
	public List<User> getFriendList() {
		return friendList;
	}
	/**
	 * @param friendList the friendList to set
	 */
	public void setFriendList(List<User> friendList) {
		this.friendList = friendList;
	}
	
}
