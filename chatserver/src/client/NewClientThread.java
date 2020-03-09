/**
 * 
 */
package client;

import java.net.Socket;
import java.net.*;
import client.*;
import common.Message;
import server.ManagerServerThread;
import server.NewServerThread;

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
	    	//讓線程拿到s
	    	this.s=s;
	    }
	 public void run() {
		 while(true) {
			 // keep receiving message from server
			 try {
				ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
	  			//此線程可接收server端的訊息
    			//this thread can receive message from server
    			//server  ------- Hi ----------------> client
    			Message m = (Message) ear.readObject();
    			System.out.println(m.getSender()+" to "+m.getRecipient() + " say: "+ m.getContain());

			 } catch (Exception e) {
				  e.printStackTrace();
			}
			 
			 
			 
		 }
		 
	 }

}
