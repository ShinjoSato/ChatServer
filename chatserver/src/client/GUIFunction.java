package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIFunction {
	
	public static Scene createNewScene(URL location, int width, int height) {
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Scene scene2 = null;
		try {
			scene2 = new Scene((Pane)fxmlLoader.load(), width, height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scene2;
	}
	
	public static void createLoginfailWindow(URL location) {
		System.out.println("createLoginfailWindow");
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Stage stage2 = new Stage();

        Scene scene2;
		try {
			scene2 = new Scene((Pane)fxmlLoader.load(), 350, 310);
			stage2.setScene(scene2);
	        stage2.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return new Stage();
	}
	
	public static ListView<Label> createFriendListView(List<User> friends) {
    	ObservableList<Label> tests = FXCollections.observableArrayList();
    	for(int i=0; i<friends.size(); i++) {
    		
    		Label friend = new Label(friends.get(i).getUserID()+" : "+friends.get(i).getUserName());
    		friend.setStyle("-fx-font-size: 20pt;");
    		if(friends.get(i).isState()) friend.setStyle("-fx-font-size: 20pt; -fx-text-fill: green;");
    		else friend.setStyle("-fx-font-size: 20pt; -fx-text-fill: red;");
    		/*HBox friendBox = new HBox(friend);
    		
    		Label status = new Label("●");
    		if(friends.get(i).isState()) status.setStyle("-fx-font-size: 17pt; -fx-text-fill: green;");
    		else status.setStyle("-fx-font-size: 20pt; -fx-text-fill: red;");
    		HBox statusBox = new HBox(status);
    		
    		HBox test1 = new HBox();
    		test1.getChildren().add(statusBox);
    		test1.getChildren().add(friendBox);*/
    		
    		tests.add(friend);
    	}
    	        	    	
		return new ListView<>(tests);
	}
	
	public static VBox addChatView(String messageText) {
        System.out.println(messageText);


        VBox testbox = new VBox();
        testbox.setStyle("-fx-background-color: blue;");
        testbox.setAlignment(Pos.CENTER_RIGHT);

        Label test = new Label(messageText);
        test.setId("yourtalk");
        test.setStyle("-fx-font-size: 17pt; -fx-text-fill: pink;");

        testbox.getChildren().add(test);
        
        return testbox;
	}
	
	public static VBox createEmoji() {
		System.out.println(":)");

        VBox testbox = new VBox();
        testbox.setStyle("-fx-background-color: blue;");
        testbox.setAlignment(Pos.CENTER_RIGHT);

        Label test = new Label(":)");
        test.setId("yourtalk");
        test.setStyle("-fx-font-size: 17pt; -fx-text-fill: pink;");

        testbox.getChildren().add(test);
        //talkHistory.getChildren().add(testbox);
        
        return testbox;
	}
}
