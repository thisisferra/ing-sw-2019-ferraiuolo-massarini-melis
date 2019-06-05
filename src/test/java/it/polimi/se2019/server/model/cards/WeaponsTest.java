package it.polimi.se2019.server.model.cards;

import com.google.gson.Gson;
import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.model.cards.weapons.*;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class WeaponsTest {

    private Match match;
    private Weapon cyberblade;
    private Weapon electroscythe;
    private Weapon flamethrower;
    private Weapon furnace;
    private Weapon grenadeLauncher;
    private Weapon heatseeker;
    private Weapon hellion;
    private Weapon lockrifle;
    private Weapon machinegun;
    private Weapon plasmaGun;
    private Weapon powerGlove;
    private Weapon railgun;
    private Weapon rocketLauncher;
    private Weapon shockwave;
    private Weapon shotgun;
    private Weapon sledgehammer;
    private Weapon thor;
    private Weapon tractorBeam;
    private Weapon vortexCannon;
    private Weapon whisper;
    private Weapon zx2;
    private Player p1,p2,p3,p4,p5;
    private ArrayList<Player> enemyPlayers;
    private InfoShot infoShot;
    @Before
    public void setWeaponsTest(){
        Gson gson = new Gson();
        try{
            cyberblade = gson.fromJson(new FileReader("./src/main/resources/cyberblade.json"), Cyberblade.class);
            electroscythe =  gson.fromJson(new FileReader("./src/main/resources/electroscythe.json"), Electroscythe.class);
            flamethrower =  gson.fromJson(new FileReader("./src/main/resources/flamethrower.json"), Flamethrower.class);
            furnace = gson.fromJson(new FileReader("./src/main/resources/furnace.json"), Furnace.class);
            grenadeLauncher = gson.fromJson(new FileReader("./src/main/resources/grenade_launcher.json"), GrenadeLauncher.class);
            heatseeker = gson.fromJson(new FileReader("./src/main/resources/heatseeker.json"), Heatseeker.class);
            hellion = gson.fromJson(new FileReader("./src/main/resources/hellion.json"), Hellion.class);
            lockrifle = gson.fromJson(new FileReader("./src/main/resources/lock_rifle.json"), LockRifle.class);
            machinegun = gson.fromJson(new FileReader("./src/main/resources/machine_gun.json"), MachineGun.class);
            plasmaGun = gson.fromJson(new FileReader("./src/main/resources/plasma_gun.json"), PlasmaGun.class);
            powerGlove = gson.fromJson(new FileReader("./src/main/resources/power_glove.json"), PowerGlove.class);
            railgun = gson.fromJson(new FileReader("./src/main/resources/railgun.json"), Railgun.class);
            rocketLauncher = gson.fromJson(new FileReader("./src/main/resources/rocket_launcher.json"), RocketLauncher.class);
            shockwave = gson.fromJson(new FileReader("./src/main/resources/shockwave.json"), Shockwave.class);
            shotgun = gson.fromJson(new FileReader("./src/main/resources/shotgun.json"), Shotgun.class);
            sledgehammer = gson.fromJson(new FileReader("./src/main/resources/sledgehammer.json"), Sledgehammer.class);
            thor = gson.fromJson(new FileReader("./src/main/resources/thor.json"), Thor.class);
            tractorBeam = gson.fromJson(new FileReader("./src/main/resources/tractor_beam.json"), TractorBeam.class);
            vortexCannon = gson.fromJson(new FileReader("./src/main/resources/vortex_cannon.json"), VortexCannon.class);
            whisper = gson.fromJson(new FileReader("./src/main/resources/whisper.json"), Whisper.class);
            zx2 = gson.fromJson(new FileReader("./src/main/resources/zx-2.json"), ZX2.class);

        } catch (FileNotFoundException e){
            System.out.println("File non trovato");
        }
        match = new Match(1);
        match.initializeMatch();
        enemyPlayers = new ArrayList<Player>();
        p1 = new Player("Mattia","blue",match);
        p2 = new Player("Marco","yellow",match);
        p3 = new Player("Alessandro","grey",match);
        p4 = new Player("Matteo","green",match);
    }

    @Test
    public void testLockRifle(){
        enemyPlayers.add(p2);
        enemyPlayers.add(p3);
        infoShot = new InfoShot(lockrifle,0);
        infoShot.setTargetPlayers(enemyPlayers);       //p2,p3 get damaged
        infoShot.setDamagingPlayer(p1);                //p1 attacks
        infoShot.setNameEffect("BasicEffect");
        lockrifle.applyEffect(infoShot);
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        infoShot.setNameEffect("BasicEffect+Extra1");
        lockrifle.applyEffect(infoShot);
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyMarks().get(0).getMarks());

    }

    @Test
    public void testElectroscythe(){
        enemyPlayers.add(p2);
        enemyPlayers.add(p3);
        infoShot = new InfoShot(electroscythe,0);
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        infoShot.setNameEffect("Optional1");
        electroscythe.applyEffect(infoShot);
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p1.getScore());
        infoShot.setNameEffect("Optional2");
        electroscythe.applyEffect(infoShot);
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(3,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
    }

    @Test
    public void testMachineGun(){
        //basic effect
        enemyPlayers.add(p2);
        infoShot = new InfoShot(machinegun,0);
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        infoShot.setNameEffect("BasicEffect");
        machinegun.applyEffect(infoShot);  //p2 1 damage
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        enemyPlayers.add(p3);
        infoShot.setTargetPlayers(enemyPlayers);
        machinegun.applyEffect(infoShot); //p2 2 damage p3 1 damage
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        //basic effect + extra1
        infoShot.setNameEffect("BasicEffect+Extra1");
        machinegun.applyEffect(infoShot); //p2 4 damage p3 2 damage
        Assert.assertEquals(4,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        //basic effect + extra 2
        infoShot.setNameEffect("BasicEffect+Extra2");
        machinegun.applyEffect(infoShot); //p2 5 damage p3 4 damage
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(4,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        enemyPlayers.add(p4);
        infoShot.setTargetPlayers(enemyPlayers); //3 targets: p2 5 damage, p3 4 damage, p4 0 damage
        machinegun.applyEffect(infoShot); // p2 6 damage, p3 6 damage,p4 1 damage
        Assert.assertEquals(6,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(6,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        //basic effect + extra 1 + extra
        infoShot.setNameEffect("BasicEffect+Extra1+Extra2");
        machinegun.applyEffect(infoShot); // p2 8 damage, p3 8 damage, p4 2 damage
        Assert.assertEquals(8,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(8,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());

    }

    @Test
    public void testTractorBeam(){
        enemyPlayers.add(p2);
        infoShot = new InfoShot(tractorBeam,0);
        infoShot.setNameEffect("Optional1");
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        infoShot.setNewPosition(5);
        tractorBeam.applyEffect(infoShot); //p2 1 damage, pos 5
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(5,p2.getPosition());
        infoShot.setNameEffect("Optional2");
        tractorBeam.applyEffect(infoShot);  //p2 4 damage, p2 pos == p1 pos
        Assert.assertEquals(4,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(p1.getPosition(),p2.getPosition());
    }

    @Test
    public void testThor(){

    }


    @Test
    public void testVortexCannon(){
        enemyPlayers.add(p2);
        infoShot = new InfoShot(vortexCannon,0);
        infoShot.setNameEffect("BasicEffect");
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        infoShot.setNewPosition(5);
        vortexCannon.applyEffect(infoShot); //p2 2 damage, pos 5
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(5,p2.getPosition());
        infoShot.setNameEffect("BasicEffect+Extra1");
        enemyPlayers.add(p3);
        enemyPlayers.add(p4);
        infoShot.setNewPosition(9);
        vortexCannon.applyEffect(infoShot);  //p2 4 damage, p3 1 damage, p4 1 damage, pos = 9
        Assert.assertEquals(4,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(9,p2.getPosition());
        Assert.assertEquals(9,p3.getPosition());
        Assert.assertEquals(9,p4.getPosition());
    }

    @Test
    public void testFurnace(){
        enemyPlayers.add(p2);
        enemyPlayers.add(p3);
        enemyPlayers.add(p4);
        infoShot = new InfoShot(furnace,0);
        infoShot.setNameEffect("Optional1");
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        furnace.applyEffect(infoShot); //p2,p3,p4 1 damage
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        infoShot.setNameEffect("Optional2");
        furnace.applyEffect(infoShot);  //p2,p3,p4 2 damage 1 mark
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyMarks().get(0).getMarks());
    }

    @Test
    public void testPlasmaGun(){

    }

    @Test
    public void testHeatseeker(){
        enemyPlayers.add(p2);
        infoShot = new InfoShot(heatseeker,0);
        infoShot.setNameEffect("BasicEffect");
        infoShot.setDamagingPlayer(p1);
        infoShot.setTargetPlayers(enemyPlayers);
        heatseeker.applyEffect(infoShot);  //3 damage
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
    }

    @Test
    public void testWhisper(){
        enemyPlayers.add(p2);
        infoShot = new InfoShot(whisper,0);
        infoShot.setNameEffect("BasicEffect");
        infoShot.setDamagingPlayer(p1);
        infoShot.setTargetPlayers(enemyPlayers);
        whisper.applyEffect(infoShot); // 3 damage 1 mark
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
    }

    @Test
    public void testFlamethrower(){
        p2.setPosition(6);
        p3.setPosition(7);
        p4.setPosition(7);
        p1.setPosition(5);
        enemyPlayers.add(p2);
        enemyPlayers.add(p3);
        infoShot = new InfoShot(flamethrower,0);
        infoShot.setNameEffect("Optional1");
        infoShot.setDamagingPlayer(p1);
        infoShot.setTargetPlayers(enemyPlayers);
        flamethrower.applyEffect(infoShot); //p2, p3 1 damage
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        enemyPlayers.add(p4);
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setNameEffect("Optional2");
        flamethrower.applyEffect(infoShot);  //p2 3 damage p3 2 damage p4 1 damage
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());
    }

    @Test
    public void testZX2(){
        enemyPlayers.add(p2);
        infoShot = new InfoShot(zx2,0);
        infoShot.setNameEffect("Optional1");
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        zx2.applyEffect(infoShot); //p2 1 damage 2 marks
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        infoShot.setNameEffect("Optional2");
        enemyPlayers.add(p3);
        enemyPlayers.add(p4);
        infoShot.setTargetPlayers(enemyPlayers);
        zx2.applyEffect(infoShot); // p2 1 damage 3 marks, p3 1 mark, p4 1 mark
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyMarks().get(0).getMarks());

    }

    @Test
    public void testGrenadeLauncher(){

    }

    @Test
    public void testShotgun(){
        enemyPlayers.add(p2);
        infoShot = new InfoShot(shotgun,0);
        infoShot.setNameEffect("Optional1");
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        infoShot.setNewPosition(5);
        shotgun.applyEffect(infoShot); //p2 3 damage
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(5,p2.getPosition());
        infoShot.setNameEffect("Optional2");
        shotgun.applyEffect(infoShot); //p2 2 damage
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
    }

    @Test
    public void testRocketLauncher(){

    }

    @Test
    public void testPowerGlove(){
        enemyPlayers.add(p2);
        infoShot = new InfoShot(powerGlove,0);
        infoShot.setNameEffect("Optional1");
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        powerGlove.applyEffect(infoShot); //p2 1 damage, 2 marks
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(p1.getPosition(),p2.getPosition());
        infoShot.setNameEffect("Optional2");
        enemyPlayers.add(p3);
        infoShot.setTargetPlayers(enemyPlayers);
        powerGlove.applyEffect(infoShot);  //p2 5 damage, p3 2 damage, pos. p1 == pos p3
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertTrue(p2.getPlayerBoard().getEnemyMarks().isEmpty());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(p1.getPosition(),p3.getPosition());
    }

    @Test
    public void testRailgun(){
        enemyPlayers.add(p2);
        infoShot = new InfoShot(railgun,0);
        infoShot.setNameEffect("Optional1");
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        railgun.applyEffect(infoShot); //3 damages to p2
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        enemyPlayers.add(p3);
        infoShot.setNameEffect("Optional2");
        infoShot.setTargetPlayers(enemyPlayers);
        railgun.applyEffect(infoShot);
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());

    }

    @Test
    public void testShockwave(){

    }

    @Test
    public void testCyberblade(){

    }

    @Test
    public void testSledgehammer(){
        enemyPlayers.add(p2);
        infoShot = new InfoShot(sledgehammer,0);
        infoShot.setNameEffect("Optional1");
        infoShot.setTargetPlayers(enemyPlayers);
        infoShot.setDamagingPlayer(p1);
        sledgehammer.applyEffect(infoShot); //2 damages
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        infoShot.setNameEffect("Optional2");
        infoShot.setNewPosition(5);
        sledgehammer.applyEffect(infoShot);
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(5,p2.getPosition());

    }
}
