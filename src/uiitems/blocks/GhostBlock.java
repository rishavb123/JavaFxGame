package uiitems.blocks;

import uiitems.entities.Entity;

public class GhostBlock extends Block {

	public GhostBlock(int x, int y) {
		super(x, y, 1, 1);
	}

	@Override
	public void hitFromUnder(Entity e)
	{
		
	}
	
	@Override
	public void hitFromAbove(Entity e) 
	{
		if((int)(Math.random()*5)==0)
			super.hitFromAbove(e);
	}
	
}
