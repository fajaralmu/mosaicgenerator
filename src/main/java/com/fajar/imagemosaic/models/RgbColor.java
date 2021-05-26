package com.fajar.imagemosaic.models;

import java.awt.image.BufferedImage;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RgbColor {

	private int red;
	private int green;
	private int blue;
	public int sum() {
		return red+green+blue;
	}
	public double average() {
		return (green + red + blue)/3;
	}
	public static double average(List<RgbColor> rgbColors) {
		double sum = 0;
		for (RgbColor rgbColor : rgbColors) {
			sum+=rgbColor.average();
		}
		
		return sum/rgbColors.size();
	}
	public static RgbColor create(BufferedImage image, int x, int y) {
		int pixel = image.getRGB(x, y);
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		return RgbColor.builder().red(red).green(green).blue(blue).build();
	}
}
