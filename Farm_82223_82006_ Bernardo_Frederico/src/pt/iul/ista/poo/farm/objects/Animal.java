package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Animal extends FarmObject implements Updatable , Interactable {
	
	private boolean isHungry = false;
	
	public boolean isHungry() {
		return isHungry;
	}

	public void setHungry(boolean isHungry) {
		this.isHungry = isHungry;
	}

	public Animal(Point2D p) {
		super(p);
		super.setName(getName());	
	}

	public void moveRandom() {
		Point2D p = getPosition().plus(Direction.random().asVector());
		System.out.println(p);
		if(ImageMatrixGUI.getInstance().isWithinBounds(p) && willNotCollide(p))
			setPosition(p);
	}

	@Override
	public  int getLayer() {
		return 3;
	}

	public void eat(Point2D p) {
		FarmObject obj = Farm.getInstance().findFarmObject(p);
		if(obj instanceof Vegetable) {
			Vegetable v = (Vegetable) obj;
			if(v.isRipe()) {
				Farm.getInstance().remove(v);
				if(this instanceof Chicken) {
					((Chicken)this).setMoving(false);
				}else {
					((Sheep)this).interact();
				}	
			}
		}
	}
}