package com.fajar.imagemosaic.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.fajar.imagemosaic.config.ConfigLoader;

public class Modifier {
	public static void main(String[] args) {
//		testResizeOneImage();
		getRandomImages();
	}
	
	public static BufferedImage scale(BufferedImage input, Integer minSize) throws IOException {
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
		return scale(input, outputW.intValue(), outputH.intValue());
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
				int minSize = ConfigLoader.instance().getRandomMinSize();
				int outputSize = ConfigLoader.instance().getRandomAdjustedSize();
				BufferedImage image = ImageIO.read(file);
				if (image == null || image.getWidth() < minSize || image.getHeight() < minSize) {
					continue;
				}
				System.out.println(file.getName() + " size: " + image.getWidth() + " x " + image.getHeight()); 
				BufferedImage newImage = scale(image, outputSize, outputSize);
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
	public static BufferedImage scale(BufferedImage inputImage, int scaledWidth, int scaledHeight) throws IOException {
 
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

	 
}
