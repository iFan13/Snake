package Snake;

import java.awt.Color;
import java.awt.Graphics;

public class Food {
	
	private int xPos, yPos, width, height;
	
	public Food (int xPos, int yPos, int tileSize) {
		this.xPos = xPos;
		this.yPos= yPos;
		width = tileSize;
		height = tileSize;
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(xPos * width, yPos * height, width, height);
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}
