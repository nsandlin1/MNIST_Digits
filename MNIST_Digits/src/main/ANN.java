package main;

import main.utilities.Functions;
import main.utilities.Matrix;

public class ANN {
	
	public static double[] forward(double[] inputs, double[] weights, double bias) {
		return Functions.sigmoid(Matrix.dotProduct(inputs, weights), bias);
	}
}
