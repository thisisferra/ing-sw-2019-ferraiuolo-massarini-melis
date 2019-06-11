package it.polimi.se2019.client.view;

import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;
import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.map.WeaponSlot;
import it.polimi.se2019.server.model.player.Player;

import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * RemoteView gathers and manages the data set of the game
 * that the GUI uses to show to the user.<br>
 *
 * INSTANTIATED BY: GUIController
 */

public class RemoteView {

    private String username;
    private String character;
    private int position;
    private int points;
    private int phaseAction;        /*useful to know if the player has enhanced action*/
    private boolean finalFrenzy;
    private ArrayList<Player> markPlayerBoard = new ArrayList<>();      //Info useful for Player-board GUI
    private ArrayList<Player> damagePlayerBoard = new ArrayList<>();    //Info useful for Player-board GUI
    private int deathsPlayerBoard;                                      //Info useful for Player-board GUI
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUp = new ArrayList<>();
    private boolean canMove = false;
    private Cubes cubes;
    private ArrayList<Square> reachableSquare = new ArrayList<>();
    private int numberOfActions = 2;
    private WeaponSlot cabinetRed;
    private WeaponSlot cabinetYellow;
    private WeaponSlot cabinetBlue;

    /**
     * Constructor of the class.
     *
     * @param username: will later be the key to retrieve the
     *                  data set of a particular client.
     */

    public RemoteView(String username) {
        this.username = username;
    }



    /* * *   GETTERS   * * */

    /**
     * Retrieves the username that links a particular remote view
     * with its user.
     *
     * @return the name chosen by the user, registered in this
     * class's object.
     */

    public String getUsername() {
        return this.username;
    }

    public String getCharacter() {
        return  this.character;
    }


    /**
     * Retrieves the position of the user's pawn in the map.
     *
     * @return an int that represents the square in which the user is.
     */

    public int getPosition() {
        return this.position;
    }


    /**
     * Retrieves the amount of points this user has scored up
     * to now.
     *
     * @return an int that represents the points scored.
     */

    public int getPoints() {
        return this.points;
    }


    /**
     * Retrieves the info useful to manage what actions a client can do.<br>
     * 0: normal abilities;<br>
     * 1: "move & grab" with 2 moves granted;<br>
     * 2: "shot" with a previous move granted along with the unlocked
     *    action from the 1.
     *
     * @return an int that represents the abilities a client may perform.
     */

    public int getPhaseAction() {
        return this.phaseAction;
    }


    /**
     * Retrieves a boolean that tells us whether we are in the game
     * finale or not. Necessary to represent the correct player board
     * with the GUI, but mostly to enable the proper client actions.
     *
     * @return a boolean that represents in which phase the game is.
     */

    public boolean getFinalFrenzy() {
        return this.finalFrenzy;
    }


    /**
     * Retrieves an ArrayList of Player objects that gives us
     * the info on the amount and also the order of the marks
     * taken from enemies by this user in particular.
     *
     * @return the ordered list of the marks given from other
     *         users to this user.
     */

    public ArrayList<Player> getMarkPlayerBoard() {
        return this.markPlayerBoard;
    }


    /**
     * Retrieves an ArrayList of Player objects that gives us
     * the info on the amount and also the order of the damage
     * taken from enemies by this user in particular.
     *
     * @return the ordered list of the blood damage given from
     *         other users to this user.
     */

    public ArrayList<Player> getDamagePlayerBoard() {
        return this.damagePlayerBoard;
    }


    /**
     * Retrieves the number of times this particular user
     * died in the game. This info is needed to manage in
     * the proper way the diminishing value of points the
     * enemies get when this user gets horribly killed.
     *
     * @return the amount of times this user got killed.
     */

    public int getDeathsPlayerBoard() {
        return this.deathsPlayerBoard;
    }


    /**
     * Retrieves an ArrayList of Weapon objects currently
     * in the hand of this user.
     *
     * @return a list of the weapons owned by the user.
     */

    public ArrayList<Weapon> getWeapons() {
        return this.weapons;
    }


