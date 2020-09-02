package socialmagnet.view;

import socialmagnet.controller.PostController;

import java.util.*;

/**
 * This Class represents the News Feed page of Social Magnet
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class NewsFeedPage implements Page{
    private Session session;
    private PostController postController = new PostController();

    /**
     * Creates the News Feed page 
     * @param session The session object to be assigned to the News Feed page
     */    
    public NewsFeedPage(Session session){
        this.session = session;
    }

    /**
     * Displays the latest 5 posts on his or his friend's wall
     */    
    public void display() {
        String username = session.getMember().getUsername();
        postController.showNewsFeed(username);
        System.out.print("[M]ain | [T]hread > ");
    }
    
    /**
     * The method controls the next page to be displayed based on the choice inputted by the user.
     * The user may enter M to return to the Main Menu page or enter T followed by the post id number to navigate to its respective thread page
     */    
    public void process() {
        Scanner sc = new Scanner(System.in);
        String nextPage = "";
        char choice;

        while (nextPage.equals("")) {
            display();
            String line = sc.nextLine();
            choice = line.charAt(0);
            switch (choice) {
                case 'M':
                    nextPage = "MainMenu";
                    break;
                case 'T':
                    if (line.equals("T")){
                        System.out.print("Enter the thread number > ");
                        String lineNo = sc.next();
                        line = line + lineNo;
                    }
                    int threadNo = Character.getNumericValue(line.charAt(1)); // thread number
                    session.setThreadNo(threadNo);
                    session.setThreadOwner(session.getMember().getUsername());
                    session.setThreadFrom("NewsFeed");
                    nextPage = "Thread";
                    break;
                default:
                    System.out.println("Enter M or T");
            }
            session.redirect(nextPage);
        } 
    }

}