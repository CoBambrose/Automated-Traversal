package automatedTraversal;

import Terrain.Terrain;
import Vehicle.Vehicle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class automatedTraversal extends PApplet {
	
	public Terrain terrain;
	public Vehicle rover;
	
	public automatedTraversal() {
		
	}
	
	public void settings() {
		size(800,600, P3D);
	}

	public void setup() {
		terrain = new Terrain(this, width*2, height*2, 40, 40);
		rover = new Vehicle(this, terrain.getCenterPoint());
	}
	
	public void draw() {
		background(51);
		push();
		translate(width/2, height/2, 0);
		rotateX(PConstants.PI/6);
		terrain.update();
		rover.update(terrain.getCenterPoint());
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
