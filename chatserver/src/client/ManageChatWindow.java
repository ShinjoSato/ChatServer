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
    private static HashMap storeWindow = new HashMap<String, ChatWindow>();
    
    //add
    public static void addChatWindow(String lodinIDAndFriendID, ChatWindow chat) {
    	storeWindow.put(lodinIDAndFriendID, chat);
    }
    
    //get
    public static ChatWindow getChatWindow(String lodinIDAndFriendID) {
    	return (ChatWindow)storeWindow.get(lodinIDAndFriendID);
    }
    
}
