package socialmagnet.social;

import java.util.*;

/**
 * This Class represents text that can be posted onto the Social Magnet.
 * It is the parent class of Post and Reply classes
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class Text{
    private String postID;
    private String message;
    private Date datePosted;
    private String posterUsername;
    private String receiverUsername;
    private ArrayList<String> taggedUsers = new ArrayList<String>();

    /**
     * Creates a new Text object with the specified message, poster's username and receiver's username
     * @param message A String representing the message to be posted
     * @param posterUsername A String representing the poster's username
     * @param receiverUsername A String representing the receiver's username
     */
    public Text(String message, String posterUsername, String receiverUsername){
        this.postID = UUID.randomUUID().toString();
        this.message = message;
        this.posterUsername = posterUsername;
        this.receiverUsername = receiverUsername;
        this.datePosted = new Date();
    }

    /**
     * Sets the post id of the Text object
     * @param postID A String representing the post id to set as
     */
    public void setPostID (String postID){
        this.postID = postID;
    }

    /**
     * Sets the message of the Text object
     * @param message A String representing the message to set as
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the date posted of the Text object
     * @param datePosted A Date object representing the date posted to set as
     */
    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    /**
     * Sets the poster's username of the Text object
     * @param posterUsername A String representing the poster's username to set as
     */
    public void setPosterUsername(String posterUsername) {
        this.posterUsername = posterUsername;
    }

    /**
     * Sets the receiver's username of the Text object
     * @param receiverUsername A String representing the receiver's username to set as
     */
    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    /**
     * Sets the list of tagged users of the Text object
     * @param taggedUsers An ArrayList of Strings representing the usernames of the tagged users
     */
    public void setTaggedUsers(ArrayList<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    /**
    * Gets the post id of the Text object
    * @return A String representing the post id
    */
    public String getPostID(){return postID;}

    /**
    * Gets the message of the Text object
    * @return A String representing the message
    */
    public String getMessage(){return message;}

    /**
    * Gets the date posted of the Text object
    * @return A Date object representing the date posted
    */
    public Date getDate(){return datePosted;}

    /**
    * Gets the poster's username of the Text object
    * @return A String representing the poster's username
    */
    public String getPosterUsername(){return posterUsername;}

    /**
    * Gets the receiver's username of the Text object
    * @return A String representing the receiver's username
    */
    public String getReceiverUsername(){return receiverUsername;}

    /**
    * Gets the list of tagged users of the Text object
    * @return An ArrayList of Strings representing the usernames of the tagged users
    */
    public ArrayList<String> getTaggedUsers(){return taggedUsers;}
}