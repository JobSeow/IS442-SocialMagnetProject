package socialmagnet.view;

import socialmagnet.controller.FarmerController;
import socialmagnet.game.Farmer;
import socialmagnet.social.*;

import java.util.*;

/**
 * This Class represents the City Farmers page
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class CityFarmersPage implements Page {
    private Session session;
    private FarmerController farmerController = new FarmerController();

    /**
     * Creates the City Farmers page 
     * @param session The session object to be assigned to the City Farmers page
     */
    public CityFarmersPage(Session session){
        this.session = session;
    }

    /**
     * Gets the username and rank of the logged in member and displays the interface of the City Farmers page
     */
    public void display() {
        Member member = session.getMember();
        Farmer farmer = farmerController.retrieveFarmer(member.getUsername());

        System.out.println("");
        System.out.println("== Social Magnet :: City Farmers ==");
        System.out.println("Welcome, " + member.getFullName() + "!");
        System.err.println("Title: " + farmer.getRank() +"       Gold :" + farmer.getGold());
        System.out.println();
        System.out.println("1. My Farmland");
        System.err.println("2. My Store");
        System.err.println("3. My Inventory");
        System.err.println("4. Visit Friend");
        System.err.println("5. Send Gift");

        System.err.print("[M]ain | Enter your choice > ");
    }
    

    /**
     * The method controls the next page to be displayed based on the choice inputted by the user.
     * The user may enter either one of the following choices: M, 1, 2, 3, 4 or 5 to navigate to the Main Menu, Farmland, Store, Inventory, Visit or Gift pages respectively
     */
    public void process() {
        char choice;
        String nextPage = "";

        display();
        while (nextPage.equals("")) {
            Scanner sc = new Scanner(System.in);
            choice = sc.next().charAt(0);

            switch (choice) {
                case 'M':
                    nextPage ="MainMenu";
                    break;
                case '1':
                    nextPage ="FarmLand";
                    break;
                case '2':
                    nextPage ="Store";
                    break;
                case '3':
                    nextPage ="Inventory";
                    break;
                case '4':
                    nextPage ="Visit";
                    break;
                case '5':
                    nextPage ="Gift";
                    break;  
                default:
                    System.out.println("Enter 1, 2, 3, 4, 5 or M");
            }
        }
        session.redirect(nextPage);
    }

}