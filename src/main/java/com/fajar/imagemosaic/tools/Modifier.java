package com.fajar.imagemosaic.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Modifier {
	public static void main(String[] args) {
//		testResizeOneImage();
		getRandomImages();
	}

	private static void testResizeOneImage() {
		// TODO Auto-generated method stub
		String input = "D:\\Development\\Fajar\\imagemosaic\\images\\input\\sample.jpg";
		String output = "D:\\Development\\Fajar\\imagemosaic\\images\\output\\sample.jpg";
		resize(input, output, 50, 50);
	}

	private static void getRandomImages() {
		String dir = "D:\\Archieve\\Pictures\\RandomImages";
		String outputDir = "D:\\Development\\Fajar\\imagemosaic\\images\\resources\\random\\";
		File directory = new File(dir);
		File[] files = directory.listFiles();

		 
		for (File file : files) {

			if (file.isDirectory()) {
				continue;
			}
			try {
				BufferedImage image = ImageIO.read(file);
				if (image == null || image.getWidth() < 70 || image.getHeight() < 70) {
					continue;
				}
				System.out.println(file.getName() + " size: " + image.getWidth() + " x " + image.getHeight()); 
				BufferedImage newImage = scale(file.getCanonicalPath(), 100, 100);
				BufferedImage outputImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
				
				Graphics2D g = outputImage.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, 100, 100);
				g.drawImage(newImage, 0, 0, null);
				g.dispose();
				String formatName = file.getName().substring(file.getName().lastIndexOf(".") + 1);
				ImageIO.write(outputImage, formatName, new File(outputDir + file.getName()));
			} catch ( Exception e) {
				System.out.println("Error generating " + file.getName());
				continue;
			}
		}

	}

	// https://www.codejava.net/java-se/graphics/how-to-resize-images-in-java
	public static BufferedImage scale(String inputImagePath, int scaledWidth, int scaledHeight) throws IOException {

		// reads input image
		File inputFile = new File(inputImagePath);

		BufferedImage inputImage = ImageIO.read(inputFile);
		System.out.println("actual size: " + inputImage.getWidth() + " x " + inputImage.getHeight());
		System.out.println("resize to: " + scaledWidth + " x " + scaledHeight);
		// creates output image
		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

		// scales the input image to the output image
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();
		return outputImage;
	}

	// https://www.codejava.net/java-se/graphics/how-to-resize-images-in-java
	public static boolean resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) {

		try { ;
			// creates output image
			BufferedImage outputImage = scale(inputImagePath, scaledWidth, scaledHeight); 

			// extracts extension of output file
			String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

			// writes to output file
			File outputFile = new File(outputImagePath);
			boolean write = ImageIO.write(outputImage, formatName, outputFile);
			return write;
		} catch (Exception ex) {

			return false;
		}

	}
}
