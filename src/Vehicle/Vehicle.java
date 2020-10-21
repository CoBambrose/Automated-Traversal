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
			_cols/2 * ( (float)_width /(float)_cols ),
			_rows/2 * ( (float)_height/(float)_rows )
		);
		dim = new PVector(
			(float)_width /(float)_cols,
			(float)_height/(float)_rows,
			(float)_height/(float)_rows/2
		);
	}
	
	public void update(float _xoff, float _yoff, float _zoff, float _height) {
		render(_xoff, _yoff, _zoff, _height);
	}
	
	public void render(float _xoff, float _yoff, float _zoff, float _height) {
		sketch.push();
		sketch.translate(_xoff + pos.x, _yoff + pos.y, _zoff + _height);
		sketch.fill(255);
		sketch.box(dim.x, dim.y, dim.z);
		sketch.pop();
	}
	
}
