package socialmagnet.controller;

import socialmagnet.dao.*;
import socialmagnet.social.*;
import socialmagnet.game.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * This Class represents the PostController class that contains the methods related to the Post class.
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class PostController {
    private MemberDAO memberDAO = new MemberDAO();
    private PostDAO postDAO = new PostDAO();
    private FarmerDAO farmerDAO = new FarmerDAO();
    private FarmerController farmerController = new FarmerController();

    /**
     * Returns a list of posts sorted by their date posted with the latest post first. The length of the post returned is determined by the size inputted.
     * @param postList An ArrayList of Post objects to be sorted
     * @param size An integer representing the size of the ArrayList to be returned
     * @return An ArrayList of Post objects that have been sorted according to their date posted
     */    
    public ArrayList<Post> sortPostList(ArrayList<Post> postList, int size){
        Comparator<Post> compareByDate = Comparator.comparing(Text::getDate);
        postList.sort(compareByDate.reversed());
        if (postList.size() > size){
            postList = new ArrayList<Post>(postList.subList(0,size));
        }
        return postList;
    }

    /**
     * Remove tags from message and populate users into a list
     * @param post The Post object to obtain tagged users from
     */ 
    public void populateTaggedUsers(Post post){
        String message = post.getMessage();
        ArrayList<String> taggedUsers = new ArrayList<String>();
        ArrayList<String> memberList = memberDAO.retrieveMemberList();

        String[] splitMessages = message.split(" ");
        for (int i = 0; i < splitMessages.length; i++){
            String part = splitMessages[i];
            if (part.charAt(0) == '@'){
                String usernameChunk = part.substring(1);
                String username = usernameChunk.split("[^\\w]+")[0];
                if (memberList.contains(username)){
                    taggedUsers.add(username);
                    splitMessages[i] = usernameChunk;
                }
            }
        }
        String finalMessage = String.join(" ", splitMessages);
//        taggedUsers.add(post.getPosterUsername());

        post.setTaggedUsers(taggedUsers);
        post.setMessage(finalMessage);
    }

    /**
     * Gets the news feed of the specified user
     * @param username A String representing the user's username
     * @return An ArrayList of Post objects that make up the user's news feed
     */ 
    public ArrayList<Post> getNewsFeed(String username){
        ArrayList<Post> rv = new ArrayList<Post>();
        ArrayList<String> friendList = memberDAO.retrieveFriendList(username);

        ArrayList<Post> postsToUser = postDAO.getPostsToUser(username);
//        ArrayList<Post> postsByUser = postDAO.getPostsByUser(username);

//        rv.addAll(postsByUser);
        rv.addAll(postsToUser);

        for (String friend: friendList){
            ArrayList<Post> postsToFriend = postDAO.getPostsToUser(friend);
            rv.addAll(postsToFriend);
        }

        rv = removeDuplicatePosts(rv);

        rv = sortPostList(rv,5);
        return rv;
    }

    /**
     * Adds a new post 
     * @param message A String representing the post's message
     * @param posterUsername A String representing the poster's username
     * @param receiverUsername A String representing the receiver's username
     */ 
    public void addNewPost(String message, String posterUsername, String receiverUsername){
        Post post = new Post(message, posterUsername, receiverUsername);
        populateTaggedUsers(post);

        postDAO.addPostToDb(post);

        ArrayList<String> taggedUsers = post.getTaggedUsers();
        for (String username: taggedUsers){
            Post newPost = new Post(post.getMessage(), posterUsername, username);
            postDAO.addPostToDb(newPost);
        }
    }

    /**
     * Removes any duplicate posts from the passed ArrayList
     * @param postList An ArrayList of Post objects
     * @return An ArrayList of Post objects with any duplicate posts removed
     */     
    public ArrayList<Post> removeDuplicatePosts(ArrayList<Post> postList){
        ArrayList<Post> rv = new ArrayList<Post>();
        ArrayList<String> messages = new ArrayList<String>();

        for (Post post : postList){
            String message = post.getMessage();
            if(!messages.contains(message)){
                rv.add(post);
                messages.add(message);
            }
        }
        return rv;
    }

    /**
     * Deletes a post 
     * @param post The Post object to be deleted
     * @param requesterUsername A String representing the requester's username
     */ 
    public void deletePost (Post post , String requesterUsername){
        String poster = post.getPosterUsername();
        if (poster.equals(requesterUsername)){
            postDAO.deletePost(post);
            System.out.println("Post deleted!");
        } else {
            System.out.println("This is not your post!");
        }
    }

    /**
     * Adds a new reply
     * @param username A String representing the user's username
     * @param post The Post object to add the reply to
     */ 
    public void addReply(String username, Post post){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your reply > ");
        String replyMessage = sc.nextLine();

        postDAO.addReply(username, post, replyMessage);
    }

    /**
     * Adds a Wall Post
     * @param poster A String representing the poster's username
     * @param receiver A String representing the receiver's username
     */ 
    public void addWallPost(String poster, String receiver){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your post> ");
        String message = sc.nextLine();
        System.out.println(poster + " " + receiver);
        addNewPost(message, poster, receiver);
    }

    //display methods
    /**
     * Displays the user's news feed
     * @param username A String representing the user's username
     */ 
    public void showNewsFeed(String username){
        ArrayList<Post> newsFeed = getNewsFeed(username);
        System.out.println("== Social Magnet :: News Feed ==");
        showPosts(newsFeed);
        System.out.println();
    }

    /**
     * Displays a user's wall
     * @param member The Member object whose wall is to be displayed
     */ 
    public void showMyWall(Member member){
        System.out.println("== Social Magnet :: My Wall ==");
        System.out.println("About " + member.getUsername());
        System.out.println("Full Name: " + member.getFullName());

        Farmer farmer = farmerDAO.retrieveFarmer(member.getUsername());
        String wealthRank = farmerController.getWealthStatus(member.getUsername());

        System.out.println(farmer.getRank() + " Farmer," + wealthRank);
        System.out.println();

        ArrayList<Post> postList = postDAO.getPostsToUser(member.getUsername());
        postList = sortPostList(postList, 5);
        showPosts(postList);
        System.out.println();
    }

    /**
     * Displays the list of posts
     * @param postList An ArrayList of Post objects to be displayed
     */ 
    public void showPosts(ArrayList<Post> postList){
        int index = 1;
        for (Post post : postList){
            System.out.println(index + " " + post.getPosterUsername() +": " + post.getMessage());
            showLikesDislikes(post);
            int subIndex = 1;
            ArrayList<Reply> replyIDs = postDAO.retrieveReplies(post.getPostID());
            for (Reply reply : replyIDs){
                System.out.println("\t" + index + "." + subIndex + " " + reply.getPosterUsername() + ": " + reply.getMessage());
                subIndex += 1;
            }
            index += 1;
            System.out.println();
        }
    }

    /**
     * Displays the thread by post
     * @param post The Post object 
     */ 
    public void showThreadByPost(Post post){
        System.out.println("== Social Magnet :: View a Thread ==");
        System.out.println("1 " + post.getPosterUsername() +": " + post.getMessage());
        showLikesDislikes(post);
        int subIndex = 1;
        ArrayList<Reply> replyIDs = postDAO.retrieveReplies(post.getPostID());
        for (Reply reply : replyIDs){
            System.out.println("\t1." + subIndex + " " + reply.getPosterUsername() + ": " + reply.getMessage());
            subIndex += 1;
        }
        System.out.println();

        System.out.println("Who likes this post:");
        ArrayList<String> likedList = postDAO.retrieveUsersWhoLiked(post);
        int index = 1;
        for (String username: likedList){
            Member member = memberDAO.retrieveByUsername(username);
            String fullName = member.getFullName();
            System.out.println(index + ". " + fullName + "(" + username + ")");
            index +=1;
        }
        System.out.println();

        System.out.println("Who dislikes this post:");
        ArrayList<String> dislikedList = postDAO.retrieveUsersWhoDisliked(post);
        index = 1;
        for (String username: dislikedList){
            Member member = memberDAO.retrieveByUsername(username);
            String fullName = member.getFullName();
            System.out.println(index + ". " + fullName + "(" + username + ")");
        }
    }

    /**
     * Displays the likes and dislikes a post has
     * @param post The Post object
     */ 
    public void showLikesDislikes(Post post){
        int likes = post.getLikes();
        int dislikes = post.getDislikes();
        System.out.println("[ " + likes + " likes, " + dislikes + " dislikes ]");
    }

    public ArrayList<Post> getPostsToUser(String username){
        return postDAO.getPostsToUser(username);
    }

    public void likePost(Post post, String username){
        postDAO.likePost(post, username);
    }

    public void disLikePost(Post post, String username){
        postDAO.disLikePost(post, username);
    }
}
