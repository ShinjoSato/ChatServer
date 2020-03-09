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
//	private ArrayList<VBox> friendChatData;
	private GUIFunction GUI;

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
        System.out.println("signIn: "+email.getText()+", "+password.getText());

        System.out.println(this.UserTable);
        
        // Finally we use this one: input email and passeword.
        
        ClientModel a = new ClientModel();
        //User b = a.sendLogInToServer(username.getText(), password.getText());
        
        ClientUser = RuleBook.checkInUserTable(getUserTable(), email.getText(), password.getText());
        System.out.println(ClientUser);
        
        if(ClientUser.getUserID() == null){
        	GUIFunction.createLoginfailWindow(getClass().getResource("fxml/loginfail.fxml"));
        }else{
        	GUI = new GUIFunction(ClientUser);
        	createFriendListWindow();
        	//renewFriendList();
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
    private void createFriendListWindow() throws Exception {
       	makeScene("fxml/friendlist.fxml", "friend list");
    }
    
    @FXML
    protected void renewFriendList(ActionEvent event) throws Exception {
    	ListView<HBox> friendlist = GUIFunction.createFriendListView(getUserTable());
    	friendListBox.getChildren().remove(0);
    	friendListBox.getChildren().add(friendlist);
    }

    /**
     * This function is used on "chat.fxml".
     */
    @FXML
    protected void sendMessage(ActionEvent event) {
    	/*VBox chatView = GUIFunction.addChatView(message.getText());
        talkHistory.getChildren().add( chatView );
        System.out.println(talkHistory);
        message.setText("");*/
    }

    /**
     * This function is used on "chat.fxml".
     */
    @FXML
    protected void searchWord(ActionEvent event) {
        /*System.out.println(keyword.getText());
        keyword.setText("");*/
    }

    /**
     * This function is used on "chat.fxml".
     */
    @FXML
    protected void pushEmoji(ActionEvent event) {
    	/*VBox emoji = GUIFunction.createEmoji();
        talkHistory.getChildren().add( emoji );*/
    }
    
}