package com.ort.risk.game;

import com.ort.risk.model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author CS
 * Game sourcecode. Contaien the two phases : Initialisation, and turn loops
 */
public class Play {

    enum ExecMode {
        CONSOLE(0),
        RANDOM(1),
        GUI(2);

        private final int value;

        ExecMode(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

    }

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
                .filter(p -> p.getControlledRegions().size() >= 1).collect(Collectors.toList());

        //While there is still 2 valid player in the game
        while (validPlayerList.size() >= 2) {

            // For each player

            validPlayerList.forEach((player) -> {
                //Player take turn
                Turn.TakeTurn(player);


            });

            //Update the valid player list after every turn
            validPlayerList = playerList.stream()
                    .filter(p -> p.getControlledRegions().size() >= 1).collect(Collectors.toList());
        }
    }

    public static void InitDeployment(int exMode) {
        Map mapObj = Map.getInstance();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(
                "__________.___  _____________  __.\n" +
                        "\\______   \\   |/   _____/    |/ _|\n" +
                        " |       _/   |\\_____  \\|      <  \n" +
                        " |    |   \\   |/        \\    |  \\ \n" +
                        " |____|_  /___/_______  /____|__ \\\n" +
                        "        \\/            \\/        \\/");

        List<Mode> allModes = mapObj.getModes();
        int selectedModeIndex = 0;

        //Random mode
        if (exMode == ExecMode.RANDOM.value()) {
            //selectedModeIndex = (int)(Math.random() * allModes.size());
            selectedModeIndex = 3;
        }

        //Console mode
        if (exMode == ExecMode.CONSOLE.value()) {
            System.out.println("Modes disponibles : ");
            for (int ci = 0; ci < allModes.size(); ci++) {
                Mode curMode = allModes.get(ci);
                System.out.println("[" + ci + "]");
                System.out.println("\t Nb joueur : " + curMode.getNbPlayer());
                System.out.println("\t Nb troupes initial : " + curMode.getNbInitTroops());

            }
            try {
                do {
                    System.out.println("Choix du mode ? ");
                    selectedModeIndex = Integer.parseInt(br.readLine());
                } while (selectedModeIndex > allModes.size() || selectedModeIndex < 0);


            } catch (Exception ex) {

            }
        }

        if (exMode == ExecMode.GUI.value) {

            /* TODO : Input utilisateur via GUI*/
            selectedModeIndex = (int) (Math.random() * allModes.size());
        }


        allModes.get(selectedModeIndex).setIsSelected(true);

        System.out.println("Mode selectionné : ");
        System.out.println("\tNb player : " + allModes.get(selectedModeIndex).getNbPlayer());
        System.out.println("\tNb Troupes initial : " + allModes.get(selectedModeIndex).getNbInitTroops());
        System.out.println("\n");

        System.out.println("Liste des joueurs : ");
        int nbTroupePerPlayer = allModes.get(selectedModeIndex).getNbInitTroops();
        for (int n = 1; n <= allModes.get(selectedModeIndex).getNbPlayer(); n++) {

            // Add players to the map
            mapObj.addPlayer(new Player("Player" + n, n, nbTroupePerPlayer));

            System.out.println("\tNom : " + "Player" + n);
            System.out.println("\tOrdre de passage : " + n);
        }

        if (exMode == ExecMode.CONSOLE.value()) {
            System.out.println("\n\n==================================================================================================================");
            System.out.println("\t\t Deploiement initial, les joueurs choissisent une région non occupée chacun leur tour !");
            System.out.println("==================================================================================================================\n\n");
        }

        //Regions' attribution
        //Filter to get only the not occupied regions
        List<Region> notOccupiedRegions = mapObj.getRegions().stream()
                .filter(p -> !p.getIsOccupied()).collect(Collectors.toList());

        while (notOccupiedRegions.size() > allModes.get(0).getNbPlayer()) {
            for (int p = 0; p < mapObj.getPlayerList().size(); p++) {
                int selectedRegionIndex = 0;
                //Random mode
                if (exMode == ExecMode.RANDOM.value()) {
                    selectedRegionIndex = (int) (Math.random() * notOccupiedRegions.size());
                }

                //Console mode
                if (exMode == ExecMode.CONSOLE.value()) {
                    System.out.println("\tCHOIX DU JOUEUR " + mapObj.getPlayerList().get(p).getName());
                    for (int nor = 0; nor < notOccupiedRegions.size(); nor++) {
                        System.out.println("[" + nor + "]" + notOccupiedRegions.get(nor).getName());
                    }
                    try {
                        do {
                            System.out.println("Choix de la region ? ");
                            selectedRegionIndex = Integer.parseInt(br.readLine());
                        } while (selectedRegionIndex > notOccupiedRegions.size() || selectedRegionIndex < 0);


                    } catch (Exception ex) {

                    }
                }

                if (exMode == ExecMode.GUI.value()) {
                    /* TODO : Input GUI */
                    selectedRegionIndex = (int) (Math.random() * notOccupiedRegions.size());
                }
                //Both region and zone list references the same region objects
                //Hence, change flags in the region list would change them in the zone list too
                notOccupiedRegions.get(selectedRegionIndex).setDeployedTroops(1);
                mapObj.getPlayerList().get(p).changeNbTroupes(-1);

                mapObj.getPlayerList().get(p).addControlledRegion(notOccupiedRegions.get(selectedRegionIndex));
                notOccupiedRegions.get(selectedRegionIndex).setIsOccupied(true);

                notOccupiedRegions = notOccupiedRegions.stream()
                        .filter(po -> !po.getIsOccupied()).collect(Collectors.toList());
            }
        }

        System.out.println("\n\n==========================================================");
        System.out.println("\t\t\tDEBUT DE LA PARTIE");
        System.out.println("==========================================================");


        //Initial deployment
        /* TODO  : A CHANGER - Le déploiement se fait de façon random, en attendant l'interface graphique */
        for (int q = 0; q < mapObj.getPlayerList().size(); q++) {
            Player currentPlayer = mapObj.getPlayerList().get(q);
            while (currentPlayer.getNbTroops() > 0) {
                int rand = (int) (Math.random() * currentPlayer.getControlledRegions().size());
                currentPlayer.getControlledRegions().get(rand).changeDeployedTroops(1);
                currentPlayer.changeNbTroupes(-1);
            }
        }

    }

    public static void printPlayerRegions() {
        Map mapObj = Map.getInstance();
        for (int pq = 0; pq < mapObj.getPlayerList().size(); pq++) {
            System.out.println(mapObj.getPlayerList().get(pq).getName());
            List<Region> playerRegion = mapObj.getPlayerList().get(pq).getControlledRegions();
            for (int pr = 0; pr < playerRegion.size(); pr++) {
                System.out.println("\t" + playerRegion.get(pr).getName() + " : " + playerRegion.get(pr).getDeployedTroops());

            }
            System.out.println("\n");
        }
    }

}
