package com.ort.risk.game;
import com.ort.risk.model.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class turn {
    public static void TakeTurn(Player currentPlayer){
        Map mapObj = Map.getInstance();
        currentPlayer.executeDeployment();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Fin du tour de " + currentPlayer.getName());
        System.out.println("Entrez 'e' pour voir les regions controllées par les joueurs ");
        System.out.println("Et le nombre de troupes deployés");
        try{
            String s = br.readLine();
            if(s.equals("e")){
                System.out.println("\n");
                System.out.println("===========================================");
                System.out.println("===========================================");
                System.out.println("\n");
                play.printPlayerRegions();
                System.out.println("\n");
                System.out.println("===========================================");
                System.out.println("===========================================");
                System.out.println("\n");

                System.out.println("--Appuyez sur une touche pour revenir au jeu--");
                br.readLine();
            }
        } catch(Exception ex){

        }





        //The player pick the actions he wish to do this turn

        /* TODO : GUI pick l'actions parmis la liste*/

    }
}
