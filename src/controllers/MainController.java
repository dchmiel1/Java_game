package controllers;

import java.io.File;
import java.io.IOException;
import graphics.MyPaths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
		String currentDirectory = System.getProperty("user.dir");
		String bip = currentDirectory + "/" + MyPaths.menuMusic;
		Media hit = new Media(new File(bip).toURI().toString());
		Main.player = new MediaPlayer(hit);
		Main.player.play();
	}

}
