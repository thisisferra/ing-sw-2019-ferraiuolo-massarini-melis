package it.polimi.se2019.server.model.cards;

import com.google.gson.Gson;
import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.model.cards.weapons.*;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Weapon copy;
    private Player p1,p2,p3,p4,p5;
    private ArrayList<Player> enemyPlayers;
    private WeaponShot weaponShot;
    Logger logger = Logger.getAnonymousLogger();
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
            logger.log(Level.INFO,"Test error, loading json files");
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
    public void testLockRifle(){
        enemyPlayers.add(p2);
        enemyPlayers.add(p3);
        weaponShot = new WeaponShot(lockrifle,"BasicEffect");
        weaponShot.setTargetPlayers(enemyPlayers);       //p2,p3 get damaged
        weaponShot.setDamagingPlayer(p1);                //p1 attacks
        lockrifle.applyEffect(weaponShot);
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        weaponShot.setNameEffect("BasicEffect+Extra1");
        lockrifle.applyEffect(weaponShot);
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        copy = new LockRifle(lockrifle);
        Assert.assertEquals(copy.getType(),(lockrifle.getType()));
        Assert.assertEquals(copy.getLoad(),(lockrifle.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(lockrifle.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(lockrifle.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(lockrifle.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(lockrifle.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(lockrifle.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(lockrifle.getReloadCost().getBlues()));


    }

    @Test
    public void testElectroscythe(){
        enemyPlayers.add(p2);
        p1.setPosition(1);
        p2.setPosition(1);
        p3.setPosition(1);
        weaponShot = new WeaponShot(electroscythe,"Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        electroscythe.applyEffect(weaponShot);
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p1.getScore());
        weaponShot.setNameEffect("Optional2");
        electroscythe.applyEffect(weaponShot);
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(3,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        copy = new Electroscythe(electroscythe);
        Assert.assertEquals(copy.getType(),(electroscythe.getType()));
        Assert.assertEquals(copy.getLoad(),(electroscythe.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(electroscythe.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(electroscythe.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(electroscythe.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(electroscythe.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(electroscythe.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(electroscythe.getReloadCost().getBlues()));
    }

    @Test
    public void testMachineGun(){
        //basic effect
        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(machinegun,"BasicEffect");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        weaponShot.setNameEffect("BasicEffect");
        machinegun.applyEffect(weaponShot);  //p2 1 damage
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        enemyPlayers.add(p3);
        weaponShot.setTargetPlayers(enemyPlayers);
        machinegun.applyEffect(weaponShot); //p2 2 damage p3 1 damage
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        //basic effect + extra1
        weaponShot.setNameEffect("BasicEffect+Extra1");
        machinegun.applyEffect(weaponShot); //p2 4 damage p3 2 damage
        Assert.assertEquals(4,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        //basic effect + extra 2
        weaponShot.setNameEffect("BasicEffect+Extra2");
        machinegun.applyEffect(weaponShot); //p2 5 damage p3 4 damage
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(4,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        enemyPlayers.add(p4);
        weaponShot.setTargetPlayers(enemyPlayers); //3 targets: p2 5 damage, p3 4 damage, p4 0 damage
        machinegun.applyEffect(weaponShot); // p2 6 damage, p3 6 damage,p4 1 damage
        Assert.assertEquals(6,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(6,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        //basic effect + extra 1 + extra
        weaponShot.setNameEffect("BasicEffect+Extra1+Extra2");
        machinegun.applyEffect(weaponShot); // p2 8 damage, p3 8 damage, p4 2 damage
        Assert.assertEquals(8,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(8,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        copy = new MachineGun(machinegun);
        Assert.assertEquals(copy.getType(),(machinegun.getType()));
        Assert.assertEquals(copy.getLoad(),(machinegun.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(machinegun.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(machinegun.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(machinegun.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(machinegun.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(machinegun.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(machinegun.getReloadCost().getBlues()));

    }

    @Test
    public void testTractorBeam(){
        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(tractorBeam,"Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        weaponShot.setNewPosition(5);
        p1.setPosition(4);
        p2.setPosition(5);
        tractorBeam.applyEffect(weaponShot); //p2 1 damage, pos 5
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        weaponShot.setNameEffect("Optional2");
        tractorBeam.applyEffect(weaponShot);  //p2 4 damage, p2 pos == p1 pos
        Assert.assertEquals(4,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(p1.getPosition(),p2.getPosition());

        copy = new TractorBeam(tractorBeam);
        Assert.assertEquals(copy.getType(),(tractorBeam.getType()));
        Assert.assertEquals(copy.getLoad(),(tractorBeam.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(tractorBeam.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(tractorBeam.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(tractorBeam.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(tractorBeam.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(tractorBeam.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(tractorBeam.getReloadCost().getBlues()));
    }

    @Test
    public void testThor(){
        Assert.assertTrue(thor.getLoad());
        enemyPlayers.add(p2);
        enemyPlayers.add(p3);
        weaponShot = new WeaponShot(thor,"BasicEffect+Extra1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        thor.applyEffect(weaponShot);
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        weaponShot.getTargetPlayer().add(p4);
        weaponShot.setNameEffect("BasicEffect+Extra1+Extra2");
        thor.applyEffect(weaponShot);
        Assert.assertEquals(4,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertFalse(thor.getLoad());

        copy = new Thor(thor);
        Assert.assertEquals(copy.getType(),(thor.getType()));
        Assert.assertEquals(copy.getLoad(),(thor.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(thor.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(thor.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(thor.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(thor.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(thor.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(thor.getReloadCost().getBlues()));
    }


    @Test
    public void testHellion(){
        enemyPlayers.add(p2);
        enemyPlayers.add(p3);
        enemyPlayers.add(p4);
        enemyPlayers.add(p5);
        weaponShot = new WeaponShot(hellion,"Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        hellion.applyEffect(weaponShot);  //p2 1 damage, p3,p4,p5 1 mark
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p5.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        weaponShot.setNameEffect("Optional2");
        hellion.applyEffect(weaponShot);
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(3,p3.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(3,p4.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(3,p5.getPlayerBoard().getEnemyMarks().get(0).getMarks());

        copy = new Hellion(hellion);
        Assert.assertEquals(copy.getType(),(hellion.getType()));
        Assert.assertEquals(copy.getLoad(),(hellion.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(hellion.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(hellion.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(hellion.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(hellion.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(hellion.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(hellion.getReloadCost().getBlues()));

    }

    @Test
    public void testVortexCannon(){
        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(vortexCannon,"BasicEffect");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        weaponShot.setNewPosition(5);
        vortexCannon.applyEffect(weaponShot); //p2 2 damage, pos 5
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(5,p2.getPosition());
        weaponShot.setNameEffect("BasicEffect+Extra1");
        enemyPlayers.add(p3);
        enemyPlayers.add(p4);
        weaponShot.setNewPosition(9);
        vortexCannon.applyEffect(weaponShot);  //p2 4 damage, p3 1 damage, p4 1 damage, pos = 9
        Assert.assertEquals(4,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(9,p2.getPosition());
        Assert.assertEquals(9,p3.getPosition());
        Assert.assertEquals(9,p4.getPosition());

        copy = new VortexCannon(vortexCannon);
        Assert.assertEquals(copy.getType(),(vortexCannon.getType()));
        Assert.assertEquals(copy.getLoad(),(vortexCannon.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(vortexCannon.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(vortexCannon.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(vortexCannon.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(vortexCannon.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(vortexCannon.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(vortexCannon.getReloadCost().getBlues()));
    }

    @Test
    public void testFurnace(){
        p1.setPosition(4);
        p2.setPosition(5);
        enemyPlayers.add(p2);
        p3.setPosition(p2.getPosition());
        p4.setPosition(p2.getPosition());
        p5.setPosition(p2.getPosition());
        weaponShot = new WeaponShot(furnace,"Optional1");
        weaponShot.setNameEffect("Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        furnace.applyEffect(weaponShot); //p2,p3,p4 1 damage
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        weaponShot.setNameEffect("Optional2");
        furnace.applyEffect(weaponShot);  //p2,p3,p4 2 damage 1 mark
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyMarks().get(0).getMarks());

        copy = new Furnace(furnace);
        Assert.assertEquals(copy.getType(),(furnace.getType()));
        Assert.assertEquals(copy.getLoad(),(furnace.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(furnace.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(furnace.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(furnace.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(furnace.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(furnace.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(furnace.getReloadCost().getBlues()));
    }

    @Test
    public void testPlasmaGun(){

        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(plasmaGun,"BasicEffect");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        plasmaGun.applyEffect(weaponShot);
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        weaponShot.setNameEffect("BasicEffect+Extra2");
        plasmaGun.applyEffect(weaponShot);
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());


        copy = new LockRifle(plasmaGun);
        Assert.assertEquals(copy.getType(),(plasmaGun.getType()));
        Assert.assertEquals(copy.getLoad(),(plasmaGun.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(plasmaGun.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(plasmaGun.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(plasmaGun.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(plasmaGun.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(plasmaGun.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(plasmaGun.getReloadCost().getBlues()));
    }

    @Test
    public void testHeatseeker(){
        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(heatseeker,"BasicEffect");
        weaponShot.setDamagingPlayer(p1);
        weaponShot.setTargetPlayers(enemyPlayers);
        heatseeker.applyEffect(weaponShot);  //3 damage
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        copy = new Heatseeker(heatseeker);
        Assert.assertEquals(copy.getType(),(heatseeker.getType()));
        Assert.assertEquals(copy.getLoad(),(heatseeker.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(heatseeker.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(heatseeker.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(heatseeker.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(heatseeker.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(heatseeker.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(heatseeker.getReloadCost().getBlues()));
    }

    @Test
    public void testWhisper(){
        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(whisper,"BasicEffect");
        weaponShot.setDamagingPlayer(p1);
        weaponShot.setTargetPlayers(enemyPlayers);
        whisper.applyEffect(weaponShot); // 3 damage 1 mark
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());

        copy = new Whisper(whisper);
        Assert.assertEquals(copy.getType(),(whisper.getType()));
        Assert.assertEquals(copy.getLoad(),(whisper.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(whisper.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(whisper.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(whisper.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(whisper.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(whisper.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(whisper.getReloadCost().getBlues()));
    }

    @Test
    public void testFlamethrower(){
        p2.setPosition(6);
        p3.setPosition(7);
        p4.setPosition(7);
        p1.setPosition(5);
        enemyPlayers.add(p2);

        weaponShot = new WeaponShot(flamethrower,"Optional1");
        weaponShot.setDamagingPlayer(p1);
        weaponShot.setTargetPlayers(enemyPlayers);
        flamethrower.applyEffect(weaponShot); //p2, p3 ,p4 1 damage
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setNameEffect("Optional2");
        flamethrower.applyEffect(weaponShot);  //p2 3 damage p3 2 damage p4 2 damage
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        copy = new Flamethrower(flamethrower);
        Assert.assertEquals(copy.getType(),(flamethrower.getType()));
        Assert.assertEquals(copy.getLoad(),(flamethrower.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(flamethrower.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(flamethrower.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(flamethrower.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(flamethrower.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(flamethrower.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(flamethrower.getReloadCost().getBlues()));
    }

    @Test
    public void testZX2(){
        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(zx2,"Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        zx2.applyEffect(weaponShot); //p2 1 damage 2 marks
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        weaponShot.setNameEffect("Optional2");
        enemyPlayers.add(p3);
        enemyPlayers.add(p4);
        weaponShot.setTargetPlayers(enemyPlayers);
        zx2.applyEffect(weaponShot); // p2 1 damage 3 marks, p3 1 mark, p4 1 mark
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyMarks().get(0).getMarks());

        copy = new ZX2(zx2);
        Assert.assertEquals(copy.getType(),(zx2.getType()));
        Assert.assertEquals(copy.getLoad(),(zx2.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(zx2.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(zx2.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(zx2.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(zx2.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(zx2.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(zx2.getReloadCost().getBlues()));

    }

    @Test
    public void testGrenadeLauncher(){

        enemyPlayers.add(p2);
        p2.setPosition(5);
        p3.setPosition(5);
        p4.setPosition(5);
        weaponShot = new WeaponShot(grenadeLauncher,"BasicEffect");
        weaponShot.setNewPosition(5);
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        grenadeLauncher.applyEffect(weaponShot); //p2 1 damage 2 marks
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        weaponShot.setNameEffect("BasicEffect+Extra1");
        enemyPlayers.add(p3);
        enemyPlayers.add(p4);
        weaponShot.setTargetPlayers(enemyPlayers);
        grenadeLauncher.applyEffect(weaponShot); // p2 1 damage 3 marks, p3 1 mark, p4 1 mark
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        weaponShot.setNameEffect("Extra1+BasicEffect");
        grenadeLauncher.applyEffect(weaponShot); // p2 1 damage 3 marks, p3 1 mark, p4 1 mark
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());


        copy = new GrenadeLauncher(grenadeLauncher);
        Assert.assertEquals(copy.getType(),(grenadeLauncher.getType()));
        Assert.assertEquals(copy.getLoad(),(grenadeLauncher.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(grenadeLauncher.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(grenadeLauncher.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(grenadeLauncher.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(grenadeLauncher.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(grenadeLauncher.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(grenadeLauncher.getReloadCost().getBlues()));
    }

    @Test
    public void testShotgun(){
        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(shotgun,"Optional1");
        weaponShot.setNameEffect("Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        weaponShot.setNewPosition(5);
        shotgun.applyEffect(weaponShot); //p2 3 damage
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(5,p2.getPosition());
        weaponShot.setNameEffect("Optional2");
        shotgun.applyEffect(weaponShot); //p2 2 damage
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        copy = new Shotgun(shotgun);
        Assert.assertEquals(copy.getType(),(shotgun.getType()));
        Assert.assertEquals(copy.getLoad(),(shotgun.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(shotgun.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(shotgun.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(shotgun.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(shotgun.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(shotgun.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(shotgun.getReloadCost().getBlues()));
    }

    @Test
    public void testRocketLauncher(){


        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(rocketLauncher,"BasicEffect");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        weaponShot.setNewPosition(5);
        rocketLauncher.applyEffect(weaponShot); //p2 3 damage
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(5,p2.getPosition());
        weaponShot.setNameEffect("BasicEffect+Extra2");
        enemyPlayers.add(p3);
        enemyPlayers.add(p4);
        p3.setPosition(5);
        p4.setPosition(5);
        rocketLauncher.applyEffect(weaponShot); //p2 2 damage
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());


        copy = new RocketLauncher(rocketLauncher);
        Assert.assertEquals(copy.getType(),(rocketLauncher.getType()));
        Assert.assertEquals(copy.getLoad(),(rocketLauncher.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(rocketLauncher.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(rocketLauncher.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(rocketLauncher.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(rocketLauncher.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(rocketLauncher.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(rocketLauncher.getReloadCost().getBlues()));
    }

    @Test
    public void testPowerGlove(){
        enemyPlayers.add(p2);
        p2.setPosition(5);
        p3.setPosition(6);
        p4.setPosition(7);
        weaponShot = new WeaponShot(powerGlove,"Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        powerGlove.applyEffect(weaponShot); //p2 1 damage, 2 marks
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(p1.getPosition(),p2.getPosition());
        weaponShot.setNameEffect("Optional2");
        enemyPlayers.clear();
        enemyPlayers.add(p3);
        weaponShot.setTargetPlayers(enemyPlayers);
        powerGlove.applyEffect(weaponShot);  // p3 3 damage ,p4 2 damage, pos. p1 == pos p4
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(p1.getPosition(),p4.getPosition());

        copy = new PowerGlove(powerGlove);
        Assert.assertEquals(copy.getType(),(powerGlove.getType()));
        Assert.assertEquals(copy.getLoad(),(powerGlove.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(powerGlove.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(powerGlove.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(powerGlove.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(powerGlove.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(powerGlove.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(powerGlove.getReloadCost().getBlues()));
    }

    @Test
    public void testRailgun(){
        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(railgun,"Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        railgun.applyEffect(weaponShot); //3 damages to p2
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        enemyPlayers.add(p3);
        weaponShot.setNameEffect("Optional2");
        weaponShot.setTargetPlayers(enemyPlayers);
        railgun.applyEffect(weaponShot);
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        copy = new Railgun(railgun);
        Assert.assertEquals(copy.getType(),(railgun.getType()));
        Assert.assertEquals(copy.getLoad(),(railgun.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(railgun.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(railgun.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(railgun.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(railgun.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(railgun.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(railgun.getReloadCost().getBlues()));

    }

    @Test
    public void testShockwave(){

        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(railgun,"Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        shockwave.applyEffect(weaponShot);
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        enemyPlayers.add(p3);
        weaponShot.setNameEffect("Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        shockwave.applyEffect(weaponShot);
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        enemyPlayers.add(p4);
        weaponShot.setTargetPlayers(enemyPlayers);
        shockwave.applyEffect(weaponShot);
        Assert.assertEquals(3,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(1,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        weaponShot.setNameEffect("Optional2");
        p1.setPosition(5);
        p2.setPosition(6);
        p3.setPosition(1);
        p4.setPosition(9);
        shockwave.applyEffect(weaponShot);
        Assert.assertEquals(4,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(3,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p4.getPlayerBoard().getEnemyDamages().get(0).getDamage());


        copy = new Shockwave(shockwave);
        Assert.assertEquals(copy.getType(),(shockwave.getType()));
        Assert.assertEquals(copy.getLoad(),(shockwave.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(shockwave.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(shockwave.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(shockwave.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(shockwave.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(shockwave.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(shockwave.getReloadCost().getBlues()));

    }

    @Test
    public void testCyberblade(){

        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(railgun,"BasicEffect");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        cyberblade.applyEffect(weaponShot);
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());

        enemyPlayers.add(p3);
        weaponShot.setNameEffect("BasicEffect+Extra2");
        weaponShot.setTargetPlayers(enemyPlayers);
        cyberblade.applyEffect(weaponShot);
        Assert.assertEquals(4,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());



        copy = new Cyberblade(cyberblade);
        Assert.assertEquals(copy.getType(),(cyberblade.getType()));
        Assert.assertEquals(copy.getLoad(),(cyberblade.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(cyberblade.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(cyberblade.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(cyberblade.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(cyberblade.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(cyberblade.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(cyberblade.getReloadCost().getBlues()));
    }

    @Test
    public void testSledgehammer(){
        enemyPlayers.add(p2);
        weaponShot = new WeaponShot(sledgehammer,"Optional1");
        weaponShot.setTargetPlayers(enemyPlayers);
        weaponShot.setDamagingPlayer(p1);
        sledgehammer.applyEffect(weaponShot); //2 damages
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        weaponShot.setNameEffect("Optional2");
        weaponShot.setNewPosition(5);
        sledgehammer.applyEffect(weaponShot);
        Assert.assertEquals(5,p2.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(5,p2.getPosition());

        copy = new Sledgehammer(sledgehammer);
        Assert.assertEquals(copy.getType(),(sledgehammer.getType()));
        Assert.assertEquals(copy.getLoad(),(sledgehammer.getLoad()));

        Assert.assertEquals(copy.getBuyingCost().getReds(),(sledgehammer.getBuyingCost()).getReds());
        Assert.assertEquals(copy.getBuyingCost().getYellows(),(sledgehammer.getBuyingCost().getYellows()));
        Assert.assertEquals(copy.getBuyingCost().getBlues(),(sledgehammer.getBuyingCost().getBlues()));

        Assert.assertEquals(copy.getReloadCost().getReds(),(sledgehammer.getReloadCost().getReds()));
        Assert.assertEquals(copy.getReloadCost().getYellows(),(sledgehammer.getReloadCost().getYellows()));
        Assert.assertEquals(copy.getReloadCost().getBlues(),(sledgehammer.getReloadCost().getBlues()));

    }
}
