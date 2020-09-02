package socialmagnet.game;

import java.util.*;

/**
 * This Class represents the farmer in the City Farmers Game
 * @version 25th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class Farmer {
    private int gold;
    private Map<String, Integer> inventory = new HashMap<String,Integer>();
    private int xp;
    private String rank;
    private int maxPlots;
    private int giftsSent = 0;

    //getter

    /** Gets the amount of gold the farmer has
     * @return An integer representing the amount of gold
     */
    public int getGold(){return gold;}

    /** Gets the inventory of the farmer
     * @return A Map object representing the inventory
     */
    public Map<String, Integer> getInventory(){return inventory;} // not yet

    /** Gets the xp of the farmer
     * @return An integer representing the amount of xp the farmer has
     */
    public int getXp(){return xp;}

    /** Gets the rank of the farmer
     * @return An String representing the rank of the farmer
     */
    public String getRank(){return rank;}

    /** Gets the maximum number of plots the farmer can own
     * @return An integer representing the maximum number of plots the farmer can own
     */
    public int getMaxPlots(){return maxPlots;}

    /** Gets the minimum number of plots the farmer can own
     * @return An integer representing the minimum number of plots the farmer can own
     */
    public int getGiftsSent(){return giftsSent;}

    //setter
    /** Sets the amount of gold the farmer has
     * @param gold An integer representing the amount of gold to set as
     */
    public void setGold(int gold){
        this.gold = gold;
    }

    /** Increase the amount of gold by the specified amount 
     * @param gold An integer representing the amount of gold to increase by
     */
    public void addGold(int gold){
        this.gold += gold;
    }

    /** Reduce the amount of gold by the specified amount 
     * @param gold An integer representing the amount of gold to reduce by
     */
    public void reduceGold(int gold){
        this.gold -= gold;
    }

    /** Sets the inventory of the farmer
     * @param inventory A Map object representing the inventory to set as
     */
    public void setInventory(Map<String, Integer> inventory){
        this.inventory = inventory;
    }

    /** Increase the amount of xp by the specified amount 
     * @param xp An integer representing the amount of xp to increase by
     */
    public void addXp(int xp){
        this.xp += xp;
    }

    /** Sets the amount of xp
     * @param xp An integer representing the amount of xp to set as
     */
    public void setXP(int xp){
        this.xp = xp;
    }

    /** Sets the rank of the farmer
     * @param rank A String representing the rank to set as
     */
    public void setRank(String rank){
        this.rank = rank;
    }

    /** Sets the maximum number of plots 
     * @param maxPlots An integer representing the maximum number of plots to set as
     */
    public void setMaxPlots(int maxPlots){
        this.maxPlots = maxPlots;
    }

    /** Sets the number of gifts sent
     * @param giftsSent An integer representing the number of gifts sent to set as
     */
    public void setGiftsSent(int giftsSent){
        this.giftsSent = giftsSent;
    }
}
