package controllers;

import java.io.IOException;

import graphics.MyPaths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.Main;

public class HelpController {

	@FXML
	private ImageView bg;

	@FXML
	private void initialize() {
		bg.setImage(new Image(getClass().getResource("/img/bg/menuBg.png").toExternalForm()));
	}

	@FXML
	private void backToMenu() {
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
		Main.stackPane.getChildren().clear();
		Main.stackPane.getChildren().add(pane);
	}
}
