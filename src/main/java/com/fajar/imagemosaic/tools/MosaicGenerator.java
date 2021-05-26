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
	static final Integer SIZE = 60;
	public static void main(String[] args) throws IOException {
		ImageUtil.setRandomImageMap();
		BufferedImage scaledImage = Modifier.scale(inputPath, SIZE, SIZE);
		List<RgbColor> rgbs = ImageUtil.getRgbColors(scaledImage);
		BufferedImage outputImage = new BufferedImage(SIZE*SIZE, SIZE*SIZE, BufferedImage.TYPE_INT_RGB);

		// scales the input image to the output image
		Graphics2D g = outputImage.createGraphics();
		g.setColor(Color.green);
		g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
		g.setColor(Color.BLACK);
		Font font = new Font("Arial", Font.BOLD, 50);
		g.setFont(font );
		int index = 0;
		for (int width = 0; width < SIZE; width++) {
			for (int height = 0; height < SIZE; height++, index++) {
				RgbColor rgb = rgbs.get(index);
				BufferedImage img = ImageUtil.getNearestImage(rgb.average());
				g.drawImage(img,width*SIZE, height*SIZE, SIZE, SIZE, null);
//				g.drawString(String.valueOf(rgbs.get(index).average()), width*SIZE, height*SIZE);
				System.out.println("progres: "+(index+1)+"/"+rgbs.size());
			}
		}
		
		g.dispose();
		ImageIO.write(outputImage, "png", new File(OUTPUT_PATH+"output.png") );
		System.out.println("DONE");
	}
	
}
