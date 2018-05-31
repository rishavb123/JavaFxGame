package uiitems.entities;

import javafx.scene.canvas.GraphicsContext;

import uiitems.entities.actions.PlayerAction;

public class Player extends Entity {
	
	private String name;
	private PlayerAction currentAction;	
	
	private boolean flying;
	private boolean walking;
	private boolean jumping;
	private boolean shortAttacking;
	
	public Player(String name, int x, int y) {
		super(x, y);
		this.name = name;
		currentAction = PlayerAction.IDLE;
		health = 1000;
	}
	
	@Override
	public void draw(GraphicsContext gc)
	{
		gc.drawImage(currentAction.getSprite().getImage(), x, y, width, height);
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
			if(currentAction == PlayerAction.SHORTATTACK)
				shortAttacking = false;
			
			currentAction.getSprite().reset();
			currentAction = PlayerAction.IDLE;
		}
		
		if(bottomTouch && currentAction == PlayerAction.JUMP)
		{
			currentAction = PlayerAction.IDLE;
		}
		
		if(walking && bottomTouch)
			currentAction = PlayerAction.WALK;
		
		if(bottomTouch)
			jumping = false;
		if(jumping)
			currentAction = PlayerAction.JUMP;
		
		if(flying)
			fly();
		
		if(shortAttacking)
			currentAction = PlayerAction.SHORTATTACK;
		
		this.currentAction.getSprite().update();
		move();
		draw(gc);
		
	}
	
	public void move(boolean right)
	{
		if(right)
			dx = 20;
		else
			dx = -20;
		walking = true;
	}
	
	public void stop()
	{
		dx = 0;
		walking = false;
		currentAction = PlayerAction.IDLE;
	}
	
	public void fly() 
	{
		if(dy>0)
			dy-=2;
		jumping = false;
		dy-=2;
		currentAction = PlayerAction.FLY;
		flying = true;
		bottomTouch = false;
	}
	
	public void stopFly(PlayerAction pa)
	{
		currentAction = pa;
		flying = false;
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
	
	public void shortAttack()
	{
		jumping = false;
		flying = false;
		shortAttacking = true;
		
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


}
