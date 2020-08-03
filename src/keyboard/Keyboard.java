package keyboard;

import java.util.ArrayList;
import graphics.Background;
import graphics.objects.Hero;
import graphics.objects.Object;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class Keyboard {

	private ArrayList<String> input = new ArrayList<String>();
	private Hero hero;
	private Background bg;
	private Scene scene;
	private boolean doubleJump;

	public Keyboard(Hero hero, Background bg, Scene scene) {
		this.hero = hero;
		this.bg = bg;
		this.scene = scene;
		setKeyboard();
	}

	private void setKeyboard() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
			String code = key.getCode().toString();
			if (!input.contains(code))
				input.add(code);
		});
		scene.addEventFilter(KeyEvent.KEY_RELEASED, key -> {
			String code = key.getCode().toString();
			if (code.equals("W") && doubleJump == false)
				doubleJump = true;
			input.remove(code);
		});
	}

	public void keyboardController() {
		if (!input.contains("D") && !input.contains("A")) {
			hero.setWalking(false);
			hero.setSpeedX(0);
			bg.setVector(0);
		}
		if (input.contains("W")) {
			if (hero.getDoubleJump() && doubleJump)
				hero.specialMove();
			else {
				hero.jump(1310);
				doubleJump = false;
			}
		}
		if (input.contains("D")) {
			hero.setSpeedX(300);
			hero.setWalking(true);
			hero.setDirection(Object.Direction.RIGHT);
			bg.setVector(-0.3);
		}
		if (input.contains("A")) {
			hero.setSpeedX(-300);
			hero.setWalking(true);
			hero.setDirection(Object.Direction.LEFT);
			bg.setVector(0.3);
		}
		if (input.contains("Q")) {
			hero.changeWeapon();
		}
		if (input.contains("SPACE")) {
			hero.startAttack();
		}
	}

}
