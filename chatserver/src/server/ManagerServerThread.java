package server;

import java.util.*;
/**
 * 
 * To manage Server connect Client thread
 *
 */



//this is ManageClientThread
public class ManagerServerThread {
	public static HashMap ServerTable = new HashMap<String,NewServerThread>();
	// if on-line the email ID should be in the nap
	
	
	//add client thread in the table
	public static void addClientThread(String email,NewServerThread thread) {
		ServerTable.put(email, thread);
	}
	
	//90(50:49)
	public static NewServerThread getClientThread(String email) {
	    return (NewServerThread)ServerTable.get(email);
	}
	
	public static String getAllOnlineUserID() {
		
		Iterator it = ServerTable.keySet().iterator();
		String res="";
		while(it.hasNext()) {
			res+=it.next().toString()+"";
		}
		return res;
	}
	
	
	
}
