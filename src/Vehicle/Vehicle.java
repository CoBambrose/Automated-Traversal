package Vehicle;

import automatedTraversal.automatedTraversal;
import processing.core.PVector;

public class Vehicle {

	automatedTraversal sketch;
	PVector pos;
	PVector dim;
	
	public Vehicle(automatedTraversal _sketch, int _width, int _height, int _rows, int _cols) {
		System.out.println("Vehicle created");
		sketch = _sketch;
		pos = new PVector(
			_sketch.width / 2f,
			_sketch.height / 2f,
			-50f
		);
		dim = new PVector(
			(float)_width /(float)_cols/2,
			(float)_height/(float)_rows/2,
			(float)_height/(float)_rows/2
		);
	}
	
	public void update() {
		render();
	}
	
	public void render() {
		sketch.push();
		sketch.translate(pos.x, pos.y, pos.z);
		sketch.fill(255);
		sketch.box(dim.x, dim.y, dim.z);
		sketch.pop();
	}
	
}
