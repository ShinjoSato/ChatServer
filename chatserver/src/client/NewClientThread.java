/**
 * 
 */
package client;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.net.*;
import client.*;
import common.Message;
//import server.ManagerServerThread;
//import server.NewServerThread;
import common.MessageType;
import common.User;
import javafx.stage.Stage;

import java.io.*;

/**
 * @author Boris
 *
 */
public class NewClientThread extends Thread{
	 private Socket s;
	 
	 /**
	 * @return the s
	 */
	public Socket getS() {
		return s;
	}
	/**
	 * @param s the s to set
	 */
	public void setS(Socket s) {
		this.s = s;
	}
	public NewClientThread(Socket s) {
	    	//To get the socket of thread.
	    	this.s=s;
	    }
	 public void run() {
		 while(true) {
			 // keep receiving message from server
			 try {
				ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
				
    			//this thread can receive message from server
    			//server  ------- Hi ----------------> client
    			Message m = (Message) ear.readObject();
    			System.out.println(m.getSender()+" to "+m.getRecipient() + " say: "+ m.getContain());
    			// Sender 01 
    			// Recipent yxc1016
    			
    			/**
    			 * show the receive text on the chat window
    			 */
    			System.out.println("NewClientThread 59");
    			for(int i =0;i<ClientController.getUserTable().size();i++) {
    				if(ClientController.getUserTable().get(i).getUserID().equals(m.getSender())) {
    				   System.out.println("line 62 "+ClientController.getUserTable().get(i));
    				   System.out.println("line 63 "+ClientController.chatRooms.get(ClientController.getUserTable().get(i)));
    				   System.out.println("line 64 "+ClientController.getChatroom());
    				   User testUser =ClientController.getUserTable().get(i);
    				   System.out.println("line 66 "+ testUser);
    				   ChatWindow user1 = ClientController.getChatroom().get(testUser.getUserID());
    				   System.out.println("line 68 "+ user1);
    				   
    				   user1.receiveMessage(m.getContain());
    				}
    			}
    			
    		//	ClientController.getChatroom().get(m.getSender()).receiveMessage(m.getContain());
    			
//    			for (User a:ClientController.getUserTable()) {
//    				if(a.getUserID().equals(m.getSender())) {
//    				friend= a;
//    				
//    				System.out.println("NewClientThread 67 "+ friend);	
//    				System.out.println("NewClientThread 68 ");
//    				System.out.println(ClientController.test());
//    				System.out.println("NewClientThread 71 "+ ClientController.chatRooms);
//    				System.out.println("NewClientThread 72");
//    				ClientController.chatRooms.get(friend).test();
//    				ClientController.chatRooms.get(friend).receiveMessage("hey hey");
//    				System.out.println("NewClientThread 74");
 //   				}
//    			}
    			
    			
                
    			//    			if(m.getMessageType().equals(MessageType.message_comm_mes)) {
//    				
//    			}
//    			else if(m.getMessageType().equals(MessageType.message_ret_onLineFriend)) {
//    			     String contain = m.getContain();	
//    			     String friend[]=contain.split("");
//    			     String getter = m.getRecipient();
//    			     
//    			     //edit
   // 			}
			 } catch (Exception e) {
				  e.printStackTrace();
			}
		 }
		 
	 }

}
