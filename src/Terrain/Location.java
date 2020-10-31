package Terrain;

import processing.core.PVector;

public class Location {
	
	// declare variables
	int row, col;
	PVector pos;
	private float height, temperature, density;
	private int state;
	
	public Location(int _row, int _col, float _x, float _y) {
		// initialise variables
		row = _row;
		col = _col;
		pos = new PVector(_x, _y);
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public PVector getPos() {
		return pos;
	}

	public void setPos(PVector pos) {
		this.pos = pos;
	}
	
	public void print() {
		System.out.println("Location: " + row + ", " + col);
		System.out.println("    Height: " + height);
		System.out.println("    Density: " + density);
		System.out.println("    State: " + state);
	}
}
