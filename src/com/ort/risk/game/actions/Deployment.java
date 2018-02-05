package com.ort.risk.game.actions;

import com.ort.risk.model.*;
import com.ort.risk.game.Launcher.ExecMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author CS
 * Deployment action : one of the 3 action a player can do in its turn
 */

public class Deployment {

    public static void execute(Player player) {
        Map mapObj = Map.getInstance();
        int exMode = mapObj.getExMode();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int min = mapObj.getNbMinReinforcement();
        int bonusSum = calcBonusSum(player);
        int nbDeployMax = Math.max(min, bonusSum);
        List<Region> playerRegions = player.getControlledRegions();
        //System.out.println(nbRenfort);

        while (nbDeployMax > 0) {

            int selectedRegionIndex = 0;
            Region target = playerRegions.get(selectedRegionIndex);


            //Chosing a number of troops to deploy
            int nbTroopsToDeploy = 1;

            //Random mode
            if (exMode == ExecMode.RANDOM.value()) {
                selectedRegionIndex = (int) ((Math.random() * (playerRegions.size())));
                target = playerRegions.get(selectedRegionIndex);

                nbTroopsToDeploy = (int) ((Math.random() * (nbDeployMax - min))) + min;
            }

            //Console mode
            if (exMode == ExecMode.CONSOLE.value()) {
                //Select region to deploy troop too
                System.out.println("Region controllés :");
                for (int cr = 0; cr < playerRegions.size(); cr++) {
                    Region currentRegion = playerRegions.get(cr);
                    System.out.println("\t[" + cr + "] " + currentRegion.getName() + " - Nb troupes : " + currentRegion.getDeployedTroops());
                }

                try {
                    do {
                        System.out.println("Choix de la region ? ");
                        selectedRegionIndex = Integer.parseInt(br.readLine());
                    } while (selectedRegionIndex >= playerRegions.size() || selectedRegionIndex < 0);
                } catch (Exception ex) {

                }

                //Select number of troop to deploy
                System.out.println("Combien de troupes deployer sur la region de " + playerRegions.get(selectedRegionIndex).getName());
                try {
                    do {
                        System.out.println(" ("+ min +"-" + nbDeployMax + ") ?" );
                        nbTroopsToDeploy = Integer.parseInt(br.readLine());
                    } while (nbTroopsToDeploy < min || nbTroopsToDeploy > nbDeployMax );
                } catch (Exception ex) {

                }
            }

            //Gui mode
            if (exMode == ExecMode.CONSOLE.value()) {
                /* TODO : Choix d'une region possédé -> GUI */
                /* TODO : Choix du nombre de troupe à deployer -> GUI */
            }


            target.changeDeployedTroops(nbTroopsToDeploy);
            nbDeployMax -= nbTroopsToDeploy;
            System.out.println(player.getName() + " a deployé (" + nbTroopsToDeploy + ") troupes sur la region de " + target.getName() + " !");
            if (nbDeployMax > 0) {
                System.out.println("Il doit encore deployer " + nbDeployMax + " troupes !!");
            } else {
                System.out.println("Déploiement de troupes terminé !");
            }
            System.out.println("\n");

        }
    }

    public static int calcBonusSum(Player p) {
        Map mapObj = Map.getInstance();
        List<Region> controlledRegions = p.getControlledRegions();
        List<Zone> allZones = mapObj.getZones();
        int bonusSum = 0;

        //For each controlled region
        for (Region region : controlledRegions) {
            //System.out.println(region.getBonus());
            bonusSum += region.getBonus();
        }

        for (Zone zone : allZones) {
            // Only if the players owns all the zone's region
            // Then he can have the zone bonus
            List<Region> zoneRegions = zone.getRegions();
            if (controlledRegions.containsAll(zoneRegions)) {
                bonusSum += zone.getBonus();
            }
            //System.out.println(zone.getBonus());
        }

        //Return the truncated integer (= rounded down)
        return (int) Math.floor(bonusSum / mapObj.getDivider());
    }
}
