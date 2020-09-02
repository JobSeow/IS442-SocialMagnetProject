package socialmagnet.view;

import socialmagnet.controller.FarmerController;
import socialmagnet.controller.MemberController;
import socialmagnet.game.Farmer;
import socialmagnet.social.*;

import java.util.*;

/**
 * This Class represents the Visit page
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class VisitPage implements Page {
    private Session session;
    private Map<Integer, String> friendMapping = new HashMap<Integer, String>();
    private FarmerController farmerController;
    private Member victim;
    private String nextPage = "";
    private MemberController memberController;

    /**
     * Creates the Visit page 
     * @param session The session object to be assigned to the Visit page
     */
    public VisitPage(Session session){
        this.session = session;
        this.memberController = new MemberController();
        this.farmerController = new FarmerController();
    }

    /**
     * Gets the full name, rank and gold of the logged in member and displays the interface of the Visit page
     */
    public void display() {
        Member member = session.getMember();
        Farmer farmer = farmerController.retrieveFarmer(member.getUsername());

        System.out.println("");
        System.out.println("== Social Magnet :: City Farmers :: Visit Friend ==");
        System.out.println("Welcome, " + member.getFullName() + "!");
        System.err.println("Title: " + farmer.getRank() +"       Gold :" + farmer.getGold());
        System.out.println();

        showFriends();
        
        System.err.print("[M]ain | [C]ity Farmer Main | Select choice > ");
        System.out.println();
    }

    /**
     * The method controls the next page to be displayed or action to perform based on the choice inputted by the user.
     * The user may enter M or C to navigate to the Main Menu or City Farmers pages respectively.
     * Otherwise, the user may the id number of their listed friend to visit their chosen friend's farm
     */
    public void process() {
        this.nextPage = "";

        char choice;

        while (nextPage.equals("")) {
            display();
            Scanner sc = new Scanner(System.in);
            choice = sc.next().charAt(0);

            switch (choice) {
                case 'M':
                    this.nextPage ="MainMenu";
                    break;
                case 'C':
                    this.nextPage ="CityFarmers";
                    break;
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                    visitFarm(choice);
                    break;  
                default:
                    System.out.println("Enter M, C or a valid friend");
            }
        }
        session.redirect(this.nextPage);
    }

    /**
     * The method retrieves the user's list of friends and displays them
     */
    public void showFriends(){
        Member member = session.getMember();
        ArrayList<String> friendList = memberController.retrieveFriendList(session.getMember().getUsername());

        System.out.println("My Friends: ");

        int index = 1;
        for (String friendName : friendList){
            Member friend = memberController.retrieveMemberFactory(friendName);
            System.out.println(index + ". " + friend.getFullName() + " (" + friendName + ")");
            friendMapping.put(index,friendName);
            index += 1;
        }
    }

    /**
     * The method enables the user to visit his chosen friend's farm.
     * At the friend's farm, a user can enter M or F to return to the Main Menu or City Farmers pages respectively.
     * Otherwise, they can enter S to steal from their friend's farm
     * @param choice A character representing the choice made by the user
     */
    public void visitFarm(char choice){
        char action;
        Member member = session.getMember();

        while (nextPage.equals("")) {
            visitFarmDisplay(choice);
            Scanner sc = new Scanner(System.in);
            action = sc.next().charAt(0);

            switch (action) {
                case 'M':
                    this.nextPage ="MainMenu";
                    break;
                case 'F':
                    this.nextPage ="CityFarmers";
                    break;
                case 'S':
                    farmerController.stealCrops(member.getUsername(),victim.getUsername());
                    break;
                default:
                    System.out.println("M, F or S");
            }
        }
    }

    /**
     * The method enables the user to visit his chosen friend's farm.
     * At the friend's farm, a user can enter M or F to return to the Main Menu or City Farmers pages respectively.
     * Otherwise, they can enter S to steal from their friend's farm.
     * @param choice A character representing the choice made by the user
     */
    public void visitFarmDisplay(char choice){
        int intChoice = Character.getNumericValue(choice);
        String friendName = friendMapping.get(intChoice);

        Member friend = memberController.retrieveMemberFactory(friendName);
        this.victim = friend;

        System.out.println("Name: " + friend.getFullName());
        System.out.println("Title: " + friend.getFarmer().getRank());
        System.out.println("Gold: " + friend.getFarmer().getGold());

        farmerController.showPlots(friend.getUsername());

        System.out.println("[M]ain | City [F]armers | [S]teal > ");
    }
}