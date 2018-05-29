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
	
	public Entity(int x, int y) 
	{
		super.x = x;
		super.y = y;
		dy = 0;
		dx = 0;
		this.width = Constants.dim/10;
		this.height = Constants.dim/10;
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
	
	public void move()
	{
		x+=dx;
		y+=dy;
	}
	
	@Override
	public void update(GraphicsContext gc)
	{
		actions();
		move();
		draw(gc);
	}
	
	public void actions() 
	{
		dy+=Constants.g;
		if(this.y+this.height>=Constants.height)
			dy*=-1;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(x, y, width, height);
	}

}