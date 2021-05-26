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

	private Double red;
	private Double green;
	private Double blue;
	public double sum() {
		return red+green+blue;
	}
	public double average() {
		return (green + red + blue)/3;
	}
	public double gap(RgbColor rgb) {
		return Math.abs(rgb.green-green)
				+Math.abs(rgb.blue-blue)
				+Math.abs(rgb.red - red);
	}
	public static RgbColor average(List<RgbColor> rgbColors) {
		double sumGreen = 0, sumRed = 0, sumBlue = 0;
		for (RgbColor rgb  : rgbColors) {
			sumGreen+=rgb.green;
			sumRed += rgb.red;
			sumBlue = rgb.blue;
		}
		
		return RgbColor.builder()
				.red(sumRed/rgbColors.size())
				.blue(sumBlue/rgbColors.size())
				.green(sumGreen/rgbColors.size())
				.build();
				
	}
	public static RgbColor create(String rgbStringSeparatedByStrip) {
//		System.out.println("rgbStringSeparatedByStrip: "+rgbStringSeparatedByStrip);
		String[] raw = rgbStringSeparatedByStrip.split("-");
		return RgbColor.builder()
				.red(Double.parseDouble(raw[0]))
				.green(Double.parseDouble(raw[1]))
				.blue(Double.parseDouble(raw[2]))
				.build();
	}
	public static RgbColor create(BufferedImage image, int x, int y) {
		int pixel = image.getRGB(x, y);
		Integer red = (pixel >> 16) & 0xff;
		Integer green = (pixel >> 8) & 0xff;
		Integer blue = (pixel) & 0xff;
		return RgbColor.builder().red(red.doubleValue()).green(green.doubleValue()).blue(blue.doubleValue()).build();
	}
}
