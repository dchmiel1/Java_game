package controllers;

import java.io.IOException;

import gameStates.Level1;
import graphics.MyPaths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.Main;

public class HeroChoiceController {

	private Glow glow = new Glow();
	private char choice;
	@FXML
	private Button playButton;
	@FXML
	private ImageView manImg;
	@FXML
	private ImageView womanImg;
	@FXML
	private ImageView bg;

	@FXML
	private void initialize() {
		bg.setImage(new Image(getClass().getResource(MyPaths.menuBg).toExternalForm()));
		womanImg.setImage(new Image(getClass().getResource(MyPaths.heroF).toExternalForm()));
		manImg.setImage(new Image(getClass().getResource(MyPaths.heroM).toExternalForm()));
		playButton.setDisable(true);
		glow.setLevel(0.9);
	}

	@FXML
	private void play() {
		var canvas = new Canvas(Main.WIDTH * Main.SCALE, Main.HEIGHT * Main.SCALE);
		Main.stackPane.getChildren().clear();
		Main.stackPane.getChildren().add(canvas);
		var level1 = new Level1(canvas, choice);
		level1.start();
	}

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
	}

	@FXML
	private void manChosen() {
		playButton.setDisable(false);
		womanImg.setEffect(null);
		manImg.setEffect(glow);
		choice = 'm';
	}

	@FXML
	private void womanChosen() {
		// playButton.setDisable(false);
		// manImg.setEffect(null);
		// womanImg.setEffect(glow);
		// choice = 'f';
	}

	private void setScreen(Pane pane) {
		Main.stackPane.getChildren().clear();
		Main.stackPane.getChildren().add(pane);
	}
}
