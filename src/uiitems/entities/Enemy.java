package uiitems.entities;

//import constants.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uiitems.ProgressBar;
import uiitems.entities.actions.EnemyAction;

public class Enemy extends Entity {

	protected EnemyAction currentAction;
	protected int direction;
	
	protected ProgressBar healthBar;
	
	public Enemy(int x, int y) {
		super(x, y);
		currentAction = EnemyAction.IDLE;
		direction = 1;
		bottomTouch = false;
		healthBar = new ProgressBar(maxHealth, x, y - height/10, width, height/10, Color.RED);
		
	}
	
	public String toString()
	{
		return currentAction.toString() + ", Health: " + health;
	}
	
	@Override
	public void update(GraphicsContext gc)
	{
		actions();
		move();
		healthBar.setX(x);
		healthBar.setY(y - height/10);
		healthBar.setValue(health);
		healthBar.update(gc);
		draw(gc);
	}

	@Override
	public void draw(GraphicsContext gc)
	{
		gc.drawImage(currentAction.getSprite().getImage(), (direction==1)? x : x + width/* - rwidth*/, y, direction*width, height);
	}
	
}
