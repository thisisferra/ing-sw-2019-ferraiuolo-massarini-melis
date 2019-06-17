package it.polimi.se2019.server.model.cards.powerUp;


import it.polimi.se2019.server.controller.InfoShot;

import java.io.Serializable;

public class TargetingScope extends PowerUp implements Serializable {

    public TargetingScope(PowerUp powerUp) {
        super();
        this.type = powerUp.getType();
        this.color = powerUp.getColor();
    }

    @Override
    public void applyEffect(InfoShot infoShot) {
        System.out.println("Ciao sono TargetingScope");
    }
}
