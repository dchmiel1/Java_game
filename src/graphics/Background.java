package graphics;

import graphics.objects.Hero;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Main;

public class Background {

	private Image image;
	private double x;
	private double dx;
	private Hero hero;
	private double moveScale;

	public Background(String s, double moveScale, Hero hero) {
		try {
			image = new Image(getClass().getResource(s).toExternalForm());
			this.moveScale = moveScale;
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.x = 0;
		this.hero = hero;
	}

	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % Main.WIDTH;
	}

	public void setVector(double dx) {
		this.dx = dx;
	}

	public void update() {
		if (hero.getBgMove())
			x += dx;
	}

	public void draw(GraphicsContext gc) {
		gc.drawImage(image, x, 0);
		if (x < 0) {
			gc.drawImage(image, x + Main.WIDTH * Main.SCALE, 0);
		}
		if (x > 0) {
			gc.drawImage(image, x - Main.WIDTH * Main.SCALE, 0);
		}
		if (x < -Main.WIDTH * Main.SCALE) {
			gc.drawImage(image, x + 2 * Main.WIDTH * Main.SCALE, 0);
		}
	}
}
