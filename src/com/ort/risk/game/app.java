package com.ort.risk.game;

import com.ort.risk.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class app {

    public static void GameLoop() {
        Map mapObj = Map.getInstance();
        List<Player> playerList = mapObj.getPlayerList();

        //Sort the players by turn order
        playerList.sort(Comparator.comparing(Player::getOrder));

        /*
        playerList.forEach((player) -> {
            System.out.println(player.getOrder());

        });
        */

        //Filter to keep only the players that have at least 1 controlled region
        List<Player> validPlayerList = playerList.stream()
                .filter(p -> p.getControlledRegions().size() >= 1 ).collect(Collectors.toList());

        //While there is still 2 valid player in the game
        while(validPlayerList.size() >= 2){

            // For each player

            validPlayerList.forEach((player) -> {
                //Player take turn
                turn.TakeTurn(player);


            });

            //Update the valid player list after every turn
            validPlayerList = playerList.stream()
                    .filter(p -> p.getControlledRegions().size() >= 1 ).collect(Collectors.toList());



        }
    }



}
