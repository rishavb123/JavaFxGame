package uiitems.entities;

import javafx.geometry.Rectangle2D;
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
	protected int maxHealth;
	protected boolean bottomTouch;
	
	public Entity(int x, int y) 
	{
		super.x = x;
		super.y = y;
		dy = 0;
		dx = 0;
		this.width = Constants.dim/5;
		this.height = Constants.dim/5;
		maxHealth = 100;
		health = maxHealth;
	}
	
	public Rectangle2D getRect()
	{
		return new Rectangle2D(x, y, width, height);
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
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getRWidth()
	{
		return width;
	}
	
	public int getRHeight()
	{
		return height;
	}
	
	public int getRx()
	{
		return x;
	}
	
	public int getRy()
	{
		return y;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getDx()
	{
		return dx;
	}
	
	public int getDy()
	{
		return dy;
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
		if(!bottomTouch)
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
	
	public void damage(int i)
	{
		health -= i;
		if(i > maxHealth/4)
		{
			dy = -20;
		}
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

	public void bottomTouched(int yy) {
		this.bottomTouch = true;
		this.y = yy;
	}

	public void setDy(int i) {
		dy = i;
	}
	public void setDx(int i) {
		dx = i;
	}

	public void setBottomTouch(boolean b) {
		bottomTouch = b;
	}

}
