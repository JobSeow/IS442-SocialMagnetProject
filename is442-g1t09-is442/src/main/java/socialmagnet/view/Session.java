package socialmagnet.view;

import socialmagnet.controller.FarmerController;
import socialmagnet.controller.PostController;
import socialmagnet.view.*;
import socialmagnet.social.*;

/**
 * This Class represents the Session class.
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class Session{
    private Member member;

    private String threadOwner;
    private int threadNo;
    private String threadFrom;
    private String wallVisiting;

    private WelcomePage welcomePage;
    private MainMenuPage mainMenuPage;
    private CityFarmersPage cityFarmersPage;
    private StorePage storePage;
    private InventoryPage inventoryPage;
    private FarmLandPage farmLandPage;
    private GiftPage giftPage;
    private VisitPage visitPage;
    private NewsFeedPage newsFeedPage;
    private MyFriendsPage myFriendsPage;
    private MyWallPage myWallPage;
    private ThreadPage threadPage;

    // constructor
    /**
     * Create a Session object and assigns it to all the pages
     */
    public Session(){
        this.welcomePage = new WelcomePage(this);
        this.mainMenuPage = new MainMenuPage(this);
        this.cityFarmersPage = new CityFarmersPage(this);
        this.storePage = new StorePage(this);
        this.inventoryPage = new InventoryPage(this);
        this.farmLandPage = new FarmLandPage(this);
        this.newsFeedPage = new NewsFeedPage(this);
        this.myFriendsPage = new MyFriendsPage(this);
        this.myWallPage = new MyWallPage(this);
        this.threadPage = new ThreadPage(this);
        this.giftPage = new GiftPage(this);
        this.visitPage = new VisitPage(this);
    }

    //getters
    /**
     * Gets the Member object 
     * @return A Member object
     */
    public Member getMember(){return member;}

    /**
     * Gets the Thread Owner
     * @return A String representing the thread owner
     */
    public String getThreadOwner() {
        return threadOwner;
    }

    /**
     * Gets the Thread number
     * @return An integer representing the thread number
     */
    public int getThreadNo() {
        return threadNo;
    }

    /**
     * Gets where the thread is from, either from the News Feed or from the Wall
     * @return A String representing where the thread comes from
     */
    public String getThreadFrom() { return threadFrom; }

    public String getWallVisiting() {return wallVisiting; }

    //setters
    /**
     * Sets the Member object 
     * @param member The Member object to set as
     */
    public void setMember(Member member){
        this.member = member;
//        createTestCases(); // for testing
    }

    /**
     * Sets the Thread Owner
     * @param threadOwner A String representing the owner of the thread to set as
     */
    public void setThreadOwner(String threadOwner) {
        this.threadOwner = threadOwner;
    }

    /**
     * Sets the Thread Number
     * @param threadNo An integer representing the thread number to set as
     */
    public void setThreadNo(int threadNo) {
        this.threadNo = threadNo;
    }

    /**
     * Sets where the thread is from
     * @param threadFrom A String representing where the thread is from to set as
     */
    public void setThreadFrom(String threadFrom) {
        this.threadFrom = threadFrom;
    }

    public void setWallVisiting(String wallVisiting) {this.wallVisiting = wallVisiting; }

    // methods
    /**
     * The method displays the Welcome page
     */
    public void start(){
        welcomePage.process();
    }

    /**
     * The method controls which page to redirect the user to 
     * @param nextPage A String representing the page to be redirected to
     */
    public void redirect(String nextPage){
        switch (nextPage){
            case "Exit":
                end();
                break;
            case "WelcomePage":
                logout();
                welcomePage.process();
                break;
            case "MainMenu":
                mainMenuPage.process();
                break;
            case "CityFarmers":
                cityFarmersPage.process();
                break;
            case "FarmLand":
                farmLandPage.process();
                break;
            case "Store":
                storePage.process();
                break;
            case "Inventory":
                inventoryPage.process();
                break;
            case "MyWall":
                myWallPage.process();
                break;
            case "Gift":
                giftPage.process();
                break;
            case "Visit":
                visitPage.process();
                break;
            case "NewsFeed":
                newsFeedPage.process();
                break;
            case "Thread":
                threadPage.process();
                break;
            case "MyFriends":
                myFriendsPage.process();
                break;
        }
    }

    /**
     * The method creates Test cases
     */
    public void createTestCases(){
        FarmerController farmerController = new FarmerController();
        farmerController.plantCrop("job", 1, "Papaya");
        farmerController.plantCrop("job", 2, "Papaya");
        farmerController.plantCrop("job", 3, "Watermelon");

        PostController postController = new PostController();
        postController.addNewPost("Hello daryl", "job","daryl");
        postController.addNewPost("Hi im daryl", "daryl","job");
        postController.addNewPost("We are taking OOP @daryl @job", "tlz","tlz");
    }

    /**
     * The method prints "Goodbye!" when the session ends
     */
    public void end(){
        System.out.println("Goodbye!");
    }

    /**
     * The method logs the user out and sets the member object as null
     */
    public void logout(){
        this.member = null;
    }
}