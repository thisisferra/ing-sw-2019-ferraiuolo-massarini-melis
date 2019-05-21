package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.EnemyDamage;
import it.polimi.se2019.server.model.player.MarkDamage;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public class LockRifle extends AbstractWeapon {
    public ArrayList<Player> getTargets(){
        return new ArrayList<>();
    }


    public void applyEffect(InfoShot infoShot){
        if (infoShot.getNameEffect().equals("BasicEffect")) {
            infoShot.getTargetPlayer().get(0).getEnemyDamages().add(new EnemyDamage(infoShot.getDamagingPlayer(), 1));
            infoShot.getTargetPlayer().get(0).getEnemyDamages().add(new EnemyDamage(infoShot.getDamagingPlayer(), 1));
            infoShot.getTargetPlayer().get(0).getMarkDamages().add(new MarkDamage(infoShot.getDamagingPlayer(), 1));
        }
        if (infoShot.getNameEffect().equals("BasicEffect+Extra1")) {
            infoShot.getTargetPlayer().get(0).getEnemyDamages().add(new EnemyDamage(infoShot.getDamagingPlayer(), 1));
            infoShot.getTargetPlayer().get(0).getEnemyDamages().add(new EnemyDamage(infoShot.getDamagingPlayer(), 1));
            infoShot.getTargetPlayer().get(0).getMarkDamages().add(new MarkDamage(infoShot.getDamagingPlayer(), 1));
            infoShot.getTargetPlayer().get(1).getMarkDamages().add(new MarkDamage(infoShot.getDamagingPlayer(), 1));
        }
    }


    public  LockRifle (Weapon weapon){
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
