package main.utilities;


public class Functions {
	
	/**
	 * sigmoid method applies sigmoid function 1 / (1 + e^(-x)) across vector
	 * 
	 * @param
	 * 		double[] arr	| the vector
	 * @return
	 *		double[] 	| the vector after sigmoid application
	 */
	public static double[] sigmoid(double[] arr) {
		double[] newArr = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = sigmoid(arr[i]);
		}
		return newArr;
	}
	
	/**
	 * sigmoid method applies sigmoid function 1 / (1 + e^(-x))
	 * 
	 * @param
	 * 		double val	| the value
	 * @return
	 *		double 	| the value after sigmoid application
	 */
	public static double sigmoid(double val) {
		return 1 / (1 + Math.exp(-val));
	}
	
	/**
	 * sum method returns sum of members of vector
	 * 
	 * @param
	 * 		double[] arr 	| the vector
	 * @return
	 * 		double 		| the result after summation
	 */
	public static double sum(double[] arr) {
		double total = 0;
		for (int i = 0; i < arr.length; i++) {
			total += arr[i];
		}
		return total;
	}
	
	public static void main(String[] args) {
		double[] arr = new double[] {1, 2, 3};
		Matrix.print_vector(sigmoid(arr));
	}
}
