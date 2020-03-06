package client;

import java.io.File;
import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIController extends Application{
	private static Stage primaryStage;

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
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Scene scene = new Scene((Pane)fxmlLoader.load(), width, height);
        
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
    public Button closeWindow;

    /**
     * This function is used on "login.fxml".
     */
    @FXML
    protected void signIn(ActionEvent event) throws Exception{
        System.out.println(username.getText()+", "+password.getText());

        //existUser();
        /*boolean exist = ClientConServer.sendLogInToServer(username.getText(), password.getText());
        System.out.println("user is "+ exist);*/

        
        boolean exist = true;
        if(exist){
            makeScene("fxml/friendlist.fxml", "friend list");
        }else{
            URL location = getClass().getResource("fxml/loginfail.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);

            Stage stage2 = new Stage();

            Scene scene2 = new Scene((Pane)fxmlLoader.load(), 350, 310);
            stage2.setScene(scene2);
            
            /*closeWindow.setOnAction(actionEvent -> {
            	stage2.close();
            });*/

            stage2.show();
        }
        
        //makeScene("fxml/friendlist.fxml", "friend list");
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

    /**
     * This function is used on "friendlist.fxml".
     */
    @FXML
    protected void selectFriend (MouseEvent event) throws Exception {
    	String frienddata = String.valueOf(friendListView.getSelectionModel().getSelectedItem());
    			
        System.out.println("clicked on " + frienddata);
        if(!frienddata.equals("null")) {
        	
        	URL location = getClass().getResource("fxml/chat.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);

            Stage stage2 = new Stage();

            Scene scene2 = new Scene((Pane)fxmlLoader.load(), 350, 310);
            stage2.setScene(scene2);

            stage2.show();
        	
            //makeScene("fxml/chat.fxml", "Friend name");        	
        }
    }

    /**
     * This function is used on "chat.fxml".
     */
    @FXML
    protected void sendMessage(ActionEvent event) {
        String messageText = message.getText();
        System.out.println(messageText);
        message.setText("");

        VBox testbox = new VBox();
        testbox.setStyle("-fx-background-color: blue;");
        testbox.setAlignment(Pos.CENTER_RIGHT);

        Label test = new Label(messageText);
        test.setId("yourtalk");
        test.setStyle("-fx-font-size: 17pt; -fx-text-fill: pink;");

        testbox.getChildren().add(test);
        talkHistory.getChildren().add(testbox);
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
        System.out.println(":)");

        VBox testbox = new VBox();
        testbox.setStyle("-fx-background-color: blue;");
        testbox.setAlignment(Pos.CENTER_RIGHT);

        Label test = new Label(":)");
        test.setId("yourtalk");
        test.setStyle("-fx-font-size: 17pt; -fx-text-fill: pink;");

        testbox.getChildren().add(test);
        talkHistory.getChildren().add(testbox);
    }
}