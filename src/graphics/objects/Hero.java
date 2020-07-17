package graphics.objects;

import java.io.File;
import java.util.ArrayList;
import gameStates.GameState;
import graphics.GameCamera;
import graphics.MyPaths;
import graphics.World;
import jaco.mp3.player.MP3Player;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import run.Runner;

public abstract class Hero extends Object {

	// flags
	private boolean bgMove;
	private boolean isWalking;
	protected boolean airTime;
	protected boolean doubleJump;
	// hero attributies
	private int bullets;
	protected int hp;
	protected int maxHp;
	protected int specialDamage;
	// timers
	private int timeToHit;
	private int timeToAttack;
	private int timeToDoubleJump;
	private int timeToChangeWeapon;
	private int attackTime;
	private int timer;
	// used classes
	private GameCamera gameCamera;
	// weapon
	private ArrayList<WeaponType> weapons = new ArrayList<WeaponType>();
	private WeaponType activeWeapon;
	// imgs
	private ArrayList<Image> weaponIcons = new ArrayList<Image>();
	// 1 standing, 2 walking, 2 none attack, 2 bat attack, 2 shooting
	protected Image heroImgsR[] = new Image[9];
	protected Image heroImgsL[] = new Image[9];
	// 3 pistol (standing, attack1, attack2), 3 bat (standing, attack1, attack2), 3
	// sword (standing, sttack1, attack2)
	protected Image weaponsImgsR[] = new Image[9];
	protected Image weaponsImgsL[] = new Image[9];
	protected Image bullet;
	// shot, empty, damage, weaponAttack, hit
	protected File soundFile[] = new File[6];
	private MP3Player player;

	//
	protected File censoredFile[] = new File[5];
	protected int j = 0;
	//

