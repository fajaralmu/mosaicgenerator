package com.fajar.imagemosaic.models;

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
}
