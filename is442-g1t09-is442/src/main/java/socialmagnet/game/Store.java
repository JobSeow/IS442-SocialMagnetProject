package socialmagnet.game;

import java.util.*;

/**
 * This Class represents the crop in the City Farmers Game
 * @version 29th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class Store{
    ArrayList<Crop> stocks = new ArrayList<Crop>();

    /**
     * Create a Store and initialise it with a list of crops
     * @param stocks An ArrayList of Crop objects representing the list of stocks 
     */
    public Store(ArrayList<Crop> stocks){
        this.stocks = stocks;
    }

    /** Gets the stock
     * @return The list of stocks in the store
     */
    public ArrayList<Crop> getStocks(){return stocks;}

    /** Sets the stock
     * @param stocks ArrayList of Crop objects representing the list of stocks to set as
     */
    public void setStocks(ArrayList<Crop> stocks){
        this.stocks = stocks;
    }
}