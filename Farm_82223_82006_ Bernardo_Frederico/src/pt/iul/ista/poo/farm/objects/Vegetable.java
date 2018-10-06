package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

/**
 * 
 * @author Bernardo Sequeira (82223) and Frederico Correia (82006)
 *
 */

public abstract class Vegetable extends FarmObject implements Updatable , Interactable {

	/**
	 * This boolean represents whether the vegetable is ripe or not.
	 */
	
	private boolean isRipe = false;
	
	/**
	 * This boolean represents whether the vegetable is rotten or not.
	 */
	
	private boolean isRotten = false;
	
	/**
	 * This boolean represents whether or not the vegetable has been take care of.
	 */
	
	private boolean takenCareOf = false;
	
	/**
	 * Constructor for the vegetable class, due to how the ImageMatrixGui pulls the images for the objects, we add the prefix "small", so that the vegetable starts out as small.
	 * @param p the coordinate of the grid where it will spawn.
	 */
	
	public Vegetable(Point2D p) {
		super(p);
		super.setName("small_" + getName());
	}
	
	/**
	 *  Removes an vegetable from the imageMatrix and the Objects list.
	 *  <p>If the vegetable is ripe it adds points to the total score.
	 */
	
	public void pluck() {
		if(isRipe || isRotten) {
			if(isRipe) { 
				addPoints();
			}	
			Farm.getInstance().remove(this);
		}	
	}
	
	/**
	 * Setter for the takenCareOf field.
	 * 
	 * @param takenCareOf the boolean value that will be assigned to the field.
	 */
	
	public void setTakenCareOf(boolean takenCareOf) {
		this.takenCareOf = takenCareOf;
	}
	
	/**
	 * Getter for the takenCareOf field.
	 * 
	 * @return Returns whether the vegetable has been taken care of or not.
	 */
	
	public boolean getTakenCareOf() {
		return takenCareOf;
	}
	
	/**	 	
	 * Getter for the isRipe field.
	 * @return Returns whether the vegetable is ripe or not.
	 */
	
	public  boolean isRipe() {
		return isRipe;
	}
	
	/**
	 * Setter for the isRipe field.
	 * @param isRipe the boolean value that will be assigned to the field.
	 */
	
	public  void setRipe(boolean isRipe) {
		this.isRipe = isRipe;
	}
	
	/**
	 * Getter for the isRipe field.
	 * @return Returns whether the vegetable is rotten or not 
	 */
	
	public  boolean isRotten() {
		return isRotten;
	}
	
	/**
	 * Setter for the isRotten field.
	 *
	 * @param isRotten the boolean value that will be assigned to the variable.
	 */
	
	public  void setRotten(boolean isRotten) {
		this.isRotten = isRotten;
	}
	
	@Override
	public int getLayer() {
		return 1;
	}
}