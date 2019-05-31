package it.polimi.se2019.server.model.cards.Weapons;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public class GrenadeLauncher extends AbstractWeapon {
    public ArrayList<Player> getTargets(){
        return new ArrayList<>();
    }
    public void applyEffect(InfoShot infoShot){
        //TODO
        if(infoShot.getNameEffect().equals("Extra1+BasicEffect")){

        }
        if(infoShot.getNameEffect().equals("BasicEffect")){
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            infoShot.getTargetPlayer().get(0).setPosition(infoShot.getNewPosition());
        }
        if(infoShot.getNameEffect().equals("BasicEffect+Extra1")){
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            for(Player target: infoShot.getTargetPlayer()){
                target.getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            }
        }
    }

    public  GrenadeLauncher (Weapon weapon){
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
