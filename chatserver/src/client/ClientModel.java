package client;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.*;

public class ClientModel {
	public static Socket s;
	
	public static boolean sendLogInToServer(String userID, String userPassword) {
		boolean canLogin = false;
		try {
			s = new Socket("192.168.0.74",50000);
			
			//Send login request to server
			ObjectOutputStream mouth = new ObjectOutputStream(s.getOutputStream());
			User a = new User();
			//GUI
			a.setUserId(userID);
			a.setPassword(userPassword);
			//GUI
			mouth.writeObject(a);
			System.out.println("client send login to server");

			//receive result from server
			ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
			Message resultFromServer = (Message) ear.readObject();
			if (resultFromServer.getMessageType().equals("1")) {
				System.out.println("receive 1 from server into FriendList");
			    canLogin= true;				
			}
		}
		catch (Exception e) {
			   e.printStackTrace();
		}
		return canLogin;
    }
	
	//draft
	public void sendMesToServer(String UserID, String text) {
		try {
			//Send message to server
			ObjectOutputStream mouth = new ObjectOutputStream(s.getOutputStream());
			Message word = new Message();
			word.setSender(UserID);
			word.setContain(text);
			word.setRecipient("Friend");
			mouth.writeObject(word);
			System.out.println("client send message to server");

			//receive result from server
			ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
			
		}
		catch (Exception e) {
			   e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		ClientModel ccs = new ClientModel();
		ccs.sendLogInToServer("yxc1016", "123456");
	}
}

