package com.ort.risk.game.actions;

import com.ort.risk.model.*;

public class attribRegion {
    public static void execute(Player player, Region chosenRegion){
        int a = chosenRegion.getDeployedTroops();
        chosenRegion.setDeployedTroops(1);
        player.changeNbTroops(-1);

        player.addControlledRegion(chosenRegion);
        chosenRegion.setIsOccupied(true);
    }
}
