package it.polimi.se2019.client.view;

import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;
import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.rmi.registry.Registry;
import java.util.ArrayList;


public class RemoteView {

    private ArrayList<RemoteView> allViews= new ArrayList<>();
    private String username;
    private int position;
    private int points;
    private int phaseAction;        /*useful to know if the player has enhanced action*/
    private boolean finalFrenzy;
    private ArrayList<Player> markPlayerBoard = new ArrayList<>();      //Info useful for Player-board GUI
    private ArrayList<Player> damagePlayerBoard = new ArrayList<>();    //Info useful for Player-board GUI
    private int deathsPlayerBoard;                                      //Info useful for Player-board GUI
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUp = new ArrayList<PowerUp>();
    private boolean canMove = false;
    private Cubes cubes;
    private ArrayList<Square> reachableSquare = new ArrayList<>();
    private int numberOfMoves = 2;

    private Registry registry;
    private RMIServerInterface stub;

    public RemoteView(String username) {
        this.username = username;
    }

    //GETTER

    public ArrayList<RemoteView> getAllViews() {
        return this.allViews;
    }

    public String getUsername() {
        return this.username;
    }

    public int getPosition() {
        return this.position;
    }

    public void updatePosition(int newPosition) {
        this.position = newPosition;
    }

    public int getPoints() {
        return this.points;
    }

    public int getPhaseAction() {
        return this.phaseAction;
    }

    public boolean getFinalFrenzy() {
        return this.finalFrenzy;
    }

    public ArrayList<Player> getMarkPlayerBoard() {
        return this.markPlayerBoard;
    }

    public ArrayList<Player> getDamagePlayerBoard() {
        return this.damagePlayerBoard;
    }

    public int getDeathsPlayerBoard() {
        return this.deathsPlayerBoard;
    }

    public ArrayList<Weapon> getWeapons() {
        return this.weapons;
    }

    public ArrayList<PowerUp> getPowerUp() {
        return this.powerUp;
    }

    public boolean getCanMove() {
        return this.canMove;
    }

    public Cubes getCubes() {
        return this.cubes;
    }

    public ArrayList<Square> getReachableSquare() {
        return this.reachableSquare;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public RMIServerInterface getStub() {
        return this.stub;
    }


    //SETTER

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void setPoints(int points) {
        this.points = points;
    }

    private void setPhaseAction(int phaseAction) {
        this.phaseAction = phaseAction;
    }

    private void finalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    private void setMarkPlayerBoard(ArrayList<Player> markPlayerBoard) {
        this.markPlayerBoard = markPlayerBoard;
    }

    private void setDamagePlayerBoard(ArrayList<Player> damagePlayerBoard) {
        this.damagePlayerBoard = damagePlayerBoard;
    }

    private void setDeathsPlayerBoard(int deathsPlayerBoard) {
        this.deathsPlayerBoard = deathsPlayerBoard;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    private void setCubes(Cubes cubes) {
        this.cubes = cubes;
    }

    public void setReachableSquare(ArrayList<Square> reacheableSquare) {
        this.reachableSquare.clear();
        this.reachableSquare = reacheableSquare;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    //ALTRO

    public void notifyNewClient(String newClient) {

    }

    public void createRemotesView(ArrayList<VirtualView> allVirtualView) {
        int size = allVirtualView.size();
        for (int i = 0; i < size; i++) {
            allViews.add(new RemoteView(allVirtualView.get(i).getUsername()));
        }
        for (RemoteView remoteView : allViews) {
            remoteView.getUsername();
        }
    }

    public void updateRemoteView(VirtualView virtualView) {
        this.username = virtualView.getUsername();
        this.position = virtualView.getPosition();
        this.weapons = virtualView.getWeapons();
        this.powerUp = virtualView.getPowerUps();
        this.cubes = virtualView.getCubes();
    }

    public RemoteView searchMyRemoteView(String username) {
        for (RemoteView remoteView : allViews) {
            if (remoteView.getUsername().equals(username)) {
                return remoteView;
            }
        }
        return null;
    }
}