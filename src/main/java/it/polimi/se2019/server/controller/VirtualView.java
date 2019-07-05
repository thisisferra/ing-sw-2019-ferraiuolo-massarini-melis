package it.polimi.se2019.server.controller;

import it.polimi.se2019.client.controller.network.RMI.GUIControllerInterface;
import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.AbstractWeapon;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.map.WeaponSlot;
import it.polimi.se2019.server.model.player.EnemyMark;
import it.polimi.se2019.server.model.player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * VirtualView is an object that represent the model data that are sernt to the client to show thr right information
 * @author mattiamassarini, merklind, thisisferra
 */
public class VirtualView implements Serializable {

    private GUIControllerInterface clientReference;

    //
    private String ipClient;
    private String username;
    private String character;
    private int position;
    private int damage;
    private int points;
    private int phaseAction;
    private int finalFrenzy;
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private Cubes cubes;
    private WeaponSlot cabinetRed;
    private WeaponSlot cabinetYellow;
    private WeaponSlot cabinetBlue;
    private WeaponShot weaponShot;
    private ArrayList<EnemyMark> markPlayerBoard = new ArrayList<>();      //Info useful for Player-board GUI
    private ArrayList<Player> damagePlayerBoard = new ArrayList<>();    //Info useful for Player-board GUI
    private int deathsPlayerBoard;                                      //Info useful for Player-board GUI
    private ArrayList<Player> killShotTrack = new ArrayList<>();
    private boolean canMove;
    private ArrayList<Square> reachableSquare = new ArrayList<>();
    private int numberOfActions;
    private ArrayList<Weapon> availableWeapons = new ArrayList<>();
    private int typePlayerBoard;
    private boolean suspended;


    /**
     * Instantiates a new Virtual view.
     *
     * @param clientReference the client reference
     */
//COSTRUTTORE
    public VirtualView(GUIControllerInterface clientReference) {
        this.clientReference = clientReference;
    }

    /**
     * Instantiates a new Virtual view.
     */
    public VirtualView() {
        //Needed for resuming a virtualView from saved match
    }

    //GETTER

    /**
     * Gets client reference.
     *
     * @return the client reference
     */
    public GUIControllerInterface getClientReference() {
        return this.clientReference;
    }

    /**
     * getter of the username of the virtualView
     *
     * @return the username of the virtualView
     */
    public String getUsername() {
        return this.username;
    }


