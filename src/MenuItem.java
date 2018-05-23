import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class MenuItem extends UIText
{
	private boolean selected;
	
	public MenuItem(String text, int x, int y, int size) 
	{
		super(text, x, y, size, FontWeight.NORMAL, Color.WHITE);
		super.x = x;
		super.y = y;
		selected = false;
	}
	
	public void setSelected(boolean s)
	{
		selected = s;
		super.color = selected? Color.ORANGE:Color.WHITE;
	}
	
	public static void select(UIItem u)
	{
		if(u instanceof MenuItem)
		{
			MenuItem m = (MenuItem)u;
			m.setSelected(true);
		}
	}
	
	public static void deselect(UIItem u)
	{
		if(u instanceof MenuItem)
		{
			MenuItem m = (MenuItem)u;
			m.setSelected(false);
		}
	}
	
	public static MenuItem centerMenuItem(String text, int y, int size, int width)
	{
		return new MenuItem(text, UIText.centerText(text, y, size, width, FontWeight.NORMAL, Color.WHITE).getX(), y, size);
	}

}
