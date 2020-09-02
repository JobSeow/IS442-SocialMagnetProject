package socialmagnet.controller;

import socialmagnet.game.Crop;

/**
 * This Class represents the CropController class that contains the method to determine the yield of a crop
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class CropController {

    /**
     * Gets the random yield of a crop which lies between its maximum and minimum yield
     * @param crop A Crop object representing the crop to get the yield for
     * @return An integer representing the yield of the crop
     */
    public int getYield(Crop crop){
        double randomDouble = Math.random();
        randomDouble = randomDouble * (crop.getMaxYield() - crop.getMinYield() );
        return crop.getMinYield() + (int) randomDouble;
    }
}
