package uiitems.entities;

import javafx.scene.canvas.GraphicsContext;

import uiitems.entities.actions.PlayerAction;

public class Player extends Entity {
	
	private String name;
	private PlayerAction currentAction;	
	
	private boolean flying;
	
	public Player(String name, int x, int y) {
		super(x, y);
		this.name = name;
		currentAction = PlayerAction.IDLE;
	}
	
	@Override
	public void draw(GraphicsContext gc)
	{
		gc.drawImage(currentAction.getSprite().getImage(), x, y, width, height);
	}
	
	@Override
	public void update(GraphicsContext gc)
	{
		actions();
		
		currentAction.getSprite().update();
		if(currentAction.isPlayOnce() && currentAction.getSprite().hasPlayedOnce())
		{
			currentAction.getSprite().reset();
			currentAction = PlayerAction.IDLE;
		}
		
		if(bottomTouch && currentAction == PlayerAction.JUMP)
		{
			currentAction = PlayerAction.IDLE;
		}
		
		if(dx != 0 && bottomTouch)
			currentAction = PlayerAction.WALK;
		if(flying)
			fly();
		move();
		draw(gc);
	}
	
	public void move(boolean right)
	{
		if(right)
			dx = 20;
		else
			dx = -20;
	}
	
	public void stop()
	{
		dx = 0;
	}
	
	public void jump() 
	{
		dx = 0;
		if(bottomTouch) 
		{
			bottomTouch = false;
			currentAction = PlayerAction.JUMP;
			dy = -30;
		}
	}
	
	public String getName()
	{
		return name;
	}

	public void fly() 
	{
		if(dy>0)
			dy-=2;
		dy-=2;
		currentAction = PlayerAction.FLY;
		flying = true;
	}
	
	public void stopFly()
	{
		currentAction = PlayerAction.IDLE;
		flying = false;
	}

}
