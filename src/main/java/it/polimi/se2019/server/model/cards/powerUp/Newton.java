package it.polimi.se2019.server.model.cards.powerUp;

import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.model.player.Player;

import java.io.Serializable;

public class Newton extends PowerUp implements Serializable {

    public Newton(PowerUp powerUp) {
        super();
        this.type = powerUp.getType();
        this.color = powerUp.getColor();
    }

    @Override
    public void applyEffect(PowerUpShot powerUpShot) {
        int newPosition = powerUpShot.getNewPosition();
        Player player = powerUpShot.getTargetingPlayer();
        player.setPosition(newPosition);
    }
}
