package com.fajar.imagemosaic.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	
	public static BufferedImage scale(BufferedImage input, Integer minSize) {
		double w = input.getWidth();
		double h = input.getHeight();
		Double outputW = w, outputH = h;
		double scaleFactor = 1.0d;
		if (w > h) {
			if (h < minSize) {
				scaleFactor = h / minSize; 
			} else {
				scaleFactor = minSize/h;
			}
			outputH = minSize.doubleValue();
			outputW = w * scaleFactor;
		} else {
			if (w < minSize) {
				scaleFactor = w / minSize; 
			} else {
				scaleFactor =minSize/w; 
			}
			outputW = minSize.doubleValue();
			outputH = h * scaleFactor;
		}
		
		System.out.println("Scale to : "+outputW.intValue()+" x "+outputH.intValue());
		
		BufferedImage outputImage = new BufferedImage(outputW.intValue(), outputH.intValue(), input.getType());
		Graphics2D g = outputImage.createGraphics(); 
		g.drawImage(input, 0, 0, outputW.intValue(), outputH.intValue(), null);
		g.dispose();
		return outputImage;
	}

	/**
	 * move random image to specified path if dimension >= 25 x 25
	 */
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
				int minSize = 25;
				int outputSize = 100;
				BufferedImage image = ImageIO.read(file);
				if (image == null || image.getWidth() < minSize || image.getHeight() < minSize) {
					continue;
				}
				System.out.println(file.getName() + " size: " + image.getWidth() + " x " + image.getHeight()); 
				BufferedImage newImage = scale(file.getCanonicalPath(), outputSize, outputSize);
				BufferedImage outputImage = new BufferedImage(outputSize, outputSize, BufferedImage.TYPE_INT_RGB);
				
				Graphics2D g = outputImage.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, outputSize, outputSize);
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
