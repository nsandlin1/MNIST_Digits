// Name: Novi Sandlin
// CWID: 10389876
// Date: 10/10/2023
// Ass. Num: 2 Part 1
// Desc.: This program seeks to replicate the process exemplified by the
// 		  given Excel spreadsheet through the implementation of fundamental
//	 	  ANN learning algorithms (e.g., gradient decent).

package main;

import main.utilities.Matrix;

// class for part 1 of this assignment
public class Part1 {
	
	public static void main(String[] args) {
		
		// define learning rate
		double learningRate = 10;
		
		// define num of epochs
		int epochs = 5;
		
		// define network's weights & biases
		Network untrainedNetwork = new Network();
		untrainedNetwork.weights = new double[][][] {
			{
				{-0.21, 0.72, -0.25, 1},
				{-0.94, -0.41, -0.47, 0.63},
				{0.15, 0.55, -0.49, -0.75},
			},
			{
				{0.76, 0.48, -0.73},
				{0.34, 0.89, -0.23}
			}
		};
		untrainedNetwork.biases = new double[][] {
			{0.1, -0.36, -0.31},
			{0.16, -0.46}
		};
		
		// define batches
		double[][][][] batches = {
				{
					{
						{0, 1, 0 ,1},
						{0, 1}
					},
					{
						{1, 0, 1, 0},
						{1, 0}
					}
				},	
				{
					{
						{0, 0, 1, 1},
						{0, 1}
					},
					{
						{1, 1, 0, 0},
						{1, 0}
					}
				}
		};
		
		// train the network
		Network trainedNetwork = ANN.trainNetwork(untrainedNetwork, learningRate, batches, epochs, true);
		
		double[] output = ANN.compute(trainedNetwork, new double[] {0, 1, 0, 1}, false);
		Matrix.print_vector(output);
	}
}
