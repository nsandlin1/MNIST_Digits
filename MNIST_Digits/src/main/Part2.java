package main;

import main.classes.*;
import main.utilities.Functions;
import main.utilities.Matrix;
import main.utilities.Part2Abs;
import java.util.Arrays;

import java.util.Scanner;

public class Part2 {
	
	public static void main(String[] args) {
		
		CSVData trainingData = Part2Abs.getData("/Users/novisandlin/git/MNIST_Digits_LocRep/mnist_train.csv", 60000);
		CSVData testingData = Part2Abs.getData("/Users/novisandlin/git/MNIST_Digits_LocRep/mnist_test.csv", 10000);
		
		Scanner in = new Scanner(System.in);
		
		Network currentNetwork = null;
		
		boolean firstRound = true;
		
		String persistentMessage = null;
		
		while (true) {
			if (firstRound) {
				System.out.println("Welcome.\n");
			} else if (persistentMessage != null) {
				System.out.println(persistentMessage);
				persistentMessage = null;
			}
			
			firstRound = false;
			
			System.out.println("What would you like to do?");

			if (currentNetwork == null) {
				System.out.print("\t[1] Create a randomized network.\n");
				System.out.print("\t[2] Load a pre-trained network.\n");
				System.out.print("\t[3] Exit program.\n");
			}
			if (currentNetwork != null) {
				System.out.print("\t[1] Train the current network on TRAINING data\n");
				System.out.print("\t[2] Display current network accuracy on TRAINING data.\n");
				System.out.print("\t[3] Display current network accuracy on TESTING data.\n");
				System.out.print("\t[4] Run current network on TESTING data showing images and labels.\n");
				System.out.print("\t[5] Save the current network state to a file.\n");
				System.out.print("\t[6] Discard current network.\n");
				System.out.print("\t[7] Exit program.\n");
			}
			
			System.out.print("\nEnter your command: ");
			
			int choice;
			try {
				choice = Integer.parseInt(in.nextLine());
				
			} catch (Exception e) {
				persistentMessage = "Invalid option. Please enter a number.";
				continue;
			}
			
			if (currentNetwork == null) {
				if (choice == 1) {
					System.out.print("Enter number of layers: ");
					int numLayers = Integer.parseInt(in.nextLine());
					int[] layers = new int[numLayers];
					for (int l = 0; l < numLayers; l++) {
						System.out.format("Enter number of nodes in layer %d: ", l+1);
						layers[l] = Integer.parseInt(in.nextLine());
					}
					currentNetwork = ANN.createNetwork(layers);
				} else if (choice == 2) {
					System.out.print("Enter the name of the file: ");
					String fileName = in.nextLine();
					currentNetwork = Part2Abs.loadFromFile(fileName);
				} else if (choice == 3) {
					System.out.println("Thanks, thanks for playin' my game.");
					break;
				}
			} else {
				if (choice == 1) {
					System.out.print("Enter number of Epochs: ");
					int epochs = Integer.parseInt(in.nextLine());
					System.out.print("Enter learning rate: ");
					int learningRate = Integer.parseInt(in.nextLine());
					System.out.print("Enter number of batches: ");
					int numBatches = Integer.parseInt(in.nextLine());
					
					currentNetwork = ANN.trainNetwork(currentNetwork, learningRate, trainingData, numBatches, epochs, true, false);
				} else if (choice == 2) {
					Part2Abs.testNetwork(currentNetwork, trainingData);
				} else if (choice == 3) {
					Part2Abs.testNetwork(currentNetwork, testingData);
				} else if (choice == 4) {
					for (int i = 0; i < testingData.pixels.length; i++) {
						if (i != 0) {
							System.out.println("\n***********************************************************\n");
						}
						System.out.format("Image %d:\n", i+1);
						double[] activation = ANN.compute(currentNetwork, testingData.pixels[i], false);
						boolean correct;
						if (testingData.values[i][Functions.getMax(activation)] == 1) {
							correct = true;
						} else {
							correct = false;
						}
						for (int j = 0; j < testingData.pixels[i].length; j += 28) {
							System.out.println(Functions.asciiArt(Arrays.copyOfRange(testingData.pixels[i], j, j+28)));
						}
						System.out.format("This image is a %d.\n", Functions.getMax(testingData.values[i]));
						System.out.format("The network classified this as a %d, this is %s.\n", Functions.getMax(activation)+1, Boolean.toString(correct));
						System.out.println("\t[1] Continue.\n\t[2] Exit.\n");
						System.out.print("Enger your command: ");
						int choice2;
						try {
							choice2 = Integer.parseInt(in.nextLine());
							
						} catch (Exception e) {
							persistentMessage = "Invalid option. Please enter a number.";
							continue;
						}
						if (choice2 == 2) {
							break;
						}
						
					}
					Functions.asciiArt(testingData.pixels[0]);
				} else if (choice == 5) {
					System.out.print("Enter the name of the file: ");
					String fileName = in.nextLine();
					Part2Abs.saveToFile(currentNetwork, fileName);
				} else if (choice == 6) {
					currentNetwork = null;
				} else if (choice == 7) {
					System.out.println("Thanks, thanks for playin' my game.");
					break;
				}
			}
			
			// I don't know if this works
			Part2Abs.clearScreen();
		}
	}
	
}
