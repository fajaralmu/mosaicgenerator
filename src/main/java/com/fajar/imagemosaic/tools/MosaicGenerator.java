package com.fajar.imagemosaic.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.fajar.imagemosaic.models.RgbColor;

public class MosaicGenerator {

	static final String outputDirectoryPath = "D:\\Development\\Fajar\\imagemosaic\\images\\resources\\random_named\\";
	static final String inputPath = "D:\\Development\\Fajar\\imagemosaic\\images\\input\\sample.jpg";
	static final String OUTPUT_PATH  = "D:\\Development\\Fajar\\imagemosaic\\images\\output\\";
	static final Integer SIZE = 80;
	public static void main(String[] args) throws IOException {
		ImageUtil.setRandomImageMap();
		BufferedImage scaledImage = Modifier.scale(ImageIO.read(new File(inputPath)), SIZE);
		 
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
		   
		int index = 0;
		for (int width = 0; width < scaledImage.getWidth(); width++) {
			for (int height = 0; height < scaledImage.getHeight(); height++, index++) {
				RgbColor rgb = rgbs.get(index);
				BufferedImage img = ImageUtil.getNearestImage(rgb);
				g.drawImage(img,width*SIZE, height*SIZE, SIZE, SIZE, null); 
				
				g2.setColor(new Color(rgb.getRed().intValue(),rgb.getGreen().intValue(), rgb.getBlue().intValue(),255 ));
				g2.fillRect(width*SIZE, height*SIZE, SIZE, SIZE);
				
				System.out.println("progres: "+(index+1)+"/"+rgbs.size());
			}
		}
		g2.dispose();
		g.dispose();
		ImageIO.write(outputImage, "png", new File(OUTPUT_PATH+"output.png") );
		ImageIO.write(outputImage2, "png", new File(OUTPUT_PATH+"output_original.png") );
		System.out.println("DONE");
	}
	
}
