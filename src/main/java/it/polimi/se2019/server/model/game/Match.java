package it.polimi.se2019.server.model.game;

import com.google.gson.Gson;

import it.polimi.se2019.server.model.cards.*;
import it.polimi.se2019.server.model.cards.powerUp.*;
import it.polimi.se2019.server.model.cards.weapons.*;
import it.polimi.se2019.server.model.map.Map;
import it.polimi.se2019.server.model.map.WeaponSlot;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.server.model.player.PlayerBoard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Match class is the core and root of the game's Model . It contains all the structures
 * used for the game to progress: weapons, power ups and ammo decks,
 * killshot track, the map, the list of players and so on.
 * @author mattiamassarini,merklin,thisisferra.
 */
public class Match implements Serializable {
    private ArrayList<Player> players;
    private ArrayList<PowerUp> powerUpStack;
    private ArrayList<Ammo> ammoStack;
    private ArrayList<Weapon> weaponStack;
    private ArrayList<PowerUp> discardedPowerUps;
    private ArrayList<Ammo> discardedAmmos;
    private int chosenMap;
    private Map map;
    private ArrayList<Player> killShotTrack = new ArrayList<>();
    private ArrayList<WeaponSlot> arsenal;
    private ArrayList<String> characterAvailable = new ArrayList<>();
    private ArrayList<Player> playersDead = new ArrayList<>();
    private boolean openConnection;
    private boolean finalFrenzyStatus;

    /**
     * Match constructor.
     * @param chosenMap the map chosen at the start of the game.
     */
    public Match(int chosenMap){
        this.chosenMap=chosenMap;
        this.arsenal = new ArrayList<>();
        this.discardedAmmos = new ArrayList<>();
        this.discardedPowerUps = new ArrayList<>();
        this.players = new ArrayList<>();
        this.openConnection = true;
        this.finalFrenzyStatus = false;

    }

    /**
     * This method contains the method to initialize the match structures.
     */
    public void initializeMatch(){
        this.initGameField();
        this.initCabinets();
        this.initializeCharacterAvailable();
    }

    /**
     * It fills characterAvailable list with all the characters.
     */
    public void initializeCharacterAvailable() {
        this.characterAvailable.add("distructor");
        this.characterAvailable.add("banshee");
        this.characterAvailable.add("dozer");
        this.characterAvailable.add("sprog");
        this.characterAvailable.add("violet");
    }

