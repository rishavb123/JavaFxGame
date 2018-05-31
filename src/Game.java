import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.animation.AnimationTimer;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import uiitems.Background;
import uiitems.UIItem;
import uiitems.entities.Player;
import uiitems.entities.actions.PlayerAction;
import uiitems.menu.MenuItem;
import uiitems.menu.Title;

import constants.Constants;

import input.Key;


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
	
	private HashMap<KeyCode, Key> keys;
	private static final KeyCode[] keyCodes = {
		KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D, KeyCode.SPACE, KeyCode.Z, KeyCode.X, KeyCode.C, KeyCode.SHIFT, KeyCode.CONTROL, KeyCode.ENTER
	};
	
	private Background background;
	private Background menuBackground;
	private Background gameBackground;
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
		scene.addEventHandler(KeyEvent.KEY_RELEASED,this);
		
		animate = new AnimateObjects();
		animate.start();
		
		stage.show();
		
		setMenu();
		setGame();
		
		background = menuBackground;
		
		keys = new HashMap<>();
		
		for(KeyCode k: keyCodes)
			keys.put(k, new Key());
		
		items = menuItems;
	}
	
	public void setPart(String s)
	{
		part = s;
		stage.setTitle(part);
	}

	public void setMenu()
	{
		menuBackground = new Background("res/imgs/MenuBackground.PNG", 2*width/3, Color.BLACK);
		
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
		gameBackground = new Background("res/imgs/GameBackground.gif", Color.rgb(255, 255, 255, 0.5));
		gameObjects = new ArrayList<>();
		player = new Player(name, 100, 100);
		player.setHealthBar(dim/60, dim/20, 3*dim/8, dim/10 - dim/60);
		gameObjects.add(player);
	}
	
	public void play()
	{
		setPart("Play");
		items = gameObjects;
		background = gameBackground;
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
			if(event.getEventType().toString().equals("KEY_PRESSED"))
				keyPressed((KeyEvent)event);
			else
				keyReleased((KeyEvent)event);
		}
	}
	
	public void keyReleased(final KeyEvent event)
	{
		if(keys.containsKey(event.getCode()))
			keys.get(event.getCode()).release();
	}
	
	public void keyPressed(final KeyEvent event)
	{
		if(keys.containsKey(event.getCode()))
			keys.get(event.getCode()).press();
		
		switch(part)
		{
			case "Game Menu":
				if(event.getCode() == KeyCode.DOWN)
				{
					MenuItem.deselect(menuItems.get(selectedIndex));
					if(selectedIndex+1<menuItems.size())
						selectedIndex++;
					MenuItem.select(menuItems.get(selectedIndex));
				}
				else if(event.getCode() == KeyCode.UP)
				{
					MenuItem.deselect(menuItems.get(selectedIndex));
					if(selectedIndex-1>0)
						selectedIndex--;
					MenuItem.select(menuItems.get(selectedIndex));
				}
				else if(event.getCode() == KeyCode.ENTER)
				{
					click();
				}
				break;
			case "Play":
				switch(event.getCode())
				{
					case UP:
					case W:
						player.jump();
						break;
					case I:
						System.out.println("Player Action: "+player.getAction());
						System.out.println("Health: "+player.getHealth());
						System.out.println("X: "+player.getX());
						System.out.println("Y: "+player.getY());
						break;
					default:
						break;
				}
		}
	}
	
	public void playerControl()
	{
		if((keys.get(KeyCode.RIGHT).isPressed() || keys.get(KeyCode.D).isPressed()) && (!player.isShortAttacking() || !player.isBottomTouch()))
			player.move(true);
		else if((keys.get(KeyCode.LEFT).isPressed() || keys.get(KeyCode.A).isPressed()) && (!player.isShortAttacking() || !player.isBottomTouch()))
			player.move(false);
		else
			if(player.isWalking())
				player.stop();
		
		if(keys.get(KeyCode.SPACE).isPressed() || keys.get(KeyCode.SHIFT).isPressed())
			player.startFly();
		else
			if(player.isFlying())
				player.stopFly(PlayerAction.IDLE);
		
		if(keys.get(KeyCode.C).isPressed())
			player.shortAttack();
		else
			if(player.isShortAttacking())
				player.stopShortAttack();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public class AnimateObjects extends AnimationTimer
	{
		public void handle(long time)
		{
			background.update(gc);
			
			if(part.equals("Play"))
				playerControl();
			
			for(UIItem item: items)
				item.update(gc);
			
		}
	}
	
}