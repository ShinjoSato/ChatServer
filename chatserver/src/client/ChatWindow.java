package client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import common.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatWindow {
	private User client, friend;
	private Stage stage;
	private VBox talkHistory;
	private final String fontSize = "13";
	
	public ChatWindow(User client, User friend) {
		System.out.println("Enter here");
		this.client = client;
		this.friend = friend;
		this.stage  = new Stage();
		this.talkHistory = new VBox();
		createChatWindowStage();
	}
	
	/**
	 * @return the client
	 */
	public User getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(User client) {
		this.client = client;
	}

	/**
	 * @return the friend
	 */
	public User getFriend() {
		return friend;
	}

	/**
	 * @param friend the friend to set
	 */
	public void setFriend(User friend) {
		this.friend = friend;
	}

	public ChatWindow() {
		
	}
	
	public void createChatWindowStage() {
		System.out.println("The friend is "+ friend);
		Group group = new Group();
		
		VBox allcontent = new VBox();
		
		VBox top = new VBox();
		top.setPrefWidth(450);
		top.setStyle("-fx-font-size: 23pt; -fx-background-color: linear-gradient(to right, #00355e, #003541);");
		Label friendName = new Label(friend.getUserName());
		friendName.setStyle("-fx-text-fill: white;");
		HBox keyword = new HBox();
		keyword.setAlignment(Pos.CENTER);
		keyword.setStyle("-fx-font-size: 9pt;");
		TextField keywordField = new TextField();
		Button keywordButton = new Button("search keyword");
		keyword.getChildren().add(keywordField);
		keyword.getChildren().add(keywordButton);
		top.getChildren().add(friendName);
		top.getChildren().add(keyword);
		
		VBox middle = new VBox();
		talkHistory.setPrefWidth(450);
		talkHistory.setPrefHeight(420);
		talkHistory.setId("talkHistoryTo"+friend.getUserID());
		talkHistory.setStyle("-fx-background-image: url(\"./client/images/background-chatroom.jpg\");");
		String cd = new File("./src").getAbsoluteFile().getParent();
        System.out.println(cd);
		ScrollPane scrollPane = new ScrollPane(talkHistory);
	    scrollPane.setFitToHeight(true);
	    middle.getChildren().add(scrollPane);
		
		VBox bottom = new VBox();
		bottom.setStyle("-fx-background-color: \"grey\";");
		HBox bottomHBox = new HBox();
		bottomHBox.setStyle(" -fx-background-color: lightgray;");
		TextArea chatText = new TextArea();
		chatText.setWrapText(true);
		chatText.setPrefHeight(100.0);
		chatText.setPrefWidth(200.0);
		Button emojiButton = new Button(":)");
		emojiButton.setStyle("-fx-background-radius: 30px;");
		emojiButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				talkHistory.getChildren().add( createSpeechBubble(":)", Pos.BASELINE_RIGHT) );
			}
		});
		
		Button messageButton = new Button("send message");
		messageButton.setStyle("-fx-background-radius: 30px;");
		
		/**
		 * Send message function.
		 */
		messageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				talkHistory.getChildren().add( createSpeechBubble(chatText.getText(), Pos.BASELINE_RIGHT) );
                Message m = new Message();
		        
		        m.setSender(client.getUserID()); 
		        m.setRecipient(friend.getUserID());// friednName
		        m.setContain(chatText.getText()); //message contain
				System.out.println("-----\nFrom:"+client+"\nTo: "+friend+"\nMessage: "+chatText.getText()+"\n-----");
		        try {
					ObjectOutputStream mouth = new ObjectOutputStream(
			        (ManagerClientThread.getServerThread(m.getSender()).getS()).getOutputStream());
				    mouth.writeObject(m);
		        } catch (IOException e) {
					e.printStackTrace();
				}
		        chatText.setText("");
			}	
		});
		bottomHBox.getChildren().add(emojiButton);
		bottomHBox.getChildren().add(messageButton);
		bottom.getChildren().add(bottomHBox);
		bottom.getChildren().add(chatText);
		
		allcontent.getChildren().add(top);
		allcontent.getChildren().add(middle);
		allcontent.getChildren().add(bottom);
		
		group.getChildren().add(allcontent);

        Scene scene2 = new Scene(group, 450, 600);
        this.stage = new Stage();
        this.stage.setScene(scene2);
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
	public void receiveMessage(String message) {
		System.out.println("receiveMessage\n-----\nTo: "+friend+"\nMessage: "+message+"\n-----");
		this.talkHistory.getChildren().add( createSpeechBubble(message, Pos.BASELINE_LEFT) );
	    System.out.println("This is chat window, I receive "+ message);
	}
	
	public void test() {
		System.out.println("The client is "+client);
	}
	
	/**
	 * This function creates speech bubbles like "Hi! How are you?" on the chat screen.
	 * @param message
	 * @param position
	 * @return
	 */
	public VBox createSpeechBubble(String message, Pos position) {
		VBox textPhrase = new VBox();
		if(!message.equals("")) {
			textPhrase.setPrefWidth(0.2);
			textPhrase.setPrefHeight(0.2);
			textPhrase.setAlignment(position);		
			//textPhrase.setStyle("-fx-background-color: \"lightblue\";");
			Label text = new Label("  "+message+"  ");
			if(position == Pos.BASELINE_RIGHT) {
				text.setStyle("-fx-font-size: "+fontSize+"pt; -fx-background-color: \"lightgreen\"; -fx-background-radius: 30px; -fx-effect: dropshadow(three-pass-box, rgb(0, 0, 0, 0.6), 5, 0.0, 0, 2);");
			}else {
				text.setStyle("-fx-font-size: "+fontSize+"pt; -fx-background-color: \"lightgray\"; -fx-background-radius: 30px; -fx-effect: dropshadow(three-pass-box, rgb(0, 0, 0, 0.6), 5, 0.0, 0, 2);");
			}
			textPhrase.getChildren().add(text);		
		}
		return textPhrase;
	}
}