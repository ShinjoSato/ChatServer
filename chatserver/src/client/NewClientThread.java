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
	 
	 public HashMap<String, ChatWindow> chatRooms;
	 
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
	public NewClientThread(Socket s, HashMap<String, ChatWindow> chatRooms) {
	    	//To get the socket of thread.
	    	this.s=s;
	    	this.chatRooms = chatRooms;
	    }
	 public void run() {
		 while(true) {
			 try {
				ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
				
    			Message m = (Message) ear.readObject();
    			System.out.println(m.getSender()+" to "+m.getRecipient() + " say: "+ m.getContain());

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
    				   ChatWindow user1 = chatRooms.get(testUser.getUserID());
    				   System.out.println("line 68 "+ user1);
    				   
    				   user1.receiveMessage(m.getContain());
    				}
    			}
			 } catch (Exception e) {
				  e.printStackTrace();
			}
		 }
		 
	 }

}
