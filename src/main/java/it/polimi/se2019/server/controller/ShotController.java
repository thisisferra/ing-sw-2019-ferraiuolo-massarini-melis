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

public class ShotController implements Serializable {

    private Match match;

    public ShotController(Match match) {
        this.match = match;
    }

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
            //infoShots = checkVisibility(currentPlayer, checkedWeapon);
        }
        System.out.println("InfoShots: "+ weaponShots);
        return purgedWeapons;
    }

    private ArrayList<Weapon> checkIsLoad(Player currentPlayer) {
        ArrayList<Weapon> loadedWeapon = new ArrayList<>();
        for (Weapon currentWeapon : currentPlayer.getHand().getWeapons()) {
            if(currentWeapon.getLoad()) {
                loadedWeapon.add(currentWeapon.weaponFactory(currentWeapon));
            }
        }
        return loadedWeapon;
    }

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
                            System.out.println(visiblePlayers);
                            visiblePlayers.clear();
                            break;
                        }
                        case "CantSee": {
                            RoomChecker roomChecker = new RoomChecker(match.getMap(), currentPlayer.getPosition());
                            notVisiblePlayers = roomChecker.getNonVisiblePlayers(match,currentPlayer);
                            System.out.println(notVisiblePlayers);
                            notVisiblePlayers.remove(currentPlayer);
                            if (notVisiblePlayers.isEmpty()) {
                                weapon.getEffect()[i] = null;
                            } else{
                                weaponShot = createInfoShot(currentPlayer,notVisiblePlayers,weapon,weapon.getEffect()[i].getNameEffect(), weaponShots,weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }

                            System.out.println(notVisiblePlayers);
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

                            System.out.println(visiblePlayers);
                            break;
                        }
                        case "CanSeeRoom": {
                            RoomChecker roomChecker = new RoomChecker(match.getMap(),currentPlayer.getPosition());
                            ArrayList<Square> roomSquares = new ArrayList<>();
                            roomSquares.addAll(roomChecker.getAccessibleRooms());
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
                            System.out.println(visiblePlayers);
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
                            System.out.println(visiblePlayers);
                            break;
                        }
                        case "DistanceFromAPosition": {
                            MovementChecker movementChecker;
                            RoomChecker roomChecker = new RoomChecker(match.getMap(), currentPlayer.getPosition());
                            ArrayList<Square> viewSquare = roomChecker.getAccessibleRooms();
                            for (Square square : viewSquare) {
                                movementChecker = new MovementChecker(match.getMap().getAllSquare(), 1, square.getPosition());
                                for(Square square1 : movementChecker.getReachableSquares()) {
                                    for (Player player : match.getAllPlayers()) {
                                        if (player.getPosition() == square1.getPosition())
                                            visiblePlayers.add(player);
                                    }
                                }
                            }
                            Set<Player> targetPlayer= new HashSet<>();
                            targetPlayer.addAll(visiblePlayers);
                            targetPlayer.remove(currentPlayer);
                            visiblePlayers.clear();
                            visiblePlayers.addAll(targetPlayer);
                            if(visiblePlayers.isEmpty())
                                weapon.getEffect()[i] = null;
                            else{
                                weaponShot = createInfoShot(currentPlayer,visiblePlayers,weapon,weapon.getEffect()[i].getNameEffect(), weaponShots,weapon.getEffect()[i]);
                                weapon.getWeaponShots().add(weaponShot);
                            }
                            System.out.println(visiblePlayers);
                            break;
                        }
                        case "Cardinal" : {
                            MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(),5,currentPlayer.getPosition());
                            ArrayList<Square> cardinalSquares = new ArrayList<>();
                            cardinalSquares.addAll(movementChecker.getAllUpwardsSquares());
                            cardinalSquares.addAll(movementChecker.getAllRightSquares());
                            cardinalSquares.addAll(movementChecker.getAllDownwardsSquares());
                            cardinalSquares.addAll(movementChecker.getAllLeftSquares());

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
                            System.out.println(visiblePlayers);
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
                            System.out.println(visiblePlayers);
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
                            System.out.println(visiblePlayers);
                            break;
                        }
                        case "Cascade" :{
                        }
                        case "Cascade2" : {

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

