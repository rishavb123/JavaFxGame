import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

	private String name;
	private SpriteSheet spriteSheet;
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			1, 4, 7, 3, 4, 3
	};
	
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int LASER = 2;
	private static final int JUMP = 3;
	private static final int SHEILD = 4;
	private static final int ATTACK = 5;
	
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

	}

}
