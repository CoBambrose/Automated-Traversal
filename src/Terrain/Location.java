package Terrain;

import processing.core.PVector;

public class Location {
	
	// declare variables
	int row, col;
	PVector pos;
	private float height, temperature, density;
	private float normalHeight, normalTemperature, normalDensity;
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

	public void setHeight(float normalHeight, float height) {
		this.normalHeight = normalHeight;
		this.height = height;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float normalTemperature, float temperature) {
		this.normalTemperature = normalTemperature;
		this.temperature = temperature;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float normalDensity, float density) {
		this.normalDensity = normalDensity;
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
	
	public double[] getNormalisedProperties() {
		double[] returnVal = new double[3];
		returnVal[0] = (double)normalHeight;
		returnVal[1] = (double)normalTemperature;
		returnVal[2] = (double)normalDensity;
		return returnVal;
	}
	
	public void print() {
		System.out.println("Location: " + row + ", " + col);
		System.out.println("    Height: " + height);
		System.out.println("    Density: " + density);
		System.out.println("    State: " + state);
	}
}
