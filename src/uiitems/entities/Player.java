package uiitems.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uiitems.entities.actions.PlayerAction;
import uiitems.ProgressBar;

public class Player extends Entity {
	
	private String name;
	private PlayerAction currentAction;	
	
	private ProgressBar healthBar;
	
	private int direction;
	
	private boolean flying;
	private boolean walking;
	private boolean jumping;
	private boolean shortAttacking;
	
	public Player(String name, int x, int y) {
		super(x, y);
		this.name = name;
		currentAction = PlayerAction.IDLE;
		health = 1000;
		direction = 1;
	}
	
	public void setHealthBar(int x, int y, int width, int height)
	{
		healthBar = new ProgressBar(1000, x, y, width, height, Color.rgb(0, 255, 0));
	}
	
	@Override
	public void draw(GraphicsContext gc)
	{
		gc.drawImage(currentAction.getSprite().getImage(), (direction==1)? x : x+width, y, direction*width, height);
	}
	
	public PlayerAction getAction()
	{
		return currentAction;
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
		if(jumping && !shortAttacking)
			currentAction = PlayerAction.JUMP;
		
		if(flying)
			fly();
		
		if(shortAttacking)
			currentAction = PlayerAction.SHORTATTACK;
		
		this.currentAction.getSprite().update();
		move();
		
		healthBar.setValue(health);
		if(health>0)
			healthBar.setColor(Color.rgb(255 - (int)(health/1000.0*255), (int)(health/1000.0*255), 0));
		
		healthBar.update(gc);
		draw(gc);
		
	}
	
	public void move(boolean right)
	{		
		if(right) {
			dx = 20;
			direction = 1;
		}
		else {
			dx = -20;
			direction = -1;
		}
		walking = true;
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
	}
	
	public void stopFly(PlayerAction pa)
	{
		currentAction = pa;
		flying = false;
	}
	
	public void shortAttack()
	{
		jumping = false;
		shortAttacking = true;
	}
	
	public void stopShortAttack()
	{
		shortAttacking = false;
		currentAction = PlayerAction.IDLE;
	}
	
	public void jump() 
	{
		if(bottomTouch) 
		{
			bottomTouch = false;
			dy = -30;
			jumping = true;
		}
	}
	
	public String getName()
	{
		return name;
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

	public ProgressBar getHealthBar() {
		return healthBar;
	}


}