	public Hero(GameState gameState, double x, double y, World world) {
		super(x, y, world, gameState);
		this.dir = Direction.RIGHT;
		this.airTime = false;
		this.bgMove = false;
		this.isWalking = false;
		this.bullets = 0;
		this.timer = 0;
		this.timeToChangeWeapon = 0;
		this.attackTime = 0;
		this.timeToAttack = 0;
		this.timeToDoubleJump = -1;
		this.timeToHit = 0;
		this.gameCamera = GameState.getGameCamera();
		this.activeWeapon = WeaponType.none;
		this.weapons.add(activeWeapon);
		try {
			loadImgs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void loadSpecialImgs();

	public abstract void specialMove();
	
	private void playSound(File file) {
		try {
			player = new MP3Player(file);
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void renderStatus() {
		gc.setFill(Color.BLACK);
		gc.fillRect(109, 9, maxHp + 2, 1);
		gc.fillRect(109, 30, maxHp + 2, 1);
		gc.fillRect(109, 10, 1, 20);
		gc.fillRect(maxHp + 110, 10, 1, 20);
		gc.setFill(Color.BLACK);
		gc.setFont(Font.font("", FontWeight.BOLD, 20));
		gc.fillText("Hp: " + hp, 10, 27);
		gc.fillText("Bullets: " + bullets, 10, 57); // 87
		for (int i = 0; i < bullets; i++) {
			gc.drawImage(bullet, i * 5 + 110, 40);
		}
		gc.setFill(Color.RED);
		gc.fillRect(110, 10, hp, 20);
	}

	private void updTimers() {
		if (timeToHit != 0)
			timeToHit--;
		if (timeToAttack != 0)
			timeToAttack--;
		if (timeToChangeWeapon != 0)
			timeToChangeWeapon--;
		if (attackTime != 0)
			attackTime--;
		if (timeToDoubleJump != 0 && timeToDoubleJump != -1) {
			timeToDoubleJump--;
		}
	}

	private void updPosition(double time) {
		double newY;
		double newX;

		// newY position
		if (airTime) {
			newY = posY - speedY * time + 8000 * time * time;
			speedY = speedY - 3500 * time;
			if (speedY <= -1000)
				speedY = -1000;
		} else
			newY = posY;

		if (timeToDoubleJump == 0 && airTime) {
			doubleJump = true;
			timeToDoubleJump = -1;
		}

		// newX position
		newX = posX + speedX * time;

		// left block
		if (newX < 5) {
			newX = posX;
		}
		// high block
		if (newY < 0) {
			speedY = 0;
			newY = posY;
		}
		// check if standing
		if (!world.ifStanding((int) posX, (int) posY, width, height)) {
			airTime = true;
		}
		// check if collision with world
		if (!world.ifCollision((int) posX, (int) newY, width, height)) {
			posY = newY;
			if (!world.ifCollision((int) newX, (int) newY, width, height)) // if no Y and X collisions
				posX = newX;
			else { // if X collision
				if (newX - posX > 0)
					setRightCollision((int) newX);
				if (newX - posX < 0)
					setLeftCollision();
			}
		} else {
			if (newY - posY > 0) {
				setDownCollision((int) newY);
				airTime = false;
			}
			if (newY - posY < 0) {
				setUpCollision();
				airTime = true;
			}
			if (!world.ifCollision((int) newX, (int) posY, width, height)) // if Y collision
				posX = newX;
			else { // if X and Y collisions
				if (newX - posX > 0)
					setRightCollision((int) newX);
				if (newX - posX < 0)
					setLeftCollision();
			}
		}

		// game over
		if (posY > Main.HEIGHT * Main.SCALE + world.getYMapOff() * world.getTileSize()) {
			Runner.setGameOver(true);
		}
		// bg move
		if (posX == newX)
			bgMove = true;
		else
			bgMove = false;
	}

	private void updItems() {
		for (int i = 0; i < gameState.getWeapons().size(); i++) {
			if (this.ifCollisionWithObject(gameState.getWeapons().get(i))) {
				if (Main.ifSounds)
					if (!Main.ifBad)
						playSound(soundFile[5]);
					else
						playSound(censoredFile[3]);
				if (activeWeapon == WeaponType.none)
					activeWeapon = gameState.getWeapons().get(i).getWeaponType();
				weapons.add(gameState.getWeapons().get(i).getWeaponType());
				weaponIcons.add(gameState.getWeapons().get(i).getWeaponIcon());
				gameState.getWeapons().remove(i);
			}
		}
		for (int i = 0; i < gameState.getItems().size(); i++) {
			if (this.ifCollisionWithObject(gameState.getItems().get(i))) {
				if (Main.ifSounds)
					if (!Main.ifBad)
						playSound(soundFile[5]);
					else
						playSound(censoredFile[3]);
				if (gameState.getItems().get(i).getItemType() == ItemType.bullets) {
					bullets += 10;
				}
				if (gameState.getItems().get(i).getItemType() == ItemType.potion) {
					if (hp > maxHp - 50)
						hp = maxHp;
					else
						hp += 50;
				}

				gameState.getItems().remove(i);
			}
		}
	}

	private void updHp() {
		for (int i = 0; i < gameState.getMonsters().size(); i++) {
			if (this.ifCollisionWithObject(gameState.getMonsters().get(i))) {
				hp -= gameState.getMonsters().get(i).hpWhenCollision;
				if (Main.ifSounds)
					if (!Main.ifBad)
						playSound(soundFile[2]);
					else {
						playSound(censoredFile[j]);
						j++;
						if (j > 2)
							j = 0;
					}
				timeToHit = 90;
			}
		}
		for (int i = 0; i < gameState.getArrows().size(); i++) {
			if (this.ifCollisionWithObject(gameState.getArrows().get(i))) {
				gameState.getArrows().remove(i);
				hp -= 40;
				if (Main.ifSounds)
					if (!Main.ifBad)
						playSound(soundFile[2]);
					else {
						playSound(censoredFile[j]);
						j++;
						if (j > 2)
							j = 0;
					}
				timeToHit = 90;
			}
		}
		if (this.ifCollisionWithBoss()) {
			hp -= gameState.getBoss().hpWhenCollision;
			if (Main.ifSounds)
				if (!Main.ifBad)
					playSound(soundFile[2]);
				else {
					playSound(censoredFile[j]);
					j++;
					if (j > 2)
						j = 0;
				}
			timeToHit = 90;
		}
	}

	private void attack() {
		int j = 0;
		int damage = 0;
		int xOff = 0;
		switch (activeWeapon) {
		case pistol:
			if (bullets > 0) {
				int shotX = (int) posX + 20;
				int shotY = (int) posY + 70;
				Shot shot = new Shot(shotX, shotY, world, gameState);
				shot.setDirection(dir);
				gameState.getShots().add(shot);
				bullets--;
				if (Main.ifSounds)
					playSound(soundFile[0]);

			} else {
				if (Main.ifSounds)
					playSound(soundFile[1]);
			}
			break;
		default:
			if (Main.ifSounds)
				playSound(soundFile[3]);
			switch (activeWeapon) {
			case bat:
				j = 6;
				damage = 20 + specialDamage;
				xOff = -59;
				break;
			case sword:
				j = 6;
				damage = 50 + specialDamage;
				xOff = -59;
				break;
			case none:
				j = 3;
				damage = 10 + specialDamage;
				xOff = -43;
				break;
			}
			for (int i = 0; i < gameState.getMonsters().size(); i++) {
				if (dir == Direction.RIGHT) {
					if (this.ifRightOrLeftCollisionWithObject((int) (2 * posX + heroImgsR[j].getWidth()) / 2,
							(int) heroImgsR[6].getWidth() / 2 + 5, gameState.getMonsters().get(i))) {
						gameState.getMonsters().get(i).ifHit(damage);
						if (Main.ifSounds)
							if (!Main.ifBad)
								playSound(soundFile[4]);
							else {
								playSound(censoredFile[4]);
							}
					}
				} else {
					if (this.ifRightOrLeftCollisionWithObject((int) posX + xOff - 5, (int) heroImgsR[j].getWidth() / 2,
							gameState.getMonsters().get(i))) {
						gameState.getMonsters().get(i).ifHit(damage);
						if (Main.ifSounds)
							if (!Main.ifBad)
								playSound(soundFile[4]);
							else {
								playSound(censoredFile[4]);
							}
					}
				}
			}
			if (dir == Direction.RIGHT) {
				if (this.ifBossHit((int) (2 * posX + heroImgsR[j].getWidth()) / 2,
						(int) heroImgsR[6].getWidth() / 2 + 5)) {
					gameState.getBoss().ifHit(damage);
					if (Main.ifSounds)
						if (!Main.ifBad)
							playSound(soundFile[4]);
						else {
							playSound(censoredFile[4]);
						}
				}
			} else {
				if (this.ifBossHit((int) posX + xOff - 5, (int) heroImgsR[j].getWidth() / 2)) {
					gameState.getBoss().ifHit(damage);
					if (Main.ifSounds)
						if (!Main.ifBad)
							playSound(soundFile[4]);
						else {
							playSound(censoredFile[4]);
						}
				}
			}
			break;
		}
		timeToAttack = 20;
	}

	private void loadImgs() {
		this.weaponsImgsR[3] = new Image(getClass().getResource(MyPaths.batR).toExternalForm());
		this.weaponsImgsL[3] = new Image(getClass().getResource(MyPaths.batL).toExternalForm());
		this.weaponsImgsR[0] = new Image(getClass().getResource(MyPaths.pistolR).toExternalForm());
		this.weaponsImgsL[0] = new Image(getClass().getResource(MyPaths.pistolL).toExternalForm());
		this.weaponsImgsL[1] = new Image(getClass().getResource(MyPaths.pistolShootingL_1).toExternalForm());
		this.weaponsImgsR[1] = new Image(getClass().getResource(MyPaths.pistolShootingR_1).toExternalForm());
		this.weaponsImgsR[2] = new Image(getClass().getResource(MyPaths.pistolShootingR_2).toExternalForm());
		this.weaponsImgsL[2] = new Image(getClass().getResource(MyPaths.pistolShootingL_2).toExternalForm());
		this.weaponsImgsL[4] = new Image(getClass().getResource(MyPaths.batAttackL).toExternalForm());
		this.weaponsImgsR[4] = new Image(getClass().getResource(MyPaths.batAttackR).toExternalForm());
		this.weaponsImgsR[5] = new Image(getClass().getResource(MyPaths.batAttackR_2).toExternalForm());
		this.weaponsImgsL[5] = new Image(getClass().getResource(MyPaths.batAttackL_2).toExternalForm());
		this.weaponsImgsR[6] = new Image(getClass().getResource(MyPaths.swordR).toExternalForm());
		this.weaponsImgsL[6] = new Image(getClass().getResource(MyPaths.swordL).toExternalForm());
		this.weaponsImgsL[7] = new Image(getClass().getResource(MyPaths.swordAttackL).toExternalForm());
		this.weaponsImgsR[7] = new Image(getClass().getResource(MyPaths.swordAttackR).toExternalForm());
		this.weaponsImgsR[8] = new Image(getClass().getResource(MyPaths.swordAttackR_2).toExternalForm());
		this.weaponsImgsL[8] = new Image(getClass().getResource(MyPaths.swordAttackL_2).toExternalForm());
		this.bullet = new Image(getClass().getResource(MyPaths.bullet).toExternalForm());
		this.soundFile[0] = new File(MyPaths.shot);
		this.soundFile[1] = new File(MyPaths.empty);
		this.soundFile[2] = new File(MyPaths.damage);
		this.soundFile[3] = new File(MyPaths.weaponAttack);
		this.soundFile[4] = new File(MyPaths.hit);
		this.soundFile[5] = new File(MyPaths.collect);
		this.censoredFile[0] = new File(MyPaths.wilku);
		this.censoredFile[1] = new File(MyPaths.madi);
		this.censoredFile[2] = new File(MyPaths.bieniu);
		this.censoredFile[3] = new File(MyPaths.miska);
		this.censoredFile[4] = new File(MyPaths.bieniuHit);
	}

	@Override
	public int update(double time) {
		updPosition(time);
		if (timeToHit == 0)
			updHp();
		updItems();
		updTimers();
		gameCamera.centerOnSprite(this);

		if (hp <= 0)
			return 1;
		return 0;
	}

	@Override
	public void render() {
		if (this.heroImgsR != null) {
			Image heroToDraw = null;
			Image weaponToDraw = null;
			int iWalking = 0;
			int iHit = 0;
			int xWeaponOff = 0;
			int yWeaponOff = 0;
			int xHeroOff = 0;
			int yHeroOff = 0;

			if (attackTime == 5)
				attack();

			if (timeToHit > 0)
				iHit = timer % 24 / 12;
			if (isWalking)
				if (timer % 24 < 6)
					iWalking = 1;
				else if (timer % 24 < 12)
					iWalking = 0;
				else if (timer % 24 < 18)
					iWalking = 2;
				else
					iWalking = 0;
			if (iHit == 1) {
				heroToDraw = null;
				weaponToDraw = null;
			} else if (attackTime > 0) {
				int i = attackTime / 5;
				switch (activeWeapon) {
				case bat:
					if (dir == Direction.LEFT) {
						heroToDraw = this.heroImgsL[6 - i];
						xHeroOff = -59;
						if (i == 1) {
							weaponToDraw = weaponsImgsL[4];
							xWeaponOff = -10;
							yWeaponOff = 20;
						} else {
							weaponToDraw = weaponsImgsL[5];
							yWeaponOff = 113;
							xWeaponOff = -70;
						}
					} else {
						heroToDraw = this.heroImgsR[6 - i];
						if (i == 1) {
							weaponToDraw = weaponsImgsR[4];
							xWeaponOff = 10;
							yWeaponOff = 20;
						} else {
							weaponToDraw = weaponsImgsR[5];
							xWeaponOff = 40;
							yWeaponOff = 113;
						}
					}
					break;
				case sword:
					if (dir == Direction.LEFT) {
						heroToDraw = this.heroImgsL[6 - i];
						xHeroOff = -59;
						if (i == 1) {
							weaponToDraw = weaponsImgsL[7];
							xWeaponOff = -10;
							yWeaponOff = 20;
						} else {
							weaponToDraw = weaponsImgsL[8];
							yWeaponOff = 113;
							xWeaponOff = -70;
						}
					} else {
						heroToDraw = this.heroImgsR[6 - i];
						if (i == 1) {
							weaponToDraw = weaponsImgsR[7];
							xWeaponOff = 10;
							yWeaponOff = 20;
						} else {
							weaponToDraw = weaponsImgsR[8];
							xWeaponOff = 40;
							yWeaponOff = 113;
						}
					}
					break;
				case none:
					if (dir == Direction.LEFT) {
						xHeroOff = -43;
						heroToDraw = this.heroImgsL[3 + i];
					} else
						heroToDraw = this.heroImgsR[3 + i];
					break;
				case pistol:
					if (dir == Direction.LEFT) {
						heroToDraw = this.heroImgsL[8 - i];
						if (i == 1) {
							xHeroOff = -5;
							weaponToDraw = weaponsImgsL[1];
							yWeaponOff = 70;
							xWeaponOff = -20;
						} else {
							weaponToDraw = weaponsImgsL[2];
							xWeaponOff = 8;
							yWeaponOff = 70;
						}

					} else {
						heroToDraw = this.heroImgsR[8 - i];
						if (i == 1) {
							weaponToDraw = weaponsImgsR[1];
							yWeaponOff = 70;
							xWeaponOff = 48;
						} else {
							weaponToDraw = weaponsImgsR[2];
							xWeaponOff = 20;
							yWeaponOff = 68;
						}
					}
					break;
				}

			} else {
				switch (activeWeapon) {
				case bat:
					if (dir == Direction.LEFT) {
						weaponToDraw = weaponsImgsL[3];
						xWeaponOff = -24;
						yWeaponOff = 113;
					} else {
						weaponToDraw = weaponsImgsR[3];
						xWeaponOff = 0;
						yWeaponOff = 113;
					}
					break;
				case pistol:
					if (dir == Direction.LEFT) {
						weaponToDraw = weaponsImgsL[0];
						xWeaponOff = 35;
						yWeaponOff = 118;
					} else {
						weaponToDraw = weaponsImgsR[0];
						xWeaponOff = 2;
						yWeaponOff = 118;
					}
					break;
				case sword:
					if (dir == Direction.LEFT) {
						weaponToDraw = weaponsImgsL[6];
						xWeaponOff = -24;
						yWeaponOff = 113;
					} else {
						weaponToDraw = weaponsImgsR[6];
						xWeaponOff = 0;
						yWeaponOff = 113;
					}
					break;
				case none:
					weaponToDraw = null;
					break;
				}
				if (dir == Direction.LEFT)
					heroToDraw = this.heroImgsL[iWalking];
				else
					heroToDraw = this.heroImgsR[iWalking];
			}

			timer++;
			gc.drawImage(heroToDraw, posX - GameState.getGameCamera().getXOffset() + xHeroOff,
					posY + yHeroOff - GameState.getGameCamera().getYOffset());
			gc.drawImage(weaponToDraw, posX - GameState.getGameCamera().getXOffset() + xWeaponOff,
					posY + yWeaponOff - GameState.getGameCamera().getYOffset());
			if (weaponIcons != null)
				for (int i = 0; i < weaponIcons.size(); i++) {
					gc.drawImage(weaponIcons.get(i), 300 + i * 30, 10);
				}
			this.renderStatus();
		}
	}

	public void jump(double jumpPower) {
		if (!airTime) {
			this.timeToDoubleJump = 15;
			this.speedY = jumpPower;
			this.airTime = true;
		}
	}

	public void startAttack() {
		if (timeToAttack == 0) {
			this.attackTime = 10;
			this.timeToAttack = 10;
		}
	}

	public void changeWeapon() {
		int i = 0;
		if (timeToChangeWeapon == 0) {
			while (weapons.get(i) != activeWeapon) {
				i++;
			}
			if (weapons.size() == i + 1)
				activeWeapon = weapons.get(0);
			else
				activeWeapon = weapons.get(i + 1);
			timeToChangeWeapon = 10;
		}
	}

	public void setTimeToAttack(double time) {
		this.timeToAttack = (int) time;
	}

	public double getTimeToShoot() {
		return timeToAttack;
	}

	public void setWalking(boolean walking) {
		this.isWalking = walking;
	}

	public boolean getBgMove() {
		return bgMove;
	}

	public boolean getDoubleJump() {
		return doubleJump;
	}

}
