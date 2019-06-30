package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.controller.GUIControllerInterface;
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

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
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

    private Timer initializeMatchTimer = new Timer();

    private Timer roundTimer = new Timer();

    private int interval = 10;

    private int roundTime = 30;

    private boolean resetTimer = false;

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

    /*
    private String getClientIP(GUIControllerInterface guiController){
        String ipClient = null;
        String port = null;
        Pattern IP_PATTERN = Pattern.compile("(((\\d)+(\\.)*)+:(\\d)+)");
        Matcher matcher = IP_PATTERN.matcher(guiController.toString());
        while(matcher.find()) {
            ipClient = matcher.group(0);
        }
        System.out.println("ipClient: " + ipClient);
        return ipClient;
    }
     */

    public synchronized void register(String username, GUIControllerInterface guiController,int mapId) throws RemoteException{
        if (!mapAlreadySelected()) {
            this.createMatch(mapId);
            this.startingMatchTimer();

        }
        //String ipClient = getClientIP(guiController);
        VirtualView virtualView = createVirtualView(guiController);
        Player player = createPlayer(username);
        virtualView.updateVirtualView(player, match);
        this.printClientConnected();
        this.initAllClient();
        if (activePlayer == null) {
            activePlayer = match.getAllPlayers().get(0);
            //TODO CHECKTHIS
            activePlayer.setFirstPlayer(true);
        }
        if (this.match.getOpenConnection()) {
            virtualView.getClientReference().waitPlayers();
        }
        //this.pingClient();
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
        pingAllClient();
        for (VirtualView virtualView : allVirtualViews) {
            if (!this.match.searchPlayerByClientName(virtualView.getUsername()).getSuspended()) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                clientRef.update(allVirtualViews);
            }
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

    public void pickUpWeapon(String username, int indexToPickUp) throws RemoteException {
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.pickUpWeapon(indexToPickUp);
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
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        currentPlayer.decreaseNumberOfAction();
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    public void setActivePlayer(String usernameLastPlayer) throws RemoteException{
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
            activePlayer =notSuspendedPlayers.get(0);
        }
        else {
            activePlayer = notSuspendedPlayers.get(index + 1);
        }
        activePlayer.setCanMove(false);
        activePlayer.clearHitThisTurnPlayers();
        updateAllVirtualView();
        updateAllClient();
        roundTime = 300;
}

    public void setSpecificActivePlayer(Player player) {
        this.activePlayer = player;

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
        player.getPlayerBoard().payCubes(weaponToReload.getReloadCost());
        this.updateAllVirtualView();
        this.updateAllClient();
    }

    public boolean checkSizeWeapon(String username) {
        Player currentPlayer = this.match.searchPlayerByClientName(username);
        return !currentPlayer.getHand().getWeapons().isEmpty();

    }

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

    public void updateAllVirtualView() {
        for (Player player : this.match.getAllPlayers()) {
            for (VirtualView virtualView : allVirtualViews) {
                if (player.getClientName().equals(virtualView.getUsername())) {
                    virtualView.updateVirtualView(player, match);
                }
            }
        }

    }

    public boolean deathPlayer(String username) throws RemoteException {
        boolean flagDeath = false;
        for (Player player : match.getAllPlayers()) {
            if (player.getPlayerDead()) {
                flagDeath = true;
                match.addPlayerKillShot(player);
                assignPoints(player);
                player.getPlayerBoard().setDeaths();
                player.setPhaseAction(0);
            }
        }
        if (this.match.getKillShotTrack().size() == 8) {
            this.enableFinalFrenzy(username);
        }
        updateAllVirtualView();
        updateAllClient();
        return flagDeath;
    }

    private void assignPoints(Player player) throws RemoteException{
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
        updateAllClient();
    }

    private void unloadWeapon(WeaponShot weaponShot) throws RemoteException{
        for(Weapon weapon : match.searchPlayerByClientName(weaponShot.getDamagingPlayer().getClientName()).getHand().getWeapons()){
            if(weaponShot.getWeapon().getType().equals(weapon.getType()))
                weapon.setLoad(false);
        }
        updateAllVirtualView();
        updateAllClient();
    }

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

    public void notifyAllClientMovement(String username, Integer newPosition) throws  RemoteException{
        String movementMessage = username + " new position is " + newPosition;
        for (VirtualView virtualView : allVirtualViews) {
            if (!virtualView.getSuspended()) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                clientRef.showMessage(movementMessage);
            }
        }
    }

    public void notifyAllClientPickUpWeapon(String username) throws RemoteException{
        String pickUpWeaponMessage = username + " pick up a weapon";
        for (VirtualView virtualView : allVirtualViews) {
            if (!virtualView.getSuspended()) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                clientRef.showMessage(pickUpWeaponMessage);
            }
        }

    }

    public void notifyAllClientPickUpAmmo(String username, String lastAmmo) throws RemoteException {
        String pickUpWeaponMessage = username + " picked up an ammo:\n" + lastAmmo;
        for (VirtualView virtualView : allVirtualViews) {
            GUIControllerInterface clientRef = virtualView.getClientReference();
            clientRef.showMessage(pickUpWeaponMessage);
        }

    }

    public void notifyAllClientUserDisconnect(String username) throws RemoteException{
        String clientDisconnect = username + " disconnected from the match\n";
        for (VirtualView virtualView : allVirtualViews) {
            if (!virtualView.getSuspended()) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                clientRef.showMessage(clientDisconnect);
            }

        }
    }

    public void toggleAction(String username) throws RemoteException{
        match.searchPlayerByClientName(username).setCanMove(!match.searchPlayerByClientName(username).getCanMove());
        updateAllVirtualView();
        updateAllClient();
    }

    public void  enableFinalFrenzy(String username) throws RemoteException {
        boolean flag = false;
        for (Player player : match.getAllPlayers()) {
            if (player.getPlayerBoard().getDamage().isEmpty())
                player.setTypePlayerBoard(1);
            if (player.getClientName().equals(username)) {
                flag = true;
            }
            if (flag) {
                player.setFinalFrenzy(2);
            } else
                player.setFinalFrenzy(1);
        }
        updateAllVirtualView();
        updateAllClient();

    }

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

    private final int countMatchTimer() throws RemoteException{
        if (interval == 1) {
            this.match.setOpenConnection(false);
            System.out.println("Match openConnection: " + this.match.getOpenConnection());
            for (VirtualView virtualView : allVirtualViews) {
                GUIControllerInterface clientRef = virtualView.getClientReference();
                clientRef.startingMatch();
            }
            setResetTimer();
            initializeMatchTimer.cancel();
        }
        return --interval;
    }

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

    }

    public void setResetTimer() throws RemoteException{
        Timer resetTimer = new Timer();
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

    public final int countSeconds() throws RemoteException{
        if (roundTime == 1) {
            passRoundTimer();
            roundTime = 30;
        }
        return --roundTime;
    }

    public ArrayList<Square> getCardinalDirectionsSquares(int steps,int position){
        ArrayList<Square> newtonSquares = new ArrayList<>();
        MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(),steps,position);
        newtonSquares.addAll(movementChecker.getWalkableLeftSquares());
        newtonSquares.addAll(movementChecker.getWalkableDownwardsSquares());
        newtonSquares.addAll(movementChecker.getWalkableRightSquares());
        newtonSquares.addAll(movementChecker.getWalkableUpwardsSquares());

        return newtonSquares;
    }

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

    public void closeClient(GUIControllerInterface clientRef){
        try {
            clientRef.closeGUI();
        }
        catch(RemoteException remException) {
            logger.log(Level.INFO, "Can't close the window");
        }
    }

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

    public void setFirstSpawnPlayer(String username) {
        this.match.searchPlayerByClientName(username).setFirstSpawn(false);
    }

    public boolean isFirstPlayer(String username) throws RemoteException{
        Player player = match.searchPlayerByClientName(username);
        return player.isFirstPlayer();
    }
}
