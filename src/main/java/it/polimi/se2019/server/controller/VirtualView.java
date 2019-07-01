package it.polimi.se2019.server.controller;

import it.polimi.se2019.client.controller.GUIControllerInterface;
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


    //COSTRUTTORE
    public VirtualView(GUIControllerInterface clientReference) {
        this.clientReference = clientReference;
    }

    public VirtualView() {
        //Needed for resuming a virtualView from saved match
    }

    //GETTER

    public String getIpClient() {
        return this.ipClient;
    }

    public GUIControllerInterface getClientReference() {
        return this.clientReference;
    }

    public String getUsername() {
        return this.username;
    }

    public String getCharacter() {
        return this.character;
    }

    public int getPosition() {
        return this.position;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getNumberOfActions() {
        return this.numberOfActions;
    }

    public ArrayList<Square> getReachableSquare(){
        return this.reachableSquare;
    }

    public ArrayList<Weapon> getWeapons(){
        return this.weapons;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return this.powerUps;
    }

    public int getPhaseAction() {
        return phaseAction;
    }

    public int getFinalFrenzy(){
        return this.finalFrenzy;
    }

    public int getPoints() {
        return points;
    }

    public int getDeathsPlayerBoard(){
        return this.deathsPlayerBoard;
    }

    public ArrayList<EnemyMark> getMarkPlayerBoard(){
        return this.markPlayerBoard;
    }

    public ArrayList<Player> getDamagePlayerBoard(){
        return this.damagePlayerBoard;
    }

    public ArrayList<Player> getKillShotTrack(){
        return this.killShotTrack;
    }

    public Cubes getCubes(){
        return this.cubes;
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

    public WeaponShot getWeaponShot() { return
            this.weaponShot;
    }

    public ArrayList<Weapon> getAvailableWeapons(){
        return this.availableWeapons;
    }

    public boolean getCanMove(){
        return this.canMove;
    }

    public int getTypePlayerBoard(){
        return this.typePlayerBoard;
    }

    public boolean getSuspended() {
        return this.suspended;
    }

    //SETTER

    public void setClientReference(GUIControllerInterface  clientReference) {
        this.clientReference = clientReference;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public void setReachableSquare(ArrayList<Square> reachableSquare){
        this.reachableSquare = reachableSquare;
    }

    public void setPhaseAction(int phaseAction) {
        this.phaseAction = phaseAction;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }

    public void setDeathsPlayerBoard(int deathsPlayerBoard){
        this.deathsPlayerBoard = deathsPlayerBoard;
    }

    public void setFinalFrenzy(int finalFrenzy){
        this.finalFrenzy = finalFrenzy;
    }

    public void setCubes(Cubes cubes) {
        this.cubes = cubes;
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

    public void setWeaponShot(WeaponShot weaponShot) {
        this.weaponShot = weaponShot;
    }

    public void setAvailableWeapons(ArrayList<Weapon> availableWeapons){
        this.availableWeapons = availableWeapons;
    }

    public void setMarkPlayerBoard(ArrayList<EnemyMark> markPlayerBoard){
        this.markPlayerBoard = markPlayerBoard;
    }

    public void setDamagePlayerBoard(ArrayList<Player> damagePlayerBoard){
        this.damagePlayerBoard = damagePlayerBoard;
    }

    public void setKillShotTrack(ArrayList<Player> killShotTrack ){
        this.killShotTrack = killShotTrack;
    }

    public void setTypePlayerBoard(int typePlayerBoard) {
        this.typePlayerBoard = typePlayerBoard;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    //ALTRO

    public void reSetVirtualView(GUIControllerInterface guiControllerToResume) {
        this.clientReference = guiControllerToResume;
    }

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
