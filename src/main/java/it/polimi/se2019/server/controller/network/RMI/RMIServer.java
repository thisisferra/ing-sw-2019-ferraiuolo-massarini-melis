package it.polimi.se2019.server.controller.network.RMI;

/**
 * Do the main setup of the server
 * Manage the communication between server and client.
 * Manage the timer of setup and of the rounds.
 * Manage the logic of the game
 * @author Alessandro Ferraiuolo, Marco Melis
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import it.polimi.se2019.client.controller.network.RMI.GUIControllerInterface;
import it.polimi.se2019.server.OneAboveAll;
import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.controller.ShotController;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.Server;
import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.game.RoomChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.map.WeaponSlot;
import it.polimi.se2019.server.model.player.EnemyDamage;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.server.model.player.PlayerBoard;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//ritorno
public class RMIServer extends Server implements RMIServerInterface {

    //Attributi

    private PrintStream out = new PrintStream(System.out);

    private Player activePlayer;

    private Match match;

    private int endgameIndex;

    private int mapId = 0;

    private String ipAddress;

    private final int port; //for testing purpose

    private final String remObjName;

    private Registry rmiRegistry;

    private ArrayList<VirtualView> allVirtualViews = new ArrayList<>();

    private ShotController shotController;

    private transient Logger logger = Logger.getAnonymousLogger();

    private Timer initializeMatchTimer = new Timer();

    private Timer resetTimer = new Timer();

    private int interval = 10;

    private int roundTime = 300;

    private boolean existfile;

    //Metodi

    /**
     * Constructor used for testing purpose
     */
    public RMIServer() {
        //for testing purpose
        this.port = 0;
        this.remObjName = "";
    }

    /**
     * Construcotr of this class
     * @param oneAboveAll: the main class the creates this class
     * @param port: the port where the registry is registered.
     * @param remObjName: the name of the remote object.
     * @param roundTime: the lenght of a round in seconds.
     */
    public RMIServer(OneAboveAll oneAboveAll, int port, String remObjName, String roundTime) {
        super(oneAboveAll);
        this.roundTime = Integer.parseInt(roundTime);
        //System.setProperty("java.rmi.server.hostname","192.168.1.208");
        try {
            this.ipAddress = InetAddress.getLocalHost().getHostAddress().trim();
        }
        catch(UnknownHostException unknownHost) {
            logger.log(Level.INFO,"RMIServer constructor Error",unknownHost);
        }
        this.port = port;
        System.out.println("IP address: " + ipAddress + ":" + this.port);
        this.remObjName = remObjName;
    }

    /**
     * The setup method of RMI, it creates a registry and export the stub.
     */
    public void startServer() {
        try {
            System.out.println("Server is up");
            rmiRegistry = LocateRegistry.createRegistry(port);
            UnicastRemoteObject.exportObject(this, port);
            rmiRegistry.bind(remObjName, this);
            System.out.println("Registry created and object exported");
        } catch (Exception e) {
            logger.log(Level.INFO,"Error starting the server",e);
        }
    }

    /**
     * Verify if exists a saving file
     */
    public void checkSuspendedMatch() {
        File suspendedFile = new File("./AdrenalinaMatchData.json");

        if (existfile = suspendedFile.exists()) {

            System.out.println("Loading previous match data...");
            resumeRMIServer(suspendedFile);

        } else {
            System.out.println("There is no previous match! Start new game...");
        }
    }

    /**
     * Read from a JSON file the last status of this class to resume in case the player want to resume the match.
     * @param suspendedFile: the file to be read.
     */
    public void resumeRMIServer(File suspendedFile) {

        try (FileReader fr = new FileReader(suspendedFile)) {
            JSONParser parser = new JSONParser();
            JSONObject jsonMatchData = (JSONObject) parser.parse(fr);

            this.mapId = ((Number) jsonMatchData.get("mapId")).intValue();
            //this.match = Match.resumeMatch((JSONObject) jsonMatchData.get("match"), this.mapId, new Match(this.mapId));
            this.match = Match.resumeMatch((JSONObject) jsonMatchData.get("match"), this.mapId);
            this.shotController = new ShotController(this.match);
            this.activePlayer = this.match.searchPlayerByClientName((String) jsonMatchData.get("activePlayer"));

            JSONArray allVirtualViewsToResume = (JSONArray) jsonMatchData.get("allVirtualViews");
            for(Object virtualViewToResume : allVirtualViewsToResume) {
                this.allVirtualViews.add(VirtualView.resumeVirtualView((JSONObject) virtualViewToResume, this.match));
            }

            logger.log(Level.INFO, "Match recovered! Latching all players...");
        } catch (Exception e) {
            logger.log(Level.INFO,"resumeRMIServer error",e);
        }
    }

    /**
     * Method called when a user want to join to a match. It verify of there is already a created match, if no create it.
     * Start the timer of setup, create a new virtualView and a new player and initialize all all the virtualView and call the related method
     * to update the remoteView.
     * @param username: the name choose by the user.
     * @param guiController: the reference of the client
     * @param mapId: the number of the mao
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public synchronized void register(String username, GUIControllerInterface guiController,int mapId) throws RemoteException{
        if (!mapAlreadySelected()) {
            this.createMatch(mapId);
            this.startingMatchTimer();

        }
        VirtualView virtualView = createVirtualView(guiController);
        Player player = createPlayer(username);
        virtualView.updateVirtualView(player, match);
        this.printClientConnected();
        this.initAllClient();
        if (activePlayer == null) {
            activePlayer = match.getAllPlayers().get(0);
            activePlayer.setFirstPlayer(true);
        }
        if (this.match.getOpenConnection()) {
            virtualView.getClientReference().waitPlayers();
        }
    }

    /**
     *
     * @param username
     * @param guiControllerToResume
     * @throws RemoteException
     */
    public synchronized void login(String username, GUIControllerInterface guiControllerToResume) throws RemoteException {
        ArrayList<Player> allPlayers = this.match.getAllPlayers();
        boolean foundPlayer = false;
        for (int i = 0; i < allPlayers.size() && !foundPlayer; i++) {
            if (username.equals(allPlayers.get(i).getClientName())) {
                searchVirtualViewByClientName(username).reSetVirtualView(guiControllerToResume);
                guiControllerToResume.restoreRemoteView(searchVirtualViewByClientName(username));
                foundPlayer = true;
            }
        }

        if (!foundPlayer) {
            guiControllerToResume.recallLoginScene();
        }

        //ALLVIRTUALVIEWS == MATCH.PLAYERS.SIZE? ALLORA CHIAMA METODO CHE AGGIORNA TUTTE LE REMOTE VIEW (GUI) !!
        if (playersReconnected() == allVirtualViews.size()) {
            for (VirtualView virtualView : allVirtualViews) {
                virtualView.getClientReference().restoreGUI();
            }
            initAllClient();
            setResetTimer();
        }
    }

    /**
     * Count the number of player reconnected
     * @return The number of player reconnected
     */
    public int playersReconnected() {
        int reconnections = 0;
        for (VirtualView virtualView : allVirtualViews) {
            if (virtualView.getClientReference() != null) {
                reconnections++;
            }
        }

        return reconnections;
    }

    /**
     * search inside allVirtualView ArrayList the virtualView with the same username passed as parameter.
     * @param clientName: the username that has to be the same.
     * @return the virtualView found.
     */
    public VirtualView searchVirtualViewByClientName(String clientName) {
        for (VirtualView virtualView : this.allVirtualViews) {
            if (virtualView.getUsername().equals(clientName)) {
                return virtualView;
            }
        }
        logger.log(Level.WARNING, clientName+"'s virtualView not found!!");
        return null;
    }

    /**
     * Verify if the clientName specified as parameter is already useb by other player.
     * @param username: the username typed by the user.
     * @return "NotUsed" if nobody typed the specified username.
     * @return "AlreadyUsed" if someone typed the specified username.
     * @return "Reconnect" if openConnection attribute is false and the user has typed a username present in the match.
     * @return "CantConnect" if openConnection attribute is false and the user has typed a username not present in the match.
     * @throws Exception if the method can't do the remote call to the client.
     */
    public synchronized String checkUsername2(String username) throws Exception {
        if (match != null) {
            if (match.getOpenConnection()) {
                for (Player player : match.getAllPlayers()) {
                    if(player.getClientName().equals(username)) {
                        return "AlreadyUsed";
                    }
                }
                return "NotUsed";
            }
            else {
                for (Player player : match.getAllPlayers()) {
                    if (player.getClientName().equals(username)) {
                        return "Reconnect";
                    }
                }
                return "CantConnect";
            }
        }
        return "NotUsed";
    }

    /**
     * Compute the reachable square from a specified position with a max number of steps.
     * @param position: the starting position
     * @param steps: max number of steps.
     * @return An ArrayList cointaining all the reachable square.
     */
    public ArrayList<Square> reachableSquares(int position, int steps){
        MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(), steps, position);
        return movementChecker.getReachableSquares();
    }

    /**
     * retrieve all the square of the map.
     * @return a vector containing all the square.
     */
    public Square[] getAllSquares(){
        return match.getMap().getAllSquare();
    }

    /**
     * Call the related model method to pick up an ammo and update all the virtual view.
     * @param username: the username of the player who picked up the ammo.
     * @throws RemoteException
     */
    public void pickUpAmmo(String username) throws RemoteException{
        Player player = match.searchPlayerByClientName(username);
        Square square = match.getMap().searchSquare(player.getPosition());
        player.pickUpAmmo(square, match);
        updateAllVirtualView();
        this.updateAllClient();
    }

    /**
     * Retrieve the last ammo that was discarded
     * @return the last ammo discarded
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public Ammo showLastAmmo() throws RemoteException{
        if(!match.getDiscardedAmmos().isEmpty())
            return match.getDiscardedAmmos().get(match.getDiscardedAmmos().size()-1);
        else return null;
    }

    /**
     * Set the new position of the player identified by the username.
     * @param username: the username of the player.
     * @param newPosition: the new position of the player.
     */
    public void setNewPosition(String username, int newPosition){
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.setPosition(newPosition);
        try {
            getMyVirtualView(username).setPosition(currentPlayer.getPosition());
            getMyVirtualView(username).setNumberOfActions(currentPlayer.getNumberOfAction());
            updateAllVirtualView();
            updateAllClient();

        } catch (Exception e) {
            logger.log(Level.INFO,"SetNewPosition Error");
        }
    }

    /**
     * Print the stub of all client that are registered to the match.
     */
    public void printClientConnected() {
        int size = allVirtualViews.size();
        out.println(allVirtualViews.get(size - 1).getUsername() + "---" + allVirtualViews.get(allVirtualViews.size() - 1).getClientReference());
    }

    /**
     * Scan allVirtualView ArrayList to gain the clientRef and call the remote method to init the remote view.
     * @throws RemoteException: if the method can't do the remote call to the client.
     */
    public void initAllClient() throws RemoteException{
        for (VirtualView virtualView : allVirtualViews) {
            GUIControllerInterface clientRef = virtualView.getClientReference();
            clientRef.initRemoteView(allVirtualViews);
        }

    }

    /**
     * Scan virtualView ArrayList and search the virtualView that has the same username passed as parameter.
     * @param username: the username to compare with virtualView username.
     * @return the virtualView with the same username of the parameter, null if there is any VirtualView with the same username.
     */
    public VirtualView getMyVirtualView(String username) {
        for(VirtualView virtualView : allVirtualViews) {
            if (virtualView.getUsername().equals(username)) {
                return  virtualView;
            }
        }
        return null;
    }

    /**
     * Scan all virtualView to gain the clientRef and for each client update call the remote method to update the remote view,
     * @throws RemoteException
     */
    public void updateAllClient() throws RemoteException{
        try {
            pingAllClient();
            for (VirtualView virtualView : allVirtualViews) {
                if (!this.match.searchPlayerByClientName(virtualView.getUsername()).getSuspended()) {
                    GUIControllerInterface clientRef = virtualView.getClientReference();
                    clientRef.update(allVirtualViews);
                }
            }
        } catch(RemoteException remException) {
            logger.log(Level.SEVERE, "Can't connect to the client");
        }
    }

    /**
     * Check if the current square identified by the position is a spawn point
     * @param position: the integer that represents the square
     * @return true if the square is a spawn point, false otherwise.
     */
    public boolean isSpawnPoint(int position) {
        if (match.getMap().searchSquare(position).isSpawnPoint()) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Call the related model method to pick up a weapon in case the player has less than three weapon in his hand.
     * @param username: username of the player who want to pick up the weapon.
     * @param indexToPickUp: index of the weapon to pick up.
     */
    public void pickUpWeapon(String username, int indexToPickUp){
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.pickUpWeapon(indexToPickUp);
        updateAllVirtualView();
        try {
            updateAllClient();
        } catch (RemoteException remException) {
            logger.log(Level.INFO, "Can't call the client");
        }

    }

    /**
     * Call the related model method to pick up a weapon in the case the player has already three weapon in his hand.
     * @param username: the username of the player who want to pick up the weapon
     * @param indexToPickUp: index of the weapon to pick up
     * @param indexToDrop: index of the weapon to switch
     * @throws RemoteException: if the method can't do the remote call to the client.
     */
    public void pickUpWeapon(String username, int indexToPickUp, int indexToDrop) throws RemoteException{
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.pickUpWeapon(indexToPickUp,indexToDrop);
        getMyVirtualView(username).setWeapons(currentPlayer.getHand().getWeapons());
        getMyVirtualView(username).setCubes(currentPlayer.getPlayerBoard().getAmmoCubes());
        updateAllVirtualView();
        updateAllClient();

    }

    /**
     * Call restoreTile and restoreWeaponSlot method.
     * @throws RemoteException
     */
    public void restoreMap()throws RemoteException{
        restoreTile();
        restoreWeaponSlot();
        updateAllVirtualView();
        updateAllClient();
    }

    /**
     * Scan all square in the map and refill it if there wasn't an ammo.
     */
    private void restoreTile() {
        for(Square square : match.getMap().getAllSquare()) {
            if (!square.isSpawnPoint()) {
                square.setFull(true);
            }
        }
    }

    /**
     * Scan all weaponSlot in the match and for each slot assign a new weapon picked uo from the weaponStack if it was null.
     */
    private void restoreWeaponSlot() {
        for (WeaponSlot weaponSlot : match.getArsenal()) {
            for (int i = 0; i < 3; i++) {
                if (weaponSlot.getSlot()[i] == null) {
                    Weapon newWeapon = match.pickUpWeapon();
                    weaponSlot.getSlot()[i] = newWeapon;
                }
            }
        }
    }

    /**
     * Check if the mapId variable is already set
     * @return true if mapID is already selected false otherwise
     */
    protected boolean mapAlreadySelected() {
        return (mapId != 0);
    }

    /**
     * Create a new matxh with the specified mapID and initialize it
     * @param mapId: the number of the map
     */
    private void createMatch(int mapId) {
        match = new Match(mapId);
        this.mapId = mapId;
        match.initializeMatch();
        this.shotController = new ShotController(this.match);
        out.println("Match created");
    }

    /**
     * Create a new VirtualView and add it to the allVirtualView ArrayList
     * @param guiController: the regerence of the client
     * @return the virtualView created
     */
    private VirtualView createVirtualView(GUIControllerInterface guiController){
        VirtualView virtualView = new VirtualView(guiController);
        allVirtualViews.add(virtualView);
        return virtualView;
    }

    /**
     * Create a new player and add it to allPlayers ArrayList.
     * @param username: the username of the player to be added.
     * @return the player created
     */
    private Player createPlayer(String username) {
        Player player = new Player(username, match);
        this.match.getAllPlayers().add(player);
        player.pickUpPowerUp();
        player.pickUpPowerUp();
        return player;
    }

    /**
     * Retrieve the match varibale
     * @return the match variable
     */
    public Match getMatch() {
        return this.match;
    }

    /**
     * Check if a player has actions availables.
     * @param username: the username of the current player
     * @return true if the player has at least one action, false otherwise
     */
    public boolean checkNumberAction(String username) {
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        if (currentPlayer.getNumberOfAction() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Increase by one the number of actions of the specified player
     * @param username: the current player
     * @throws RemoteException: if the method can't do the remote call to the client.
     */
    public void giftAction(String username) throws RemoteException{
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.increaseNumberOfAction();
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    /**
     * Decrease the number of action of a player
     * @param username: the current player
     * @throws RemoteException: if the method can't do the remote call to the client.
     */
    public void useAction(String username) throws RemoteException{
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        currentPlayer.decreaseNumberOfAction();
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    /**
     * Set the active player
     * @param usernameLastPlayer: the name of the player to assign to activePlayer variable
     */
    public void setActivePlayer(String usernameLastPlayer) throws RemoteException{
        Player currentPlayer = this.match.searchPlayerByClientName(usernameLastPlayer);
        currentPlayer.setFirstSpawn(false);
        ArrayList<Player> notSuspendedPlayers = new ArrayList<>();
        for (Player player : this.match.getAllPlayers()) {
            player.setCanMove(true);
            if (!player.getSuspended())
                notSuspendedPlayers.add(player);
        }
        int size = notSuspendedPlayers.size();
        int index = 0;
        while(!usernameLastPlayer.equals(notSuspendedPlayers.get(index).getClientName())) {
            index++;
        }
        if (index == size - 1) {
            activePlayer = notSuspendedPlayers.get(0);
        }
        else {
            activePlayer = notSuspendedPlayers.get(index + 1);
        }
        activePlayer.setCanMove(false);
        activePlayer.clearHitThisTurnPlayers();
        this.match.searchPlayerByClientName(usernameLastPlayer).getLastDamagingPlayers().clear();
        updateAllVirtualView();
        updateAllClient();
        roundTime = 300;
        if (activePlayer.getFirstSpawn()) {
            getMyVirtualView(activePlayer.getClientName()).getClientReference().respawnDialog();
            activePlayer.setFirstSpawn(false);
        }
        if (this.match.isFinalFrenzyStatus()) {
            endgameIndex++;
            if(endgameIndex == match.getAllPlayers().size()+1){
                this.finishMatch();
            }
        }
}

    /**
     * Set the active player
     * @param player: the player to assign to activePlayer variable
     */
    public void setSpecificActivePlayer(Player player) {
        this.activePlayer = player;

    }

    /**
     * Retrieve the active player.
     * @return
     */
    public Player getSpecificActivePlayer() {
        return this.activePlayer;
    }

    /**
     * Retrieve the active player.
     * @return
     */
    public ArrayList<Player> getKillShotTrack() {
        return match.getKillShotTrack();
    }

    /**
     * retrieve the active player.
     * @return
     */
    public String getActivePlayer() {
        return this.activePlayer.getClientName();
    }

    /**
     * Reset the number of action of a player at the end of his round.
     * @param username
     * @throws RemoteException
     */
    public void resetActionNumber(String username) throws RemoteException{
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        currentPlayer.resetNumberOfAction();
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    /**
     * Call the the shotController method "checkAll" that verify which weapon a player can use in his round.
     * @param username: the current player
     * @return
     */
    public ArrayList<Weapon> verifyWeapons(String username) {
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        return getShotController().checkAll(currentPlayer);
    }

    /**
     * Get the shotController variable
     * @return
     */
    public ShotController getShotController() {
        return this.shotController;
    }

    /**
     * Retfrieve the useful info about damaging player, targeting player, which weapon the player want to use from the WeaponShot object.+
     * Apply the effect of the weapon calling the related model method and decrease the number of cubes
     * @param weaponShot: object that contains all the info about the weapon and its effect.
     * @throws RemoteException: if the method can't do the remote call to the client.
     */
    public void applyEffectWeapon(WeaponShot weaponShot) throws RemoteException{
        WeaponShot newWeaponShot = new WeaponShot();
        for(Player target : weaponShot.getTargetPlayer()){
            newWeaponShot.getTargetPlayer().add(match.searchPlayerByClientName(target.getClientName()));
        }
        newWeaponShot.setNameEffect(weaponShot.getNameEffect());
        newWeaponShot.setWeapon(weaponShot.getWeapon());
        newWeaponShot.setDamagingPlayer(match.searchPlayerByClientName(weaponShot.getDamagingPlayer().getClientName()));
        newWeaponShot.setNewPosition(weaponShot.getNewPosition());
        weaponShot.getWeapon().applyEffect(newWeaponShot);
        unloadWeapon(newWeaponShot);
        for(int i = 0; i< newWeaponShot.getWeapon().getEffect().length; i++){
            if(newWeaponShot.getWeapon().getEffect()[i] != null){
                if(newWeaponShot.getNameEffect().equals(newWeaponShot.getWeapon().getEffect()[i].getNameEffect()))
                    newWeaponShot.getDamagingPlayer().getPlayerBoard().payCubes(
                            newWeaponShot.getWeapon().getEffect()[i].getExtraCost()
                    );
            }
        }
        updateAllVirtualView();
        updateAllClient();
    }

    /**
     * Call the related model method to trade a powerUp in change of a cubes.
     * @param index: index of the powerUp to discard
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public void tradeCube(int index) throws RemoteException{
        activePlayer.tradeCube(index);
        this.updateAllVirtualView();
        this.updateAllClient();

    }

    /**
     * Method that removes from the hand of the specified player the powerUp related to the index passed as parameter
     * and add it to the discardedPowerUp ArrayList
     * It sets the new position of the player after the respawn in base of the color of the powerUp.
     * @param username: the player dead
     * @param index: index of the powerUp in the hand of the player.
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public void discardAndSpawn(String username,int index) throws RemoteException{
        Player player = this.match.searchPlayerByClientName(username);
        PowerUp discardedPowerUp = player.getHand().getPowerUps().remove(index);

        switch (discardedPowerUp.getColor()){
            case "red" : {
                this.setNewPosition(username,4);
                break;
            }
            case "blue" : {
                this.setNewPosition(username,2);
                break;
            }
            case "yellow" : {
                this.setNewPosition(username,11);
                break;
            }
            default:
                System.out.println("ERRORE");
        }
        match.getDiscardedPowerUps().add(discardedPowerUp);
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    /**
     * Method that return which weapons the player can reload
     * @param username: the current player
     * @return An ArrayList containing the weapon the player can reload.
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public ArrayList<Weapon> getReloadableWeapons(String username) throws RemoteException{
        Player player = this.match.searchPlayerByClientName(username);
        return player.getReloadableWeapons();
    }

    /**
     * Set to true the variable load of the weapon
     * @param username: the current player
     * @param index: index of the weapon to be reload
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public void reloadWeapon(String username ,int index) throws RemoteException{
        Player player = this.match.searchPlayerByClientName(username);
        Weapon weaponToReload = player.getHand().getWeapons().get(index);
        weaponToReload.setLoad(true);
        player.getPlayerBoard().payCubes(weaponToReload.getReloadCost());
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    /**
     * Check if the player has some weapon in his hand
     * @param username: the current player
     * @return true if the player has some weapon in his hand, false otherwise
     */
    public boolean checkSizeWeapon(String username) {
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        return !currentPlayer.getHand().getWeapons().isEmpty();

    }

    /**
     * Method that call the related model method to apply the effect of a powerUp.
     * It sets the targeting player, the damaging player and removed the powerUp from the hand of the player.
     * @param username: the player who want to use the powerUp
     * @param index: index in the hand of the powerUp
     * @param powerUpShot: object that contains all the useful info about the usage of the powerUp
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public void usePowerUp(String username, int index, PowerUpShot powerUpShot) throws RemoteException{
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        Player targetPlayer = this.match.searchPlayerByClientName(powerUpShot.getTargetingPlayer().getClientName());
        powerUpShot.setTargetingPlayer(targetPlayer);
        powerUpShot.setDamagingPlayer(currentPlayer);
        PowerUp powerUp = currentPlayer.getHand().getPowerUps().get(index);
        powerUp.applyEffect(powerUpShot);
        match.getDiscardedPowerUps().add(currentPlayer.getHand().getPowerUps().remove(index));
        this.updateAllVirtualView();
        this.updateAllClient();

    }

    /**
     * Method that call the related model method to decrease the number of cubes that a player has.
     * @param username the current Player
     * @param reds number of red cubes
     * @param yellows number of yellows cubes
     * @param blues number of blues cubes
     */
    public void payCubes(String username,int reds, int yellows, int blues){
        Cubes cubesToPay = new Cubes(reds,yellows,blues);
        getMatch().searchPlayerByClientName(username).getPlayerBoard().payCubes(cubesToPay);
        updateAllVirtualView();
        try{
            updateAllClient();
        }catch (RemoteException e){
            logger.log(Level.INFO,"PayCubes method error",e);
        }
    }

    /**
     * Method that scan all the virtualView to update the data.
     */
    public void updateAllVirtualView() {
        for (Player player : this.match.getAllPlayers()) {
            for (VirtualView virtualView : allVirtualViews) {
                if (player.getClientName().equals(virtualView.getUsername())) {
                    virtualView.updateVirtualView(player, match);
                }
            }
        }

    }

    /**
     * Method that scan all player in search of players who are dead.
     * Verify if is the time to start the final frenzy.
     * @param username Username of the player who do the lsk kill.
     * @return a flag that is True if there was a kill, false otherwise.
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public boolean deathPlayer(String username) throws RemoteException {
        boolean flagDeath = false;
        for (Player player : match.getAllPlayers()) {
            if (player.getPlayerDead()) {
                flagDeath = true;
                match.addPlayerKillShot(player);
                assignPoints(player);
                player.getPlayerBoard().setDeaths();
                player.setPhaseAction(0);
                if (this.match.isFinalFrenzyStatus()) {
                    player.setTypePlayerBoard(1);
                }
            }
        }
        if (this.match.getKillShotTrack().size() == 1 && !this.match.isFinalFrenzyStatus()) {
            this.match.setFinalFrenzyStatus(true);
            this.enableFinalFrenzy(username);
        }
        updateAllVirtualView();
        updateAllClient();
        return flagDeath;
    }

    /**
     * Method that computes how many points each player has to gain from a kill.
     * @param player the player that is dead.
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public void assignPoints(Player player) throws RemoteException{
        PlayerBoard playerBoard = player.getPlayerBoard();
        int indexPointDeaths = 0;
        for (EnemyDamage enemyDamagePoints : playerBoard.getEnemyDamages()) {
            Player aggressorPlayer = enemyDamagePoints.getAggressorPlayer();
            if (indexPointDeaths <= player.getPlayerBoard().getPointDeaths().size()) {
                aggressorPlayer.addPoints(player.getPlayerBoard().getPointDeaths().get(indexPointDeaths));
            }
            else {
                aggressorPlayer.addPoints(1);
            }
            indexPointDeaths += 1;
        }
        playerBoard.getPointDeaths().remove(0);
        playerBoard.clearDamage();
        updateAllVirtualView();
        try {
            updateAllClient();
        } catch(RemoteException remException) {
            logger.log(Level.SEVERE, "Can't connect to the client");
        }

    }

    /**
     * Set the load property of a weapon to False
     * @param weaponShot object that contains the info of which weapons has to be unloaded.
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    private void unloadWeapon(WeaponShot weaponShot) throws RemoteException{
        for(Weapon weapon : match.searchPlayerByClientName(weaponShot.getDamagingPlayer().getClientName()).getHand().getWeapons()){
            if(weaponShot.getWeapon().getType().equals(weapon.getType()))
                weapon.setLoad(false);
        }
        updateAllVirtualView();
        updateAllClient();
    }

    /**
     * Scan all player, if a player is set to dead and not suspended, he pick up a power up,
     * then choose one to discard and respawn in the spawn point of the color of the powerUp.
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public void respawnPlayer() throws  RemoteException{
        for (Player playerToRespawn : this.match.getAllPlayers()) {
            if (playerToRespawn.getPlayerDead() && !playerToRespawn.getSuspended()) {
                this.setSpecificActivePlayer(playerToRespawn);
                playerToRespawn.pickUpPowerUpToRespawn();
                getMyVirtualView(playerToRespawn.getClientName()).getClientReference().respawnDialog();
                playerToRespawn.setPlayerDead(false);
            }
            if (playerToRespawn.getPlayerDead() && playerToRespawn.getSuspended()) {
                playerToRespawn.pickUpPowerUpToRespawn();
                int sizePowerUp = playerToRespawn.getHand().getPowerUps().size();
                double randomNumber = (int) (Math.random() * sizePowerUp);
                int intRandomNumber = (int) randomNumber;
                this.discardAndSpawn(playerToRespawn.getClientName(), intRandomNumber);
                playerToRespawn.setPlayerDead(false);
            }
        }
        updateAllVirtualView();
        updateAllClient();
    }

    /**
     * Do a remote call to each client to notify them that a user moves himself.
     * @param username of the active player.
     * @param newPosition the new position the player.
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public void notifyAllClientMovement(String username, Integer newPosition) throws  RemoteException{
        String movementMessage = username + " new position is " + newPosition;
        for (VirtualView virtualView : allVirtualViews) {
            if (!virtualView.getSuspended()) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                clientRef.showMessage(movementMessage);
            }
        }
    }

    /**
     * Do a remote call to each client to notify them that a user picked up a weapon.
     * @param username of the player who picked up the weapon.
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public void notifyAllClientPickUpWeapon(String username) throws RemoteException{
        String pickUpWeaponMessage = username + " pick up a weapon";
        for (VirtualView virtualView : allVirtualViews) {
            if (!virtualView.getSuspended()) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                clientRef.showMessage(pickUpWeaponMessage);
            }
        }

    }

    /**
     * Do a remote call to each client to notify them that a user picked up an ammo.
     * @param username of the player who picked up the ammo.
     * @param lastAmmo the ammo thet the player has picked up
     * @throws RemoteException if the method can't do the remote call to the client.
     */
    public void notifyAllClientPickUpAmmo(String username, String lastAmmo) throws RemoteException {
        String pickUpWeaponMessage = username + " picked up an ammo:\n" + lastAmmo;
        for (VirtualView virtualView : allVirtualViews) {
            if (!virtualView.getSuspended()) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                clientRef.showMessage(pickUpWeaponMessage);
            }
        }

    }

    /**
     * Do a remote call to each client to notify them that a user has disconnected from the match.
     * @param username of player who is disconnected.
     * @throws RemoteException if the method can't do the remote call to the client
     */
    public void notifyAllClientUserDisconnect(String username) throws RemoteException{
        String clientDisconnect = username + " disconnected from the match\n";
        for (VirtualView virtualView : allVirtualViews) {
            if (!virtualView.getSuspended()) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                clientRef.showMessage(clientDisconnect);
            }

        }
    }

    /**
     *
     * @param username
     * @throws RemoteException
     */
    public void toggleAction(String username) throws RemoteException{
        match.searchPlayerByClientName(username).setCanMove(!match.searchPlayerByClientName(username).getCanMove());
        updateAllVirtualView();
        updateAllClient();
    }

    /**
     * Set the canMove variable of a player to the parameter canMove
     * @param username username of the player
     * @param canMove boolean that indicates if a player can move or not.
     * @throws RemoteException if the method can't do the remote call to the client-
     */
    public void setCanMove(String username,boolean canMove) throws RemoteException{
        match.searchPlayerByClientName(username).setCanMove(canMove);
        updateAllVirtualView();
        updateAllClient();
    }

    /**
     * The method set in a roght way how many action and which type of action a user can do during the final frenzy.
     * @param username the username of the player who do the last kill.
     * @throws RemoteException if the method can't do the remote call to the client
     */
    public void  enableFinalFrenzy(String username) throws RemoteException {
        boolean flag = false;
        for (Player player : match.getAllPlayers()) {
            player.setPhaseAction(0);
            if (flag) {
                player.setFinalFrenzy(2);
            } else
                player.setFinalFrenzy(1);
            if (player.getPlayerBoard().getDamage().isEmpty())
                player.setTypePlayerBoard(1);
            if (player.getClientName().equals(username)) {
                flag = true;
            }
        }
        updateAllVirtualView();
        updateAllClient();

    }

    /**
     * Scan all virtualView to gain the clientRef of each client. The method do a remote call to each client to verify that the client is still alive.
     */
    public void pingAllClient() {
        for (VirtualView virtualView : allVirtualViews) {
            GUIControllerInterface clientRef = virtualView.getClientReference();
            int attempt = 100;
            try {
                attempt = clientRef.pingClient();
            }
            catch (RemoteException remException) {
                this.match.searchPlayerByClientName(virtualView.getUsername()).setSuspended(true);
            }
            if (attempt != 13) {
                this.match.searchPlayerByClientName(virtualView.getUsername()).setSuspended(true);
            }
        }
    }

    /**
     * Method that implement the reconnection when a user is disconnected from a match.
     * The clientRed of the player is switched with a new one.
     * @param usernameTyped the username of the player who wants to reconnect.
     * @param guiController The new clientRef
     * @throws RemoteException if the method can't do the remote call to the client
     */
    public void reconnect(String usernameTyped, GUIControllerInterface guiController) throws RemoteException {
        for (VirtualView virtualView : allVirtualViews) {
            if (virtualView.getUsername().equals(usernameTyped)) {
                this.match.searchPlayerByClientName(usernameTyped).setSuspended(false);
                virtualView.setClientReference(guiController);
                this.initAllClient();
                virtualView.getClientReference().update(allVirtualViews);

                System.out.println("Client " + usernameTyped + " reconnect");

            }
        }

    }

    /**
     * Implement a timer that each second check how many user are registred to the match.
     * If there is three players the timer is deleted and the setMatchTimer is called.
     */
    public void startingMatchTimer() {
        Timer startingMatchTimer = new Timer();
        startingMatchTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (match.getAllPlayers().size() == 3) {
                    setMatchTimer();
                    startingMatchTimer.cancel();

                }
            }
        }, 0, 1000);
    }

    /**
     * Implement a timer that each second call the countMatchTimer method.
     */
    public void setMatchTimer() {
        initializeMatchTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    countMatchTimer();
                }
                catch(RemoteException remException) {
                    logger.log(Level.INFO,"setMatchTimer error",remException);
                }

            }
        }, 0, 1000);
    }

    /**
     * Each second the interval variable is decreased, if it is equals to 1, the openConnection variable of the match is set to false.
     * It means that nobody can connect him to the current match. The method browse each virtualView saved into the ArrayList allVirtualView
     * to gain the clientRef and call a method that shows the GUI on each client.
     * @return the interval variable decreased by 1.
     * @throws RemoteException if the method can't do the remote call to the client
     */
    private final int countMatchTimer() throws RemoteException{
        if (interval == 1) {
            this.match.setOpenConnection(false);
            System.out.println("Match openConnection: " + this.match.getOpenConnection());
            for (VirtualView virtualView : allVirtualViews) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                if(this.match.searchPlayerByClientName(virtualView.getUsername()).isFirstPlayer()) {
                    clientRef.respawnDialog();
                    clientRef.startingMatch();
                }
                else {
                    clientRef.startingMatch();
                }
            }
            setResetTimer();
            initializeMatchTimer.cancel();
        }
        return --interval;
    }

    /**
     * passRoundTimer is called when roundTimer variable is equals to 1.
     * It means the current player has not pass the round so he become suspended. The round is passed to the next player not suspended.
     * If in the match remains only two player tha match will finish.
     * @throws RemoteException if the method can't do a remote call to the client.
     */
    public void passRoundTimer() throws RemoteException{
        String usernameLastPlayer = activePlayer.getClientName();
        GUIControllerInterface clientRef = getMyVirtualView(usernameLastPlayer).getClientReference();
        try {
            setActivePlayer(usernameLastPlayer);
        } catch (RemoteException remException) {
            logger.log(Level.INFO,"passRoundTimer error",remException);
        }
        match.searchPlayerByClientName(usernameLastPlayer).setSuspended(true);
        updateAllVirtualView();
        try {
            updateAllClient();
        } catch (RemoteException remException1) {
            logger.log(Level.INFO, "Errore nella chiamata");
        }
        this.notifyAllClientUserDisconnect(usernameLastPlayer);
        this.closeClient(clientRef);

        if (this.match.numberPlayerNotSuspended() < 3) {
            this.finishMatch();
        }
    }

    /**
     * Implement a timer that each second call the countSeconds methos
     * @throws RemoteException
     */
    public void setResetTimer() throws RemoteException{
        resetTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                try {
                    countSeconds();
                }
                catch(RemoteException remException) {
                    logger.log(Level.INFO,"setResetTimer",remException);
                }
            }
        }, 0, 1000);
    }

    /**
     * A method call by the timer each second that decrease roundTime variable.
     * When roundTime variable is equals to 1 another method is called and roundTime varibale is intialized.
     * @return roundTime decreased of 1.
     * @throws RemoteException
     */
    public final int countSeconds() throws RemoteException{
        if (roundTime == 1) {
            passRoundTimer();
            roundTime = 300;
        }
        return --roundTime;
    }

    /**
     * Method used to obtain the squares available while pushing an enemy on
     * cardinal directions.
     * @param steps the number of square the player can push.
     * @param position the position where the push starts.
     * @return the list of squares the player can push someone on.
     */
    public ArrayList<Square> getCardinalDirectionsSquares(int steps,int position){
        ArrayList<Square> newtonSquares = new ArrayList<>();
        MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(),steps,position);
        newtonSquares.addAll(movementChecker.getWalkableLeftSquares());
        newtonSquares.addAll(movementChecker.getWalkableDownwardsSquares());
        newtonSquares.addAll(movementChecker.getWalkableRightSquares());
        newtonSquares.addAll(movementChecker.getWalkableUpwardsSquares());

        return newtonSquares;
    }

    /**
     * This method is used while calculating targets for the Vortex Cannon.
     * @param currentPlayer the player using the weapon
     * @param position the position the player wants to set the vortex to.
     * @return the list of player that can be hit.
     * @throws RemoteException
     */
    public ArrayList<Player> getLocalTargets(String currentPlayer, int position) throws RemoteException{
        MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(),1,position);
        ArrayList<Player> targets = new ArrayList<>();
        for(Square square : movementChecker.getReachableSquares()){
            for(Player player : match.getAllPlayers()){
                if(player.getPosition() == square.getPosition() && !currentPlayer.equals(player.getClientName())){
                    targets.add(player);
                }
            }
        }
        return targets;
    }

    /**
     * Do a remote call to close the GUI of a specified client
     * @param clientRef the reference to a specified client
     */
    public void closeClient(GUIControllerInterface clientRef) throws RemoteException{
        try {
            clientRef.closeGUI();
        }
        catch(RemoteException remException) {
            logger.log(Level.INFO, "Can't close the window");
        }
    }
    /**
     * Save the string passed by saveState on a file in order to later retrieve the state of the match.
     * Eventually, it close all clients' GUI.
     * @return A JSON object that serialize the state of the match.
     */
    public void save() {
        String matchData = saveState();

        JsonParser parser = new JsonParser();
        JsonObject jsonMatchData = parser.parse(matchData).getAsJsonObject();
        Gson gsonMatchData = new GsonBuilder().setPrettyPrinting().create();
        String prettyMatchData = gsonMatchData.toJson(jsonMatchData);

        System.out.print("Saving match data...");
        try (FileWriter fw = new FileWriter("./AdrenalinaMatchData.json")) {
            fw.write(prettyMatchData);
            System.out.println(" ...match saved!!!");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "\nMatch NOT saved!!");
        }
        for (VirtualView virtualView : this.allVirtualViews) {
            try {
                virtualView.getClientReference().closeGUI();
            } catch (Exception e) {
                logger.log(Level.WARNING, "Cannot be able to close GUI of "+ virtualView.getUsername());
            }
        }
    }

    /**
     * Save the state of this class in order to resume the match
     * @return A JSON object that serialize the state of the match.
     */
    public String saveState() {
        JSONObject rmiserverJson = new JSONObject();


        rmiserverJson.put("activePlayer", this.getSpecificActivePlayer().getClientName());

        rmiserverJson.put("match", this.getMatch().toJSON());

        rmiserverJson.put("mapId", this.getMatch().getMap().getMapID());

        JSONArray allVirtualViewsJson = new JSONArray();
        for (VirtualView virtualView : this.allVirtualViews) {
            allVirtualViewsJson.add(virtualView.toJSON());
        }
        rmiserverJson.put("allVirtualViews", allVirtualViewsJson);


        return rmiserverJson.toJSONString();
    }

    /**
     * This method is used when calculating the enemy targets using the thor weapon.
     * @param weaponShot the weaponShot object containing the data of the targets.
     * @param targetsSize the index of the list containing the targets already hit,
     *                    needed to calculate the next target.
     * @return the weaponShot updated with new targets.
     * @throws RemoteException
     */
    public WeaponShot getThorTargets(WeaponShot weaponShot,int targetsSize) throws RemoteException{
        RoomChecker roomChecker = new RoomChecker(match.getMap(),weaponShot.getTargetPlayer().get(targetsSize).getPosition());
        ArrayList<Player> trueTargets = new ArrayList<>();
        ArrayList<Player> targetablePlayers = new ArrayList<>();
        for(Player player : roomChecker.getVisiblePlayers(match,weaponShot.getTargetPlayer().get(targetsSize)))
            if(!player.getClientName().equals(weaponShot.getDamagingPlayer().getClientName()))
                targetablePlayers.add(player);
        ArrayList<Player> alreadyTargets = new ArrayList<>();
        for(Player already : weaponShot.getAlreadyTarget()){
            alreadyTargets.add(match.searchPlayerByClientName(already.getClientName()));
        }
        targetablePlayers.removeAll(alreadyTargets);
        trueTargets.addAll(targetablePlayers);
        trueTargets.remove(match.searchPlayerByClientName(weaponShot.getDamagingPlayer().getClientName()));
        weaponShot.getTargetablePlayer().clear();
        weaponShot.getTargetablePlayer().addAll(trueTargets);

        return weaponShot;
    }

    /**
     * Set to false the first spawn variable of the player with the specified username.
     * @param username of the player
     */
    public void setFirstSpawnPlayer(String username) {
        this.match.searchPlayerByClientName(username).setFirstSpawn(false);
    }

    /**
     * Find the player related to a specific username and check if he is the first player
     * @param username username of the player
     * @return true if the player is the first, false otherwise
     */
    public boolean isFirstPlayer(String username) {
        Player player = match.searchPlayerByClientName(username);
        return player.isFirstPlayer();
    }

    /**
     * Return a boolean that indicates if a saving file exists or not
     * @return True if a saving file exists, false otherwise
     */
    public boolean checkExistFile() {
        return this.existfile;
    }

    /**
     * This method is called when final frenzy finish or when after a disconnection only two user remains in the match.
     * The method computes the damage on all player-boards and computes the points to assign to each player for each kill.
     * @throws RemoteException if the client isn't reachable.
     */
    public void finishMatch() throws RemoteException{


        for (Player player : this.match.getAllPlayers()) {
            this.assignPoints(player);
        }
        this.computePointsFinal();
    }

    public void computePointsFinal() throws RemoteException{
        ArrayList<Integer> killPointsShotTrack = new ArrayList<>();
        killPointsShotTrack.add(8);
        killPointsShotTrack.add(6);
        killPointsShotTrack.add(4);
        killPointsShotTrack.add(2);
        killPointsShotTrack.add(1);
        killPointsShotTrack.add(1);

        Map<String, Integer> pointsKillShotTrack = new HashMap<>();
        for (Player player : this.match.getKillShotTrack()) {
            if (!pointsKillShotTrack.containsKey(player.getClientName())) {
                pointsKillShotTrack.put(player.getClientName(), Integer.valueOf(1));
            }
            else {
                Integer numberOfOccurences = pointsKillShotTrack.get(player.getClientName());
                pointsKillShotTrack.put(player.getClientName(), Integer.valueOf(numberOfOccurences) + 1);
            }
        }

        Player currentPlayer = null;
        int highest = 0;
        int size = pointsKillShotTrack.size();
        //retrieve highest number of occurence
        for (int i = 0; i < size; i++) {
            for (Map.Entry<String, Integer> mapEntry : pointsKillShotTrack.entrySet()) {
                if (mapEntry.getValue() > highest) {
                    currentPlayer = this.match.searchPlayerByClientName(mapEntry.getKey());
                    highest = mapEntry.getValue().intValue();

                }
            }
            currentPlayer.addPoints(killPointsShotTrack.remove(0));
            highest = 0;
            pointsKillShotTrack.remove(currentPlayer.getClientName());
        }

        Player winner = null;
        for (Player player : this.match.getAllPlayers()) {
            if (player.getScore() > highest) {
                highest = player.getScore();
                winner = player;

            }
        }

        for (VirtualView virtualView : allVirtualViews) {
            GUIControllerInterface clientRef = virtualView.getClientReference();
            clientRef.showEndGameWindow(this.match.getAllPlayers());
        }
    }

    /**
     * Get all virtual view in allVirtualView variable
     * @return ArrayList of VirtualView
     */
    public ArrayList<VirtualView> getAllVirtualView() {
        return this.allVirtualViews;
    }

    /**
     * Set the created match in the match variable
     * @param match the current match
     */
    public void setMatch(Match match) {
        //for testing purpose
        this.match = match;
    }
}