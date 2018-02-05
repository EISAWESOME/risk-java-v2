package com.ort.risk.game;

import com.ort.risk.model.Map;

/**
 * @author CS
 * Launcher of the app
 */
public class Launcher {

    public static void main(String[] args) {
        Map mapObj = Map.getInstance();

        if(args.length > 0){
            switch (args[0]) {
                //Mode console
                case "-c":
                    mapObj.setExMode(ExecMode.CONSOLE.value());
                    break;
                //Mode random
                case "-r":
                    mapObj.setExMode(ExecMode.RANDOM.value());
                    break;
                //Mode graphique
                case "-g":
                    mapObj.setExMode(ExecMode.GUI.value());
                    break;
                default:
                    mapObj.setExMode(ExecMode.GUI.value());
            }
        } else {
            mapObj.setExMode(ExecMode.CONSOLE.value());
        }

        // Parse XML file into Map object
        Parser.prepMap();

        //Mode selection, and regions repartitions between the players
        Play.InitDeployment();

        //Main loop
        Play.GameLoop();

    }

    public enum ExecMode {
        CONSOLE(0, "-c"),
        RANDOM(1, "-r"),
        GUI(2, "-g");

        private final int value;
        private final String consoleArg;

        ExecMode(int value, String consoleArg) {

            this.value = value;
            this.consoleArg = consoleArg;
        }

        public int value() {
            return this.value;
        }
        public String arg() {
            return this.consoleArg;
        }

    }


}
