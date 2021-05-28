package com.fajar.imagemosaic.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fajar.imagemosaic.config.RequestFilter;
import com.fajar.imagemosaic.models.WebRequest;
import com.fajar.imagemosaic.models.WebResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/app")
public class AppController {
	
	public AppController() {
		log.info("App Controller");
	}

	@GetMapping(value="/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse test() {
		
		return new WebResponse();
	}
	@RequestMapping(method = RequestMethod.POST, value="/generatemosaic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse generatemosaic(@RequestBody WebRequest request, HttpServletResponse httpServletResponse) {
		
		WebResponse response = new WebResponse();
		response.setImageData(request.getImageData());
		log.info("Processing");
		return response ;
	}
	
}
