package socialmagnet.view;

import socialmagnet.controller.*;
import socialmagnet.social.*;

import java.util.*;

/**
 * This Class represents the My Wall page of Social Magnet
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class MyWallPage implements Page{
    private Session session;
    private PostController postController = new PostController();
    private MemberController memberController = new MemberController();
    private String wallVisiting;

    /**
     * Creates the My Wall page 
     * @param session The session object to be assigned to the My Wall page
     */   
    public MyWallPage(Session session){
        this.session = session;
    }

    /**
     * Displays the latest 5 status updates
     */
    public void display() {
        Member member = memberController.retrieveByUsername(wallVisiting);
        postController.showMyWall(member);
//        System.out.print("[M]ain | [T]hread | [A]ccept Gift | [P]ost > ");
        System.out.print("[M]ain | [T]hread | [P]ost > ");
    }

    /**
     * The method controls the next page to be displayed or action to be performed based on the choice inputted by the user.
     * The user may enter M to return to the Main Menu page or enter T followed by the post id number to navigate to its respective thread page.
     * Otherwise, they may enter A to accept a gift or enter P to post to the wall.
     */    
    public void process() {
        Scanner sc = new Scanner(System.in);
        String nextPage = "";
        char choice;

        this.wallVisiting = session.getWallVisiting();

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
                    session.setThreadFrom("MyWall");
                    nextPage = "Thread";
                    break;
//                case 'A':
//                    break;
                case 'P':
                    postController.addWallPost(session.getMember().getUsername(), wallVisiting);
                    break;
                default:
                    System.out.println("Enter M or T");
            }
            session.redirect(nextPage);
        }
    }

}