package client;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientController extends Application{
	private static Stage primaryStage;
	private static User ClientUser;
	private List<User> UserTable;
	protected static HashMap<User, ChatWindow> chatRooms;
	private final static int WindowHeight = 600;

	@Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        chatRooms = new HashMap<User, ChatWindow>();
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
    //public ListView<String> friendListView;
    
    @FXML
    public Label UserName;
    
    @FXML
    public VBox friendListBox;
    
    /**
     * This function is used on "login.fxml".
     */
    @FXML
    protected void signIn(ActionEvent event) throws Exception{
        System.out.println("signIn: "+userID.getText()+", "+password.getText());

        System.out.println(this.UserTable);
        
        // Finally we use this one: input email and passeword.
        
        ClientModel a = new ClientModel();
        User b = a.sendLogInToServer(userID.getText(), password.getText());
        ClientUser = b;
        //ClientUser = RuleBook.checkInUserTable(getUserTable(), userID.getText(), password.getText());
        System.out.println(ClientUser);
        
        if(ClientUser.getUserID() == null){
        	Stage newStage = new Stage();
    		newStage.initModality(Modality.NONE);
    		newStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("fxml/loginfail.fxml")), 450, 300 ) );
    		newStage.show();
        }else{
        	//send friend online state request  to server
//        	ObjectOutputStream mouth = new ObjectOutputStream(
//        	(ManagerClientThread.getServerThread(ClientUser.getUserID()).getS()).getOutputStream());
//        	Message m = new Message();
//        	m.setMessageType(MessageType.message_get_onLineFriend);
//        	//ask cilent's friend List
//        	m.setSender(ClientUser.getUserID());
//        	mouth.writeObject(m);
        	
        	this.primaryStage.close();
        	createFriendListWindow();
        }
    }
    
    public static List<User> getUserTable(){
    	List<User> userTable = new ArrayList<User>();
        userTable.add(new User("01", "Shinjo Shinjo", "12345", "sxs1640@student.bham.ac.uk", true));
        userTable.add(new User("yxc1016", "Yi-Ming Chen", "12345", "yxc1016@student.bham.ac.uk", true));
        userTable.add(new User("03", "Zhengnan Sun", "12345", "zxs944@student.bham.ac.uk", false));
        userTable.add(new User("04", "Saba Akhlagh-Nejat", "12345", "sxa1012@student.bham.ac.uk", true));
        userTable.add(new User("05", "Ibiyemi Ogunyemi", "12345", "ixo984@student.bham.ac.uk", false));
        userTable.add(new User("06", "root", "1", "mail", false));
        
        return userTable;
    }

    /**
     * This function is used on "login.fxml".
     */
    @FXML
    protected void moveToNewaccount(ActionEvent event) throws Exception{
        System.out.println("create new account");
        makeScene("fxml/NewAccount.fxml", "create account");
    }

    /**
     * This function is used on "login.fxml".
     */
    @FXML
    protected void forgetPassword(ActionEvent event) throws Exception{
        System.out.println("forget your password?");
        makeScene("fxml/forgetting.fxml", "forget your password?");
    }
    
    /**
     * This function is used on "newaccount.fxml".
     */
    @FXML
    protected void createAccount(ActionEvent event) {
        System.out.println(email.getText()+", "+username.getText()+", "+password.getText());
    }

    /**
     * This function is used on "newaccount.fxml" and "forgetting.fxml".
     */
    @FXML
    protected void backToLogin(ActionEvent event) throws Exception {
        makeScene("fxml/login.fxml", "Login");
    }

    /**
     * This function is used on "forgetting.fxml".
     */
    @FXML
    protected void sendPassword(ActionEvent event) throws Exception {
    }
    
    /**
     * -------------------------------------
     * 
     * These are functions about FriendList.
     * 
     * -------------------------------------
     */
    
    
    @FXML
    private VBox friendList;
    private void createFriendListWindow() throws Exception {
    	this.primaryStage = createFriendListStage();
    	this.primaryStage.show();
    }
    
