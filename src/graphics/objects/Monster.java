package graphics.objects;

import gameStates.GameState;
import graphics.World;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Monster extends Object {

	protected int hpWhenCollision;
	protected int timeToHit;
	protected int timer;
	protected int hp;
	protected int maxHp;
	protected int iHit;
	protected int points;

	public Monster(double x, double y, World world, GameState gameState) {
		super(x, y, world, gameState);
		this.timeToHit = 0;
		this.timer = 0;
		this.iHit = 0;
	}

	@Override
	public abstract int update(double time);

	@Override
	public abstract void render();

	protected void renderHp() {
		gc.setFill(Color.BLACK);
		gc.setFont(new Font("", 20));
		gc.setFill(Color.BLACK);
		gc.fillRect(posX + (width / 2) - (maxHp / 2) - GameState.getGameCamera().getXOffset(),
				posY - 25 - GameState.getGameCamera().getYOffset(), maxHp + 2, 1);
		gc.fillRect(posX + (width / 2) - (maxHp / 2) - GameState.getGameCamera().getXOffset(),
				posY - 9 - GameState.getGameCamera().getYOffset(), maxHp + 2, 1);
		gc.fillRect(posX + (width / 2) - (maxHp / 2) - GameState.getGameCamera().getXOffset(),
				posY - 24 - GameState.getGameCamera().getYOffset(), 1, 15);
		gc.fillRect(posX + (width / 2) - (maxHp / 2) + 1 + maxHp - GameState.getGameCamera().getXOffset(),
				posY - 24 - GameState.getGameCamera().getYOffset(), 1, 15);
		gc.setFill(Color.RED);
		gc.fillRect(posX + (width / 2) - (maxHp / 2) + 1 - GameState.getGameCamera().getXOffset(),
				posY - 24 - GameState.getGameCamera().getYOffset(), hp, 15);
	}

	protected void ifShooted() {
		for (int i = 0; i < gameState.getShots().size(); i++) {
			if (this.ifCollisionWithObject(gameState.getShots().get(i))) {
				hp -= 50;
				timeToHit = 20;
				iHit = 5;
				gameState.getShots().remove(i);
			}
		}
	}

	protected void ifHit(int damage) {
		hp -= damage;
		iHit = 5;
		timeToHit = 20;
	}

	protected int monsterUpdate(double time) {
		double newX = speedX * time + posX;

		if (world.ifRotate((int) newX, (int) posY, width, height, dir)
				|| world.ifCollision((int) newX, (int) posY, width, height)) {
			if (dir == Direction.RIGHT)
				dir = Direction.LEFT;
			else
				dir = Direction.RIGHT;
			speedX = -speedX;
			newX = speedX * time + posX;
		}
		posX = newX;

		ifShooted();
		if (timeToHit > 0)
			timeToHit--;
		if (iHit > 0)
			iHit--;

		if (timer < 10000)
			timer++;
		else
			timer = 0;

		if (hp <= 0) {
			GameState.setScore(GameState.getScore() + points);
			return 1;
		} else
			return 0;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHp() {
		return hp;
	}

	public void setTimeToHit(int time) {
		this.timeToHit = time;
	}

}
