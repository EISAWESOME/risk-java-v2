package com.ort.risk.game.actions;

import com.ort.risk.model.*;

import java.util.List;


public class calcMaxDeploy {
    public static int execute(Player p){

        Map mapObj = Map.getInstance();
        int min = mapObj.getNbMinReinforcement();
        int bonusSum = calcBonusSum(p);
        return Math.max(min, bonusSum);

    }

    private static int calcBonusSum(Player p) {
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
