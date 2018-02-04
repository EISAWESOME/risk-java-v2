package com.ort.risk.game;

import com.ort.risk.model.*;

import java.util.List;
import java.util.stream.Collectors;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author CS
 */
public class parser {
    public static void prepMap() {
        Map mapObj = Map.getInstance();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        /* TODO : La map doit etre importer d'apres un input de l'utilisateur, pas en dur*/
        String mapPath = "resources/map/classic.xml";

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File mapXML = new File(mapPath);
            Document xml = builder.parse(mapXML);
            Element root = xml.getDocumentElement();
            XPathFactory xpf = XPathFactory.newInstance();
            XPath path = xpf.newXPath();

            String[] guiArgs = new String[5];
            //Set map's Img, name, minimal and divider
            setMapParams(mapObj, path, root);

            //Set map's modes
            setMapModes(mapObj, path, root);

            //Set map's zone, that's the big boy function
            setMapZones(mapObj, path, root);

            /* TODO : L'utilisateur choisis le modequ'il souhaite jouer*/

            /* TODO : On set le flag selected sur le mode selectionné*/

            /* TODO : On crée le nombre de Player correspondant au nb du mode*/

            //Here we got a complete map object, and ready to play
            //System.out.println(mapObj.toString());


            System.out.println(
                    "__________.___  _____________  __.\n" +
                    "\\______   \\   |/   _____/    |/ _|\n" +
                    " |       _/   |\\_____  \\|      <  \n" +
                    " |    |   \\   |/        \\    |  \\ \n" +
                    " |____|_  /___/_______  /____|__ \\\n" +
                    "        \\/            \\/        \\/");

            List<Mode> allModes = mapObj.getModes();
            allModes.get(0).setIsSelected(true);

            System.out.println("Mode selectionné : ");
            System.out.println("\tNb player : " + allModes.get(0).getNbPlayer());
            System.out.println("\tNb Troupes initial : " + allModes.get(0).getNbInitTroops());
            System.out.println("\n");

            System.out.println("Liste des joueurs : ");
            int nbTroupePerPlayer = allModes.get(0).getNbInitTroops();
            for(int n = 1; n <= allModes.get(0).getNbPlayer(); n++){

                // Add players to the map
                mapObj.addPlayer(new Player("Player"+ n, n, nbTroupePerPlayer));

                System.out.println("\tNom : " + "Player"+ n);
                System.out.println("\tOrdre de passage : " + n);

            }

            //Regions' attribution
            //Filter to get only the not occupied regions
            List<Region> notOccupiedRegions = mapObj.getRegions().stream()
                    .filter(p -> !p.getIsOccupied()).collect(Collectors.toList());

            while(notOccupiedRegions.size() >= allModes.get(0).getNbPlayer()) {
                for(int p = 0; p < mapObj.getPlayerList().size(); p++){
                    int rand =  (int)(Math.random() * notOccupiedRegions.size());

                    //Both region and zone list references the same region objects
                    //Hence, change flags in the region list would change them in the zone list too


                    notOccupiedRegions.get(rand).setDeployedTroops(1);
                    mapObj.getPlayerList().get(p).changeNbTroupes(-1);

                    mapObj.getPlayerList().get(p).addControlledRegion(notOccupiedRegions.get(rand));
                    notOccupiedRegions.get(rand).setIsOccupied(true);

                    notOccupiedRegions = notOccupiedRegions.stream()
                            .filter(po -> !po.getIsOccupied() ).collect(Collectors.toList());
                }


            }

            //Initial deployment
            /* TODO  : A CHANGER - Le déploiement se fait de façon random, en attendant l'interface graphique */
            for(int q = 0; q < mapObj.getPlayerList().size(); q++){
                Player currentPlayer = mapObj.getPlayerList().get(q);
                while(currentPlayer.getNbTroops() > 0){
                    int rand =  (int)(Math.random() * currentPlayer.getControlledRegions().size());
                    currentPlayer.getControlledRegions().get(rand).changeDeployedTroops(1);
                    currentPlayer.changeNbTroupes(-1);
                }
            }




            for(int pq = 0; pq < mapObj.getPlayerList().size(); pq++){
                System.out.println(mapObj.getPlayerList().get(pq).getName());
                List<Region> playerRegion = mapObj.getPlayerList().get(pq).getControlledRegions();
                for(int pr =0; pr < playerRegion.size(); pr++){
                    System.out.println("\t" + playerRegion.get(pr).getName()  + " : " + playerRegion.get(pr).getDeployedTroops() );

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    protected static void setMapParams(Map mapObj, XPath path, Element root) {
        try {

            // Set the map's name
            String nameStr = (String) path.evaluate("/map/name", root);
            mapObj.setName(nameStr);

            //Set the map's img url
            String imgStr = (String) path.evaluate("/map/image", root);
            mapObj.setImg(imgStr);


            //Set the map's minimal reinforcment
            Integer nbMin = Integer.parseInt((path.evaluate("/map/minimal", root)).trim());
            mapObj.setNbMinReinforcement(nbMin);

            //Set the map's divider
            Integer nbDiv = Integer.parseInt((path.evaluate("/map/divisor", root)).trim());
            mapObj.setDivider(nbDiv);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected static void setMapModes(Map mapObj, XPath path, Element root) {

        try {
            //Get all <mode> from <modes>
            String modesExpr = "modes//mode";
            NodeList modesList = (NodeList) path.evaluate(modesExpr, root, XPathConstants.NODESET);

            //For each <mode>
            for (int i = 0; i < modesList.getLength(); i++) {
                Mode objMode = new Mode();
                Node mode = modesList.item(i);

                //Set number of player
                Integer nbPlayers = Integer.parseInt((path.evaluate("players", mode)).trim());
                objMode.setNbPlayer(nbPlayers);

                //Set the number of init troops
                Integer nbInit = Integer.parseInt((path.evaluate("initial", mode)).trim());
                objMode.setNbInitTroops(nbInit);

                //Add the mode to the map object
                //mapObj.toString();
                mapObj.addMode(objMode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    protected static void setMapZones(Map mapObj, XPath path, Element root) {

        try {
            //Get all <zone> from <zones>
            String zonesExpr = "zones//zone";
            NodeList zonesList = (NodeList) path.evaluate(zonesExpr, root, XPathConstants.NODESET);

            //For each <zone>
            for (int i = 0; i < zonesList.getLength(); i++) {
                Zone objZone = new Zone();
                Node zone = zonesList.item(i);

                //Set zone's name
                String zName = path.evaluate("name", zone);
                objZone.setName(zName);

                //Set zone's bonus
                Integer zBonus = Integer.parseInt((path.evaluate("bonus", zone)).trim());
                objZone.setBonus(zBonus);

                NodeList zoneRegionList = (NodeList) path.evaluate("regions/region", zone, XPathConstants.NODESET);

                // For each zone's region
                for (int j = 0; j < zoneRegionList.getLength(); j++) {
                    Node region = zoneRegionList.item(j);
                    Region objRegion = new Region();
                    objRegion.setIsOccupied(false);

                    //Set region's name
                    String rName = path.evaluate("name", region).trim();


                    //Get the regions from the <map><regions><region> so we can get <bonus>
                    NodeList regionRoot = (NodeList) path.evaluate("regions/region", root, XPathConstants.NODESET);
                    for (int mr = 0; mr < regionRoot.getLength(); mr++) {
                        Node matchedRegion = regionRoot.item(mr);
                        String matchedRegionName = path.evaluate("name", matchedRegion).trim();
                        String matchedRegionBonus = path.evaluate("bonus", matchedRegion).trim();
                        if (matchedRegionName.trim().equals(rName)) {
                            objRegion.setName(matchedRegionName);
                            objRegion.setBonus(Integer.parseInt(matchedRegionBonus));
                        }
                    }


                    //System.out.println(region.getNodeName() + " : " + region.getTextContent());


                    Node adjRoot = (Node) path.evaluate("adjacencies", root, XPathConstants.NODE);
                    //System.out.println(adjRoot.getTextContent());

                    //Look for region's corresponding adjacency object
                    String adjExpr = "//adjacency";
                    NodeList matchedAdjList = (NodeList) path.evaluate(adjExpr, adjRoot, XPathConstants.NODESET);
                    /*

                    // ATTENTION
                    // Ca marche SEULEMENT car les Adj sont dans le meme ordre que les regions
                    Node matchedAdj = matchedAdjList.item(j);
                    System.out.println(matchedAdj.getTextContent());

                    */

                    // Plutot nul a chier et pas rapide mais j'ai pas trouver mieu
                    // On devrais pouvoir matcher directement l'adjacency par rapport a la valeur de <start><region><name>
                    // mais j'y suis pas arrivé

                    // For every adjacency node
                    for (int k = 0; k < matchedAdjList.getLength(); k++) {
                        Node matchedAdj = matchedAdjList.item(k);
                        Node matchedAdjStartRegionName = (Node) path.evaluate("start/region/name", matchedAdj, XPathConstants.NODE);
                        // If the adjacency strat match with the current iteration's region name
                        if (matchedAdjStartRegionName.getTextContent().trim().equals(rName)) {

                            NodeList endList = (NodeList) path.evaluate("ends//end", matchedAdj, XPathConstants.NODESET);

                            // For every end region of the matched adjacency
                            for (int l = 0; l < endList.getLength(); l++) {

                                // Create Frontier object
                                Node end = endList.item(l);
                                Frontier objFrontier = new Frontier();

                                Node endRegionNameNode = (Node) path.evaluate("region/name", end, XPathConstants.NODE);
                                String endRegionName = endRegionNameNode.getTextContent().trim();

                                objFrontier.setRegionEnd(endRegionName);

                                NodeList endRegionMoves = (NodeList) path.evaluate("moves//move", end, XPathConstants.NODESET);
                                for (int m = 0; m < endRegionMoves.getLength(); m++) {
                                    Node move = endRegionMoves.item(m);
                                    Move objMove = new Move();
                                    Node moveNameNode = (Node) path.evaluate("name", move, XPathConstants.NODE);
                                    String moveName = moveNameNode.getTextContent().trim();
                                    objMove.setName(moveName);
                                    objFrontier.addMove(objMove);
                                }
                                objRegion.addFrontier(objFrontier);
                            }

                            objZone.addRegion(objRegion);
                            mapObj.addRegion(objRegion);
                        }
                    }
                }
                mapObj.addZone(objZone);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}