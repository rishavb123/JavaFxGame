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
import utilities.MyFileReader;
import utilities.MyFileWriter;

public class Test extends Application {

	public static void main(String[] args) {
		MyFileWriter.write("src/res/text/highscore.txt", "0");
		System.out.println(MyFileReader.read("src/res/text/highscore.txt"));
		System.out.println("Done");
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
		
		ImageIO.write(EnemyAction.IDLE.getSprite().getBufferedImage().getSubimage(42, 3, 53, 102), "jpg", new File("test.jpg"));
		
		Image background = new Image("res/imgs/MenuBackground.PNG");
		gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
				
	}

}
