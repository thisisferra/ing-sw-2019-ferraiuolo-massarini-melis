package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.CubesChecker;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.game.RoomChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * ShotController class aim is to scan each weapon of a player to verify which of the
 * weapon a player can use to shoot others player.
 * @author Marco Melis, Mattia Massarini
 */

public class ShotController implements Serializable {

    private Match match;

    /**
     * Constructor of the class. It links this object with the current match
     * @param match: the current match
     */
    public ShotController(Match match) {
        this.match = match;
    }

    /**
     *The method call in sequence checkIsLoad, checkCubes, checkVisibility and purgeUselessWeapons the verify which weapon a player can use
     * @param currentPlayer: the current player
     * @return
     */
    public ArrayList<Weapon> checkAll(Player currentPlayer) {
        ArrayList<Weapon> loadedWeapon = checkIsLoad(currentPlayer);
        ArrayList<Weapon> checkedWeapon;
        ArrayList<Weapon> usableWeapon;
        ArrayList<Weapon> purgedWeapons;
        ArrayList<WeaponShot> weaponShots = new ArrayList<>();
        if (loadedWeapon.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            checkedWeapon = checkCubes(currentPlayer, loadedWeapon);
            usableWeapon = checkVisibility(currentPlayer,checkedWeapon);
            purgedWeapons = purgeUselessWeapons(usableWeapon);
        }
        return purgedWeapons;
    }

    /**
     * checkIsLoad scan all the weapons a player has in his hand and verify if the weapon is load or not.
     * @param currentPlayer: the current player
     * @return An ArrayList of only loaded weapon
     */
    private ArrayList<Weapon> checkIsLoad(Player currentPlayer) {
        ArrayList<Weapon> loadedWeapon = new ArrayList<>();
        for (Weapon currentWeapon : currentPlayer.getHand().getWeapons()) {
            if(currentWeapon.getLoad()) {
                loadedWeapon.add(currentWeapon.weaponFactory(currentWeapon));
            }
        }
        return loadedWeapon;
    }

    /**
     * Scan all the weapons of the current player to verify which effect of all weapons the players could pay.
     * @param currentPlayer: the current player
     * @param loadedWeapon: An ArrayList of loaded weapon
     * @return An ArrayList of weapon in which the effects the player con't pay are set to null.
     */
    private ArrayList<Weapon> checkCubes(Player currentPlayer, ArrayList<Weapon> loadedWeapon) {
        CubesChecker cubesChecker;
        for(Weapon currentWeapon : loadedWeapon) {
            int length = currentWeapon.getEffect().length;
            for(int i = 0; i < length; i++) {
                cubesChecker = new CubesChecker(currentPlayer.getPlayerBoard().getAmmoCubes(), currentWeapon.getEffect()[i].getExtraCost());
                if(!cubesChecker.check()){
                    currentWeapon.getEffect()[i] = null;
                }
            }
        }
        return loadedWeapon;
    }

