package uiitems.entities;

import javafx.scene.canvas.GraphicsContext;

import uiitems.entities.Actions.PlayerAction;

public class Player extends Entity {
	
	private String name;
	private PlayerAction currentAction;
	
	
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
	
	public String getName()
	{
		return name;
	}

}
