package Vehicle;

import NeuralNetwork.NeuralNetwork;
import Terrain.Location;
import Terrain.Terrain;
import automatedTraversal.automatedTraversal;
import processing.core.PApplet;
import processing.core.PVector;

public class Vehicle {

	// declare variables
	automatedTraversal sketch;
	PVector pos;
	PVector dim;
	public NeuralNetwork brain;
	
	public Vehicle(automatedTraversal _sketch, int _width, int _height, int _rows, int _cols) {
		System.out.println("Vehicle created");
		// initialise variables
		sketch = _sketch;
		// set dimensions of vehicle cub(e/oid)
		dim = new PVector(
			(float)_width / (float)_cols / 2,
			(float)_width / (float)_cols / 2,
			(float)_width / (float)_cols / 2
		);
		// sets position of vehicle relative to the sketch
		pos = new PVector(
			_sketch.width / 2f,
			_sketch.height / 2f,
			-50f + dim.z/2
		);
		
		// creates the vehicle's traversal AI
		brain = new NeuralNetwork(27, 10, 10, 8);
	}
	
	// runs every frame
	public void update() {
		render();
	}
	
	// draw the vehicle to sketch
	public void render() {
		sketch.push(); // pushes transformation to stack
		sketch.translate(pos.x, pos.y, pos.z); // moves to location of vehicle
		sketch.fill(255); // sets vehicle colour
		sketch.box(dim.x, dim.y, dim.z); // draws cub(e/oid) with vehicle dimensions
		sketch.pop(); // pops transformations from stack
	}

	public void train(Terrain terrain) {
		double[] inputs = new double[27];
		int[] centerCoords = terrain.getCenterCoords();
		double[] traversabilities = new double[8];
		double[] centerNormals = terrain.getLocation(centerCoords[0], centerCoords[1]).getNormalisedProperties();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// inputs array
				int[] neighbourCoords = {centerCoords[0]+i-1, centerCoords[1]+j-1};
				Location neighbour = terrain.getLocation(neighbourCoords[0], neighbourCoords[1]);
				inputs[3*(3*i+j)    ] = (double) neighbour.getHeight();
				inputs[3*(3*i+j) + 1] = (double) neighbour.getDensity();
				inputs[3*(3*i+j) + 2] = (double) neighbour.getTemperature();
				// targets array
				double[] neighbourNormals = neighbour.getNormalisedProperties();
				double hScore = (centerNormals[0] - neighbourNormals[0] + 1) / 2;
				double tScore = 1 - Math.sqrt(2 * neighbourNormals[1] - Math.pow(neighbourNormals[1], 2));
				double dScore = Math.sqrt(2 * neighbourNormals[2] - Math.pow(neighbourNormals[2], 2));
				if (3*i+j < 4) {
					traversabilities[3*i+j] = (hScore + tScore + dScore) / 3d;
				} else if (3*i+j > 4) {
					traversabilities[3*i+j-1] = (hScore + tScore + dScore) / 3d;
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			System.out.println(traversabilities[i]);
		}
		System.out.println("- - - - -");
	}
	
}
