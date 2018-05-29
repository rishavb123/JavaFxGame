package game;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import uiitems.*;
import uiitems.entities.*;
import uiitems.menu.*;

import constants.Constants;


public class Game extends Application implements EventHandler<InputEvent>
{
	
	public static int dim = Constants.dim;
	public static int width = dim*2;
	public static int height = dim;
		
	private GraphicsContext gc;
	private AnimateObjects animate;
	private Canvas canvas;
	private String part;
	private Stage stage;
	
	private Image background;
	private ArrayList<UIItem> items;
	
	private ArrayList<UIItem> menuItems;
	private int selectedIndex;
	
	private ArrayList<UIItem> gameObjects;
	private Player player;
	String name;

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
		scene.addEventHandler(KeyEvent.KEY_PRESSED,this);
		
		animate = new AnimateObjects();
		animate.start();
		
		stage.show();
		
		
		background = new Image("MenuBackground.png");
		
		setMenu();
		setGame();
		items = menuItems;
	}
	
	public void setPart(String s)
	{
		part = s;
		stage.setTitle(part);
	}

	public void setMenu()
	{
		name = "Iaz";
		menuItems = new ArrayList<>();
		menuItems.add(Title.centerTitle(name+"'s Mission", dim/6, dim/8, width));
		menuItems.add(MenuItem.centerMenuItem("Play", dim/3, dim/12, width, new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				play();
				return null;
			}
			
		}));
		MenuItem.select(menuItems.get(1));
		menuItems.add(MenuItem.centerMenuItem("Levels", dim/2, dim/12, width));
		menuItems.add(MenuItem.centerMenuItem("Exit", 2*dim/3, dim/12, width, new Callable<Void>() {

			@Override
			public Void call() throws Exception 
			{
				System.exit(0);
				return null;
			}
			 
		}));
		
		selectedIndex = 1;
	}
	
	public void setGame() 
	{
		gameObjects = new ArrayList<>();
		player = new Player(name, 100, 100);
		gameObjects.add(player);
		
	}
	
	public void play()
	{
		setPart("Play");
		items = gameObjects;
//		background = new Image();
	}
	
	public void click()
	{
		try {
			((MenuItem)(menuItems.get(selectedIndex))).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handle(final InputEvent event) 
	{
		if(event instanceof KeyEvent)
		{
			switch(part)
			{
				case "Game Menu":
					if(((KeyEvent)event).getCode() == KeyCode.DOWN)
					{
						MenuItem.deselect(menuItems.get(selectedIndex));
						if(selectedIndex+1<menuItems.size())
							selectedIndex++;
						MenuItem.select(menuItems.get(selectedIndex));
					}
					else if(((KeyEvent)event).getCode() == KeyCode.UP)
					{
						MenuItem.deselect(menuItems.get(selectedIndex));
						if(selectedIndex-1>0)
							selectedIndex--;
						MenuItem.select(menuItems.get(selectedIndex));
					}
					else if(((KeyEvent)event).getCode() == KeyCode.ENTER)
					{
						click();
					}
					break;
				case "Play":
					break;
			}
		}
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
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(background, width/6, 0, 2*width/3, height);
			
			for(UIItem item: items)
				item.update(gc);
			
		}
	}
	
}