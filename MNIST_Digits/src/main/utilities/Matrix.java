package main.utilities;

public class Matrix {
	
	/**
	 * multiply method multiplies two matrices
	 * 
	 * @param m1 | first matrix
	 * @param m2 | second matrix
	 * @return product of multiplication of m1 and m2
	 * 				     null if zero matrix, int[][] otherwise	
	 * @throws Exception Matrix multiplication undefined. | if matrices are incompatible for matrix
	 * 													     multiplication										
	*/		
	public static double[][] multiply(double[][] m1, double[][] m2) throws Exception {
		if (m1 == null || m1.length == 0 || m1[0].length == 0) {
			return null;
		} else if (m2 == null || m2.length == 0 || m2[0].length == 0) {
			return null;
		} else if (m1[0].length != m2.length) {
			throw new Exception("Matrix multiplication undefined.");
		} else {
			double[][] product = new double[m1.length][m2[0].length];
			
			double[] temp_vector = new double[m2[0].length];
			double temp_sum;
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
	
	/**
	 * dotProduct method returns dot product of two vectors
	 * 
	 * @param
	 * 		double[] v1	| first vector
	 * 		double[] v2	| second vector
	 * @return
	 * 		double 	| product of dot product of v1 and v2	
	 * @throws
	 * 		Exception: Vector Dot Product undefined.		| if vectors are incompatible for vector
	 * 									| multiplication										
	*/	
	public static double dotProduct(double[] m1, double[] m2) throws Exception {
		if (m1.length != m2.length) {
			throw new Exception("Vector Dot Product Undefined.");
		} else {
			double product = 0;
			for (int i = 0; i < m1.length; i++) {
				product += m1[i] * m2[i];
			}
			return product;
		}
	}
	
	/**
	 * add method adds two matrices
	 * 
	 * @param
	 * 		double[][] m1	| first matrix
	 * 		double[][] m2	| second matrix
	 * @return
	 * 		double[][] 	| sum of addition of m1 and m2	
	 * @throws
	 * 		Exception: Matrix addition undefined.		| if matrices are incompatible for matrix
	 * 									| addition										
	*/	
	public static double[][] add(double[][] m1, double[][] m2) throws Exception {
		if (m1.length != m2.length || m1[0].length != m2[0].length) {
			throw new Exception("Matrix addition undefined.");
		} else {
			double[][] sum = new double[m1.length][m1[0].length];
			for (int i = 0; i < m1.length; i++) {
				for (int j = 0; j < m1[0].length; j++) {
					sum[i][j] = m1[i][j] + m2[i][j];
				}
			}
			return sum;
		}
	}
	
	/**
	 * add method adds two vectors
	 * 
	 * @param
	 * 		double[] v1	| first vector
	 * 		double[] v2	| second vector
	 * @return
	 * 		double[] 	| sum of addition of v1 and v2	
	 * @throws
	 * 		Exception: Vector addition undefined.		| if vectors are incompatible for vector
	 * 									| addition										
	*/
	public static double[] add(double[] v1, double[] v2) throws Exception {
		if (v1.length != v2.length) {
			throw new Exception("Vector addition undefined.");
		} else {
			double[] sum = new double[v1.length];
			for (int i = 0; i < v1.length; i++) {
				sum[i] = v1[i] + v2[i];
			}
			return sum;
		}
	}
	
	// function for appealingly displaying matrix
	public static void print_matrix(double[][] m) {
		for (int i = 0; i < m.length; i++) {
			System.out.print("[ ");
			for (int j = 0; j < m[0].length; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println("]");
		}
	}
	
	// function for appealingly displaying vector
	public static void print_vector(double[] v) {
		System.out.print("[ ");
		for (int i = 0; i < v.length; i++) {
			System.out.print(v[i] + " ");
		}
		System.out.println("]");
	}
	
//	public static void main(String[] args) {
//		int[][] x = {{1, 2, 3},
//					 {3, 4, 5},
//					 {1, 2, 3}};
//		int[][] y = {
//					{1, 3, 3}, 
//					{2, 4, 2}, 
//					{3, 5, 3}
//		};
//		try {
//			int[][] p = multiply(x, y);
//			print_matrix(p);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
}
