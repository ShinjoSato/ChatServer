package client;

import java.util.List;

import common.*;

public class RuleBook {
	/**
	 * Users cannot open themselves.
	 */
	public static boolean canOpenChatWindow(User other, User client) {
		if(other.getUserID().contentEquals( client.getUserID() )) return false;
		return true;
	}
	
	public static User checkInUserTable(List<User> userTable, String userID, String password) {
    	User loginUser = new User();
    	for(int i=0; i<userTable.size(); i++) {
    		if(userTable.get(i).getUserID().equals(userID) && userTable.get(i).getPassword().equals(password)) {
    			loginUser = userTable.get(i);
    		}
    	}
    	return loginUser;
    }
}
