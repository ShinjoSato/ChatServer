package server;

import java.net.Socket;
import java.util.*;

import common.User;
/**
 * 
 * To manage Server connect Client thread
 *
 */


//this is ManageClientThread
public class ManagerServerThread {
	//public static HashMap ServerTable = new HashMap<String,NewServerThread>();
	// if on-line the email ID should be in the nap
	
	public static HashMap ServerTable = new HashMap<String, NewServerThread>();
	
	//add client thread in the table
	public static void addClientThread(String userID,NewServerThread thread) {
		ServerTable.put(userID, thread);
	}
	
	
	//tO get the thread 
	public static NewServerThread getClientThread(String userID) {
	    return (NewServerThread)ServerTable.get(userID);
	}
	
//	public static String getAllOnlineUserID() {
//		
//		Iterator it = ServerTable.keySet().iterator();
//		String res="";
//		while(it.hasNext()) {
//			res+=it.next().toString()+"";
//		}
//		return res;
//	}
	
	public static void main(String[] args) {
//		User onlineFriend0 = new User();
//		onlineFriend0.setUserID("01");
//		onlineFriend0.setUserName("Shinjo");
//		onlineFriend0.setState(true);
//		
//		User onlineFriend1 = new User();
//		onlineFriend1.setUserID("02");
//		onlineFriend1.setUserName("boris");
//		onlineFriend1.setState(true);
//
//		User onlineFriend2 = new User();
//		onlineFriend2.setUserID("03");
//		onlineFriend2.setUserName("Don");
//		onlineFriend2.setState(true);
//		
//		
//		
//		Socket s = new Socket();
//		NewServerThread a = new NewServerThread(s);
//		ServerTable.put(onlineFriend0,a);
//		ServerTable.put(onlineFriend1,a);
//		ServerTable.put(onlineFriend2,a);
//		
//		
////		ServerTable.get(onlineFriend).toString();
//		//System.out.println("test");
//		
////		for (Object key : ServerTable.keySet()) {
////			
////			   System.out.println("Key : " + key.toString());
////			  }
////		
//		ArrayList friendList = new ArrayList();
//		Iterator<User> it = ServerTable.keySet().iterator();
//		while(it.hasNext()) {
//			User element = (User) it.next();
//			friendList.add(element);
//            System.out.println("---element----" + element.toString());
//            
//            System.out.println("List: "+friendList.toString());
//            System.out.println("List: "+friendList.get(0));
//            User bbb = (User) friendList.get(0);
//            System.out.println(bbb.isState());
//		}
	}
	
}
