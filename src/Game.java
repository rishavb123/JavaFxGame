import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer; 
import javafx.scene.text.Font;

public class Game extends Application {

	private int width = 1200;
	private int height = 600;
	private GraphicsContext gc;
	private AnimateObjects animate;
	private Canvas canvas;
	private String part;
	private Stage stage;
	private Image background;

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
			
		}
	}
	
}