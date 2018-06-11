package uiitems;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utilities.Constants;

public class Grenade extends UIItem {

	private int dx;
	private int dy;
	
	public static int swh = Constants.dim/20;
	public int wh;
	
	private Color c;
	
	private long startExplode;
	private boolean present;
	
	public Grenade(int x, int y, int dx, int dy) {
		super.x = x;
		super.y = y;
		wh = swh;
		this.dx = dx;
		this.dy = dy;
		c = Color.GREENYELLOW;
		present = true;
	}
	
	public void gravity() {
		dy+=Constants.g;
	}
	
	public void move() {
		x+=dx;
		y+=dy;
	}
	
	public Circle getCircle() {
		return new Circle(x, y, wh);
	}
	
	public boolean intersects(Rectangle2D r)
	{
		return getCircle().intersects(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
	}
	
	public void explode()
	{
		wh*=3;
		c = Color.RED;
		startExplode = System.nanoTime();
	}

	@Override
	public void update(GraphicsContext gc) {
		if(startExplode == 0) 
		{
			gravity();
			move();
		}
		else
		{
			if(System.nanoTime() - startExplode >600000000)
				present = false;
		}
		if(present)
			draw(gc);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(c);
		gc.fillOval(x - wh/2, y - wh/2, wh, wh);
	}
	
	public boolean isExploded()
	{
		return startExplode != 0;
	}
	
	public boolean isPresent()
	{
		return present;
	}

}
