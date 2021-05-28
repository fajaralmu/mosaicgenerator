package com.fajar.imagemosaic.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.fajar.imagemosaic.config.ConfigLoader;
import com.fajar.imagemosaic.models.RgbColor;

public class ImageUtil {

	static Map<RgbColor, BufferedImage> randomImages = new HashMap<RgbColor, BufferedImage>();
	static final String namedRandImagesPath = ConfigLoader.instance().getRandomImagePath();
 
	
	public static void main(String[] args) {
//		System.out.println(ImageUtil);
	}
	
	public static BufferedImage readImageFromBase64String(String data) throws IOException {
		String[] imageData = data.split(",");
		if (imageData == null || imageData.length < 2) {
			return null;
		}
		// create a buffered image
		String imageString = imageData[1];
		BufferedImage image = null;
		byte[] imageByte;

		Base64.Decoder decoder = Base64.getDecoder();
		imageByte = decoder.decode(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();
		
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = output.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		return output;
	}

	public static BufferedImage getNearestImage(RgbColor rgb) {
		System.out.println("avgRgb: " + rgb);
		double gap = 255 * 3;
		RgbColor selectedKey = null;
		for (RgbColor key : randomImages.keySet()) {

			if (key.gap(rgb) < gap) {
				gap = key.gap(rgb);
				selectedKey = key;
			}
		}
		return randomImages.get(selectedKey);
	}
	
	public static String imgToBase64String(final BufferedImage img, final String formatName)
	{
	  final ByteArrayOutputStream os = new ByteArrayOutputStream();

	  try
	  {
	    ImageIO.write(img, formatName, os);
	    return Base64.getEncoder().encodeToString(os.toByteArray());
	  }
	  catch (final IOException ioe)
	  {
	    throw new UncheckedIOException(ioe);
	  }
	}

	public static void setRandomImageMap() throws IOException  {
		randomImages.clear();

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		// Ant-style path matching
		Resource[] resources = resolver.getResources("/random_named/**");

		for (Resource resource : resources) {
			
			try {
				File file = resource.getFile();
				BufferedImage image = ImageIO.read(file);
				if (image == null) {
					continue;
				}
				String name = file.getName().replace("E-", "00").substring(0, file.getName().lastIndexOf("."));

				randomImages.put(RgbColor.create(name), image);

			} catch (IOException e) {
				System.out.println("Error reading " + resource.getFilename());
				continue;
			}

		}
	}

	public static void renameRandomImagesToRgbComponents() {
		String directoryPath = ConfigLoader.instance().getRandomRawImagePath();
		String outputDirectoryPath = namedRandImagesPath;
		List<BufferedImage> images = getImagesFromDirectory(directoryPath);
		for (BufferedImage image : images) {
			List<RgbColor> rgbs = getRgbColors(image);
			RgbColor avg = RgbColor.average(rgbs);

			try {
				String name = avg.getRed() + "-" + avg.getGreen() + "-" + avg.getBlue();
				ImageIO.write(image, "png", new File(outputDirectoryPath + name + ".png"));
				System.out.println("done: " + avg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<BufferedImage> getImagesFromDirectory(String directoryPath) {

		List<BufferedImage> images = new ArrayList<BufferedImage>();
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();
		for (File file : files) {

			if (file.isDirectory()) {
				continue;
			}
			try {
				BufferedImage image = ImageIO.read(file);
				if (image == null) {
					continue;
				}
				images.add(image);
			} catch (IOException e) {
				System.out.println("Error reading " + file.getName());
				continue;
			}
		}
		return images;
	}

	public static List<RgbColor> getRgbColors(BufferedImage image) {
		List<RgbColor> colors = new ArrayList<RgbColor>();
		int width = image.getWidth();
		int height = image.getHeight();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				colors.add(RgbColor.create(image, x, y));
			}
		}
		return colors;
	}
}
