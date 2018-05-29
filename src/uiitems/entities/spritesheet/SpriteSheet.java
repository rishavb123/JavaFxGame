package uiitems.entities.spritesheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class SpriteSheet 
{

	private static final long delay = 50;
	private static final int spriteWidth = 120;
	private static final int spriteHeight = 120;
	
	private BufferedImage[] frames;
	private int currentFrame;
	private long time;
	private int start;
	private BufferedImage spritesheet;
	private boolean playedOnce;
	private boolean hold;
	private boolean holding;
		
	public SpriteSheet(String path, int start, int stop, boolean hold)
	{
		
		try {
			spritesheet = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.start = start;
		this.hold = hold;
		
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
	
	public boolean isHolding()
	{
		return this.holding;
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
			if(hold) {
				currentFrame--;
				holding = true;
			}
			else {
				currentFrame = 0;
				playedOnce = true;
			}
		}
		
	}
	
	public void reset()
	{
		playedOnce = false;
		currentFrame = 0;
	}
	
	public boolean hasPlayedOnce()
	{
		return playedOnce;
	}

}
