package server;

import java.net.*;
import java.util.ArrayList;
import java.util.List;

import client.*;
import common.Message;
import common.MessageType;
import common.User;

import java.io.*;
/**
 * This class is Server Connect Client Thread, 
 * to build a thread between server and client
 * 
 */
//ServerConClientThread

public class NewServerThread extends Thread{
	Socket s;
	List<User> userTable;
	
	
    public NewServerThread(Socket s, List<User> userTable ) {
    	//to get the socket of thread
    	this.s=s;
    	this.userTable=userTable;
    }
    public void run() {
    	while(true) {
    		
    		try {
    			//this thread can receive message from client
    			ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
    			Message m = (Message) ear.readObject();
    			System.out.println("-----\nFrom:"+m.getSender()+"\nTo: "+m.getRecipient()+"\nMessage: "+m.getContain()+"\n-----");
    			
    			//handle different type of message

    			//server ------- text ---------------->recipient 
    			if(m.getMessageType().equals("3")) {
    			   //To get the thread of recipient
    			   NewServerThread FindRecipent = ManagerServerThread.getClientThread(m.getRecipient());
    			   ObjectOutputStream  mouth = new ObjectOutputStream(FindRecipent.s.getOutputStream());
    			   mouth.writeObject(m);
    			   System.out.println("transfer message successful" );
    			}
    			else if(m.getMessageType().equals(MessageType.message_get_onLineFriend)){
    				User client = m.getUser();//to use in database selectALL
    				UserDao database = new UserDao();
    				userTable = database.selectAll();//select except owner , user in list have id, name, state
    			    
    			    NewServerThread FindSender = ManagerServerThread.getClientThread(m.getSender());
    			    ObjectOutputStream  mouth = new ObjectOutputStream(FindSender.s.getOutputStream());
     			    mouth.writeObject(m);//send friendList back to sender
     			    System.out.println("return friendList back successful");
    			    
    			}
    				
    			
    				//List<User> FriendList = userTable;
//    				for(int i =0;i<ClientController.getUserTable().size();i++) {
//    				    User friend = ClientController.getUserTable().get(i);
//    				    if(friend.isState()==true) {
//    				    userTable.add(friend);
//    				    }
//    				}
    		    }
    			   	
//    			   String res=ManagerServerThread.getAllOnlineUserID();
//    			   Message m2 =new Message();
//    			   m2.setMessageType(MessageType.message_ret_onLineFriend);
//    			   m2.setContain(res);
//    			   m2.setRecipient(m.getSender());
//    			   ObjectOutputStream  mouth = new ObjectOutputStream(s.getOutputStream());
//    			   mouth.writeObject(m2);
//    			   
//    			}   
    	    catch (Exception e) {
    			   e.printStackTrace();
    	    }	   
        }
    }
    
}