    /**
     * Scan all the effects of all weapons and check for each effect if there is at
     * least one target the player could attack related to the typeVisibility of that effect.
     * @param currentPlayer: the current player
     * @param checkedWeapon: An ArrayList of weapon loaded without the effect the player can't pay
     * @return An ArrayList of weapon without the effect that the player can't use because there isn't possible target
     */
    private ArrayList<Weapon> checkVisibility(Player currentPlayer, ArrayList<Weapon> checkedWeapon) {

        ArrayList<Player> visiblePlayers = new ArrayList<>();
        ArrayList<Player> notVisiblePlayers = new ArrayList<>();
        ArrayList<WeaponShot> weaponShots = new ArrayList<>();

        //per ogni arma del giocatore
        for (Weapon weapon : checkedWeapon) {
            //per ogni effetto dell'arma
            for (int i = 0; i < weapon.getEffect().length; i++) {
                visiblePlayers.clear();
                notVisiblePlayers.clear();
                //se l'effetto è diverso da null ( posso pagarlo e l'arma e carica)
                if(weapon.getEffect()[i] != null){
                    //controllo il tipo di visibilità dell'effetto
                    WeaponShot weaponShot;
                    switch(weapon.getEffect()[i].getTypeVisibility()) {
                        case "CanSee": {
                            //Create a RoomChecker object
                            RoomChecker roomChecker = new RoomChecker(match.getMap(), currentPlayer.getPosition());
                            //ArrayList that contains all accessible square from my position
                            ArrayList<Square> accessibleSquare = roomChecker.getAccessibleRooms();
                            //Get all players who are on squares that I can view from my position
                            visiblePlayers = roomChecker.getVisiblePlayers(match, currentPlayer);
                            //If i can't view anyone i can't use this effect
                            if (visiblePlayers.isEmpty()) {
                                weapon.getEffect()[i] = null;
                            } else{
                                weaponShot = createInfoShot(currentPlayer,visiblePlayers,weapon,weapon.getEffect()[i].getNameEffect(), weaponShots,weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }
                            visiblePlayers.clear();
                            break;
                        }
                        case "CantSee": {
                            RoomChecker roomChecker = new RoomChecker(match.getMap(), currentPlayer.getPosition());
                            notVisiblePlayers = roomChecker.getNonVisiblePlayers(match,currentPlayer);
                            notVisiblePlayers.remove(currentPlayer);
                            if (notVisiblePlayers.isEmpty()) {
                                weapon.getEffect()[i] = null;
                            } else{
                                weaponShot = createInfoShot(currentPlayer,notVisiblePlayers,weapon,weapon.getEffect()[i].getNameEffect(), weaponShots,weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }
                            break;
                        }
                        case "CanSeeDistance": {
                            int minDistanceTarget = weapon.getEffect()[i].getMinDistanceTarget();
                            int maxDistanceTarget = weapon.getEffect()[i].getMaxDistanceTarget();

                            if (maxDistanceTarget == -1) {
                                RoomChecker roomChecker = new RoomChecker(match.getMap(), currentPlayer.getPosition());
                                MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(), weapon.getEffect()[i].getMinDistanceTarget() - 1, currentPlayer.getPosition());
                                visiblePlayers = roomChecker.getVisiblePlayers(match, currentPlayer);
                                for (Player player : match.getAllPlayers()) {
                                    for (Square square : movementChecker.getReachableSquares()) {
                                        if (player.getPosition() == square.getPosition()) {
                                            visiblePlayers.remove(player);
                                        }
                                    }
                                }
                            }
                            else if(maxDistanceTarget == minDistanceTarget && maxDistanceTarget == 0) {
                                for (Player player : match.getAllPlayers()) {
                                    if (player.getPosition() == currentPlayer.getPosition() && !player.getClientName().equals(currentPlayer.getClientName())) {
                                        visiblePlayers.add(player);
                                    }
                                }
                            }
                            else {
                                ArrayList<Square> reacheableSquare;
                                ArrayList<Square> cantShootSquare;
                                MovementChecker maxMovementChecker = new MovementChecker(match.getMap().getAllSquare(), maxDistanceTarget, currentPlayer.getPosition());
                                MovementChecker minMovementChecker = new MovementChecker(match.getMap().getAllSquare(), minDistanceTarget - 1, currentPlayer.getPosition());
                                reacheableSquare = maxMovementChecker.getReachableSquares();
                                cantShootSquare = minMovementChecker.getReachableSquares();
                                reacheableSquare.removeAll(cantShootSquare);
                                for (Square square : reacheableSquare) {
                                    for (Player player : match.getAllPlayers()) {
                                        if (player.getPosition() == square.getPosition()) {
                                            visiblePlayers.add(player);
                                        }
                                    }
                                }
                            }
                            visiblePlayers.remove(currentPlayer);
                            if(visiblePlayers.isEmpty())
                                weapon.getEffect()[i] = null;
                            else{
                                weaponShot = createInfoShot(currentPlayer,visiblePlayers,weapon,weapon.getEffect()[i].getNameEffect(), weaponShots,weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }
                            break;
                        }
                        case "CanSeeRoomNotIn": {
                            RoomChecker roomChecker = new RoomChecker(match.getMap(),currentPlayer.getPosition());
                            Square[] allSquares = match.getMap().getAllSquare();
                            ArrayList<Square> roomSquares = new ArrayList<>();
                            String currentPlayerColor = allSquares[currentPlayer.getPosition()].getColor();
                            for(Square square : roomChecker.getAccessibleRooms()){
                                if(!square.getColor().equals(currentPlayerColor))
                                    roomSquares.add(square);
                            }
                            for(Square square : roomSquares){
                                for(Player player : match.getAllPlayers()){
                                    if(player.getPosition() == square.getPosition()){
                                        visiblePlayers.add(player);
                                    }
                                }
                            }
                            visiblePlayers.remove(currentPlayer);
                            if(visiblePlayers.isEmpty())
                                weapon.getEffect()[i] = null;
                            else{
                                weaponShot = createInfoShot(currentPlayer,visiblePlayers,weapon,weapon.getEffect()[i].getNameEffect(), weaponShots,weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }
                            break;
                        }
                        case "Cardinal" : {
                            MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(),5,currentPlayer.getPosition());
                            ArrayList<Square> cardinalSquares = new ArrayList<>();
                            cardinalSquares.addAll(movementChecker.getAllUpwardsSquares());
                            cardinalSquares.addAll(movementChecker.getAllRightSquares());
                            cardinalSquares.addAll(movementChecker.getAllDownwardsSquares());
                            cardinalSquares.addAll(movementChecker.getAllLeftSquares());
                            cardinalSquares.add(match.getMap().getAllSquare()[currentPlayer.getPosition()]);
                            for(Square square : cardinalSquares){
                                for(Player player : match.getAllPlayers()){
                                    if(player.getPosition() == square.getPosition())
                                        visiblePlayers.add(player);
                                }
                            }
                            visiblePlayers.remove(currentPlayer);
                            if(visiblePlayers.isEmpty())
                                weapon.getEffect()[i] = null;
                            else {
                                weaponShot = createInfoShot(currentPlayer,visiblePlayers,weapon,weapon.getEffect()[i].getNameEffect(), weaponShots,weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }
                            break;
                        }
                        case "InitiallyCantSee" : {

                            ArrayList<Square> visibleSquares = new ArrayList<>();
                            ArrayList<Square> extendedSquares = new ArrayList<>();
                            HashSet<Player> playersSet = new HashSet<>();
                            HashSet<Square> temporarySquares = new HashSet<>();
                            RoomChecker roomChecker = new RoomChecker(match.getMap(),currentPlayer.getPosition());
                            MovementChecker movementChecker;
                            //ora contiene tutti gli square raggiungibili
                            visibleSquares.addAll(roomChecker.getAccessibleRooms());

                            extendedSquares.addAll(visibleSquares);
                            for(Square square : visibleSquares){
                                movementChecker = new MovementChecker(match.getMap().getAllSquare(),2,square.getPosition());
                                temporarySquares.addAll(movementChecker.getReachableSquares());
                            }
                            temporarySquares.addAll(extendedSquares);
                            extendedSquares.clear();
                            extendedSquares.addAll(temporarySquares);
                            for(Square square : extendedSquares){
                                for(Player player : match.getAllPlayers()){
                                    if(player.getPosition() == square.getPosition()){
                                        playersSet.add(player);
                                    }
                                }
                            }
                            playersSet.remove(currentPlayer);
                            visiblePlayers.addAll(playersSet);
                            if(visiblePlayers.isEmpty())
                                weapon.getEffect()[i] = null;
                            else {
                                weaponShot = createInfoShot(currentPlayer,visiblePlayers,weapon,weapon.getEffect()[i].getNameEffect(), weaponShots,weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }
                            break;
                        }
                        case "Cardinal2" : {
                            MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(),5,currentPlayer.getPosition());
                            weaponShot = new WeaponShot();
                            for(Square square : movementChecker.getAllUpwardsSquares()){
                                for(Player player : match.getAllPlayers()){
                                    if(player.getPosition() == square.getPosition() && !player.equals(currentPlayer))
                                        weaponShot.getNorthTargets().add(player);
                                }
                            }

                            for(Square square : movementChecker.getAllRightSquares()){
                                for(Player player : match.getAllPlayers()){
                                    if(player.getPosition() == square.getPosition() && !player.equals(currentPlayer))
                                        weaponShot.getEastTargets().add(player);
                                }
                            }

                            for(Square square : movementChecker.getAllDownwardsSquares()){
                                for(Player player : match.getAllPlayers()){
                                    if(player.getPosition() == square.getPosition() && !player.equals(currentPlayer))
                                        weaponShot.getSouthTargets().add(player);
                                }
                            }

                            for(Square square : movementChecker.getAllLeftSquares()){
                                for(Player player : match.getAllPlayers()){
                                    if(player.getPosition() == square.getPosition() && !player.equals(currentPlayer))
                                        weaponShot.getWestTargets().add(player);
                                }
                            }
                            for(Player player : match.getAllPlayers()){
                                if(player.getPosition() == currentPlayer.getPosition() && !player.getClientName().equals(currentPlayer.getClientName())){
                                    weaponShot.getNorthTargets().add(player);
                                    weaponShot.getEastTargets().add(player);
                                    weaponShot.getSouthTargets().add(player);
                                    weaponShot.getWestTargets().add(player);
                                }
                            }
                            if(weaponShot.getNorthTargets().isEmpty() && weaponShot.getEastTargets().isEmpty() && weaponShot.getSouthTargets().isEmpty() &&
                                    weaponShot.getWestTargets().isEmpty())
                                weapon.getEffect()[i] = null;
                            else {
                                weaponShot.setDamagingPlayer(currentPlayer);
                                weaponShot.setWeapon(weapon);
                                weaponShot.setNameEffect(weapon.getEffect()[i].getNameEffect());
                                weaponShot.setChosenEffect(weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }
                            break;
                        }
                        case "Cascade" :{
                            RoomChecker roomChecker = new RoomChecker(match.getMap(), currentPlayer.getPosition());
                            visiblePlayers = roomChecker.getVisiblePlayers(match, currentPlayer);
                            if (visiblePlayers.isEmpty()) {
                                weapon.getEffect()[i] = null;
                            } else{
                                weaponShot = createInfoShot(currentPlayer,visiblePlayers,weapon,weapon.getEffect()[i].getNameEffect(), weaponShots,weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }
                            visiblePlayers.clear();
                            break;
                        }
                        case "Vortex": {
                            RoomChecker roomChecker = new RoomChecker(match.getMap(),currentPlayer.getPosition());
                            ArrayList<Square> squares = new ArrayList<>();
                            for(Square square : roomChecker.getAccessibleRooms()){
                                if(square.getPosition() != currentPlayer.getPosition()){
                                    squares.add(square);
                                }
                            }
                            weaponShot = new WeaponShot();
                            weaponShot.setDamagingPlayer(currentPlayer);
                            weaponShot.setWeapon(weapon);
                            weaponShot.setNameEffect(weapon.getEffect()[i].getNameEffect());
                            weaponShot.setChosenEffect(weapon.getEffect()[i]);
                            weaponShot.getSquares().addAll(squares);
                            weapon.getWeaponShots().add(weaponShot);
                            break;

                        }
                        default: {
                            System.out.println(weapon + " not yet implemented");
                            break;
                        }
                    }
                }
            }
        }
        return checkedWeapon;
    }

    /**
     * Scan all the usable weapons in order to delete those weapon that has zero available effect
     * @param usableWeapon: an ArrayList of usable weapon
     * @return: An ArrayList of weapon the player can use with the related effects
     */
    private ArrayList<Weapon> purgeUselessWeapons(ArrayList<Weapon> usableWeapon){
        ArrayList<Weapon> purgedWeapon = new ArrayList<>();
        for(Weapon weapon : usableWeapon){
            if(!weapon.getWeaponShots().isEmpty())
                purgedWeapon.add(weapon);
        }
        return purgedWeapon;
    }

    private WeaponShot createInfoShot(Player damagingPlayer, ArrayList<Player> targetablePlayers, Weapon weapon, String nameEffect, ArrayList<WeaponShot> weaponShots, Shot chosenEffect){
        WeaponShot weaponShot = new WeaponShot();
        weaponShot.setDamagingPlayer(damagingPlayer);
        weaponShot.setWeapon(weapon);
        weaponShot.getTargetablePlayer().addAll(targetablePlayers);
        weaponShot.setNameEffect(nameEffect);
        weaponShot.setChosenEffect(chosenEffect);

        return weaponShot;
    }

}

