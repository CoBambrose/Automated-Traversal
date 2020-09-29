package NeuralNetwork;

public class Matrix {
	
	int rows;
	int cols;
	public double[][] data;

	public Matrix(int _rows, int _cols) {
		this.rows = _rows;
		this.cols = _cols;
		this.data = new double[_rows][_cols];
		this.fill(0);
	}
	
	public void fill(double _val) {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.data[i][j] = _val;
			}
		}
	}
	
	public Matrix copy() {
		Matrix _m = new Matrix(this.rows, this.cols);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				_m.data[i][j] = this.data[i][j];
			}
		}
		return _m;
	}
	
	public static Matrix toMatrix(double[] _arr) {
		Matrix _m = new Matrix(_arr.length, 1);
		for (int i = 0; i < _arr.length; i++) {
			_m.data[i][0] = _arr[i];
		}
		return _m;
	}
	
	public static Matrix subtract(Matrix _a, Matrix _b) {
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
	
	public double[] toArray() {
		double[] _arr = new double[this.rows * this.cols];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				_arr[i*this.cols + j] = this.data[i][j];
			}
		}
		return _arr;
	}
	
	public void randomise() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.data[i][j] = (double) (Math.random()*2f - 1f);
			}
		}
	}
	
	public Matrix add(Matrix _b) {
		if (this.rows != _b.rows || this.cols != _b.cols) {
			System.out.println("Cols and rows don't match [ add ]");
		}
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.data[i][j] += _b.data[i][j];
			}
		}
		return this;
	}
	
	public static Matrix transpose(Matrix _a) {
		Matrix _m = new Matrix(_a.cols, _a.rows);
		for (int i = 0; i < _a.rows; i++) {
			for (int j = 0; j < _a.cols; j++) {
				_m.data[j][i] = _a.data[i][j];
			}
		}
		return _m;
	}
	
	public static Matrix multiply(Matrix _a, Matrix _b) {
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
	
	public Matrix scale(double _scalar) {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.data[i][j] *= _scalar;
			}
		}
		return this;
	}
	
	public Matrix hadamard(Matrix _b) {
		if (this.rows != _b.rows || this.cols != _b.cols) {
			System.out.println("Cols and rows don't match [ hadamard ]");
		}
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.data[i][j] *= _b.data[i][j];
			}
		}
		return this;
	}
	
	public Matrix sigmoid() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.data[i][j] = (double) (1d / (1d + Math.exp(-this.data[i][j])));
			}
		}
		return this;
	}
	
	public Matrix dsigmoid() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.data[i][j] = (double) (this.data[i][j] * (1d - this.data[i][j]));
			}
		}
		return this;
	}
	
	public void print() {
		String s = "[\n";
		for (int i = 0; i < this.rows; i++) {
			s += "  [ ";
			for (int j = 0; j < this.cols; j++) {
				s += String.valueOf(this.data[i][j]) + ", ";
			}
			s += "]\n";
		}
		s += "]";
		System.out.println(s);
	}
}
