package com.ort.risk.game;

import com.ort.risk.model.Map;
import com.ort.risk.console.ConsoleLauncher;
import com.ort.risk.ui.UILauncher;

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
                    ConsoleLauncher.run();
                    break;
                //Mode random
                case "-r":
                    mapObj.setExMode(ExecMode.RANDOM.value());
                    ConsoleLauncher.run();
                    break;
                //Mode graphique
                case "-g":
                    UILauncher.run(args);
                    break;
                default:
                    mapObj.setExMode(ExecMode.CONSOLE.value());
                    ConsoleLauncher.run();
            }
        } else {
            mapObj.setExMode(ExecMode.CONSOLE.value());
            ConsoleLauncher.run();
        }

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
