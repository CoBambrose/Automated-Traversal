package Terrain;

import automatedTraversal.automatedTraversal;
import processing.core.PVector;

public class Terrain {

	automatedTraversal sketch;
	Location[][] map;
	int width, height;
	int rows, cols;
	int xoff, yoff;
	float x,y,z;
	
	private Generator generator;
	private Renderer renderer;
	
	public Terrain(automatedTraversal _sketch, int _width, int _height, int _rows, int _cols, float _x, float _y, float _z) {
		System.out.println("Terrain created");
		width = _width;
		height = _height;
		rows = _rows;
		cols = _cols;
		x = _x;
		y = _y;
		z = _z;
		generator = new Generator(_sketch, width, height, rows, cols, 20f, 200f);
		renderer = new Renderer(_sketch, width, height);
		map = generator.generateMap(xoff, yoff);
	}
	
	public void update() {
		map = generator.generateMap(xoff, yoff);
		renderer.update(map, x, y, z - getCenterPoint().getHeight());
	}
	
	public void move(PVector _dir) {
		xoff += (int)_dir.x;
		yoff += (int)_dir.y;
	}
	
	public Location getCenterPoint() {
		return map[(int)(cols/2)][(int)(rows/2)];
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
}
