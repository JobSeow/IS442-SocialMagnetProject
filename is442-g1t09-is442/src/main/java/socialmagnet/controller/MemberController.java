package socialmagnet.controller;

import socialmagnet.dao.FarmerDAO;
import socialmagnet.dao.MemberDAO;
import socialmagnet.game.Farmer;
import socialmagnet.social.Member;
import socialmagnet.view.Session;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This Class represents the MemberController that contains methods related to the Member class such as registering, login, showing a member's list of friends and friend requests, and creating a friend request.
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class MemberController {
    private MemberDAO memberDAO;
    private FarmerDAO farmerDAO;

    /**
     * Create a MemberController with a new instance of a MemberDAO and FarmerDAO objects
     */
    public MemberController(){
        this.memberDAO = new MemberDAO();
        this.farmerDAO = new FarmerDAO();
    }

    /**
     * Registers a member based on their inputted full name, username and password and adds them to the database
     * @param fullname A String representing the user's full name
     * @param username A String representing the user's username
     * @param password A String representing the user's password
     */
    public void registerMemberFactory(String fullname, String username, String password){
        memberDAO.addMemberToDB(fullname, username, password);
        memberDAO.addFarmerToDB(username);
    }

    /**
     * Retrieves and returns a Member object identified by its username
     * @param username A String representing the user's username
     * @return A Member object representing the member retrieved
     */
    public Member retrieveMemberFactory(String username){
        Member member = memberDAO.retrieveByUsername(username);

        ArrayList<String> friendList = memberDAO.retrieveFriendList(username);
        member.setFriendList(friendList);

        Farmer farmer = farmerDAO.retrieveFarmerFactory(username);
        member.setFarmer(farmer);
        return member;
    }

    /**
     * Displays the interface when registering a user and registers the user based on the entered details if the inputs are valid
     */
    public void register() {
        Scanner sc = new Scanner(System.in);
        MemberDAO mDAO = new MemberDAO();

        System.out.println("== Social Magnet :: Register ==");
        System.out.print("Enter your username   >");
        String username = sc.nextLine();

        while (mDAO.existInDB(username)) { // check if username exist in db
            System.out.println("Username exist already");
            System.out.print("Enter your username   >");
            username = sc.nextLine();
        }

        System.out.print("Enter your Full name  >");
        String fullname = sc.nextLine();

        String password = " ";
        String cfmPassword = " ";
        System.out.print("Enter your password   >");
        password = sc.nextLine();

        while (!cfmPassword.equals(password)) { // check if password and cfm pw is the same
            System.out.print("Confirm your password >");
            cfmPassword = sc.nextLine();

            if (!cfmPassword.equals(password)){
                System.out.println("Passwords do not match!");
            }
        }

        // if no prob, then add member into db
        try {
            registerMemberFactory(fullname, username, password);
            System.out.println(username + ", your account is successfully created.");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Displays the login interface and if the inputted credentials are correct, the user is logged in
     * @param session The session object
     * @return true is login is successful, else return false
     */
    public boolean login(Session session) {
        MemberDAO mDAO = new MemberDAO();

        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("== Social Magnet :: Login ==");

        System.out.print("Enter your username   > ");
        String username = sc.nextLine();

        System.out.print("Enter your password   > ");
        String password = sc.nextLine();


        try {
            boolean validLogin = mDAO.checkPassword(username, password);
            if (validLogin){
                Member member = retrieveMemberFactory(username);
                System.out.println("Login success!");
                session.setMember(member);
                return true;
            }else {
                
                throw new NullPointerException();
            }
        } catch (NullPointerException e){
            System.out.println("Invalid username/password");
            System.out.println("Please try to login again");
            
        }
        return false;
    }

    /**
     * Displays the list of friends the specified user has
     * @param username A String representing the user's username
     */    
    public void showFriends(String username){
        ArrayList<String> friendList = memberDAO.retrieveFriendList(username);
        int index = 1;
        for (String friend: friendList){
            System.out.println(index + ". " + friend);
            index += 1;
        }
    }

    /**
     * Displays the list of friend requests the specified user has
     * @param username A String representing the user's username
     */       
    public void showRequests(String username){
        ArrayList<String> friendList = memberDAO.retrieveFriendRequests(username);
        int index = 1;
        for (String friend: friendList){
            System.out.println(index + ". " + friend);
        }
    }

    /**
     * Creates a new friend request
     * @param sender A String representing the sender's username
     */   
    public void createFriendRequest(String sender){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the username> ");
        String receiver = sc.nextLine();

        if (memberDAO.checkFriendship(sender,receiver)){
            System.out.println("Already Friends!");
        } else {
            if (memberDAO.existInDB(receiver)) {
                boolean result = memberDAO.addFriendRequest(sender, receiver);
                if (result) {
                    System.out.println("Friend request sent!");
                }
            } else {
                System.out.println("User does not exist!");
            }
        }
    }

    public Member retrieveByUsername(String username){
        return memberDAO.retrieveByUsername(username);
    }

    public boolean checkFriendship(String username, String username2){
        return memberDAO.checkFriendship(username,username2);
    }

    public ArrayList<String> retrieveFriendList(String username){
        return memberDAO.retrieveFriendList(username);
    }

    public void removeFriendship(String username, String username2){
        memberDAO.removeFriendship(username, username2);
    }

    public ArrayList<String> retrieveFriendRequests(String username){
        return memberDAO.retrieveFriendList(username);
    }

    public void acceptRequest(String sender, String receiver){
        memberDAO.acceptRequest(sender,receiver);
    }

    public void rejectRequest(String sender, String receiver){
        memberDAO.rejectRequest(sender,receiver);
    }
}