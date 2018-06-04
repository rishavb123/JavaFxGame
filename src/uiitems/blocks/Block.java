package uiitems.blocks;

import java.awt.image.BufferedImage;

import constants.Constants;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uiitems.UIItem;
import uiitems.entities.Entity;
import uiitems.entities.spritesheet.SpriteSheet;

public class Block extends UIItem {

	protected int x;
	protected int y;
	
	public static BufferedImage blockSheet;
	
	private Image image;
	
	public Block(int x, int y, int row, int col) {
		this.x = x;
		this.y = y;
		
		int w = SpriteSheet.spriteWidth;
		int h = SpriteSheet.spriteHeight;
		
		this.image = SpriteSheet.getImage(blockSheet, row*w, col*h, w, h);
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(image, x, y, Constants.dim/10, Constants.dim/10);
	}
	
	public void landOn(Entity e) 
	{
		System.out.println("landed");
		e.bottomTouched(y - e.getRHeight() + 1);
		e.setDy(0);
	}
	
	public void hit(Entity e)
	{
		if(e.getDy()>0) {
			landOn(e);
		}
	}
	
	public Rectangle2D getRect()
	{
		return new Rectangle2D(x, y, Constants.dim/10, Constants.dim/10);
	}

}
