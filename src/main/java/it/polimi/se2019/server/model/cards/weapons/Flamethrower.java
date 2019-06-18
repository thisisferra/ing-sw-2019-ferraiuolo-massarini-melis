package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;


public class Flamethrower extends AbstractWeapon {
    public void applyEffect(InfoShot infoShot){
        MovementChecker m1;
        if(infoShot.getNameEffect().equals("Optional1")){
            infoShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            infoShot.getTargetPlayer().get(1).getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
        }
        else if(infoShot.getNameEffect().equals("Optional2")){
            for(Player target:infoShot.getTargetPlayer()){
                target.getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
            }
            m1 = new MovementChecker(infoShot.getDamagingPlayer().getMatch().getMap().getAllSquare(),1,infoShot.getDamagingPlayer().getPosition());
            ArrayList<Square> reachableSquares = m1.getReachableSquares();
            for(Player player : infoShot.getTargetPlayer()){
                for(Square square : reachableSquares){
                    if(player.getPosition() == square.getPosition())
                        player.getPlayerBoard().dealDamage(infoShot.getDamagingPlayer(),1);
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
