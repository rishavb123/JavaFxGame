package uiitems.blockGroups;

import java.util.ArrayList;

import uiitems.blocks.Block;
import utilities.Constants;

public class Floor extends BlockGroup {
	
	protected ArrayList<Block> blockList;

	
	public Floor() 
	{
		blockList = new ArrayList<>();
		for(int x=0;x<20;x++)
		{
			blockList.add(new Block(Constants.dim*x/10, Constants.dim*9/10, 0, 3));
		}
	}
	
	public Floor(int y) 
	{
		blockList = new ArrayList<>();
		for(int x=0;x<20;x++)
		{
			blockList.add(new Block(Constants.dim*x/10, Constants.dim*y/10, 0, 3));
		}
	}
	
	public Floor(int row, int col) 
	{
		blockList = new ArrayList<>();
		for(int x=0;x<20;x++)
		{
			blockList.add(new Block(Constants.dim*x/10, Constants.dim*9/10, row, col));
		}
	}
	
	public Floor(int width, int startX, int startY, int row, int col)
	{
		blockList = new ArrayList<>();
		for(int x=0;x<width;x++)
		{
			blockList.add(new Block(Constants.dim*(x+startX)/10, Constants.dim*startY/10, row, col));
		}
	}
	
	public Floor(int y, int row, int col) 
	{
		blockList = new ArrayList<>();
		for(int x=0;x<20;x++)
		{
			blockList.add(new Block(Constants.dim*x/10, Constants.dim*y/10, row, col));
		}
	}

	@Override
	public ArrayList<Block> getBlockList() {
		return blockList;
	}

}
