package socialmagnet.view;

import socialmagnet.controller.FarmerController;
import socialmagnet.social.*;
import socialmagnet.game.*;

import java.util.*;

/**
 * This Class represents the Inventory page of the City Farmers game
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class InventoryPage implements Page{
    private Session session;
    private FarmerController farmerController = new FarmerController();

    /**
     * Creates the Inventory page 
     * @param session The session object to be assigned to the Inventory page
     */
    public InventoryPage(Session session){
        this.session = session;
    }

    /**
     * Gets the full name, rank, gold and inventory of the logged in member and displays the interface of the Inventory page
     */
    public void display() {
        Member member = session.getMember();
        Farmer farmer = farmerController.retrieveFarmer(member.getUsername());

        System.out.println("");
        System.out.println("== Social Magnet :: City Farmers :: My Inventory ==");
        System.out.println("Welcome, " + member.getFullName() + "!");
        System.err.println("Title: " + farmer.getRank() +"       Gold :" + farmer.getGold());
        System.out.println();
        
        System.out.println("      My seeds:");
        
        showInventory();

        System.out.print("[M]ain | [C]ity Farmers | Select choice > ");
    }
    
    /**
     * According to the user's choice, the method enables users to return to the Main Menu page or City Farmers page.
     * The user may enter M to return to the Main Menu or enter C to return to the City Farmer's page
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
                    nextPage = "MainMenu";
                    break;
                case 'C':
                    nextPage = "CityFarmers";
                    break;
                default:
                    System.out.println("Enter M or C");
            }
        }
        session.redirect(nextPage);
    }

    /**
     * The method prints out the list of seeds and their quantity owned by the user
     */
    public void showInventory(){
//        Farmer farmer = session.getMember().getFarmer();
        Member member = session.getMember();
        Map<String, Integer> inventory = farmerController.retrieveInventory(member.getUsername());
        int index = 1;

        for (String cropName : inventory.keySet()){
            System.out.println(index + ". " + inventory.get(cropName) + " Bags of " + cropName);
            index += 1;
        }
    }
}