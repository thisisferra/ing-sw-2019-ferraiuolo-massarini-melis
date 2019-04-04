package it.polimi.se2019.model.map;

import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;

public abstract class Square {
    private int[] position;
    private ArrayList<Square> roomSquares;
    private ArrayList<Square> hammingSquare;
    private ArrayList<Square> axialSquares;
    private ArrayList<Square> doors;
    private boolean spawnPoint;
    private ArrayList<Player> playersOn;
    private String color;

    public void restock(ArrayList<> deck);
}