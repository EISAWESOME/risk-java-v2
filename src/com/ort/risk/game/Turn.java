package com.ort.risk.game;

import com.ort.risk.model.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author CS
 * Player turn
 */
public class Turn {
    public static void TakeTurn(Player currentPlayer){
        Map mapObj = Map.getInstance();
        int exMode = mapObj.getExMode();

        System.out.println("\n\n==========================================================");
        System.out.println("\t\t DEBUT DU TOUR DE " + currentPlayer.getName());
        System.out.println("==========================================================\n\n");

        //The player pick the actions he wish to do this turn

        /* TODO : GUI pick l'actions parmis la liste*/

        currentPlayer.executeDeployment();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n==========================================================");
        System.out.println("\t\tFIN DU TOUR DE " + currentPlayer.getName());
        System.out.println("==========================================================\n\n");

        //Print end of turn info only in console mode
        if(exMode == Launcher.ExecMode.CONSOLE.value()){
            System.out.println("Entrez 'e' pour voir les regions controllées par les joueurs ");
            try {
                String s = br.readLine();
                if(s.equals("e")){
                    System.out.println("\n");
                    System.out.println("===========================================");
                    System.out.println("===========================================");
                    System.out.println("\n");
                    Play.printPlayerRegions();
                    System.out.println("\n");
                    System.out.println("===========================================");
                    System.out.println("===========================================");
                    System.out.println("\n");

                    System.out.println("--Appuyez sur une touche pour revenir au jeu--");
                    br.readLine();
                }
            } catch (Exception ex) {

            }
        }

    }
}
