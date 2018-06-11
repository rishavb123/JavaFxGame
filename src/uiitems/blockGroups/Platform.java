package uiitems.blockGroups;

import java.util.ArrayList;

import uiitems.blocks.Block;
import uiitems.blocks.BouncyBlock;
import uiitems.blocks.GhostBlock;
import utilities.Constants;

public class Platform extends BlockGroup {

	protected ArrayList<Block> blockList;
	protected int x;
	protected int y;
	protected int numBlocks;
	
	public Platform(int x, int y, int numBlocks, String attribute) {
		this.x = x;
		this.y = y;
		this.numBlocks = numBlocks;
		blockList = new ArrayList<>();
		createBlockList(attribute);
	}
	
	public Platform(int x, int y, int numBlocks) {
		this.x = x;
		this.y = y;
		this.numBlocks = numBlocks;
		blockList = new ArrayList<>();
		createDefaultBlockList();
	}
	
	public void createBlockList(String attribute)
	{
		switch(attribute)
		{
			case "bouncy":
				for(int i=0;i<numBlocks;i++)
					blockList.add(new BouncyBlock(x+i*Constants.dim/10, y));
				break;
			case "ghost":
				for(int i=0;i<numBlocks;i++)
					blockList.add(new GhostBlock(x+i*Constants.dim/10, y));
				break;
			default:
				createDefaultBlockList();
				break;
		}
	}
	
	public void createDefaultBlockList()
	{
		for(int i=0;i<numBlocks;i++)
			blockList.add(new Block(x+i*Constants.dim/10, y, 0, 5));
	}

	@Override
	public ArrayList<Block> getBlockList() {
		return blockList;
	}

}
