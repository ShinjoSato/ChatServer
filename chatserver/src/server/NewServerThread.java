package server;

import java.net.*;

import client.*;
import common.Message;
import common.MessageType;

import java.io.*;
/**
 * This class is Server Connect Client Thread, 
 * to build a thread between server and client
 * 
 */
//ServerConClientThread

public class NewServerThread extends Thread{
	Socket s;
	
    public NewServerThread(Socket s) {
    	//to get the socket of thread
    	this.s=s;
    }
    public void run() {
    	while(true) {
    		
    		try {
    			ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
    			//this thread can receive message from client
    			//sender  ------- Hi ----------------> server
    			Message m = (Message) ear.readObject();
    		//	System.out.println(m.getSender()+" to "+m.getRecipient() + " say: "+ m.getContain());
    			System.out.println("-----\nFrom:"+m.getSender()+"\nTo: "+m.getRecipient()+"\nMessage: "+m.getContain()+"\n-----");
    			
    			//handle different type of message
    			//3 is message
    			//4 is Friend on-line state request
    			//5
    	//		if(m.getMessageType().equals(MessageType.message_comm_mes)) {
    			   //To get the thread of recipient
    			   //server ------- Hi ---------------->recipient 
    			   NewServerThread FindRecipent = ManagerServerThread.getClientThread(m.getRecipient());
    			   ObjectOutputStream  mouth = new ObjectOutputStream(FindRecipent.s.getOutputStream());
    			   mouth.writeObject(m);
//    			}else if(m.getMessageType().equals(MessageType.message_get_onLineFriend)){
//    			   String res=ManagerServerThread.getAllOnlineUserID();
//    			   Message m2 =new Message();
//    			   m2.setMessageType(MessageType.message_ret_onLineFriend);
//    			   m2.setContain(res);
//    			   m2.setRecipient(m.getSender());
//    			   ObjectOutputStream  mouth = new ObjectOutputStream(s.getOutputStream());
//    			   mouth.writeObject(m2);
//    			   
  //  			}   
    		}
    	    catch (Exception e) {
    			   e.printStackTrace();
    	    }	   
        }
    }
    
}
