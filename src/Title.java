import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class Title extends UIText {

	public Title(String text, int x, int y, int size) {
		super(text, x, y, size, FontWeight.BOLD, Color.BLUE);
		
	}

	public static Title centerTitle(String text, int y, int size, int width) {
		return new Title(text, UIText.centerText(text, y, size, width, FontWeight.BOLD, Color.WHITE).getX(), y, size);
	}
	
}
