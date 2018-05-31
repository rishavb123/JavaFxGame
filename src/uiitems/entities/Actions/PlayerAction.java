package uiitems.entities.actions;

import uiitems.entities.spritesheet.SpriteSheet;

public enum PlayerAction {
	IDLE("IDLE", 0, 0, 1, false, false, false),
	WALK("WALK", 1, 1, 4, false, false, false),
	RANGEATTACK("RANGEATTACK", 2, 5, 7, true, true, false),
	JUMP("JUMP", 3, 12, 1, true, true, false),
	FLY("FLY", 4, 11, 3, true, true, false),
	SHEILD("SHEILD", 5, 15, 4, true, true, false),
	SHORTATTACK("SHORTATTACK", 6, 19, 3, true, false, true);
		
	private String action;
	private int index;
	private int startFrame;
	private int numFrames;
	private boolean playOnce;
	private SpriteSheet sprite;
	
	private PlayerAction(String action, int index, int startFrame, int numFrames, boolean playOnce, boolean hold, boolean useLongDelay)
	{
		this.action = action;
		this.index = index;
		this.startFrame = startFrame;
		this.numFrames = numFrames;
		this.playOnce = playOnce;
		sprite = new SpriteSheet("../spritesheet/spritesheets/Player.png", startFrame, startFrame + numFrames, hold, useLongDelay);
	}
	
	public String toString()
	{
		return action+" "+sprite.toString();
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
