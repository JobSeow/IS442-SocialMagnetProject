package socialmagnet.view;

import java.util.*;

/**
 * This Class represents the Main Menu page of the Social Magnet.
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class MainMenuPage implements Page{
    private Session session;

    /**
     * Creates the Main Menu page 
     * @param session The session object to be assigned to the Main Menu page
     */
    public MainMenuPage (Session session){
        this.session = session;
    }

    /**
     * Gets the full name of the logged in user and displays the interface of the Main Menu page
     */
    public void display() {
        System.out.println();
        System.out.println("== Social Magnet :: Main Menu ==");
        System.out.println("Welcome, " + session.getMember().getFullName());
        System.out.println();
        System.out.println("1. News Feed");
        System.out.println("2. My Wall");
        System.out.println("3. My Friends");
        System.out.println("4. City Farmers");
        System.out.println("5. Logout");

        System.out.print("Enter your choice > ");
    }

    /**
     * The method controls the next page to be displayed based on the choice inputted by the user.
     * The user may enter either one of the following choices: 1, 2, 3 or 4 to navigate to the News Feed, My Wall, My Friends or City Farmers pages respectively.
     * Otherwise, they may enter 5 to logout from the Social Magnet and return to the Welcome Page.
     */
    public void process() {
        char choice;
        String nextPage = "";

        display();
        while (nextPage.equals("")) {
            Scanner sc = new Scanner(System.in);
            choice = sc.next().charAt(0);

            switch (choice) {
                case '1':
                    nextPage = "NewsFeed";
                    break;
                case '2':
                    session.setWallVisiting(session.getMember().getUsername());
                    nextPage = "MyWall";
                    break;
                case '3':
                    nextPage = "MyFriends";
                    break;
                case '4': 
                    nextPage = "CityFarmers";
                    break;
                case '5':
                    nextPage = "WelcomePage";
                    break;
                default:
                    System.out.println("Enter a choice between 1 to 5");
            }
        }
        session.redirect(nextPage);
    }
}
