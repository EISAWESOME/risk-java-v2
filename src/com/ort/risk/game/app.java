package com.ort.risk.game;

import com.ort.risk.model.*;

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



/**
 * @author CS
 */
public class app {
    public static void main(String[] args) {
        Map mapObj = new Map();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        String mapPath = "resources/map/classic.xml";

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File mapXML = new File(mapPath);
            Document xml = builder.parse(mapXML);
            Element root = xml.getDocumentElement();
            XPathFactory xpf = XPathFactory.newInstance();
            XPath path = xpf.newXPath();

            //Set Map's name
            setMapName(mapObj,path, root);

            //Set map's Img
            setMapImg(mapObj, path, root);

            //Get all <mode> from <modes>
            String modesExpr = "modes//mode";
            NodeList modesList = (NodeList)path.evaluate(modesExpr, root, XPathConstants.NODESET);

            //For each <mode>
            for(int i = 0 ; i < modesList.getLength(); i++){
                Mode objMode = new Mode();
                Node mode = modesList.item(i);

                //Set number of player
                Integer nbPlayers = Integer.parseInt(path.evaluate("players", mode));
                objMode.setNbPlayer(nbPlayers);

                //Set the number of init troops
                Integer nbInit = Integer.parseInt(path.evaluate("initial", mode));
                objMode.setNbInitTroops(nbInit);

                //Add the mode to the map object
                mapObj.addMode(objMode);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    protected static void setMapName(Map mapObj, XPath path, Element root){
        try {
            // Set the map's name
            String nameExpr = "/map/name";
            String nameStr = (String) path.evaluate(nameExpr, root);
            mapObj.setName(nameStr);

            // System.out.println(mapObj.getName());
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }


    protected static void setMapImg(Map mapObj, XPath path, Element root){
        try {
            //Set the map's img url
            String imgExpr = "/map/image";
            String imgStr = (String)path.evaluate(imgExpr, root);
            mapObj.setImg(imgStr);

            // System.out.println(mapObj.getImg());
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }




}