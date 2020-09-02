package socialmagnet.controller;

import java.util.*;
import socialmagnet.dao.*;
import socialmagnet.game.*;
import socialmagnet.social.Member;

/**
 * This Class represents the StoreController that contains methods related to the Store class.
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class StoreController {
    private Store store;
    private FarmerDAO farmerDAO;

    /**
     * Create a StoreController
     */
    public StoreController(){
        CropDAO cropDAO = new CropDAO();
        ArrayList<Crop> cropList = cropDAO.readCropList();
        this.store = new Store(cropList);
        this.farmerDAO = new FarmerDAO();
    }

    /**
     * Gets the Store object
     * @return A Store object
     */
    public Store getStore(){
        return store;
    }

    /**
     * Updates a Farmer's inventory and gold when a purchase specifying the crop and quantity bought is made
     * @param member The Member object that made the purchase
     * @param crop The Crop object to be purchased
     * @param quantity An integer representing the quantity of the crop purchased
     * @return true if the purchase is successful, else false is returned.
     */
    public boolean purchaseCrop(Member member, Crop crop, int quantity){
        Farmer farmer = member.getFarmer();
        if (hasSufficientGold(farmer, crop, quantity)){
            Map<String, Integer> currentInventory = farmer.getInventory();
            if (currentInventory.containsKey(crop.getName())){
                int currentQuantity = currentInventory.get(crop.getName());
                currentInventory.put(crop.getName(),currentQuantity + quantity);
                farmerDAO.updateInventory(member.getUsername(),crop.getName(), currentQuantity + quantity);
            } else {
                currentInventory.put(crop.getName(), quantity);
                farmerDAO.addToInventory(member.getUsername(),crop.getName(), quantity);
            }

            farmer.reduceGold(crop.getCost()*quantity);
            farmerDAO.updateGold(member.getUsername(), farmer.getGold());
            return true;
        }
        return false;
    }

    /**
     * Checks if a farmer has sufficient amount of gold for the purchase
     * @param farmer The Farmer object making the purchase
     * @param crop The Crop object to be purchased
     * @param quantity An integer representing the quantity of the crop purchased
     * @return true if the farmer has sufficient gold, else false is returned
     */
    public boolean hasSufficientGold(Farmer farmer, Crop crop, int quantity){
        int currentGold = farmer.getGold();
        int totalPrice = crop.getCost() * quantity;
        return currentGold >= totalPrice;
    }

    /**
     * Displays the stocks the store has
     */
    public void showStocks(){
        Store store = getStore();
        ArrayList<Crop> stocks = store.getStocks();

        int index = 1;
        for (Crop crop : stocks){
            System.out.println(index + ". " + crop.getName() + " - " + crop.getCost() + " gold");
            System.out.println("   Harvest in: " + crop.getTime() + " mins");
            System.out.println("   XP Gained: " + crop.getXP());

            index +=1;
        }
    }
}