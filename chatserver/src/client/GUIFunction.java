package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import common.Message;
import common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIFunction {
	
	private static User client;
	
	public GUIFunction(User client) {
		this.client = client;
	}
	
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
	}
	
	public static ListView<HBox> createFriendListView(List<User> friends) {
    	ObservableList<HBox> tests = FXCollections.observableArrayList();
    	for(int i=0; i<friends.size(); i++) {
    		Label friendTag = new Label(friends.get(i).getUserName());
			friendTag.setStyle("-fx-font-size: 20pt;");
    		Label statusTag = new Label("���");
    		if(friends.get(i).isState()) statusTag.setStyle("-fx-font-size: 20pt; -fx-text-fill: green;");
    		else statusTag.setStyle("-fx-font-size: 20pt; -fx-text-fill: red;");
    		HBox friendrow = new HBox(statusTag, friendTag);
    		
    		tests.add(friendrow);
    	}
    	ListView<HBox> list = new ListView<>(tests);
    	list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	URL location = getClass().getResource("fxml/chat.fxml");
            	try {
            		List<Integer> friendIndex = list.getSelectionModel().getSelectedIndices();
            		User friend = friends.get( friendIndex.get(0));
                    Stage stage2 = createChatStage(friend);
                    stage2.show();
            	}catch(NullPointerException e){}
            }
        });
		return list;
	}
	
	public static Stage createChatStage(User friend) {
		
		System.out.println("The friend is "+ friend);
		Group group = new Group();
		
		VBox allcontent = new VBox();//whole chat window
		//---------------Top-----------------------------------------
		VBox top = new VBox(); //Green area
		top.setPrefWidth(450);// window width
		top.setStyle("-fx-font-size: 23pt; -fx-background-color: \"lawngreen\";");
		Label friendName = new Label(friend.getUserName());// UserName
		
		//-------------------------------------------------------
		/** 92(11:55)
		ManageChat.addChat(client.getUserID(), friendName);
		*/
		HBox keyword = new HBox();// Search word area
		keyword.setAlignment(Pos.CENTER);
		keyword.setStyle("-fx-font-size: 9pt;");
		TextField keywordField = new TextField();
		Button keywordButton = new Button("search keyword");
		keyword.getChildren().add(keywordField);
		keyword.getChildren().add(keywordButton);
		top.getChildren().add(friendName);
		top.getChildren().add(keyword);
		//-----------------------------------------------------------
		
		//-----------------------mid------------------------
		VBox talkHistory = new VBox();
		talkHistory.setPrefWidth(450);
		talkHistory.setPrefHeight(320);
		talkHistory.setId("talkHistoryTo"+friend.getUserID());
		ScrollPane scrollPane = new ScrollPane(talkHistory);
	    scrollPane.setFitToHeight(true);
		
		VBox bottom = new VBox();
		bottom.setStyle("-fx-background-color: \"grey\";");
		bottom.setLayoutX(14.0);
		bottom.setLayoutX(200.0);
		bottom.setPrefHeight(300.0);
		bottom.setPrefWidth(450.0);
		HBox bottomHBox = new HBox();
		TextArea chatText = new TextArea();
		chatText.setWrapText(true);
		Button emojiButton = new Button(":)");
		emojiButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				VBox textPhrase = new VBox();
				textPhrase.setAlignment(Pos.BASELINE_RIGHT);
				Label text = new Label(":)");
				text.setStyle("-fx-font-size: 17pt;");
				textPhrase.getChildren().add(text);
				talkHistory.getChildren().add(textPhrase);
			}
		});
		Button messageButton = new Button("send message");
		
		
		/**
		 * Send message function.
		 */
		messageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("-----\nFrom:"+client+"\nTo: "+friend+"\nMessage: "+chatText.getText()+"\n-----");
				//text area [hi]
				VBox textPhrase = new VBox();
				textPhrase.setAlignment(Pos.BASELINE_RIGHT);
				
				Label text = new Label(chatText.getText());//get text from bottom
				text.setStyle("-fx-font-size: 17pt;");
				textPhrase.getChildren().add(text);
				talkHistory.getChildren().add(textPhrase);
                Message m = new Message();
		        
		        m.setSender(client.getUserID()); 
		        m.setRecipient(friend.getUserID());// friednName
		        m.setContain(chatText.getText()); //message contain
				System.out.println("-----\nFrom:"+client+"\nTo: "+friend+"\nMessage: "+chatText.getText()+"\n-----");
		        chatText.setText("");
		        try {
					ObjectOutputStream mouth = new ObjectOutputStream(
			        (ManagerClientThread.getServerThread(m.getSender()).getS()).getOutputStream());
				    mouth.writeObject(m);
		        } catch (IOException e) {
					e.printStackTrace();
				}
			
			}	
		});
		
		bottomHBox.getChildren().add(chatText);
		bottomHBox.getChildren().add(emojiButton);
		bottomHBox.getChildren().add(messageButton);
		bottom.getChildren().add(bottomHBox);
		
		allcontent.getChildren().add(top);
		allcontent.getChildren().add(scrollPane);
		allcontent.getChildren().add(bottom);
		
		group.getChildren().add(allcontent);

        Scene scene2 = new Scene(group, 450, 600);
        Stage stage = new Stage();
        stage.setScene(scene2);
        
        return stage;
	}
	
/*	public static VBox addChatView(String messageText) {
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
        
        return testbox;
	}*/
}