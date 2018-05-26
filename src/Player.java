//import javafx.scene.canvas.GraphicsContext;

public class Player extends Entity {

	private String name;
	
	public Player(String name, int x, int y) 
	{
		super(x, y);
		this.name = name;
	}
	
	public Player(String name, int x, int y, int dx, int dy) 
	{
		super(x, y, dx, dy);
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

//	@Override
//	public void draw(GraphicsContext gc) {
//		//BufferedImage stuff
//	}

}
