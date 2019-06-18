package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.view.GUIControllerInterface;
import it.polimi.se2019.server.OneAboveAll;
import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.controller.ShotController;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.Server;
import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.map.WeaponSlot;
import it.polimi.se2019.server.model.player.Player;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//ritorno
public class RMIServer extends Server implements RMIServerInterface {

    //Attributi

    private PrintStream out = new PrintStream(System.out);

    private Player activePlayer;

    private Match match;

    private int mapId = 0;

    private String ipAddress;

    private final int port;

    private final String remObjName;

    private Registry rmiRegistry;

    private ArrayList<VirtualView> allVirtualViews = new ArrayList<>();

    private ShotController shotController;

    private Logger logger = Logger.getAnonymousLogger();

    //Metodi

    public RMIServer(OneAboveAll oneAboveAll, int port, String remObjName) {
        super(oneAboveAll);
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

    public void startServer() {
        try {
            System.out.println("Server is up");
            rmiRegistry = LocateRegistry.createRegistry(port);
            UnicastRemoteObject.exportObject(this, port);
            rmiRegistry.bind(remObjName, this);
            System.out.println("Registry created and object exported");
            /*
            match = new Match(1);
            match.initializeMatch();
            System.out.println("Match created");
            System.out.println("Client connessi: ");*/

        } catch (Exception e) {
            logger.log(Level.INFO,"Error starting the server",e);
        }
    }

    public String getClientIP() throws ServerNotActiveException {
        return RemoteServer.getClientHost();
    }

    public synchronized void register(String username, GUIControllerInterface guiController,int mapId) throws RemoteException{
        if (!mapAlreadySelected()) {
            this.createMatch(mapId);
        }
        VirtualView virtualView = createVirtualView(guiController);
        Player player = createPlayer(username);
        virtualView.updateVirtualView(player, match);
        this.printClientConnected();
        this.initAllClient();
        if (activePlayer == null) {
            activePlayer = match.getAllPlayers().get(0);
        }

        /*
        for (VirtualView virtualView : allVirtualViews) {
            virtualView.getClientReference().notifyNewClient(username);
        }
        try {
            this.updateAllClient();
        }
        catch (Exception e) {
            System.err.println("Errore");
        }
         */
    }

    public synchronized boolean checkUsername(String username) {


        if (match != null) {
            if (match.getAllPlayers().size() < 5) {
                for (Player player : match.getAllPlayers()) {
                    if (player.getClientName().equals(username)) {
                        return false;
                    }
                }
            } else {
                logger.log(Level.INFO,"Already 5 players",new Exception());
            }
        }
        return true;
    }

    public ArrayList<Square> reachableSquares(int position, int steps){
        MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(), steps, position);
        return movementChecker.getReachableSquares();
    }

    public Square[] getAllSquares(){
        return match.getMap().getAllSquare();
    }

    public void pickUpAmmo(String username) throws RemoteException{
        Player player = match.searchPlayerByClientName(username);
        Square square = match.getMap().searchSquare(player.getPosition());
        player.pickUpAmmo(square, match);
        updateAllVirtualView();
        this.updateAllClient();
    }

    public void pickUpPowerUp(String username) throws RemoteException{
        Player player = match.searchPlayerByClientName(username);
        player.pickUpPowerUp();
        updateAllVirtualView();
        this.updateAllClient();
    }

    public Ammo showLastAmmo() throws  RemoteException{
        if(!match.getDiscardedAmmos().isEmpty())
            return match.getDiscardedAmmos().get(match.getDiscardedAmmos().size()-1);
        else return null;
    }

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

    public void printClientConnected() {
        int size = allVirtualViews.size();
        out.println(allVirtualViews.get(size - 1).getUsername() + "---" + allVirtualViews.get(allVirtualViews.size() - 1).getClientReference());
    }

    //Call to each client to create all remotes view
    public void initAllClient() throws RemoteException{
        for (VirtualView virtualView : allVirtualViews) {
            GUIControllerInterface clientRef = virtualView.getClientReference();
            clientRef.initRemoteView(allVirtualViews);
        }

    }

    public VirtualView getMyVirtualView(String username) {
        for(VirtualView virtualView : allVirtualViews) {
            if (virtualView.getUsername().equals(username)) {
                return  virtualView;
            }
        }
        return null;
    }

    public void updateAllClient() throws RemoteException{
        for (VirtualView virtualView : allVirtualViews) {
            GUIControllerInterface clientRef = virtualView.getClientReference();
            clientRef.update(allVirtualViews);
        }
    }

