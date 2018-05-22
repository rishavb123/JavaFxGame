import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Title extends UIItem {
	
	private Color color;
	private Font font;
	private String text;
	
	public Title(String text, int x, int y, Font font) {
		this.text = text;
		super.x = x;
		super.y = y;
		this.font = font;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		gc.setFont(font);
		gc.fillText(text, x, y);
		gc.fillText(text, x, y);
	}

}
