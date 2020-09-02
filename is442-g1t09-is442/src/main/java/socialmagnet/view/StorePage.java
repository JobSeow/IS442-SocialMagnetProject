package socialmagnet.view;

import socialmagnet.controller.FarmerController;
import socialmagnet.controller.StoreController;
import socialmagnet.social.*;
import socialmagnet.game.*;

import java.util.*;

/**
 * This Class represents the Store page of City Farmers game
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class StorePage implements Page {
    private Session session;
    private StoreController storeController;
    private FarmerController farmerController = new FarmerController();

    /**
     * Creates the Store page 
     * @param session The session object to be assigned to the Store page
     */      
    public StorePage(Session session){
        this.session = session;
        this.storeController = new StoreController();
    }

    /**
     * Gets the full name, rank and gold of the logged in member and displays the interface of the My Store page
     */    
    public void display() {
        Member member = session.getMember();
        Farmer farmer = farmerController.retrieveFarmer(member.getUsername());

        System.out.println("");
        System.out.println("== Social Magnet :: City Farmers :: My Store ==");
        System.out.println("Welcome, " + member.getFullName() + "!");
        System.err.println("Title: " + farmer.getRank() +"       Gold :" + farmer.getGold());
        System.out.println();
        
        System.out.println("Seeds Available: ");

        storeController.showStocks();

        System.out.print("[M]ain | [C]ity Farmers | Select choice > ");
    }
    
    /**
     * The method controls the next page to be displayed or action to perform based on the choice inputted by the user.
     * The user may enter M or C to navigate to the Main Menu or City Farmers page respectively.
     * Otherwise, they may enter 1, 2, 3 or 4 to make a purchase
     */    
    public void process() {
        char choice;
        String nextPage = "";

        while (nextPage.equals("")) {
            display();
            Scanner sc = new Scanner(System.in);
            choice = sc.next().charAt(0);

            switch (choice) {
                case 'M':
                    nextPage = "MainMenu";
                    break;
                case 'C':
                    nextPage = "CityFarmers";
                    break;
                case '1':
                case '2':
                case '3':
                case '4':
                    newOrder(choice);
                    break;
                default:
                    System.out.println("Enter M, C, or 1-4");
            }
        }
        session.redirect(nextPage);
    }

    /**
     * The method enables the user to purchase the seed of their choice and specify the quanity they wish to purchase, within the limits of the amount of gold they have. 
     * If they do not have enough gold to make their puchase, "Not enough money" will be printed.
     * @param choice A character representing the choice made by the user    
     */
    public void newOrder(char choice){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter quantity > ");

        Store store = storeController.getStore();
        Member member = session.getMember();
        int quantity = sc.nextInt();
        int intChoice = Character.getNumericValue(choice) -1 ;
        Crop crop = store.getStocks().get(intChoice);
        boolean result = storeController.purchaseCrop(member, crop, quantity);
        int cost = quantity * crop.getCost();

        if (result) {
            System.out.println(quantity + " bags of "+ crop.getName() +" seeds purchase for " + cost + " gold.");
        } else {
            System.out.println("Not enough money!");
        }
    }
}