package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import common.Message;
import common.User;

public class ServerModel {
    
	public ServerModel(){
		try {
			System.out.println("Server Listening");
			//Listening at 50000
			ServerSocket ss=new ServerSocket(50000);
			//wait for accepting
			while(true) {
				Socket s=ss.accept();
				System.out.println("-----------connection built------------");
				
				//receive information from client
				ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
				//send information to server
				ObjectOutputStream mouth = new ObjectOutputStream(s.getOutputStream());
				
				User inputUser= (User) ear.readObject();
				System.out.println("server receive request from server ");
				System.out.println("Server is checking email and password....");
				Message feedback = new Message();
				
				
				//get data from database
//				UserDao a = new UserDao();
//				User dataUser = a.selectUserById(inputUser.getUserID());
				
				
				
	//------------------------------------need to get data from database------------------------
//				if(inputUser.getUserID().equals(dataUser.getUserID()) && 
//				   inputUser.getPassword().equals(dataUser.getPassword())) {
	//------------------------------------need to get data from database------------------------
				
				if(inputUser.getUserID().equals("yxc1016") && inputUser.getPassword().equals("12345")
				   || inputUser.getUserID().equals("01") && inputUser.getPassword().equals("12345")
				   || inputUser.getUserID().equals("03") && inputUser.getPassword().equals("12345")) {   
				   
					//---------------------------------------------------------------------------
					// update state to database
					//----------------------------------------------------------------------------
				   System.out.println("Server: login confirm.");
				   
				   // 1 means successful 
				   feedback.setMessageType("1");
				   
				   mouth.writeObject(feedback);
				   System.out.println("End");
				   
				   //build a thread between server and client, different client will input different socket
				   NewServerThread thread = new NewServerThread(s);
				   ManagerServerThread.addClientThread(inputUser.getUserID(), thread);
				   //start the thread
				   thread.start();
				}
				else {
				   // 2 means fail
				   feedback.setMessageType("2");	
				   System.out.println("login Fail");
				   mouth.writeObject(feedback);
				   //if fail,then close this connection into while loop start again
				   s.close();
				} 
				// send the result to client
			}	
		} 
		catch (Exception e) {
			   e.printStackTrace();
		}
		
		
		
	}
	public static void main(String[] args) {
		ServerModel s =new ServerModel();
	}
}
