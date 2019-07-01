package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.RoomChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public class Furnace extends AbstractWeapon {
    public void applyEffect(WeaponShot weaponShot){
        if(weaponShot.getNameEffect().equals("Optional1")){
            Square[] allSquares = weaponShot.getTargetPlayer().get(0).getMatch().getMap().getAllSquare();
            String color = allSquares[weaponShot.getTargetPlayer().get(0).getPosition()].getColor();
            for(Player target : weaponShot.getDamagingPlayer().getMatch().getAllPlayers()) {
                if(allSquares[target.getPosition()].getColor().equals(color)){
                    target.getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
                }
            }
        }
        else if(weaponShot.getNameEffect().equals("Optional2")){
            for(Player target : weaponShot.getDamagingPlayer().getMatch().getAllPlayers()) {
                if (target.getPosition() == weaponShot.getTargetPlayer().get(0).getPosition()){
                    target.getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(), 1);
                    target.getPlayerBoard().dealMark(weaponShot.getDamagingPlayer(),1);
                }
            }
        }
        this.setLoad(false);
    }
    public  Furnace (Weapon weapon){
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
