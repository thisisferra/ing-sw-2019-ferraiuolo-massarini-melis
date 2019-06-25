package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;

public class VortexCannon extends AbstractWeapon {
    public void applyEffect(WeaponShot weaponShot){
        if(weaponShot.getNameEffect().equals("BasicEffect")){
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),2);
            weaponShot.getTargetPlayer().get(0).setPosition(weaponShot.getNewPosition());
        }
        else if(weaponShot.getNameEffect().equals("BasicEffect+Extra1")){
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),2);
            weaponShot.getTargetPlayer().get(0).setPosition(weaponShot.getNewPosition());
            if(weaponShot.getTargetPlayer().size()>1){
                weaponShot.getTargetPlayer().get(1).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
                weaponShot.getTargetPlayer().get(1).setPosition(weaponShot.getNewPosition());
            }
            if(weaponShot.getTargetPlayer().size()>2){
                weaponShot.getTargetPlayer().get(2).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
                weaponShot.getTargetPlayer().get(2).setPosition(weaponShot.getNewPosition());
            }
        }
        this.setLoad(false);
    }

    public  VortexCannon (Weapon weapon){
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
