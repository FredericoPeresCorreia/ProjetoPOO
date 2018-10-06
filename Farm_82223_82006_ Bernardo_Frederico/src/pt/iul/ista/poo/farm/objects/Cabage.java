package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Cabage extends Vegetable {
	
	private  int points = 2;
	private static final int RIPEAGE = 10;
	private static final int ROTTENAGE = 30;

	public Cabage(Point2D p) {
		super(p);
	}

	@Override
	public void timeAdvance() {
		if((!getTakenCareOf() && !isRotten()) || isRipe()) {
			incrementProgress();
		}
		if(getTakenCareOf() && (!isRipe() && !isRotten())) {
			incrementProgress();
			incrementProgress();
			setTakenCareOf(false);
		}
		if(getProgress() >= RIPEAGE && !isRipe() && !isRotten()) {
			setName("cabage");
			setRipe(true);
		}
		if(getProgress() >= ROTTENAGE && !isRotten()) {
			setName("bad_cabage");
			setRotten(true);
			setRipe(false);
		}
	}

	@Override
	public void interact() {
		setTakenCareOf(true);
		pluck();
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public void fixState() {
		if(getProgress() >= RIPEAGE) {
			setName("cabage");
			setRipe(true);
		}
		if(getProgress() >= ROTTENAGE) {
			setName("bad_cabage");
			setRotten(true);
			setRipe(false);
		}
	}	
}