    /**
     * Retrieves an ArrayList of PowerUp objects currently
     * in the hand of this user.
     *
     * @return a list of the power ups owned by the user.
     */

    public ArrayList<PowerUp> getPowerUp() {
        return this.powerUp;
    }


    /**
     * Retrieves the status of the move action started by the user:<br>
     * false: the user didn't press on move button yet;<br>
     * true: otherwise.
     *
     * @return a boolean that tells if the user asked to move or not.
     */

    public boolean getCanMove() {
        return this.canMove;
    }


    /**
     * Retrieves a Cubes object, a triplet of int values
     * that represents the valuables of this game associated
     * with this user.
     *
     * @return the amount of red, yellow and blue cubes the user
     *         own.
     */

    public Cubes getCubes() {
        return this.cubes;
    }


    /**
     * Retrieves an ArrayList of Square objects that gives us
     * the info on which squares are reachable from the currently
     * position in the map of the user's pawn by the user's pawn
     * itself.
     *
     * @return a list of squares reachable by the user from its
     *         position.
     */

    public ArrayList<Square> getReachableSquare() {
        return this.reachableSquare;
    }


    /**
     * Retrieves the amount of actions left to this user in this turn.
     *
     * @return the number of actions remained.
     */

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public WeaponSlot getCabinetRed() {
        return this.cabinetRed;
    }

    public WeaponSlot getCabinetYellow() {
        return this.cabinetYellow;
    }

    public WeaponSlot getCabinetBlue() {
        return this.cabinetBlue;
    }



    /* * *   SETTERS   * * */

    /**
     * Sets in this remote view the updated position of the
     * user's pawn in the map.
     *
     * @param position: an int that represents the up-to-date
     *                  square in which the user is.
     */

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    private void setPoints(int points) {
        this.points = points;
    }

    private void setPhaseAction(int phaseAction) {
        this.phaseAction = phaseAction;
    }

    private void setFinalFrenzy(boolean finalFrenzy) {
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


    /**
     * Sets the status of the move action:<br>
     * false: at the end of the move action;<br>
     * true: whenever the user clicks on the move button.
     *
     * @param canMove: a boolean that represents the will
     *                 of the user about his desire to move.
     */

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    private void setCubes(Cubes cubes) {
        this.cubes = cubes;
    }


    /**
     * Sets a list of Square objects that are the positions on the
     * map reachable by the user's pawn from its currently position.
     *
     * @param reachableSquare: list of squares reachable by the user
     *                         from its position.
     */

    public void setReachableSquare(ArrayList<Square> reachableSquare) {
        this.reachableSquare.clear();
        this.reachableSquare = reachableSquare;
    }

    public void setNumberOfMoves(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    public void setCabinetRed(WeaponSlot cabinetRed) {
        this.cabinetRed = cabinetRed;
    }

    public void setCabinetYellow(WeaponSlot cabinetYellow) {
        this.cabinetYellow = cabinetYellow;
    }

    public void setCabinetBlue(WeaponSlot cabinetBlue) {
        this.cabinetBlue = cabinetBlue;
    }



    /* * *   OTHER   * * */

    public void notifyNewClient(String newClient) {

    }


    /**
     * Updates the status of this class object attributes with
     * the up-to-date values. The data is retrieved by the
     * SERVER side to the CLIENT side through RMI: here virtualview
     * is obtained in {@see GUIController} class in {@see update}
     * method.
     *
     * @param virtualView: a VirtualView object, in short the
     *                     SERVER counterpart of the RemoteView
     *                     class.
     */

    public void updateRemoteView(VirtualView virtualView) {
        this.username = virtualView.getUsername();
        this.character = virtualView.getCharacter();
        this.position = virtualView.getPosition();
        this.weapons = virtualView.getWeapons();
        this.powerUp = virtualView.getPowerUps();
        this.cubes = virtualView.getCubes();
        this.cabinetRed = virtualView.getCabinetRed();
        this.cabinetYellow = virtualView.getCabinetYellow();
        this.cabinetBlue = virtualView.getCabinetBlue();
    }
}