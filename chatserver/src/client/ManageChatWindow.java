/**
 * 
 */
package client;
import java.util.*;
import common.Message;
import common.User;
/**
 * @author Boris
 *
 */



public class ManageChatWindow {
    private static HashMap storeWindow = new HashMap<ChatWindow, ChatWindow>();
    
    //add
    public static void addChatWindow(ChatWindow friend, ChatWindow chat) {
    	storeWindow.put(friend, chat);
    }
    
    //get
    public static ChatWindow getChatWindow(ChatWindow friend) {
    	return (ChatWindow)storeWindow.get(friend);
    }
    
}
