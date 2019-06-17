package it.polimi.se2019.server.model.cards.powerUp;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Teleporter extends PowerUp implements Serializable {

    public Teleporter(PowerUp powerUp) {
        super();
        this.type = powerUp.getType();
        this.color = powerUp.getColor();
    }

    @Override
    public void applyEffect(InfoShot infoShot) {
    }
}
