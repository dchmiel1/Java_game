package graphics.objects;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import javafx.scene.image.Image;

public class Sign extends Object {

	private Image img;

	public Sign(double x, double y, World world, SignType type, GameState gameState) {
		super(x, y, world, gameState);
		try {
			if (type == SignType.easy) {
				this.img = new Image(getClass().getResource(MyPaths.easySign).toExternalForm());
			} else {
				this.img = new Image(getClass().getResource(MyPaths.hardSign).toExternalForm());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int update(double time) {
		return 0;
	}

	@Override
	public void render() {
		gc.drawImage(img, posX - GameState.getGameCamera().getXOffset(), posY - GameState.getGameCamera().getYOffset());
	}

}
