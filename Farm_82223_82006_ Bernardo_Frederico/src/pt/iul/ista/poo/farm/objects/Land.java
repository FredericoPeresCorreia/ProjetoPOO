package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public class Land extends FarmObject implements Interactable {

	private boolean isPlowed = false;
	private boolean isRocky = (Math.random() < 0.1);

	public Land(Point2D p) {
		super(p);
	}

	@Override
	public int getLayer() {
		return 0;
	}

	public String getName() {
		if(!isRocky) {
			return super.getName();
		}
		else {
			return "rock";
		}
	}

	public boolean isPlowed() {
		return isPlowed;
	}

	public boolean isRocky() {
		return isRocky;
	}

	public void setPlowed(boolean isPlowed) {
		this.isPlowed = isPlowed;
		if(isPlowed) {
			setName("plowed");
		}else {
			setName("land");
		}
	}

	@Override
	public void interact() {
		if(!isRocky()) {
			if(isPlowed()) {
				Farm.getInstance().plantRandom(getPosition());
			}else {
				setPlowed(true);
			}
		}	
	}

	public void setRocky(boolean isRocky) {
		this.isRocky = isRocky;
		if(isRocky) {
			setName("rock");
		}else {
			setName("land");
		}
	}

	@Override
	public String toString() {
		return super.toString() +";"+isRocky() +";"+isPlowed();
	}
}