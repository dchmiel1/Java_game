package graphics.objects;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import javafx.scene.image.Image;
import main.Main;

public class Arrow extends Object{
	
	private Image img[] = new Image[10];
	private int heroX;
	private int heroY;
	private double timeToHit;
	private double g;
	private double timer;

	public Arrow(double x, double y, World world, int heroX, int heroY, int heroW, Direction dir, GameState gameState) {
		super(x, y, world, gameState);
		this.dir = dir;
		try {
			switch(this.dir) {
			case LEFT:
				img[0] = new Image(getClass().getResource(MyPaths.arrowL_0).toExternalForm());
				img[1] = new Image(getClass().getResource(MyPaths.arrowL_1).toExternalForm());
				img[2] = new Image(getClass().getResource(MyPaths.arrowL_2).toExternalForm());
				img[3] = new Image(getClass().getResource(MyPaths.arrowL_3).toExternalForm());
				img[4] = new Image(getClass().getResource(MyPaths.arrowL_4).toExternalForm());
				img[5] = new Image(getClass().getResource(MyPaths.arrowL_5).toExternalForm());
				break;
			case RIGHT:
				img[0] = new Image(getClass().getResource(MyPaths.arrowR_0).toExternalForm());
				img[1] = new Image(getClass().getResource(MyPaths.arrowR_1).toExternalForm());
				img[2] = new Image(getClass().getResource(MyPaths.arrowR_2).toExternalForm());
				img[3] = new Image(getClass().getResource(MyPaths.arrowR_3).toExternalForm());
				img[4] = new Image(getClass().getResource(MyPaths.arrowR_4).toExternalForm());
				img[5] = new Image(getClass().getResource(MyPaths.arrowR_5).toExternalForm());
				break;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.timer = 0;
		this.heroY = heroY;
		this.heroX = heroX + heroW*1/2;
		this.timeToHit = 1.2;
		this.g = 1500;
		this.dir = dir;
		calcSpeed();
		this.width = (int)img[0].getWidth();
		this.height = (int)img[0].getHeight();
	}
	
	private void calcSpeed() {
		this.speedX = (heroX - posX)/timeToHit;
		this.speedY = -(posY - this.heroY +timeToHit*timeToHit/2*g)/timeToHit;
	}

	@Override
	public int update(double time) {
		posY = posY + speedY * time + 1/2*g*time;
		posX = posX + speedX * time;
		speedY = speedY + g * time;
		
		if(world.ifCollision((int)posX, (int)posY, width, height) || posY > Main.SCALE* Main.HEIGHT + world.getYMapOff()*world.getTileSize() || posX < 0 || posY < 0)
			return 1;
		timer++;
		return 0;
	}

	@Override
	public void render() {
		Image arrowToDraw = null;
		int i =(int) (timer/((timeToHit*60)/6));
		if(i > 5)
			i = 5;
		arrowToDraw = img[i];
		gc.drawImage(arrowToDraw, posX - GameState.getGameCamera().getXOffset(), posY - GameState.getGameCamera().getYOffset());
		
	}

}
