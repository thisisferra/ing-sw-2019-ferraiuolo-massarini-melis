package it.polimi.se2019.model.player;

import it.polimi.se2019.model.game.Match;

public class Player {
    private String clientName;
    private int[] position;
    private int score;
    private String color;
    private PlayerBoard playerBoard;
    private boolean firstPlayer;
    private boolean suspended;

    public void draw(ArrayList<> deck);
}