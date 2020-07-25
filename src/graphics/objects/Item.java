package graphics.objects;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import javafx.scene.image.Image;

public class Item extends Object {
	protected Image img;
	protected ItemType itemType;
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	public Item(int x, int y, World world, ItemType itemType, GameState gameState) {
		super(x, y, world, gameState);
		try {
			if (itemType == ItemType.bullets) {
				img = new Image(getClass().getResource(MyPaths.bullets).toExternalForm());
			} else
				img = new Image(getClass().getResource(MyPaths.potion).toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (world != null)
			this.gc = world.getGc();
		this.width = (int) img.getWidth();
		this.height = (int) img.getHeight();
		this.itemType = itemType;
	}

	@Override
	public int update(double time) {
		return 0;
	}

	public void render() {
		if (gc != null)
			gc.drawImage(img, posX - GameState.getGameCamera().getXOffset(),
					posY - GameState.getGameCamera().getYOffset());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ItemType getItemType() {
		return itemType;
	}
}
