package client;

import java.util.*;
/**
 * 
 * To manage Client connect server thread
 *
 */

//this is ManageThread
public class ManagerClientThread {
	public static HashMap threadTable = new HashMap<String,NewClientThread>();
	// if on-line the email ID should be in the map
	
	
	//add client thread in the table
	public static void addServerThread(String userID,NewClientThread thread) {
		threadTable.put(userID, thread);
	}
	
	//90(50:49)
	public static NewClientThread getServerThread(String userID) {
	    return (NewClientThread)threadTable.get(userID);
	}
}
