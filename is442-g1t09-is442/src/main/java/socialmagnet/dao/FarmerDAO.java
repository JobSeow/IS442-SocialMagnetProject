package socialmagnet.dao;

import socialmagnet.game.*;
import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * This Class represents the Farmer DAO.
 * Initialised using values from the SQL Database
 * @version 25th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class FarmerDAO {
    private DBController controller;
    private Map<Integer, String> rankNameXpMapping = new HashMap<Integer,String>();
    private Map<Integer, Integer> xpPlotsMapping = new HashMap<Integer,Integer>();

    /**
     * Create a FarmerDAO
     */
    public FarmerDAO(){
        this.controller = DBController.getController();
        initializeHashMaps();
    }

    // read methods
    /**
     * Returns the Farmer object that have the inputted username and updates the Rank and Maximum number of plots of that Farmer
     * @param username A String representing the username
     * @return A Farmer object
     */
    public Farmer retrieveFarmerFactory(String username){
        Farmer farmer = retrieveFarmer(username);
        farmer.setInventory(retrieveInventory(username));
        updateRankAndPlots(username,farmer);

        return farmer;
    }

    /**
     * Initialises and Returns the Farmer object from the SQL Database that have the inputted username
     * @param username A String representing the username
     * @return A Farmer object
     */
    public Farmer retrieveFarmer(String username){
        Farmer farmer = new Farmer();

        int xp = 0;
        int gold = 0;
        String rankName = "";
        int plots = 0;
        int giftsSent = 0;

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM magnet.farmer WHERE ownerUsername = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                xp = rs.getInt("xp");
                gold = rs.getInt("gold");
                rankName = rs.getString("rankName");
                plots = rs.getInt("plots");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        farmer.setRank(rankName);
        farmer.setMaxPlots(plots);
        farmer.setGold(gold);
        farmer.setXP(xp);
        farmer.setGiftsSent(giftsSent);

        return farmer;
    }

    /**
     * Returns the Map object that represents the user's inventory
     * @param username A String representing the username 
     * @return A Map object that maps Strings representing the crop name as keys, and integers representing the quantity as values
     */
    public Map<String, Integer> retrieveInventory(String username){
        Map<String,Integer> inventory = new HashMap<String,Integer>();

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM magnet.inventory WHERE username = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                String cropName = rs.getString("cropName");
                int quantity = rs.getInt("quantity");
                inventory.put(cropName, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    // update methods
    /**
     * Updates the Rank and Maximum number of plots of the Farmer object that belongs to the specified username
     * @param username A String representing the username of the user whose rank and maximum number of plots is to be updated
     * @param farmer The Farmer object to be updated
     */
    public void updateRankAndPlots(String username, Farmer farmer){
        int currentXp = farmer.getXp();
        int xpDenomination = setXpDenomination(currentXp);

        String newRank = rankNameXpMapping.get(xpDenomination);
        int newPlots = xpPlotsMapping.get(xpDenomination);

        farmer.setRank(newRank);
        farmer.setMaxPlots(newPlots);

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE magnet.farmer SET rankName = ?, plots = ? WHERE ownerUsername = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newRank);
            stmt.setInt(2, newPlots);
            stmt.setString(3, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the quantity of the specified crop in the inventory belonging to the specified username 
     * @param username A String representing the username 
     * @param cropName A String representing the crop name
     * @param quantity An integer representing the quantity of crop to be updated with
     */
    public void updateInventory(String username, String cropName, int quantity){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE magnet.inventory SET quantity = ? WHERE username = ? AND cropName = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quantity);
            stmt.setString(2, username);
            stmt.setString(3, cropName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the amount of gold belonging to the specified username 
     * @param username A String representing the username 
     * @param gold An integer representing the amount of gold to be updated with
     */
    public void updateGold(String username, int gold){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE magnet.farmer SET gold = ? WHERE ownerUsername = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gold);
            stmt.setString(2, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the amount of xp of the specified username 
     * @param username A String representing the username 
     * @param xp An integer representing the amount of xp to be updated with
     */
    public void updateXp(String username, int xp){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE magnet.farmer SET xp = ? WHERE ownerUsername = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, xp);
            stmt.setString(2, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new crop and its quantity into the inventory belonging to the specified username
     * @param username A String representing the username 
     * @param cropName A String representing the name of the crop to be added
     * @param quantity An integer representing the quantity of the crop to be added
     */
    public void addToInventory(String username, String cropName, int quantity){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "INSERT INTO magnet.inventory (username, cropName, quantity) VALUES (?,?,?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, cropName);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //other methods
    /**
     * Initialises 2 HashMaps, rankNameXpMapping which maps xp as the keys and rankName as values, and xpPlotsMapping which maps xp as keys and plots as values
     */
    public void initializeHashMaps(){
        String csvFile = "data/rank.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] cropInfo = line.split(cvsSplitBy);

                String rankName = cropInfo[0];
                int xp = Integer.parseInt(cropInfo[1]);
                int plots = Integer.parseInt(cropInfo[2]);

                rankNameXpMapping.put(xp,rankName);
                xpPlotsMapping.put(xp, plots);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the Xp denomination based on the user's current xp
     * @param currentXp An integer representing the current xp of the user
     * @return An integer representing the xp denomination
     */
    public int setXpDenomination(int currentXp){
        int denomination = 0;
        if (currentXp < 1000){
            denomination = 0;
        } else if (currentXp < 2500){
            denomination = 1000;
        } else if (currentXp < 5000){
            denomination = 2500;
        } else if (currentXp < 12000){
            denomination = 5000;
        } else {
            denomination = 12000;
        }
        return denomination;
    }

    /**
     * Returns the RankNameXpMapping Map object
     * @return A Map object with the xp as the key and rank name as the value
     */
    public Map<Integer, String> readRankNameXpMapping(){return this.rankNameXpMapping;}
    /**
     * Returns the RankNameXpMapping Map object
     * @return A Map object with the xp as the key and plot as the value
     */
    public Map<Integer, Integer> readXpPlotsMapping(){return this.xpPlotsMapping;}
}