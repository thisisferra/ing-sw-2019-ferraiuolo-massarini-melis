package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public class PowerGlove extends AbstractWeapon {
    public void applyEffect(InfoShot infoShot){
        if(infoShot.getNameEffect().equals("Optional1")){
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealMark(infoShot.getDamagingPlayer(),2);
            infoShot.getTargetPlayer().get(0).setPosition(infoShot.getTargetPlayer().get(0).getPosition());

        }
        else if(infoShot.getNameEffect().equals("Optional2")){
            if(!infoShot.getTargetPlayer().isEmpty()){
                infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),2);
                infoShot.getDamagingPlayer().setPosition(infoShot.getTargetPlayer().get(0).getPosition());
            }
            if(infoShot.getTargetPlayer().size() >1){
                infoShot.getTargetPlayer().get(1).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),2);
                infoShot.getDamagingPlayer().setPosition(infoShot.getTargetPlayer().get(1).getPosition());
            }
        }
        this.setLoad(false);
    }

    public  PowerGlove (Weapon weapon){
        this.type = weapon.getType();
        this.load = weapon.getLoad();
        this.buyingCost = new Cubes(weapon.getBuyingCost().getReds(), weapon.getBuyingCost().getYellows(), weapon.getBuyingCost().getBlues());
        this.reloadCost = new Cubes(weapon.getReloadCost().getReds(), weapon.getReloadCost().getYellows(), weapon.getReloadCost().getBlues());
        int length = weapon.getEffect().length;
        this.effect = new Shot[length];
        this.maxTarget = weapon.getMaxTarget();
        for(int i = 0; i < length; i++)
            this.effect[i] = new Shot(weapon.getEffect()[i]);
    }
}
