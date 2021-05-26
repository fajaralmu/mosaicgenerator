package com.fajar.imagemosaic.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.fajar.imagemosaic.models.RgbColor;

public class ImageUtil {
	
	public static void main(String[] args) {
		String directoryPath = "D:\\Development\\Fajar\\imagemosaic\\images\\resources\\random\\";
		String outputDirectoryPath = "D:\\Development\\Fajar\\imagemosaic\\images\\resources\\random_named\\";
		List<BufferedImage> images = getImagesFromDirectory(directoryPath );
		for (BufferedImage image : images) {
			List<RgbColor> rgb = getRgbColors(image);
			double avg = RgbColor.average(rgb);  
			 
			try {
				ImageIO.write(image, "png", new File(outputDirectoryPath+avg+".png"));
				System.out.println("done: "+avg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<BufferedImage> getImagesFromDirectory(String directoryPath){
		
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		File directory = new File(directoryPath);
		File[] files = directory.listFiles(); 
		for (File file : files) {
		
			if (file.isDirectory()) {
				continue;
			}
			try {
				BufferedImage image = ImageIO.read(file);
				if (image == null  ) {
					continue;
				} 
				images.add(image); 
			} catch (IOException e) {
				System.out.println("Error reading "+file.getName());
				continue;
			}
		}
		return images;
	}
	
	public static List<RgbColor> getRgbColors(BufferedImage image) {
		List<RgbColor> colors = new ArrayList<RgbColor>();
		int width = image.getWidth();
		int height = image.getHeight();

		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {
				int pixel = image.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				colors.add(RgbColor.builder()
						.red(red)
						.green(green)
						.blue(blue)
						.build());
			}

		}
		return colors;
	}
}
