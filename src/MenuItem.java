import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import java.util.concurrent.Callable;

public class MenuItem extends UIText
{
	private boolean selected;
	private Callable<Void> c;
	
	public MenuItem(String text, int x, int y, int size, Callable<Void> func) 
	{
		super(text, x, y, size, FontWeight.NORMAL, Color.WHITE);
		super.x = x;
		super.y = y;
		c = func;
		selected = false;
	}
	
	public MenuItem(String text, int x, int y, int size) 
	{
		super(text, x, y, size, FontWeight.NORMAL, Color.WHITE);
		super.x = x;
		super.y = y;
		c = new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				return null;
			}
		};
		selected = false;
	}
	
	public void click() throws Exception
	{
		c.call();
	}
	
	public void setSelected(boolean s)
	{
		selected = s;
		super.color = selected? Color.YELLOW:Color.WHITE;
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
	
	public static MenuItem centerMenuItem(String text, int y, int size, int width, Callable<Void> func)
	{
		return new MenuItem(text, UIText.centerText(text, y, size, width, FontWeight.NORMAL, Color.WHITE).getX(), y, size, func);
	}

}
