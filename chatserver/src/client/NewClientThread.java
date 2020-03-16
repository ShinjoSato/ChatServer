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
import javafx.scene.layout.VBox;
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
    			if (m.getMessageType().equals(MessageType.message_comm_mes)) {
    			    for(int i =0;i<ClientController.getUserTable().size();i++) {
    				    User friend = ClientController.getUserTable().get(i);
    				    if(friend.getUserID().equals(m.getSender())) {
    				    ChatWindow friendWindow = chatRooms.get(friend.getUserID());
    				    friendWindow.receiveMessage(m.getContain());
    				   }
    		        }
    			}
    			else if(m.getMessageType().equals(MessageType.message_ret_onLineFriend)) {
        			//m.getFriendList();
        			//send List to GUI
    				ClientController.updateFriendList(/*ArrayList<User> here*/);
    			}
    			
    			
    			
    			
    			
    			
    			
			 } catch (Exception e) {
				  e.printStackTrace();
			}
		 }
		 
	 }
}
