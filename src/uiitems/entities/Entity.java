package uiitems.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import uiitems.UIItem;
import constants.Constants;

public class Entity extends UIItem {

	protected int dy;
	protected int dx;
	protected int width;
	protected int height;
	protected int health;
	protected boolean bottomTouch;
	
	public Entity(int x, int y) 
	{
		super.x = x;
		super.y = y;
		dy = 0;
		dx = 0;
		this.width = Constants.dim/5;
		this.height = Constants.dim/5;
		health = 100;
	}
	
	public Entity(int x, int y, int dy, int dx) 
	{
		super.x = x;
		super.y = y;
		this.dy = dx;
		this.dx = dy;
		this.width = Constants.dim/10;
		this.height = Constants.dim/10;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public boolean isBottomTouch()
	{
		return bottomTouch;
	}
	
	public void move()
	{
		x+=dx;
		y+=dy;
	}
	
	public void actions() 
	{
		gravity();
		if(this.y+this.height>=Constants.height && dy>0)
		{
			this.y = Constants.height - this.height;
			dy=0;
			bottomTouch = true;
		}
		
		if(this.y<=0) {
			dy = Constants.g*10;
			health-=2*Constants.healthLoss;
			y = 0;
		}
	}

	public void gravity()
	{
		dy+=Constants.g;
	}
	
	@Override
	public void update(GraphicsContext gc)
	{
		actions();
		move();
		draw(gc);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(x, y, width, height);
	}

}
