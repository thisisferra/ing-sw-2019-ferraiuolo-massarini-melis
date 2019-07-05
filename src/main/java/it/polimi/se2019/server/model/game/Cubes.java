package it.polimi.se2019.server.model.game;

import org.json.simple.JSONObject;
import java.io.Serializable;

/**
 * The Cubes class represent a collection of maximum 9 cubes: 3 of each colour.
 * It has three int fields: reds, yellows and blues indicating the number of cubes for each color.
 * @author mattiamassarini,merklind.
 */
public class Cubes implements Serializable {
    private int reds;
    private int yellows;
    private int blues;

    /**
     * Constructor with three int parameters: reds, yellows and blue.
     *
     * @param reds the number of red cubes to be set.
     * @param yellows the number of yellow cubes to be set.
     * @param blues the number of blue cubes to be set.
     */
    public Cubes(int reds, int yellows, int blues) {
        this.reds = reds;
        this.yellows = yellows;
        this.blues = blues;
    }

    /**
     * Constructor with no parameters.
     * Private attributes reds,yellows and blues are set to 0.
     */
    public Cubes() {
        //Needed for resuming cubes from saved match
    }

    /**
     * Reds cubes getter method.
     * @return the number of reds cubes the player has.
     */
    public int getReds(){
        return this.reds;
    }

    /**
     * Yellow cubes getter method.
     * @return the number of yellow cubes the player has.
     */
    public int getYellows(){
        return this.yellows;
    }

    /**
     * Blue cubes getter method.
     * @return the number of blue cubes the player has.
     */
    public int getBlues(){
        return this.blues;
    }

    /**
     * It sets the number of cubes the player acquires.
     * The number of cubes the player has, cannot exceeds
     * three units for each color.
     * @param cube the number of cubes to be added to the cubes stack.
     */
    public void setCubes(Cubes cube) {
        if (checkNumberRedCubes(cube)) {
            reds = reds + cube.getReds();
        } else reds = 3;

        if (checkNumberYellowCubes(cube)) {
            yellows = yellows + cube.getYellows();
        } else yellows = 3;

        if (checkNumberBlueCubes(cube)) {
            blues = blues + cube.getBlues();
        } else blues = 3;
    }

    /**
     * Check if the number of red cubes to be added exceed the maximum allowed.
     * @param cube the cubes to be added.
     * @return true if the sum of cubes owned and added are minor or equals to three, false otherwise.
     */
    public boolean checkNumberRedCubes(Cubes cube) {
        if (reds + cube.getReds() <= 3) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Check if the number of blue cubes to be added exceed the maximum allowed.
     * @param cube the cubes to be added.
     * @return true if the sum of cubes owned and added are minor or equals to three, false otherwise.
     */
    public boolean checkNumberBlueCubes(Cubes cube) {
        if (blues + cube.getBlues() <= 3) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Check if the number of yellow cubes to be added exceed the maximum allowed.
     * @param cube the cubes to be added.
     * @return true if the sum of cubes owned and added are minor or equals to three, false otherwise.
     */
    public boolean checkNumberYellowCubes(Cubes cube) {
        if (yellows + cube.getYellows() <= 3) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * It sets the number of cubes as the cubes parameter.
     * @param cubes the number of cubes to be overwritten.
     */
    public void setDeltaCubes(Cubes cubes) {
        this.reds = cubes.getReds();
        this.yellows = cubes.getYellows();
        this.blues = cubes.getBlues();
    }

    /**
     * Check if the player has at least one cube of any color.
     * @return true if the player has at least one cube, false otherwise.
     */
    public boolean checkAtLeastOne(){
        if(this.getBlues() + this.getYellows() + this. getReds() > 0)
            return true;
        else return false;
    }
    public String toString(){
        return "Reds: "+ this.getReds() + " Blues: " + this.getBlues() + " Yellows: " + this.getYellows();
    }

    /**
     * JSON method to serialize Cubes class.
     * It saves the number of cubes of each color into a JSONObject object.
     * @return a JSONObject object.
     */
    public JSONObject toJSON() {
        JSONObject cubesJson = new JSONObject();

        cubesJson.put("reds", this.getReds());
        cubesJson.put("yellows", this.getYellows());
        cubesJson.put("blues", this.getBlues());

        return cubesJson;
    }


    /**
     * It restores an object Cube from a JSONObject object.
     * @param ammoCubesToResume the JSONObject to be read in order to restore the Cubes object.
     * @return the Cubes object restored.
     */
    public static Cubes resumeCubes(JSONObject ammoCubesToResume) {
        Cubes resumedCubes = new Cubes();

        resumedCubes.reds = ((Number) ammoCubesToResume.get("reds")).intValue();
        resumedCubes.yellows = ((Number) ammoCubesToResume.get("yellows")).intValue();
        resumedCubes.blues = ((Number) ammoCubesToResume.get("blues")).intValue();

        return resumedCubes;
    }
}
