package socialmagnet.view;

import socialmagnet.controller.PostController;
import socialmagnet.social.*;

import java.util.*;

/**
 * This Class represents the Thread page of Social Magnet
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class ThreadPage implements Page{
    private Session session;
    private PostController postController = new PostController();
    private Post post;
    private String wallVisiting;

    /**
     * Creates the Thread page 
     * @param session The session object to be assigned to the Thread page
     */    
    public ThreadPage(Session session){
        this.session = session;
        this.wallVisiting = session.getWallVisiting();
    }

    /**
     * Displays the interface of the thread page, including post, replies and users who like and dislike the post
     */
    public void display() {
        String username = session.getMember().getUsername();
        if (session.getThreadFrom().equals("NewsFeed")){
            this.post = postController.getNewsFeed(username).get(session.getThreadNo()-1);
        } else {
            ArrayList<Post> postList = postController.getPostsToUser(wallVisiting);
            postList = postController.sortPostList(postList, 5);
            this.post = postList.get(session.getThreadNo()-1);
        }
        postController.showThreadByPost(post);
        System.out.print("[M]ain | [K]ill | [R]eply | [L]ike | [D]islike > ");
    }

    /**
     * The method controls the next page to be displayed or action to perform based on the choice inputted by the user.
     * The user may enter M to navigate to the Main Menu Page.
     * The user may also enter K to remove any post from his wall before being navigated to the Main Menu Page.
     * Otherwise, the user may enter R to reply to the post, L to like the post or D to dislike the post.
     */    
    public void process() {
        Scanner sc = new Scanner(System.in);
        String nextPage = "";
        char choice;
        this.wallVisiting = session.getWallVisiting();

        String sessionMember =  this.session.getMember().getUsername();

        while (nextPage.equals("")) {
            display();
            String line = sc.nextLine();
            choice = line.charAt(0);
            switch (choice) {
                case 'M':
                    nextPage = "MainMenu";
                    break;
                case 'K':
                    postController.deletePost(post,sessionMember);
                    nextPage = "MainMenu";
                    break;
                case 'R':
                    postController.addReply(sessionMember, post);
                    break;
                case 'L':
                    postController.likePost(post, sessionMember);
                    break;
                case 'D':
                    postController.disLikePost(post, sessionMember);
                    break;
                default:
                    System.out.println("Enter M, K, R, L or D");
            }
            session.redirect(nextPage);
        }
    }
}