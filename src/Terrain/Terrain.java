package Terrain;

import automatedTraversal.automatedTraversal;
import processing.core.PVector;

public class Terrain {

	// declare variables
	automatedTraversal sketch;
	Location[][] map;
	int width, height;
	int rows, cols;
	public int xoff, yoff;
	float x,y,z;
	
	// declare components
	private Generator generator;
	private Renderer renderer;
	
	public Terrain(automatedTraversal _sketch, int _width, int _height, int _rows, int _cols, float _x, float _y, float _z) {
		System.out.println("Terrain created");
		
		// initialise variables
		width = _width;
		height = _height;
		rows = _rows;
		cols = _cols;
		x = _x;
		y = _y;
		z = _z;
		
		// initialise components
		generator = new Generator(_sketch, width, height, rows, cols, 20f, 200f);
		renderer = new Renderer(_sketch, width, height);
		// create map using generator
		map = generator.generateMap(xoff, yoff);
	}
	
	// runs every draw loop
	public void update() {
		// regenerate map based on new position of rover
		map = generator.generateMap(xoff, yoff);
		// redraw the terrain
		renderer.update(map, x, y, z - getCenterPoint().getHeight());
	}
	
	// "moves" terrain by 1 unit ( simulates rover moving )
	public void move(PVector _dir) {
		xoff += (int)_dir.x;
		yoff += (int)_dir.y;
	}
	
	// gets location closest to the center of the terrain
	public Location getCenterPoint() {
		return map[(int)(cols/2)][(int)(rows/2)];
	}
	
	public int[] getCenterCoords() {
		int[] coords = {(int)(cols/2), (int)(rows/2)};
		return coords;
	}
	
	public Location getLocation(int _col, int _row) {
		return map[_col][_row];
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
}
