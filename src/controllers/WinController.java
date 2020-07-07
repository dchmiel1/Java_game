package controllers;

import java.io.IOException;
import graphics.MyPaths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import main.Main;

public class WinController {

	@FXML
	private void backMenu() {
		var loader = new FXMLLoader(this.getClass().getResource(MyPaths.screenMenu));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setScreen(pane);
		Main.ifMusic = true;
		Main.ifSounds = true;
		Main.setMusic(MyPaths.menuMusic);
	}

	@FXML
	private void exit() {
		System.exit(0);
	}

	private void setScreen(Pane pane) {
		Main.stackPane.getChildren().clear();
		Main.stackPane.getChildren().add(pane);
	}

}
