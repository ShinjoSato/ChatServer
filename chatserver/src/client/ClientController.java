package client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientController extends Application {
	private static Stage primaryStage;
	private User ClientUser;
	private static List<User> UserTable;
	protected static HashMap<String, ChatWindow> chatRooms;
	private final static int WindowHeight = 600;
	static List<Integer> Transfer;	
//	public static ChatWindow c; 
	private final String address = "172.22.108.237";
    
	@Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        chatRooms = new HashMap<String, ChatWindow>();
        makeScene("fxml/login.fxml", "Login");
    }
	
	public static Scene createNewScene(URL location, int width, int height) {
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Scene scene2 = null;
		try {
			scene2 = new Scene((Pane)fxmlLoader.load(), width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scene2;
	}

	public void makeScene(String fxmlfile, String title) throws Exception {
		this.primaryStage.close();
        makeScene(fxmlfile, title, 450, 600);
    }

    public void makeScene(String fxmlfile, String title, int width, int height) throws Exception {
        URL location = getClass().getResource(fxmlfile);
        Scene scene = createNewScene(location, width, height);
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle(title);
        this.primaryStage.show();
    }

    /**
     * @param username      	User inputs it on "login.fxml", "newaccount.fxml" and "forgetting.fxml".
     * @param password      	User inputs it on "login.fxml" and "newaccount.fxml".
     * @param email         	User inputs it on "newaccount.fxml" and "forgetting.fxml".
     * @param message       	User inputs it on "chat.fxml".
     * @param keyword       	User inputs it on "chat.fxml".
     * @param talkHistory   	User inputs it on "chat.fxml".
     * @param friendListview	User selects a friend on "friendlist.fxml".
     */
    @FXML
    public TextField username, password, email, keyword, userID;
    public TextArea message;
    public VBox talkHistory;
    
    @FXML
    public Label UserName;
    
    @FXML
    public VBox friendListBox;
    
    /**
     * --------------------------------
     * 
     * Login Window.
     * 
     * --------------------------------
     */
    
    @FXML
    protected void signIn(ActionEvent event) throws Exception{        
        // Finally we use this one: input email and password.
        User ClientUser = sendLogInToServer(userID.getText(), password.getText());
        System.out.println("this is signin "+ClientUser.getFriendList().toString());
        
        if(ClientUser.getUserID() == null){
        	Stage newStage = new Stage();
    		newStage.initModality(Modality.NONE);
    		newStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("fxml/loginfail.fxml")), 450, 300 ) );
    		newStage.setTitle(ClientUser.getUserID());
    		newStage.show();
        }else{
        	UserTable=ClientUser.getFriendList();
        	//send friend list request to server
//        	ObjectOutputStream mouth = new ObjectOutputStream(getServerThread(ClientUser.getUserID()).getS().getOutputStream());
//        	Message friendListRequest = new Message();
//        	friendListRequest.setMessageType(MessageType.message_get_onLineFriend);
//        	friendListRequest.setSender(ClientUser.getUserID());
//        	mouth.writeObject(friendListRequest);
        	
        	this.primaryStage.close();
        	createFriendListWindow(ClientUser);
        }
    }
    
    Socket s;
    
	public User sendLogInToServer(String ID, String password) {
		try {
			s = new Socket(address,50000);
			//Send login request to server
			ObjectOutputStream mouth = new ObjectOutputStream(s.getOutputStream());
			User loginUser = new User();
			loginUser.setUserID(ID);
			loginUser.setPassword(password);
			Message loginRequest = new Message();
			loginRequest.setMessageType("1"); 
			loginRequest.setUser(loginUser);
			mouth.writeObject(loginRequest);

			//receive result from server
			ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
			Message resultFromServer = (Message) ear.readObject();
			//verify login
			if (resultFromServer.getMessageType().equals("1")) {
				NewClientThread thread = new NewClientThread(s, chatRooms);
				addServerThread(loginUser.getUserID(), thread);
				thread.start();
				;
	            //loginUser.setFriendList(resultFromServer.getUser().getFriendList());
				//return loginUser;
				System.out.println("This client I get friend List"+resultFromServer.getUser().getFriendList().toString());
				return resultFromServer.getUser();
			}
		}
		catch (Exception e) {
			   e.printStackTrace();
		}
		return new User();
    }
	
	public  User register(String email, String registerID , String password) {
		try {
		s = new Socket(address,50000);
		//Send register request to server
		ObjectOutputStream mouth = new ObjectOutputStream(s.getOutputStream());
		User registerUser = new User();
		registerUser.setEmail(email);
		registerUser.setUserID(registerID);
		registerUser.setPassword(password);
		Message registerRequest = new Message();
		registerRequest.setMessageType(MessageType.message_register); 
		registerRequest.setUser(registerUser);
		mouth.writeObject(registerRequest);

		//receive result from server
		ObjectInputStream ear = new ObjectInputStream(s.getInputStream());
		Message resultFromServer = (Message) ear.readObject();
		//verify login
		if (resultFromServer.getMessageType().equals("0")) {
			NewClientThread thread = new NewClientThread(s, chatRooms);
			addServerThread(registerUser.getUserID(), thread);
			thread.start();
			return registerUser;
		}
	}
	catch (Exception e) {
		   e.printStackTrace();
	}
	return new User();
}
	
	
	
	
	public static HashMap threadTable = new HashMap<String ,NewClientThread>();
	// if on-line the email ID should be in the map
	
	
	//add client thread in the table
	public static void addServerThread(String userID,NewClientThread thread) {
		threadTable.put(userID, thread);
	}
	
	//90(50:49)
	public static NewClientThread getServerThread(String userID) {
	    return (NewClientThread)threadTable.get(userID);
	}
    
	private static boolean isTest = true;
    public static List<User> getUserTable(){
    	List<User> userTable = new ArrayList<User>();
    	//userTable =UserTable;
    	if(isTest) {
    		userTable.add(new User("01", "Shinjo Shinjo",false));
            userTable.add(new User("yxc1016", "Yi-Ming Chen", false));
            userTable.add(new User("03", "Zhengnan Sun", false));
            userTable.add(new User("04", "Saba Akhlagh-Nejat", false));
            userTable.add(new User("05", "Ibiyemi Ogunyemi", false));
            userTable.add(new User("06", "root", "1", "mail", false));
            isTest = false;
    	}else {
    		userTable.add(new User("01", "Shinjo Shinjo",false));
            userTable.add(new User("yxc1016", "Yi-Ming Chen", true));
            userTable.add(new User("03", "Zhengnan Sun", false));
            userTable.add(new User("04", "Saba Akhlagh-Nejat", true));
            userTable.add(new User("05", "Ibiyemi Ogunyemi", false));
            userTable.add(new User("06", "root", "1", "mail", false));
            isTest = true;
    	}        
        return userTable;
    }

    @FXML
    protected void moveToNewaccount(ActionEvent event) throws Exception{
        makeScene("fxml/NewAccount.fxml", "create account");
    }

    @FXML
    protected void forgetPassword(ActionEvent event) throws Exception{
        makeScene("fxml/forgetting.fxml", "forget your password?");
    }

    /**
     * ----------------------------------------
     * 
     * NewAccount and ForgettingAccount Window.
     * 
     * ----------------------------------------
     */
    @FXML
    protected void createAccount(ActionEvent event) {
    }

    @FXML
    protected void backToLogin(ActionEvent event) throws Exception {
    	makeScene("fxml/login.fxml", "Login");
    }

    
    @FXML
    protected void sendPassword(ActionEvent event) throws Exception {
    }
    
    /**
     * ---------------------------------------
     * 
     * FriendList Window
     * 
     * ---------------------------------------
     */
    private void createFriendListWindow(User ClientUser) throws Exception {
    	this.primaryStage = createFriendListStage(ClientUser);
    	this.primaryStage.setTitle("user id: "+ClientUser.getUserID());
    	this.primaryStage.show();
    }
    
    public Stage createFriendListStage(User ClientUser) {
    	Group group = new Group();
    	HBox allcontent = new HBox();
    	allcontent.setPrefWidth(450);
    	allcontent.setPrefHeight(WindowHeight);
    	
    	VBox leftBox = new VBox();    	
    	leftBox.setPrefWidth(60);
    	leftBox.setPrefHeight(WindowHeight);
    	leftBox.getStyleClass().add("leftBox");
    	Button addingFriend = new Button();
    	addingFriend.setPrefWidth(60);
    	addingFriend.setPrefHeight(60);
    	addingFriend.getStyleClass().add("addingFriend");
    	
    	addingFriend.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("click addingFriend button.");
				updateFriendList();
			}
    	});
    	leftBox.getChildren().add(addingFriend);
    	
    	VBox mainBox = new VBox();
    	mainBox.setPrefWidth(390);
    	mainBox.setPrefHeight(WindowHeight);
    	//VBox friendListBox = new VBox();
    	friendListBox = new VBox();
    	friendListBox.setId("friendListBox");
    	friendListBox.getChildren().add(createFriendListView(getUserTable(), ClientUser));
    	
    	mainBox.getChildren().add(friendListBox);
    	
    	allcontent.getChildren().add(leftBox);
    	allcontent.getChildren().add(mainBox);
    	
    	group.getChildren().add(allcontent);

        Scene scene2 = new Scene(group, 450, WindowHeight);
        scene2.getStylesheets().add(
        	getClass().getResource("./css/friendlist.css").toExternalForm()
        );
        Stage stage = new Stage();
        stage.setTitle(ClientUser.getUserName());
        stage.setScene(scene2);
        return stage;
    }

    public void updateFriendList() {
    	ListView<HBox> friendlist = createFriendListView(getUserTable(), ClientUser);
    	if(friendListBox == null) {
    		friendListBox = new VBox();
    	}
    	friendListBox.getChildren().clear(); 
    	friendListBox.getChildren().add(friendlist);
    }
    
    public void updateFriendList(ArrayList<User> friends) {
    	ListView<HBox> friendlist = createFriendListView(friends, ClientUser);
    	if(friendListBox == null) {
    		friendListBox = new VBox();
    	}
    	friendListBox.getChildren().clear(); 
    	friendListBox.getChildren().add(friendlist);
    }
    
	public static ListView<HBox> createFriendListView(List<User> friends, User ClientUser) {
    	ObservableList<HBox> tests = FXCollections.observableArrayList();
    	//friends = ClientUser.getFriendList();
    	for(int i=0; i<friends.size(); i++) {
    		Label friendTag = new Label(friends.get(i).getUserName());
			friendTag.getStyleClass().add("friendTag");
			//Label statusTag = new Label("●");
    		Circle statusCircle = new Circle(8.0);
    		/*if(friends.get(i).isState()) statusTag.getStyleClass().add("statusT");
    		else 						 statusTag.getStyleClass().add("statusF");*/
    		if(friends.get(i).isState()) statusCircle.getStyleClass().add("statusT");
    		else 						 statusCircle.getStyleClass().add("statusF");
    		HBox friendrow = new HBox(statusCircle, friendTag);
    		
    		tests.add(friendrow);
    	}
    	ListView<HBox> list = new ListView<>(tests);
    	list.setPrefHeight(WindowHeight);
    	
    	list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {            	
            	List<Integer> friendIndex = list.getSelectionModel().getSelectedIndices();
            	User friend = friends.get(friendIndex.get(0)); 
            	if(chatRooms.get(friend.getUserID()) == null) {
            		ChatWindow chatwindow = new ChatWindow(ClientUser,friend);
            		chatRooms.put(friend.getUserID(), chatwindow);
                    Stage stage2 = chatwindow.getStage();
                    stage2.show();
                    
                    /*
                     * ---------------------------------------------------
                     * closing the chat window.
                     * ---------------------------------------------------
                     */
                    stage2.showingProperty().addListener((observable, oldValue, newValue) -> {
                        if (oldValue == true && newValue == false) {
                        	chatRooms.remove(friend.getUserID());
                        }
                    });
            	}else {
            		System.out.println("The chatroom already exist.");
            		//chatRooms.get(friend.getUserID()).receiveMessage("hey Boy");
            	}
            }
        });
		return list;
	}
	
	public static HashMap<String, ChatWindow> getChatroom() {
		return chatRooms; 
	}
	
	public static void main(String[] args) {
	 	launch(args);
	}
}
