package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public class PowerGlove extends AbstractWeapon {
    public void applyEffect(WeaponShot weaponShot){
        Square[] squares = weaponShot.getDamagingPlayer().getMatch().getMap().getAllSquare();
        ArrayList<Player> players = weaponShot.getDamagingPlayer().getMatch().getAllPlayers();
        int orientation = weaponShot.getTargetPlayer().get(0).getPosition()-weaponShot.getDamagingPlayer().getPosition();

        if(weaponShot.getNameEffect().equals("Optional1")){
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),1);
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealMark(weaponShot.getDamagingPlayer(),2);
            weaponShot.getDamagingPlayer().setPosition(weaponShot.getTargetPlayer().get(0).getPosition());
        }
        else if(weaponShot.getNameEffect().equals("Optional2")){
            weaponShot.getTargetPlayer().get(0).getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),2);
            switch(orientation){
                case 1 :{
                    if(squares[weaponShot.getTargetPlayer().get(0).getPosition()].getEast()!= -1){
                        for(Player player : players){
                            if(player.getPosition() == squares[weaponShot.getTargetPlayer().get(0).getPosition()].getEast()){
                                player.getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(),2);
                                weaponShot.getDamagingPlayer().setPosition(player.getPosition());
                            }
                        }
                    }
                    break;
                }

                case -1 :{
                    weaponShot.getDamagingPlayer().setPosition(weaponShot.getTargetPlayer().get(0).getPosition());
                    if(squares[weaponShot.getTargetPlayer().get(0).getPosition()].getWest()!= -1){
                        for(Player player : players) {
                            if (player.getPosition() == squares[weaponShot.getTargetPlayer().get(0).getPosition()].getWest()) {
                                player.getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(), 2);
                                weaponShot.getDamagingPlayer().setPosition(player.getPosition());
                            }
                        }
                    }
                    break;
                }

                case -4 :{
                    weaponShot.getDamagingPlayer().setPosition(weaponShot.getTargetPlayer().get(0).getPosition());
                    if(squares[weaponShot.getTargetPlayer().get(0).getPosition()].getNorth()!= -1) {
                        for (Player player : players) {
                            if (player.getPosition() == squares[weaponShot.getTargetPlayer().get(0).getPosition()].getNorth()) {
                                player.getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(), 2);
                                weaponShot.getDamagingPlayer().setPosition(player.getPosition());
                            }
                        }
                    }
                    break;
                }

                case 4 :{
                    weaponShot.getDamagingPlayer().setPosition(weaponShot.getTargetPlayer().get(0).getPosition());
                    if(squares[weaponShot.getTargetPlayer().get(0).getPosition()].getSouth()!= -1) {
                        for (Player player : players) {
                            if (player.getPosition() == squares[weaponShot.getTargetPlayer().get(0).getPosition()].getSouth()) {
                                player.getPlayerBoard().dealDamage(weaponShot.getDamagingPlayer(), 2);
                                weaponShot.getDamagingPlayer().setPosition(player.getPosition());
                            }
                        }
                    }
                    break;
                }
                case 0: {
                    weaponShot.getDamagingPlayer().setPosition(weaponShot.getTargetPlayer().get(0).getPosition());
                }
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
        for(int i = 0; i < length; i++)
            this.effect[i] = new Shot(weapon.getEffect()[i]);
    }
}
