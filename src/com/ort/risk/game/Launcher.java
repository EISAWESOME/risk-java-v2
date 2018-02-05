package com.ort.risk.game;

/**
 * @author CS
 * Launcher of the app
 */
public class Launcher {

    public static void main(String[] args) {
        Integer ExecMode;

        switch (args[0]) {
            //Mode console
            case "-c":
                ExecMode = 0;
                break;
            //Mode random
            case "-r":
                ExecMode = 1;
                break;
            //Mode graphique
            case "-g":
                ExecMode = 2;
                break;
            default:
                ExecMode = 2;
        }
        // Parse XML file into Map object
        Parser.prepMap();

        //Mode selection, and regions repartitions between the players
        Play.InitDeployment(ExecMode);

        //Main loop
        Play.GameLoop();

    }


}
