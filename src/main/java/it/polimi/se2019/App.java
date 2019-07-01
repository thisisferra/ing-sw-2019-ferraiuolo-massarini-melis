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
                OneAboveAll.main(new String[]{});
                break;
            case "2":
                Bootstrapper.main(new String[]{});
                break;
            default:
                System.out.println("ERROR!");

        }
    }
}