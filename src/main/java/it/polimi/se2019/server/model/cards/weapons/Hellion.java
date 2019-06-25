package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;

public class Hellion extends AbstractWeapon {
    public void applyEffect(WeaponShot weaponShot){
        if(weaponShot.getNameEffect().equals("Optional1")){
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
            for(Player target : weaponShot.getTargetPlayer()){
                target.getPlayerBoard().dealMark(weaponShot.getDamagingPlayer(),1);
            }
        }
        else if(weaponShot.getNameEffect().equals("Optional2")){
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
            for(Player target : weaponShot.getTargetPlayer()){
                target.getPlayerBoard().dealMark(weaponShot.getDamagingPlayer(),2);
            }
        }
        this.setLoad(false);
    }

    public  Hellion (Weapon weapon){
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
