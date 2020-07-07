package graphics.objects;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import javafx.scene.image.Image;

public class WomenHero extends Hero {

	public WomenHero(GameState gameState, double x, double y, World world) {
		super(gameState, x, y, world);
		this.hp = 100;
		this.maxHp = 100;
		this.specialDamage = 0;
		this.doubleJump = false;
		try {
			loadSpecialImgs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.width = (int) this.heroImgsR[0].getWidth();
		this.height = (int) this.heroImgsR[0].getHeight();
	}

	@Override
	protected void loadSpecialImgs() {
		this.heroImgsR[0] = new Image(getClass().getResource(MyPaths.heroF_R).toExternalForm());
		this.heroImgsL[0] = new Image(getClass().getResource(MyPaths.heroF_L).toExternalForm());
		/*
		 * imgR[1] = new Image(getClass().getResource(MyPaths.heroF_R_Hit_1).toExternalForm());
		 * imgL[1] = new Image(getClass().getResource(MyPaths.heroF_L_Hit_1).toExternalForm());
		 * imgR[2] = new Image(getClass().getResource(MyPaths.heroF_L_Hit_1).toExternalForm());
		 * imgL[2] = new Image(getClass().getResource(MyPaths.heroF_L_Hit_1).toExternalForm());
		 */
	}

	@Override
	public void specialMove() {
		if (doubleJump && airTime) {
			this.speedY = 1000;
			doubleJump = false;
		} else {
			if (doubleJump)
				doubleJump = false;
		}
	}

}
