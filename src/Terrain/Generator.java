package Terrain;

import automatedTraversal.automatedTraversal;
import processing.core.PApplet;

public class Generator {
	
	int width, height, rows, cols;
	float roughness, magnitude;
	automatedTraversal sketch;
	Location[][] map;
	
	public Generator(automatedTraversal _sketch, int _width, int _height, int _rows, int _cols, float _roughness, float _magnitude) {
		System.out.println("Generator created");
		width  = _width;
		height = _height;
		rows = _rows;
		cols = _cols;
		roughness = _roughness;
		magnitude = _magnitude;
		sketch = _sketch;
		map = generateMap(0f, 0f);
	}
	
	public Location[][] generateMap(float _xoff, float _yoff) {
		Location[][] temp = new Location[cols][rows];
		float xscl = width / cols;
		float yscl = height / rows;
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				temp[i][j] = new Location(j, i, i*xscl, j*yscl);
				float h = PApplet.map(sketch.noise((i+_xoff)/roughness, (j+_yoff)/roughness), 0, 1, -magnitude, magnitude);
				temp[i][j].setHeight(h);
				// temperature(0 - 100 C)
				temp[i][j].setTemperature(PApplet.map(sketch.noise((i+_xoff)/10f+100f, (j+_yoff)/10f+100f), 0, 1, 0, 100));
				// density(0 - 10 g/cm3)
				temp[i][j].setDensity(PApplet.map(sketch.noise((i+_xoff)/10f+100f, (j+_yoff)/10f+100f), 0, 1, 10, 0));
			}
		}
		return temp;
	}
	
}
