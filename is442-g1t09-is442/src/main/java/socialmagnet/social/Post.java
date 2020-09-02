package socialmagnet.social;

import java.util.*;

/**
 * This Class represents the post in Social Magnet
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class Post extends Text{
    private int likes;
    private int dislikes;

    // constructor
    /**
     * Create a Post with the specified message, poster's username and receiver's username
     * @param message A String representing the message to be posted
     * @param posterUsername A String representing the poster's username
     * @param receiverUsername A String representing the receiver's username
     */
    public Post(String message, String posterUsername, String receiverUsername){
        super(message, posterUsername,receiverUsername);
    }
    
    // getters
    /**
    * Gets the number of likes a post has
    * @return An integer representing the number of likes
    */
    public int getLikes(){return likes;}

    /**
    * Gets the number of dislikes a post has
    * @return An integer representing the number of dislikes
    */
    public int getDislikes(){return dislikes;}

    //setters
    /**
    * Sets the number of likes a post has
    * @param likes An integer representing the number of likes to set as
    */
    public void setLikes(int likes){
        this.likes = likes;
    }

    /**
    * Sets the number of dislikes a post has
    * @param dislikes An integer representing the number of dislikes to set as
    */
    public void setDislikes(int dislikes){
        this.dislikes = dislikes;
    }
}