package socialmagnet.game;

import socialmagnet.controller.FarmerController;
import socialmagnet.dao.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FarmerTest {

    FarmerDAO farmerDAO = new FarmerDAO();
    LandDAO landDAO = new LandDAO();
    FarmerController farmerController = new FarmerController();

    @Test
    public void retrieveFarmerTest(){
        Farmer farmer = farmerDAO.retrieveFarmer("daryl");
        assertEquals(99999, farmer.getGold());
    }

    @Test
    public void testWealth(){
        String wealth = farmerController.getWealthStatus("daryl");
        assertEquals("Richest", wealth);
    }

//    @Test
//    public void retrieveInventoryTest(){
//        Map<String,Integer> inventory = farmerDAO.retrieveInventory("daryl");
//        int qty = inventory.get("Papaya");
//        assertEquals(9999, qty);
//    }

    @Test
    public void testMapping(){
        Map<Integer,String> rankNameXpMapping = farmerDAO.readRankNameXpMapping();
        assertEquals("Apprentice", rankNameXpMapping.get(1000));
    }
//
//    @Test
//    public void farmerFactoryTest(){
//        Farmer darylFarmer = farmerDAO.retrieveFarmerFactory("daryl");
//        int qty = darylFarmer.getInventory().get("Sunflower");
//        assertEquals(9999, qty);
//    }

//    @Test
//    public void updateRankNameTest(){
//        String username = "daryl";
//        Farmer farmer = farmerDAO.retrieveFarmer(username);
//        farmer.setXP(6000); // should be Grandmaster
//        farmerDAO.updateRankAndPlots(username, farmer);
//        assertEquals("Grandmaster", farmer.getRank());
//    }
//
//    @Test
//    public void testPlantCrop(){
//        landDAO.plantCrop("daryl", 1, "Papaya" );
//        Land land = landDAO.retrieveLand("daryl", 1);
//        assertEquals("Papaya",land.getCropName());
//    }
//
//    @Test
//    public void testFarmerPlantCrop(){
//        farmerController.plantCrop("daryl", 2, "Sunflower");
//        Land land = landDAO.retrieveLand("daryl", 2);
//        assertEquals("Sunflower",land.getCropName());
//    }
}