package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.CubesChecker;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.game.RoomChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShotController {

    Controller controller;
    Match match;

    public ShotController(Match match) {
        this.match = match;
    }

    public ArrayList<Weapon> checkAll(Player currentPlayer) {
        ArrayList<Weapon> loadedWeapon = checkIsLoad(currentPlayer);
        ArrayList<Weapon> checkedWeapon;
        ArrayList<Weapon> usableWeapon;
        if (loadedWeapon.size() == 0) {
            return null;
        }
        else {
            checkedWeapon = checkCubes(currentPlayer, loadedWeapon);
            usableWeapon = checkVisibility(match, currentPlayer, checkedWeapon);
        }
        return usableWeapon;
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

    public ArrayList<Weapon> checkVisibility(Match match, Player currentPlayer, ArrayList<Weapon> checkedWeapon) {
        boolean usable = false;
        ArrayList<Weapon> weaponCanUse = new ArrayList<>();
        ArrayList<Player> visiblePlayers = new ArrayList<>();
        ArrayList<Player> notVisiblePlayers = new ArrayList<>();
        for (Weapon weapon : checkedWeapon) {
            for (int i = 0; i < weapon.getEffect().length; i++) {
                if(weapon.getEffect()[i] != null){
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
                            }
                            System.out.println(visiblePlayers);
                            visiblePlayers.clear();
                            break;
                        }
                        case "CantSee": {
                            RoomChecker roomChecker = new RoomChecker(match.getMap(), currentPlayer.getPosition());
                            visiblePlayers = roomChecker.getVisiblePlayers(match, currentPlayer);
                            notVisiblePlayers.addAll(match.getAllPlayers());
                            notVisiblePlayers.removeAll(visiblePlayers);
                            notVisiblePlayers.remove(currentPlayer);
                            if (!notVisiblePlayers.isEmpty()) {
                                weapon.getEffect()[i] = null;
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
                                    if (player.getPosition() == currentPlayer.getPosition() && player.getClientName() != currentPlayer.getClientName()) {
                                        visiblePlayers.add(player);
                                    }
                                }
                            }
                            else {
                                int distance = maxDistanceTarget - minDistanceTarget;
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
                            if(visiblePlayers.isEmpty()) weapon.getEffect()[i] = null;
                            System.out.println(visiblePlayers);
                            visiblePlayers.clear();
                            break;
                        }
                        case "CanSeeRoom": {
                            for (Square square : match.getMap().getAllSquare()) {
                                if (currentPlayer.getPosition() == square.getPosition()) {
                                    if (!(square.getNorthWall() != -1 || square.getEastWall() != -1 || square.getSouthWall() != -1 || square.getWestWall() != -1)) {
                                        weapon.getEffect()[i] = null;
                                    }
                                }
                            }
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
                            if(visiblePlayers.isEmpty()) weapon.getEffect()[i] = null;
                            System.out.println(visiblePlayers);
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

}

