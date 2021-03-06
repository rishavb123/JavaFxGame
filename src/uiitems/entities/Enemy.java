package uiitems.entities;

import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uiitems.Grenade;
import uiitems.Laser;
import uiitems.ProgressBar;
import uiitems.UIItem;
import uiitems.entities.actions.EnemyAction;
import uiitems.entities.artificialIntelligence.EnemyAI;
import utilities.Constants;

public class Enemy extends Entity {

	public static int jumpSpeed = Constants.dim/20;
	public static int grenadeSpeedX = Constants.dim/18;
	public static int grenadeSpeedY = Constants.dim/30;
	
	protected EnemyAction currentAction;
	protected int direction;
	
	protected int rx;
	protected int ry;
	protected int rwidth;
	protected int rheight;
	
	protected boolean walking;
	
	protected ProgressBar healthBar;
	protected EnemyAI ai;
	
	public static int getJumpMaxHeight(int y)
	{
		return y - (jumpSpeed*jumpSpeed/(2*Constants.g));
	}
	
	public Enemy(int x, int y, Player target, ArrayList<UIItem> list) {
		super(x, y);
		currentAction = EnemyAction.IDLE;
		direction = 1;
		bottomTouch = false;
		healthBar = new ProgressBar(maxHealth, x, y - height/10, width, height/10, Color.RED);
		ai = new EnemyAI(this, target, list);
		setRealDimensions();
	}
	
	public void setRealDimensions()
	{
		rx = (int)(x+width*21.0/60);
		ry = (int)(y+height/40.0);
		rwidth = (int)(53.0*width/120);
		rheight = (int)(102.0*width/120);
	}
	
	public void setDrawableDimenstions()
	{
		x = (int)(rx-width*21.0/60);
		y = (int)(ry+height/40.0);
	}
	
	public String toString()
	{
		return currentAction.toString() + ", Health: " + health;
	}
	
	@Override
	public Rectangle2D getRect() {
		return new Rectangle2D(rx, ry, rwidth, rheight);
	}

	@Override
	public int getRWidth() {
		return rwidth;
	}

	@Override
	public int getRHeight() {
		return rheight;
	}

	@Override
	public int getRx() {
		return rx;
	}

	@Override
	public int getRy() {
		return ry;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public Laser laser()
	{
		currentAction = EnemyAction.LASER;
		return new Laser((direction==1)? rx+1+rwidth : rx - 1 - Constants.dim/10  , ry + rheight/10, direction*Constants.dim/10);
	}
	
	public int[] grenadeStartPositions()
	{
		int[] arr = {(direction == 1)? rx + rwidth + Grenade.swh + 1: rx - 1 - Grenade.swh, y};
		return arr;
	}
	
	public Grenade shoot()
	{
		currentAction = EnemyAction.SHOOT;
		return new Grenade((direction == 1)? rx + rwidth + Grenade.swh + 1: rx - 1 - Grenade.swh, y, Constants.dim/18*direction, -Constants.dim/30);
	}
	
	public Grenade shootDown()
	{
		currentAction = EnemyAction.SHOOT;
		return new Grenade((direction == 1)? rx + rwidth + Grenade.swh + 1: rx - 1 - Grenade.swh, y, Constants.dim/18*direction, Constants.dim/30);
	}
	
	public void jump()
	{
		dy = -jumpSpeed;
	}
	
	public void move(boolean right)
	{
		if(right && !rightTouch) {
			dx = Constants.dim/60;
			direction = 1;
			walking = true;
		}
		else if(!leftTouch){
			dx = -Constants.dim/60;
			direction = -1;
			walking = true;
		}
	}
	
	public void stop()
	{
		dx = 0;
		walking = false;
	}

	public void turn(boolean right)
	{
		if(right)
			direction = 1;
		else
			direction = -1;
	}
	
	@Override
	public void update(GraphicsContext gc)
	{
		ai.control();
		
		actions();
		move();
	
		if(currentAction.isPlayOnce() && currentAction.getSprite().hasPlayedOnce() && !currentAction.getSprite().isHolding())
		{
			currentAction.getSprite().reset();
			currentAction = EnemyAction.IDLE;
		}
		
		
		
		this.currentAction.getSprite().update();
		healthBar.setX(x);
		healthBar.setY(y - height/10);
		healthBar.setValue(health);
		healthBar.update(gc);
		if(health>0 && health<=maxHealth)
			healthBar.setColor(Color.rgb(255 - (int)((double)health/maxHealth*255), (int)((double)health/maxHealth*255), 0));
		
		setRealDimensions();
		
		draw(gc);
		
	}

	@Override
	public void draw(GraphicsContext gc)
	{
		gc.drawImage(currentAction.getSprite().getImage(), (direction==-1)? x : x + width + rwidth/3, y, -1*direction*width, height);
	}
	
}