    /** Generate the game field:
     *  - the map is create calling the Map constructor, passing the mapId parameter.
     *  -weaponStack, powerUpStack and ammoStack are created here by reading the respective
     *  json files.
     *  The stacks are shuffled before the game starts.
     */
    private void initGameField(){
        weaponStack = new ArrayList<>();
        powerUpStack = new ArrayList<>();
        Ammo[] aS;
        map = new Map(this.chosenMap);
        map.setAllSquare();
        map.setRoomSquare();
        Gson gson = new Gson();
        Logger logger = Logger.getAnonymousLogger();
        try {
            aS = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/ammo.json")), Ammo[].class);

            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/plasma_gun.json")), PlasmaGun.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/electroscythe.json")), Electroscythe.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/lock_rifle.json")), LockRifle.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/whisper.json")), Whisper.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/zx-2.json")), ZX2.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/shockwave.json")), Shockwave.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/machine_gun.json")), MachineGun.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/flamethrower.json")), Flamethrower.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/furnace.json")), Furnace.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/hellion.json")), Hellion.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/shotgun.json")), Shotgun.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/power_glove.json")), PowerGlove.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/heatseeker.json")), Heatseeker.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/vortex_cannon.json")), VortexCannon.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/tractor_beam.json")), TractorBeam.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/railgun.json")), Railgun.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/sledgehammer.json")), Sledgehammer.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/cyberblade.json")), Cyberblade.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/rocket_launcher.json")), RocketLauncher.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/grenade_launcher.json")), GrenadeLauncher.class));
            weaponStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/thor.json")), Thor.class));


            for (int i = 0; i < 2; i++) {
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/targeting_scopeRed.json")), TargetingScope.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/targeting_scopeYellow.json")), TargetingScope.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/targeting_scopeBlue.json")), TargetingScope.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/teleporterRed.json")), Teleporter.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/teleporterYellow.json")), Teleporter.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/teleporterBlue.json")), Teleporter.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/tagback_grenadeRed.json")), TagbackGrenade.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/tagback_grenadeYellow.json")), TagbackGrenade.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/tagback_grenadeBlue.json")), TagbackGrenade.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/newtonRed.json")), Newton.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/newtonYellow.json")), Newton.class));
                powerUpStack.add(gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/newtonBlue.json")), Newton.class));
            }
            ammoStack = new ArrayList<>(Arrays.asList(aS));

            Collections.shuffle(weaponStack);
            Collections.shuffle(powerUpStack);
            Collections.shuffle(ammoStack);

        } catch (Exception e) {
            logger.log(Level.INFO,"Error while reading json files",e);
        }
    }

    /**
     * Initialize the red, yellow and blue cabinets.
     */
    private void initCabinets(){
        this.arsenal.add(new WeaponSlot("red",this));
        this.arsenal.add(new WeaponSlot("yellow",this));
        this.arsenal.add(new WeaponSlot("blue",this));
    }

    /**
     * Getter of allPlayers field.
     * @return the list of all players.
     */
    public ArrayList<Player> getAllPlayers() {
        return this.players;
    }

    /**
     * It draws a card from the ammo list's bottom.
     * @return a powerup from the list.
     */
    //if the stack isn't empty, it return the ammo card from the last position (arraylist size -1)
    public Ammo pickUpAmmoStack() {
        int size = ammoStack.size();
        return ammoStack.remove(size-1);
    }

    /**
     * It draws a card from the power up list's bottom.
     * @return a powerup from the list.
     */
    //if the stack isn't empty, it return the powerup card from the last position (arraylist size -1)
    public PowerUp pickUpPowerUp() {
        return powerUpStack.remove(powerUpStack.size() - 1);
    }

    /**
     * It draws a card from the weapons list's bottom.
     * @return a powerup from the list.
     */
    public Weapon pickUpWeapon(){
        return weaponStack.remove(weaponStack.size()-1);
    }

    /**
     * The discarded ammo goes on the discardedAmmos stack.
     * @param currentAmmo the ammo discarded.
     */
    // currentAmmo is discarded and saved in discardedAmmos
    public void discardAmmo(Ammo currentAmmo) {
        //currentAmmo is the last ammo I've picked up
        discardedAmmos.add(currentAmmo);

    }

    /**
     * Getter of the map reference.
     * @return the Map object of the map.
     */
    public Map getMap(){
        return this.map;
    }

    /**
     * Getter of the WeaponSlot list containing the colored cabinets.
     * @return the list of the colored cabinets(red, yellow and blue).
     */
    public ArrayList<WeaponSlot> getArsenal(){
        return this.arsenal;
    }

    /**
     * Add to the killshot track the reference of the player who made the kill.
     * @param dead the player who died.
     */
    public void addPlayerKillShot(Player dead){
        PlayerBoard playerBoard = dead.getPlayerBoard();
        if (playerBoard.getDamage().size() == 11) {
            this.killShotTrack.add(playerBoard.getDamage().get(10));
        }
        else if(playerBoard.getDamage().size() == 12) {
            this.killShotTrack.add(playerBoard.getDamage().get(11));
        }
    }

    /**
     * Getter of the killShotTrack field.
     * @return the killShotTrack list.
     */
    public ArrayList<Player> getKillShotTrack(){
        return this.killShotTrack;
    }

    /**
     * Getter of the powerUpStack field.
     * @return the powerUpStack list.
     */
    public ArrayList<PowerUp> getPowerUpStack(){
        return this.powerUpStack;
    }

    /**
     * Getter of the weaponStack field.
     * @return the weaponStack list.
     */
    public ArrayList<Weapon> getWeaponStack(){
        return this.weaponStack;
    }

    /**
     * Getter of the ammoStack field.
     * @return the ammoStack list.
     */
    public ArrayList<Ammo> getAmmoStack(){
        return this.ammoStack;
    }

    /**
     * Getter of the discardedAmmos field.
     * @return the discardedAmmos list.
     */
    public ArrayList<Ammo> getDiscardedAmmos(){
        return this.discardedAmmos;
    }

    /**
     * Getter of the discardedPowerUp field.
     * @return the discardedPowerUps list.
     */
    public ArrayList<PowerUp> getDiscardedPowerUps(){
        return this.discardedPowerUps;
    }

    /**
     * Search the Player reference based on its clientName.
     * @param clientName the name of player.
     * @return the Player reference  containing the clientName wanted, null otherwise.
     */
    public Player searchPlayerByClientName(String clientName) {
        for (Player player : this.getAllPlayers()) {
            if (player.getClientName().equals(clientName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Getter of the characterAvailableList.
     * @return the characterAvailable list.
     */
    public ArrayList<String> getCharacterAvailable() {
        return this.characterAvailable;
    }

    public void setPlayerDead() {
        for (Player player : this.getAllPlayers()) {
            if (player.getPlayerDead()) {
                this.playersDead.add(player);
            }
        }
    }

    public ArrayList<Player> getPlayersDead() {
        return this.playersDead;
    }

    /**
     * It saves the match state into a JSONObject object.
     * @return the JSONObject containing all the match's infos.
     */
    public JSONObject toJSON() {
        JSONObject matchJson = new JSONObject();

        JSONArray playersJson = new JSONArray();
        for (Player player : this.getAllPlayers()) {
            playersJson.add(player.toJSON());
        }
        matchJson.put("players", playersJson);

        JSONArray powerUpStackJson = new JSONArray();
        for (PowerUp powerUp : powerUpStack) {
            powerUpStackJson.add(powerUp.toJSON());
        }
        matchJson.put("powerUpStack", powerUpStackJson);

        JSONArray ammoStackJson = new JSONArray();
        for (Ammo ammo : ammoStack) {
            ammoStackJson.add(ammo.toJSON());
        }
        matchJson.put("ammoStack", ammoStackJson);

        JSONArray weaponStackJson = new JSONArray();
        for (Weapon weapon : this.getWeaponStack()) {
            weaponStackJson.add(weapon.getType());
        }
        matchJson.put("weaponStack", weaponStackJson);

        JSONArray discardedPowerUpsJson = new JSONArray();
        for (PowerUp powerUp : this.getDiscardedPowerUps()) {
            discardedPowerUpsJson.add(powerUp.toJSON());
        }
        matchJson.put("discardedPowerUps", discardedPowerUpsJson);

        JSONArray discardedAmmosJson = new JSONArray();
        for (Ammo ammo : this.getDiscardedAmmos()) {
            discardedAmmosJson.add(ammo.toJSON());
        }
        matchJson.put("discardedAmmos", discardedAmmosJson);

        matchJson.put("chosenMap", this.getMap().getMapID());

        matchJson.put("map", this.getMap().toJSON());

        JSONArray killShotTrackJson = new JSONArray();
        for (Player player : this.getKillShotTrack()) {
            killShotTrackJson.add(player.toJSON());
        }
        matchJson.put("killShotTrack", killShotTrackJson);

        JSONArray arsenalJson = new JSONArray();
        for (WeaponSlot weaponSlot : this.getArsenal()) {
            arsenalJson.add(weaponSlot.toJSON());
        }
        matchJson.put("arsenal", arsenalJson);

        JSONArray characterAvailableJson = new JSONArray();
        for (String playerName : this.getCharacterAvailable()) {
            characterAvailableJson.add(playerName);
        }
        matchJson.put("characterAvailable", characterAvailableJson);

        JSONArray playersDeadJson = new JSONArray();
        for (Player player : this.getPlayersDead()) {
            playersDeadJson.add(player.getClientName());
        }
        matchJson.put("playersDead", playersDeadJson);

        matchJson.put("finalFrenzyStatus", this.isFinalFrenzyStatus());

        return matchJson;
    }

    /**
     * Restore the match from a JSONObject object.
     * @param matchToResume the match to be restored
     * @param chosenMap the match's map id.
     * @return the Match object restored from the JSON file.
     */
    public static Match resumeMatch(JSONObject matchToResume, int chosenMap) {
        Match resumedMatch = new Match(chosenMap);
        JSONArray discardedPowerUpsToResume = (JSONArray) matchToResume.get("discardedPowerUps");
        for (Object discardedPowerUpToResume : discardedPowerUpsToResume) {
            resumedMatch.discardedPowerUps.add(PowerUp.resumePowerUp((JSONObject) discardedPowerUpToResume));
        }

        JSONArray discardedAmmosToResume = (JSONArray) matchToResume.get("discardedAmmos");
        for (Object discardedAmmoToResume : discardedAmmosToResume) {
            resumedMatch.discardedAmmos.add(Ammo.resumeAmmo((JSONObject) discardedAmmoToResume));
        }

        JSONArray weaponStackToResume = (JSONArray) matchToResume.get("weaponStack");
        resumedMatch.weaponStack = new ArrayList<>();
        for (Object weaponToResume : weaponStackToResume) {
            resumedMatch.weaponStack.add(AbstractWeapon.resumeWeapon((String) weaponToResume));
        }

        JSONArray powerUpStackToResume = (JSONArray) matchToResume.get("powerUpStack");
        resumedMatch.powerUpStack = new ArrayList<>();
        for (Object powerUpToResume : powerUpStackToResume) {
            resumedMatch.powerUpStack.add(PowerUp.resumePowerUp((JSONObject) powerUpToResume));
        }

        JSONArray ammoStackToResume = (JSONArray) matchToResume.get("ammoStack");
        resumedMatch.ammoStack = new ArrayList<>();
        for (Object ammoToResume : ammoStackToResume) {
            resumedMatch.ammoStack.add(Ammo.resumeAmmo((JSONObject) ammoToResume));
        }

        resumedMatch.map = Map.resumeMap((JSONObject) matchToResume.get("map"), chosenMap);

        JSONArray arsenalToResume = (JSONArray) matchToResume.get("arsenal");
        for (Object weaponSlotToResume : arsenalToResume) {
            resumedMatch.arsenal.add(WeaponSlot.resumeWeaponSlot((JSONObject) weaponSlotToResume, resumedMatch));
        }

        JSONArray characterAvailableToResume = (JSONArray) matchToResume.get("characterAvailable");
        for (Object character : characterAvailableToResume) {
            resumedMatch.characterAvailable.add((String) character);
        }

        JSONArray playersToResume = (JSONArray) matchToResume.get("players");
        for (Object playerToResume : playersToResume) {
            resumedMatch.players.add(Player.resumePlayer((JSONObject) playerToResume, resumedMatch));
        }

        //HERE ALL PLAYERS HAVE BEEN RESUMED EXCEPT FOR THEIR PLAYERBOARD!!

        //For each player, take his json playerBoard according to the clientName of the player and populate
        for (Player player : resumedMatch.players) {
            boolean foundPlayerBoard = false;
            for (int i = 0; i < playersToResume.size() && !foundPlayerBoard; i++) {
                if (player.getClientName().equals(((JSONObject) playersToResume.get(i)).get("clientName"))) {
                    player.reSetPlayerBoard((JSONObject) ((JSONObject) playersToResume.get(i)).get("playerBoard"), resumedMatch);
                    foundPlayerBoard = true;
                }
            }
        }

        //HERE ALL PLAYERS ARE READY!!

        JSONArray killShotTrackToResume = (JSONArray) matchToResume.get("killShotTrack");
        for (Object killToResume : killShotTrackToResume) {
            resumedMatch.killShotTrack.add(resumedMatch.searchPlayerByClientName((String) ((JSONObject) killToResume).get("clientName")));
        }

        JSONArray playersDeadToResume = (JSONArray) matchToResume.get("playersDead");
        for (Object playerDeadToResume : playersDeadToResume) {
            resumedMatch.playersDead.add(resumedMatch.searchPlayerByClientName((String) ((JSONObject) playerDeadToResume).get("clientName")));
        }

        resumedMatch.finalFrenzyStatus = (boolean) matchToResume.get("finalFrenzyStatus");

        return resumedMatch;

    }

    /**
     * Setter of the openConnection field.
     * @param openConnection true if the lobby is open, false otherwise.
     */
    public void setOpenConnection(boolean openConnection) {
        this.openConnection = openConnection;
    }

    /**
     * Getter of the openConnection field.
     * @return true if the match can accept new players, false otherwise.
     */
    public boolean getOpenConnection() {
        return this.openConnection;
    }

    /**
     * Setter of the final frenzy field.
     * @param finalFrenzyStatus is true if the game is in the final frenzy mode, false otherwise.
     */
    public void setFinalFrenzyStatus(boolean finalFrenzyStatus) {
        this.finalFrenzyStatus =finalFrenzyStatus;
    }

    /**
     * Getter of the finalFrenzyStatus field.
     * @return the true if the final frenzy is on, false otherwise.
     */
    public boolean isFinalFrenzyStatus() {
        return this.finalFrenzyStatus;

    }

    /**
     * Counts the number of players who are not suspended in a given time.
     * @return the number of active players.
     */
    public int numberPlayerNotSuspended() {
        int numberNotSuspended = 0;
        for (Player player : this.getAllPlayers()) {
            if (player.getSuspended())
                numberNotSuspended++;
        }
        return numberNotSuspended;
    }

}