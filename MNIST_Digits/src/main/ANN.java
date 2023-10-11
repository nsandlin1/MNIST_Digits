// Name: Novi Sandlin
// CWID: 10389876
// Date: 10/10/2023
// Ass. Num: 2 Part 1
// Desc.: This program seeks to replicate the process exemplified by the
// 		  given Excel spreadsheet through the implementation of fundamental
//	 	  ANN learning algorithms (e.g., gradient decent).

package main;

import main.utilities.*;

public class ANN {
//	Did not use.
//	// function for calculating final level node error
//	public static double finalLayerBiasGradient(double node_activation, double correct_activation) {
//		return (node_activation - correct_activation) * node_activation * (1 - node_activation);
//	}
//	
//	// function for calculating mid-level node error
//	public static double midLayerBiasGradient(double node_activation, int node_index, double[] l_p_1_biases, double[][] l_p_1_weights) {
//		double sum = 0;
//		for (int i = 0; i < l_p_1_biases.length; i++) {
//			sum += l_p_1_biases[node_index] * l_p_1_weights[node_index][i];
//		}
//		return sum * node_activation * (1 - node_activation);
//	}
//	
//	// function to calculate weight error
//	public static double universalWeightError(double node_activation, double node_bias_gradient) {
//		return node_activation * node_bias_gradient;
//	}
	
	/**
	 * perform feed forward for single neuron
	 * 
	 * @param inputs | an array of inputs
	 * @param weights | an array of weights for the neuron
	 * @param bias | the bias for the neuron
	 * @return the activation of the neuron
	 * @throws Exception
	 */
	public static double singleNeuronActivation(double[] input, double[] weights, double bias) {
		try {
			return Functions.sigmoid(Matrix.dotProduct(input, weights) + bias);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * perform feed forward for multiple neurons
	 * 
	 * @param inputs | an array of inputs
	 * @param weights | an array arrays of weights for the neurons
	 * @param bias | an array of biases for the neurons
	 * @return the activations of the neurons
	 * @throws Exception
	 */
	public static double[] singleLayerActivation(double[] input, double[][] weights, double[] biases) {
		// instantiate activation vector
		double[] activations = new double[weights.length];
		// for each neuron, perform singleNeuronFeedForward
		for (int i = 0; i < weights.length; i++) {
			activations[i] = singleNeuronActivation(input, weights[i], biases[i]);
		}
		// return activations vector
		return activations;
	}
	
	public static double[][] feedForward(double[][][] weights, double[][] biases, double[] input) {
		// input is first output (of theoretical input nodes)
		double[][] outputs = new double[weights.length+1][];
		System.out.println(outputs.length);
		outputs[0] = input.clone();
		for (int current_layer = 0; current_layer < outputs.length-1; current_layer++) {
			outputs[current_layer+1] = singleLayerActivation(outputs[current_layer], weights[current_layer], biases[current_layer]);
		}
		
		return outputs;
	}
	
	public static double[][] backpropogate(double[][][] weights, double[][] biases, double[][] activations, double[] correct_results) {
		// arrays for biases, biases conveniently happens to be the correct dimensions
		double[][] bias_gradients = biases.clone();
		
		// calculate bias gradient for output layer
		int final_layer_index = activations.length-1;
		for (int i = 0; i < activations[final_layer_index].length; i++) {
			double the_activation = activations[final_layer_index][i];
			bias_gradients[final_layer_index-1][i] = (the_activation - correct_results[i]) * the_activation * (1 - the_activation);
		}
		
		// calculate bias gradient for all other layers
		// iterate through levels
		for (int i = bias_gradients.length-2; i >= 0; i--) {
//			System.out.println("i: " + i);
			// iterate through nodes on that level
			for (int j = 0; j < bias_gradients[i].length; j++) {
//				System.out.println("j: " + j);
				double sum = 0;
				// iterate through nodes on the next level
				for (int k = 0; k < bias_gradients[i+1].length; k++) {
//					System.out.println("k: " + k);
//					System.out.println("bias_gradients[i+1][k]" + bias_gradients[i+1][k]);
//					System.out.println("weights[i+1][k][j]" + weights[i+1][k][j]);
					sum += bias_gradients[i+1][k] * weights[i+1][k][j];
				}
//				System.out.println("sum: " + sum);
				System.out.println("activations[i][j]" + activations[i][j]);
				bias_gradients[i][j] = sum * activations[i+1][j] * (1 - activations[i+1][j]);
				System.out.println("bias_gradients[i][j]" + bias_gradients[i][j]);
			}
		}
		
		Matrix.print_matrix(bias_gradients);
		return bias_gradients;
	}
	
	public static Network trainNetwork(Network N, double learningRate, double[][][][] batches, int epochs) {
		
		for (int epoch = 0; epoch < epochs; epoch++) {
			for (int batch = 0; batch < batches.length; batch++) {
				System.out.println("res");
				double[][] activations = new double[batches[batch].length][];
				for (int training_set = 0; training_set < batches[batch].length; training_set++) {
					activations = feedForward(N.weights, N.biases, batches[batch][training_set][0]);
					System.out.println("activations");
					Matrix.print_matrix(activations);
					backpropogate(N.weights, N.biases, activations, batches[batch][training_set][1]);
				}
				//revise weights
			}
		}
		
		return N;
	}
	
	public static void main(String[] args) {
		double[] inputs = new double[] {1, 2, 3};
		double[] weights = new double[] {1, 0.5, 1};
		double bias = 2;
		try {
			double a = singleNeuronActivation(inputs, weights, bias);
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
