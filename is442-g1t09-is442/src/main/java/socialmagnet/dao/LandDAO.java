package socialmagnet.dao;

import java.util.*;
import java.io.*;
import java.text.*;
import java.sql.*;
import java.util.Date;

import socialmagnet.game.*;

/**
 * This Class represents the Land DAO.
 * Initialised using values from "crop.csv"
 * @version 25th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class LandDAO {
    private CropDAO cropDAO = new CropDAO();
    private DBController controller = DBController.getController();

    //read
    /**
     * Returns the Land object that belongs to the specified username and plot number
     * @param owner A String representing the username
     * @param plotNumber An integer representing the plot number
     * @return A Land object
     */
    public Land retrieveLand(String owner, int plotNumber){
        Land land = new Land();

        String cropName = null;
        Date plantTime = null;
        int currentStatus = 0;
        String progressBar = null;

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM magnet.land WHERE owner = ? and plotNumber = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, owner);
            stmt.setInt(2, plotNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cropName = rs.getString("cropName");
                plantTime = rs.getTimestamp("plantTime");
                currentStatus = rs.getInt("currentStatus");
                progressBar = rs.getString("progressBar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (cropName != null){
            Date plantTimeDate = new Date(plantTime.getTime());

            land.setOwner(owner);
            land.setCrop(cropName);
            land.setPlantTime(plantTimeDate, cropName);
            land.setPlotNumber(plotNumber);
            land.setCurrentStatus(currentStatus);
            land.setProgressBar(progressBar);
        }
        return land;
    }

    // update
    /**
     * Plants the specified crop on the spcified user's plotnumber
     * @param owner A String representing the username
     * @param plotNumber An integer representing the plot number
     * @param cropName A String representing the crop to be planted
     */
    public void plantCrop(String owner, int plotNumber,  String cropName){
        Land land = new Land();

        land.setCrop(cropName);
        land.setPlantTime(new Date(), cropName);
        land.setOwner(owner);
        land.setPlotNumber(plotNumber);

        Date plantTime = land.getPlantTime();
        Date finishedTime = land.getFinishedTime();
        Date witherTime = land.getWitherTime();
        int currentStatus = land.getCurrentStatus();
        String progressBar = land.getProgressBar();

//        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedPlantTime = sdf.format(plantTime);
//        String formattedFinishedTime = sdf.format(finishedTime);
//        String formattedWitherTime = sdf.format(witherTime);

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "INSERT INTO magnet.land (owner, plotNumber, plantTime, finishedTime, witherTime, cropName, currentStatus, progressBar) VALUES (?,?,?,?,?,?,?,?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, owner);
            stmt.setInt(2, plotNumber);
            stmt.setTimestamp(3, new java.sql.Timestamp(plantTime.getTime()) );
            stmt.setTimestamp(4, new java.sql.Timestamp(finishedTime.getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(witherTime.getTime()));
            stmt.setString(6, cropName);
            stmt.setInt(7, currentStatus);
            stmt.setString(8, progressBar);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the growing progress of crop on the specified land
     * @param land The land object ot be updated
     */
    public void updateProgress(Land land){
        String wiltedString = "[  wilted  ]";
        StringBuilder progressBar = new StringBuilder();
        String percentageCompleted = "";
        int finalStatus = land.getCurrentStatus();

        if (land.getCropName() != null){
            Crop crop = cropDAO.retrieveCrop(land.getCropName());
            double timeToGrow = crop.getTime();

            Date plantTime = land.getPlantTime();
            Date finishedTime = land.getFinishedTime();
            Date witherTime = land.getWitherTime();
            Date timeNow = new Date();

            int timeAfterWilt = (int) (timeNow.getTime() - witherTime.getTime());
            int timeAfterFinished = (int) (timeNow.getTime() - finishedTime.getTime());
//            double timeElapsed =(double) (timeNow.getTime() - plantTime.getTime());
            double timeElapsed =(double) ((timeNow.getTime() - plantTime.getTime()) / 60000);

            if (timeAfterWilt > 0){ //withered
                land.setCurrentStatus(-1);
                finalStatus = -1;
                progressBar = new StringBuilder(wiltedString);
            } else if (timeAfterFinished > 0 ){
                land.setCurrentStatus(2);
                finalStatus = 2;
                progressBar = new StringBuilder("[##########]");
                percentageCompleted = "100%";
            } else {
                double percentage = (timeElapsed / timeToGrow )* 100;
                int intPercentage = (int) percentage;
                percentageCompleted = intPercentage +  "%";
                progressBar = new StringBuilder("[");
                int display = intPercentage/10 ;
                for (int i =1; i<= 10; i++){
                    if (i <= display){
                        progressBar.append("#");
                    } else {
                        progressBar.append("-");
                    }
                }
                progressBar.append("]");
            }
        }

        String finalProgressBar = progressBar + " "+ percentageCompleted;
        land.setProgressBar(finalProgressBar);

        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE magnet.land SET currentStatus = ?, progressBar = ? WHERE owner = ? AND plotNumber = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, finalStatus);
            stmt.setString(2, finalProgressBar);
            stmt.setString(3, land.getOwner());
            stmt.setInt(4, land.getPlotNumber());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //delete
    /**
     * Clears the plot of land
     * @param owner A String representing the username
     * @param plotNumber An integer representing the plot number
     */
    public void clearLand(String owner, int plotNumber){
        Connection conn = controller.getConnection();
        PreparedStatement stmt = null;
        String sql = "DELETE FROM magnet.land WHERE owner = ? AND plotNumber = ?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, owner);
            stmt.setInt(2, plotNumber);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}