    /**
     * Gets character.
     *
     * @return the character
     */
    public String getCharacter() {
        return this.character;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Gets damage.
     *
     * @return the damage
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Gets number of actions.
     *
     * @return the number of actions
     */
    public int getNumberOfActions() {
        return this.numberOfActions;
    }

    /**
     * Get reachable square array list.
     *
     * @return the array list
     */
    public ArrayList<Square> getReachableSquare(){
        return this.reachableSquare;
    }

    /**
     * Get weapons array list.
     *
     * @return the array list
     */
    public ArrayList<Weapon> getWeapons(){
        return this.weapons;
    }

    /**
     * Gets power ups.
     *
     * @return ArrayList of power ups
     */
    public ArrayList<PowerUp> getPowerUps() {
        return this.powerUps;
    }

    /**
     * Gets phase action.
     *
     * @return int that represent the type of phase action
     */
    public int getPhaseAction() {
        return phaseAction;
    }

    /**
     * Get final frenzy int.
     *
     * @return int that represent the type of finalFrenzy
     */
    public int getFinalFrenzy(){
        return this.finalFrenzy;
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Get deaths player board int.
     *
     * @return the int
     */
    public int getDeathsPlayerBoard(){
        return this.deathsPlayerBoard;
    }

    /**
     * Get mark player board array list.
     *
     * @return the array list
     */
    public ArrayList<EnemyMark> getMarkPlayerBoard(){
        return this.markPlayerBoard;
    }

    /**
     * Get damage player board array list.
     *
     * @return the array list
     */
    public ArrayList<Player> getDamagePlayerBoard(){
        return this.damagePlayerBoard;
    }

    /**
     * Get kill shot track array list.
     *
     * @return the array list
     */
    public ArrayList<Player> getKillShotTrack(){
        return this.killShotTrack;
    }

    /**
     * Get cubes cubes.
     *
     * @return the cubes
     */
    public Cubes getCubes(){
        return this.cubes;
    }

    /**
     * Gets cabinet red.
     *
     * @return the cabinet red
     */
    public WeaponSlot getCabinetRed() {
        return this.cabinetRed;
    }

    /**
     * Gets cabinet yellow.
     *
     * @return the cabinet yellow
     */
    public WeaponSlot getCabinetYellow() {
        return this.cabinetYellow;
    }

    /**
     * Gets cabinet blue.
     *
     * @return the cabinet blue
     */
    public WeaponSlot getCabinetBlue() {
        return this.cabinetBlue;
    }

    /**
     * Gets weapon shot.
     *
     * @return the weapon shot
     */
    public WeaponShot getWeaponShot() { return
            this.weaponShot;
    }

    /**
     * Get available weapons array list.
     *
     * @return the array list
     */
    public ArrayList<Weapon> getAvailableWeapons(){
        return this.availableWeapons;
    }

    /**
     * Get can move boolean.
     *
     * @return the boolean
     */
    public boolean getCanMove(){
        return this.canMove;
    }

    /**
     * Get type player board int.
     *
     * @return the int
     */
    public int getTypePlayerBoard(){
        return this.typePlayerBoard;
    }

    /**
     * Gets suspended.
     *
     * @return the suspended
     */
    public boolean getSuspended() {
        return this.suspended;
    }

    //SETTER

    /**
     * Sets client reference.
     *
     * @param clientReference the client reference
     */
    public void setClientReference(GUIControllerInterface  clientReference) {
        this.clientReference = clientReference;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets character.
     *
     * @param character the character
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Set points.
     *
     * @param points the points
     */
    public void setPoints(int points){
        this.points = points;
    }

    /**
     * Set reachable square.
     *
     * @param reachableSquare the reachable square
     */
    public void setReachableSquare(ArrayList<Square> reachableSquare){
        this.reachableSquare = reachableSquare;
    }

    /**
     * Sets phase action.
     *
     * @param phaseAction the phase action
     */
    public void setPhaseAction(int phaseAction) {
        this.phaseAction = phaseAction;
    }

    /**
     * Sets damage.
     *
     * @param damage the damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Sets number of actions.
     *
     * @param numberOfActions the number of actions
     */
    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    /**
     * Sets weapons.
     *
     * @param weapons the weapons
     */
    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }

    /**
     * Sets power ups.
     *
     * @param powerUps the power ups
     */
    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    /**
     * Set can move.
     *
     * @param canMove the can move
     */
    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }

    /**
     * Set deaths player board.
     *
     * @param deathsPlayerBoard the deaths player board
     */
    public void setDeathsPlayerBoard(int deathsPlayerBoard){
        this.deathsPlayerBoard = deathsPlayerBoard;
    }

    /**
     * Set final frenzy.
     *
     * @param finalFrenzy the final frenzy
     */
    public void setFinalFrenzy(int finalFrenzy){
        this.finalFrenzy = finalFrenzy;
    }

    /**
     * Sets cubes.
     *
     * @param cubes the cubes
     */
    public void setCubes(Cubes cubes) {
        this.cubes = cubes;
    }

    /**
     * Sets cabinet red.
     *
     * @param cabinetRed the cabinet red
     */
    public void setCabinetRed(WeaponSlot cabinetRed) {
        this.cabinetRed = cabinetRed;
    }

    /**
     * Sets cabinet yellow.
     *
     * @param cabinetYellow the cabinet yellow
     */
    public void setCabinetYellow(WeaponSlot cabinetYellow) {
        this.cabinetYellow = cabinetYellow;
    }

    /**
     * Sets cabinet blue.
     *
     * @param cabinetBlue the cabinet blue
     */
    public void setCabinetBlue(WeaponSlot cabinetBlue) {
        this.cabinetBlue = cabinetBlue;
    }

    /**
     * Sets weapon shot.
     *
     * @param weaponShot the weapon shot
     */
    public void setWeaponShot(WeaponShot weaponShot) {
        this.weaponShot = weaponShot;
    }

    /**
     * Set available weapons.
     *
     * @param availableWeapons the available weapons
     */
    public void setAvailableWeapons(ArrayList<Weapon> availableWeapons){
        this.availableWeapons = availableWeapons;
    }

    /**
     * Set mark player board.
     *
     * @param markPlayerBoard the mark player board
     */
    public void setMarkPlayerBoard(ArrayList<EnemyMark> markPlayerBoard){
        this.markPlayerBoard = markPlayerBoard;
    }

    /**
     * Set damage player board.
     *
     * @param damagePlayerBoard the damage player board
     */
    public void setDamagePlayerBoard(ArrayList<Player> damagePlayerBoard){
        this.damagePlayerBoard = damagePlayerBoard;
    }

    /**
     * Set kill shot track.
     *
     * @param killShotTrack the kill shot track
     */
    public void setKillShotTrack(ArrayList<Player> killShotTrack ){
        this.killShotTrack = killShotTrack;
    }

    /**
     * Sets type player board.
     *
     * @param typePlayerBoard the type player board
     */
    public void setTypePlayerBoard(int typePlayerBoard) {
        this.typePlayerBoard = typePlayerBoard;
    }

    /**
     * Sets suspended.
     *
     * @param suspended the suspended
     */
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    //ALTRO

    /**
     * Re set virtual view.
     *
     * @param guiControllerToResume the gui controller to resume
     */
    public void reSetVirtualView(GUIControllerInterface guiControllerToResume) {
        this.clientReference = guiControllerToResume;
    }

    /**
     * Update virtual view.
     *
     * @param player the player
     * @param match  the match
     */
    public void updateVirtualView(Player player, Match match) {
        this.setUsername(player.getClientName());
        this.setPosition(player.getPosition());
        this.setCharacter(player.getCharacter());
        this.setWeapons(player.getHand().getWeapons());
        this.setPowerUps(player.getHand().getPowerUps());
        this.setCubes(player.getPlayerBoard().getAmmoCubes());
        this.setCabinetRed(match.getArsenal().get(0));
        this.setCabinetYellow(match.getArsenal().get(1));
        this.setCabinetBlue(match.getArsenal().get(2));
        this.setPoints(player.getScore());
        this.setCanMove(player.getCanMove());
        this.setFinalFrenzy(player.getFinalFrenzy());
        this.setPhaseAction(player.getPhaseAction());
        this.setMarkPlayerBoard(player.getPlayerBoard().getEnemyMarks());
        this.setDamagePlayerBoard(player.getPlayerBoard().getDamage());
        this.setKillShotTrack(match.getKillShotTrack());
        this.setDeathsPlayerBoard(player.getPlayerBoard().getDeaths());
        this.setNumberOfActions(player.getNumberOfAction());
        this.setTypePlayerBoard(player.getTypePlayerBoard());
        this.setSuspended(player.getSuspended());
    }

    /**
     * To json json object.
     *
     * @return the json object
     */
    public JSONObject toJSON() {
        JSONObject virtualViewJson = new JSONObject();

        virtualViewJson.put("username", this.getUsername());
        virtualViewJson.put("character", this.getCharacter());
        virtualViewJson.put("position", this.getPosition());
        virtualViewJson.put("damage", this.getDamage());
        virtualViewJson.put("points", this.getPoints());
        virtualViewJson.put("phaseAction", this.getPhaseAction());
        virtualViewJson.put("finalFrenzy", this.getFinalFrenzy());

        JSONArray weaponsJson = new JSONArray();
        for (Weapon weapon : this.getWeapons()) {
            weaponsJson.add(weapon.getType());
        }
        virtualViewJson.put("weapons", weaponsJson);

        JSONArray powerUpsJson = new JSONArray();
        for (PowerUp powerUp : this.getPowerUps()) {
            powerUpsJson.add(powerUp.toJSON());
        }
        virtualViewJson.put("powerUps", powerUpsJson);

        virtualViewJson.put("cubes", this.getCubes().toJSON());

        virtualViewJson.put("cabinetRed", this.getCabinetRed().toJSON());
        virtualViewJson.put("cabinetYellow", this.getCabinetYellow().toJSON());
        virtualViewJson.put("cabinetBlue", this.getCabinetBlue().toJSON());

        //      virtualViewJson.put("infoShot", this.getInfoShot().toJSON());

        JSONArray markPlayerBoardJson = new JSONArray();
        for (EnemyMark enemyMark : this.getMarkPlayerBoard()) {
            markPlayerBoardJson.add(enemyMark.toJSON());
        }
        virtualViewJson.put("markPlayerBoard", markPlayerBoardJson);

        JSONArray damagePlayerBoardJson = new JSONArray();
        for (Player player : this.getDamagePlayerBoard()) {
            damagePlayerBoardJson.add(player.toJSON());
        }
        virtualViewJson.put("damagePlayerBoard", damagePlayerBoardJson);

        virtualViewJson.put("deathsPlayerBoard", this.getDeathsPlayerBoard());

        JSONArray killShotTrackJson = new JSONArray();
        for (Player player : this.getKillShotTrack()) {
            killShotTrackJson.add(player.toJSON());
        }
        virtualViewJson.put("killShotTrack", killShotTrackJson);

        virtualViewJson.put("canMove", this.getCanMove());

        JSONArray reachableSquareJson = new JSONArray();
        for (Square square : this.getReachableSquare()) {
            reachableSquareJson.add(square.toJSON());
        }
        virtualViewJson.put("reachableSquare", reachableSquareJson);

        virtualViewJson.put("numberOfActions", this.getNumberOfActions());

        JSONArray availableWeaponsJson = new JSONArray();
        for (Weapon weapon : this.getAvailableWeapons()) {
            availableWeaponsJson.add(weapon.getType());
        }
        virtualViewJson.put("availableWeapons", availableWeaponsJson);

        virtualViewJson.put("typePlayerBoard", this.getTypePlayerBoard());
        virtualViewJson.put("suspended", false);

        return virtualViewJson;
    }

    /**
     * Resume virtual view from a saved match
     *
     * @param virtualViewToResume the virtual view to resume
     * @param resumedMatch        the resumed match
     * @return the virtual view resumed
     */
    public static VirtualView resumeVirtualView(JSONObject virtualViewToResume, Match resumedMatch) {
        VirtualView resumedVirtualView = new VirtualView();

        resumedVirtualView.username = (String) virtualViewToResume.get("username");
        resumedVirtualView.character = (String) virtualViewToResume.get("character");
        resumedVirtualView.position = ((Number) virtualViewToResume.get("position")).intValue();
        resumedVirtualView.damage = ((Number) virtualViewToResume.get("damage")).intValue();
        resumedVirtualView.points = ((Number) virtualViewToResume.get("points")).intValue();
        resumedVirtualView.phaseAction = ((Number) virtualViewToResume.get("phaseAction")).intValue();
        resumedVirtualView.finalFrenzy = ((Number) virtualViewToResume.get("finalFrenzy")).intValue();
        resumedVirtualView.typePlayerBoard = ((Number) virtualViewToResume.get("typePlayerBoard")).intValue();
        resumedVirtualView.suspended = (boolean) virtualViewToResume.get("suspended");

        JSONArray weaponsToResume = (JSONArray) virtualViewToResume.get("weapons");
        for (Object weaponToResume : weaponsToResume) {
            resumedVirtualView.weapons.add(AbstractWeapon.resumeWeapon((String) weaponToResume));
        }

        JSONArray powerUpsToResume = (JSONArray) virtualViewToResume.get("powerUps");
        for (Object powerUpToResume : powerUpsToResume) {
            resumedVirtualView.powerUps.add(PowerUp.resumePowerUp((JSONObject) powerUpToResume));
        }

        resumedVirtualView.cubes = Cubes.resumeCubes((JSONObject) virtualViewToResume.get("cubes"));

        resumedVirtualView.cabinetRed = WeaponSlot.resumeWeaponSlot((JSONObject) virtualViewToResume.get("cabinetRed"), resumedMatch);
        resumedVirtualView.cabinetYellow = WeaponSlot.resumeWeaponSlot((JSONObject) virtualViewToResume.get("cabinetYellow"), resumedMatch);
        resumedVirtualView.cabinetBlue = WeaponSlot.resumeWeaponSlot((JSONObject) virtualViewToResume.get("cabinetBlue"), resumedMatch);

        JSONArray markPlayerBoardToResume = (JSONArray) virtualViewToResume.get("markPlayerBoard");
        for (Object markToResume : markPlayerBoardToResume) {
            Player aggressorPlayerToResume = resumedMatch.searchPlayerByClientName((String) ((JSONObject) markToResume).get("aggressorPlayer"));
            int marksToResume = ((Number) ((JSONObject) markToResume).get("marks")).intValue();
            resumedVirtualView.markPlayerBoard.add(new EnemyMark(aggressorPlayerToResume, marksToResume));
        }

        JSONArray damagePlayerBoardToResume = (JSONArray) virtualViewToResume.get("damagePlayerBoard");
        for (Object damageToResume : damagePlayerBoardToResume) {
            Player damagingPlayerToResume = resumedMatch.searchPlayerByClientName((String) ((JSONObject) damageToResume).get("clientName"));
            resumedVirtualView.damagePlayerBoard.add(damagingPlayerToResume);
        }

        resumedVirtualView.deathsPlayerBoard = ((Number) virtualViewToResume.get("deathsPlayerBoard")).intValue();

        JSONArray killShotTrackToResume = (JSONArray) virtualViewToResume.get("killShotTrack");
        for (Object killShotToResume : killShotTrackToResume) {
            Player killerToResume = resumedMatch.searchPlayerByClientName((String) ((JSONObject) killShotToResume).get("clientName"));
            resumedVirtualView.damagePlayerBoard.add(killerToResume);
        }

        resumedVirtualView.canMove = (boolean) virtualViewToResume.get("canMove");
/*
        JSONArray reachableSquareToResume = (JSONArray) virtualViewToResume.get("");
        for (Object squareToResume : reachableSquareToResume) {
            resumedVirtualView.reachableSquare.add(Square.resumeSquare((JSONObject) squareToResume));
        }
*/
        resumedVirtualView.numberOfActions = ((Number) virtualViewToResume.get("numberOfActions")).intValue();

        JSONArray availableWeaponsToResume = (JSONArray) virtualViewToResume.get("availableWeapons");
        for (Object availableWeaponToResume : availableWeaponsToResume) {
            resumedVirtualView.availableWeapons.add(AbstractWeapon.resumeWeapon((String) availableWeaponToResume));
        }

        return resumedVirtualView;
    }
}
