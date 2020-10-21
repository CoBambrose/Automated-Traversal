package Vehicle;

import automatedTraversal.automatedTraversal;
import processing.core.PVector;

public class Vehicle {

	// declare variables
	automatedTraversal sketch;
	PVector pos;
	PVector dim;
	
	public Vehicle(automatedTraversal _sketch, int _width, int _height, int _rows, int _cols) {
		System.out.println("Vehicle created");
		// initialise variables
		sketch = _sketch;
		// set dimensions of vehicle cub(e/oid)
		dim = new PVector(
			(float)_width / (float)_cols / 2,
			(float)_width / (float)_cols / 2,
			(float)_width / (float)_cols / 2
		);
		// sets position of vehicle relative to the sketch
		pos = new PVector(
			_sketch.width / 2f,
			_sketch.height / 2f,
			-50f + dim.z/2
		);
	}
	
	// runs every frame
	public void update() {
		render();
	}
	
	// draw the vehicle to sketch
	public void render() {
		sketch.push(); // pushes transformation to stack
		sketch.translate(pos.x, pos.y, pos.z); // moves to location of vehicle
		sketch.fill(255); // sets vehicle colour
		sketch.box(dim.x, dim.y, dim.z); // draws cub(e/oid) with vehicle dimensions
		sketch.pop(); // pops transformations from stack
	}
	
}
