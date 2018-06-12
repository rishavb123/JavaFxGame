package uiitems.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ColorPlayer extends Player {

	private Color backgroundColor;
	
	public ColorPlayer(String name, int x, int y, Color c) {
		super(name, x, y, true);
		backgroundColor = c;
	}
	
	@Override
	public void draw(GraphicsContext gc)
	{
		gc.setFill(backgroundColor);
		gc.fillRect(x, y, width, height);
		super.draw(gc);
	}

}
