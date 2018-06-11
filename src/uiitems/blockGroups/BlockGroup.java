package uiitems.blockGroups;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import uiitems.UIItem;
import uiitems.blocks.Block;

public abstract class BlockGroup extends UIItem{
		
	public abstract ArrayList<Block> getBlockList();
	
	@Override
	public void update(GraphicsContext gc)
	{
		for(Block b: getBlockList())
		{
			b.update(gc);
		}
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		for(Block b: getBlockList())
		{
			b.draw(gc);
		}
	}
	
}
