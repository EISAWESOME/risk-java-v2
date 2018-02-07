package com.ort.risk.console;

import com.ort.risk.game.Launcher;
import com.ort.risk.model.Map;
import com.ort.risk.model.Player;

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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n==========================================================");
        System.out.println("\t\tDEBUT DU TOUR DE " + currentPlayer.getName());
        System.out.println("==========================================================\n\n");


        //The player pick the actions he wish to do this turn
        int chosenAction = 0;
        System.out.println("Que faire ce tour ? ");
        System.out.println("\t[0] Deploiement");
        System.out.println("\t[1] Guerre");
        System.out.println("\t[2] Renforcement");
        try {
            do {
                chosenAction = Integer.parseInt(br.readLine());
            } while ( (chosenAction < 0 || chosenAction > 2)
                    || (currentPlayer.getReinforcementRegions().size() == 0 && chosenAction == 2)
                    || (currentPlayer.getWarRegions().size() == 0 && chosenAction == 1));
        } catch (Exception ex) {

        }

        switch(chosenAction){
            case 0:
                Deployment.execute(currentPlayer);
                break;
            case 1:
                War.execute(currentPlayer);
                break;
            case 2:
                Reinforcement.execute(currentPlayer);
                break;
        }

        //currentPlayer.executeDeployment();


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
