package com.ort.risk.ui.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class UIGameStage extends CustomStage {

	public UIGameStage() {
		super("Risk - Game");

		// Building main panel
		mainPane = new GridPane();
        mainPane.setPadding(new Insets(5));
        mainPane.setHgap(5);
        mainPane.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(150);
        //column2.setHgrow(Priority.ALWAYS);
        mainPane.getColumnConstraints().addAll(column1, column2);
        
        // Map name
        this.setTitle(getTitle() + " - " + map.getName());
        
        // Map image
        String imgUri = map.getImg();
        System.out.println(imgUri);
//        Label imgLbl = new Label(imgUri);
//        Image mapImg = new Image(imgUri);
//        ImageView mapImgView = new ImageView();
//        mapImgView.setImage(mapImg);
        
        // Filling main panel
        // Map View
//        GridPane.setHalignment(mapImgView, HPos.RIGHT);
//		mainPane.add(mapImgView, 1, 0);
	}

}
