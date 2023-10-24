package main.utilities;

import main.classes.CSVData;
import main.utilities.Functions;

import java.util.Random;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import main.classes.Network;
import main.ANN;
import main.classes.CSVData;

public class Part2Abs {
	
	/**
	 * randomize the order of CSVdata object attributes
	 * 
	 * @param data | the input data
	 * @return the data after order has been randomized
	 */
	public static CSVData randomize(CSVData data) {
		CSVData randomized = new CSVData();
		randomized.pixels = Functions.deepCopyTwoDWithVars(data.pixels);
		randomized.values = Functions.deepCopyTwoDWithVars(data.values);
		
		Random rand = new Random();
		
		for (int i = 0; i < randomized.values.length; i++) {
			int randomIndex = rand.nextInt(data.values.length);
			double[] tempPixels = Functions.deepCopyVector(randomized.pixels[randomIndex]);
			double[] tempValues = Functions.deepCopyVector(randomized.values[randomIndex]);
			
			randomized.pixels[randomIndex] = Functions.deepCopyVector(randomized.pixels[i]);
			randomized.values[randomIndex] = Functions.deepCopyVector(randomized.values[i]);
			
			randomized.pixels[i] = Functions.deepCopyVector(tempPixels);
			randomized.values[i] = Functions.deepCopyVector(tempValues);
		}
		
		return randomized;
	}
	
