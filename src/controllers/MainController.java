package controllers;

import java.io.File;
import java.io.IOException;

import graphics.MyPaths;
import jaco.mp3.player.MP3Player;
//import jaco.mp3.player.MP3Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import main.Main;

public class MainController {

	@FXML
	private StackPane mainStackPane;

	@FXML
	private void initialize() {
		setMenuMusic();
		setMenu();
	}

	private void setMenu() {
		var loader = new FXMLLoader(this.getClass().getResource(MyPaths.screenMenu));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setScreen(pane);
	}

	private void setScreen(Pane pane) {
		mainStackPane.getChildren().clear();
		mainStackPane.getChildren().add(pane);
	}

	private void setMenuMusic() {
		Main.player = new MP3Player(new File(MyPaths.menuMusic));
		Main.player.play();
	}

}
