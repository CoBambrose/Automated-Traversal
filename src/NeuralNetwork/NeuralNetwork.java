package NeuralNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NeuralNetwork implements java.io.Serializable {
	
	// declare variables
	private static final long serialVersionUID = -6501992245045165575L;
	private int inputNodes;
	private int hidden1Nodes;
	private int hidden2Nodes;
	private int outputNodes;
	private double learning_rate;
	public Matrix[] weights;
	public Matrix[] biases;

	public NeuralNetwork(
			int _inputNodes,
			int _hidden1Nodes,
			int _hidden2Nodes,
			int _outputNodes
	) {
		System.out.println("Neural Network created");
		
		// initialise layer sizes
		this.inputNodes = _inputNodes;
		this.hidden1Nodes = _hidden1Nodes;
		this.hidden2Nodes = _hidden2Nodes;
		this.outputNodes = _outputNodes;

		// intialise weights for all layers
		this.weights = new Matrix[3];
		this.weights[0] = new Matrix(this.hidden1Nodes, this.inputNodes);
		this.weights[1] = new Matrix(this.hidden2Nodes, this.hidden1Nodes);
		this.weights[2] = new Matrix(this.outputNodes, this.hidden2Nodes);
		for (int i = 0; i < 3; i++) {this.weights[i].randomise();}

		// intialise biases for all layers
		this.biases = new Matrix[3];
		this.biases[0] = new Matrix(this.hidden1Nodes, 1);
		this.biases[1] = new Matrix(this.hidden2Nodes, 1);
		this.biases[2] = new Matrix(this.outputNodes, 1);
		for (int i = 0; i < 3; i++) {this.biases[i].randomise();}
		
		// set global learning rate
		this.learning_rate = 0.1d;
	}
	
	// feed forward algorithm
	public double[] feed(double[] _inputs) {
		Matrix inputs = Matrix.toMatrix(_inputs);
		
		Matrix hidden1 = Matrix.multiply(this.weights[0], inputs);
		hidden1.add(this.biases[0]);
		hidden1.sigmoid();
		
		Matrix hidden2 = Matrix.multiply(this.weights[1], hidden1);
		hidden2.add(this.biases[1]);
		hidden2.sigmoid();
		
		Matrix outputs = Matrix.multiply(this.weights[2], hidden2);
		outputs.add(this.biases[2]);
		outputs.sigmoid();
		
		return outputs.toArray();
	}
	
	// back-propagation algorithm
	public double train(double[] _inputs, double[] _targets) {
		// inputs to train with
		Matrix inputs = Matrix.toMatrix(_inputs);
		// expected outputs for inputs given
		Matrix targets = Matrix.toMatrix(_targets);
		
		// feed forward
		Matrix hidden1 = Matrix.multiply(this.weights[0], inputs);
		hidden1.add(this.biases[0]);
		hidden1.sigmoid();
		
		Matrix hidden2 = Matrix.multiply(this.weights[1], hidden1);
		hidden2.add(this.biases[1]);
		hidden2.sigmoid();
		
		Matrix outputs = Matrix.multiply(this.weights[2], hidden2);
		outputs.add(this.biases[2]);
		outputs.sigmoid();
		
		// calculate errors
		Matrix output_error = Matrix.subtract(targets, outputs);
		Matrix hidden2_error = Matrix.multiply( Matrix.transpose(this.weights[2]), output_error );
		Matrix hidden1_error = Matrix.multiply( Matrix.transpose(this.weights[1]), hidden2_error );
		
		// calculate gradients at current position in MSE curve
		Matrix output_gradient = outputs.dsigmoid().hadamard(output_error).scale(this.learning_rate);
		Matrix hidden2_gradient = hidden2.dsigmoid().hadamard(hidden2_error).scale(this.learning_rate);
		Matrix hidden1_gradient = hidden1.dsigmoid().hadamard(hidden1_error).scale(this.learning_rate);

		// calculate amounts to adjust weights
		Matrix output_delta = Matrix.multiply(output_gradient, Matrix.transpose(hidden2));
		Matrix hidden2_delta = Matrix.multiply(hidden2_gradient, Matrix.transpose(hidden1));
		Matrix hidden1_delta = Matrix.multiply(hidden1_gradient, Matrix.transpose(inputs));
		
		// adjust biases by the gradients
		this.biases[2].add(output_gradient);
		this.biases[1].add(hidden2_gradient);
		this.biases[0].add(hidden1_gradient);

		// adjust weights by the deltas
		this.weights[2].add(output_delta);
		this.weights[1].add(hidden2_delta);
		this.weights[0].add(hidden1_delta);
		
		// calculate mean squared error for outputs
		double MSE = 0f;
		for (int i = 0; i < output_error.rows; i++) {
			for (int j = 0; j < output_error.cols; j++) {
				MSE += Math.pow(output_error.data[i][j], 2);
			}
		}
		
		// return the total error
		return MSE;
	}
	
	public void serialise() {
		try {
			String outFile = "serialised/nn.txt";
			FileOutputStream fos = new FileOutputStream(outFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			fos.close();
			System.out.println("Serialised data is saved in " + outFile);
		} catch (IOException i) {
			System.out.println("Not able to be serialised");
			i.printStackTrace();
		}
	}
	
	public static NeuralNetwork deserialise() {
		try {
			String inFile = "serialised/nn.txt";
			FileInputStream fis = new FileInputStream(inFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			NeuralNetwork nn = (NeuralNetwork) ois.readObject();
			fis.close();
			ois.close();
			System.out.println("Deserialised data saved in " + inFile);
			return nn;
		} catch (IOException | ClassNotFoundException i) {
			System.out.println("Not able to be deserialised");
			i.printStackTrace();
		}
		return null;
	}
}
