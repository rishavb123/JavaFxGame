package uiitems.entities;

import constants.Constants;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uiitems.entities.actions.PlayerAction;
import uiitems.Laser;
import uiitems.ProgressBar;

public class Player extends Entity {
		
	private String name;
	private PlayerAction currentAction;	
	
	private int rx;
	private int ry;
	private int rwidth;
	private int rheight;
	
	private ProgressBar healthBar;
	
	private int direction;
	
	private boolean flying;
	private boolean walking;
	private boolean jumping;
	private boolean shortAttacking;
	private boolean sheilding;
	private boolean longAttacking;
	
	private int longAttackPointer;
	private double longAttackPointerSpeed;
	
	public Player(String name, int x, int y) {
		super(x, y);
		this.name = name;
		currentAction = PlayerAction.IDLE;
		maxHealth = 1000;
		health = maxHealth;
		direction = 1;
		longAttackPointer = x+width;

		setRealDimensions();
	}
	
	private void setRealDimensions() 
	{
		rx = (int)(x+width/4.0);
		ry = (int)(y+height*3.0/120);
		rwidth = (int)(width/4.0);
		rheight = (int)(height*88.0/120);		
	}
	
	private void setDrawableDimensions()
	{
		x = (int)(rx - width/4.0);
		y = (int)(ry-height*3.0/120);
	}
	
	public void setHealthBar(int x, int y, int width, int height)
	{
		healthBar = new ProgressBar(1000, x, y, width, height, Color.rgb(0, 255, 0));
	}
	
	@Override
	public void draw(GraphicsContext gc)
	{
		if(longAttacking) {
			gc.setFill(Color.rgb(0,255,255,0.5));
			gc.fillRect(longAttackPointer, 0, Constants.dim/20, Constants.dim);
		}
		gc.drawImage(currentAction.getSprite().getImage(), (direction==1)? x : x + width - rwidth, y, direction*width, height);
	}
	
	@Override
	public void actions() 
	{
		
		if(!bottomTouch)
			gravity();
		if(this.ry+this.rheight>=Constants.height && dy>0)
		{
			this.ry = Constants.height - this.rheight;
			dy=0;
			bottomTouch = true;
		}
		
		if(this.ry<=0) {
			dy = Constants.g*10;
			health-=2*Constants.healthLoss;
			ry = 0;
		}
		
		setDrawableDimensions();
		
	}
	
	public PlayerAction getAction()
	{
		return currentAction;
	}
	
	@Override
	public void bottomTouched(int yy) {
		this.bottomTouch = true;
		this.ry = yy;
		setDrawableDimensions();
	}
	
	@Override
	public void topTouched(int yy) {
		this.topTouch = true;
		this.ry = yy;
		setDrawableDimensions();
	}
	
	@Override
	public void leftTouched(int x) {
		this.leftTouch = true;
		this.rx = x;
		setDrawableDimensions();
	}
	
	@Override
	public void rightTouched(int x)
	{
		this.rightTouch = true;
		this.rx = x;
		setDrawableDimensions();
	}
	
	@Override
	public void update(GraphicsContext gc)
	{
		
		actions();
		
		if(currentAction.isPlayOnce() && currentAction.getSprite().hasPlayedOnce() && !currentAction.getSprite().isHolding())
		{
			currentAction.getSprite().reset();
			currentAction = PlayerAction.IDLE;
		}
		
		if(bottomTouch && currentAction == PlayerAction.JUMP)
		{
			currentAction = PlayerAction.IDLE;
		}
		
		if(walking && bottomTouch && !shortAttacking)
			currentAction = PlayerAction.WALK;
		
		if(bottomTouch)
			jumping = false;
		
		if(!topTouch)
		{
			if(jumping && !shortAttacking)
				currentAction = PlayerAction.JUMP;
			
			if(flying)
				fly();
		}
		
		if(shortAttacking)
			shortAttack();
		
		if(sheilding)
			sheild();
		
		if(longAttacking) 
			longAttack();
		else
			longAttackPointer = (direction==-1)? x - Constants.dim/20  : x + width;
		
		this.currentAction.getSprite().update();
		move();
		
		if(!(flying||walking||jumping||shortAttacking||sheilding||dx!=0||dy!=0) && health<maxHealth)
		{
			if((int)(Math.random()*2) == 0)
				health++;
		}
		
		healthBar.setValue(health);
		if(health>0 && health<=maxHealth)
			healthBar.setColor(Color.rgb(255 - (int)((double)health/maxHealth*255), (int)((double)health/maxHealth*255), 0));
		
		setRealDimensions();
		
		healthBar.update(gc);
		draw(gc);
		
	}
	
