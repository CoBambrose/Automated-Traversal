package NeuralNetwork;

public class Matrix {
	
	int rows; // dimensions of matrix
	int cols;
	public double[][] data; // values stored in the matrix

	public Matrix(int _rows, int _cols) {
		rows = _rows;
		cols = _cols;
		data = new double[_rows][_cols];
		fill(0); // matrix begins as all 0s
	}
	
	public void fill(double _val) { // fills with a single value
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = _val;
			}
		}
	}
	
	public Matrix copy() { // returns a copied matrix
		Matrix _m = new Matrix(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				_m.data[i][j] = data[i][j];
			}
		}
		return _m;
	}
	
	public static Matrix toMatrix(double[] _arr) { // converts 2D array to matrix object
		Matrix _m = new Matrix(_arr.length, 1);
		for (int i = 0; i < _arr.length; i++) {
			_m.data[i][0] = _arr[i];
		}
		return _m;
	}
	
	public static Matrix subtract(Matrix _a, Matrix _b) { // subtracts two matrices
		if (_a.rows != _b.rows || _a.cols != _b.cols) {
			System.out.println("Cols and rows don't match [ Subtract ]");
		}
		Matrix _m = new Matrix(_a.rows, _a.cols);
		for (int i = 0; i < _a.rows; i++) {
			for (int j = 0; j < _a.cols; j++) {
				_m.data[i][j] = _a.data[i][j] - _b.data[i][j];
			}
		}
		return _m;
	}
	
	public double[] toArray() { // converts matrix object to 2D array
		double[] _arr = new double[rows * cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				_arr[i*cols + j] = data[i][j];
			}
		}
		return _arr;
	}
	
	public void randomise() { // randomises values in the matrix
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = (double) (Math.random()*2f - 1f);
			}
		}
	}
	
	public Matrix add(Matrix _b) { // adds matrix B to self
		if (rows != _b.rows || cols != _b.cols) {
			System.out.println("Cols and rows don't match [ add ]");
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] += _b.data[i][j];
			}
		}
		return this;
	}
	
	public static Matrix transpose(Matrix _a) { // transposes a given matrix
		Matrix _m = new Matrix(_a.cols, _a.rows);
		for (int i = 0; i < _a.rows; i++) {
			for (int j = 0; j < _a.cols; j++) {
				_m.data[j][i] = _a.data[i][j];
			}
		}
		return _m;
	}
	
	public static Matrix multiply(Matrix _a, Matrix _b) { // multiplies two matrices
		if (_a.cols != _b.rows) {
			System.out.println("A cols and B rows don't match [ multiply ]");
		}
		Matrix _m = new Matrix(_a.rows, _b.cols);
		for (int i = 0; i < _m.rows; i++) {
			for (int j = 0; j < _m.cols; j++) {
				double sum = 0;
				for (int k = 0; k < _a.cols; k++) {
					sum += _a.data[i][k] * _b.data[k][j];
				}
				_m.data[i][j] = sum;
			}
		}
		return _m;
	}
	
	public Matrix scale(double _scalar) { // multiples all elements of self by a scalar
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] *= _scalar;
			}
		}
		return this;
	}
	
	public Matrix hadamard(Matrix _b) { // multiples all elements with a matrix B element wise
		if (rows != _b.rows || cols != _b.cols) {
			System.out.println("Cols and rows don't match [ hadamard ]");
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] *= _b.data[i][j];
			}
		}
		return this;
	}
	
	public Matrix sigmoid() { // performs sigmoid(x) on all elements of self
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = (double) (1d / (1d + Math.exp(-data[i][j])));
			}
		}
		return this;
	}
	
	public Matrix dsigmoid() { // performs part of dsigmoid(x)/dx
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = (double) (data[i][j] * (1d - data[i][j]));
			}
		}
		return this;
	}
	
	public void print() { // prints formatted string of matrix to console
		String s = "[\n";
		for (int i = 0; i < rows; i++) {
			s += "  [ ";
			for (int j = 0; j < cols; j++) {
				s += String.valueOf(data[i][j]) + ", ";
			}
			s += "]\n";
		}
		s += "]";
		System.out.println(s);
	}
}
