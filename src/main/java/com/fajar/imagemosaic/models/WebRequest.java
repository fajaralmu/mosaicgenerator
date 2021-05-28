package com.fajar.imagemosaic.models;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class WebRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8029883602345176048L;
	private String imageData;
}
