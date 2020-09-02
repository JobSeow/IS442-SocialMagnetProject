package socialmagnet.game;

import java.util.*;
import socialmagnet.dao.*;

/**
 * This Class represents the land in the City Farmers Game
 * @version 25th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class Land {
    private String owner;
    private int plotNumber;
    private Date plantTime;
    private Date finishedTime;
    private Date witherTime;
    private String cropName;
    private int currentStatus; // -1 wilted, 0 empty, 1 contains crop, 2 is finished
    private String progressBar;
    private CropDAO cropDAO = new CropDAO();

    /**
     * Create a land
     */
    public Land() {
    }

    //getters
    /** Gets the owner
     * @return The owner
     */
    public String getOwner(){return owner;}

    /** Gets the plot number
     * @return The plot number
     */
    public int getPlotNumber(){return plotNumber;}

    /** Gets the time when the crop was planted
     * @return The time when the crop was planted
     */
    public Date getPlantTime(){return plantTime;}

    /** Gets the time when the crop has matured
     * @return The time when the crop has matured
     */
    public Date getFinishedTime(){return finishedTime;}

    /** Gets the time when the crop has withered
     * @return The time when the crop has withered
     */
    public Date getWitherTime(){return witherTime;}

    /** Gets the time when the crop name
     * @return The time when the crop name
     */
    public String getCropName(){return cropName;}

    /** Gets the current status of the land where -1 is wilted, 0 is empty, 1 contains crop, 2 is finished
     * @return The current status of the land
     */
    public int getCurrentStatus(){return currentStatus;}

    /** Gets the progress bar which shows how mature the crop is
     * @return The progress bar which shows how mature the crop is
     */
    public String getProgressBar(){return progressBar;}

    //setters

    /** Sets the time when the crop is planted
     * @param plantTime A Date object representing the when the crop was planted
     * @param cropName A String representing the crop name 
     */
    public void setPlantTime(Date plantTime, String cropName){
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(plantTime);
        Crop crop = cropDAO.retrieveCrop(cropName);
        this.plantTime = calNow.getTime();

        calNow.add(Calendar.MINUTE, crop.getTime());
        setFinishedTime(calNow.getTime());
        
        calNow.add(Calendar.MINUTE, crop.getTime());
        setWitherTime(calNow.getTime());
    }

    /** Sets the name of the crop planted
     * @param cropName A String representing the crop name 
     */
    public void setCrop(String cropName){
        this.cropName = cropName;
        this.currentStatus = 1;
    }

    /** Sets the time when the crop has matured
     * @param finishedTime A Date object representing the time when the crop has reached maturity
     */
    public void setFinishedTime(Date finishedTime){
        this.finishedTime = finishedTime;
    }

    /** Sets the time when the crop has withered
     * @param witherTime A Date object representing the time when the crop has withered
     */
    public void setWitherTime(Date witherTime){
        this.witherTime = witherTime;
    }

    /** Sets the progress bar which shows how mature the crop is
     * @param progressBar A String representing the new progressbar to set as
     */
    public void setProgressBar(String progressBar){
        this.progressBar = progressBar;
    }

    /** Sets the current status of the land where -1 is wilted, 0 is empty, 1 contains crop, 2 is finished
     * @param currentStatus An integer reprsenting the current status of the land to set as
     */
    public void setCurrentStatus(int currentStatus){
        this.currentStatus = currentStatus;
    }

    /** Sets the owner of the land
     * @param username A String representing the username to set as
     */
    public void setOwner(String username){
        this.owner = username;
    }

    /** Sets the plot number of the land
     * @param plotNumber An integer representing the plot number to set as
     */
    public void setPlotNumber(int plotNumber){
        this.plotNumber = plotNumber;
    }
}