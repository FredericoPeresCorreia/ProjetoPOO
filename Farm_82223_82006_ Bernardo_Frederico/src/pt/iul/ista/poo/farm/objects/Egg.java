package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public class Egg extends Animal {  
	private boolean isHatching = false;
	private int points = 1;
	private static final int HATCHAGE = 20;
	
	public Egg(Point2D p) {
		super(p);
	}

	@Override
	public int getLayer() {
		return 3;
	}
	
	@Override
	public void timeAdvance() {
		incrementProgress();
		if(getProgress() == HATCHAGE) {
			setHatching(true);
		}
	}
	
	public void hatch() {
		Point2D p = randomAdjacentPoint();
		if(canSpawn(p)) {
			Farm.getInstance().add(new Chicken(p));
			Farm.getInstance().remove(this);
		}	
	}
	
	public void setHatching(boolean isHatching) {
		this.isHatching = isHatching;
	}

	@Override
	public void interact() {
		Farm.getInstance().remove(this);
		addPoints();
	}

	public boolean isHatching() {
		return isHatching;
	}

	@Override
	public int getPoints() {
		return points;
	}
}
