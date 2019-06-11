package it.polimi.se2019.server.model.cards;


import it.polimi.se2019.server.model.player.EnemyDamage;
import it.polimi.se2019.server.model.player.Player;

import java.io.Serializable;

public class PowerUp implements Serializable {
    private String type;
    private String color;
    private Shot effect;

    public String getType(){
        return this.type;
    }

    public String getColor(){
        return this.color;
    }

    //return the infos about the powerup
    public String toString(){
        return "Type: " + this.type + " Color: " + this.color;
    }


//    //Effect of targeting scope
//    public void effect(Player damagingPlayer, Player targetingPlayer) {
//        EnemyDamage newEnemyDamages = new EnemyDamage(damagingPlayer, 1);
//        targetingPlayer.getEnemyDamages().add(newEnemyDamages);
//    }
//
//    //Effect of teleporter
//    public void effect(Player ownerPowerUp, int newPosition){
//        ownerPowerUp.setPosition(newPosition);
//    }

}