	public void move(boolean right)
	{		
		if(right && !rightTouch) {
			dx = 20;
			direction = 1;
			walking = true;
		}
		else if(!leftTouch){
			dx = -20;
			direction = -1;
			walking = true;
		}
	}
	
	public void stop()
	{
		dx = 0;
		walking = false;
		currentAction = PlayerAction.IDLE;
	}
	
	public void startFly()
	{
		flying = true;
	}
	
	public void fly() 
	{
		if(dy>=0)
			dy-=4;
		jumping = false;
		dy-=4;
		currentAction = PlayerAction.FLY;
		bottomTouch = false;
		health--;
	}
	
	public void stopFly()
	{
		currentAction = PlayerAction.IDLE;
		flying = false;
	}
	
	public void startShortAttack()
	{
		jumping = false;
		shortAttacking = true;
	}
	
	public void shortAttack()
	{
		currentAction = PlayerAction.SHORTATTACK;
		sheilding = false;
		
	}
	
	public void stopShortAttack()
	{
		shortAttacking = false;
		currentAction = PlayerAction.IDLE;
	}
	
	public void startSheild()
	{
		sheilding = true;
	}
	
	public void sheild()
	{
		dx = 0;
		currentAction = PlayerAction.SHEILD;
		health--;
	}
	
	public void stopSheild()
	{
		PlayerAction.SHEILD.getSprite().reset();
		sheilding = false;
		currentAction = PlayerAction.IDLE;
	}
	
	public void startLongAttack()
	{
		longAttacking = true;
	}
	
	public void longAttack()
	{
		longAttackPointerSpeed+=0.5;
		currentAction = PlayerAction.LONGATTACK;
		longAttackPointer+=(int)(Constants.dim/1200.0*longAttackPointerSpeed*direction);
		dx = 0;
	}
	
	public Laser stopLongAttack()
	{
		longAttackPointerSpeed = 0;
		PlayerAction.LONGATTACK.getSprite().reset();
		longAttacking = false;
		currentAction = PlayerAction.LONGATTACKDOWN;
		return new Laser(longAttackPointer, 0, 0, Constants.dim/6);
	}
	
	public void jump() 
	{
		if(bottomTouch) 
		{
			bottomTouch = false;
			dy = -25;
			jumping = true;
		}
	}
	
	@Override
	public void damage(int i)
	{
		if(sheilding)
			health-=i/2;
		else
			health-=i;
	}
	
	@Override
	public Rectangle2D getRect()
	{
		return new Rectangle2D(rx, ry, rwidth, rheight);
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getRx()
	{
		return rx;
	}
	
	public int getRy()
	{
		return ry;
	}
	
	public int getRWidth()
	{
		return rwidth;
	}
	
	public int getRHeight()
	{
		return rheight;
	}
	
	public boolean isFlying()
	{
		return flying;
	}
	
	public boolean isWalking()
	{
		return walking;
	}
	
	public boolean isJumping()
	{
		return jumping;
	}
	
	public boolean isShortAttacking()
	{
		return shortAttacking;
	}
	
	public boolean isSheilding()
	{
		return sheilding;
	}

	public boolean isLongAttacking() 
	{
		return longAttacking;
	}

	public ProgressBar getHealthBar() {
		return healthBar;
	}


}
