package graphics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import gameStates.GameState;
import graphics.objects.Object.Direction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Main;

public class World {

	private int tileSize;
	private int numRows;
	private int numCols;
	private GraphicsContext gc;
	private char map[][];
	private Tile[][] tiles;
	private Image img1 = new Image(getClass().getResource(MyPaths.grassLeft).toExternalForm());
	private Image img2 = new Image(getClass().getResource(MyPaths.grassMiddle).toExternalForm());
	private Image img3 = new Image(getClass().getResource(MyPaths.grassRight).toExternalForm());
	private Image img4 = new Image(getClass().getResource(MyPaths.grassSingle).toExternalForm());
	private Image img5 = new Image(getClass().getResource(MyPaths.dirt).toExternalForm());
	private int yMapOff = 8;

	public World(int tileSize, GraphicsContext gc) {
		this.gc = gc;
		this.tileSize = tileSize;
		numRows = Main.HEIGHT * Main.SCALE / tileSize + 10;
	}

	public void loadTiles() {
		try {
			for (int i = 0; i < numRows; i++)
				for (int j = 0; j < numCols; j++) {
					tiles[i][j] = new Tile(map[i][j] - 48, this);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadMap() {
		try {
			char a;
			var br = new BufferedReader(
					new InputStreamReader(getClass().getResourceAsStream(MyPaths.mapLevel1)));
			numCols = Integer.parseInt(br.readLine());
			map = new char[numRows][numCols];
			tiles = new Tile[numRows][numCols];

			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numCols; col++) {
					a = (char) br.read();
					if (a > 47)
						map[row][col] = a;
					else if (col != numCols - 1)
						col--;
					else
						row--;
				}
			}
		} catch (Exception e) {
			System.exit(0);
			e.printStackTrace();
		}
	}

	public void render(int x) {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (col * tileSize < 1250 + x && col * tileSize + 1000 > x)
					if (map[row][col] > 48) {
						tiles[row][col].render(gc, col, row, GameState.getGameCamera().getXOffset(),
								GameState.getGameCamera().getYOffset());
					}

			}
		}
	}

	public boolean ifCollision(int px, int py, int w, int h) {
		int yTilesToCheck = 0;
		int x = px / tileSize;
		int y = py / tileSize;

		int pixelX = x * tileSize;
		int pixelY = y * tileSize;
		if (h % tileSize == 0 && py == pixelY)
			yTilesToCheck = h / tileSize;
		else if (h % tileSize == 0)
			yTilesToCheck = h / tileSize + 1;
		else
			yTilesToCheck = h / tileSize + 2;
		int xTilesToCheck = w / tileSize + 2;
		for (int i = 0; i < yTilesToCheck; i++)
			for (int j = 0; j < xTilesToCheck; j++) {
				if (y + i >= numRows || x + j >= numCols)
					return false;
				if (tiles[y + i][x + j].getCollision() && px + w > pixelX + j * tileSize
						&& px < pixelX + j * tileSize + tileSize && py + h > pixelY + i * tileSize
						&& py < pixelY + i * tileSize + tileSize)
					return true;
			}
		return false;

	}

	public boolean ifStanding(int px, int py, int w, int h) {
		int x = px / tileSize;
		int y = (py + h) / tileSize;
		int pixelX = x * tileSize;
		int tilesToCheck;
		if (w % 40 == 0 && px == pixelX)
			tilesToCheck = w / tileSize;
		else if (px + w < pixelX + tileSize * 2)
			tilesToCheck = w / tileSize + 1;
		else
			tilesToCheck = w / tileSize + 2;

		for (int i = 0; i < tilesToCheck; i++) {
			if (y >= numRows)
				return true;
			if (tiles[y][x + i].getCollision())
				return true;
		}
		return false;
	}

	public boolean ifRotate(int px, int py, int w, int h, Direction dir) {
		int x = px / tileSize;
		int y = (py + h) / tileSize;
		int i = 1;
		if (w > tileSize)
			i = 2;
		if (dir == Direction.LEFT) {
			if (x < 0 || x > numCols - 1 || !tiles[y][x].getCollision())
				return true;
		} else if (x + i < 0 || x + i > numCols - 1 || !tiles[y][x + i].getCollision())
			return true;

		return false;
	}

	public void bossTime() {
		var bossTile = new Tile(5, this);
		for (int j = 683; j > 675; j--)
			for (int i = 23; i > 9; i--) {
				map[i][j] = 53;
				tiles[i][j] = bossTile;
			}
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getYMapOff() {
		return yMapOff;
	}

	public Image getImg1() {
		return img1;
	}

	public Image getImg2() {
		return img2;
	}

	public Image getImg3() {
		return img3;
	}

	public Image getImg4() {
		return img4;
	}

	public Image getImg5() {
		return img5;
	}

}
