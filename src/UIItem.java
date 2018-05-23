import javafx.scene.canvas.GraphicsContext;
public abstract class UIItem 
{
	
	protected int x;
	protected int y;
	
	public abstract void draw(GraphicsContext gc);
	
	public int getX() {return x;}
	public int getY() {return y;}
	
}
