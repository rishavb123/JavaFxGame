import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Entity extends UIItem {

	protected int dy;
	protected int dx;
	protected int width;
	protected int height;
	protected Image image;
	
	public Entity(int x, int y) 
	{
		super.x = x;
		super.y = y;
		dy = 0;
		dx = 0;
		this.width = Game.dim/10;
		this.height = Game.dim/10;
		image = new Image("res/imgs/Entity.png");
	}
	
	public Entity(int x, int y, int dy, int dx) 
	{
		super.x = x;
		super.y = y;
		this.dy = dx;
		this.dx = dy;
		this.width = Game.dim/10;
		this.height = Game.dim/10;
		image = new Image("res/imgs/Entity.png");
	}
	
	public void move()
	{
		x+=dx;
		y+=dy;
	}
	
	@Override
	public void act(GraphicsContext gc)
	{
		actions();
		move();
		draw(gc);
	}
	
	public void actions() 
	{
		dy+=Game.g;
		if(this.y+this.height>=Game.height)
			dy*=-1;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(x, y, width, height);
		gc.drawImage(image, x, y, width, height);
	}

}
