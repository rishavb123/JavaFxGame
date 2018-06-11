package uiitems.blocks;

import uiitems.entities.Entity;
import utilities.Constants;

public class BouncyBlock extends Block {

	public BouncyBlock(int x, int y) {
		super(x, y, 3, 1);
	}

	@Override
	public void hitFromAbove(Entity e) 
	{
		e.bottomTouched(y + e.getRHeight() + 1);
		e.setDy(-e.getDy());
		e.setY(e.getY() - Constants.dim/10 - 1);
		this.y -= Constants.dim/10;
	}
	
}
