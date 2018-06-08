package uiitems;

import constants.Constants;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Laser extends UIItem {

	private int dx;
	private int dy;
	private int width;
	private int height;
	
	public Laser(int x, int y, int dx, int dy) {
		super.x = x;
		super.y = y;
		this.dx = dx;
		this.dy = dy;
		width = (Math.abs(dx)<Math.abs(dy)) ? Constants.dim/20 : Constants.dim/10;
		height = (Math.abs(dx)<Math.abs(dy)) ? Constants.dim/10 : Constants.dim/20;
	}
		
	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Laser(int x, int y, int dx) {
		super.x = x;
		super.y = y;
		this.dx = dx;
		this.dy = 0;
		width = Constants.dim/10;
		height = Constants.dim/20;
	}
	
	public Rectangle2D getRect()
	{
		return new Rectangle2D(x, y, width, height);
	}
	
	public void move()
	{
		x+=dx;
		y+=dy;
	}
	
	@Override
	public void update(GraphicsContext gc)
	{
		
		draw(gc);
		move();
		
	}

	@Override
	public void draw(GraphicsContext gc) 
	{
		gc.setFill(Color.rgb(0,255,255));
		gc.fillRect(x, y, width, height);
	}

}
