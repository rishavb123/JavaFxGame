package uiitems.entities.spritesheet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class SpriteSheet 
{

	private static final long delay = 50;
	private static final long longDelay = 700;
	public static final int spriteWidth = 120;
	public static final int spriteHeight = 120;
	
	private BufferedImage[] frames;
	private int currentFrame;
	private long time;
	private int start;
	private BufferedImage spritesheet;
	private boolean playedOnce;
	private boolean hold;
	private boolean holding;
	private boolean useLongDelay;
		
	public SpriteSheet(String path, int start, int stop, boolean hold, boolean useLongDelay)
	{
		
		try {
			spritesheet = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.start = start;
		this.hold = hold;
		this.useLongDelay = useLongDelay;
		
		holding = false;
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
	
	public void saveCurrentFrame()
	{
		try {
			ImageIO.write(frames[currentFrame], "png", new File("image.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getImage()
	{
		return SwingFXUtils.toFXImage(frames[currentFrame], null);
	}
	
	public static Image getImage(BufferedImage bi, int x, int y, int width, int height)
	{
		return SwingFXUtils.toFXImage(bi.getSubimage(x, y, width, height), null);
	}
	
	public BufferedImage getBufferedImage()
	{
		return frames[currentFrame];
	}
	
	public boolean isHolding()
	{
		return this.holding;
	}
	
	public void update()
	{
		if(((System.nanoTime() - time) / 1000000 > delay && !useLongDelay) || ((System.nanoTime() - time) / 1000000 > longDelay && useLongDelay))
		{
			currentFrame++;
			time = System.nanoTime();
		}
		
		if(currentFrame == this.frames.length)
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
	
	public String toString()
	{
		return currentFrame+" "+frames.length;
	}

}
