package com.ort.risk.game;

import com.ort.risk.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import java.io.File;
import java.io.IOException;



public class play {

    public static void GameLoop() {
        Map mapObj = Map.getInstance();
        List<Player> playerList = mapObj.getPlayerList();

        //Sort the players by turn order
        playerList.sort(Comparator.comparing(Player::getOrder));

        /*
        playerList.forEach((player) -> {
            System.out.println(player.getOrder());

        });
        */

        //Filter to keep only the players that have at least 1 controlled region
        List<Player> validPlayerList = playerList.stream()
                .filter(p -> p.getControlledRegions().size() >= 1 ).collect(Collectors.toList());

        //While there is still 2 valid player in the game
        while(validPlayerList.size() >= 2){

            // For each player

            validPlayerList.forEach((player) -> {
                //Player take turn
                turn.TakeTurn(player);


            });

            //Update the valid player list after every turn
            validPlayerList = playerList.stream()
                    .filter(p -> p.getControlledRegions().size() >= 1 ).collect(Collectors.toList());



        }
    }

    public static void InitDeployment(){
        Map mapObj = Map.getInstance();

        System.out.println(
                        "__________.___  _____________  __.\n" +
                        "\\______   \\   |/   _____/    |/ _|\n" +
                        " |       _/   |\\_____  \\|      <  \n" +
                        " |    |   \\   |/        \\    |  \\ \n" +
                        " |____|_  /___/_______  /____|__ \\\n" +
                        "        \\/            \\/        \\/");

        List<Mode> allModes = mapObj.getModes();
        allModes.get(0).setIsSelected(true);

        System.out.println("Mode selectionné : ");
        System.out.println("\tNb player : " + allModes.get(0).getNbPlayer());
        System.out.println("\tNb Troupes initial : " + allModes.get(0).getNbInitTroops());
        System.out.println("\n");

        System.out.println("Liste des joueurs : ");
        int nbTroupePerPlayer = allModes.get(0).getNbInitTroops();
        for(int n = 1; n <= allModes.get(0).getNbPlayer(); n++){

            // Add players to the map
            mapObj.addPlayer(new Player("Player"+ n, n, nbTroupePerPlayer));

            System.out.println("\tNom : " + "Player"+ n);
            System.out.println("\tOrdre de passage : " + n);
        }

        //Regions' attribution
        //Filter to get only the not occupied regions
        List<Region> notOccupiedRegions = mapObj.getRegions().stream()
                .filter(p -> !p.getIsOccupied()).collect(Collectors.toList());

        while(notOccupiedRegions.size() >= allModes.get(0).getNbPlayer()) {
            for(int p = 0; p < mapObj.getPlayerList().size(); p++){
                /* TODO  : A CHANGER - Le déploiement se fait de façon random, en attendant l'interface graphique */
                int rand =  (int)(Math.random() * notOccupiedRegions.size());

                //Both region and zone list references the same region objects
                //Hence, change flags in the region list would change them in the zone list too
                notOccupiedRegions.get(rand).setDeployedTroops(1);
                mapObj.getPlayerList().get(p).changeNbTroupes(-1);

                mapObj.getPlayerList().get(p).addControlledRegion(notOccupiedRegions.get(rand));
                notOccupiedRegions.get(rand).setIsOccupied(true);

                notOccupiedRegions = notOccupiedRegions.stream()
                        .filter(po -> !po.getIsOccupied() ).collect(Collectors.toList());
            }
        }

        //Initial deployment
        /* TODO  : A CHANGER - Le déploiement se fait de façon random, en attendant l'interface graphique */
        for(int q = 0; q < mapObj.getPlayerList().size(); q++){
            Player currentPlayer = mapObj.getPlayerList().get(q);
            while(currentPlayer.getNbTroops() > 0){
                int rand =  (int)(Math.random() * currentPlayer.getControlledRegions().size());
                currentPlayer.getControlledRegions().get(rand).changeDeployedTroops(1);
                currentPlayer.changeNbTroupes(-1);
            }
        }

    }

    public static void printPlayerRegions(){
        Map mapObj = Map.getInstance();
        for(int pq = 0; pq < mapObj.getPlayerList().size(); pq++){
            System.out.println(mapObj.getPlayerList().get(pq).getName());
            List<Region> playerRegion = mapObj.getPlayerList().get(pq).getControlledRegions();
            for(int pr =0; pr < playerRegion.size(); pr++){
                System.out.println("\t" + playerRegion.get(pr).getName()  + " : " + playerRegion.get(pr).getDeployedTroops() );

            }
            System.out.println("\n");
        }
    }
}
