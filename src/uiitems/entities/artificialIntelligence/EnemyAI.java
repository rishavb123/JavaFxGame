package uiitems.entities.artificialIntelligence;

import uiitems.entities.Player;
import utilities.Constants;

import java.util.ArrayList;

import uiitems.Laser;
import uiitems.UIItem;
import uiitems.entities.Enemy;

public class EnemyAI extends OneTargetAI<Enemy, Player>{
	
	private int counter;
	
	public EnemyAI(Enemy control, Player target, ArrayList<UIItem> gameObjects) {
		
		super(control, target, gameObjects);
		counter = (int)(Math.random()*30);
	}
	
	public boolean predictGrenade()
	{
		int tcx = target.getX() + target.getWidth()/2;
		int sx = control.grenadeStartPositions()[0];
		
		int deltaX = 2*control.getDirection()*Enemy.grenadeSpeedX*Enemy.grenadeSpeedY/Constants.g;
		return Math.abs((sx + deltaX)*6/Constants.dim - tcx*6/Constants.dim) <= 2;
	}
	
	@Override
	public void targetInteractions()
	{
		boolean d = false;
		
		if(target.getRx() - control.getRx() > 0 && target.getX()<=Constants.width)
			d = true;
		else if(target.getRx() - control.getRx() < 0 && target.getX()+target.getWidth()>=0)
			d = false;
		
		control.move(d);
		
		if(target.getRy() < control.getRy() - control.getHeight() && Enemy.getJumpMaxHeight(control.getRy())>0)
			control.jump();
		
		if(counter%10==0 && Math.abs(target.getRy() - control.getRy()) < Constants.dim/10)
		{
			if(Math.abs(target.getRx() - control.getRx()) > Constants.dim/15)
			{
				control.turn(target.getRx() - control.getRx() > 0);
				gameObjects.add(control.laser());
			}
		}
		
		if(predictGrenade() && counter%5 == 0) {
			gameObjects.add(control.shoot());
		}
		
		counter++;
	}

	@Override
	public void itemsInteractions() 
	{
		for(UIItem u: gameObjects)
		{
			if(u instanceof Laser)
			{
				Laser l = (Laser)u;
				int ly = l.getY();
				int lx = l.getX();
				
				if(ly < control.getRy() + control.getRHeight() + Constants.dim/10 && Math.abs(lx - control.getRx()) < Constants.dim/12 && ly>control.getRy())
				{
					control.jump();
				}
				
			}
		}
	}
	
}
