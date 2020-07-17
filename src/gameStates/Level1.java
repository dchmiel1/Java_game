package gameStates;

import graphics.Background;
import graphics.GameCamera;
import graphics.MyPaths;
import graphics.World;
import graphics.objects.Item;
import graphics.objects.ManHero;
import graphics.objects.Object.ItemType;
import graphics.objects.Object.SignType;
import graphics.objects.Object.WeaponType;
import graphics.objects.Sign;
import graphics.objects.Weapon;
import graphics.objects.WomenHero;
import graphics.objects.monsters.BossTroll;
import graphics.objects.monsters.MonsterDryad;
import graphics.objects.monsters.MonsterTree;
import graphics.objects.monsters.MonsterWorm;
import javafx.scene.canvas.Canvas;
import keyboard.Keyboard;
import main.Main;
import run.Runner;

public class Level1 extends GameState {

	private MonsterWorm wormMonster;
	private MonsterTree treeMonster;
	private MonsterDryad dryadMonster;
	private char choice;
	private Weapon bat;
	private Weapon pistol;
	private Weapon sword;
	private Item bullets;
	private Item potion;
	private Sign sign;
	private double wormX[] = { 27, 113, 156, 149, 149.5, 150, 150.5, 151, 151.5, 152, 152.5, 274, 347, 366, 373, 405,
			408, 410, 415, 420, 335, 456, 478, 498, 465, 469, 485, 538, 528, 578, 605, 610, 611, 558, 560, 561, 538,
			600, 604 };
	private double wormY[] = { 650, 610, 130, 370, 370, 370, 370, 370, 370, 370, 370, 10, 610, 610, 570, 650, 650, 650,
			650, 650, 530, 650, 570, 650, 210, 210, 210, 610, 650, 330, 330, 330, 330, 10, 610, 610, 330, 610, 610 };
	private double treeX[] = { 50, 118, 175, 406, 410, 417, 477, 489, 608, 610 };
	private double treeY[] = { 440, 120, 440, 480, 480, 480, 0, 40, 160, 160 };
	private double dryadX[] = { 214, 220, 233, 193, 68, 621, 599 };
	private double dryadY[] = { 420, 300, 180, 180, 100, -60, 100 };

	public Level1(Canvas canvas, char choice) {
		this.choice = choice;
		Level1.bossTime = false;
		this.win = false;
		this.gc = canvas.getGraphicsContext2D();
		this.canvas = canvas;
		GameState.score = 0;
		this.timer = 0;
	}

	private void bossTime() {
		if (Main.ifMusic && !Main.ifBad)
			Main.setMusic(MyPaths.bossMusic);
		else if (Main.ifMusic)
			Main.setMusic(MyPaths.delfin);
		world.bossTime();
	}

	@Override
	protected void update(double elapsedTime) {
		bg.update();
		if (hero.getX() > 685 * world.getTileSize() && bossTime == false) {
			bossTime = true;
			bossTime();
		}
		if (hero.update(elapsedTime) == 1)
			Runner.setGameOver(true);
		for (int i = 0; i < monsters.size(); i++)
			if (monsters.get(i).getX() < 1250 + hero.getX() && monsters.get(i).getX() + 500 > hero.getX())
				if (monsters.get(i).update(elapsedTime) == 1)
					monsters.remove(i);
		if (!win)
			if (boss.getX() < 1400 + hero.getX() && boss.getX() + 1400 > hero.getX())
				if (boss.update(elapsedTime) == 1)
					Runner.setWin(true);
		for (int i = 0; i < shots.size(); i++) {
			if (shots.get(i).update(elapsedTime) == 1)
				shots.remove(i);
		}
		for (int i = 0; i < arrows.size(); i++) {
			if (arrows.get(i).getX() < 1250 + hero.getX() && arrows.get(i).getX() + 500 > hero.getX())
				if (arrows.get(i).update(elapsedTime) == 1)
					arrows.remove(i);
		}
	}

	@Override
	protected void render() {
		bg.draw(gc);
		world.render((int) hero.getX());
		hero.render();
		for (int i = 0; i < weapons.size(); i++)
			weapons.get(i).render();
		for (int i = 0; i < items.size(); i++)
			items.get(i).render();
		for (int i = 0; i < monsters.size(); i++)
			if (monsters.get(i).getX() < 1250 + hero.getX() && monsters.get(i).getX() + 500 > hero.getX())
				monsters.get(i).render();
		if (!win)
			if (boss.getX() < 1400 + hero.getX() && boss.getX() + 1400 > hero.getX())
				boss.render();
		for (int i = 0; i < shots.size(); i++)
			shots.get(i).render();
		for (int i = 0; i < arrows.size(); i++)
			if (arrows.get(i).getX() < 1250 + hero.getX() && arrows.get(i).getX() + 500 > hero.getX())
				arrows.get(i).render();
		for (int i = 0; i < signs.size(); i++)
			if (signs.get(i).getX() < 1100 + hero.getX() && signs.get(i).getX() + 500 > hero.getX())
				signs.get(i).render();
		renderScore();
	}

