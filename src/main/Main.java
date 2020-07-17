package main;

import java.io.File;

import graphics.MyPaths;
import jaco.mp3.player.MP3Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static int SCALE = 8;
	public static int WIDTH = 160;
	public static int HEIGHT = 90;
	public static Scene scene;
	public static StackPane stackPane;
	public static boolean ifSounds;
	public static boolean ifMusic;
	public static MP3Player player;

	public static boolean ifBad = false;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		var loader = new FXMLLoader(this.getClass().getResource(MyPaths.screenMain));
		Main.ifSounds = true;
		Main.ifMusic = true;
		stackPane = loader.load();
		scene = new Scene(stackPane, WIDTH * SCALE, HEIGHT * SCALE);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Game");
		stage.show();
	}

	public static void setMusic(String path) {
		try {
			String currentDirectory = System.getProperty("user.dir");
			String pathToMusic = currentDirectory + "/" + path;
			player.pause();
			player = new MP3Player(new File(pathToMusic));
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
