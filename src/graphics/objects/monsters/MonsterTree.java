package graphics.objects.monsters;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import graphics.objects.Monster;
import javafx.scene.image.Image;

public class MonsterTree extends Monster {

	private Image imgR[] = new Image[2];
	private Image imgL[] = new Image[2];

	public MonsterTree(double x, double y, World world, GameState gameState) {
		super(x, y, world, gameState);
		this.dir = Direction.LEFT;
		this.speedX = -75;
		this.hpWhenCollision = 50;
		this.hp = 200;
		this.maxHp = 200;
		this.points = 80;
		try {
			imgR[0] = new Image(getClass().getResource(MyPaths.treeMonsterR_1).toExternalForm());
			imgL[0] = new Image(getClass().getResource(MyPaths.treeMonsterL_1).toExternalForm());
			imgR[1] = new Image(getClass().getResource(MyPaths.treeMonsterR_2).toExternalForm());
			imgL[1] = new Image(getClass().getResource(MyPaths.treeMonsterL_2).toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.width = (int) imgR[0].getWidth();
		this.height = (int) imgR[0].getHeight();
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
		if (imgR != null) {
			Image imgToDraw = null;
			int i = timer % 20 / 10;
			if (dir == Direction.LEFT)
				imgToDraw = imgL[i];
			else
				imgToDraw = imgR[i];

			if (iHit > 0) {
				imgToDraw = null;
			}
			this.renderHp();

			gc.drawImage(imgToDraw, posX - GameState.getGameCamera().getXOffset(),
					posY - GameState.getGameCamera().getYOffset());
		}
	}
}
