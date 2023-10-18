package main.utilities;

import java.lang.Math;

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
	 * compute a deep copy of a three dimensional matrix (including jagged)
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
	 * compute a deep copy of a two dimensional matrix (including jagged)
	 * 
	 * @param arr | the input matrix
	 * @return a copy of the matrix
	 */
	public static double[][] deepCopyTwoD(double[][] arr) {
		double[][] theCopy = new double[arr.length][];
		
		for (int i = 0; i < arr.length; i++) {
			theCopy[i] = new double[arr[i].length];
		}

		return theCopy;
	}
	
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
	
	public static double[] deepCopyVector(double[] v) {
		double[] copy = new double[v.length];
		
		for (int i = 0; i < v.length; i++) {
			copy[i] = v[i];
		}
		
		return copy; 
	}
	
	public static double[] convAndNormalize(String[] v) {
		double[] output = new double[v.length - 1];
		
		for (int i = 0; i < output.length; i++) {
			output[i] = Double.parseDouble(v[i+1]) / 255;
		}
		
		return output;
	}
	
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
	
	public static void main(String[] args) {
		System.out.println(chopDecimal("9.43243", 6));
	}
	
}
