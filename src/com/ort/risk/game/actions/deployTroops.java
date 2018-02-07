package com.ort.risk.game.actions;

import com.ort.risk.model.*;

public class deployTroops {
    public static void execute(Region chosenRegion, int nbTroopsToDeploy){
        chosenRegion.changeDeployedTroops(nbTroopsToDeploy);
    }
}
