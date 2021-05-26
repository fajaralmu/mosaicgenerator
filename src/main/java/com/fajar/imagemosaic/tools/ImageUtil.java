package com.fajar.imagemosaic.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.fajar.imagemosaic.models.RgbColor;

public class ImageUtil {
	
	static Map<RgbColor, BufferedImage> randomImages = new HashMap<RgbColor, BufferedImage>();
	static final String namedRandImagesPath = "D:\\Development\\Fajar\\imagemosaic\\images\\resources\\random_named\\";
	public static void main(String[] args) {
//		setRandomImageMap();
		renameRandomImagesToAvgRgbComponents();
	}
	public static BufferedImage getNearestImage(RgbColor rgb) {
		System.out.println("avgRgb: "+rgb);
		double gap = 255*3;
		RgbColor selectedKey = null;
		for (RgbColor key : randomImages.keySet()) {
			
			if (key.gap(rgb) < gap) {
				gap = key.gap(rgb);
				selectedKey = key;
			}
		} 
		return randomImages.get(selectedKey);
	}
	static void setRandomImageMap() {
		randomImages.clear();
		
		File directory = new File(namedRandImagesPath);
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
				String name = file.getName().replace("E-", "00").substring(0, file.getName().lastIndexOf("."));
				 
				randomImages.put(RgbColor.create(name), image);
				 
			} catch (IOException e) {
				System.out.println("Error reading "+file.getName());
				continue;
			}
			
		}
	}
	
	public static void renameRandomImagesToAvgRgbComponents() {
		String directoryPath = "D:\\Development\\Fajar\\imagemosaic\\images\\resources\\random\\";
		String outputDirectoryPath = namedRandImagesPath;
		List<BufferedImage> images = getImagesFromDirectory(directoryPath );
		for (BufferedImage image : images) {
			List<RgbColor> rgbs = getRgbColors(image);
			RgbColor avg = RgbColor.average(rgbs);  
			 
			try {
				String name = avg.getRed()+"-"+avg.getGreen()+"-"+avg.getBlue();
				ImageIO.write(image, "png", new File(outputDirectoryPath+name+".png"));
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
				colors.add(RgbColor.create(image,y,x));
			}
		}
		return colors;
	}
}
