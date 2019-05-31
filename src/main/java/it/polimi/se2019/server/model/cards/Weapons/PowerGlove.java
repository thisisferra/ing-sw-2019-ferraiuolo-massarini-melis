package it.polimi.se2019.server.model.cards.Weapons;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;

import javax.sound.sampled.Line;
import java.util.ArrayList;

public class PowerGlove extends AbstractWeapon {
    public ArrayList<Player> getTargets(){
        return new ArrayList<>();
    }
    public void applyEffect(InfoShot infoShot){
        if(infoShot.getNameEffect().equals("Optional1")){
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealMark(infoShot.getDamagingPlayer(),2);
            infoShot.getTargetPlayer().get(0).setPosition(infoShot.getTargetPlayer().get(0).getPosition());

        }
        else if(infoShot.getNameEffect().equals("Optional2")){
            if(infoShot.getTargetPlayer().size() ==1){
                infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),2);
                infoShot.getDamagingPlayer().setPosition(infoShot.getTargetPlayer().get(0).getPosition());
            }
            if(infoShot.getTargetPlayer().size() ==2){
                infoShot.getTargetPlayer().get(1).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),2);
                infoShot.getDamagingPlayer().setPosition(infoShot.getTargetPlayer().get(1).getPosition());
            }
        }
    }

    public  PowerGlove (Weapon weapon){
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
