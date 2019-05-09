package it.polimi.se2019.client.controller.network.RMI;

/*import it.polimi.se2019.server.model.cards.Weapon;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;*/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    /**
     * Lets the server get input from a client
     * @return whatever the client writes
     */
    String getInput() throws RemoteException;

    /*Weapon chooseWeaponToUse() throws RemoteException;*/

    /*Player chooseTarget() throws RemoteException;*/

    /**
     * Lets the server ask the client in which square
     * he wants to move his totem
     * @return the square in which the player will move
     */
    /*Square chooseSquare() throws RemoteException;*/
}