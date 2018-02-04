package com.ort.risk.ui;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UILauncher extends Application {

	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		
		this.stage = primaryStage;
		this.stage.setTitle("Risk");
		
		initRootLayout();
				
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout() {
        
        Button mapChooserInput = new Button("Choose a map");
        mapChooserInput.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
				File mapFile = fileChooser.showOpenDialog(stage);
			}
        	
        });
        StackPane root = new StackPane();
        root.getChildren().add(mapChooserInput);

        // Show the scene containing the root layout.
        stage.setScene(new Scene(root, 300, 200));
        stage.show();
    }
}
