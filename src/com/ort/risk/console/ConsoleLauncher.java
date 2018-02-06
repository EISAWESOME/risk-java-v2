package com.ort.risk.console;

import com.ort.risk.game.*;

public class ConsoleLauncher {

    public static void run() {
        System.out.println("MODE CONSOLE");

        // Parse XML file into Map object
        Parser.prepMap();

        //Mode selection, and regions repartitions between the players
        Play.InitDeployment();

        //Main loop
        Play.GameLoop();
    }

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }



}
