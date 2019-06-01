package it.polimi.se2019;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;


public class App {
    public static void main( String[] args ) {
        Match m1  = new Match(1,5);
        m1.initializeMatch();
        Player p0 = m1.getAllPlayers().get(0);  //attaccante
        Player p1 = m1.getAllPlayers().get(1);  //ricevente
        Player p2 = m1.getAllPlayers().get(2);
        Player p3 = m1.getAllPlayers().get(3);
        Player p4 = m1.getAllPlayers().get(4);
        Weapon lockRifle = null;
        for(Weapon weapon : m1.getWeaponStack()){
            if(weapon.getType().equals("lock_rifle"))
                lockRifle = weapon;
        }
        ArrayList<Player> players = new ArrayList<>();
        p1.setPosition(5);  //posizione del ricevente
        players.add(p1);
        players.add(p2);
        InfoShot infoShot = new InfoShot(lockRifle,1);
        infoShot.setDamagingPlayer(p0);
        infoShot.setTargetablePlayer(players);
        infoShot.setNameEffect("BasicEffect+Extra1");
        infoShot.setTargetPlayers(players);
        System.out.println();
        if(lockRifle!=null)
            lockRifle.applyEffect(infoShot);
        System.out.println(p1.getPlayerBoard().getEnemyDamages() + " "+ p2.getPlayerBoard().getEnemyMarks()+" "+ p0.getScore());
    }
}