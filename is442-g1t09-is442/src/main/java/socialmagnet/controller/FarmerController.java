package socialmagnet.controller;

import socialmagnet.dao.*;
import socialmagnet.game.*;
import socialmagnet.social.Member;
import socialmagnet.social.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class represents the FarmerController class that contains the methods representing the actions that the farmer can perform
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class FarmerController {
    private MemberController memberController = new MemberController();
    private CropDAO cropDAO = new CropDAO();
    private LandDAO landDAO = new LandDAO();
    private FarmerDAO farmerDAO = new FarmerDAO();
    private MemberDAO memberDAO = new MemberDAO();

    /**
     * Harvest crops ready to be harvested from the specified user's farm and displays the amount of xp and gold earned from the harvest.
     * @param username A String representing the user's username
     */    
    public void harvestCrops(String username){
        Farmer farmer = farmerDAO.retrieveFarmer(username);
        int maxPlots = farmer.getMaxPlots();

        Map<String, Integer> yield = new HashMap<String, Integer>();
        CropController cropController = new CropController();

        int totalXp = 0;
        int totalGold = 0;

        for (int plotNumber = 1; plotNumber <= maxPlots; plotNumber ++){
            Land land = landDAO.retrieveLand(username, plotNumber);
            if (land.getCurrentStatus() == 2){
                String cropName = land.getCropName();
                Crop crop = cropDAO.retrieveCrop(cropName);

                int harvestSize = cropController.getYield(crop);
                totalXp += harvestSize * crop.getXP();
                totalGold += harvestSize * crop.getSalePrice();
                landDAO.clearLand(username, plotNumber);

                if (!(yield.containsKey(cropName))){
                    yield.put(cropName, harvestSize);
                } else {
                    int currentHarvest = yield.get(cropName);
                    yield.put(cropName, currentHarvest + harvestSize);
                }
            }
        }
        farmerDAO.updateXp(username, farmer.getXp() + totalXp);
        farmerDAO.updateGold(username, farmer.getGold() + totalGold);

        String output = "You have harvested ";
        for (String cropName : yield.keySet()){
            output += yield.get(cropName) + cropName + ",";
        }
        output += " for " + totalXp + " XP and " + totalGold + " gold.";
        System.out.println(output);
    }

    /**
     * Clear crops that have withered from the specified user's farm and displays the amount of gold lost.
     * @param username A String representing the user's username
     */    
    public void clearCrops(String username){
        Farmer farmer = farmerDAO.retrieveFarmer(username);
        int maxPlots = farmer.getMaxPlots();
        int totalGold = 0;

        for (int plotNumber = 1; plotNumber <= maxPlots; plotNumber ++){
            Land land = landDAO.retrieveLand(username,plotNumber);
            if (land.getCurrentStatus() == -1){
                totalGold += 5;
                landDAO.clearLand(username, plotNumber);
            }
        }
        farmerDAO.updateGold(username, farmer.getGold() - totalGold);
        System.out.println("Wilted crops have been removed and you lost " + totalGold + " gold.");
    }

    /**
     * Plant the specified crop identified by its name on the specified plot belonging to the specified user
     * @param username A String representing the user's username
     * @param plotNumber An integer representing the id of the plot to plant the crop on
     * @param cropName A String representing the name of the crop to be planted
     */    
    public void plantCrop(String username, int plotNumber, String cropName){

        Map<String, Integer> inventory = farmerDAO.retrieveInventory(username);

        int currentQuantity = inventory.get(cropName);

        farmerDAO.updateInventory(username, cropName, currentQuantity-1);
        landDAO.plantCrop(username, plotNumber, cropName);

        System.out.println(cropName + " planted on plot " + plotNumber);
    }

    /**
     * Sends a bag of seeds of the specified crop from the specified receiver to the specified sender
     * @param sender A Member object representing the sender
     * @param receiverName A String representing the receiver's username
     * @param cropName A String representing the name of the crop 
     * @return Returns true if the gift is successfully sent, else returns false
     */  
    public boolean sendGift(Member sender, String receiverName, String cropName){
        Member receiver = memberController.retrieveMemberFactory(receiverName);

        Farmer senderFarm = sender.getFarmer();
        Farmer receiverFarm = receiver.getFarmer();
        boolean result = true;

        if (senderFarm.getGiftsSent() < 5){
            Map<String, Integer> senderInventory = senderFarm.getInventory();
            Map<String, Integer> receiverInventory = receiverFarm.getInventory();

            String copyName = sender.getFullName();
            String copyUsername = sender.getUsername();

            int senderCurrentQuantity = senderInventory.get(cropName);
            senderInventory.put(cropName, senderCurrentQuantity-1);

            senderFarm.setInventory(senderInventory);
            farmerDAO.updateInventory(sender.getUsername(), cropName, senderCurrentQuantity-1);

            // put in receiver inventory
            if (receiverInventory.containsKey(cropName)){
                int receiverCurrentQuantity = receiverInventory.get(cropName);
                receiverInventory.put(cropName, receiverCurrentQuantity + 1);
                farmerDAO.updateInventory(receiverName, cropName, receiverCurrentQuantity + 1);
            } else {
                receiverInventory.put(cropName, 1);
                farmerDAO.addToInventory(receiverName, cropName, 1);
            }
            receiverFarm.setInventory(receiverInventory);

            senderFarm.setGiftsSent(senderFarm.getGiftsSent() + 1);
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Displays the plots of land the specified user have and shows if the plot of land is empty. If the plot of land is not empty, the progress of the crop's growth is displayed.
     * @param username A String representing the user's username
     */  
    public void showPlots(String username){
        Farmer farmer = farmerDAO.retrieveFarmer(username);
        int maxPlots = farmer.getMaxPlots();

        LandDAO landDAO = new LandDAO();

        for (int index = 1; index <= maxPlots; index ++){
            Land land = landDAO.retrieveLand(username, index);

            landDAO.updateProgress(land);

            String cropName = land.getCropName();
            String cropString = "";

            if (cropName == null){
                cropString = "<empty>";
            } else {
                cropString = cropName;
            }
            System.out.println(index + ". " + cropString + "   "+ land.getProgressBar());
        }
    }

    /**
     * The method enables the specified thief, identified by his username to steal crops from the victim user, also identified by his username.
     * @param thiefUsername A String representing the thief's username
     * @param victimUsername A String representing the victim's username
     */  
    public void stealCrops(String thiefUsername, String victimUsername){
        Farmer victimFarmer =  farmerDAO.retrieveFarmer(victimUsername);
        Farmer thiefFarmer = farmerDAO.retrieveFarmer(thiefUsername);
        int victimMaxPlots = victimFarmer.getMaxPlots();

        Map<Crop, Integer> yield = new HashMap<Crop, Integer>();
        CropController cropController = new CropController();
        int totalXp = 0;
        int totalGold = 0;

        for (int plotNumber = 1; plotNumber <= victimMaxPlots; plotNumber++){
            Land land = landDAO.retrieveLand(victimUsername, plotNumber);
            if (land.getCurrentStatus() == 2){
                String cropName = land.getCropName();

                Crop crop = cropDAO.retrieveCrop(cropName);

                int harvestSize = cropController.getYield(crop);
                totalXp += harvestSize * crop.getXP();
                totalGold += harvestSize * crop.getSalePrice();
                landDAO.clearLand(victimUsername,plotNumber);

                if (!(yield.containsKey(crop))){
                    yield.put(crop, harvestSize);
                } else {
                    int currentHarvest = yield.get(crop);
                    yield.put(crop, currentHarvest + harvestSize);
                }
            }
        }

        farmerDAO.updateXp(thiefUsername, thiefFarmer.getXp() + totalXp);
        farmerDAO.updateGold(thiefUsername, thiefFarmer.getGold() + totalGold);

        String output = "You have successfully stolen ";
        for (Crop crop : yield.keySet()){
            output += yield.get(crop) + crop.getName() + ",";
        }
        output += " for " + totalXp + " XP and " + totalGold + " gold.";
        System.out.println(output);
    }

    /**
     * The method gets the wealth status of the specified user
     * @param username A String representing the user's username
     * @return A String representing the user's wealth ranking or status in the City Farmers game
     */      
    public String getWealthStatus(String username){
        ArrayList<String> friendList = memberDAO.retrieveFriendList(username);
        int position = 1;
        Farmer farmer = farmerDAO.retrieveFarmer(username);
        int currentGold = farmer.getGold();

        for (String friend: friendList){
            Farmer friendFarmer = farmerDAO.retrieveFarmer(friend);
            int theirGold = friendFarmer.getGold();
            if(theirGold > currentGold){
                position += 1;
            }
        }

        Map<Integer, String> rankMapping = new HashMap<Integer, String>();
        rankMapping.put(1,"Richest");
        rankMapping.put(2,"2nd richest");
        rankMapping.put(3,"3rd richest");

        if (position <= 3){
            return rankMapping.get(position);
        } else {
            return position + "th richest";
        }
    }

    public Farmer retrieveFarmer(String username){
        return farmerDAO.retrieveFarmer(username);
    }

    public Map<String, Integer> retrieveInventory(String username){
        return farmerDAO.retrieveInventory(username);
    }

    public Land retrieveLand(String username, int id){
        return landDAO.retrieveLand(username, id);
    }
}
