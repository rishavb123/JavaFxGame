package uiitems;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import utilities.Constants;

public class Background extends UIItem {

	private Image image;
	private int width;
	private Color backgroundColor;
	private Color cover;
	
	public Background(String path) 
	{
		if(path!=null) image = new Image(path);
		width = Constants.width;
		backgroundColor = Color.rgb(0, 0, 0, 0);;
		this.cover = Color.rgb(0, 0, 0, 0);;
	}
	
	public Background(String path, int width, Color backgroundColor) 
	{
		if(path!=null) image = new Image(path);
		this.width = width;
		this.backgroundColor = backgroundColor;
		this.cover = Color.rgb(0, 0, 0, 0);
	}
	
	public Background(String path, Color cover) 
	{
		if(path!=null) image = new Image(path);
		width = Constants.width;
		backgroundColor = Color.rgb(0, 0, 0, 0);
		this.cover = cover;
	}
	
	public Background(String path, int width, Color backgroundColor, Color cover) 
	{
		if(path!=null) image = new Image(path);
		this.width = width;
		this.backgroundColor = backgroundColor;
		this.cover = cover;
	}

	@Override
	public void draw(GraphicsContext gc) 
	{
		gc.clearRect(0, 0, Constants.width, Constants.height);
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Constants.width, Constants.height);
		gc.drawImage(image, (Constants.width - width)/2.0, 0, width, Constants.height);
		gc.setFill(cover);
		gc.fillRect(0, 0, Constants.width, Constants.height);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getCover() {
		return cover;
	}

	public void setCover(Color cover) {
		this.cover = cover;
	}

}
