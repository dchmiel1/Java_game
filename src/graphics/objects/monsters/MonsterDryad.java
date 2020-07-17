package graphics.objects.monsters;

import java.io.File;
import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import graphics.objects.Arrow;
import graphics.objects.Hero;
import graphics.objects.Monster;
import jaco.mp3.player.MP3Player;
import javafx.scene.image.Image;
import main.Main;

public class MonsterDryad extends Monster {

	private Image imgR[] = new Image[2];
	private Image imgL[] = new Image[2];
	private Image arrowsR[] = new Image[2];
	private Image arrowsL[] = new Image[2];
	private Hero hero;
	private MP3Player player;

	public MonsterDryad(double x, double y, World world, Hero hero, GameState gameState) {
		super(x, y, world, gameState);
		this.dir = Direction.LEFT;
		this.speedX = 0;
		this.hpWhenCollision = 30;
		this.hero = hero;
		this.hp = 100;
		this.maxHp = 100;
		this.points = 100;
		try {
			imgR[0] = new Image(getClass().getResource(MyPaths.dryadR_1).toExternalForm());
			imgR[1] = new Image(getClass().getResource(MyPaths.dryadR_2).toExternalForm());
			imgL[0] = new Image(getClass().getResource(MyPaths.dryadL_1).toExternalForm());
			imgL[1] = new Image(getClass().getResource(MyPaths.dryadL_2).toExternalForm());
			arrowsL[0] = new Image(getClass().getResource(MyPaths.arrowL_0).toExternalForm());
			arrowsR[0] = new Image(getClass().getResource(MyPaths.arrowR_0).toExternalForm());
			arrowsL[1] = null;
			arrowsR[1] = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.width = (int) imgR[0].getWidth();
		this.height = (int) imgR[0].getHeight();

	}

	private void attack() {
		double arrowX;
		double arrowY;
		if (dir == Direction.LEFT) {
			arrowX = posX;
			arrowY = posY;
		} else {
			arrowX = posX + 50;
			arrowY = posY;
		}
		var arrow = new Arrow(arrowX, arrowY, world, (int) hero.getX(), (int) hero.getY(), hero.getWidth(), dir,
				gameState);
		gameState.getArrows().add(arrow);
	}

	@Override
	public int update(double time) {
		ifShooted();
		if (iHit > 0)
			iHit--;

		if (hero.getX() > posX)
			dir = Direction.RIGHT;
		else
			dir = Direction.LEFT;
		if (hp <= 0) {
			GameState.setScore(GameState.getScore() + points);
			return 1;
		} else
			return 0;
	}

	@Override
	public void render() {
		if (imgR != null) {
			Image dryadToDraw = null;
			Image arrowToDraw = null;
			int xArrowOff = 0;
			int yArrowOff = 0;
			int i = 0;

			if (timer % 150 == 112) {
				attack();
				var musicPath = new File(MyPaths.bow);
				if (Main.ifSounds)
					try {
						player = new MP3Player(musicPath);
						player.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}

			if (timer % 150 < 113)
				i = 0;
			else
				i = 1;

			if (dir == Direction.RIGHT) {
				dryadToDraw = imgR[i];
				arrowToDraw = arrowsR[i];
				xArrowOff = 32;
				yArrowOff = 10;
			} else {
				dryadToDraw = imgL[i];
				arrowToDraw = arrowsL[i];
				xArrowOff = 5;
				yArrowOff = 10;
			}

			if (iHit > 0) {
				dryadToDraw = null;
			}
			renderHp();

			if (timer < 10000)
				timer++;
			else
				timer = 0;

			gc.drawImage(dryadToDraw, posX - GameState.getGameCamera().getXOffset(),
					posY - GameState.getGameCamera().getYOffset());
			gc.drawImage(arrowToDraw, posX - GameState.getGameCamera().getXOffset() + xArrowOff,
					posY + yArrowOff - GameState.getGameCamera().getYOffset());
		}
	}
}