	/**
	 * clear terminal screen (untested)
	 */
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	} 
	
	/**
	 * load data in file to CSVData object
	 * 
	 * @param fileName the name of the file the data is in
	 * @param dataLength the amount of data
	 * @return a CSVData object containing the data
	 */
	public static CSVData getData(String fileName, int dataLength) {
		double[][] one_hot = new double[][] {
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
		};
		
		File training_data = new File(fileName);
		
		CSVData csvData = new CSVData();
		csvData.values = new double[dataLength][10];
		csvData.pixels = new double[dataLength][784];
		
		int line_index = 0;
		
		try {
			Scanner inputStream = new Scanner(training_data);
			
			while(inputStream.hasNext()) {
				String line = inputStream.next();
				String[] items = line.split(",");
				
				csvData.values[line_index] = one_hot[Integer.valueOf(items[0])];
				
				csvData.pixels[line_index] = Functions.convAndNormalize(items);
				
				line_index++;
			}
			
			inputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return csvData;
	}
	
	
	/**
	 * save Network object to a file
	 * 
	 * @param N the network to be saved
	 * @param fileName the name of the file in which to save the network
	 */
	public static void saveToFile(Network N, String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			
			writer.println("[Weights]");
			for (int i = 0; i < N.weights.length; i++) {
				if (i != 0) {
					writer.println("------------");
				}
				for (int j = 0; j < N.weights[i].length; j++) {
					for (int k = 0; k < N.weights[i][j].length; k++) {
						writer.print(N.weights[i][j][k]);
						if (k != N.weights[i][j].length-1) {
							writer.print(",");
						}
					}
					writer.println("");
				}
			}
			
			writer.println("[Biases]");
			for (int i = 0; i < N.biases.length; i++) {
				if (i != 0) {
					writer.println("------------");
				}
				for (int j = 0; j < N.biases[i].length; j++) {
					writer.print(N.biases[i][j]);
					if (j != N.biases[i].length-1) {
						writer.print(",");
					}
				}
				writer.println("");
			}
			
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * upload a network from a file
	 * 
	 * @param fileName the name of the file from which to load the network
	 * @return a network object
	 */
	public static Network loadFromFile(String fileName) {

		File file = new File("/Users/novisandlin/git/MNIST_Digits_LocRep/MNIST_Digits/" + fileName);
		int biasIndex = -1;
		boolean biasIndexFound = false;
		String newLine;
		ArrayList<String> lines = new ArrayList<String>();
		
		int numLevels = 0;
		
		try {
			Scanner inputStream = new Scanner(file);
			
			while(inputStream.hasNext()) {
				newLine = inputStream.next();
				lines.add(newLine);
				if (newLine.equals("------------") && !biasIndexFound) {
					numLevels++;
				}
				if (!biasIndexFound) {
					biasIndex++;
				}
				if (newLine.equals("[Biases]")) {
					biasIndexFound = true;
					numLevels++;
				}
			}
			
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
		Network N = new Network();
		N.weights = new double[numLevels][][];
		N.biases = new double[numLevels][];
		
		String line;
		String[] items;
		int currentLevel = 0;
		int currentNode = 0;
		boolean midLine = false;
		for (int i = 1; i < biasIndex; i++) {
			if (!midLine) {
				int j = i;
				while (!lines.get(j).equals("------------") && !lines.get(j).equals("[Biases]")) {
					j++;
				}
				N.weights[currentLevel] = new double[j-i][];
				midLine = true;
			}
			line = lines.get(i);
			if (line.equals("------------")) {
				midLine = false;
				currentLevel++;
				currentNode = 0;
				continue;
			}
			items = line.split(",");
			N.weights[currentLevel][currentNode] = new double[items.length];

			for (int j = 0; j < items.length; j++) {
				N.weights[currentLevel][currentNode][j] = Double.parseDouble(items[j]);
			}
			
			currentNode++;
		}
		
		currentLevel = 0;
		for (int i = biasIndex + 1; i < lines.size(); i++) {
			line = lines.get(i);
			if (line.equals("------------")) {
				currentLevel++;
				continue;
			}
			items = line.split(",");
			N.biases[currentLevel] = new double[items.length];
			
			for (int j = 0; j < items.length; j++) {
				N.biases[currentLevel][j] = Double.parseDouble(items[j]);
			}
		}
		
		for (int i = 0; i < N.weights.length; i++) {
			Matrix.print_matrix(N.weights[i]);
		}
		
		for (int i = 0; i < N.biases.length; i++) {
			Matrix.print_vector(N.biases[i]);
		}
		
		return N;
	}

	/**
	 * generate list of batches from CSVData object
	 * 
	 * @param data data from which to derive batches
	 * @param numBatches the approximate number of batches to create
	 * @return a list of batches 
	 */
	public static double[][][][] generateBatches(CSVData data, int numBatches) {
		if (numBatches > data.values.length) {
			return null;
		}
		
		int batchSize = data.values.length / numBatches;
		
		int currDataIndex = 0;
		double[][][][] batches = new double[numBatches][][][];
		for (int i = 0; i < numBatches - 1; i++) {
			batches[i] = new double[batchSize][][];
			for (int j = 0; j < batchSize; j++, currDataIndex++) {
				batches[i][j] = new double[][] {
						data.pixels[currDataIndex],
						data.values[currDataIndex]
				};
			}
		}
		
		batches[numBatches - 1] = new double[data.values.length - currDataIndex][][];
		for (int i = 0;currDataIndex < data.values.length; currDataIndex++, i++) {
			batches[numBatches - 1][i] = new double[][] {
				data.pixels[currDataIndex],
				data.values[currDataIndex]
			};
		}
		
	
		return batches;
	}
	
	/**
	 * test a Network on a dataset and print metrics
	 * 
	 * @param N the network to use
	 * @param testData the data to test the network on
	 */
	public static void testNetwork(Network N, CSVData testData) {
		int[] right = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] total = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		double[] output;
		for (int i = 0; i < testData.pixels.length; i++) {
			System.out.print("right before: ");
			Matrix.print_vector(right);
			System.out.print("total before: ");
			Matrix.print_vector(total);
			System.out.print("answer: ");
			Matrix.print_vector(testData.values[i]);
			output = ANN.compute(N, testData.pixels[i], false);
			System.out.print("output: ");
			Matrix.print_vector(output);
			int maxIndex = Functions.getMax(output);
			total[Functions.getMax(testData.values[i])]++;
			if (testData.values[i][maxIndex] == 1) {
				right[maxIndex]++;
			}
			System.out.print("right after: ");
			Matrix.print_vector(right);
			System.out.print("total after: ");
			Matrix.print_vector(total);
		}
		
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
}
