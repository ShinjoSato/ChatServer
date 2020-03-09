package server;

import java.net.*;

import client.*;
import common.Message;
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
    	//���u�{����s
    	this.s=s;
    }
    public void run() {
    	while(true) {
    		
    		try {
    			ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
    			//���u�{�i�����Ȥ�ݪ��T��
    			//this thread can receive message from client
    			//sender  ------- Hi ----------------> server
    			Message m = (Message) ear.readObject();
    			System.out.println(m.getSender()+" to "+m.getRecipient() + " say: "+ m.getContain());
    			System.out.println("-----\nFrom:"+m.getSender()+"\nTo: "+m.getRecipient()+"\nMessage: "+m.getContain()+"\n-----");
    			//���o�����H���u�{
    			//server ------- Hi ---------------->recipient 
    			NewServerThread FindRecipent = ManagerServerThread.getClientThread(m.getRecipient());
    			ObjectOutputStream  mouth = new ObjectOutputStream(FindRecipent.s.getOutputStream());
    			mouth.writeObject(m);
    			
    		}
    	    catch (Exception e) {
    			   e.printStackTrace();
    	    }	   
        }
    }
    
}
