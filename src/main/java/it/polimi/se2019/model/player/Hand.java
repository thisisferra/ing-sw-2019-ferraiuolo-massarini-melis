package it.polimi.se2019.model.player;

import it.polimi.se2019.model.cards.Weapon;
import it.polimi.se2019.model.cards.PowerUp;

import java.util.ArrayList;
import java.util.Scanner;

public class Hand {
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    /*
      discard one card from the player hand,
      the card is lost if it's a weapon card,
      otherwise it's added on the corresponding
      discarded list
    */

    public void discardWeapon(){

    }
    public void discardPower(){

    }

    public void addPowerUp(PowerUp currentPowerUp) {
        if (checkPowerUps()) {
            this.powerUps.add(powerUps.size() - 1, currentPowerUp);
        }
        else{
            this.powerUps.add(powerUps.size() - 1, currentPowerUp);
            System.out.println("You already have three PowerUp cards.");
            System.out.println("Choose one to discard!");
            this.chooseToDiscard();


        }
    }

    //Check how many PowerUp the player has in his hand.
    //Return true if the player can draw a PowerUp, false otherwise.
    public boolean checkPowerUps() {
        if(powerUps.size() <= 3) {
            return true;
        }
        else{
            return false;
        }
    }

    public void chooseToDiscard() {
        int index = 1;
        Scanner input = new Scanner(System.in);
        System.out.println("Choose one powerUp to discard (insert an index):");
        //Print the list of all powerups the player own
        for(PowerUp object : powerUps) {
            System.out.println(index + ". " + object.getType());
            index++;
        }

        int powerUpChoosed = Integer.parseInt(input.nextLine());
        if(powerUpChoosed >=4) {
            System.out.println("You have typed an illegal number");
        }
        else {
            //remove from the ArrayList the PowerUp chosen
            powerUps.remove(powerUpChoosed -  1);
        }
    }
}
