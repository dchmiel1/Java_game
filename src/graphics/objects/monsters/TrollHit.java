package graphics.objects.monsters;

import gameStates.GameState;
import graphics.World;
import graphics.objects.Monster;

public class TrollHit extends Monster {

	public TrollHit(double x, double y, World world, GameState gameState) {
		super(x, y, world, gameState);
		this.hpWhenCollision = 100;
		this.speedX = 0;
		this.width = 200;
		this.height = 200;
		this.hp = 10;
	}

	@Override
	public int update(double time) {
		hp--;
		if (hp == 0)
			return 1;
		else
			return 0;
	}

	@Override
	public void render() {
	}

}
