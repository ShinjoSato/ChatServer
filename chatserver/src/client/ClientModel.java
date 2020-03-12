package client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import common.Message;
import common.User;
import javafx.scene.Scene;

public class ClientModel {
	public Socket s;
	
	public  User sendLogInToServer(String ID, String password) {
		try {
			s = new Socket("172.22.134.200",50000);
			
			//Send login request to server
			ObjectOutputStream mouth = new ObjectOutputStream(s.getOutputStream());
			User a = new User();
			a.setUserID(ID);
			a.setPassword(password);
			mouth.writeObject(a);
			System.out.println("client: send login request to server");

			//receive result from server
			ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
			Message resultFromServer = (Message) ear.readObject();
			//verify login
			if (resultFromServer.getMessageType().equals("1")) {
				a.setState(true);
				System.out.println("receive 1 from server, login successful");
				NewClientThread thread = new NewClientThread(s);
				ManagerClientThread.addServerThread(a.getUserID(), thread);
				thread.start();
				System.out.println("client build the receive thread");
				//
				return a;
			}
		}
		catch (Exception e) {
			   e.printStackTrace();
		}
		return new User();
    }
	
	public static void main(String[] args) {
//		System.out.println("This is ClientModel.java");
//		ClientModel client = new ClientModel();
//		client.sendLogInToServer("yxc1016", "12345");
//		Message word = new Message();
//		Scanner input =new Scanner(System.in);
//		String a = null;
//		word.setSender("Boris");
//		word.setRecipient("Shinjo");
//		System.out.println("input message¡G");
//		while(input.hasNext()) {
//		    try {
//		    	ObjectOutputStream mouth = new ObjectOutputStream(client.s.getOutputStream());
//				a =input.next();
//				word.setContain(a);
//				mouth.writeObject(word);
//		    } catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		    }
//        }
	}
}

