package it.polimi.se2019.server.model.cards;

import com.google.gson.Gson;
import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.model.cards.powerUp.*;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class PowerUpTest {
    private Match match;
    private ArrayList<Player> enemyPlayers;
    private Player p1,p2,p3,p4,p5;
    private PowerUp teleporter, targetingScope,newton,tagbackGrenade,copy;
    @Before
    public void setPowerUpTest(){
        Gson gson = new Gson();
        try{
            teleporter = gson.fromJson(new FileReader("./src/main/resources/teleporterRed.json"), Teleporter.class);
            targetingScope = gson.fromJson(new FileReader("./src/main/resources/targeting_scopeYellow.json"), TargetingScope.class);
            newton = gson.fromJson(new FileReader("./src/main/resources/newtonYellow.json"), Newton.class);
            tagbackGrenade = gson.fromJson(new FileReader("./src/main/resources/tagback_grenadeBlue.json"), TagbackGrenade.class);
        } catch ( FileNotFoundException e){
            e.printStackTrace();
        }

        match = new Match(1);
        match.initializeMatch();
        enemyPlayers = new ArrayList<Player>();
        p1 = new Player("Mattia",match);
        p2 = new Player("Marco",match);
        p3 = new Player("Alessandro",match);
        p4 = new Player("Matteo",match);
        p5 = new Player("Bruno",match);
        match.getAllPlayers().add(p1);
        match.getAllPlayers().add(p2);
        match.getAllPlayers().add(p3);
        match.getAllPlayers().add(p4);
        match.getAllPlayers().add(p5);

    }

    @Test
    public void testTeleporter(){
        PowerUpShot powerUpShot = new PowerUpShot();
        powerUpShot.setNewPosition(5);
        powerUpShot.setTargetingPlayer(p1);
        powerUpShot.setDamagingPlayer(p1);
        powerUpShot.setCubeToPay(new Cubes(0,0,0));
        teleporter.applyEffect(powerUpShot);
        Assert.assertEquals(5,p1.getPosition());
        copy = new Teleporter(teleporter);
        Assert.assertEquals(teleporter.getColor(),copy.getColor());
        Assert.assertEquals(teleporter.getType(),copy.getType());
        Assert.assertEquals("Type: teleporter Color: red",teleporter.toString());
    }

    @Test
    public void testNewton(){
        PowerUpShot powerUpShot = new PowerUpShot();
        powerUpShot.setNewPosition(5);
        powerUpShot.setTargetingPlayer(p2);
        powerUpShot.setDamagingPlayer(p1);
        powerUpShot.setCubeToPay(new Cubes(0,0,0));
        newton.applyEffect(powerUpShot);
        Assert.assertEquals(5,p2.getPosition());
        copy = new Newton(newton);
        Assert.assertEquals(newton.getColor(),copy.getColor());
        Assert.assertEquals(newton.getType(),copy.getType());
        Assert.assertEquals("Type: newton Color: yellow",newton.toString());

    }

    @Test
    public void testTargetingScope(){
        PowerUpShot powerUpShot = new PowerUpShot();
        powerUpShot.setNewPosition(0);
        powerUpShot.setTargetingPlayer(p2);
        powerUpShot.setDamagingPlayer(p1);
        powerUpShot.setCubeToPay(new Cubes(0,0,0));
        targetingScope.applyEffect(powerUpShot);
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        copy = new TargetingScope(targetingScope);
        Assert.assertEquals(targetingScope.getColor(),copy.getColor());
        Assert.assertEquals(targetingScope.getType(),copy.getType());
        Assert.assertEquals("Type: targeting_scope Color: yellow",targetingScope.toString());

    }

    @Test
    public void testTagbackGrenade(){
        PowerUpShot powerUpShot = new PowerUpShot();
        powerUpShot.setNewPosition(5);
        powerUpShot.setTargetingPlayer(p2);
        powerUpShot.setDamagingPlayer(p1);
        powerUpShot.setCubeToPay(new Cubes(0,0,0));
        tagbackGrenade.applyEffect(powerUpShot);
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        copy = new TagbackGrenade(tagbackGrenade);
        Assert.assertEquals(tagbackGrenade.getColor(),copy.getColor());
        Assert.assertEquals(tagbackGrenade.getType(),copy.getType());
        Assert.assertEquals("Type: tagback_grenade Color: blue",tagbackGrenade.toString());

    }
}
