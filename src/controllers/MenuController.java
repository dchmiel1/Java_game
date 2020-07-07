package controllers;

import java.io.IOException;
import graphics.MyPaths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.Main;

public class MenuController {

	@FXML
	private ImageView music;
	@FXML
	private ImageView sound;
	@FXML
	private ImageView bg;

	@FXML
	private void initialize() {
		bg.setImage(new Image(getClass().getResource(MyPaths.menuBg).toExternalForm()));
		music.setImage(new Image(getClass().getResource(MyPaths.music_on).toExternalForm()));
		sound.setImage(new Image(getClass().getResource(MyPaths.volume_on).toExternalForm()));
	}

	@FXML
	private void play() {
		var loader = new FXMLLoader(this.getClass().getResource(MyPaths.screenHeroChoice));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setScreen(pane);
	}

	@FXML
	private void help() {
		var loader = new FXMLLoader(this.getClass().getResource(MyPaths.screenHelp));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setScreen(pane);
	}

	@FXML
	private void credits() {
		var loader = new FXMLLoader(this.getClass().getResource(MyPaths.screenCredits));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setScreen(pane);
	}

	@FXML
	private void changeMusic() {
		String url = music.getImage().getUrl();
		if (url.substring(url.toString().length() - 6, url.toString().length() - 4).equals("on")) {
			music.setImage(new Image(getClass().getResource(MyPaths.music_off).toExternalForm()));
			Main.ifMusic = false;
			Main.player.pause();
		} else {
			music.setImage(new Image(getClass().getResource(MyPaths.music_on).toExternalForm()));
			Main.player.play();
			Main.ifMusic = true;
		}
	}

	@FXML
	private void changeSound() {
		String url = sound.getImage().getUrl();
		if (url.substring(url.toString().length() - 6, url.toString().length() - 4).equals("on")) {
			sound.setImage(new Image(getClass().getResource(MyPaths.volume_off).toExternalForm()));
			Main.ifSounds = false;
		} else {
			sound.setImage(new Image(getClass().getResource(MyPaths.volume_on).toExternalForm()));
			Main.ifSounds = true;
		}

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
