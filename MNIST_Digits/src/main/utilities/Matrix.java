package main.utilities;

public class Matrix {
	
	/**
	 * multiply method multiplies two matrices
	 * 
	 * @param m1 | first matrix
	 * @param m2 | second matrix
	 * @return product of multiplication of m1 and m2
	 * @throws Exception Matrix multiplication undefined.									
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
	 * @param v1 | first vector
	 * @param v2 | second vector
	 * @return dot product of v1 and v2	
	 * @throws Exception Vector Dot Product undefined.										
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
	 * @param m1 | first matrix
	 * @param m2 | second matrix
	 * @return sum of m1 and m2	
	 * @throws Exception Matrix addition undefined.									
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
	 * @param v1 | first vector
	 * @param v2 | second vector
	 * @return sum of v1 and v2	
	 * @throws Exception Vector addition undefined. 									
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
	
	/**
	 * add method adds two vectors
	 * 
	 * @param v1 | first vector
	 * @param v2 | second vector
	 * @return sum of v1 and v2	
	 * @throws Exception Vector addition undefined. 									
	*/
	public static int[] add(int[] v1, int[] v2) throws Exception {
		if (v1.length != v2.length) {
			throw new Exception("Vector addition undefined.");
		} else {
			int[] sum = new int[v1.length];
			for (int i = 0; i < v1.length; i++) {
				sum[i] = v1[i] + v2[i];
			}
			return sum;
		}
	}
	
	/**
	 * display 2d-matrix to stdout
	 * 
	 * @param m | the input matrix
	 */
	public static void print_matrix(double[][] m) {
		for (int i = 0; i < m.length; i++) {
			System.out.print("[ ");
			for (int j = 0; j < m[i].length; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println("]");
		}
	}
	
	/**
	 * display vector to stdout
	 * 
	 * @param v | the input vector
	 */
	public static void print_vector(double[] v) {
		System.out.print("[ ");
		for (int i = 0; i < v.length; i++) {
			System.out.print(v[i] + " ");
		}
		System.out.println("]");
	}
	
}
