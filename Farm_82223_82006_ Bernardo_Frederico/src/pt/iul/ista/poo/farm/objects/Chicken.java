package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public class Chicken extends Animal {

	private boolean moving = Math.random() < 0.5;
	private int points = 2;
	private boolean isLaying = false;
	private static final int LAYAGE = 10;
	
	public Chicken(Point2D p) {
		super(p);
		setHungry(true);
	}

	public boolean isMoving() {
		return moving;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	@Override
	public void eat(Point2D p) {
		if(Farm.getInstance().findFarmObject(p) instanceof Tomato) {
			super.eat(p);
		}
	}

	@Override
	public void timeAdvance() {
		incrementProgress();
		if(!isLaying() && getProgress() >= LAYAGE) {
			setLaying(true);
		}
		if(moving){
			moveRandom();
			setMoving(false);
		}else{
			setMoving(true);		
		}
	}
	
	public void lay() {
		if(canGenerate(getPosition())) {
			Farm.getInstance().add(new Egg(getPosition()));
			setProgress(0);
			setLaying(false);
		}	
	}

	@Override
	public void interact() {
		Farm.getInstance().remove(this);
		addPoints();
	}
	
	@Override
	public int getPoints() {
		return points;
	}

	public boolean isLaying() {
		return isLaying;
	}

	public void setLaying(boolean isLaying) {
		this.isLaying = isLaying;
	}

	@Override
	public String toString() {
		return super.toString() +";"+ isMoving();
	}
}