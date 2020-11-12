package Vehicle;

import java.util.Arrays;

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
	public PVector update(Terrain _terrain, boolean _trainingMode) {
		PVector dir = new PVector(0,0);
		if (_trainingMode && sketch.frameCount == 0) {
			dir = learn(_terrain);
		} else if (_trainingMode && sketch.frameCount % 20 == 0) {
			dir = learn(_terrain);
		} else if (sketch.frameCount % 20 == 0) {
			dir = traverse(_terrain);
		}
		render();
		return dir;
	}
	
	// draw the vehicle to sketch
	public void render() {
		sketch.push(); // pushes transformation to stack
		sketch.translate(pos.x, pos.y, pos.z); // moves to location of vehicle
		sketch.fill(255); // sets vehicle colour
		sketch.box(dim.x, dim.y, dim.z); // draws cub(e/oid) with vehicle dimensions
		sketch.pop(); // pops transformations from stack
	}

	public PVector learn(Terrain _terrain) {
		double[] inputs = new double[27];
		double[] targets = new double[8];
		
		double[] current = _terrain.getLocation(_terrain.getCenterCoords()[0], _terrain.getCenterCoords()[1]).getNormalisedProperties();
		
		int highestIndex = -1;
		double highestHeuristic = 0d;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				double[] neighbour = _terrain.getLocation(_terrain.getCenterCoords()[0] + i - 1, _terrain.getCenterCoords()[1] + j - 1).getNormalisedProperties();
				
				// inputs array
				inputs[i*3+j + 0] = neighbour[0];
				inputs[i*3+j + 1] = neighbour[1];
				inputs[i*3+j + 2] = neighbour[2];
				
				// targets array
				double hScore = Math.abs(current[0] - neighbour[0]);
				double tScore = Math.pow(1d-Math.pow(neighbour[1], 2d), 1d/2d);
				double dScore = Math.pow(2d * neighbour[1] - Math.pow(neighbour[1], 2d), 1d/2d);
				
				if (i*3 + j < 4) {
					targets[i*3+j] = (hScore + tScore + dScore) / 3d;
					if (targets[i*3+j] > highestHeuristic) {
						highestHeuristic = targets[i*3+j];
						highestIndex = i*3+j;
					}
				} else if (i*3 + j > 4) {
					targets[i*3+j-1] = (hScore + tScore + dScore) / 3d;
					if (targets[i*3+j-1] > highestHeuristic) {
						highestHeuristic = targets[i*3+j-1];
						highestIndex = i*3+j-1;
					}
				}
			}
		}
		
		for (int i = 0; i < 8; i++) {
			targets[i] = 0.001d;
		}
		targets[highestIndex] = 0.999d;
		
		brain.train(inputs, targets);
		
		return indexToVector(highestIndex);
	}
	
	public PVector traverse(Terrain _terrain) {
		double[] inputs = new double[27];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				double[] neighbour = _terrain.getLocation(_terrain.getCenterCoords()[0] + i - 1, _terrain.getCenterCoords()[1] + j - 1).getNormalisedProperties();
				inputs[i*3+j + 0] = neighbour[0];
				inputs[i*3+j + 1] = neighbour[1];
				inputs[i*3+j + 2] = neighbour[2];
			}
		}
		
		double[] traversabilities = brain.feed(inputs);
		int highestIndex = -1;
		double highestVal = 0d;
		for (int i = 0; i < 8; i++) {
			if (traversabilities[i] > highestVal) {
				highestIndex = i;
				highestVal = traversabilities[i];
			}
		}

		for (double val : traversabilities) {
			System.out.println(val);
		}
		System.out.println();
		
		return indexToVector(highestIndex);
	}
	
	private PVector indexToVector(int _index) {
		if (_index > 3) {_index++;}
		
		int xDir = _index % 3 - 1;
		int yDir = _index / 3 - 1;
		
		return new PVector(xDir, yDir);
	}
	
}
