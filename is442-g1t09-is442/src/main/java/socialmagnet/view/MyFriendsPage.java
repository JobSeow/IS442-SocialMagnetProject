package socialmagnet.view;

import socialmagnet.controller.MemberController;

import java.util.*;

/**
 * This Class represents the My Friends page of the Social Magnet
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class MyFriendsPage {
    private Session session;
    private MemberController memberController = new MemberController();

    /**
     * Creates the My Friends page 
     * @param session The session object to be assigned to the My Friends page
     */
    public MyFriendsPage(Session session){
        this.session = session;
    }

    /**
     * Gets the full name, friends and friend requests of the logged in user and displays the interface of the My Friends page
     */
    public void display() {
        System.out.println("");
        System.out.println("== Social Magnet :: My Friends ==");
        System.out.println("Welcome, " + session.getMember().getFullName() + "!");
        System.err.println("My Friends: ");
        memberController.showFriends(session.getMember().getUsername());
        System.err.println("");

        System.out.println("My Requests: ");
        memberController.showRequests(session.getMember().getUsername());

        System.err.print("[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject | [V]iew  > ");
    }
    
    /**
     * According to the user's choice, the method enables users to return to the Main Menu page, unfriend a user, send, accept or reject a friend request or view a friend's wall. 
     * The user may enter M to return to the Main Menu page.
     * Additionally, the user may enter Q to send a request or V to view a friend's wall.
     * Otherwise, U, A or R followed by the id number of the listed friend or request to unfriend a friend, accept or reject a friend request respectively
     */
    public void process() {
        Scanner sc = new Scanner(System.in);
        char choice;
        String nextPage = "";
        while (nextPage.equals("")) {
            display();
            String fullChoice = sc.next();
            choice = fullChoice.charAt(0);
            switch (choice) {
                case 'M':
                    nextPage = "MainMenu";
                    break;
                case 'U':
                    if (fullChoice.equals("U")){
                        System.out.print("Enter the friend number > ");
                        String friendNo = sc.next();
                        fullChoice = fullChoice + friendNo;
                    }
                    unfriend(fullChoice);
                    break;
                case 'Q':
                    memberController.createFriendRequest(session.getMember().getUsername());
                    break;
                case 'A':
                    if (fullChoice.equals("A")){
                        System.out.print("Enter the request number > ");
                        String friendNo = sc.next();
                        fullChoice = fullChoice + friendNo;
                    }
                    acceptFriend(fullChoice);
                    break;
                case 'R':
                    if (fullChoice.equals("R")){
                        System.out.print("Enter the request number > ");
                        String friendNo = sc.next();
                        fullChoice = fullChoice + friendNo;
                    }
                    rejectFriend(fullChoice);
                    break;
                case 'V':
                    if (fullChoice.equals("V")){
                        System.out.print("Enter the friend number > ");
                        String friendNo = sc.next();
                        fullChoice = fullChoice + friendNo;
                    }
                    redirectToWall(fullChoice);
                    nextPage = "MyWall";
                    break;
                default:
                    System.out.println("Enter M, U, Q, A, R or V");
            }

        } 
        session.redirect(nextPage);
    }

    /**
     * The method removes the selected user from the user's friend list
     * @param input A String representing the id of the friend to unfriend
     */
    public void unfriend(String input){
        ArrayList<String> friendList = memberController.retrieveFriendList(session.getMember().getUsername());
        int id = Character.getNumericValue(input.charAt(1)) -1;
        memberController.removeFriendship(session.getMember().getUsername(), friendList.get(id));
        System.out.println(friendList.get(id) + " removed from friend list");
    }

    /**
     * The method accepts the friend request and both the user and sender are now friends
     * @param input A String representing the id of the friend who sent the friend request
     */
    public void acceptFriend(String input){
        ArrayList<String> requestList = memberController.retrieveFriendRequests(session.getMember().getUsername());
        try{
            int id = Character.getNumericValue(input.charAt(1));

            memberController.acceptRequest(requestList.get(id-1), session.getMember().getUsername());
            System.out.println(requestList.get(id-1) + " added as friend!");
        } catch (java.lang.IndexOutOfBoundsException e ){
            System.out.println("Enter a valid request!");
        }
    }

    /**
     * The method rejects the friend request and removes it from the list of friend requests
     * @param input A String representing the id of the friend who sent the friend request
     */
    public void rejectFriend(String input){
        ArrayList<String> requestList = memberController.retrieveFriendRequests(session.getMember().getUsername());
        try {
            int id = Character.getNumericValue(input.charAt(1));

            memberController.rejectRequest(requestList.get(id-1), session.getMember().getUsername());
            System.out.println(requestList.get(id-1) + " rejected as friend!");
        } catch (java.lang.IndexOutOfBoundsException e ){
            System.out.println("Enter a valid request!");
        }
    }

    public void redirectToWall(String input){
        int friendNo = Character.getNumericValue(input.charAt(1));
        friendNo -= 1;
        ArrayList<String> friendList = memberController.retrieveFriendList(session.getMember().getUsername());

        String friend = friendList.get(friendNo);
        session.setWallVisiting(friend);
        session.setThreadFrom("MyWall");
    }
}