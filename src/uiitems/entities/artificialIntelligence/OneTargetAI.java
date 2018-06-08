package uiitems.entities.artificialIntelligence;

import java.util.ArrayList;

import uiitems.UIItem;
import uiitems.entities.Entity;

public abstract class OneTargetAI<C, T>{

	protected T target;
	protected C control;
	
	protected ArrayList<UIItem> gameObjects;
	
	public OneTargetAI(C control, T target, ArrayList<UIItem> gameObjects) {
		this.target = target;
		this.control = control;
		this.gameObjects = gameObjects;
	}
	
	public T getTarget() {
		return target;
	}
	
	public C getControl() {
		return control;
	}
	
	public void control() {
		if(((Entity)target).isAlive() && ((Entity)control).isAlive())
			targetInteractions();
			itemsInteractions();
	}
	
	public abstract void itemsInteractions();
	public abstract void targetInteractions();
	

}