//	@Override
//	public void run() {
//		while(true) {
//		try {
//		    Thread.sleep(1000);
////			ListView<HBox> friendlist = GUIFunction.createFriendListView(getUserTable());
////			friendListBox.getChildren().remove(0);
////			friendListBox.getChildren().add(friendlist);
////		    renewFriendList();
//			times+=1;
//			System.out.println("running");
//			System.out.println(times);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		}
//	}
    
    public Stage createFriendListStage() {
    	Group group = new Group();
    	HBox allcontent = new HBox();
    	allcontent.setPrefWidth(450);
    	allcontent.setPrefHeight(WindowHeight);
    	
    	VBox leftBox = new VBox();    	
    	leftBox.setPrefWidth(60);
    	leftBox.setPrefHeight(WindowHeight);
    	leftBox.setStyle("-fx-background-color: #00355e;");
    	Button addingFriend = new Button();
    	addingFriend.setPrefWidth(60);
    	addingFriend.setPrefHeight(60);
    	addingFriend.setStyle("-fx-background-image: url(\"./client/images/addFriendImg_60x60.png\"); -fx-background-color: #00355e;");
    	addingFriend.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("click addingFriend button.");
			}
    	});
    	leftBox.getChildren().add(addingFriend);
    	
    	VBox mainBox = new VBox();
    	mainBox.setPrefWidth(390);
    	mainBox.setPrefHeight(WindowHeight);
    	VBox friendListBox = new VBox();
    	friendListBox.setId("friendListBox");
    	friendListBox.getChildren().add(createFriendListView(getUserTable()));
    	
    	mainBox.getChildren().add(friendListBox);
    	
    	allcontent.getChildren().add(leftBox);
    	allcontent.getChildren().add(mainBox);
    	
    	group.getChildren().add(allcontent);

        Scene scene2 = new Scene(group, 450, WindowHeight);
        Stage stage = new Stage();
        stage.setTitle(ClientUser.getUserName());
        stage.setScene(scene2);
        return stage;
    }

    public void updateFriendList() {
    	ListView<HBox> friendlist = createFriendListView(getUserTable());
    	//friendListBox.getChildren().remove(0);
    	
    	System.out.println("This is friendlistbox: "+friendListBox);
    	if(friendListBox == null) {
    		friendListBox = new VBox();
    	}
    	friendListBox.getChildren().add(friendlist);
    }
    
	public static ListView<HBox> createFriendListView(List<User> friends) {
    	ObservableList<HBox> tests = FXCollections.observableArrayList();
    	for(int i=0; i<friends.size(); i++) {
    		Label friendTag = new Label(friends.get(i).getUserName());
			friendTag.setStyle("-fx-font-size: 20pt; -fx-font-family: 'Courier';");
    		Label statusTag = new Label("●");
    		if(friends.get(i).isState()) statusTag.setStyle("-fx-font-size: 20pt; -fx-text-fill: green;");
    		else statusTag.setStyle("-fx-font-size: 20pt; -fx-text-fill: red;");
    		HBox friendrow = new HBox(statusTag, friendTag);
    		
    		tests.add(friendrow);
    	}
    	ListView<HBox> list = new ListView<>(tests);
    	list.setPrefHeight(WindowHeight);
    	
    	// click friend on the list
    	list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {            	
            	List<Integer> friendIndex = list.getSelectionModel().getSelectedIndices();
            	User friend = friends.get( friendIndex.get(0) );
            	
            	
            	//if(chatRooms.get(friend) == null) {
            		System.out.println("create chatwindow");	
            		
            		ChatWindow chatwindow = new ChatWindow(ClientUser,friend);
            		ManageChatWindow.addChatWindow(ClientUser.getUserID()+" "+friend.getUserID(), chatwindow);
                	
 //           		chatRooms.put(friend, chatwindow);
                    Stage stage2 = chatwindow.getStage();
                    stage2.show();
//                    
//                    /*
//                     * ---------------------------------------------------
//                     * It happens when the client close the chat window.
//                     * ---------------------------------------------------
//                     */
//                    stage2.showingProperty().addListener((observable, oldValue, newValue) -> {
//                        if (oldValue == true && newValue == false) {
//                        	System.out.println("The window close");
//                        	chatRooms.remove(friend);
//                        }
//                    });
//            	}else {
//            		System.out.println("The chatroom already exist.");
//            		//chatRooms.get(friend).receiveMessage();
//            	}
//            	
            	System.out.println("the size of chatroom is "+chatRooms.size());
            }
        });
		return list;
	}
	public static void main(String[] args) {
	 	launch(args);
	}
}