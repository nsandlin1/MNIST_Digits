package main.utilities;

import java.lang.Math;
import java.util.Random;

public class Functions {
	
	/**
	 * applies sigmoid function 1 / (1 + e^(-x)) across vector
	 * 
	 * @param arr | the vector
	 * @return the vector after sigmoid application
	 */
	public static double[] sigmoid(double[] arr) {
		// define new array
		double[] newArr = new double[arr.length];

		// iterate through arr, apply sigmoid to each index
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = sigmoid(arr[i]);
		}

		// return post-sigmoid array
		return newArr;
	}
	
	/**
	 * computes sigmoid function 1 / (1 + e^(-x))
	 * 
	 * @param val | the input
	 * @return the value after sigmoid application
	 */
	public static double sigmoid(double val) {
		return 1 / (1 + Math.exp(-val));
	}
	
	/**
	 * returns sum of members of vector
	 * 
	 * @param arr | the input vector
	 * @return double | the sum of the vector's constituents
	 */
	public static double sum(double[] arr) {
		double total = 0;
		for (int i = 0; i < arr.length; i++) {
			total += arr[i];
		}
		return total;
	}
	
	/**
	 * returns sum of members of vector
	 * 
	 * @param arr the input vector
	 * @return the sum of the vector's constituents
	 */
	public static int sum(int[] arr) {
		int total = 0;
		for (int i = 0; i < arr.length; i++) {
			total += arr[i];
		}
		return total;
	}
	
	/**
	 * fills vector with given num
	 * 
	 * @param arr | the input vector
	 * @param num | the number to fill arr with
	 * @return a vector of arr's dimension, filled with nums
	 */
	public static double[] fill(double[] arr, double num) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = num;
		}
		return arr;
	}
	
	/**
	 * compute an empty deep copy of a three dimensional matrix (including jagged)
	 * 
	 * @param arr | the input matrix
	 * @return a copy of the matrix
	 */
	public static double[][][] deepCopyThreeD(double[][][] arr) {
		double[][][] theCopy = new double[arr.length][][];

		for (int i = 0; i < arr.length; i++) {
			theCopy[i] = new double[arr[i].length][];
			for (int j = 0; j < arr[i].length; j++) {
				theCopy[i][j] = new double[arr[i][j].length];
			}
		}
		
		return theCopy;
	}
	
	/**
	 * compute an empty deep copy of a two dimensional matrix (including jagged)
	 * 
	 * @param arr | the input matrix
	 * @return an empty copy of the matrix
	 */
	public static double[][] deepCopyTwoD(double[][] arr) {
		double[][] theCopy = new double[arr.length][];
		
		for (int i = 0; i < arr.length; i++) {
			theCopy[i] = new double[arr[i].length];
		}

		return theCopy;
	}
	
	/**
	 * compute a deep copy of a two dimensional matrix (including jagged)
	 * 
	 * @param arr | the input matrix
	 * @return a copy of the matrix
	 */
	public static double[][] deepCopyTwoDWithVars(double[][] arr) {
		double[][] theCopy = new double[arr.length][];
		
		for (int i = 0; i < arr.length; i++) {
			theCopy[i] = new double[arr[i].length];
			for (int j = 0; j < arr[i].length; j++) {
				theCopy[i][j] = arr[i][j];
			}
		}

		return theCopy;
	}
	
	/**
	 * compute a deep copy of a vector
	 * 
	 * @param v | the input vector
	 * @return a copy of the vector
	 */
	public static double[] deepCopyVector(double[] v) {
		double[] copy = new double[v.length];
		
		for (int i = 0; i < v.length; i++) {
			copy[i] = v[i];
		}
		
		return copy; 
	}
	
	/**
	 * convert a list of strings of doubles to a normalized list of doubles
	 * first number in v is the classification
	 * 
	 * @param v | the input vector
	 * @return a vector containing the normalized doubles
	 */
	public static double[] convAndNormalize(String[] v) {
		double[] output = new double[v.length - 1];
		
		for (int i = 0; i < output.length; i++) {
			output[i] = Double.parseDouble(v[i+1]) / 255;
		}
		
		return output;
	}
	
	/**
	 * convert a list of doubles to a list of normalized doubles
	 * 
	 * @param v | the input vector
	 * @return a vector containing the normalized doubles
	 */
	public static double[] normalize(double[] v) {
		double min_value = v[0];
		double max_value = v[0];
		
		for (int i = 0; i < v.length; i++) {
			if (v[i] < min_value) {
				min_value = v[i];
			} else if (v[i] > max_value) {
				max_value = v[i];
			}
		}
		
		double[] output = new double[v.length];
		
		for (int i = 0; i < output.length; i++) {
			output[i] = ((v[i] - min_value) / (max_value - min_value));
		}
		
		return output;
	}
	
	public static double[][] normalizeMatrix(double[][] m) {
		double min_value = m[0][0];
		double max_value = m[0][0];
		
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				if (m[i][j] < min_value) {
					min_value = m[i][j];
				} else if (m[i][j] > max_value) {
					max_value = m[i][j];
				}
			}
		}
		
		double[][] output = new double[m.length][m[0].length];
		
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				output[i][j] = ((m[i][j] - min_value) / (max_value - min_value));
			}
		}
		
		return output;
	}
	
	/**
	 * print a vector of doubles in ascii art relative to value intensities
	 * 
	 * @param v | the input vector
	 */
	public static char[] asciiArt(double[] v) {

		String chars = "`.-':_,^=;><+!rc*/z?sLTv)J7(|Fi{C}fI31tlu[neoZ5Yxjya]2ESwqkP6h9d4VpOGbUAKXHm8RD#$Bg0MNWQ%&@";
		
		char[] art = new char[v.length];

		for (int i = 0; i < art.length; i++) {
			System.out.print(chars.charAt((int) Math.round(v[i] * 90)));
		}
		return art;
	}
	
	/**
	 * find the index of the maximum value of an array
	 * 
	 * @param arr the array from which you want the max
	 * @return the index of the maximum value of the array
	 */
	public static int getMax(double[] arr) {
		int maxIndex = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > arr[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	
	/**
	 * format a decimal string to n chars after decimal
	 * 
	 * @param d the decimal string
	 * @param ads the number of points after the decimal
	 * @return a decimal string formatted to ads points after the decimal
	 */
	public static String chopDecimal(String d, int ads) {
		int i = 0;
		int ad = 0;
		boolean dotFound = false;
		while (ad <= ads) {
			if (i == d.length()) {
				break;
			}
			if (d.charAt(i) == '.') {
				dotFound = true;
			}
			if (dotFound) {
				ad++;
			}
			i++;
		}
		return d.substring(0, i);
	}
	
	public static double[][] unflatten(double[] v, int n) {
		double[][] unflattened = new double[n][n];
		for (int i = 0, k = 0; i < v.length; i++) {
			unflattened[k][i % n] = v[i];
			if ((i + 1) % n == 0) {
				k += 1;
			}
		}
		return unflattened;
	}
	
	public static double[] flatten(double[][] m) {
		double[] flattened = new double[m.length * m.length];
		int vi = 0;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				flattened[vi] = m[i][j];
				vi++;
			}
		}
		return flattened;
	}
	
	// generate random square matrix with values between (including) min and (not including) max
	public static double[][] newRandomSquareMatrix(int size, int min, int max) {
		double[][] randomMatrix = new double[size][size];
		
		Random rand = new Random();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				randomMatrix[i][j] = (rand.nextDouble() * (max - min)) + min;
			}
		}
		
		return randomMatrix;
	}
	
	public static double[] newRandomVector(int size, int min, int max) {
		double[] randomVector = new double[size];
		
		Random rand = new Random();
		
		for (int i = 0; i < size; i++) {
			randomVector[i] = (rand.nextDouble() * (max - min)) + min;
		}
		
		return randomVector;
	}
	
	public static void main(String[] args) {
		double[] matrix = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5};
		Matrix.print_matrix(unflatten(matrix, 5));
	}
}
