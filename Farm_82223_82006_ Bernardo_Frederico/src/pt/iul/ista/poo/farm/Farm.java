package pt.iul.ista.poo.farm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Scanner;

import pt.iul.ista.poo.farm.objects.Animal;
import pt.iul.ista.poo.farm.objects.Cabage;
import pt.iul.ista.poo.farm.objects.Chicken;
import pt.iul.ista.poo.farm.objects.Egg;
import pt.iul.ista.poo.farm.objects.FarmObject;
import pt.iul.ista.poo.farm.objects.Farmer;
import pt.iul.ista.poo.farm.objects.Interactable;
import pt.iul.ista.poo.farm.objects.Land;
import pt.iul.ista.poo.farm.objects.Sheep;
import pt.iul.ista.poo.farm.objects.Tomato;
import pt.iul.ista.poo.farm.objects.Updatable;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farm implements Observer {

	// TODO
	//
	private static final String SAVE_FNAME = "Farm.txt";
	private static final int MIN_X = 5;
	private static final int MIN_Y = 5;
	private static final int space = 32;
	private static final int lKey = 76;
	private static final int sKey = 83;
	private static Farm INSTANCE;

	private int points;
	private int max_x;
	private int max_y;
	private boolean interact;
	private Farmer Joao;
	private List<FarmObject> objects;

	private Farm(int max_x, int max_y) {
		if (max_x < MIN_X || max_y < MIN_Y)
			throw new IllegalArgumentException();

		this.max_x = max_x;
		this.max_y = max_y;

		INSTANCE = this;

		ImageMatrixGUI.setSize(max_x, max_y);


		loadScenario();
	}

	//Basic getters and setters go here
	public int getMax_x() {
		return max_x;
	}

	public int getMax_y() {
		return max_y;
	}

	private List<Animal> getLayHatchers(){
		List<Animal> chickensEggs = new ArrayList<>();
		for(FarmObject obj :  objects) {
			if( obj instanceof Chicken) {
				Chicken c = (Chicken)obj;
				if(c.isLaying())
					chickensEggs.add(c);
			}
			if(obj instanceof Egg) {
				Egg e = (Egg) obj;
				if(e.isHatching())
					chickensEggs.add(e);
			}
		}
		return chickensEggs;
	}

	public List<Animal> getEaters() {
		List<Animal> eaters = new ArrayList<>();
		for (FarmObject obj : objects) {
			if (obj instanceof Animal) {
				Animal a = (Animal) obj;
				if (a.isHungry())
					eaters.add(a);
			}
		}
		return eaters;
	}	

	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void setJoao(Farmer f) {
		Joao = f;
	}
	
	

	//Methods that mess with the imageMatrix

	private void registerAll() {
		// TODO
		List<ImageTile> images = new ArrayList<ImageTile>();
		Joao = new Farmer(new Point2D(0, 0));
		objects.add(Joao);

		images.add(Joao);
		for (int i = 0; i < max_x; i++) {
			for (int j = 0; j < max_y; j++) {
				ImageTile land = new Land(new Point2D(i, j));
				images.add(land);
				objects.add(((FarmObject) land));
			}
		}
		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
		addAnimals();
	}

	private void loadScenario() {
		// TODO
		points = 0;
		interact = false;
		objects = new ArrayList<FarmObject>();
		registerAll();
	}

	public void add(FarmObject obj) {
		objects.add(obj);
		ImageMatrixGUI.getInstance().addImage(obj);
	}

	public void remove(FarmObject obj) {
		int layer = obj.getLayer();
		Point2D point = obj.getPosition();
		objects.remove(obj);
		ImageMatrixGUI.getInstance().removeImage(obj);
		if (layer == 1) {
			obj = findFarmObject(point);
			Land l = (Land) obj;
			l.setPlowed(false);
		}
	}

	@Override
	public void update(Observable gui, Object a) {
		System.out.println("Update sent " + a);
		if (a instanceof Integer) {
			int key = (Integer) a;
			if(key == sKey) {
				writeFarm();
			}
			if(key == lKey) {
				readFarm();
			}
			if (key == space) {
				interact = true;
			}
			if(Direction.isDirection(key)) {
				Point2D p = Joao.getPosition().plus(Direction.directionFor(key).asVector());
				if (interact) {
					interact(p);
				}else if (ImageMatrixGUI.getInstance().isWithinBounds(p) && Joao.willNotCollide(p)) {
					System.out.println("Update is a Direction " + Direction.directionFor(key));
					Joao.setPosition(p);
				}
				interact = false;
				allEat();
				updateObjects();
				allLayHatch();
				
			}
		}
		ImageMatrixGUI.getInstance().setStatusMessage("Points: " + points);
		ImageMatrixGUI.getInstance().update();
	}

	private void removeAll() {
		objects.clear();
		ImageMatrixGUI.getInstance().clearImages();
	}

	private void interact(Point2D p) { 
		FarmObject obj = findFarmObject(p);
		if (obj instanceof Interactable) {
			((Interactable) obj).interact();
		}
	}

	private void updateObjects() {
		for (FarmObject obj : objects) {
			if (obj instanceof Updatable) {
				((Updatable) obj).timeAdvance();
			}
		}	
	}

	public FarmObject findFarmObject(Point2D p) {
		FarmObject object = null;
		int highestLayer = 0;
		for (FarmObject obj : objects) {
			if (obj.getPosition().equals(p)) {
				if (obj.getLayer() >= highestLayer) {
					object = obj;
					highestLayer = obj.getLayer();
				}
			}
		}
		return object;
	}

	public void plantRandom(Point2D p) {
		Random r = new Random();
		if (r.nextDouble() < 0.5) {
			Tomato t = new Tomato(p);
			add(t);
		} else {
			Cabage c = new Cabage(p);
			add(c);
		}
	}

	public void addAnimals() {
		Point2D p;
		int chickenCount = 0;
		int sheepCount = 0;
		while(chickenCount!=2) {
			p = randomPoint();
			if(canSpawn(p)) {
				add(new Chicken(p));
				chickenCount++;
			}
		}
		while(sheepCount!=2) {
			p= randomPoint();
			if(canSpawn(p)) {
				add(new Sheep(p));
				sheepCount++;
			}
		}			
	}

	public void allEat() {
		List<Animal> eaters = getEaters();
		System.out.println(eaters);
		for (Animal a : eaters) {
			Point2D p = a.getPosition().plus(Direction.values()[((int)(Math.random()*4))].asVector());
			a.eat(p);
		}
	}
	
	private void allLayHatch() {
		List<Animal> list = getLayHatchers();
		for(Animal a : list) {
			if(a instanceof Chicken) {
				((Chicken)a).lay();
			}else {
				((Egg)a).hatch();
			}				
		}
	}

	private void readFarm() {
		try{
			Scanner s = new Scanner(new File(SAVE_FNAME));
			if(s.nextInt() == max_x && s.nextInt() == max_y) {
				removeAll();
				System.out.println("Dimensões corretas, irá começar a copia");
				int points = s.nextInt();
				setPoints(points);
				ImageMatrixGUI.getInstance().setStatusMessage("Points: " + points);
				while(s.hasNextLine()) {
					String[] data = s.nextLine().split(";");
					System.out.println(data[0]);
					FarmObject.createFarmObject(data); 
				}
			}else {
				System.out.println("Dimensões erradas");
			}
			s.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Ficheiro não encontrado!!");
		}
	}
	
	public void writeFarm() {
		try {
			PrintWriter file = new PrintWriter(new File(SAVE_FNAME));
			file.println(getMax_x() + " " + getMax_y());
			file.println(getPoints());
			for(FarmObject l : objects) {
				file.println(l);
			}
			if(file.checkError()) {
				System.out.println("Erro na escrito");
			}
			file.close();
		}catch (FileNotFoundException e) {
			System.out.println("erro a abrir");
		}
	}

	public Point2D randomPoint() {
		Point2D p = new Point2D((int) (Math.random() * (max_x - 1)), ((int) (Math.random() * (max_y - 1))));
		while(!ImageMatrixGUI.getInstance().isWithinBounds(p)) {
			p = new Point2D((int) (Math.random() * (max_x - 1)), ((int) (Math.random() * (max_y - 1))));
		} 
		return p;
	}

	public boolean canSpawn(Point2D p) {
		return (findFarmObject(p).getLayer() != 3); //? false : true;
	}

	// N�o precisa de alterar nada a partir deste ponto
	private void play() {
		ImageMatrixGUI.getInstance().addObserver(this);
		ImageMatrixGUI.getInstance().go();
	}

	public static Farm getInstance() {
		assert (INSTANCE != null);
		return INSTANCE;
	}

	public static void main(String[] args) {
		Farm f = new Farm(5, 7);
		f.play();
	}
}