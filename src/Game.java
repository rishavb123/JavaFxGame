import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
//import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;

public class Game extends Application 
{
	
	private int dim = 600;
	private int width = dim*2;
	private int height = dim;
	private GraphicsContext gc;
	private AnimateObjects animate;
	private Canvas canvas;
	private String part;
	private Stage stage;
	private Image background;
	private ArrayList<UIItem> items;
	private ArrayList<UIItem> menuItems;

	public void start(Stage stage)
	{
		this.stage = stage;
		setPart("Game Menu");
		Group root = new Group();
		canvas = new Canvas(width, height);
		root.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		animate = new AnimateObjects();
		animate.start();
		stage.show();
		
		background = new Image("res/imgs/MenuBackground.jpeg");
		menuItems = new ArrayList<>();
		menuItems.add(Title.centerTitle(part, dim/6, dim/8, width));
		menuItems.add(MenuItem.centerMenuItem("Exit", dim/3, dim/12, width));
		MenuItem.select(menuItems.get(1));
		MenuItem.deselect(menuItems.get(1));
		items = menuItems;
	}
	
	public void setPart(String s)
	{
		part = s;
		stage.setTitle(part);
	}

	public static void main(String[] args)
	{
		launch(args);
	}
	
	public class AnimateObjects extends AnimationTimer
	{
		public void handle(long time)
		{
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(background, 0, 0, width, height);
			
			for(UIItem item: items)
				item.draw(gc);
			
		}
	}
	
}