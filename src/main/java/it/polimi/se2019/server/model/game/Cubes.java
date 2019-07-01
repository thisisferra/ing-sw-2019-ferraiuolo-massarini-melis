package it.polimi.se2019.server.model.game;

import org.json.simple.JSONObject;

import java.io.Serializable;

public class Cubes implements Serializable {
    private int reds;
    private int yellows;
    private int blues;

    public Cubes(int reds, int yellows, int blues) {
        this.reds = reds;
        this.yellows = yellows;
        this.blues = blues;
    }

    public Cubes() {
        //Needed for resuming cubes from saved match
    }

    public int getReds(){
        return this.reds;
    }

    public int getYellows(){
        return this.yellows;
    }

    public int getBlues(){
        return this.blues;
    }

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

    public boolean checkNumberRedCubes(Cubes cube) {
        if (reds + cube.getReds() <= 3) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkNumberBlueCubes(Cubes cube) {
        if (blues + cube.getBlues() <= 3) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkNumberYellowCubes(Cubes cube) {
        if (yellows + cube.getYellows() <= 3) {
            return true;
        } else {
            return false;
        }

    }

    public void setDeltaCubes(Cubes cubes) {
        this.reds = cubes.getReds();
        this.yellows = cubes.getYellows();
        this.blues = cubes.getBlues();
    }
    public String toString(){
        return "Reds: "+ this.getReds() + " Blues: " + this.getBlues() + " Yellows: " + this.getYellows();
    }

    public JSONObject toJSON() {
        JSONObject cubesJson = new JSONObject();

        cubesJson.put("reds", this.getReds());
        cubesJson.put("yellows", this.getYellows());
        cubesJson.put("blues", this.getBlues());

        return cubesJson;
    }

    public static Cubes resumeCubes(JSONObject ammoCubesToResume) {
        Cubes resumedCubes = new Cubes();

        resumedCubes.reds = ((Number) ammoCubesToResume.get("reds")).intValue();
        resumedCubes.yellows = ((Number) ammoCubesToResume.get("yellows")).intValue();
        resumedCubes.blues = ((Number) ammoCubesToResume.get("blues")).intValue();

        return resumedCubes;
    }
}
