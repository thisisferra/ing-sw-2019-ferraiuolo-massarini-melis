package it.polimi.se2019.server.model.cards.weapons;

import com.google.gson.Gson;
import it.polimi.se2019.server.model.cards.*;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.controller.WeaponShot;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractWeapon implements Weapon, Serializable {

    protected String type;
    protected boolean load;
    protected Cubes buyingCost;
    protected Cubes reloadCost;
    protected Shot[] effect;
    protected int maxTarget;
    protected int maxMovementTarget;
    protected int maxMovementPlayer;
    protected ArrayList<WeaponShot> weaponShots = new ArrayList<>();

    public void setLoad(boolean load) {
        this.load = load;
    }

    public boolean getLoad(){
        return this.load;
    }

    public ArrayList<WeaponShot> getWeaponShots(){
        return this.weaponShots;
    }

    public Cubes getReloadCost() {
        return reloadCost;
    }

    public Cubes getBuyingCost() {
        return buyingCost;
    }

    public String getType() {
        return type;
    }

    public String toString(){
        return this.type;
    }

    public Shot[] getEffect(){
        return this.effect;
    }

    public void applyEffect(){

    }


    // factory method for weapons: based on the weapon sub-type it returns a copy.
    // static type : Weapon , dynamic type : weapon sub-type
    public Weapon weaponFactory(Weapon weapon){
    Weapon copiedWeapon;

        switch(weapon.getType()){

            case "lock_rifle" : {

                copiedWeapon = new LockRifle(weapon);
                break;
            }
            case "cyberblade" : {

                copiedWeapon = new Cyberblade(weapon);
                break;
            }
            case "electroscythe" : {

                copiedWeapon = new Electroscythe(weapon);
                break;
            }
            case "flamethrower" : {

                copiedWeapon = new Flamethrower(weapon);
                break;
            }
            case "furnace" : {

                copiedWeapon = new Furnace(weapon);
                break;
            }
            case "grenade_launcher" : {

                copiedWeapon = new GrenadeLauncher(weapon);
                break;
            }
            case "power_glove" : {

                copiedWeapon = new PowerGlove(weapon);
                break;
            }
            case "heatseeker" : {

                copiedWeapon = new Heatseeker(weapon);
                break;
            }
            case "hellion" : {

                copiedWeapon = new Hellion(weapon);
                break;
            }
            case "machine_gun" : {

                copiedWeapon = new MachineGun(weapon);
                break;
            }
            case "plasma_gun" : {

                copiedWeapon = new PlasmaGun(weapon);
                break;
            }
            case "railgun" : {

                copiedWeapon = new Railgun(weapon);
                break;
            }
            case "rocket_launcher" : {

                copiedWeapon = new RocketLauncher(weapon);
                break;
            }
            case "shockwave" : {

                copiedWeapon = new Shockwave(weapon);
                break;
            }
            case "shotgun" : {

                copiedWeapon = new Shotgun(weapon);
                break;
            }
            case "sledgehammer" : {

                copiedWeapon = new Sledgehammer(weapon);
                break;
            }
            case "thor" : {

                copiedWeapon = new Thor(weapon);
                break;
            }
            case "tractor_beam" : {

                copiedWeapon = new TractorBeam(weapon);
                break;
            }
            case "vortex_cannon" : {

                copiedWeapon = new VortexCannon(weapon);
                break;
            }
            case "whisper" : {

                copiedWeapon = new Whisper(weapon);
                break;
            }
            case "zx-2" : {

                copiedWeapon = new ZX2(weapon);
                break;
            }
            default : copiedWeapon = null;
        }
        return copiedWeapon;
    }

    public static Weapon resumeWeapon(String weaponToResume) {
        Weapon resumedWeapon = null;
        Logger logger = Logger.getAnonymousLogger();
        Gson gson = new Gson();

        try {

            switch (weaponToResume) {
                case "plasma_gun":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/plasma_gun.json")), PlasmaGun.class);
                    break;
                case "thor":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/thor.json")), Thor.class);
                    break;
                case "electroscythe":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/electroscythe.json")), Electroscythe.class);
                    break;
                case "lock_rifle":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/lock_rifle.json")), LockRifle.class);
                    break;
                case "whisper":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/whisper.json")), Whisper.class);
                    break;
                case "zx-2":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/zx-2.json")), ZX2.class);
                    break;
                case "shockwave":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/shockwave.json")), Shockwave.class);
                    break;
                case "machine_gun":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/machine_gun.json")), MachineGun.class);
                    break;
                case "flamethrower":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/flamethrower.json")), Flamethrower.class);
                    break;
                case "furnace":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/furnace.json")), Furnace.class);
                    break;
                case "hellion":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/hellion.json")), Hellion.class);
                    break;
                case "shotgun":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/shotgun.json")), Shotgun.class);
                    break;
                case "power_glove":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/power_glove.json")), PowerGlove.class);
                    break;
                case "heatseeker":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/heatseeker.json")), Heatseeker.class);
                    break;
                case "vortex_cannon":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/vortex_cannon.json")), VortexCannon.class);
                    break;
                case "tractor_beam":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/tractor_beam.json")), TractorBeam.class);
                    break;
                case "railgun":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/railgun.json")), Railgun.class);
                    break;
                case "sledgehammer":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/sledgehammer.json")), Sledgehammer.class);
                    break;
                case "cyberblade":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/cyberblade.json")), Cyberblade.class);
                    break;
                case "rocket_launcher":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/rocket_launcher.json")), RocketLauncher.class);
                    break;
                case "grenade_launcher":
                    resumedWeapon = gson.fromJson(new InputStreamReader(Object.class.getResourceAsStream("/grenade_launcher.json")), GrenadeLauncher.class);
                    break;
            }

        } catch (Exception e) {
            logger.log(Level.INFO,"Resuming weapons error",e);
        }

        return resumedWeapon;
    }
}
