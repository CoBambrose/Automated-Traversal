package Terrain;

import automatedTraversal.automatedTraversal;
import processing.core.PVector;

public class Terrain {

	automatedTraversal sketch;
	Location[][] map;
	int width, height;
	int rows, cols;
	int xoff, yoff;
	
	private Generator generator;
	private Renderer renderer;
	
	public Terrain(automatedTraversal _sketch, int _width, int _height, int _rows, int _cols) {
		System.out.println("Terrain created");
		width = _width;
		height = _height;
		rows = _rows;
		cols = _cols;
		generator = new Generator(_sketch, width, height, rows, cols, 20f, 200f);
		renderer = new Renderer(_sketch, width, height);
		map = generator.generateMap(xoff, yoff);
	}
	
	public void update() {
		map = generator.generateMap(xoff, yoff);
		renderer.update(map);
	}
	
	public void move(PVector _dir) {
		xoff += (int)_dir.x;
		yoff += (int)_dir.y;
	}
	
	public Location getCenterPoint() {
		return map[(int)(cols/4)][(int)(rows/4)];
	}
}
