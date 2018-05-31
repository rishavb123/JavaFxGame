package uiitems;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ProgressBar extends UIItem {

	private int width;
	private int height;
	private Color color;
	private int max;
	private int value;
	private double ratio;
	
	public ProgressBar(int max, int x, int y, int width, int height, Color color) {
		super.x = x;
		super.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.max = max;
		value = 0;
	}

	@Override
	public void draw(GraphicsContext gc)
	{
		gc.setFill(color);
		gc.fillRect(x, y, width*ratio, height);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(x, y, width, height);
	}
	
	@Override
	public void update(GraphicsContext gc)
	{
		ratio = (double)value/max;
		
		draw(gc);
	}

	public int getMax() {
		return max;
	}

	public void setValue(int value)
	{
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public double getRatio() {
		return ratio;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
