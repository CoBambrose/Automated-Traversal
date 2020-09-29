package Vehicle;

import Terrain.Location;
import automatedTraversal.automatedTraversal;
import processing.core.PVector;

public class Vehicle {

	automatedTraversal sketch;
	Location location;
	PVector dim;
	
	public Vehicle(automatedTraversal _sketch, Location _point) {
		System.out.println("Vehicle created");
		sketch = _sketch;
		location = _point;
	}
	
	public void update(Location _location) {
		render(_location);
	}
	
	public void render(Location _location) {
		location = _location;
		sketch.push();
		sketch.translate(-sketch.width/2+location.getPos().x, -sketch.height/2+location.getPos().y, location.getHeight()-200);
		sketch.fill(255);
		sketch.box(10,10,10);
		sketch.pop();
	}
	
}
