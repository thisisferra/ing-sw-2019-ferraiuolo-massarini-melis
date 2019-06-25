package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;


public class Flamethrower extends AbstractWeapon {
    public void applyEffect(WeaponShot weaponShot){
        MovementChecker m1;
        if(weaponShot.getNameEffect().equals("Optional1")){
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
            weaponShot.getTargetPlayer().get(1).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
        }
        else if(weaponShot.getNameEffect().equals("Optional2")){
            for(Player target: weaponShot.getTargetPlayer()){
                target.getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
            }
            m1 = new MovementChecker(weaponShot.getDamagingPlayer().getMatch().getMap().getAllSquare(),1, weaponShot.getDamagingPlayer().getPosition());
            ArrayList<Square> reachableSquares = m1.getReachableSquares();
            for(Player player : weaponShot.getTargetPlayer()){
                for(Square square : reachableSquares){
                    if(player.getPosition() == square.getPosition())
                        player.getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
                }
            }
        }
        this.setLoad(false);
    }
    public  Flamethrower (Weapon weapon){
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
