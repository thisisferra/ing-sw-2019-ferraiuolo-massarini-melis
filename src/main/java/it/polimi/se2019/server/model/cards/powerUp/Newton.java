package it.polimi.se2019.server.model.cards.powerUp;


import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.player.Player;

import java.io.Serializable;

public class Newton extends PowerUp implements Serializable {

    public Newton(PowerUp powerUp) {
        super();
        this.type = powerUp.getType();
        this.color = powerUp.getColor();
    }

    public Newton(String type, String color) {
        //Needed for resuming a powerup from saved match
        super();
        this.type = type;
        this.color = color;
    }

    @Override
    public void applyEffect(PowerUpShot powerUpShot) {
        int newPosition = powerUpShot.getNewPosition();
        Player player = powerUpShot.getTargetingPlayer();
        player.setPosition(newPosition);
    }
}
