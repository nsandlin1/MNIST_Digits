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
		double[] newArr = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = sigmoid(arr[i]);
		}
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
		// find length of max dimensions
		int max_rows = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].length > max_rows) {
				max_rows = arr[i].length;
			}
		}
		int max_columns = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j].length > max_columns) {
					max_columns = arr[i][j].length;
				}
			}
		}
		
		return new double[arr.length][max_rows][max_columns];
	}
	
	/**
	 * compute a deep copy of a two dimensional matrix (including jagged)
	 * 
	 * @param arr | the input matrix
	 * @return a copy of the matrix
	 */
	public static double[][] deepCopyTwoD(double[][] arr) {
		// find max number of rows
		int max_rows = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].length > max_rows) {
				max_rows = arr[i].length;
			}
		}
		
		return new double[arr.length][max_rows];
	}
	
	public static double[] convAndNormalize(String[] v) {
		double[] output = new double[v.length - 1];
		
		for (int i = 0; i < output.length; i++) {
			output[i] = Double.parseDouble(v[i+1]) / 255;
		}
		
		return output;
	}
	
	public static char[][] asciiArt(double[][] arr) {

		String chars = "`.-':_,^=;><+!rc*/z?sLTv)J7(|Fi{C}fI31tlu[neoZ5Yxjya]2ESwqkP6h9d4VpOGbUAKXHm8RD#$Bg0MNWQ%&@";
		
		char[][] art = new char[arr.length][arr[0].length];

		for (int i = 0; i < art.length; i++) {
			for (int j = 0; j < art[i].length; j++) {
				System.out.print(chars.charAt((int) Math.round(arr[i][j] * 90)));
			}
			System.out.println();
		}
		return art;
	}
	
	public static int getMax(double[] arr) {
		int maxIndex = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > arr[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	
	// ad: after decimal
	// ads: after decimals
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
