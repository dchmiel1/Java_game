package graphics.objects;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import javafx.scene.image.Image;

public class ManHero extends Hero {

	public ManHero(GameState gameState, double x, double y, World world) {
		super(gameState, x, y, world);
		this.hp = 150;
		this.maxHp = 150;
		this.specialDamage = 10;
		try {
			loadSpecialImgs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.width = (int) heroImgsR[0].getWidth();
		this.height = (int) heroImgsR[0].getHeight();
	}

	@Override
	protected void loadSpecialImgs() {
		this.heroImgsR[0] = new Image(getClass().getResource(MyPaths.heroM_R).toExternalForm());
		this.heroImgsL[0] = new Image(getClass().getResource(MyPaths.heroM_L).toExternalForm());
		this.heroImgsR[1] = new Image(getClass().getResource(MyPaths.heroM_R_walking_1).toExternalForm());
		this.heroImgsR[2] = new Image(getClass().getResource(MyPaths.heroM_R_walking_2).toExternalForm());
		this.heroImgsL[1] = new Image(getClass().getResource(MyPaths.heroM_L_walking_1).toExternalForm());
		this.heroImgsL[2] = new Image(getClass().getResource(MyPaths.heroM_L_walking_2).toExternalForm());
		this.heroImgsR[3] = new Image(getClass().getResource(MyPaths.heroM_R_attack_1).toExternalForm());
		this.heroImgsR[4] = new Image(getClass().getResource(MyPaths.heroM_R_attack_2).toExternalForm());
		this.heroImgsL[3] = new Image(getClass().getResource(MyPaths.heroM_L_attack_1).toExternalForm());
		this.heroImgsL[4] = new Image(getClass().getResource(MyPaths.heroM_L_attack_2).toExternalForm());
		this.heroImgsL[7] = new Image(getClass().getResource(MyPaths.heroM_L_shooting_1).toExternalForm());
		this.heroImgsR[7] = new Image(getClass().getResource(MyPaths.heroM_R_shooting_1).toExternalForm());
		this.heroImgsL[8] = new Image(getClass().getResource(MyPaths.heroM_L_shooting_2).toExternalForm());
		this.heroImgsR[8] = new Image(getClass().getResource(MyPaths.heroM_R_shooting_2).toExternalForm());
		this.heroImgsR[5] = new Image(getClass().getResource(MyPaths.heroM_R_batAttack_1).toExternalForm());
		this.heroImgsR[6] = new Image(getClass().getResource(MyPaths.heroM_R_batAttack_2).toExternalForm());
		this.heroImgsL[5] = new Image(getClass().getResource(MyPaths.heroM_L_batAttack_1).toExternalForm());
		this.heroImgsL[6] = new Image(getClass().getResource(MyPaths.heroM_L_batAttack_2).toExternalForm());
	}

	@Override
	public void specialMove() {
		doubleJump = false;
	}

}
