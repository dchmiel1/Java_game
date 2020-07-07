package graphics.objects.monsters;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import graphics.objects.Monster;
import javafx.scene.image.Image;

public class MonsterWorm extends Monster {

	private Image imgR;
	private Image imgL;

	public MonsterWorm(double x, double y, World world, GameState gameState) {
		super(x, y, world, gameState);
		this.dir = Direction.LEFT;
		this.speedX = -150;
		this.hpWhenCollision = 20;
		this.hp = 50;
		this.maxHp = 50;
		this.points = 50;
		try {
			imgR = new Image(getClass().getResource(MyPaths.wormR).toExternalForm());
			imgL = new Image(getClass().getResource(MyPaths.wormL).toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.width = (int) imgR.getWidth();
		this.height = (int) imgR.getHeight();
	}

	@Override
	public int update(double time) {
		if (monsterUpdate(time) == 1)
			return 1;
		else
			return 0;
	}

	@Override
	public void render() {
		Image imgToDraw = null;
		if (dir == Direction.LEFT)
			imgToDraw = imgL;
		else
			imgToDraw = imgR;

		if (iHit > 0) {
			imgToDraw = null;
		}
		renderHp();
		gc.drawImage(imgToDraw, posX - GameState.getGameCamera().getXOffset(),
				posY - GameState.getGameCamera().getYOffset());
	}
}
