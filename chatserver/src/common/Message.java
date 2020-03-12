package common;
import java.io.Serializable;

/**
 * make rules for message 
*/

public class Message implements Serializable{
	private String messageType;
	private String sender;
	private String recipient;
	private String contain;
	private String sendTime;
	private User owner;
	private User friend;
	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
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
	
	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}
	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}
	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	/**
	 * @return the contain
	 */
	public String getContain() {
		return contain;
	}
	/**
	 * @param con the contain to set
	 */
	public void setContain(String contain) {
		this.contain = contain;
	}
	/**
	 * @return the sendTime
	 */
	public String getSendTime() {
		return sendTime;
	}
	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	
	
}
