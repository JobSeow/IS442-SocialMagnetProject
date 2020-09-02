package socialmagnet.view;

import socialmagnet.controller.MemberController;

import java.util.*;

/**
 * This Class represents the Welcome page, which is shown to the user when he or she first starts the application.
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class WelcomePage implements Page {
    private Session session;
    private MemberController memberController;

    /**
     * Creates the Welcome page 
     * @param session The session object to be assigned to the Welcome page
     */
    public WelcomePage(Session session){
        this.session = session;
        this.memberController = new MemberController();
    }

    /**
     * Displays the interface of the Welcome page
     */    
    public void display(){
        System.out.println("== Social Magnet :: Welcome ==");
        System.out.println("Good morning, anonymous!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice > ");
    }

    /**
     * The method controls the next page to be displayed or action to perform based on the choice inputted by the user.
     * The user may enter 1 to register, 2 to login or 3 to exit the application.
     */
    public void process(){
        char choice;
        
        String nextPage = "";

        while (nextPage.equals("")) {
            display();
            Scanner sc = new Scanner(System.in);
            choice = sc.next().charAt(0);

            switch (choice) {
                case '1':
                    memberController.register();
                    break;
                case '2':
                    nextPage =  memberController.login(session) ? "MainMenu" : "";
                    break;
                case '3':
                    nextPage = "Exit";
                    break;
                default:
                    System.out.println("Enter a choice between 1 to 3");
            }
        }
        session.redirect(nextPage);
    }
}