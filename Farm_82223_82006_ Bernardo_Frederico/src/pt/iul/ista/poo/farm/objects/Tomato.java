package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Tomato extends Vegetable {
	
	private int points = 3;
	private static final int RIPEAGE = 15;
	private static final int ROTTENAGE = 25;
	
	public Tomato(Point2D p) {
		super(p);
	}

	@Override
	public void timeAdvance() {
		if((!isRipe() && !isRotten()) ) {
			incrementProgress();
		}
		if((!isRotten()) && isRipe()) {
			incrementProgress();
		}
		if(getProgress() >= RIPEAGE  && getTakenCareOf()) {
			setName("tomato");
			setRipe(true);
		}
		if(getProgress() >= ROTTENAGE && getTakenCareOf()) {
			setName("bad_tomato");
			setRotten(true);
			setRipe(false);
		}
	}
	
	@Override
	public void interact() {
		if(!isRipe() || !isRotten()) {
			setTakenCareOf(true);
		}
		pluck();
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public String toString() {	
		return super.toString()+";"+getTakenCareOf();
	}

	@Override
	public void fixState() {
		if(getProgress() >= RIPEAGE) {
			setName("tomato");
			setRipe(true);
		}
		if(getProgress() >= ROTTENAGE) {
			setName("bad_tomato");
			setRotten(true);
			setRipe(false);
		}
	}	
	
	
}