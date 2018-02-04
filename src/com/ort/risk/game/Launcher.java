package com.ort.risk.game;

public class Launcher {
    public static void main(String[] args){

        // Parse XML file into Map object
        Parser.prepMap();

        //Mode selection, and regions repartitions between the players
        Play.InitDeployment();

        //Main loop
        Play.GameLoop();

    }
}
