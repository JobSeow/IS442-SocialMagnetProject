package socialmagnet.dao;

import socialmagnet.social.*;
import java.sql.*;
import java.util.*;

/**
 * This Class represents the Member DAO.
 * @version 25th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class MemberDAO{
    private DBController controller;
    private String memberTableName = "magnet.member";

    /**
     * Create a MemberDAO
     */
    public MemberDAO(){
        this.controller = DBController.getController();
    }

    //read methods
    /**
     * Returns the list of members
     * @return A Farmer object
     */
    public ArrayList<String> retrieveMemberList(){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM magnet.friends;";

        ArrayList<String> rv = new ArrayList<String>();

        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                String username = rs.getString("username");
                rv.add(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    /**
     * Returns the Member object that have the inputted username 
     * @param username A String representing the username
     * @return A Member object if username exists, else returns null
     */
    public Member retrieveByUsername(String username) {
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String password = null;
        String fullname = null;

        String sql = "SELECT * FROM "+ memberTableName + " WHERE username = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                password = rs.getString("password");
                fullname = rs.getString("fullname");
                username = rs.getString("username");
                return new Member(fullname, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns true if the user is friends with another user, and false if otherwise
     * @param username A String representing the username
     * @param friendUsername A String representing the friend's username
     * @return A boolean, true if the user is friends with another user, and false if otherwise
     */
    public boolean checkFriendship(String username, String friendUsername){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        boolean rv = false;

        String sql = "SELECT * FROM magnet.friends WHERE username = ? AND friend_username = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, friendUsername);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                rv = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    /**
     * Returns true if the user exits in the database, and false if otherwise
     * @param username A String representing the username
     * @return A boolean, true if the user exits in the database, and false if otherwise
     */
    public boolean existInDB(String username){
        // replace with check db
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String password = null;
        String sql = "SELECT password FROM "+ memberTableName + " WHERE username = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                //password = rs.getString("password");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns true if the password is correct, and returns false if the password entered is incorrect
     * @param username A String representing the username
     * @param password A String representing the password
     * @return A boolean, true if the password is correct, and returns false if the password entered is incorrect
     */
    public boolean checkPassword(String username, String password){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;

        String checkPassword = null;

        String sql = "SELECT * FROM "+ memberTableName + " WHERE username = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                checkPassword = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password.equals(checkPassword);
    }

    /**
     * Returns the user's list of friends
     * @param username A String representing the username
     * @return An Arraylist of the user's friends
     */
    public ArrayList<String> retrieveFriendList(String username){
        ArrayList<String> rv = new ArrayList<String>();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;

        String sql = "SELECT * FROM magnet.friends WHERE username = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rv.add(rs.getString("friend_username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    /**
     * Returns the user's list of friend requests
     * @param username A String representing the username
     * @return An Arraylist of the user's friend requests
     */
    public ArrayList<String> retrieveFriendRequests(String username){
        ArrayList<String> rv = new ArrayList<String>();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;

        String sql = "SELECT * FROM magnet.friendrequests WHERE receiver = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rv.add(rs.getString("sender"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    //update methods
    /**
     * Adds a new user to the database
     * @param fullName A String representing the user's full name
     * @param username A String representing the user's username
     * @param password A String representing the user's password
     */
    public void addMemberToDB(String fullName, String username, String password){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sqlMember = "INSERT INTO "+ memberTableName + "(username, fullname, password)"+ "VALUES (?,?,?)";
        try {
            stmt = conn.prepareStatement(sqlMember);
            stmt.setString(1, username);
            stmt.setString(2, fullName);
            stmt.setString(3, password);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new Farmer to the database
     * @param username A String representing the user's username
     */
    public void addFarmerToDB(String username){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String farmerTableName = "magnet.farmer";
        String sqlFarmer = "INSERT INTO magnet.farmer (ownerUsername, gold, xp, rankName, plots, giftsSent) VALUES (?,?,?,?,?,?)";
        try {
            stmt = conn.prepareStatement(sqlFarmer);
            stmt.setString(1, username);
            stmt.setInt(2, 50);
            stmt.setInt(3, 0);
            stmt.setString(4, "Novice");
            stmt.setInt(5, 5);
            stmt.setInt(6, 0);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the specified users as friends
     * @param username A String representing the user's username
     * @param friendUsername A String representing the friend's username
     */
    public void addFriends(String username, String friendUsername){
        if (!checkFriendship(username,friendUsername)){
            addFriend(username, friendUsername);
            addFriend(friendUsername, username);
        }
    }

    /**
     * Add a new friend of the user to the database
     * @param username A String representing the user's username
     * @param friendUsername A String representing the friend's username
     */
    public void addFriend(String username, String friendUsername){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql1 = "INSERT INTO magnet.friends (username, friend_username)"+ "VALUES (?,?)";
        try {
            stmt = conn.prepareStatement(sql1);
            stmt.setString(1, username);
            stmt.setString(2, friendUsername);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a new friend request 
     * @param sender A String representing the sender
     * @param receiver A String representing the receiver
     * @return true if friend request was sent succesfully, else return false
     */
    public boolean addFriendRequest(String sender, String receiver){
         Connection conn = controller.getConnection();
         PreparedStatement stmt = null;
         boolean result = false;
         String sql1 = "INSERT INTO magnet.friendrequests (sender, receiver) VALUES (?,?)";
         try {
             stmt = conn.prepareStatement(sql1);
             stmt.setString(1, sender);
             stmt.setString(2, receiver);
             stmt.executeUpdate();
             result = true;
         } catch (SQLException e) {
             System.out.println("Request failed");
         }
         return result;
    }

    //delete methods
    /**
     * Accept a friend request 
     * @param sender A String representing the sender
     * @param receiver A String representing the receiver
     */
    public void acceptRequest(String sender, String receiver){
        addFriends(sender,  receiver);

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "DELETE FROM magnet.friendrequests WHERE sender = ? AND receiver = ?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, sender);
            stmt.setString(2, receiver);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reject a friend request 
     * @param sender A String representing the sender
     * @param receiver A String representing the receiver
     */
    public void rejectRequest(String sender, String receiver){

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "DELETE FROM magnet.friendrequests WHERE sender = ? AND receiver = ?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, sender);
            stmt.setString(2, receiver);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove friendship between two users
     * @param username1 A String representing the first user's username
     * @param username2 A String representing the second user's username
     */
    public void removeFriendship(String username1 , String username2){
        removeFriends(username1, username2);
        removeFriends(username2, username1);
    }

    /**
     * Remove the second username as a friend from first username
     * @param username1 A String representing the first user's username
     * @param username2 A String representing the second user's username
     */
    public void removeFriends(String username1, String username2){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "DELETE FROM magnet.friends WHERE username = ? AND friend_username = ?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username1);
            stmt.setString(2, username2);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}