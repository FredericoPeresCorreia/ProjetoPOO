package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Farmer extends FarmObject {

	public Farmer(Point2D p) {
		super(p);
	}

	@Override
	public int getLayer() {
		return 3;
	}
}
