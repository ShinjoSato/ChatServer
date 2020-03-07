package client;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientController extends Application{
	private static Stage primaryStage;
	private User ClientUser;
	private List<User> UserTable;

	@Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        
        makeScene("fxml/login.fxml", "Login");
    }

	public void makeScene(String fxmlfile, String title) throws Exception {
        makeScene(fxmlfile, title, 450, 600);
    }

    public void makeScene(String fxmlfile, String title, int width, int height) throws Exception {
        URL location = getClass().getResource(fxmlfile);

        Scene scene = GUIFunction.createNewScene(location, width, height);
        
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
    public TextField username, password, email, keyword;
    public TextArea message;
    public VBox talkHistory;
    public ListView<String> friendListView;
    
    @FXML
    public Label UserName;
    //public Button closeWindow;
    
    /**
     * This function is used on "login.fxml".
     */
    @FXML
    protected void signIn(ActionEvent event) throws Exception{
        System.out.println("signIn: "+email.getText()+", "+password.getText());

        System.out.println(this.UserTable);
        ClientUser = checkInUserTable(email.getText(), password.getText());
        System.out.println(ClientUser);
        if(ClientUser.getUserID() == null){
        	GUIFunction.createLoginfailWindow(getClass().getResource("fxml/loginfail.fxml"));
        }else{
        	makeScene("fxml/friendlist.fxml", "friend list");
            //addFriendToList();
            sampleFriendList();
        }
    }
    
    public static List<User> getUserTable(){
    	List<User> userTable = new ArrayList<User>();
        userTable.add(new User("01", "Shinjo Shinjo", "12345", "sxs1640@student.bham.ac.uk", true));
        userTable.add(new User("02", "Yi-Ming Chen", "12345", "yxc1016@student.bham.ac.uk", true));
        userTable.add(new User("03", "Zhengnan Sun", "12345", "zxs944@student.bham.ac.uk", false));
        userTable.add(new User("04", "Saba Akhlagh-Nejat", "12345", "sxa1012@student.bham.ac.uk", true));
        userTable.add(new User("05", "Ibiyemi Ogunyemi", "12345", "ixo984@student.bham.ac.uk", false));
        userTable.add(new User("06", "root", "1", "mail", false));
        
        return userTable;
    }
    
    public static User checkInUserTable(String email, String password) {
    	List<User> userTable = getUserTable();
    	User loginUser = new User();
    	for(int i=0; i<userTable.size(); i++) {
    		if(userTable.get(i).getEmail().equals(email) && userTable.get(i).getPassword().equals(password)) {
    			loginUser = userTable.get(i);
    		}
    	}
    	return loginUser;
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
        System.out.println("Login");
        makeScene("fxml/login.fxml", "Login");
    }

    /**
     * This function is used on "forgetting.fxml".
     */
    @FXML
    protected void sendPassword(ActionEvent event) throws Exception {
        System.out.println(username.getText()+", "+email.getText());
    }
    
    @FXML
    VBox friendList;
    protected void addFriendToList() {
    	User a = new User("1", "Shinjo", "1", "email", true);
    	
    	
    	if(a.getUserName().equals("Shinjo")) {
    		Label status = new Label("●");
    		
    		Label user = new Label("shinjo");
    		
    		HBox friend = new HBox();
    		
    		friend.getChildren().add(status);
    		friend.getChildren().add(user);
    		
    		ObservableList<String> listRecords = FXCollections.observableArrayList("shinio", "sato");
    		friendListView = new ListView<String>(listRecords);
    		friendListView.setCellFactory(TextFieldListCell.forListView());
    		friendListView.setEditable(true);
    		
    		System.out.println(friendListView);
    	}
    }
    
    private void sampleFriendList() {
    	List<User> friends = getUserTable();

    	ListView<Label> list = GUIFunction.createFriendListView(friends);
    	list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	try {
            		List<Label> test = (List<Label>)list.getSelectionModel().getSelectedItems();
            		Label test1 = test.get(0);
            		System.out.println(test1.getText());
            		String[] friend = test1.getText().split(":");
            		if(friends.get( list.getSelectionModel().getSelectedIndex() ).isState()) {
            			URL location = getClass().getResource("fxml/chat.fxml");
                    	Scene scene2 = GUIFunction.createNewScene(location, 350, 310);
                    	
                    	Stage stage2 = new Stage();
                        stage2.setScene(scene2);
                        stage2.show();
            		}
            	}catch(NullPointerException e){
            	}
            }
        });
    	
        list.setEditable(true);
        
        Label header = new Label(ClientUser.getUserName());
        header.setStyle("-fx-font-size: 45pt;");
        VBox headerBox = new VBox();
        headerBox.setStyle("alignment: center; -fx-background-color: \"indianred\"; ");
        headerBox.getChildren().add(header);
        
        VBox root = new VBox(headerBox, list);
                
        Scene scene = new Scene(root, 450, 600);

        primaryStage.setTitle("Friend list");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This function is used on "friendlist.fxml".
     */
    @FXML
    protected void selectFriend (MouseEvent event) throws Exception {
    	String frienddata = String.valueOf(friendListView.getSelectionModel().toString());
    			
        System.out.println("clicked on " + frienddata);
        if(!frienddata.equals("null")) {
        	URL location = getClass().getResource("fxml/chat.fxml");
        	Scene scene2 = GUIFunction.createNewScene(location, 350, 310);
        	
        	UserName.setText(frienddata);
        	Stage stage2 = new Stage();
            stage2.setScene(scene2);
            stage2.show();
        	        	
        }
    }

    /**
     * This function is used on "chat.fxml".
     */
    @FXML
    protected void sendMessage(ActionEvent event) {
    	VBox chatView = GUIFunction.addChatView(message.getText());
        talkHistory.getChildren().add( chatView );
        message.setText("");
    }

    /**
     * This function is used on "chat.fxml".
     */
    @FXML
    protected void searchWord(ActionEvent event) {
        System.out.println(keyword.getText());
        keyword.setText("");
    }

    /**
     * This function is used on "chat.fxml".
     */
    @FXML
    protected void pushEmoji(ActionEvent event) {
    	VBox emoji = GUIFunction.createEmoji();
        talkHistory.getChildren().add( emoji );
    }
}