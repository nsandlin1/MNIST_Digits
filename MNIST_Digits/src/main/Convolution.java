package main;

import main.utilities.Matrix;

public class Convolution {
	
	/**
	 * perform a same convolution to an image
	 * 
	 * @param image the image's pixels
	 * @param kernel the kernel to convolve the image with
	 */
	public static double[][] Convolve(double[][] image, double[][] kernel) {
		
		int middle_kernel_row = (kernel.length / 2);
		int middle_kernel_column = (kernel[0].length / 2);
		
		double[][] convolved_image = new double[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				for (int k = 0; k < kernel.length; k++) {
					for (int w = 0; w < kernel[0].length; w++) {
						int image_row = i - middle_kernel_row + k;
						int image_column = j - middle_kernel_column + w;
						
						if (image_row < 0 || image_row >= image.length) {
							continue;
						} else if (image_column < 0 || image_column >= image[0].length) {
							continue;
						} else {
//							System.out.println(image[image_row][image_column]);
//							System.out.println(kernel[k][w]);
							convolved_image[i][j] += image[image_row][image_column] * kernel[k][w];
						}
					}
				}
			}
		}
		
		return convolved_image;
	}
	
	public static double[][] Pool(double[][] image, int poolSize) {
		
		boolean perfect_row = image.length % poolSize == 0;
		boolean perfect_column = image[0].length % poolSize == 0;
		
		double[][] pooled_image;
		
		if (perfect_row) {
			if (perfect_column) {
				pooled_image = new double[image.length / poolSize][image[0].length / poolSize];
			} else {
				pooled_image = new double[image.length / poolSize][(image[0].length / poolSize) + 1];
			}
		} else {
			if (perfect_column) {
				pooled_image = new double[(image.length / poolSize) + 1][image[0].length / poolSize];
			} else {
				pooled_image = new double[(image.length / poolSize) + 1][(image[0].length / poolSize) + 1];
			}
		}
		
		for (int i = 0; i < image.length; i += poolSize) {
			for (int j = 0; j < image[0].length; j += poolSize) {
				double max = 0;
				for (int k = i; k < i + poolSize; k++) {
					if (k >= image.length) {
						continue;
					}
					for (int w = j; w < j + poolSize; w++) {
						if (w >= image[0].length) {
							continue;
						}
						if (image[k][w] > max) {
							max = image[k][w];
						}
					}
				}
				pooled_image[i / poolSize][j / poolSize] = max;
			}
		}
		
		return pooled_image;
	}
	
	public static void main(String[] args) {
		double[][] kernel = new double[][] {
			{1, 0, 1, 3, 2},
			{2, 0, 2, 2, 3},
			{1, 0, 1, 1, 0}
		};
		double[][] image = new double[][] {
			{1, 2, 3, 4, 5},
			{6, 7, 8, 9, 10},
			{11, 12, 32, 14, 15},
			{16, 17, 18, 19, 20},
			{21, 22, 23, 24, 25}
		};
		
		Matrix.print_matrix(Convolve(image, kernel));
		Matrix.print_matrix(Pool(image, -342));
	}
}
