package it.polimi.se2019.server.model.cards.powerUp;


import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.player.EnemyDamage;
import it.polimi.se2019.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class PowerUp implements Serializable {
    protected String type;
    protected String color;
    protected Shot effect;

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

    public abstract void applyEffect(PowerUpShot powerUpShot);

}