package com.fajar.imagemosaic.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fajar.imagemosaic.models.WebRequest;
import com.fajar.imagemosaic.models.WebResponse;
import com.fajar.imagemosaic.tools.ImageUtil;
import com.fajar.imagemosaic.tools.MosaicGenerator;

@Service
public class ImageProcessingService {

	@PostConstruct
	public void init() throws IOException {
		ImageUtil.setRandomImageMap();
	}

	public WebResponse generateMosaic(WebRequest request) throws IOException {
		Assert.notNull(request.getImageData(), "image data not present");
		BufferedImage image = ImageUtil.readImageFromBase64String(request.getImageData());
		BufferedImage resultImage = MosaicGenerator.generate(image, false);
		String resultImageString = ImageUtil.imgToBase64String(resultImage, "png");
		WebResponse response = new WebResponse();
		response.setImageData("data:image/png;base64,"+resultImageString);
		return response ;
	}
}
