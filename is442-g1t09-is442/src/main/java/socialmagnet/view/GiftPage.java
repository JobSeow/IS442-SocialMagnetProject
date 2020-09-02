package socialmagnet.view;

import socialmagnet.controller.FarmerController;
import socialmagnet.controller.MemberController;
import socialmagnet.social.*;
import socialmagnet.game.*;

import java.util.*;

/**
 * This Class represents the Gift page of the City Farmers game
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class GiftPage implements Page{
    private Session session;
    private Map<Integer, String> cropMapping = new HashMap<Integer, String>();
    private MemberController memberController = new MemberController();
    private FarmerController farmerController = new FarmerController();

    /**
     * Creates the Gift page 
     * @param session The session object to be assigned to the Gift page
     */
    public GiftPage(Session session){
        this.session = session;
    }

    /**
     * Gets the username, rank and gold of the logged in member and displays the interface of the Gift page
     */
    public void display() {
        Member member = session.getMember();
        Farmer farmer = farmerController.retrieveFarmer(member.getUsername());

        System.out.println("");
        System.out.println("== Social Magnet :: City Farmers :: Send a Gift ==");
        System.out.println("Welcome, " + member.getFullName() + "!");
        System.err.println("Title: " + farmer.getRank() +"       Gold :" + farmer.getGold());
        System.out.println();
        System.out.println("     Gifts Available:");

        showGiftsAvailable();

        System.err.print("[R]eturn to main | Select choice > ");
    }
    
    /**
     * According to the user's choice, the method enables users to return to the City Farmers page and controls the gift to be sent to the user's friends.
     * The user may enter R to return to the City Farmers page or enter 1, 2, 3 or 4 to choose a gift to send to their friends.
     */
    public void process() {

        char choice;
        String nextPage = "";

        while (nextPage.equals("")) {
            display();
            Scanner sc = new Scanner(System.in);
            choice = sc.next().charAt(0);

            switch (choice) {
                case 'R':
                    nextPage ="MainMenu";
                    break;
                case '1':
                case '2':
                case '3':
                case '4':
                    sendToFriends(choice);
                    break;
                default:
                    System.out.println("Enter 1, 2, 3, 4 or M");
            }
        }
        session.redirect(nextPage);
    }

    /**
     * The method prints out the list of gifts available to be sent to a friend
     */
    public void showGiftsAvailable(){
        Member member = session.getMember();
        Map<String, Integer> inventory = member.getFarmer().getInventory();

        int index = 1;
        for (String cropName : inventory.keySet()){
            cropMapping.put(index, cropName);
            System.out.println(index + ". 1 Bag of "+ cropName + " Seeds");
            index += 1;
        }
    }

    /**
     * Sends the gift of the user's choice to his selected friends
     * @param choice A character representing the choice of gift the user selected
     */
    public void sendToFriends(char choice){
        FarmerController farmerController = new FarmerController();
        int intChoice = Character.getNumericValue(choice);

        Member member = session.getMember();

        String cropChosen = cropMapping.get(intChoice);

        Scanner sc = new Scanner(System.in);
        System.out.print("Send to> ");
        String sendTo = sc.nextLine();

        boolean result = true;

        String[] sendToArray = sendTo.split(",");
        for (String username : sendToArray){
            String receiver = null;
            if (memberController.checkFriendship(username, member.getUsername())){
                receiver = username;
            }
            result = farmerController.sendGift(member, receiver, cropChosen);
            if (!result){
                break;
            }
        }

        if (result){
            System.out.println("Gift posted to your friends' wall");
        } else {
            System.out.println("You have hit your gift limit");
        }
    }
}