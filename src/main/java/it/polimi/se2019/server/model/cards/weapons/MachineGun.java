package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public class MachineGun extends AbstractWeapon {
    public void applyEffect(InfoShot infoShot){
        if (infoShot.getNameEffect().equals("BasicEffect")) {
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            if(infoShot.getTargetPlayer().size()==2)
                infoShot.getTargetPlayer().get(1).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);

        }
        else if(infoShot.getNameEffect().equals("BasicEffect+Extra1")) {
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),2);
            if(infoShot.getTargetPlayer().size()==2)
                infoShot.getTargetPlayer().get(1).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
        }
        else if(infoShot.getNameEffect().equals("BasicEffect+Extra2")) {
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            if(infoShot.getTargetPlayer().size()>1)
                infoShot.getTargetPlayer().get(1).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),2);
            if(infoShot.getTargetPlayer().size()==3){
                infoShot.getTargetPlayer().get(2).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            }
        }
        else if(infoShot.getNameEffect().equals("BasicEffect+Extra1+Extra2")) {
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),2);
            if(infoShot.getTargetPlayer().size()>1)
                infoShot.getTargetPlayer().get(1).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),2);
            if(infoShot.getTargetPlayer().size()>2){
                infoShot.getTargetPlayer().get(2).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            }
        }
        this.setLoad(false);
    }

    public  MachineGun (Weapon weapon){
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
