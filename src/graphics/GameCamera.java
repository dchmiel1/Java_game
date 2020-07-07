package graphics;

import gameStates.GameState;
import graphics.objects.Object;
import main.Main;

public class GameCamera {

	private int xOffset;
	private int yOffset;
	private World world;
	
	public GameCamera(int xOffset, int yOffset, World world) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.world = world;
	}
	
	public void centerOnSprite(Object s) {
		if(!GameState.getBossTime()) {
			if(s.getX() > Main.WIDTH * Main.SCALE / 2 - 300)
				xOffset = (int)s.getX() - Main.WIDTH * Main.SCALE / 2 + 300;
		}
		else {
			if(s.getX() > 692.5*world.getTileSize() )
				xOffset = (int)s.getX() - Main.WIDTH * Main.SCALE / 2;
		}
		if(s.getY() < 400)
			yOffset = (int)s.getY() - 80;
		else
			yOffset = 320;
	}
	
	public void move(int x, int y) {
		xOffset += x;
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}
}
