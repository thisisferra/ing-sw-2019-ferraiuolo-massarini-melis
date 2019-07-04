package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.controller.network.RMI.GUIControllerInterface;
import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//ritorno
public interface RMIServerInterface extends Remote {

    void login(String username, GUIControllerInterface guiController) throws RemoteException;

    String checkUsername2(String username) throws Exception;

    void register(String username, GUIControllerInterface guiController,int mapId) throws RemoteException;

    ArrayList<Square> reachableSquares(int position, int steps) throws RemoteException;

    Square[] getAllSquares() throws  RemoteException;

    void pickUpAmmo(String username) throws RemoteException;

    Ammo showLastAmmo() throws RemoteException;

    void setNewPosition(String username, int newPosition) throws RemoteException;

    boolean isSpawnPoint(int position) throws RemoteException;

    void pickUpWeapon(String username, int indexToPickUp)throws RemoteException;

    void pickUpWeapon(String username, int indexToPickUp, int indexToDrop)throws RemoteException;

    void restoreMap() throws RemoteException;

    Match getMatch() throws RemoteException;

    boolean checkNumberAction(String username) throws RemoteException;

    void giftAction(String username) throws RemoteException;

    void useAction(String username) throws RemoteException;

    void setActivePlayer(String usernameLastPlayer) throws RemoteException;

    String getActivePlayer() throws RemoteException;

    void resetActionNumber(String username) throws RemoteException;

    ArrayList<Player> getKillShotTrack() throws RemoteException;

    ArrayList<Weapon> verifyWeapons(String username) throws RemoteException;

    void applyEffectWeapon(WeaponShot weaponShot) throws RemoteException;

    void tradeCube(int index) throws  RemoteException;

    void discardAndSpawn(String username,int index) throws RemoteException;

    ArrayList<Weapon> getReloadableWeapons(String username) throws RemoteException;

    void reloadWeapon(String username, int index) throws RemoteException;

    boolean checkSizeWeapon(String username) throws RemoteException;

    void usePowerUp(String username, int index, PowerUpShot powerUpShot) throws RemoteException;

    boolean deathPlayer(String username) throws RemoteException;

    void respawnPlayer() throws RemoteException;

    void notifyAllClientMovement(String username, Integer newPosition) throws RemoteException;

    void notifyAllClientPickUpWeapon(String username) throws RemoteException;

    void notifyAllClientPickUpAmmo(String username, String lastAmmo) throws RemoteException;

    void toggleAction(String username) throws RemoteException;

    void setCanMove(String username,boolean canMove) throws RemoteException;

    void payCubes(String username,int reds,int yellows,int blues) throws RemoteException;

    void save() throws RemoteException;

    ArrayList<Square> getCardinalDirectionsSquares(int steps,int position) throws RemoteException;

    ArrayList<Player> getLocalTargets(String currentPlayer, int position) throws RemoteException;

    WeaponShot getThorTargets(WeaponShot weaponShot,int targetsSize) throws RemoteException;

    boolean isFirstPlayer(String username) throws RemoteException;

    void reconnect(String usernameTyped, GUIControllerInterface guiController) throws RemoteException;

    void setFirstSpawnPlayer(String username) throws RemoteException;

    boolean checkExistFile() throws RemoteException;
}