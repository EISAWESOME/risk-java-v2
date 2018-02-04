package com.ort.risk.game;
import com.ort.risk.model.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class turn {
    public static void TakeTurn(Player currentPlayer){
        Map mapObj = Map.getInstance();

        /* TODO : Choix d'une region possédé -> GUI */
        //Chosing a random controlled region

        int rand =  (int)(Math.random() * currentPlayer.getControlledRegions().size());
        Region t = currentPlayer.getControlledRegions().get(rand);

        currentPlayer.executeDeployment(t);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Fin du tour de " + currentPlayer.getName());
        try{
            String s = br.readLine();
        } catch(Exception ex){

        }



        //The player pick the actions he wish to do this turn

        /* TODO : GUI pick l'actions parmis la liste*/

    }
}
