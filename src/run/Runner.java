package run;

import java.io.IOException;
import gameStates.GameState;
import graphics.MyPaths;
import graphics.objects.Object.Direction;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import main.Main;

public class Runner {

	private Canvas canvas;
	private GraphicsContext gc;
	private static boolean gameOver;
	private static boolean win;
	private GameState gameState;

	public Runner(GameState gameState, Canvas canvas) {
		this.gameState = gameState;
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		Runner.gameOver = false;
		Runner.win = false;
	}
	
	private void gameOver() {
		if (Main.ifSpecialSounds)
			Main.setMusic(MyPaths.bGameOver);
		else
			Main.player.pause();
		var loader = new FXMLLoader(this.getClass().getResource(MyPaths.screenGameOver));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Main.stackPane.getChildren().clear();
		Main.stackPane.getChildren().add(pane);
	}

	private void win() {
		if (gameState.getBoss().getDirection() == Direction.LEFT) {
			var image = new Image(getClass().getResource(MyPaths.troll_dead_L).toExternalForm());
			gc.drawImage(image, gameState.getBoss().getX() - GameState.getGameCamera().getXOffset(), 520);
		} else {
			var image = new Image(getClass().getResource(MyPaths.troll_dead_R).toExternalForm());
			gc.drawImage(image, gameState.getBoss().getX() - GameState.getGameCamera().getXOffset(), 520);
		}
		var loader = new FXMLLoader(this.getClass().getResource(MyPaths.screenWin));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
		if (Main.ifSounds && !Main.ifSpecialSounds)
			Main.setMusic(MyPaths.winSound);
		else if (Main.ifSounds)
			Main.setMusic(MyPaths.bWin);
		Main.stackPane.getChildren().add(pane);
	}

	public void run() {
		LongValue lastNanoTime = new LongValue(System.nanoTime());
		new AnimationTimer() {
			public void handle(long currTime) {
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				double elapsedTime = (currTime - lastNanoTime.value) / 1000000000.0;
				lastNanoTime.value = currTime;
				gameState.tick(elapsedTime);
				if (gameOver || win) {
					this.stop();
					if (gameOver)
						gameOver();
					else
						win();
				}
			}
		}.start();
	}

	public class LongValue {
		public long value;

		public LongValue(long time) {
			this.value = time;
		}
	}

	public static void setGameOver(boolean gameOver) {
		Runner.gameOver = gameOver;
	}

	public static void setWin(boolean win) {
		Runner.win = win;
	}
}
