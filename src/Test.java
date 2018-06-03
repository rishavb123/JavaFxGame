import java.io.File;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import uiitems.entities.actions.*;

public class Test extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Group root = new Group();
		Canvas canvas = new Canvas(1200, 600);
		root.getChildren().add(canvas);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		stage.show();
		
		ImageIO.write(PlayerAction.IDLE.getSprite().getBufferedImage().getSubimage(30, 3, 30, 88), "jpg", new File("test.jpg"));
		
		Image background = new Image("res/imgs/MenuBackground.PNG");
		gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		
		System.exit(0);
		
	}

}
