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
    			
            	User friend =ClientController.f;
    			ClientController.chatRooms.get(friend).receiveMessage(m.getContain());;

    			//    			if(m.getMessageType().equals(MessageType.message_comm_mes)) {
//    				
//    			}
//    			else if(m.getMessageType().equals(MessageType.message_ret_onLineFriend)) {
//    			     String contain = m.getContain();	
//    			     String friend[]=contain.split("");
//    			     String getter = m.getRecipient();
//    			     
//    			     //edit
//    			     
//    			     
   // 			}
			 } catch (Exception e) {
				  e.printStackTrace();
			}
		 }
		 
	 }

}
