package main;

import main.utilities.*;

// Class for ANN functionalities
public class ANN {
	
	// Class for aggregating network components
	private static class Gradients {
		public double[][][] weight_gradients;
		public double[][] bias_gradients;
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
	private static double singleNeuronActivation(double[] input, double[] weights, double bias) {
		
		try {
			
			// return the activation of the neuron
			return Functions.sigmoid(Matrix.dotProduct(input, weights) + bias);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * compute output for a layer of neurons
	 * 
	 * @param input | the output from the previous layer of nodes
	 * @param weights | the weights of the neural layer
	 * @param bias | the biases of the neural layer
	 * @return the activations of a layer of neurons
	 */
	private static double[] singleLayerActivation(double[] input, double[][] weights, double[] biases) {
		
		// activations vector holds the activations of the layer's nodes
		double[] activations = new double[weights.length];
		
		// iterate through each node in the layer
		for (int i = 0; i < weights.length; i++) {
			
			// compute the activation of the i'th neuron and assign it to the 
			// i'th index of the activations vector
			activations[i] = singleNeuronActivation(input, weights[i], biases[i]);
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
	 * @return the outputs, or activations of the NN given the input
	 */
	private static double[][] feedForward(double[][][] weights, double[][] biases, double[] input) {
		
		// outputs holds the activations of NN nodes
		// the first index is defined as the outputs of the first layer--the
		// input layer--and is their activations, which are, by definition, 
		// just themselves.
		double[][] outputs = new double[weights.length+1][];
		outputs[0] = input.clone();
		
		// iterate through layers of the network
		for (int current_layer = 0; current_layer < outputs.length-1; current_layer++) {
			
			// compute the activations of the current_layer
			outputs[current_layer+1] = singleLayerActivation(outputs[current_layer], weights[current_layer], biases[current_layer]);
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
	 * @return the gradients of the network derived from a single input
	 */
	private static Gradients backpropogate(double[][][] weights, double[][] biases, double[][] activations, double[] correct_results) {
		
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
	public static Network trainNetwork(Network N, double learningRate, double[][][][] batches, int epochs, boolean diagnostics) {
		
		if (diagnostics) { 
			System.out.println("Beginning training...");
			System.out.println("-------------------------------------");
		}
		
		// iterate through epochs
		for (int epoch = 0; epoch < epochs; epoch++) {
			
			if (diagnostics) { 
				System.out.format("Epoch %d\n", epoch+1);
			}
			
			// iterate through mini-batches
			for (int batch = 0; batch < batches.length; batch++) {
				
				// activations stores the activations of all nodes, including input nodes
				double[][] activations = new double[batches[batch].length][];
				
				// list of gradients objects; gradients[i] is the gradients (weights and bias)
				// returned from backtracking the i'th training_item
				Gradients[] gradients = new Gradients[batches[batch].length];
				
				// iterate through training items
				for (int training_item = 0; training_item < batches[batch].length; training_item++) {
					
					if (diagnostics) {
						System.out.format("training_item: %d\n", training_item);
						System.out.print("Input: ");
						Matrix.print_vector(batches[batch][training_item][0]);
					}
					
					// feed input through network and store in activations
					activations = feedForward(N.weights, N.biases, batches[batch][training_item][0]);
					
					// back-propogate; this calculates the gradients (weight & bias) for all
					// nodes on all levels
					gradients[training_item] = backpropogate(N.weights, N.biases, activations, batches[batch][training_item][1]);
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
		}
		
		if (diagnostics) { 
			System.out.println("Training Done.");
		}
		
		// return the network
		return N;
	}
	
	/**
	 * compute output from NN
	 * 
	 * @param N | Network object containing weights and biases
	 * @param input | the input to the network
	 * @return the output of the network
	 */
	public static double[] compute(Network N, double[] input) {
		
		// outputs represents the outputs from all layers after input is
		// fed through the network
		double[][] outputs = feedForward(N.weights, N.biases, input);
		
		// return the last index of output, which is the activations of 
		// the last layer (the layer's "output")
		return outputs[outputs.length - 1];
	}
	
}
