package automatedTraversal;

import Terrain.Terrain;
import Vehicle.Vehicle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class automatedTraversal extends PApplet {
	
	public Terrain terrain;
	public Vehicle rover;
	float terrainX;
	float terrainY;
	float terrainZ;
	
	public automatedTraversal() {}
	
	public void settings() {
		size(800,600, P3D);
	}

	public void setup() {
		int terrainWidth = width*2;
		int terrainHeight = height*2;
		int terrainCols = 40;
		int terrainRows = 40;
		
//		terrainX = -(float)width/2f;
//		terrainY = -(float)height*(5f/6f);
//		terrainZ = -400f;
		
		terrainX = -(float)width/2f;
		terrainY = -(float)height*(1f/2f);
		terrainZ = -50f;
		
		terrain = new Terrain(this, terrainWidth, terrainHeight, terrainRows, terrainCols, terrainX, terrainY, terrainZ);
		rover = new Vehicle(this, terrainWidth, terrainHeight, terrainRows, terrainCols);
	}
	
	public void draw() {
		background(51);
		push();
		rotateX(PConstants.PI/6);
		terrain.update();
		rover.update(terrainX, terrainY, terrainZ, terrain.getCenterPoint().getHeight());
		pop();
	}
	
	public void keyPressed() {
		if (keyCode == LEFT) {
			terrain.move(new PVector(-1, 0));
		}
		if (keyCode == RIGHT) {
			terrain.move(new PVector(1, 0));
		}
		if (keyCode == UP) {
			terrain.move(new PVector(0, -1));
		}
		if (keyCode == DOWN) {
			terrain.move(new PVector(0, 1));
		}
	}
	
	public static void main(String[] args) {
		String[] PArgs = {"Automated Traversal"};
		automatedTraversal sketch = new automatedTraversal();
		PApplet.runSketch(PArgs, sketch);
	}

}
