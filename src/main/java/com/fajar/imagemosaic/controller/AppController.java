package com.fajar.imagemosaic.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fajar.imagemosaic.models.WebRequest;
import com.fajar.imagemosaic.models.WebResponse;
import com.fajar.imagemosaic.service.ImageProcessingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	private ImageProcessingService imageProcessingService;
	public AppController() {
		log.info("App Controller");
	}

	@GetMapping(value="/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse test() {
		
		return new WebResponse();
	}
	@PostMapping(value="/generatemosaic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse generatemosaic(@RequestBody WebRequest request, HttpServletResponse httpServletResponse) throws IOException {
		
		return imageProcessingService.generateMosaic(request);
	}
	
}
