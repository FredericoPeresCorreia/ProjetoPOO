package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class FarmObject implements ImageTile {

	private Point2D position;
	private String name = getClass().getSimpleName().toLowerCase(); 
	private int points;
	private int progress = 0;

	public FarmObject(Point2D p) {
		this.position = p;
	}
	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean willNotCollide(Point2D p) {
		return getLayer() > Farm.getInstance().findFarmObject(p).getLayer();
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPoints() {
		Farm.getInstance().setPoints(Farm.getInstance().getPoints() + getPoints());
	}
	
	public void incrementProgress() {
		progress++;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + ";" + getPosition() + ";" + getProgress(); 
	}
	
	public boolean canSpawn(Point2D p) {
		return Farm.getInstance().findFarmObject(p).getLayer()==3 ? false : true;
	}
	
	public boolean canGenerate(Point2D p) {
		return Farm.getInstance().findFarmObject(p).equals(this) ? true : false;
	}
	
	public Point2D randomAdjacentPoint() {
		Point2D p = getPosition().plus(Direction.random().asVector());
		while(!ImageMatrixGUI.getInstance().isWithinBounds(p)){
			p = getPosition().plus(Direction.random().asVector());
		}
		return p;
	}
	
	public void fixState() {
		
	}
	
	public static void createFarmObject(String[] data) {
		switch(data[0]) {
			case("Farmer"):
				Farmer Joao = new Farmer(pointFromString(data[1]));
				Farm.getInstance().add(Joao);
				Farm.getInstance().setJoao(Joao);
				break;
			case("Land"):
				Land l = new Land(pointFromString(data[1]));
				l.setRocky(data[3].startsWith("t"));
				l.setPlowed(data[4].startsWith("t"));
				Farm.getInstance().add(l);
				break;
			case("Cabage"):
				Cabage c = new Cabage(pointFromString(data[1]));
				c.setProgress(Integer.valueOf(data[2]));
				Farm.getInstance().add(c);
				c.fixState();
				break;
			case("Tomato"):
				Tomato t = new Tomato(pointFromString(data[1]));
				t.setProgress(Integer.valueOf(data[2]));
				t.setTakenCareOf(data[3].startsWith("t"));
				t.fixState();
				Farm.getInstance().add(t);
				break;
			case("Sheep"):
				Sheep s = new Sheep(pointFromString(data[1]));
				s.setProgress(Integer.valueOf(data[2]));
				s.fixState();
				Farm.getInstance().add(s);
				break;
			case("Chicken"):
				Chicken ch = new Chicken(pointFromString(data[1]));
				ch.setProgress(Integer.valueOf(data[2]));
				ch.setMoving(data[3].startsWith("t"));
				Farm.getInstance().add(ch);
				break;
			case("Egg"):
				Egg egg = new Egg(pointFromString(data[1]));
				egg.setProgress(Integer.valueOf(data[2]));
				Farm.getInstance().add(egg);
				break;
		}
	}
	
	private static Point2D pointFromString(String str) {
		Point2D p = new Point2D(Character.getNumericValue(str.charAt(1)),Character.getNumericValue(str.charAt(4)));
		return p;
	}
}	