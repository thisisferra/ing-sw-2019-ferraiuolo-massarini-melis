package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.view.GUIController;
import it.polimi.se2019.client.view.GUIControllerInterface;
import it.polimi.se2019.server.OneAboveAll;
import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.controller.ShotController;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.Server;
import it.polimi.se2019.server.model.cards.Ammo;
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
import java.util.LinkedHashMap;
import java.util.Map;

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

    //Metodi

    public RMIServer(OneAboveAll oneAboveAll, int port, String remObjName) {
        super(oneAboveAll);
        //System.setProperty("java.rmi.server.hostname","192.168.1.208");
        try {
            this.ipAddress = InetAddress.getLocalHost().getHostAddress().trim();
        }
        catch(UnknownHostException unknownHost) {
            System.err.println("Unknown host");
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
            e.printStackTrace();
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
        virtualView.initializeVirtualView(player, match);
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

    public synchronized boolean checkUsername(String username) throws Exception {
        if (match != null) {
            if (match.getAllPlayers().size() < 5) {
                for (Player player : match.getAllPlayers()) {
                    if (player.getClientName().equals(username)) {
                        return false;
                    }
                }
            } else {
                throw new Exception("Already 5 players");
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
            getMyVirtualView(username).setNumberOfAction(currentPlayer.getNumberOfAction()-1);
            updateAllClient();

        } catch (Exception e) {
            e.printStackTrace();
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
        updateAllClient();

    }
    public void pickUpWeapon(String username, int indexToPickUp, int indexToDrop) throws RemoteException{
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.pickUpWeapon(indexToPickUp,indexToDrop);
        getMyVirtualView(username).setWeapons(currentPlayer.getHand().getWeapons());
        getMyVirtualView(username).setCubes(currentPlayer.getPlayerBoard().getAmmoCubes());
        updateAllClient();

    }

    @Override
    public void restoreMap()throws RemoteException{
        restoreTile();
        restoreWeaponSlot();
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
        this.mapId = 1;
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

    public void useAction(String username) {
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.setNumberOfAction();
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
        this.updateAllClient();
    }

    public ArrayList<Weapon> verifyWeapons(String username) {
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        return getShotController().checkAll(currentPlayer);
    }

    public ShotController getShotController() {
        return this.shotController;
    }

    public void applyEffect(InfoShot infoShot) {
        infoShot.getWeapon().applyEffect(infoShot);
    }

    public void tradeCube(int index) throws RemoteException{
        activePlayer.tradeCube(index);
        updateAllClient();
    }
}