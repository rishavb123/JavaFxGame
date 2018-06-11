import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
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
import uiitems.UIText;
import uiitems.blocks.Block;
import uiitems.blockGroups.BlockGroup;
import uiitems.blockGroups.Floor;
import uiitems.blockGroups.Platform;
import uiitems.entities.Entity;
import uiitems.entities.Player;
import uiitems.entities.ColorPlayer;
import uiitems.entities.Enemy;
import uiitems.menu.MenuItem;
import uiitems.menu.Title;
import utilities.Constants;
import utilities.MyFileReader;
import utilities.MyFileWriter;
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
		KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D, KeyCode.SPACE, KeyCode.Z, KeyCode.X, KeyCode.C, KeyCode.SHIFT, KeyCode.CONTROL, KeyCode.ENTER, KeyCode.PERIOD, KeyCode.SLASH, KeyCode.F, KeyCode.Q, KeyCode.R, KeyCode.E
	};
	
	private Background background;
	private Background menuBackground;
	private Background gameBackground;
	private Background gameOverBackground;
	
	private ArrayList<UIItem> items;
	private ArrayList<UIItem> menuItems;
	private static ArrayList<UIItem> gameObjects;
	private ArrayList<UIItem> gameOverObjects;
	private ArrayList<UIItem> multiPlayerObjects;
	private ArrayList<UIItem> winObjects;
	
	private int selectedIndex;
	private Player player;
	String name;
	
	private ColorPlayer player1;
	private ColorPlayer player2;
	
	private int score;
	private int highScore;
	private UIText scoreDisplay;
	private UIText highScoreDisplay;
	
	
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
		setGameOver();
		setMultiPlayer();
		setWin(1);
		
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
		menuItems.add(MenuItem.centerMenuItem("Multi Player", dim/2, dim/12, width, new Callable<Void>() {
			
			@Override
			public Void call() throws Exception
			{
				playMultiPlayer();
				return null;
			}
			
		}));
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
		player = new Player(name, Constants.dim/6, Constants.dim/6);
		player.setHealthBar(dim/60, dim/20, 3*dim/8, dim/10 - dim/60);
		gameObjects.add(player);
		gameObjects.add(new Enemy((int)(Constants.dim * 4.0/3),Constants.dim/6, player, gameObjects));
		gameObjects.add(new Floor(0, 3));
		score = 0;
		String shighScore = MyFileReader.read("src/res/text/highscore.txt");
		highScore = (shighScore == null || shighScore.equals("null\n")|| shighScore.length() == 0)? 0 : Integer.parseInt(shighScore);
		MyFileWriter.write("src/res/text/highscore.txt", Integer.toString(highScore));
		scoreDisplay = new UIText("Score: "+score, width-dim/60-dim/2, dim/18, dim/12, FontWeight.MEDIUM, Color.WHITE);
		highScoreDisplay = new UIText("High Score: "+highScore, width-dim/60-dim*8/9, dim*29/30, dim/12, FontWeight.MEDIUM, Color.WHITE);
		gameObjects.add(scoreDisplay);
		gameObjects.add(highScoreDisplay);
	}
	
	public void setMultiPlayer() 
	{
		int num = (int)(Math.random()*8)+1;
		gameBackground = new Background("res/imgs/DuelBackground"+num+".gif", Color.rgb(255, 255, 255, 0.25));
		multiPlayerObjects = new ArrayList<>();
		player1 = new ColorPlayer(name, Constants.dim/6, Constants.dim/6, Color.rgb(255, 0, 0, 0.5));
		player2 = new ColorPlayer(name, width - Constants.dim/5 - Constants.dim/6, Constants.dim/6, Color.rgb(0, 0, 255, 0.5));
		player2.setDirection(-1);
		player1.setHealthBar(dim/60, dim/20, 3*dim/8, dim/10 - dim/60);
		player2.setHealthBar(width - dim/60 - 3*dim/8, dim/20, 3*dim/8, dim/10 - dim/60);
		multiPlayerObjects.add(player1);
		multiPlayerObjects.add(player2);
		multiPlayerObjects.add(new Platform(Constants.dim/5, Constants.dim*3/10, 4, "ghost"));
		multiPlayerObjects.add(new Platform(Constants.dim/5, Constants.dim*3/5, 4, "bouncy"));
		multiPlayerObjects.add(new Platform(width - Constants.dim*3/5, Constants.dim*3/10, 4, "ghost"));
		multiPlayerObjects.add(new Platform(width - Constants.dim*3/5, Constants.dim*3/5, 4, "bouncy"));
		multiPlayerObjects.add(new Platform(Constants.dim*4/5, Constants.dim*9/20, 4));
		multiPlayerObjects.add(new Floor(0, 3));
	}
	
	public void setWin(int i)
	{
		gameOverBackground = new Background(null, Color.rgb(0, 0, 0, 1));
		winObjects = new ArrayList<>();
		winObjects.add(Title.centerTitle("Player "+i+" Won!", dim/3, dim/6, width));
		winObjects.add(MenuItem.centerMenuItem("R to restart", (int)(5.0*dim/9), dim/12, width));
		winObjects.add(MenuItem.centerMenuItem("M for back to Menu", (int)(7.0*dim/9), dim/12, width));
	}
	
	public void setGameOver()
	{
		gameOverBackground = new Background(null, Color.rgb(0, 0, 0, 1));
		gameOverObjects = new ArrayList<>();
		gameOverObjects.add(Title.centerTitle("Game Over", dim/3, dim/6, width));
		gameOverObjects.add(MenuItem.centerMenuItem("R to restart", (int)(5.0*dim/9), dim/12, width));
		gameOverObjects.add(MenuItem.centerMenuItem("M for back to Menu", (int)(7.0*dim/9), dim/12, width));
		gameOverObjects.add(scoreDisplay);
		gameOverObjects.add(highScoreDisplay);
	}
	
	public void play()
	{
		setGame();
		setGameOver();
		setPart("Play");
		items = gameObjects;
		background = gameBackground;
	}
	
	public void playMultiPlayer()
	{
		setMultiPlayer();
		setPart("Multi Player");
		items = multiPlayerObjects;
		background = gameBackground;
	}
	
	public void died()
	{
		setPart("Game Over");
		MyFileWriter.write("src/res/text/highscore.txt", Integer.toString(highScore));
		items = gameOverObjects;
		background = gameOverBackground;
	}
	
	public void win(int i)
	{
		setPart("Won");
		setWin(i);
		items = winObjects;
		background = gameOverBackground;
	}
	
	public void menu()
	{
		setPart("Game Menu");
		items = menuItems;
		background = menuBackground;
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
					default:
						break;
				}
				break;
			case "Multi Player":
				switch(event.getCode())
				{
					case UP:
						player2.jump();
						break;
					case W:
						player1.jump();
						break;
					default:
						break;
				}
				break;
			case "Game Over":
				switch(event.getCode())
				{
					case R:
						play();
						break;
					case M:
						menu();
						break;
					default:
						break;
				}
				break;
			case "Won":
				switch(event.getCode())
				{
					case R:
						playMultiPlayer();
						break;
					case M:
						menu();
						break;
					default:
						break;
				}
				break;
		}
	}
	
	public void actions()
	{
		playerControl();
		itemsCheck();
		updateGame();
		checkHighScore();
		if(!player.isAlive())
			died();
		scoreDisplay.setText("Score: "+score);
	}
	
	public void multiPlayerActions()
	{
		playersControl();
		itemsCheck();
		if(!player1.isAlive())
			win(2);
		else if(!player2.isAlive())
			win(1);
		
	}
	
	public void updateGame()
	{
		int enemyCount = 0;
		for(UIItem i: gameObjects)
			if(i instanceof Enemy)
				enemyCount++;
		if(enemyCount == 0) {
			int a = 1;
			int b = score;
			while(b > 0)
			{
				b-=a;
				a++;
			}
			for(int x=0;x<a;x++)
				gameObjects.add(new Enemy((int)(Math.random()*(width - Constants.dim/5)), -((int)(Math.random()*3)+1)*Constants.dim/5, player, gameObjects));
		}
	}
	
	public void checkHighScore() 
	{
		if(score>highScore)
		{
			highScore = score;
			highScoreDisplay.setText("High Score: "+score);
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
	
	public void playersControl()
	{
		if((keys.get(KeyCode.RIGHT).isPressed() && (!player2.isShortAttacking() || !player2.isBottomTouch())))
			player2.move(true);
		else if((keys.get(KeyCode.LEFT).isPressed() && (!player2.isShortAttacking() || !player2.isBottomTouch())))
			player2.move(false);
		else
			if(player2.isWalking())
				player2.stop();
		
		if((keys.get(KeyCode.D).isPressed() && (!player1.isShortAttacking() || !player1.isBottomTouch())))
				player1.move(true);
			else if((keys.get(KeyCode.A).isPressed() && (!player1.isShortAttacking() || !player1.isBottomTouch())))
				player1.move(false);
			else
				if(player1.isWalking())
					player1.stop();
		
		if(keys.get(KeyCode.CONTROL).isPressed())
			player2.startFly();
		else
			if(player2.isFlying())
				player2.stopFly();
		
		if(keys.get(KeyCode.SLASH).isPressed())
			player2.startShortAttack();
		else
			if(player2.isShortAttacking())
				player2.stopShortAttack();
		
		if(keys.get(KeyCode.PERIOD).isPressed())
			player2.startSheild();
		else
			if(player2.isSheilding())
				player2.stopSheild();
		
		if(keys.get(KeyCode.SHIFT).isPressed())
			player2.startLongAttack();
		else
			if(player2.isLongAttacking())
				multiPlayerObjects.add(player2.stopLongAttack());
		
		
		if(keys.get(KeyCode.F).isPressed())
			player1.startFly();
		else
			if(player1.isFlying())
				player1.stopFly();
		
		if(keys.get(KeyCode.Q).isPressed())
			player1.startShortAttack();
		else
			if(player1.isShortAttacking())
				player1.stopShortAttack();
		
		if(keys.get(KeyCode.R).isPressed())
			player1.startSheild();
		else
			if(player1.isSheilding())
				player1.stopSheild();
		
		if(keys.get(KeyCode.E).isPressed())
			player1.startLongAttack();
		else
			if(player1.isLongAttacking())
				multiPlayerObjects.add(player1.stopLongAttack());
		
	}
	
	public void itemsCheck()
	{
		for(int x=0; x < items.size(); x++)
		{
			if(items.get(x) instanceof Player)
			{
				Player p = (Player)items.get(x);
				if(p.isShortAttacking())
				{
					int d = p.getDirection();
					Rectangle2D r = new Rectangle2D(p.getRx() + ((d == 1)? p.getRWidth() : -1.3*p.getRWidth()), p.getRy(), 1.3*p.getRWidth(), p.getRHeight());
					for(int y=0; y < items.size(); y++)
					{
						if(items.get(y) instanceof Entity && items.get(y) != p && r.intersects(((Entity)items.get(y)).getRect()))
						{
							((Entity)items.get(y)).damage(1 + (int)(Math.sqrt(p.getDx()*p.getDx()+p.getDy()*p.getDy())));
						}
						if(items.get(y) instanceof Enemy && items.get(y) != p && r.intersects(((Enemy)items.get(y)).getRect()))
						{
							p.damage(5);
						}
					}
				} else {
					int d = p.getDirection();
					Rectangle2D r = new Rectangle2D(p.getRx() + ((d == 1)? p.getRWidth() : -1.3*p.getRWidth()), p.getRy(), 1.3*p.getRWidth(), p.getRHeight());
					for(int y=0; y < items.size(); y++)
					{
						if(items.get(y) instanceof Enemy && items.get(y) != p && r.intersects(((Enemy)items.get(y)).getRect()))
						{
							p.damage(200);
						}
					}
				}
			}
			if(items.get(x) instanceof Entity)
			{
				Entity p = (Entity)items.get(x);
				boolean hit = false;
				for(int y=0;y<items.size();y++)
				{
					if(items.get(y) instanceof Block)
					{
						Block b = (Block)items.get(y);
						if(b.getRect().intersects(p.getRect()))
						{
							b.hit(p);
							hit = true;
						}
						
					}
					
					if(items.get(y) instanceof BlockGroup)
					{
						for(Block b : ((BlockGroup)items.get(y)).getBlockList())
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
				
				if(((Entity)items.get(x)).getHealth() <= 0 || ((Entity)items.get(x)).getRx()+((Entity)items.get(x)).getRWidth()<0 || ((Entity)items.get(x)).getRx()>Constants.width)
				{
					if(items.get(x) instanceof Enemy && ((Entity)items.get(x)).getHealth() <= 0)
						score++;
					((Entity)items.get(x)).die();
					items.remove(x);
					x--;
					continue;
				}
			}
			if(items.get(x) instanceof Laser)
			{
				Laser laser = (Laser)items.get(x);
				if(laser.getY() > height)
				{
					items.remove(x);
					x--;
					continue;
				}

				boolean hit = false;
				for(int y=0;y<items.size();y++) {
					if(items.get(y) instanceof Entity && laser.getRect().intersects(((Entity)items.get(y)).getRect()))
					{
						((Entity)items.get(y)).damage((int)(Math.sqrt(laser.getDx()*laser.getDx()+laser.getDy()*laser.getDy()))*5);
						hit = true;
					}
					else if(items.get(y) instanceof Block && laser.getRect().intersects(((Block)items.get(y)).getRect()))
					{
						hit = true;
					}
				}
				
				if(hit) 
				{
					items.remove(x);
					x--;
					continue;
				}
			}
			if(items.get(x) instanceof Grenade) 
			{
				Grenade g = (Grenade)items.get(x);
				
				if(!g.isPresent())
				{
					items.remove(x);
					x--;
					continue;
				}
				
				
				
				for(int y=0;y<items.size();y++)
				{
					
					if(items.get(y) instanceof Entity && g.intersects(((Entity)items.get(y)).getRect()))
					{
						if(g.isExploded())
						{
							((Entity)items.get(y)).damage(10);
						}
						else
						{
							g.explode();
							((Entity)items.get(y)).damage(400);
							y =-1;
						}
					}
					else if(items.get(y) instanceof Laser && g.intersects(((Laser)items.get(y)).getRect()))
					{
						if(!g.isExploded())
						{
							y = -1;
							g.explode();
						}
					}
					else if(items.get(y) instanceof Block && g.intersects(((Block)items.get(y)).getRect()))
					{
						if(!g.isExploded())
						{
							y = -1;
							g.explode();
						}
					}
					else if(items.get(y) instanceof BlockGroup)
					{
						for(Block b: ((BlockGroup)items.get(y)).getBlockList())
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
				actions();
			else if(part.equals("Multi Player"))
				multiPlayerActions();
			for(int x=0;x<items.size();x++) 
				items.get(x).update(gc);
			
		}
	}
	
}