	@Override
	protected void setStartingObjects() {
		try {
			if (choice == 'm')
				hero = new ManHero(this, 2 * world.getTileSize(), 750, world);
			else
				hero = new WomenHero(this, 150, 100, world);
			for (int i = 0; i < wormX.length; i++) {
				wormMonster = new MonsterWorm(wormX[i] * world.getTileSize(),
						wormY[i] + world.getTileSize() * world.getYMapOff(), world, this);
				monsters.add(wormMonster);
			}
			for (int i = 0; i < treeX.length; i++) {
				treeMonster = new MonsterTree(treeX[i] * world.getTileSize(),
						treeY[i] + world.getTileSize() * world.getYMapOff(), world, this);
				monsters.add(treeMonster);
			}
			for (int i = 0; i < dryadX.length; i++) {
				dryadMonster = new MonsterDryad(dryadX[i] * world.getTileSize(),
						dryadY[i] + world.getTileSize() * world.getYMapOff(), world, hero, this);
				monsters.add(dryadMonster);
			}
			sign = new Sign(445 * world.getTileSize(), 550 + world.getYMapOff() * world.getTileSize(), world,
					SignType.easy, this);
			signs.add(sign);
			sign = new Sign(521 * world.getTileSize(), 350 + world.getYMapOff() * world.getTileSize(), world,
					SignType.easy, this);
			signs.add(sign);
			sign = new Sign(451 * world.getTileSize(), 230 + world.getYMapOff() * world.getTileSize(), world,
					SignType.hard, this);
			signs.add(sign);
			sign = new Sign(517 * world.getTileSize(), 590 + world.getYMapOff() * world.getTileSize(), world,
					SignType.hard, this);
			signs.add(sign);
			bg = new Background(MyPaths.bg, 0.1, hero);
			bg.draw(gc);
			bat = new Weapon(47 * world.getTileSize(), 490 + world.getTileSize() * world.getYMapOff(), world,
					WeaponType.bat, this);
			pistol = new Weapon(101 * world.getTileSize(), -170 + world.getTileSize() * world.getYMapOff(), world,
					WeaponType.pistol, this);
			weapons.add(bat);
			weapons.add(pistol);
			sword = new Weapon(423 * world.getTileSize(), 600 + world.getTileSize() * world.getYMapOff(), world,
					WeaponType.sword, this);
			weapons.add(sword);
			bullets = new Item(46 * world.getTileSize(), 530 + world.getTileSize() * world.getYMapOff(), world,
					ItemType.bullets, this);
			items.add(bullets);
			bullets = new Item(251 * world.getTileSize(), -120 + world.getTileSize() * world.getYMapOff(), world,
					ItemType.bullets, this);
			items.add(bullets);
			bullets = new Item(252 * world.getTileSize() + 20, -120 + world.getTileSize() * world.getYMapOff(), world,
					ItemType.bullets, this);
			items.add(bullets);
			potion = new Item(147 * world.getTileSize() + 60, -90 + world.getTileSize() * world.getYMapOff(), world,
					ItemType.potion, this);
			items.add(potion);
			potion = new Item(629 * world.getTileSize(), -50 + world.getTileSize() * world.getYMapOff(), world,
					ItemType.potion, this);
			items.add(potion);
			potion = new Item(618 * world.getTileSize(), 450 + world.getTileSize() * world.getYMapOff(), world,
					ItemType.potion, this);
			items.add(potion);
			bullets = new Item(616 * world.getTileSize(), 450 + world.getTileSize() * world.getYMapOff(), world,
					ItemType.bullets, this);
			items.add(bullets);
			potion = new Item(660 * world.getTileSize(), 450 + world.getTileSize() * world.getYMapOff(), world,
					ItemType.potion, this);
			items.add(potion);
			boss = new BossTroll(714 * world.getTileSize(), 385 + world.getYMapOff() * world.getTileSize(), world, hero,
					this);
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Override
	public void start() {
		try {
			world = new World(40, gc);
			world.loadMap();
			world.loadTiles();
			gameCamera = new GameCamera(0, 0, world);
		} catch (RuntimeException e) {
			System.exit(0);
		}
		setStartingObjects();
		if (Main.ifMusic && !bossTime)
			Main.setMusic(MyPaths.gameMusic);
		else if (Main.ifMusic && bossTime)
			Main.setMusic(MyPaths.bossMusic);
		keyboard = new Keyboard(hero, bg, Main.scene);
		runner = new Runner(this, canvas);
		runner.run();
	}

	@Override
	public void tick(double elapsedTime) {
		keyboard.keyboardController();
		update(elapsedTime);
		render();
		timer++;
		if (!bossTime && timer == 10080 || bossTime && timer == 7488) {
			timer = 0;
			if (bossTime)
				Main.setMusic(MyPaths.bossMusic);
			else
				Main.setMusic(MyPaths.gameMusic);
		}

	}

}
