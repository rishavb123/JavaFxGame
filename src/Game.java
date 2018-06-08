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
import javafx.geometry.Rectangle2D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import uiitems.Background;
import uiitems.Grenade;
import uiitems.Laser;
import uiitems.UIItem;
import uiitems.blocks.Block;
import uiitems.blockGroups.BlockGroup;
import uiitems.blockGroups.Floor;
import uiitems.entities.Entity;
import uiitems.entities.Player;
import uiitems.entities.Enemy;
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
	
	private static ArrayList<UIItem> gameObjects;
	
	private Player player;
	String name;
	
	private Enemy enemy;
	
	public void start(Stage stage)
	{
		this.stage = stage;
		
		setPart("Game Menu");
		
		try {
			Block.blockSheet = ImageIO.read(getClass().getResourceAsStream("uiitems/blocks/blockImages/BlockSheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		int num = (int)(Math.random()*4)+1;
		gameBackground = new Background("res/imgs/GameBackground"+num+".gif", Color.rgb(255, 255, 255, 0.25));
		gameObjects = new ArrayList<>();
		player = new Player(name, 100, 100);
		player.setHealthBar(dim/60, dim/20, 3*dim/8, dim/10 - dim/60);
		gameObjects.add(player);
		Player testPlayer = new Player(name, 800, 100);
		testPlayer.setHealthBar(width - dim/60 - 3*dim/8, dim/20, 3*dim/8, dim/10 - dim/60);
		gameObjects.add(testPlayer);
		enemy = new Enemy(400,100);
		gameObjects.add(enemy);
		gameObjects.add(new Floor(0, 3));
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
		
		if(part == "Play")
			switch(event.getCode())
			{
				case J:
					enemy.stop();
					break;
					
				case L:
					enemy.stop();
					break;
					
				default:
					break;
			}
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
					case O:
						System.out.println("Player Action: "+player.getAction());
						System.out.println("Health: "+player.getHealth());
						System.out.println("X: "+player.getX());
						System.out.println("Y: "+player.getY());
						break;
						
					case P:
						player.getAction().getSprite().saveCurrentFrame();
						break;
						
					case U:
						gameObjects.add(enemy.laser());
						break;
						
					case I:
						enemy.jump();
						break;
						
					case J:
						enemy.move(false);
						break;
						
					case L:
						enemy.move(true);
						break;
						
					case M:
						gameObjects.add(enemy.shoot());
						break;
						
					default:
						break;
				}
		}
	}
	
	public void actions()
	{
		playerControl();
		itemsCheck();
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
				player.stopFly();
		
		if(keys.get(KeyCode.C).isPressed())
			player.startShortAttack();
		else
			if(player.isShortAttacking())
				player.stopShortAttack();
		
		if(keys.get(KeyCode.Z).isPressed())
			player.startSheild();
		else
			if(player.isSheilding())
				player.stopSheild();
		
		if(keys.get(KeyCode.X).isPressed())
			player.startLongAttack();
		else
			if(player.isLongAttacking())
				gameObjects.add(player.stopLongAttack());
	}
	
	public void itemsCheck()
	{
		for(int x=0; x < gameObjects.size(); x++)
		{
			if(gameObjects.get(x) instanceof Player)
			{
				Player p = (Player)gameObjects.get(x);
				if(p.isShortAttacking())
				{
					int d = p.getDirection();
					Rectangle2D r = new Rectangle2D(p.getRx() + ((d == 1)? p.getRWidth() : -1.3*p.getRWidth()), p.getRy(), 1.3*p.getRWidth(), p.getRHeight());
					for(int y=0; y < gameObjects.size(); y++)
					{
						if(gameObjects.get(y) instanceof Entity && gameObjects.get(y) != p && r.intersects(((Entity)gameObjects.get(y)).getRect()))
						{
							((Entity)gameObjects.get(y)).damage(1 + 2*(int)(Math.sqrt(p.getDx()*p.getDx()+p.getDy()*p.getDy())));
							
						}
					}
				}
			}
			if(gameObjects.get(x) instanceof Entity)
			{
				Entity p = (Entity)gameObjects.get(x);
				boolean hit = false;
				for(int y=0;y<gameObjects.size();y++)
				{
					if(gameObjects.get(y) instanceof Block)
					{
						Block b = (Block)gameObjects.get(y);
						if(b.getRect().intersects(p.getRect()))
						{
							b.hit(p);
							hit = true;
						}
						
					}
					
					if(gameObjects.get(y) instanceof BlockGroup)
					{
						for(Block b : ((BlockGroup)gameObjects.get(y)).getBlockList())
							if(b.getRect().intersects(p.getRect()))
							{
								b.hit(p);
								hit = true;
							}
					}
						
				}
				
				if(!hit) {
					p.setBottomTouch(false);
					p.setTopTouch(false);
					p.setLeftTouch(false);
					p.setRightTouch(false);
				}
				
				if(((Entity)gameObjects.get(x)).getHealth() <= 0 || gameObjects.get(x).getX()+((Entity)gameObjects.get(x)).getWidth()<0 || gameObjects.get(x).getX()>Constants.width)
				{
					gameObjects.remove(x);
					x--;
					continue;
				}
			}
			if(gameObjects.get(x) instanceof Laser)
			{
				Laser laser = (Laser)gameObjects.get(x);
				if(laser.getY() > height)
				{
					gameObjects.remove(x);
					x--;
					continue;
				}

				boolean hit = false;
				for(int y=0;y<gameObjects.size();y++) {
					if(gameObjects.get(y) instanceof Entity && laser.getRect().intersects(((Entity)gameObjects.get(y)).getRect()))
					{
						((Entity)gameObjects.get(y)).damage((int)(Math.sqrt(laser.getDx()*laser.getDx()+laser.getDy()*laser.getDy()))*5);
						hit = true;
					}
					else if(gameObjects.get(y) instanceof Block && laser.getRect().intersects(((Block)gameObjects.get(y)).getRect()))
					{
						hit = true;
					}
				}
				
				if(hit) 
				{
					gameObjects.remove(x);
					x--;
					continue;
				}
			}
			if(gameObjects.get(x) instanceof Grenade) 
			{
				Grenade g = (Grenade)gameObjects.get(x);
				
				if(!g.isPresent())
				{
					gameObjects.remove(x);
					x--;
					continue;
				}
				
				
				
				for(int y=0;y<gameObjects.size();y++)
				{
					
					if(gameObjects.get(y) instanceof Entity && g.intersects(((Entity)gameObjects.get(y)).getRect()))
					{
						if(g.isExploded())
						{
							((Entity)gameObjects.get(y)).damage(10);
						}
						else
						{
							g.explode();
							((Entity)gameObjects.get(y)).damage(400);
							y =-1;
						}
					}
					else if(gameObjects.get(y) instanceof Laser && g.intersects(((Laser)gameObjects.get(y)).getRect()))
					{
						if(!g.isExploded())
						{
							y = -1;
							g.explode();
						}
					}
					else if(gameObjects.get(y) instanceof Block && g.intersects(((Block)gameObjects.get(y)).getRect()))
					{
						if(!g.isExploded())
						{
							y = -1;
							g.explode();
						}
					}
					else if(gameObjects.get(y) instanceof BlockGroup)
					{
						for(Block b: ((BlockGroup)gameObjects.get(y)).getBlockList())
							if(g.intersects(b.getRect()) && !g.isExploded())
							{
								y = -1;
								g.explode();
							}
					}
				}
				
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
			background.update(gc);
			
			if(part.equals("Play"))
			{
				actions();
			}
			
			for(UIItem item: items)
				item.update(gc);
						
		}
	}
	
}