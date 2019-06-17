package it.polimi.se2019.server.model.cards.powerUp;


import it.polimi.se2019.server.controller.InfoShot;

import java.io.Serializable;

public class Newton extends PowerUp implements Serializable {

    public Newton(PowerUp powerUp) {
        super();
        this.type = powerUp.getType();
        this.color = powerUp.getColor();
    }

    @Override
    public void applyEffect(InfoShot infoShot) {
        System.out.println("Ciao sono Newton");
    }
}
