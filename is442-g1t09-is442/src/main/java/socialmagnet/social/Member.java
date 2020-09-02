package socialmagnet.social;

import java.util.*;
import socialmagnet.game.*;

/**
 * This Class represents the member in Social Magnet
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class Member{
    private String fullName;
    private String username;
    private String password;
    private ArrayList<String> friendList = new ArrayList<String>();
    private Farmer farmer;
    private ArrayList<Post> postList = new ArrayList<Post>();

    /**
     * Create a Member with the specified full name, username and password
     * @param fullName A String representing the user's full name
     * @param username A String representing the user's username
     * @param password A String representing the user's password
     */
    public Member(String fullName, String username, String password){
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.farmer = new Farmer();
    }

    //getters
    /**
    * Gets the full name of the member
    * @return The full name of the member
    */
    public String getFullName(){return fullName;}
    
    /**
    * Gets the username of the member
    * @return The username of the member
    */ 
    public String getUsername(){return username;}
    
    /**
    * Gets the password of the member
    * @return The password of the member
    */
    public String getPassword(){return password;}

    /**
    * Gets the member's list of friends
    * @return An ArrayList of Strings representing the list of friends
    */
    public ArrayList<String> getFriendList(){return friendList;}

    /**
    * Gets the Farmer object of the member
    * @return A Farmer object
    */
    public Farmer getFarmer(){return farmer;}

    //setters

    /**
    * Sets the Farmer
    * @param farmer The Farmer object to set as
    */
    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    /**
    * Gets the list of posts
    * @return An ArrayList of Post objects
    */
    public ArrayList<Post> getPostList(){return postList;}

    /**
    * Sets the list of friends
    * @param friendList The list of friends to set as
    */
    public void setFriendList(ArrayList<String> friendList){
        this.friendList = friendList;
    }

    // public void setPostList(ArrayList<Post> postList){
    //     this.postList = postList;
    // }

    // public void deletePost(Post post){
    //     postList.remove(post);
    // }
}


