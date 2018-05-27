import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Entity {

	private String name;
	private SpriteSheet spriteSheet;
	
	public Player(String name, int x, int y) 
	{
		super(x, y);
		this.name = name;
		image = new Image("res/imgs/Player.png");
		spriteSheet = new SpriteSheet("res/imgs/Player.png");
		
	}
	
	public Player(String name, int x, int y, int dx, int dy) 
	{
		super(x, y, dx, dy);
		this.name = name;
		spriteSheet = new SpriteSheet("res/imgs/Player.png");
	}
	
	public String getName()
	{
		return name;
	}

	@Override
	public void draw(GraphicsContext gc) {
		spriteSheet.draw();
	}

}
