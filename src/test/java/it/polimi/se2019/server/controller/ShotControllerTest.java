package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.WeaponSlot;
import it.polimi.se2019.server.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ShotControllerTest {

    private Match match;
    private ShotController shotController;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player p5;

    @Before
    public void setClass() {
        match = new Match(1);
        match.initializeMatch();
        p1 = new Player("Marco", match);
        p2 = new Player("Mattia", match);
        p3 = new Player("Matteo", match);
        p4 = new Player("Ferra", match);
        p5 = new Player("Bruno", match);
        match.getAllPlayers().add(p1);
        match.getAllPlayers().add(p2);
        match.getAllPlayers().add(p3);
        match.getAllPlayers().add(p4);
        match.getAllPlayers().add(p5);
        shotController = new ShotController(match);
    }

    @Test
    public void checkAllTest() {
        p1.setPosition(5);
        p2.setPosition(6);
        p3.setPosition(0);
        p4.setPosition(5);
        p5.setPosition(5);


        for (Weapon weapon : match.getWeaponStack()) {
            if (weapon.getType().equals("lock_rifle")) {
                p1.getHand().getWeapons().add(weapon);
            }

        }
        if (p1.getHand().getWeapons().isEmpty()) {
            for (WeaponSlot weaponSlot : this.match.getArsenal()) {
                for (int i = 0; i < weaponSlot.getSlot().length; i++) {
                    if (weaponSlot.getSlot()[i].getType().equals("lock_rifle")) {
                        p1.getHand().getWeapons().add(weaponSlot.getSlot()[i]);
                    }
                }
            }
        }


        Assert.assertEquals("lock_rifle", shotController.checkAll(p1).get(0).getType());

        p1.getHand().getWeapons().clear();

        for (Weapon weapon : match.getWeaponStack()) {
            if (weapon.getType().equals("heatseeker")) {
                p1.getHand().getWeapons().add(weapon);
            }

        }
        if (p1.getHand().getWeapons().isEmpty()) {
            for (WeaponSlot weaponSlot : this.match.getArsenal()) {
                for (int i = 0; i < weaponSlot.getSlot().length; i++) {
                    if (weaponSlot.getSlot()[i].getType().equals("heatseeker")) {
                        p1.getHand().getWeapons().add(weaponSlot.getSlot()[i]);
                    }
                }
            }
        }

        Assert.assertEquals("heatseeker", shotController.checkAll(p1).get(0).getType());

        p1.getHand().getWeapons().clear();

        for (Weapon weapon : match.getWeaponStack()) {
            if (weapon.getType().equals("whisper")) {
                p1.getHand().getWeapons().add(weapon);
            }

        }
        if (p1.getHand().getWeapons().isEmpty()) {
            for (WeaponSlot weaponSlot : this.match.getArsenal()) {
                for (int i = 0; i < weaponSlot.getSlot().length; i++) {
                    if (weaponSlot.getSlot()[i].getType().equals("whisper")) {
                        p1.getHand().getWeapons().add(weaponSlot.getSlot()[i]);
                    }
                }
            }
        }

        p2.setPosition(2);

        Assert.assertEquals("whisper", shotController.checkAll(p1).get(0).getType());

        p1.getHand().getWeapons().clear();

        for (Weapon weapon : match.getWeaponStack()) {
            if (weapon.getType().equals("furnace")) {
                p1.getHand().getWeapons().add(weapon);
            }

        }
        if (p1.getHand().getWeapons().isEmpty()) {
            for (WeaponSlot weaponSlot : this.match.getArsenal()) {
                for (int i = 0; i < weaponSlot.getSlot().length; i++) {
                    if (weaponSlot.getSlot()[i].getType().equals("furnace")) {
                        p1.getHand().getWeapons().add(weaponSlot.getSlot()[i]);
                    }
                }
            }
        }

        Assert.assertEquals("furnace", shotController.checkAll(p1).get(0).getType());

        p1.getHand().getWeapons().clear();

        for (Weapon weapon : match.getWeaponStack()) {
            if (weapon.getType().equals("railgun")) {
                p1.getHand().getWeapons().add(weapon);
            }

        }
        if (p1.getHand().getWeapons().isEmpty()) {
            for (WeaponSlot weaponSlot : this.match.getArsenal()) {
                for (int i = 0; i < weaponSlot.getSlot().length; i++) {
                    if (weaponSlot.getSlot()[i].getType().equals("railgun")) {
                        p1.getHand().getWeapons().add(weaponSlot.getSlot()[i]);
                    }
                }
            }
        }

        Assert.assertEquals("railgun", shotController.checkAll(p1).get(0).getType());

        p1.setPosition(0);
        p2.setPosition(5);
        p3.setPosition(5);
        p4.setPosition(5);
        p5.setPosition(5);

        Assert.assertEquals(true, shotController.checkAll(p1).isEmpty());

        p1.getHand().getWeapons().clear();

        for (Weapon weapon : match.getWeaponStack()) {
            if (weapon.getType().equals("tractor_beam")) {
                p1.getHand().getWeapons().add(weapon);
            }

        }
        if (p1.getHand().getWeapons().isEmpty()) {
            for (WeaponSlot weaponSlot : this.match.getArsenal()) {
                for (int i = 0; i < weaponSlot.getSlot().length; i++) {
                    if (weaponSlot.getSlot()[i].getType().equals("tractor_beam")) {
                        p1.getHand().getWeapons().add(weaponSlot.getSlot()[i]);
                    }
                }
            }
        }

        Assert.assertEquals("tractor_beam", shotController.checkAll(p1).get(0).getType());

        p2.setPosition(11);
        p3.setPosition(11);
        p4.setPosition(11);
        p5.setPosition(11);

        Assert.assertEquals(true, shotController.checkAll(p1).isEmpty());

        p2.setPosition(1);
        p3.setPosition(2);
        p4.setPosition(2);

        p1.getHand().getWeapons().clear();

        for (Weapon weapon : match.getWeaponStack()) {
            if (weapon.getType().equals("railgun")) {
                p1.getHand().getWeapons().add(weapon);
            }

        }
        if (p1.getHand().getWeapons().isEmpty()) {
            for (WeaponSlot weaponSlot : this.match.getArsenal()) {
                for (int i = 0; i < weaponSlot.getSlot().length; i++) {
                    if (weaponSlot.getSlot()[i].getType().equals("railgun")) {
                        p1.getHand().getWeapons().add(weaponSlot.getSlot()[i]);
                    }
                }
            }
        }

        Assert.assertEquals("railgun", shotController.checkAll(p1).get(0).getType());

        p1.getHand().getWeapons().clear();

        for (Weapon weapon : match.getWeaponStack()) {
            if (weapon.getType().equals("thor")) {
                p1.getHand().getWeapons().add(weapon);
            }

        }
        if (p1.getHand().getWeapons().isEmpty()) {
            for (WeaponSlot weaponSlot : this.match.getArsenal()) {
                for (int i = 0; i < weaponSlot.getSlot().length; i++) {
                    if (weaponSlot.getSlot()[i].getType().equals("thor")) {
                        p1.getHand().getWeapons().add(weaponSlot.getSlot()[i]);
                    }
                }
            }
        }

        Assert.assertEquals("thor", shotController.checkAll(p1).get(0).getType());

        p2.setPosition(11);
        p3.setPosition(11);
        p4.setPosition(11);
        p5.setPosition(11);

        Assert.assertEquals(true, shotController.checkAll(p1).isEmpty());

        p1.getHand().getWeapons().clear();

        p2.setPosition(11);
        p3.setPosition(2);
        p4.setPosition(6);
        p5.setPosition(8);

        for (Weapon weapon : match.getWeaponStack()) {
            if (weapon.getType().equals("vortex_cannon")) {
                p1.getHand().getWeapons().add(weapon);
            }

        }
        if (p1.getHand().getWeapons().isEmpty()) {
            for (WeaponSlot weaponSlot : this.match.getArsenal()) {
                for (int i = 0; i < weaponSlot.getSlot().length; i++) {
                    if (weaponSlot.getSlot()[i].getType().equals("vortex_cannon")) {
                        p1.getHand().getWeapons().add(weaponSlot.getSlot()[i]);
                    }
                }
            }
        }

        Assert.assertEquals(false, shotController.checkAll(p1).isEmpty());



    }


}
