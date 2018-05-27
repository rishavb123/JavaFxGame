import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SpriteSheet {
	
	private BufferedImage bufferedImage;
//	private String path;
	private int width;
	private int height;
	private int[] pixels;

	public SpriteSheet(String path)
	{
//		this.path = path;
		try {
			bufferedImage = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.width = bufferedImage.getWidth();
		this.height = bufferedImage.getHeight();
		
		pixels = bufferedImage.getRGB(0, 0, width, height, null, 0, width);
		
		for(int i=0; i < 8;i++)
			System.out.println(pixels[i]);
		
		for(int i=0; i< pixels.length;i++)
			pixels[i] = (pixels[i]&0xff)/64;
		
		System.out.println();

		for(int i=0; i < 8;i++)
			System.out.println(pixels[i]);
		
		
	}
	
	public void draw() {
		
	}
	

}
