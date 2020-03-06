package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

	public MyServer(){
		try {
			System.out.println("Server Listening ");
			//Listening at 50000
			ServerSocket ss=new ServerSocket(50000);
			//wait for accepting
			Socket s=ss.accept();
			//receive information from client
			System.out.println("Connection successful");
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			User a= (User) ois.readObject();
				
			System.out.println("Server get User informtation from client");
			if(a.getUserId().equals("yxc1016") && a.getPasswd().equals("123456")) {
			   System.out.println("LogIn successful"); 					
			}
			else {
				  System.out.println("LogIn fail");
			} 
			System.out.println("ID is: "+a.getPasswd());
			System.out.println("Password is: "+a.getUserId());
			s.close();
				
		} 
		catch (Exception e) {
			   e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		MyServer s =new MyServer();
	}
}
