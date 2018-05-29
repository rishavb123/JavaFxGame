package uiitems.entities.actions;

import uiitems.entities.spritesheet.SpriteSheet;

public enum PlayerAction {
	IDLE("IDLE", 0, 0, 1, false),
	WALKING("WALKING", 1, 1, 4, false),
	LASER("LASER", 2, 5, 7, true),
	JUMP("JUMP", 3, 12, 3, true),
	SHEILD("SHEILD", 4, 15, 4, true),
	ATTACK("ATTACK", 5, 19, 3, true);
	
	private String action;
	private int index;
	private int startFrame;
	private int numFrames;
	private boolean playOnce;
	private SpriteSheet sprite;
	
	private PlayerAction(String action, int index, int startFrame, int numFrames, boolean playOnce)
	{
		this.action = action;
		this.index = index;
		this.startFrame = startFrame;
		this.numFrames = numFrames;
		this.playOnce = playOnce;
		sprite = new SpriteSheet("spritesheer/spritesheets/Player.png", startFrame, startFrame + numFrames);
	}

	public String getAction() 
	{
		return action;
	}

	public int getIndex() 
	{
		return index;
	}

	public int getStartFrame() 
	{
		return startFrame;
	}

	public int getNumFrames() 
	{
		return numFrames;
	}

	public boolean isPlayOnce() 
	{
		return playOnce;
	}

	public SpriteSheet getSprite() 
	{
		return sprite;
	}
}
