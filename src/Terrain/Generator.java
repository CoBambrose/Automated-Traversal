package Terrain;

import automatedTraversal.automatedTraversal;
import processing.core.PApplet;

public class Generator {
	
	// declare variables
	int width, height, rows, cols;
	float roughness, magnitude, melting_point;
	automatedTraversal sketch;
	Location[][] map;
	
	public Generator(automatedTraversal _sketch, int _width, int _height, int _rows, int _cols, float _roughness, float _magnitude) {
		System.out.println("Generator created");
		
		// initialise variables
		width  = _width;
		height = _height;
		rows = _rows;
		cols = _cols;
		roughness = _roughness;
		magnitude = _magnitude;
		melting_point = (float) (100.0 * 0.5);
		sketch = _sketch;
		
		// generate terrain map
		map = generateMap(0f, 0f);
	}
	
	// generate terrain map
	public Location[][] generateMap(float _xoff, float _yoff) {
		// create temporary variable for the map
		Location[][] tempMap = new Location[cols][rows];
		
		// determine distances between location nodes
		float colSize = (float)width / (float)cols;
		float rowSize = (float)height / (float)rows;
		
		// for each node in the map
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				// initialise location
				tempMap[i][j] = new Location(j, i, i*colSize, j*rowSize);
				// height(arbitrary units)
				float h = PApplet.map(sketch.noise((i+_xoff)/roughness, (j+_yoff)/roughness), 0, 1, -magnitude, magnitude);
				tempMap[i][j].setHeight(h);
				// temperature(0 - 100 C)
				float t = PApplet.map(sketch.noise((i+_xoff)/10f+100f, (j+_yoff)/10f+100f), 0, 1, 0, 100);
				tempMap[i][j].setTemperature(t);
				// density(0 - 10 g/cm3)
				float d = PApplet.map(sketch.noise((i+_xoff)/10f+100f, (j+_yoff)/10f+100f), 0, 1, 10, 0);
				tempMap[i][j].setDensity(d);
				// state(0 - Solid, 1 - Liquid)
				int s = 0;
				// if temperature is greater than melting point, it is liquid
				if (t > melting_point) { s = 1; }
				tempMap[i][j].setState(s);
			}
		}
		return tempMap;
	}
	
}
