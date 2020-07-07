package gameStates;

import java.util.ArrayList;

import graphics.Background;
import graphics.GameCamera;
import graphics.World;
import graphics.objects.Arrow;
import graphics.objects.Hero;
import graphics.objects.Item;
import graphics.objects.Monster;
import graphics.objects.Shot;
import graphics.objects.Sign;
import graphics.objects.Weapon;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import keyboard.Keyboard;
import run.Runner;

public abstract class GameState {

	protected Hero hero;
	protected Background bg;
	protected World world;
	protected GraphicsContext gc;
	protected Runner runner;
	protected Canvas canvas;
	protected Keyboard keyboard;
	protected int timer;
	protected static int score;
	protected ArrayList<Monster> monsters = new ArrayList<Monster>();
	protected ArrayList<Sign> signs = new ArrayList<Sign>();
	protected ArrayList<Shot> shots = new ArrayList<Shot>();
	protected ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	protected ArrayList<Item> items = new ArrayList<Item>();
	protected ArrayList<Arrow> arrows = new ArrayList<Arrow>();
	protected Monster boss;
	protected static GameCamera gameCamera;
	protected static boolean bossTime;
	protected boolean win;
	
	protected void renderScore() {
		gc.setFill(Color.BLACK);
		gc.setFont(Font.font("", FontWeight.BOLD, 20));
		gc.fillText("Score: " + score, 10, 87);
	}

	protected abstract void update(double elapsedTime);

	protected abstract void render();

	protected abstract void setStartingObjects();

	protected abstract void start();

	public abstract void tick(double elapsedTime);

	public Hero getHero() {
		return hero;
	}
	
	public Background getBg() {
		return bg;
	}

	public World getWorld() {
		return world;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int score) {
		GameState.score = score;
	}

	public ArrayList<Shot> getShots() {
		return shots;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public ArrayList<Arrow> getArrows() {
		return arrows;
	}
	
	public static GameCamera getGameCamera() {
		return gameCamera;
	}
	
	public Monster getBoss() {
		return boss;
	}
	
	public static boolean getBossTime() {
		return bossTime;
	}

}
