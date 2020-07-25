package graphics.objects;

import gameStates.GameState;
import graphics.World;
import javafx.scene.canvas.GraphicsContext;

public abstract class Object {
	protected GraphicsContext gc;
	protected World world;
	protected double posX;
	protected double posY;
	protected double speedX;
	protected double speedY;
	protected int width;
	protected int height;
	protected Direction dir;
	protected GameState gameState;

	public enum Direction {
		LEFT, RIGHT
	};

	public enum WeaponType {
		bat, sword, pistol, none
	};

	public enum ItemType {
		bullets, potion
	};

	public enum SignType {
		easy, hard
	};

	public Object(double x, double y, World world, GameState gameState) {
		this.posX = x;
		this.posY = y;
		this.world = world;
		this.gc = world.getGc();
		this.speedX = 0;
		this.speedY = 0;
		this.gameState = gameState;
	}

	public abstract int update(double time);

	public abstract void render();

	protected void setLeftCollision() {
		speedX = 0;
		int newPosX = (int) posX / world.getTileSize();
		posX = newPosX * world.getTileSize();
	}

	protected void setRightCollision(int badPos) {
		speedX = 0;
		int xTile;
		xTile = (badPos + width) / world.getTileSize();
		posX = xTile * world.getTileSize() - width;
	}

	protected void setUpCollision() {
		int newPosY = (int) posY / world.getTileSize();
		posY = newPosY * world.getTileSize();
		speedY = 0;
	}

	protected void setDownCollision(int badPos) {
		int yTile = 0;
		yTile = (badPos + height) / world.getTileSize();
		posY = yTile * world.getTileSize() - height;
	}

	protected boolean ifCollisionWithObject(Object second) {
		if (posX + width >= second.posX && posX <= second.posX + second.width && posY + height >= second.posY
				&& posY <= second.posY + second.height)
			return true;
		else
			return false;
	}

	protected boolean ifRightOrLeftCollisionWithObject(int posX, int width, Object second) {
		if (posX + width >= second.posX && posX <= second.posX + second.width && posY + height >= second.posY
				&& posY <= second.posY + second.height)
			return true;
		else
			return false;
	}

	protected boolean ifBossHit(int posX, int width) {
		double bx = gameState.getBoss().getX();
		double by = gameState.getBoss().getY();
		double bw = gameState.getBoss().getWidth();
		double bh = gameState.getBoss().getHeight();
		Direction bDir = gameState.getBoss().getDirection();
		if ((posX + width >= bx && posX <= bx + bw / 2 && posY + height >= by && posY <= by + bh
				&& bDir == Direction.LEFT)
				|| (posX + width >= bx + bw / 2 && posX <= bx + bw && posY + height >= by && posY <= by + bh
						&& bDir == Direction.RIGHT))
			return true;
		else
			return false;
	}

	protected boolean ifCollisionWithBoss() {
		double bx = gameState.getBoss().getX();
		double by = gameState.getBoss().getY();
		double bw = gameState.getBoss().getWidth();
		double bh = gameState.getBoss().getHeight();
		Direction bDir = gameState.getBoss().getDirection();
		if (posX + width >= bx && posX <= bx + bw && posY + height >= by + 150 && posY <= by + bh)
			return true;
		if (bDir == Direction.LEFT && posX + width >= bx && posX <= bx + bw - 100 && posY + height >= by
				&& posY <= by + bh)
			return true;
		if (bDir == Direction.RIGHT && posX + width >= bx + 100 && posX <= bx + bw && posY + height >= by
				&& posY <= by + bh)
			return true;
		return false;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getX() {
		return posX;
	}

	public double getY() {
		return posY;
	}

	public void setX(double x) {
		this.posX = x;
	}

	public void setY(double y) {
		this.posY = y;
	}

	public void setDirection(Direction dir) {
		this.dir = dir;
	}

	public Direction getDirection() {
		return dir;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
