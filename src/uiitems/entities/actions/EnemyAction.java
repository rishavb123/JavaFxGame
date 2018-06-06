package uiitems.entities.actions;

import uiitems.entities.spritesheet.SpriteSheet;

public enum EnemyAction {
	
	IDLE("IDLE", 0, 0, 2, false, false, false),
	LASER("LASER", 1, 2, 2, true, false, true),
	SHOOT("SHOOT", 2, 4, 2, true, false, true);

	private String action;
	private int index;
	private int startFrame;
	private int numFrames;
	private boolean playOnce;
	private SpriteSheet sprite;
	
	private EnemyAction(String action, int index, int startFrame, int numFrames, boolean playOnce, boolean hold, boolean useLongDelay)
	{
		this.action = action;
		this.index = index;
		this.startFrame = startFrame;
		this.numFrames = numFrames;
		this.playOnce = playOnce;
		sprite = new SpriteSheet("../spritesheet/spritesheets/Enemy.png", startFrame, startFrame + numFrames, hold, useLongDelay);
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
