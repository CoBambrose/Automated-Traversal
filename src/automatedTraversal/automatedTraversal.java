package automatedTraversal;

import NeuralNetwork.NeuralNetwork;
import Terrain.Terrain;
import Vehicle.Vehicle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class automatedTraversal extends PApplet {
	
	public Terrain terrain;
	public Vehicle vehicle;
	float terrainX; // x,y,z offsets for drawing terrain in 3D space
	float terrainY;
	float terrainZ;
	boolean trainingMode = true;
	
	public automatedTraversal() {}
	
	public void settings() {
		size(1200, 900, P3D); // set size of canvas and specify it as 3D
	}
	
	// like a constructor function run once at the beginning
	public void setup() {
		int terrainWidth = width*2; // set dimensions of the terrain
		int terrainHeight = height*2;
		int terrainCols = 40; // set resolution of terrain
		int terrainRows = 40;
		
		terrainX = -(float)width/2f; // initialise offsets
		terrainY = -(float)height*(1f/2f);
		terrainZ = -50f;
		
		// initialise terrain object
		terrain = new Terrain(this, terrainWidth, terrainHeight, terrainRows, terrainCols, terrainX, terrainY, terrainZ);
		// initialise rover object
		vehicle = new Vehicle(this, terrainWidth, terrainHeight, terrainRows, terrainCols);
		// load the rover's last trained brain
//		vehicle.brain = NeuralNetwork.deserialise();
	}
	
	// a draw loop executed once every frame
	public void draw() {
		background(51); // resets window to grey
		
		push(); // starts a transformation stack
		
		rotateX(PConstants.PI/6); // rotates canvas
		translate(0, -height/6f, 0);
		
		// execute objects' updates
		terrain.update();
		vehicle.update();
		
		pop(); // reverts all transformations to previous push()
		
		// display screen
//		fill(0,0,0,100);
//		translate(width/2f, height*(3f/4f), height*(3f/4f));
//		box(width, height/2f, 1f);
		
		if (trainingMode && frameCount == 1) {
			vehicle.train(terrain);
		}
	}
	
	public void keyPressed() {
		if (keyCode == LEFT) {
			terrain.move(new PVector(-1, 0));
			vehicle.train(terrain);
		}
		if (keyCode == RIGHT) {
			terrain.move(new PVector(1, 0));
			vehicle.train(terrain);
		}
		if (keyCode == UP) {
			terrain.move(new PVector(0, -1));
			vehicle.train(terrain);
		}
		if (keyCode == DOWN) {
			terrain.move(new PVector(0, 1));
			vehicle.train(terrain);
		}
	}
	
	// Initialise sketch and run it using processing
	public static void main(String[] args) {
		String[] PArgs = {"Automated Traversal"};
		automatedTraversal sketch = new automatedTraversal();
		PApplet.runSketch(PArgs, sketch);
	}

}
