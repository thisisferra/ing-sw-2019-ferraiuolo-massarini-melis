package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.game.RoomChecker;
import it.polimi.se2019.server.model.map.Square;

import java.util.ArrayList;

public class TractorBeam extends AbstractWeapon {
    public void applyEffect(WeaponShot weaponShot){
        int pos = 0;
        if(weaponShot.getNameEffect().equals("Optional1")){
            /*
            MovementChecker movementChecker = new MovementChecker(weaponShot.getDamagingPlayer().getMatch().getMap().getAllSquare(),2,weaponShot.getTargetPlayer().get(0).getPosition());
            RoomChecker roomChecker = new RoomChecker(weaponShot.getDamagingPlayer().getMatch().getMap(),weaponShot.getDamagingPlayer().getPosition());
            ArrayList<Square> intersecionSquare = new ArrayList<>();
            for(Square square : movementChecker.getReachableSquares()){
                for(Square roomSquare :roomChecker.getAccessibleRooms()){
                    if(roomSquare.getPosition() == square.getPosition())
                        intersecionSquare.add(square);
                }
            }
            if(!intersecionSquare.isEmpty())
                pos = intersecionSquare.get(0).getPosition();
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
            weaponShot.getTargetPlayer().get(0).setPosition(pos);
             */
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
            weaponShot.getTargetPlayer().get(0).setPosition(weaponShot.getNewPosition());
        }
        else if(weaponShot.getNameEffect().equals("Optional2")){
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),3);
            weaponShot.getTargetPlayer().get(0).setPosition(weaponShot.getDamagingPlayer().getPosition());
        }
        this.setLoad(false);
    }
    public  TractorBeam (Weapon weapon){
        this.type = weapon.getType();
        this.load = weapon.getLoad();
        this.buyingCost = new Cubes(weapon.getBuyingCost().getReds(), weapon.getBuyingCost().getYellows(), weapon.getBuyingCost().getBlues());
        this.reloadCost = new Cubes(weapon.getReloadCost().getReds(), weapon.getReloadCost().getYellows(), weapon.getReloadCost().getBlues());
        int length = weapon.getEffect().length;
        this.effect = new Shot[length];
        for(int i = 0; i < length; i++)
            this.effect[i] = new Shot(weapon.getEffect()[i]);
    }
}
