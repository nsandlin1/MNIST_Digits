package main;

import main.utilities.Functions;
import main.utilities.Matrix;

public class ANN {
	
	public static double singleNeuronForward(double[] inputs, double[] weights, double bias) throws Exception {
		return Functions.sigmoid(Matrix.dotProduct(inputs, weights) + bias);
	}
	
	
	public static void main(String[] args) {
		double[] inputs = new double[] {1, 2, 3};
		double[] weights = new double[] {1, 0.5, 1};
		double bias = 2;
		try {
			double a = singleNeuronForward(inputs, weights, bias);
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
