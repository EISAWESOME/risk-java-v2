package com.ort.risk.ui.views;

import java.util.HashMap;
import java.util.Set;

import com.ort.risk.model.Map;
import com.ort.risk.model.Mode;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * 
 * @author tibo
 *
 * Mode and players selector frame
 */
public class UIModeStage extends Stage {

	private Map map;
	private GridPane mainPane;
	private ChoiceBox<String> modeChoiceBox;
	private Label modeChoiceBoxLabel;
	
	public UIModeStage() {
		this.setTitle("Risk");
		map = Map.getInstance();

		// Building main panel
		mainPane = new GridPane();
        mainPane.setPadding(new Insets(5));
        mainPane.setHgap(5);
        mainPane.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(150);
        column2.setHgrow(Priority.ALWAYS);
        mainPane.getColumnConstraints().addAll(column1, column2);
        
        // Building mode selecter
        modeChoiceBoxLabel = new Label("Choose a mode");
		HashMap<String, Mode> mapModes = new HashMap<String, Mode> ();
		for (Mode mode : map.getModes()) {
			String computedMode = String.format("%s - %s", mode.getNbPlayer(), mode.getNbInitTroops());
			mapModes.put(computedMode, mode);
		}
		Set<String> computedModes = mapModes.keySet();
		modeChoiceBox = new ChoiceBox<String>();
		modeChoiceBox.setItems(FXCollections.observableArrayList(computedModes));
		
		modeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number> () {
			
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number value, Number newValue) {
				String computedMode = (String) computedModes.toArray()[newValue.intValue()];
				Mode selectedMode = mapModes.get(computedMode);
				selectedMode.setIsSelected(true);
			}
			
		});
		
		// Adding controllers to main panel
		// ChoiceBox Label
		GridPane.setHalignment(modeChoiceBoxLabel, HPos.RIGHT);
		mainPane.add(modeChoiceBoxLabel, 0, 0);
		
		// ChoiceBox
		GridPane.setHalignment(modeChoiceBox, HPos.LEFT);
		mainPane.add(modeChoiceBox, 1, 0);
		
	}
	
	public void getDisplay() {
		setScene(new Scene(mainPane, 300, 200));
		show();
	}
	
}
