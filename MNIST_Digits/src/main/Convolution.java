package main;

import main.utilities.Functions;
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
	
	public static double relu(double d) {
		if (d > 0) {
			return d;
		} else {
			return 0;
		}
	}
	
	public static double[][] pixelRelu(double[][] pixels) {
		double[][] relued = new double[pixels.length][pixels[0].length];
		
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				relued[i][j] = relu(pixels[i][j]);
			}
		}
		
		return relued;
	}
	
	public static void main(String[] args) {
		double[][] x = {
				{1.5, -2.3, 1},
				{4.3, 23.2, -2},
				{-4, 6.4, -1}
		};
		Matrix.print_matrix(pixelRelu(x));
	}
	
}
