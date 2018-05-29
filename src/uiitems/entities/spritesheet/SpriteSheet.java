package uiitems.entities.spritesheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class SpriteSheet 
{

	private static final long delay = 100;
	private static final int spriteWidth = 120;
	private static final int spriteHeight = 120;
	
	private BufferedImage[] frames;
	private int currentFrame;
	private long time;
	private int start;
	private BufferedImage spritesheet;
	private boolean playedOnce;
		
	public SpriteSheet(String path, int start, int stop)
	{
		this.start = start;
		
		try {
			spritesheet = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		time = System.nanoTime();
		frames = new BufferedImage[stop-start];
		currentFrame = 0;
		setFrames();
	}
	
	public void setFrames()
	{
		for(int x = 0; x < frames.length; x++)
		{
			frames[x] = spritesheet.getSubimage((x+start)*spriteWidth, 0, spriteWidth, spriteHeight);
		}
	}
	
	public Image getImage()
	{
		return SwingFXUtils.toFXImage(frames[currentFrame], null);
	}
	
	public void update()
	{
		if((System.nanoTime() - time) / 1000000 > delay)
		{
			currentFrame++;
			time = System.nanoTime();
		}
		
		if(currentFrame == frames.length)
		{
			currentFrame = 0;
			playedOnce = true;
		}
		
	}
	
	public boolean reset()
	{
		return true;
	}
	
	public boolean hasPlayedOnce()
	{
		return playedOnce;
	}

}
