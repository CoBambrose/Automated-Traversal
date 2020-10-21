package Terrain;

import automatedTraversal.automatedTraversal;
import processing.core.PApplet;
import processing.core.PConstants;

public class Renderer {

	// declare variables
	float width, height;
	automatedTraversal sketch;
	
	public Renderer(automatedTraversal _sketch, int _width, int _height) {
		System.out.println("Renderer created");
		
		// initialise variables
		width = (float)_width;
		height = (float)_height;
		sketch = _sketch;
	}
	
	public void update(Location[][] _map, float _xoff, float _yoff, float _zoff) {
		// render the terrain
		render(_map, _xoff, _yoff, _zoff);
	}
	
	// render the terrain
	public void render(Location[][] _map, float _xoff, float _yoff, float _zoff) {
		int rows = _map[0].length;
		int cols = _map.length;
		sketch.push(); // begin transformation stack
		sketch.translate(_xoff, _yoff, _zoff); // offset by terrain location in sketch
		sketch.directionalLight(255,255,255,0,0,-1); // place a white light at the top of the scene facing down (-Y)
		sketch.strokeWeight(.5f); // set thickness of lines
		
		// for each column in terrain
		for (int i = 0; i < cols-1; i++) {
			sketch.beginShape(PConstants.TRIANGLE_STRIP); // begin a triangle strip
			sketch.push(); // push another transformation to stack
			// for each node in column
			for (int j = 0; j < rows; j++) {
				// calculate colours corresponding to location properties
				float temp = PApplet.map(_map[i][j].getTemperature(), 0, 100, 0, 255);
				float density = PApplet.map(_map[i][j].getDensity(), 0, 10, 255, 0);
				// set fill colour for triangle
				sketch.fill(temp, 0, 255-temp);
				// set border colour for triangle
				sketch.stroke(density);
				// place 2 vertices from on either side of T strip
				sketch.vertex(_map[i][j].pos.x, _map[i][j].pos.y, _map[i][j].getHeight());
				sketch.vertex(_map[i+1][j].pos.x, _map[i+1][j].pos.y, _map[i+1][j].getHeight());
			}
			sketch.pop(); // pop transformation from stack
			sketch.endShape(); // end triangle strip
		}
		
		sketch.pop();
	}
}
