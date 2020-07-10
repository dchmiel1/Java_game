package graphics.objects.monsters;

import java.io.File;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import graphics.objects.Hero;
import graphics.objects.Monster;
//import jaco.mp3.player.MP3Player;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Main;

public class BossTroll extends Monster {

	private Image imgR[] = new Image[2];
	private Image imgL[] = new Image[2];
	private Image attackR[] = new Image[3];
	private Image attackL[] = new Image[3];
	private int timeToAttack;
	private boolean attack;
	private Hero hero;
	private MP3Player player;

	public BossTroll(double x, double y, World world, Hero hero, GameState gameState) {
		super(x, y, world, gameState);
		this.hero = hero;
		this.dir = Direction.LEFT;
		this.speedX = -80;
		this.hpWhenCollision = 50;
		this.timeToAttack = 200;
		this.attack = false;
		this.hp = 1500;
		this.maxHp = 1500;
		this.points = 500;
		try {
			imgR[0] = new Image(getClass().getResource(MyPaths.trollR_1).toExternalForm());
			imgR[1] = new Image(getClass().getResource(MyPaths.trollR_2).toExternalForm());
			imgL[0] = new Image(getClass().getResource(MyPaths.trollL_1).toExternalForm());
			imgL[1] = new Image(getClass().getResource(MyPaths.trollL_2).toExternalForm());
			attackR[0] = new Image(getClass().getResource(MyPaths.troll_attackR_1).toExternalForm());
			attackR[1] = new Image(getClass().getResource(MyPaths.troll_attackR_3).toExternalForm());
			attackL[0] = new Image(getClass().getResource(MyPaths.troll_attackL_1).toExternalForm());
			attackL[1] = new Image(getClass().getResource(MyPaths.troll_attackL_3).toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.width = (int) imgR[0].getWidth();
		this.height = (int) imgR[0].getHeight();
	}
	
	private void attack() {
		if (Main.ifSounds) {
			/*try {
				player = new MP3Player(new File(MyPaths.trollHit));
				player.play();
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
		if (dir == Direction.LEFT && hero.getX() > posX - 170 && hero.getX() < posX && hero.getY() > posY - 100
				&& hero.getY() < posY + height) {
			gameState.getMonsters().add(new TrollHit(posX - 170, posY - 100, world, gameState));
		}
		if (dir == Direction.RIGHT && hero.getX() > posX + width && hero.getX() < posX + width + 200
				&& hero.getY() > posY - 100 && hero.getY() < posY + height) {
			gameState.getMonsters().add(new TrollHit(posX + 170, posY - 100, world, gameState));
		}
	}

	private void renderBossHp() {
		gc.setFill(Color.BLACK);
		gc.setFont(new Font("", 30));
		gc.fillText("Boss HP: ", 80, 125);
		gc.fillRect(200, 100, maxHp * 2 / 3 + 2, 1);
		gc.fillRect(200, 131, maxHp * 2 / 3 + 2, 1);
		gc.fillRect(200, 101, 1, 30);
		gc.fillRect(200 + 1 + maxHp * 2 / 3, 101, 1, 30);
		gc.setFill(Color.RED);
		gc.fillRect(200 + 1, 101, hp * 2 / 3, 30);
	}

	@Override
	public int update(double time) {
		timeToAttack--;
		if (timeToAttack == 0) {
			attack = true;
			timeToAttack = 300;
		}
		if (monsterUpdate(time) == 1)
			return 1;
		else
			return 0;
	}

	@Override
	public void render() {
		if (imgR != null) {
			Image imgToDraw = null;
			int yOff = 0;
			int xOff = 0;
			if (attack) {
				speedX = 0;
				if (timeToAttack > 260) {
					yOff = 15;
					if (dir == Direction.LEFT)
						imgToDraw = attackL[0];
					else {
						xOff = 20;
						imgToDraw = attackR[0];
					}
				} else if (timeToAttack > 230) {
					if (timeToAttack == 258)
						attack();
					if (dir == Direction.LEFT) {
						xOff = 100;
						imgToDraw = attackL[1];
					} else {
						xOff = -120;
						imgToDraw = attackR[1];
					}
				} else if (timeToAttack == 230) {
					attack = false;
					int speed = 80;
					if (hp <= 500)
						speed = 150;
					if (dir == Direction.LEFT)
						speedX = -speed;
					else
						speedX = speed;
				}
			} else {
				int i = timer % 20 / 10;
				if (dir == Direction.LEFT)
					imgToDraw = imgL[i];
				else
					imgToDraw = imgR[i];
			}

			if (iHit > 0) {
				imgToDraw = null;

			}
			renderBossHp();
			gc.drawImage(imgToDraw, posX - GameState.getGameCamera().getXOffset() - xOff,
					posY - GameState.getGameCamera().getYOffset() - yOff);
		}
	}

}
