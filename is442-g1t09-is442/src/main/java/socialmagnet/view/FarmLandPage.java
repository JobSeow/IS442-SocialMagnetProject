package socialmagnet.view;

import socialmagnet.social.*;
import socialmagnet.game.*;
import socialmagnet.controller.*;

import java.util.*;

/**
 * This Class represents the Farm Land page of the City Farmers game
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class FarmLandPage implements Page{
    private Session session;
    private String nextPage;
    private FarmerController farmerController = new FarmerController();

    /**
     * Creates the Farm Land page 
     * @param session The session object to be assigned to the Farm Land page
     */
    public FarmLandPage(Session session){
        this.session = session;
    }

    /**
     * Gets the full name, rank, gold and maximum number of plots of the logged in member and displays the interface of the Farm Land page
     */
    public void display() {
        Member member = session.getMember();
        Farmer farmer = farmerController.retrieveFarmer(member.getUsername());

        System.out.println("");
        System.out.println("== Social Magnet :: City Farmers :: My FarmLand ==");
        System.out.println("Welcome, " + member.getFullName() + "!");
        System.err.println("Title: " + farmer.getRank() +"       Gold :" + farmer.getGold());
        System.out.println();

        System.out.println("You have " + farmer.getMaxPlots() + " plots of land");

        farmerController.showPlots(member.getUsername());

        System.err.print("[M]ain | City [F]armers | [P]lant | [C]lear | [H]arvest > ");
    }

    /**
     * The method controls the next page to be displayed or actions the user can perform on their land, based on the choice inputted.
     * The user may enter either M or F navigate to the Main Menu and City Farmers pages respectively, or they may enter P to plant crops, C to clear crops or H to harvest crops. 
     */
    public void process() {

        this.nextPage = "";
        char choice;

        while (this.nextPage.equals("")) {
            display();

            Scanner sc = new Scanner(System.in);
            String fullChoice = sc.next();
            choice = fullChoice.charAt(0);

            switch (choice) {
                case 'M':
                    this.nextPage = "MainMenu";
                    break;
                case 'F':
                    this.nextPage = "CityFarmers";
                    break;
                case 'P':
                    if (fullChoice.equals("P")){
                        System.out.print("Enter the plot number > ");
                        String plotNo = sc.next();
                        fullChoice = fullChoice + plotNo;
                    }
                    plantCrop(fullChoice);
                    break;
                case 'C':
                    clearCrops();
                    break;
                case 'H':
                    harvestCrops();
                    break;
                default:
                    System.out.println("Enter M, P, C, or H");
            }
        }
        session.redirect(this.nextPage);
    }

    /**
    * Plants a crop of the user's choice on their chosen plot of land
    * @param choice A String representing the plot of land chosen by the user to plant a crop on
    */
    public void plantCrop(String choice){
        String username = session.getMember().getUsername();

        int id = Character.getNumericValue(choice.charAt(1));

        Map<String, Integer> inventory = farmerController.retrieveInventory(username);
        Map<Integer, String> tempMapping = new HashMap<Integer,String>();

        System.out.println("Select the crop:");

        int index = 1;
        for (String cropName: inventory.keySet()){
            if (inventory.get(cropName) > 0){
                tempMapping.put(index, cropName);
                System.out.println(index + ". " + cropName);
                index += 1;
            }
        }

        Land land = farmerController.retrieveLand(username, id);
        if (land.getCropName() == null){ // check if this plot is empty
            System.out.print("[M]ain | City [F]armers | Select Choice >  ");

            Scanner sc = new Scanner(System.in);
            char action;

            action = sc.next().charAt(0);
            switch (action){
                case 'M':
                    this.nextPage = "MainMenu";
                    break;
                case 'F':
                    this.nextPage = "CityFarmers";
                    break;
                case '1':
                case '2':
                case '3':
                case '4':
                    int chosen = Character.getNumericValue(action);
                    String cropName = tempMapping.get(chosen);
                    farmerController.plantCrop(username, id, cropName);
            }
        } else {
            System.out.println("There is already a crop here.");
        }
    }

    /**
    * Clears crops that have withered from any plot of land
    */
    public void clearCrops(){
        String username = session.getMember().getUsername();
        farmerController.clearCrops(username);
    }

    /**
    * Harvests crops that are ready for harvest from any plot of land
    */
    public void harvestCrops(){
        String username = session.getMember().getUsername();
        farmerController.harvestCrops(username);
    }
}