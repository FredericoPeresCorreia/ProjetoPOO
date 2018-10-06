package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Sheep extends Animal {

	private int points = 1;
	private final static int HUNGRYAGE = 10;
	private final static int FAMISHEDAGE = 20;

	public Sheep(Point2D p) {
		super(p);
	}

	@Override
	public void timeAdvance() {
		incrementProgress();
		if(getProgress() >= HUNGRYAGE && getProgress() < FAMISHEDAGE) {
			setHungry(true);
			moveRandom();
		}else if(getProgress() >= FAMISHEDAGE) {
			setName("famished_sheep");
			setHungry(false);
		}else {
			addPoints();
		}
	}
	
	@Override
	public void fixState() {
		if(getProgress() >= HUNGRYAGE && getProgress() < FAMISHEDAGE) {
			setHungry(true);
		}
		if(getProgress() >= FAMISHEDAGE ) {
			setName("famished_sheep");
			setHungry(false);
		}
	}

	@Override
	public void interact() {
		System.out.println(getProgress());
		setProgress(0);
		setName("sheep");
		setHungry(false);
	}

	@Override
	public int getPoints() {
		return points;
	}
}