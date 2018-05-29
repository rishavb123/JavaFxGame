package uiitems;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIText extends UIItem 
{
	
	private Font font;
	private Text text;
	private double width;
	protected Color color;
	
	public UIText(String text, int x, int y, int size, FontWeight f, Color color) 
	{
		this.text = new Text(text);
		super.x = x;
		super.y = y;
		this.font = Font.font("Courier New", f, size);
		this.text.setFont(font);
		width = this.text.getLayoutBounds().getWidth();
		this.color = color;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public static UIText centerText(String text, int y, int size, int width, FontWeight f, Color color) 
	{
		
		Text t = new Text(text);
		t.setFont(Font.font("Courier New", f, size));
		
		double tw = t.getLayoutBounds().getWidth();
		
		int x = (int)((width-tw)/2);
		
		return new UIText(text, x, y, size, f, color);
		
	}
	
	@Override
	public void draw(GraphicsContext gc) 
	{
		gc.setFill(color);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		gc.setFont(font);
		gc.fillText(text.getText(), x, y);
		gc.fillText(text.getText(), x, y);
	}

}