    public boolean isSpawnPoint(int position) {
        if (match.getMap().searchSquare(position).isSpawnPoint()) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void pickUpWeapon(String username, int indexToPickUp) throws RemoteException {
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.pickUpWeapon(indexToPickUp);
        getMyVirtualView(username).setWeapons(currentPlayer.getHand().getWeapons());
        getMyVirtualView(username).setCubes(currentPlayer.getPlayerBoard().getAmmoCubes());
        updateAllVirtualView();
        updateAllClient();

    }
    public void pickUpWeapon(String username, int indexToPickUp, int indexToDrop) throws RemoteException{
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.pickUpWeapon(indexToPickUp,indexToDrop);
        getMyVirtualView(username).setWeapons(currentPlayer.getHand().getWeapons());
        getMyVirtualView(username).setCubes(currentPlayer.getPlayerBoard().getAmmoCubes());
        updateAllVirtualView();
        updateAllClient();

    }

    @Override
    public void restoreMap()throws RemoteException{
        restoreTile();
        restoreWeaponSlot();
        updateAllVirtualView();
        updateAllClient();
    }

    private void restoreTile() {
        for(Square square : match.getMap().getAllSquare()) {
            if (!square.isSpawnPoint()) {
                square.setFull(true);
            }
        }
    }

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

    private boolean mapAlreadySelected() {
        return (mapId != 0);
    }

    private void createMatch(int mapId) {
        match = new Match(mapId);
        this.mapId = mapId;
        match.initializeMatch();
        this.shotController = new ShotController(this.match);
        out.println("Match created");
    }

    private VirtualView createVirtualView(GUIControllerInterface guiController){
        VirtualView virtualView = new VirtualView(guiController);
        allVirtualViews.add(virtualView);
        return virtualView;
    }

    private Player createPlayer(String username) {
        Player player = new Player(username, match);
        this.match.getAllPlayers().add(player);
        player.pickUpPowerUp();
        player.pickUpPowerUp();
        return player;
    }

    public Match getMatch() {
        return this.match;
    }

    public boolean checkNumberAction(String username) {
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        if (currentPlayer.getNumberOfAction() > 0) {
            return true;
        }
        return false;
    }

    public void giftAction(String username) throws RemoteException{
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.increaseNumberOfAction();
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    public void useAction(String username) throws RemoteException{
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.decreaseNumberOfAction();
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    public void setActivePlayer(String usernameLastPlayer) {
        Player lastPlayer = match.searchPlayerByClientName(usernameLastPlayer);
        int size = match.getAllPlayers().size();
        int index = 0;
        while(!usernameLastPlayer.equals(match.getAllPlayers().get(index).getClientName())) {
            index++;
        }
        if (index == size - 1) {
            activePlayer = match.getAllPlayers().get(0);
        }
        else {
            activePlayer = match.getAllPlayers().get(index + 1);
        }
        System.out.println("Next player is: " + activePlayer.getClientName());
    }

    public ArrayList<Player> getKillShotTrack() throws RemoteException{
        return match.getKillShotTrack();
    }

    public String getActivePlayer() {
        return this.activePlayer.getClientName();
    }

    public void resetActionNumber(String username) throws RemoteException{
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        currentPlayer.resetNumberOfAction();
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    public ArrayList<Weapon> verifyWeapons(String username) {
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        return getShotController().checkAll(currentPlayer);
    }

    public ShotController getShotController() {
        return this.shotController;
    }

    public void applyEffectWeapon(InfoShot infoShot) {
        infoShot.getWeapon().applyEffect(infoShot);
    }

    public void tradeCube(int index) throws RemoteException{
        activePlayer.tradeCube(index);
        this.updateAllVirtualView();
        this.updateAllClient();

    }

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

    public ArrayList<Weapon> getReloadableWeapons(String username) throws RemoteException{
        Player player = this.match.searchPlayerByClientName(username);
        return player.getReloadableWeapons();
    }

    public void reloadWeapon(String username ,int index) throws RemoteException{
        Player player = this.match.searchPlayerByClientName(username);
        Weapon weaponToReload = player.getHand().getWeapons().get(index);
        weaponToReload.setLoad(true);
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    public boolean checkSizeWeapon(String username) {
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        return !currentPlayer.getHand().getWeapons().isEmpty();

    }

    public void usePowerUp(String username, int index, InfoShot infoShot) throws RemoteException{

        Player currentPlayer = this.match.searchPlayerByClientName(username);
        PowerUp powerUp = currentPlayer.getHand().getPowerUps().get(index);
        powerUp.applyEffect(infoShot);
        //TODO discardpowerup
        this.updateAllVirtualView();
        this.updateAllClient();

    }

    public void updateAllVirtualView() {
        for (Player player : match.getAllPlayers()) {
            for (VirtualView virtualView : allVirtualViews) {
                if (player.getClientName().equals(virtualView.getUsername())) {
                    virtualView.updateVirtualView(player, match);
                }
            }
        }

    }
}










