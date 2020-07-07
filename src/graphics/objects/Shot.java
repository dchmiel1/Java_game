package graphics.objects;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import javafx.scene.image.Image;

public class Shot extends Object {

	private Image img;

	public Shot(int x, int y, World world, GameState gameState) {
		super(x, y, world, gameState);
		try {
			img = new Image(getClass().getResource(MyPaths.pocisk).toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		speedX = 1000;
		width = (int) img.getWidth();
		height = (int) img.getHeight();
	}

	@Override
	public int update(double time) {
		if (dir == Direction.LEFT)
			posX = (int) (posX - speedX * time);
		else
			posX = (int) (posX + speedX * time);

		if (gone())
			return 1;
		else
			return 0;
	}
	
	@Override
	public void render() {
		if (gc != null)
			gc.drawImage(img, posX - GameState.getGameCamera().getXOffset(),
					posY - GameState.getGameCamera().getYOffset());
	}

	public boolean gone() {
		if (posX < 5 || world.ifCollision((int) posX, (int) posY, width, height))
			return true;
		else
			return false;
	}

	
}
