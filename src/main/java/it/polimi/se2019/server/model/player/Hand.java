package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.cards.powerUp.PowerUp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Hand implements Serializable {

    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    /*
      discard one card from the player hand,
      the card is lost if it's a weapon card,
      otherwise it's added on the corresponding
      discarded list
    */
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void discardWeapon(){

    }

    public void discardPowerUp() {
        int index = this.indexToDiscard();
        this.chooseToDiscard(index);
    }

    public void addPowerUp(PowerUp currentPowerUp) {
        if (checkPowerUps()) {
            this.powerUps.add( currentPowerUp);
        }
        else{
            this.powerUps.add( currentPowerUp);
            System.out.println("You already have three powerUp cards.");
            System.out.println("Choose one to discard!");
            int index = indexToDiscard();
            this.chooseToDiscard(index);


        }
    }

    //Check how many powerUp the player has in his hand.
    //Return true if the player can draw a powerUp, false otherwise.
    public boolean checkPowerUps() {
        if(powerUps.size() <= 3) {
            return true;
        }
        else{
            return false;
        }
    }

    //TODO da mettere nel controller
    public int indexToDiscard() {
        Scanner input = new Scanner(System.in);
        int index = 1;
        //int index = Integer.parseInt(input.nextLine());
        return index;
    }

    public PowerUp chooseToDiscard(int indexToDiscard) {
        int index = 1;
        //Print the list of all powerups the player own
        for(PowerUp object : powerUps) {
            System.out.println(index + ". " + object.getType() + " - " + object.getColor());
            index++;
        }

        if(indexToDiscard >=4) {
            System.out.println("You have typed an illegal number");
            return null;
        }
        else {
            //remove from the ArrayList the powerUp chosen
            return powerUps.remove(indexToDiscard);

        }
    }
}