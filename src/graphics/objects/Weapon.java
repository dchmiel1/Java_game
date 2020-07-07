package graphics.objects;

import gameStates.GameState;
import graphics.MyPaths;
import graphics.World;
import javafx.scene.image.Image;

public class Weapon extends Object {

	private WeaponType weaponType;
	private Image img;
	private Image imgIcon;

	public Weapon(int x, int y, World world, WeaponType weaponType, GameState gameState) {
		super(x, y, world, gameState);
		this.weaponType = weaponType;
		try {
			switch (weaponType) {
			case pistol:
				this.img = new Image(getClass().getResource(MyPaths.pistolToCollect).toExternalForm());
				this.imgIcon = new Image(getClass().getResource(MyPaths.pistolIcon).toExternalForm());
				break;
			case bat:
				this.img = new Image(getClass().getResource(MyPaths.batToCollect).toExternalForm());
				this.imgIcon = new Image(getClass().getResource(MyPaths.batIcon).toExternalForm());
				break;
			case sword:
				this.img = new Image(getClass().getResource(MyPaths.swordToCollect).toExternalForm());
				this.imgIcon = new Image(getClass().getResource(MyPaths.swordIcon).toExternalForm());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (img != null) {
			this.width = (int) img.getWidth();
			this.height = (int) img.getHeight();
		}
	}

	@Override
	public int update(double time) {
		return 0;
	}

	@Override
	public void render() {
		if (gc != null)
			gc.drawImage(img, posX - GameState.getGameCamera().getXOffset(),
					posY - GameState.getGameCamera().getYOffset());
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public Image getWeaponIcon() {
		return imgIcon;
	}

}
