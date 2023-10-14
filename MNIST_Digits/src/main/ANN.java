package main;

import java.text.DecimalFormat;

import main.classes.Network;
import main.utilities.*;
import java.util.Random;

// Class for ANN functionalities
public class ANN {
	
	// a class to consolidate network attributes' gradients for returns
	private static class Gradients {
		public double[][][] weight_gradients;
		public double[][] bias_gradients;
	}
	
	// a class for aggregating activation components (pre, post sigmoid application)
	private static class Act {
		public double a;
		public double z;
	}
	
	/**
	 * perform feed forward for single neuron
	 * 
	 * @param inputs | an array of inputs
	 * @param weights | an array of weights for the neuron
	 * @param bias | the bias for the neuron
	 * @return the activation of the neuron
	 * @throws Exception
	 */
	private static Act singleNeuronActivation(double[] input, double[] weights, double bias) {
		
		try {
			
			// define the activation of the neuron
			Act A = new Act();
			A.z = Matrix.dotProduct(input, weights) + bias;
			A.a = Functions.sigmoid(A.z);
			
			// return the activation
			return A;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * compute output for a layer of neurons
	 * 
	 * @param input | the output from the previous layer of nodes
	 * @param weights | the weights of the neural layer
	 * @param bias | the biases of the neural layer
	 * @param diagnostics | whether you want verbose descriptions of the goings-on
	 * @return the activations of a layer of neurons
	 */
	private static double[] singleLayerActivation(double[] input, double[][] weights, double[] biases, boolean diagnostics) {
		
		// activations vector holds the activations of the layer's nodes
		double[] activations = new double[weights.length];
		
		// for diagnostics
		double[] z_values = new double[weights.length];
		
		// iterate through each node in the layer
		for (int i = 0; i < weights.length; i++) {
			
			// compute the activation of the i'th neuron and assign it to the 
			// i'th index of the activations vector
			Act A = singleNeuronActivation(input, weights[i], biases[i]);
			activations[i] = A.a;
			
			if (diagnostics) {
				z_values[i] = A.z;
			}
		}
		
		if (diagnostics) { 
			System.out.print("z values (pre sigmoid): ");
			Matrix.print_vector(z_values);
		}
		
		// return activations vector
		return activations;
	}
	
	/**
	 * compute outputs, or activations, of NN given an input
	 * 
	 * @param weights | the weights of the neural network
	 * @param biases | the biases of the neural network
	 * @param input | the input to the network
	 * @param diagnostics | whether you want verbose descriptions of the goings-on
	 * @return the outputs, or activations of the NN given the input
	 */
	private static double[][] feedForward(double[][][] weights, double[][] biases, double[] input, boolean diagnostics) {
		
		// outputs holds the activations of NN nodes
		// the first index is defined as the outputs of the first layer--the
		// input layer--and is their activations, which are, by definition, 
		// just themselves.
		double[][] outputs = new double[weights.length+1][];
		outputs[0] = input.clone();
		
		// iterate through layers of the network
		for (int current_layer = 0; current_layer < outputs.length-1; current_layer++) {
			
			if (diagnostics) {
				System.out.format("Layer %d\n", current_layer+1);
				System.out.format("Input to layer (a^%d): ", current_layer);
				Matrix.print_vector(outputs[current_layer]);
			}
			
			// compute the activations of the current_layer
			outputs[current_layer+1] = singleLayerActivation(outputs[current_layer], weights[current_layer], biases[current_layer], diagnostics);
			
			if (diagnostics) {
				System.out.format("Output of layer (a^%d): ", current_layer+1);
				Matrix.print_vector(outputs[current_layer+1]);
			}
		}

		// return the outputs, or activations, of the network
		return outputs;
	}
	
	/**
	 * compute ANN gradients from single input
	 * 
	 * @param weights | the weights of the neural network
	 * @param biases | the biases of the neural network
	 * @param activations | the activations of the network during the feed-forward phase
	 * @param correct_results | the expected activations of the output layer
	 * @param diagnostics | whether you want verbose descriptions of the goings-on
	 * @return the gradients of the network derived from a single input
	 */
	private static Gradients backpropogate(double[][][] weights, double[][] biases, double[][] activations, double[] correct_results, boolean diagnostics) {
		
		// declare gradient object to store weight and bias gradients for training_item
		Gradients G = new Gradients();

		// initialize G's attributes to correct size
		G.bias_gradients = Functions.deepCopyTwoD(biases);
		G.weight_gradients = Functions.deepCopyThreeD(weights);
		
		// calculate bias gradients for output layer
		
		// designate network's final layer index
		int final_layer_index = activations.length-1;
		
		// iterate through nodes on final layer
		for (int i = 0; i < activations[final_layer_index].length; i++) {
			
			// define the activation for the i'th node on the final layer
			double the_activation = activations[final_layer_index][i];
			
			// calculate the gradient for this node and save to correlative G.bias_gradients index
			G.bias_gradients[final_layer_index-1][i] = (the_activation - correct_results[i]) * the_activation * (1 - the_activation);
		}
		
		// calculate bias gradients for all other layers
		
		// iterate through network levels
		for (int i = biases.length-2; i >= 0; i--) {

			// iterate through nodes on the i'th level
			for (int j = 0; j < biases[i].length; j++) {

				// sum of gradient * relative weight of L+1 layer's nodes
				double sum = 0;
				
				// iterate through nodes on the next level and calculate the above sum
				for (int k = 0; k < biases[i+1].length; k++) {
					sum += G.bias_gradients[i+1][k] * weights[i+1][k][j];
				}
				
				// calculate the gradient for this node and save to correlative G.bias_gradients index
				G.bias_gradients[i][j] = sum * activations[i+1][j] * (1 - activations[i+1][j]);
			}
		}

		// calculate weight gradients
		
		// iterate through levels
		for (int i = 0; i < weights.length; i++) {
			
			// iterate through nodes on i'th layer
			for (int j = 0; j < weights[i].length; j++) {
				
				// iterate through weights associated with j'th node
				for (int k = 0; k < weights[i][j].length; k++) {
					
					// calculate the gradient for this node and save to correlative G.weight_gradients index
					G.weight_gradients[i][j][k] = activations[i][k] * G.bias_gradients[i][j];
				}
			}
		}
		
		if (diagnostics) {
			for (int i = 0; i < G.bias_gradients.length; i++) {
				System.out.format("Layer %d\n", i+1);
				System.out.print("Bias gradients: ");
				Matrix.print_vector(G.bias_gradients[i]);
				System.out.println("Weight gradients:");
				Matrix.print_matrix(G.weight_gradients[i]);
			}
		}
		
		// return the gradient object
		return G;
	}
	
	/**
	 * train an ANN
	 * 
	 * @param N | Network object containing weights and biases
	 * @param learningRate | the rate at which your network will learn
	 * @param batches | the pre-processed training data
	 * @param epochs | the number of epochs for which to train
	 * @param diagnostics | whether you want verbose descriptions of the goings-on
	 * @return the trained network
	 */
	public static Network trainNetwork(Network N, double learningRate, double[][][][] batches, int epochs, boolean epochStatements, boolean diagnostics) {
		
		// for epochStatements
		int[] right;
		int[] total;
		
		if (diagnostics) { 
			System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////////////\n");
			System.out.println("Beginning training...");
		}
		
		// iterate through epochs
		for (int epoch = 0; epoch < epochs; epoch++) {
			
			right = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			total = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			
			if (diagnostics) { 
				System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.format("Epoch %d\n", epoch+1);
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			}
			
			// iterate through mini-batches
			for (int batch = 0; batch < batches.length; batch++) {
				
				if (diagnostics) {
					System.out.println("\n=========================================================================================================");
					System.out.format("Batch %d\n", batch+1);
					System.out.println("=========================================================================================================\n");
					System.out.println("Network weights:");
					for (int i = 0; i < N.weights.length; i++) {
						System.out.format("Level %d\n", i);
						Matrix.print_matrix(N.weights[i]);
					}
					System.out.println("\nNetwork biases:");
					for (int i = 0; i < N.biases.length; i++) {
						System.out.format("Level %d\n", i);
						Matrix.print_vector(N.biases[i]);
					}
				}
				
				// activations stores the activations of all nodes, including input nodes
				double[][] activations = new double[batches[batch].length][];
				
				// list of gradients objects; gradients[i] is the gradients (weights and bias)
				// returned from backtracking the i'th training_item
				Gradients[] gradients = new Gradients[batches[batch].length];
				
				// iterate through training items
				for (int training_item = 0; training_item < batches[batch].length; training_item++) {
					
					if (diagnostics) {
						System.out.println("\n---------------------------------------------------------------------------------------------------------");
						System.out.format("\ntraining_item: %d\n", training_item+1);
						System.out.print("Input: ");
						Matrix.print_vector(batches[batch][training_item][0]);
						System.out.println("\nFeeding forward");
					}
					
					// feed input through network and store in activations
					activations = feedForward(N.weights, N.biases, batches[batch][training_item][0], diagnostics);
					
					if (epochStatements) {
						int maxIndex = Functions.getMax(activations[activations.length - 1]);
						total[maxIndex]++;
						if (batches[batch][training_item][1][maxIndex] == 1) {
							right[maxIndex]++;
						}
					}
					
					if (diagnostics) {
						System.out.println("\nBackpropogating");
					}
					
					// back-propogate; this calculates the gradients (weight & bias) for all
					// nodes on all levels
					gradients[training_item] = backpropogate(N.weights, N.biases, activations, batches[batch][training_item][1], diagnostics);
				}
				
				// sumGradient stores the sum of all gradient vectors in gradients array
				Gradients sumGradient = new Gradients();
				
				// instantiate sumGradient attributes with deep copy for original-data persistence
				sumGradient.weight_gradients = Functions.deepCopyThreeD(gradients[0].weight_gradients);
				sumGradient.bias_gradients = Functions.deepCopyTwoD(gradients[0].bias_gradients);
				
				// calculate the sumGradient (weights and biases)
				
				// iterate through gradients array for gradients from individual tracking_item's
				for (int g = 0; g < gradients.length; g++) {
					
					// for weights
					
					// iterate through layers
					for (int i = 0; i < N.weights.length; i++) {
						
						// iterate through nodes
						for (int j = 0; j < N.weights[i].length; j++) {
							
							try {
								
								// add node's weight gradient to correlative sumGradient weight gradient
								sumGradient.weight_gradients[i][j] = Matrix.add(sumGradient.weight_gradients[i][j], gradients[g].weight_gradients[i][j]);
								
							} catch (Exception e) {
								e.printStackTrace();
								System.exit(1);
							}
						}
					}
					
					// for biases
					
					// iterate through layers
					for (int i = 0; i < N.biases.length; i++) {
						
						try {
							
							// add node's bias gradient to correlative sumGradient bias gradient
							sumGradient.bias_gradients[i] = Matrix.add(sumGradient.bias_gradients[i], gradients[g].bias_gradients[i]);
						
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(1);
						}
					}
				}
		
				// post mini-batch revision of network weights
				
				// iterate through levels
				for (int i = 0; i < N.weights.length; i++) {
					
					// iterate through nodes
					for (int j = 0; j < N.weights[i].length; j++) {
						
						// iterate through individual weights
						for (int k = 0; k < N.weights[i][j].length; k++) {	
							
							// update weight
							N.weights[i][j][k] = N.weights[i][j][k] - ((learningRate / batches[batch].length) * sumGradient.weight_gradients[i][j][k]);
						}
					}
				}
				
				// post mini-batch revision of network biases
				
				// iterate through levels
				for (int i = 0; i < N.biases.length; i++) {
					
					// iterate through individual biases (essentially nodes)
					for (int j = 0; j < N.biases[i].length; j++) {
						
						// update bias
						N.biases[i][j] = N.biases[i][j] - ((learningRate / batches[batch].length) * sumGradient.bias_gradients[i][j]);
					}
				}	
			}
			
			if (epochStatements) {
				System.out.format("Stats for epoch %d\n", epoch + 1);
				System.out.format("0 = %9s ", String.valueOf(right[0]) + "/" + String.valueOf(total[0]));
				System.out.format("1 = %9s ", String.valueOf(right[1]) + "/" + String.valueOf(total[1]));
				System.out.format("2 = %9s ", String.valueOf(right[2]) + "/" + String.valueOf(total[2]));
				System.out.format("3 = %9s ", String.valueOf(right[3]) + "/" + String.valueOf(total[3]));
				System.out.format("4 = %9s ", String.valueOf(right[4]) + "/" + String.valueOf(total[4]));
				System.out.format("5 = %9s\n", String.valueOf(right[5]) + "/" + String.valueOf(total[5]));
				System.out.format("6 = %9s ", String.valueOf(right[6]) + "/" + String.valueOf(total[6]));
				System.out.format("7 = %9s ", String.valueOf(right[7]) + "/" + String.valueOf(total[7]));
				System.out.format("8 = %9s ", String.valueOf(right[8]) + "/" + String.valueOf(total[8]));
				System.out.format("9 = %9s ", String.valueOf(right[9]) + "/" + String.valueOf(total[9]));
				int sumRight = Functions.sum(right);
				int sumTotal = Functions.sum(total);
				System.out.format("Accuracy = %11s = %6s%%", String.valueOf(sumRight) + "/" + String.valueOf(sumTotal), Functions.chopDecimal(String.valueOf((double) sumRight / sumTotal), 5));
				System.out.println();
			}
			
			if (diagnostics && (epoch == epochs - 1)) {
				System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
				System.out.println("Final network state:");
				System.out.println("Network weights:");
				for (int i = 0; i < N.weights.length; i++) {
					System.out.format("Level %d\n", i);
					Matrix.print_matrix(N.weights[i]);
				}
				System.out.println("\nNetwork biases:");
				for (int i = 0; i < N.biases.length; i++) {
					System.out.format("Level %d\n", i);
					Matrix.print_vector(N.biases[i]);
				}
			}
		}
		
		if (diagnostics) { 
			System.out.println("\nTraining Done.\n");
			System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////////////");
		}
		
		// return the network
		return N;
	}
	
	/**
	 * compute output from NN
	 * 
	 * @param N | Network object containing weights and biases
	 * @param input | the input to the network
	 * @param diagnostics | whether you want verbose descriptions of the goings-on
	 * @return the output of the network
	 */
	public static double[] compute(Network N, double[] input, boolean diagnostics) {
		
		// outputs represents the outputs from all layers after input is
		// fed through the network
		double[][] outputs = feedForward(N.weights, N.biases, input, diagnostics);
		
		// return the last index of output, which is the activations of 
		// the last layer (the layer's "output")
		return outputs[outputs.length - 1];
	}
	
	
	public static Network createNetwork(int[] layers) {
		Random r = new Random();
		
		Network N = new Network();
		
		N.weights = new double[layers.length-1][][];
		N.biases = new double[layers.length-1][];
		for (int i = 1; i < layers.length; i++) {
			N.weights[i-1] = new double[layers[i]][];
			N.biases[i-1] = new double[layers[i]];
			for (int j = 0; j < layers[i]; j++) {
				if (i-1 == 0) {
					N.weights[i-1][j] = new double[layers[0]];
				} else {
					N.weights[i-1][j] = new double[layers[i-1]];
				}
				for (int k = 0; k < N.weights[i-1][j].length; k++) {
					N.weights[i-1][j][k] = -1 + (2 * r.nextDouble());
				}
				N.biases[i-1][j] = -1 + (2 * r.nextDouble());
			}
		}
		
		return N;
	}
	
}
