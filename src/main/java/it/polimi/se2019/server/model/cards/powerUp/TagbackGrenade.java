package it.polimi.se2019.server.model.cards.powerUp;

import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.model.player.Player;

import java.io.Serializable;

public class TagbackGrenade extends PowerUp implements Serializable {

    public TagbackGrenade(PowerUp powerUp) {
        super();
        this.type = powerUp.getType();
        this.color = powerUp.getColor();
    }

    public TagbackGrenade(String type, String color) {
        //Needed for resuming a powerup from saved match
        super();
        this.type = type;
        this.color = color;
    }

    @Override
    public void applyEffect(PowerUpShot powerUpShot) {
        Player damagingPlayer = powerUpShot.getDamagingPlayer();
        Player targetPlayer = powerUpShot.getTargetingPlayer();
        targetPlayer.getPlayerBoard().dealMark(damagingPlayer, 1);
    }
}
