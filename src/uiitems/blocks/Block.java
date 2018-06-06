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
		
	public Block(int x, int y, int col, int row) {
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
	
	public void hitFromAbove(Entity e) 
	{
		e.bottomTouched(y - e.getRHeight() + 1);
		e.setDy(0);
	}
	
	public void hitFromUnder(Entity e)
	{
		e.setDy(0);
		e.topTouched(y+Constants.dim/10);
	}
	
	public void hitFromLeft(Entity e)
	{
		e.setDx(0);
		e.leftTouched(x - e.getRWidth() + 1);
	}
	
	public void hitFromRight(Entity e)
	{
		e.setDx(0);
		e.rightTouched(x + Constants.dim/10 -1);
	}
	
	public final void hit(Entity e)
	{		
		if(e.getRx() + e.getRWidth() < x + e.getDx() + 1 && e.getRy() + e.getRWidth() > y)
			hitFromLeft(e);
		else if(e.getRx() > x + Constants.dim/10 + e.getDx() && e.getRy() + e.getRWidth() > y)
			hitFromRight(e);
		else if(e.getDy()>0 && e.getRy() <= y + Constants.dim/10 - e.getRHeight()) 
			hitFromAbove(e);
		else if(e.getDy()<0 && e.getRy() > y)
			hitFromUnder(e);
	}
	
	public Rectangle2D getRect()
	{
		return new Rectangle2D(x, y, Constants.dim/10, Constants.dim/10);
	}

}
