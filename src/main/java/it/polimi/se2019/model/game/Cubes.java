package it.polimi.se2019.model.game;

public class Cubes {
    private int reds;
    private int yellows;
    private int blues;

    public Cubes(int reds, int yellows, int blues) {
        this.reds = reds;
        this.yellows = yellows;
        this.blues = blues;
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
        if (checkNumberRedCubes(cube) == true) {
            reds = reds + cube.getReds();
        } else reds = 3;

        if (checkNumberYellowCubes(cube) == true) {
            yellows = yellows + cube.getReds();
        } else yellows = 3;
        if (checkNumberBlueCubes(cube) == true) {
            blues = blues + cube.getReds();
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
    public String toString(){
        return "Reds: "+ this.getReds() + " Blues: " + this.getBlues() + " Yellows: " + this.getYellows();
    }
}
