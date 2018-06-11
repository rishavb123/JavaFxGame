package uiitems.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import uiitems.UIItem;
import utilities.Constants;

public class Entity extends UIItem {

	protected int dy;
	protected int dx;
	protected int width;
	protected int height;
	protected int health;
	protected int maxHealth;
	
	protected boolean bottomTouch;
	protected boolean topTouch;
	protected boolean leftTouch;
	protected boolean rightTouch;
	
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
	public boolean isTopTouch()
	{
		return topTouch;
	}
	public boolean isLeftTouch()
	{
		return leftTouch;
	}
	public boolean isRightTouch()
	{
		return rightTouch;
	}

	public boolean isAlive()
	{
		return health > 0;
	}
	
	public void die()
	{
		health = 0;
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
	
	public void bottomTouched(int y) {
		this.bottomTouch = true;
		this.y = y;
	}
	public void topTouched(int y) {
		this.topTouch = true;
		this.y = y;
	}
	public void leftTouched(int x) {
		this.leftTouch = true;
		this.x = x;
	}
	public void rightTouched(int x) {
		this.rightTouch = true;
		this.x = x;
	}
	
	public void rightTouhed(int x)
	{
		this.rightTouch = true;
		this.x = x;
	}
	
	public void setX(int i)
	{
		x = i;
	}
	public void setY(int i)
	{
		y = i;
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
	public void setTopTouch(boolean b) {
		topTouch = b;
	}
	public void setLeftTouch(boolean b) {
		leftTouch = b;
	}	
	public void setRightTouch(boolean b) {
		rightTouch = b;
	}
}
