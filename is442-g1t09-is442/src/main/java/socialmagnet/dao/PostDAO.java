package socialmagnet.dao;

import socialmagnet.social.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * This Class represents the Post DAO.
 * @version 25th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class PostDAO{
    private DBController controller = DBController.getController();

    //create
    /**
     * Adds a new post to the database
     * @param post A Post object to be added to the database
     */
    public void addPostToDb(Post post){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "INSERT INTO magnet.post (postID, posterUsername, receiverUsername, datePosted, message, likes, dislikes)"+ "VALUES (?,?,?,?,?,?,?)";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, post.getPostID());
            stmt.setString(2, post.getPosterUsername());
            stmt.setString(3, post.getReceiverUsername());
            stmt.setTimestamp(4, new java.sql.Timestamp(post.getDate().getTime()));
            stmt.setString(5, post.getMessage());
            stmt.setInt(6,post.getLikes());
            stmt.setInt(7,post.getDislikes());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new reply to the specified post
     * @param poster A String representing the poster
     * @param post A Post object 
     * @param replyMessage A String representing the reply message
     */
    public void addReply(String poster, Post post, String replyMessage){
        String postID = post.getPostID();
        String receiverUsername = post.getPosterUsername();
        String replyID = UUID.randomUUID().toString();
        Date datePosted = new Date();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "INSERT INTO magnet.reply (replyID, postedToID, posterUsername, receiverUsername, datePosted, message)"+ "VALUES (?,?,?,?,?,?)";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, replyID);
            stmt.setString(2, postID);
            stmt.setString(3, poster);
            stmt.setString(4, receiverUsername);
            stmt.setTimestamp(5, new java.sql.Timestamp(datePosted.getTime()));
            stmt.setString(6, replyMessage);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //read
    /**
     * Returns the list of posts made by the specified user
     * @param username A String representing the username
     * @return An ArrayList of posts
     */
    public ArrayList<Post> getPostsByUser(String username){
        ArrayList<Post> rv = new ArrayList<Post>();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM magnet.post WHERE posterUsername = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String postID = rs.getString("postID");
                String posterUsername = rs.getString("posterUsername");
                String receiverUsername = rs.getString("receiverUsername");
                Date datePosted = rs.getTimestamp("datePosted");
                String message = rs.getString("message");
                int likes = rs.getInt("likes");
                int dislikes = rs.getInt("dislikes");

                datePosted = new Date(datePosted.getTime());

                Post post = new Post(message, posterUsername, receiverUsername);
                post.setPostID(postID);
                post.setDatePosted(datePosted);
                post.setLikes(likes);
                post.setDislikes(dislikes);

                rv.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    /**
     * Returns the list of posts made to the specified user
     * @param username A String representing the username
     * @return An ArrayList of posts
     */
    public ArrayList<Post> getPostsToUser(String username){
        ArrayList<Post> rv = new ArrayList<Post>();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM magnet.post WHERE receiverUsername = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String postID = rs.getString("postID");
                String posterUsername = rs.getString("posterUsername");
                String receiverUsername = rs.getString("receiverUsername");
                Date datePosted = rs.getTimestamp("datePosted");
                String message = rs.getString("message");
                int likes = rs.getInt("likes");
                int dislikes = rs.getInt("dislikes");

                datePosted = new Date(datePosted.getTime());

                Post post = new Post(message, posterUsername, receiverUsername);
                post.setPostID(postID);
                post.setDatePosted(datePosted);
                post.setLikes(likes);
                post.setDislikes(dislikes);

                rv.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    /**
     * Returns the list of replies of the specified post identified by its post id
     * @param postID A String representing the post's id
     * @return An ArrayList of replies
     */
    public ArrayList<Reply> retrieveReplies(String postID){
        ArrayList<Reply> rv = new ArrayList<Reply>();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM magnet.reply WHERE postedToID = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, postID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String replyID = rs.getString("replyID");
                String postedToID = rs.getString("postedToID");
                String posterUsername = rs.getString("posterUsername");
                String receiverUsername = rs.getString("receiverUsername");
                Date datePosted = rs.getTimestamp("datePosted");
                String message = rs.getString("message");

                datePosted = new Date(datePosted.getTime());

                Reply reply = new Reply(message, posterUsername, receiverUsername);
                reply.setPostedTo(postedToID);
                reply.setDatePosted(datePosted);
                reply.setPostID(replyID);

                rv.add(reply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rv = sortReplyList(rv,3);
        Collections.reverse(rv);
        return rv;
    }

    /**
     * Returns the list of usernames who liked the specified post 
     * @param post The Post Object
     * @return An ArrayList of usernames who liked the post
     */
    public ArrayList<String> retrieveUsersWhoLiked(Post post){
        String postID = post.getPostID();
        ArrayList<String> rv = new ArrayList<String>();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM magnet.likes WHERE postID = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, postID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                rv.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    /**
     * Returns the list of usernames who disliked the specified post 
     * @param post The Post Object
     * @return An ArrayList of usernames who disliked the post
     */
    public ArrayList<String> retrieveUsersWhoDisliked(Post post){
        String postID = post.getPostID();
        ArrayList<String> rv = new ArrayList<String>();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM magnet.dislikes WHERE postID = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, postID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                rv.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    // update
    /**
     * Adds a like to the specified post
     * @param post The Post Object
     */
    public void addLike(Post post){
        int likes = post.getLikes();
        String postID = post.getPostID();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE magnet.post SET likes = ? WHERE postID = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, likes + 1);
            stmt.setString(2, postID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a dislike to the specified post
     * @param post The Post Object
     */
    public void addDislike(Post post){
        int dislikes = post.getDislikes();
        String postID = post.getPostID();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE magnet.post SET dislikes = ? WHERE postID = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, dislikes + 1);
            stmt.setString(2, postID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //delete
    /**
     * Deletes the specified post
     * @param post The Post Object
     */
    public void deletePost(Post post){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "DELETE FROM magnet.post WHERE postID = ?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, post.getPostID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // other methods
    /**
     * Returns the list of sorted replies according to their date posted with the latest post first
     * @param replyList The ArrayList of replies to be sorted
     * @param size An integer representing the size of the reply list that is returned
     * @return An ArrayList of sorted replies
     */
    public ArrayList<Reply> sortReplyList(ArrayList<Reply> replyList, int size){
        Comparator<Reply> compareByDate = Comparator.comparing(Text::getDate);
        replyList.sort(compareByDate.reversed());
        if (replyList.size() > size){
            replyList = new ArrayList<Reply>(replyList.subList(0,size));
        }
        return replyList;
    }

    /**
     * Updates database when a user's post receive a new like
     * @param post The Post object
     * @param username A String representing the username
     */
    public void likePost(Post post, String username){
        String postID = post.getPostID();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "INSERT INTO magnet.likes (postID, username)"+ "VALUES (?,?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, postID);
            stmt.setString(2, username);
            stmt.executeUpdate();
            addLike(post);

        } catch (SQLException e) {
            System.out.println("Already liked");
        }
    }

    /**
     * Updates database when a user's post receive a new dislike
     * @param post The Post object
     * @param username A String representing the username
     */
    public void disLikePost(Post post, String username){
        String postID = post.getPostID();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "INSERT INTO magnet.dislikes (postID, username)"+ "VALUES (?,?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, postID);
            stmt.setString(2, username);
            stmt.executeUpdate();
            addDislike(post);

        } catch (SQLException e) {
            System.out.println("Already disliked");
        }
    }
}