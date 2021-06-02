package com.fajar.imagemosaic.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.fajar.imagemosaic.config.ConfigLoader;
import com.fajar.imagemosaic.models.RgbColor;

public class MosaicGenerator {

	static final String RANDOM_IMAGE_PATH =  ConfigLoader.instance().getRandomImagePath();
	
	static final String OUTPUT_PATH  = ConfigLoader.instance().getOutputPath();
	static final Integer SIZE = ConfigLoader.instance().getInputScaleSize(); 
	
	public static void main(String[] args) throws IOException {
		String pathname = "D:\\Archieve\\Pictures\\RandomImages\\arucocalibration.png";
		BufferedImage image = ImageIO.read(new File(pathname ));
		generate(image, true, null);
	}
	
	public static BufferedImage generate(BufferedImage image, boolean writeFile, MosaicProcessNotifier notifier) throws IOException {
		ImageUtil.setRandomImageMap();
		BufferedImage scaledImage = Modifier.scale(image, SIZE);
		 
		List<RgbColor> rgbs = ImageUtil.getRgbColors(scaledImage);
		BufferedImage outputImage = new BufferedImage(scaledImage.getWidth()*SIZE, scaledImage.getHeight()*SIZE, BufferedImage.TYPE_INT_RGB);
		BufferedImage outputImage2 = new BufferedImage(scaledImage.getWidth()*SIZE, scaledImage.getHeight()*SIZE, BufferedImage.TYPE_INT_RGB);

		// scales the input image to the output image
		Graphics2D g = outputImage.createGraphics();
		Graphics2D g2 = outputImage2.createGraphics();

		g2.setColor(Color.white);
		g2.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
		g.setColor(Color.white);
		g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
		
		System.out.println();
		
		int steps = 1;
		
		int index = 0;
		for (int width = 0; width < scaledImage.getWidth(); width++) {
			for (int height = 0; height < scaledImage.getHeight(); height++, index++) {
				RgbColor rgb = rgbs.get(index);
				BufferedImage img = ImageUtil.getNearestImage(rgb);
				g.drawImage(img,width*SIZE, height*SIZE, SIZE, SIZE, null); 
				
				g2.setColor(new Color(rgb.getRed().intValue(),rgb.getGreen().intValue(), rgb.getBlue().intValue(),255 ));
				g2.fillRect(width*SIZE, height*SIZE, SIZE, SIZE);
				
				if (index % 40 == 0) {
					if (notifier!=null) {
						notifier.notify(steps, rgbs.size()/40);
						steps++;
					}
					System.out.println();
				}
				System.out.print("*");
			}
		}
		System.out.println();
		g2.dispose();
		g.dispose();
		if (writeFile) {
			ImageIO.write(outputImage, "png", new File(OUTPUT_PATH+"output.png") );
			ImageIO.write(outputImage2, "png", new File(OUTPUT_PATH+"output_original.png") );
		}
		System.out.println("DONE");
		
		return outputImage;
	}
	
}
