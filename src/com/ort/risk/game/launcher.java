package com.ort.risk.game;

public class launcher {
    public static void main(String[] args){

        // Parse XML file into Map object
        parser.prepMap();

        //Mode selection, and regions repartitions between the players
        play.InitDeployment();

        //Main loop
        play.GameLoop();

    }
}
