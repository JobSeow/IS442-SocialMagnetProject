package socialmagnet.game;

/**
 * This Class represents the crop in the City Farmers Game
 * @version 25th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class Crop {
    private String name;
    private int cost;
    private int time; // time to grow to maturity
    private int xp;
    private int minYield;
    private int maxYield;
    private int salePrice;

    /**
     * Create a crop
     */
    public Crop(){
    }

    /** Gets the crop's name
     * @return The crop's name
     */
    public String getName(){return name;}

    /** Gets the crop's cost
     * @return The crop's cost
     */
    public int getCost(){return cost;}

    /** Gets the crop's time taken to mature
     * @return The crop's time taken to mature
     */
    public int getTime(){return time;}

    /** Gets the amount of xp generated from each unit of produce
     * @return The amount of xp
     */
    public int getXP(){return xp;}

    /** Gets the min yield of the crop
     * @return The min yield of the crop
     */
    public int getMinYield(){return minYield;}

    /** Gets the max yield of the crop
     * @return The max yield of the crop
     */
    public int getMaxYield(){return maxYield;}

    /** Gets the sale price of the crop
     * @return The sale price of the crop
     */
    public int getSalePrice(){return salePrice;}

    /** Sets the crop's name
     * @param name A String representing the crop name to set as
     */
    public void setName(String name){
        this.name = name; 
    }

    /** Sets the crop's cost
     * @param cost An integer representing the cost to set as
     */
    public void setCost(int cost){
        this.cost = cost;
    }

    /** Sets the crop's time taken to mature
     * @param time An integer representing the time taken to set as
     */
    public void setTime(int time){
        this.time = time;
    }

    /** Sets the amount of xp generated from each unit of produce
     * @param xp An integer representing the amount of xp to set as
     */
    public void setXP(int xp){
        this.xp = xp;
    }
    
    /** Sets the min yield of the crop
     * @param minYield An integer representing the minimum yield to be set as
     */
    public void setMinYield(int minYield){
        this.minYield = minYield;
    }

    /** Sets the max yield of the crop
     * @param maxYield An integer representing the maximum yield to be set as
     */
    public void setMaxYield(int maxYield){
        this.maxYield = maxYield;
    }

    /** Sets the sale price of the crop
     * @param salePrice An integer representing the sale price to set as
     */
    public void setSalePrice(int salePrice){
        this.salePrice = salePrice;
    }
}