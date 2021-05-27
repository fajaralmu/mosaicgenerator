package com.fajar.imagemosaic.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
