package it.polimi.se2019;

import it.polimi.se2019.client.Bootstrapper;
import it.polimi.se2019.server.OneAboveAll;

import java.io.BufferedReader;


public class App {
    public static void main( String[] args ) {

        BufferedReader console = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        System.out.println("Wanna do 1) SERVER or 2) CLIENT?");
        String choice = "";
        try {
            choice = console.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (choice) {
            case "1":
                System.out.println("How much would you like a turn to last?");
                Integer stamina = 0;
                do {
                    try {
                        System.out.println("A turn should last for 0 to 300 sec.");
                        stamina = Integer.parseInt(console.readLine());
                        System.out.println(stamina);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (stamina.intValue() <= 0 || stamina.intValue() > 300);
                OneAboveAll.main(new String[]{stamina.toString()});
                break;
            case "2":
                Bootstrapper.main(new String[]{});
                break;
            default:
                System.out.println("ERROR!");

        }
    }
}