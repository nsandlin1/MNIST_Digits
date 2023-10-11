package main;


public class Part1 {
	public static void main(String[] args) {
		// define learning rate
		double learningRate = 10;
		// define num of epochs
		int epochs = 6;
		
		// create the network
		Network N = new Network();
		N.weights = new double[][][] {
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
		N.biases = new double[][] {
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
		
		ANN.trainNetwork(N, learningRate, batches, epochs);
	}
}
