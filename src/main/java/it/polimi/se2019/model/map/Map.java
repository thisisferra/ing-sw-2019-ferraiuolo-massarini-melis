package it.polimi.se2019.model.map;

import java.util.ArrayList;

public class Map {
    private int mapID;
    private int[] dimensions;
    private ArrayList<Square> allSquare;

    public int[] getDimensions() {
        return dimensions;
    }
}