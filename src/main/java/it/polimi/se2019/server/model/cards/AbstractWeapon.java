package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public class AbstractWeapon implements Weapon {

    protected String type;
    protected boolean load;
    protected Cubes buyingCost;
    protected Cubes reloadCost;
    protected Shot[] effect;

    public void setLoad(boolean load) {
        this.load = load;
    }

    public boolean getLoad(){
        return this.load;
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

    public ArrayList<Player> getTargets(){
        return new ArrayList<>();
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
}
