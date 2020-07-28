package graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {

	private Image image;
	private boolean collision;
	private int type;
	private World world;

	public Tile(int type, World world) {
		this.type = type;
		this.world = world;
		switch (type) {
		case 0:
			image = null;
			collision = false;
			break;
		case 1:
			this.image = world.getImg1();
			collision = true;
			break;
		case 2:
			image = world.getImg2();
			collision = true;
			break;
		case 3:
			image = world.getImg3();
			collision = true;
			break;
		case 4:
			image = world.getImg4();
			collision = true;
			break;
		case 5:
			image = world.getImg5();
			collision = true;
			break;
		default:
			image = null;
			collision = false;
		}
	}

	public void render(GraphicsContext gc, int x, int y, int xOffset, int yOffset) {
		gc.drawImage(image, x * world.getTileSize() - xOffset, y * world.getTileSize() - yOffset);
	}

	public Image getImage() {
		return image;
	}

	public int getType() {
		return type;
	}

	public boolean getCollision() {
		return collision;
	}
}
