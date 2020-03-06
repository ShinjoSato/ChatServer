package client;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConServer {
	
	public static boolean sendLogInToServer(String username, String userpassword) {
		boolean canLogin = false;
		try {
			Socket s=new Socket("172.22.134.200",50000);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			User a = new User();
			a.setUserId("yxc1016");
			a.setPasswd("123456");
			oos.writeObject(a);
			System.out.println("send object to server");

			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			System.out.println(ois);

			s.close();


			
		}
		catch (Exception e) {
			   e.printStackTrace();
		}
		return canLogin;
    }
	
	public static void main(String[] args) {
		ClientConServer ccs = new ClientConServer();
	}
}

