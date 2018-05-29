package input;

import javafx.scene.input.KeyCode;

public class Key {

	private KeyCode keyCode;
	private boolean pressed = false;
	private String keyName;
	
	public Key(String keyName, KeyCode keyCode) {
		this.keyName = keyName;
		this.keyCode = keyCode;
	}
	
	public Key() {
		this.keyName = "key";
		this.keyCode = null;
	}
	
	public void press()
	{
		pressed = true;
	}
	
	public void release()
	{
		pressed = false;
	}
	
	public boolean isPressed()
	{
		return pressed;
	}

	public String getKeyName() {
		return keyName;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}
}
