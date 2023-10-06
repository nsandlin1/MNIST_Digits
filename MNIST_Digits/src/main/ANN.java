package main;

import main.utilities.Functions;
import main.utilities.Matrix;

public class ANN {
	
	/**
	 * perform feed forward for single neuron
	 * 
	 * @param inputs | an array of inputs
	 * @param weights | an array of weights for the neuron
	 * @param bias | the bias for the neuron
	 * @return A, the activation of the neuron
	 * @throws Exception
	 */
	public static double singleNeuronFeedForward(double[] inputs, double[] weights, double bias) throws Exception {
		return Functions.sigmoid(Matrix.dotProduct(inputs, weights) + bias);
	}
	
	/**
	 * perform feed forward for multiple neurons
	 * 
	 * @param inputs | an array of inputs
	 * @param weights | an array arrays of weights for the neurons
	 * @param bias | an array of biases for the neurons
	 * @return vector A, the activations of the neurons
	 * @throws Exception
	 */
	public static double[] multiNeuronFeedForward(double[] inputs, double[][] weights, double[] biasis) throws Exception {
		// instantiate activation vector
		double[] activations = new double[weights.length];
		// for each neuron, perform singleNeuronFeedForward
		for (int i = 0; i < weights.length; i++) {
			activations[i] = singleNeuronFeedForward(inputs, weights[i], biasis[i]);
		}
		// return activations vector
		return activations;
	}
	
	public static void main(String[] args) {
		double[] inputs = new double[] {1, 2, 3};
		double[] weights = new double[] {1, 0.5, 1};
		double bias = 2;
		try {
			double a = singleNeuronFeedForward(inputs, weights, bias);
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
