package socialmagnet.social;

/**
 * This Class represents the reply in Social Magnet
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class Reply extends Text{
    private String postedTo;

    /**
     * Create a Reply with the specified message, poster's username and receiver's username
     * @param message A String representing the reply message
     * @param posterUsername A String representing the poster's username
     * @param receiverUsername A String representing the receiver's username
     */ 
    public Reply(String message, String posterUsername, String receiverUsername){
        super(message, posterUsername,receiverUsername);
    }

    /**
     * Gets the receiver's username
     * @return A String representing the receiver's username
     */ 
    public String getPostedTo() {
        return postedTo;
    }

    /**
    * Sets the receiver the reply is posted to
    * @param postedTo A String representing the receiver's username
    */
    public void setPostedTo(String postedTo) {
        this.postedTo = postedTo;
    }
}