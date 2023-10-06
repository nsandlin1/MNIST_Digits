package main.utilities;

public class Matrix {
	
	/**
	 * multiply method multiplies two matrices
	 * @params
	 * 		int[][] m1	| first matrix
	 * 		int[][] m2	| second matrix
	 * @returns
	 * 		product 	| product of multiplication of m1 and m2
	 * 				| null if zero matrix, int[][] otherwise	
	 * @throws
	 * 		Exception: Matrix multiplication undefined.		| if matrices are incompatible for matrix
	 * 									| multiplication										
	*/		
	public static int[][] multiply(int[][] m1, int[][] m2) throws Exception {
		if (m1 == null || m1.length == 0 || m1[0].length == 0) {
			return null;
		} else if (m2 == null || m2.length == 0 || m2[0].length == 0) {
			return null;
		} else if (m1[0].length != m2.length) {
			throw new Exception("Matrix multiplication undefined.");
		} else {
			int[][] product = new int[m1.length][m2[0].length];
			
			int[] temp_vector = new int[m2[0].length];
			int temp_sum;
			for (int i = 0; i < m1.length; i++) {
				System.out.println("i: " + i);
				for (int j = 0; j < m2[0].length; j++) {
					System.out.println("j: " + j);
					temp_sum = 0;
					for (int k = 0; k < m1[0].length; k++) {
						System.out.println("k: " + k);
						temp_sum += m1[i][k] * m2[k][j];
						System.out.println("done");
					}
					temp_vector[j] = temp_sum;
				}
				product[i] = temp_vector.clone();
			}
			
			return product;
		}
	}
	
	// function for appealingly displaying matrix
	public static void print_matrix(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			System.out.print("[ ");
			for (int j = 0; j < m[0].length; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println("]");
		}
	}
	
	// function for appealingly displaying vector
	public static void print_vector(int[] v) {
		System.out.print("[ ");
		for (int i = 0; i < v.length; i++) {
			System.out.print(v[i] + " ");
		}
		System.out.println("]");
	}
	
	public static void main(String[] args) {
		int[][] x = {{1, 2, 3},
					 {3, 4, 5},
					 {1, 2, 3}};
		int[][] y = {
					{1, 3, 3}, 
					{2, 4, 2}, 
					{3, 5, 3}
		};
		try {
			int[][] p = multiply(x, y);
			print_matrix(p);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
