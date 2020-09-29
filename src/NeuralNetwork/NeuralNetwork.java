package NeuralNetwork;

public class NeuralNetwork {
	
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
		this.inputNodes = _inputNodes;
		this.hidden1Nodes = _hidden1Nodes;
		this.hidden2Nodes = _hidden2Nodes;
		this.outputNodes = _outputNodes;

		this.weights = new Matrix[3];
		this.weights[0] = new Matrix(this.hidden1Nodes, this.inputNodes);
		this.weights[1] = new Matrix(this.hidden2Nodes, this.hidden1Nodes);
		this.weights[2] = new Matrix(this.outputNodes, this.hidden2Nodes);
		for (int i = 0; i < 3; i++) {this.weights[i].randomise();}


		this.biases = new Matrix[3];
		this.biases[0] = new Matrix(this.hidden1Nodes, 1);
		this.biases[1] = new Matrix(this.hidden2Nodes, 1);
		this.biases[2] = new Matrix(this.outputNodes, 1);
		for (int i = 0; i < 3; i++) {this.biases[i].randomise();}
		
		this.learning_rate = 0.1d;
	}
	
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
	
	public double train(double[] _inputs, double[] _targets) {
		Matrix inputs = Matrix.toMatrix(_inputs);
		Matrix targets = Matrix.toMatrix(_targets);
		
		Matrix hidden1 = Matrix.multiply(this.weights[0], inputs);
		hidden1.add(this.biases[0]);
		hidden1.sigmoid();
		
		Matrix hidden2 = Matrix.multiply(this.weights[1], hidden1);
		hidden2.add(this.biases[1]);
		hidden2.sigmoid();
		
		Matrix outputs = Matrix.multiply(this.weights[2], hidden2);
		outputs.add(this.biases[2]);
		outputs.sigmoid();
		
		Matrix output_error = Matrix.subtract(targets, outputs);
		Matrix hidden2_error = Matrix.multiply( Matrix.transpose(this.weights[2]), output_error );
		Matrix hidden1_error = Matrix.multiply( Matrix.transpose(this.weights[1]), hidden2_error );
		
		Matrix output_gradient = outputs.dsigmoid().hadamard(output_error).scale(this.learning_rate);
		Matrix hidden2_gradient = hidden2.dsigmoid().hadamard(hidden2_error).scale(this.learning_rate);
		Matrix hidden1_gradient = hidden1.dsigmoid().hadamard(hidden1_error).scale(this.learning_rate);

		Matrix output_delta = Matrix.multiply(output_gradient, Matrix.transpose(hidden2));
		Matrix hidden2_delta = Matrix.multiply(hidden2_gradient, Matrix.transpose(hidden1));
		Matrix hidden1_delta = Matrix.multiply(hidden1_gradient, Matrix.transpose(inputs));
		
		this.biases[2].add(output_gradient);
		this.biases[1].add(hidden2_gradient);
		this.biases[0].add(hidden1_gradient);

		this.weights[2].add(output_delta);
		this.weights[1].add(hidden2_delta);
		this.weights[0].add(hidden1_delta);
		
		double MSE = 0f;
		for (int i = 0; i < output_error.rows; i++) {
			for (int j = 0; j < output_error.cols; j++) {
				MSE += Math.pow(output_error.data[i][j], 2);
			}
		}
		
		return MSE;
	}
}

































