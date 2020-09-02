package socialmagnet.dao;

import socialmagnet.game.*;
import java.sql.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This Class represents the Crop DAO.
 * Initialised using values from "crop.csv"
 * @version 25th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class CropDAO{

    // Read
    /**
     * Initialises the Crop objects in the list using values from "crop.csv"
     * @return An ArrayList of initialised Crop objects
     */
    public ArrayList<Crop> readCropList(){
        ArrayList<Crop> rv = new ArrayList<Crop>();

        String csvFile = "data/crop.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] cropInfo = line.split(cvsSplitBy);
                Crop newCrop = new Crop();
                
                newCrop.setName(cropInfo[0]);
                newCrop.setCost(Integer.parseInt(cropInfo[1]));
                newCrop.setTime(Integer.parseInt(cropInfo[2]));
                newCrop.setXP(Integer.parseInt(cropInfo[3]));
                newCrop.setMinYield(Integer.parseInt(cropInfo[4]));
                newCrop.setMaxYield(Integer.parseInt(cropInfo[5]));
                newCrop.setSalePrice(Integer.parseInt(cropInfo[6]));

                rv.add(newCrop);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rv;
    }

    /**
     * Initialises and returns the Crop object from the csv file that have the inputted crop name
     * @param cropName A String containing the crop name
     * @return A Crop object
     */
    public Crop retrieveCrop(String cropName){
        Crop crop = new Crop();
        String csvFile = "data/crop.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] cropInfo = line.split(cvsSplitBy);

                if (cropInfo[0].equals(cropName)) {
                    crop.setName(cropInfo[0]);
                    crop.setCost(Integer.parseInt(cropInfo[1]));
                    crop.setTime(Integer.parseInt(cropInfo[2]));
                    crop.setXP(Integer.parseInt(cropInfo[3]));
                    crop.setMinYield(Integer.parseInt(cropInfo[4]));
                    crop.setMaxYield(Integer.parseInt(cropInfo[5]));
                    crop.setSalePrice(Integer.parseInt(cropInfo[6]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crop;
    }
}


