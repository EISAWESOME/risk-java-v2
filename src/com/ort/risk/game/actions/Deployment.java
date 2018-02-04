package com.ort.risk.game.actions;

import com.ort.risk.model.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Deployment {

    public static void execute(Player player){
        Map mapObj = Map.getInstance();
        int min = mapObj.getNbMinReinforcement();
        int bonusSum = calcBonusSum(player);
        int nbDeploy = Math.min(min, bonusSum);
        List<Region> playerRegions =  player.getControlledRegions();
        //System.out.println(nbRenfort);

        while(nbDeploy > 0){

            /* TODO : Choix d'une region possédé -> GUI */
            //Chosing a random controlled region
            int rand = (int)((Math.random() * (playerRegions.size()))) ;
            Region target = playerRegions.get(rand);


            /* TODO : Choix du nombre de troupe à deployer -> GUI */
            //Chosing a random number of troops to deploy

            int nbTroopsToDeploy =  (int)((Math.random() * (nbDeploy - 1))) + 1;
            target.changeDeployedTroops(nbTroopsToDeploy);
            nbDeploy -= nbTroopsToDeploy;
            System.out.println( player.getName() + " a deployé (" + nbTroopsToDeploy + ") troupes sur la region de " + target.getName() + " !");
            if(nbDeploy > 0){
                System.out.println( "Il doit encore deployer " + nbDeploy + " troupes !!");
            } else {
                System.out.println( "Déploiement de troupes terminé !");
            }
            System.out.println("\n");

        }
    }

    public static int calcBonusSum(Player p){
        Map mapObj = Map.getInstance();
        List<Region> controlledRegions = p.getControlledRegions();
        List<Zone> allZones = mapObj.getZones();
        int bonusSum = 0;

        //For each controlled region
        for(Region region : controlledRegions) {
            //System.out.println(region.getBonus());
            bonusSum += region.getBonus();
        }

        for(Zone zone : allZones) {
            // Only if the players owns all the zone's region
            // Then he can have the zone bonus
            List<Region> zoneRegions = zone.getRegions();
            if(controlledRegions.containsAll(zoneRegions)){
                bonusSum += zone.getBonus();
            }
            //System.out.println(zone.getBonus());
        }


        //Return the truncated integer (= rounded down)
        return (int)Math.floor(bonusSum / mapObj.getDivider());
    }
}
